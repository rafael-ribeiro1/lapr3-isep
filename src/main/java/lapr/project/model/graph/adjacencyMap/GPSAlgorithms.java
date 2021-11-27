package lapr.project.model.graph.adjacencyMap;

import lapr.project.model.Caminho;
import lapr.project.model.Endereco;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;

import java.util.LinkedList;
import java.util.List;
public class GPSAlgorithms {
    private GPSAlgorithms() {
        //Impedir instanciação
    }

    public static Tuple<Double, Double,Double> shortestPathComEnergiaLimitada(Graph<Endereco,? extends Caminho> g, Double energiaAtual, double energiaMaxima, List<Endereco> enderecos, List<Label> infoViagem, boolean tempo){
        if(enderecos.size ()<2) return Tuple.create ( 0d,0d,0d );
        //1º->energia gasta; 2º-> energia restante após fim de viagem; 3º -> distância percorrida no troço
        Tuple<Double, Double,Double> valoresFinais=Tuple.create ( 0d,energiaAtual,0d );
        Tuple<Double, Double, Double> valoresTroco;

        for (int i = 0; i < enderecos.size ()-1; i++) {
            if(tempo) valoresTroco=shortestPathComEnergiaLimitadaTempo ( g,valoresFinais.get2nd (),energiaMaxima,enderecos.get ( i ),enderecos.get ( i+1 ),infoViagem );
            else      valoresTroco=shortestPathComEnergiaLimitada ( g,valoresFinais.get2nd (),energiaMaxima,enderecos.get ( i ),enderecos.get ( i+1 ), infoViagem );

            if(valoresTroco.get3rd ()==0){                      //distancia = 0. nao ha caminho. Terminar execução
                valoresFinais.set2nd ( valoresTroco.get2nd () );//energia restante na bateria
                return valoresFinais;
            }
            valoresFinais.set1st (valoresFinais.get1st () + valoresTroco.get1st ()); //energia total gasta
            valoresFinais.set2nd ( valoresTroco.get2nd () );                         //energia restante na bateria
            valoresFinais.set3rd (valoresFinais.get3rd () + valoresTroco.get3rd ()); //distancia total percorrida
            //verificar se ainda tem energia
            if(valoresFinais.get2nd ()<0)
                return valoresFinais; //terminar calculo do caminho mais curto. Caminho é impossível.
        }
        if(tempo) valoresTroco=shortestPathComEnergiaLimitadaTempo ( g,valoresFinais.get2nd (),energiaMaxima,enderecos.get ( enderecos.size ()-1 ),enderecos.get ( 0 ), infoViagem );
        else      valoresTroco=shortestPathComEnergiaLimitada ( g,valoresFinais.get2nd (),energiaMaxima,enderecos.get ( enderecos.size ()-1 ),enderecos.get ( 0 ), infoViagem );

        valoresFinais.set1st (valoresFinais.get1st () + valoresTroco.get1st ()); //energia total gasta
        valoresFinais.set2nd (valoresFinais.get2nd () - valoresTroco.get1st ());//energia restante na bateria
        valoresFinais.set3rd (valoresFinais.get3rd () + valoresTroco.get3rd ()); //distancia total percorrida
        return valoresFinais;
    }

    private static void inicializarArrays(int size, boolean[] visited, double[] energiaDesdeOrigem,double[] distancia){
        for(int i=0;i< size;i++){
            visited[i]=false;
            energiaDesdeOrigem[i]=Double.MAX_VALUE;
            distancia[i]=Double.MAX_VALUE;
        }
    }


    /**
     *
     * @param g peso = distância
     * @param energiaAtual
     * @param energiaMaxima
     * @param vOrig
     * @param vDest
     * @param infoTroco
     * @return
     */

    public static Tuple<Double,Double,Double> shortestPathComEnergiaLimitadaTempo(Graph<Endereco,? extends Caminho> g, Double energiaAtual, double energiaMaxima, Endereco vOrig, Endereco vDest, List<Label> infoTroco){
        if(!g.validVertex(vOrig) || !g.validVertex(vDest))
            return new Tuple<> ( Double.MAX_VALUE,Double.MIN_VALUE,Double.MAX_VALUE );

        int[] path = new int[g.numVertices()];
        double[] energiaDesdeOrigem = new double[g.numVertices()];
        double[] energia=new double[g.numVertices()];
        double[] distancia=new double[g.numVertices()];
        double[] carregamento=new double[g.numVertices()];
        boolean[] visited = new boolean[g.numVertices()];
        inicializarArrays ( g.numVertices (),visited,energiaDesdeOrigem,distancia );

        energiaDesdeOrigem[g.getKey ( vOrig )]=0;
        energia[g.getKey ( vOrig )]=energiaAtual;
        distancia[g.getKey ( vOrig )]=0;

        Endereco v=vOrig;
        Endereco prev=vOrig;

        //get all the min distances to all the vertices in reach
        while(v!=null){
            int vIndex=g.getKey ( v );
            visited[vIndex]=true;
            Iterable<Endereco> adjVertices=g.adjVertices ( v );
            energiaAtual=energia[vIndex];

            for (Endereco adjVertex : adjVertices) {
                Edge<Endereco, ? extends Caminho> edge=g.getEdge ( v,adjVertex );
                int adjIndex=g.getKey ( adjVertex );

                if(!visited[adjIndex] && distancia[adjIndex] > distancia[vIndex] + edge.getElement ().getComprimento ()){
                    energiaDesdeOrigem[adjIndex]=energiaDesdeOrigem[vIndex]+ edge.getWeight (); //somar energia gasta desde da origem
                    path[adjIndex]=vIndex;
                    distancia[adjIndex]=distancia[vIndex] + edge.getElement ().getComprimento ();
                    recarregarVeiculoSeForFarmacia (edge,vIndex,adjIndex, carregamento,energia, energiaMaxima, energiaAtual);
                }
            }
            prev=v;
            v=AdjacencyMapAlgorithms.getVertMinDist ( g,distancia,visited);
            energiaAtual=atualizarEnergia ( g.getEdge ( prev,v ),energiaAtual );
            if(energiaAtual<0) //nao ha energia para chegar ao vértice mais perto. Algortimo termina execução
                return new Tuple<> ( energia[g.getKey ( v )],energiaAtual, distancia[g.getKey ( v )]  );

            energiaAtual=recarregarVeiculoSeForFarmacia (g,v,carregamento,energia,energiaAtual,energiaMaxima );
        }

        if(!visited[g.getKey ( vDest )]) // Se o endereço final não foi visitado, retornar os dados do caminho que percorreu
            return new Tuple<> (energiaDesdeOrigem[g.getKey ( prev )],energiaAtual, distancia[g.getKey ( prev )]);

        LinkedList<Endereco> shortPath=new LinkedList<> ();
        double weight=AdjacencyMapAlgorithms.getPath ( g,g.getKey ( vOrig ),g.getKey ( vDest ),path,distancia,shortPath );
        if(weight==Double.MAX_VALUE)
            return new Tuple<> ( Double.MAX_VALUE,Double.MIN_VALUE,Double.MAX_VALUE );
        shortPath.push(vOrig);
        double energiaGasta=criarTrocos(g,shortPath,infoTroco,energia);
        return new Tuple<> (energiaGasta,energia[g.getKey ( vDest )], weight);
    }


    /**
     *
     * @param g
     * @param energiaAtual
     * @param energiaMaxima
     * @param vOrig
     * @param vDest
     * @return Tuplo de valores, onde o primeiro valor é a energia gasta na viagem, o segundo a energia restante no veículo e o terceiro é a distância total percorrida
     */
    public static Tuple<Double,Double,Double> shortestPathComEnergiaLimitada(Graph<Endereco,? extends Caminho> g, Double energiaAtual, double energiaMaxima, Endereco vOrig, Endereco vDest, List<Label> infoTroco){
        if(!g.validVertex(vOrig) || !g.validVertex(vDest)){
            return new Tuple<> ( Double.MAX_VALUE,Double.MIN_VALUE,Double.MAX_VALUE );
        }
        boolean[] visited = new boolean[g.numVertices()];
        double[] energiaDesdeOrigem = new double[g.numVertices()];
        int[] path = new int[g.numVertices()];
        double[] energia=new double[g.numVertices()];
        double[] distancia=new double[g.numVertices()];
        double[] carregamento=new double[g.numVertices()];
        inicializarArrays ( g.numVertices (),visited,energiaDesdeOrigem,distancia );

        LinkedList<Endereco> shortPath=new LinkedList<> ();

        energiaDesdeOrigem[g.getKey ( vOrig )]=0;
        energia[g.getKey ( vOrig )]=energiaAtual;
        distancia[g.getKey ( vOrig )]=0;

        Endereco v=vOrig;
        Endereco prev=vOrig;

        //get all the min distances to all the vertices in reach
        while(v!=null){
            int vIndex=g.getKey ( v );
            visited[vIndex]=true;
            Iterable<Endereco> adjVertices=g.adjVertices ( v );
            energiaAtual=energia[vIndex];

            for (Endereco adjVertex : adjVertices) {
                Edge<Endereco,? extends Caminho> edge=g.getEdge ( v,adjVertex );
                int adjIndex=g.getKey ( adjVertex );

                if(!visited[adjIndex] && energiaDesdeOrigem[adjIndex]>energiaDesdeOrigem[vIndex]+ edge.getWeight ()){
                    energiaDesdeOrigem[adjIndex]=energiaDesdeOrigem[vIndex]+ edge.getWeight ();
                    path[adjIndex]=vIndex;
                    distancia[adjIndex]=distancia[vIndex] + edge.getElement ().getComprimento ();
                    recarregarVeiculoSeForFarmacia (edge,vIndex,adjIndex, carregamento,energia, energiaMaxima, energiaAtual);
                }
            }
            prev=v;
            v=AdjacencyMapAlgorithms.getVertMinDist ( g,energiaDesdeOrigem,visited);
            energiaAtual=atualizarEnergia ( g.getEdge ( prev,v ),energiaAtual );
            if(energiaAtual<0)//nao ha energia para chegar ao vértice mais perto. Algortimo termina execução
                return new Tuple<> ( energia[g.getKey ( v )],energiaAtual, distancia[g.getKey ( v )]  );
            energiaAtual=recarregarVeiculoSeForFarmacia (g,v,carregamento,energia,energiaAtual,energiaMaxima );
        }
        if(!visited[g.getKey ( vDest )])
           return new Tuple<> (energiaDesdeOrigem[g.getKey ( prev )],energiaAtual, distancia[g.getKey ( prev )]);

        double result=AdjacencyMapAlgorithms.getPath ( g,g.getKey ( vOrig ),g.getKey ( vDest ),path,energiaDesdeOrigem,shortPath );
        if(result==Double.MAX_VALUE)
            return new Tuple<> ( Double.MAX_VALUE,Double.MIN_VALUE,0d );
        shortPath.push(vOrig);
        double energiaTotal=criarTrocos(g,shortPath,infoTroco,energia);
        return new Tuple<> (energiaTotal,energia[g.getKey ( vDest )], distancia[g.getKey ( vDest )]);
    }

    private static double recarregarVeiculoSeForFarmacia(Graph<Endereco, ? extends Caminho> g,Endereco v, double[]carregamento,double[]energia,double energiaAtual, double energiaMaxima){
        double energiaAtualizada=energiaAtual;
        if(v!=null && v.isFarmacia ()){
            int vIndex=g.getKey ( v );
            carregamento[vIndex]=energiaMaxima-energia[vIndex];
            energiaAtualizada = energiaMaxima;
        }
        return energiaAtualizada;
    }

    private static double criarTrocos(Graph<Endereco, ? extends Caminho> g,List<Endereco> shortestPath, List<Label> infoTroco, double[]energia){
        double energiaTotal=0;
        for (int i = 0; i < shortestPath.size ()-1; i++) {
            Endereco e1=shortestPath.get ( i );
            Endereco e2=shortestPath.get ( i+1 );
            energiaTotal+=g.getEdge ( e1,e2 ).getWeight ();
            infoTroco.add ( new Label ( e1,e2,g.getEdge ( e1,e2 ).getWeight (), energia[g.getKey ( e2 )],g.getEdge ( e1,e2 ).getElement ())) ;
        }
        return energiaTotal;
    }

    private static double atualizarEnergia(Edge<Endereco, ? extends Caminho> edge, double energiaAtual){
        if(edge!=null)
            energiaAtual-=edge.getWeight ();
        return energiaAtual;
    }

    private static void recarregarVeiculoSeForFarmacia(Edge<Endereco, ? extends Caminho> edge, int vIndex, int adjIndex,double[]carregamento,double[] energia, double energiaMaxima, double energiaAtual){
        Endereco adjVertex= edge.getVDest ();
        if(adjVertex.isFarmacia ()){
            carregamento[adjIndex]=energiaMaxima-energia[vIndex];
            energia[adjIndex]=energiaMaxima;
        }else {
            energia[adjIndex] = energiaAtual - edge.getWeight ();
            if(energia[adjIndex] > energiaMaxima)
                energia[adjIndex] = energiaMaxima;
        }
    }
}
