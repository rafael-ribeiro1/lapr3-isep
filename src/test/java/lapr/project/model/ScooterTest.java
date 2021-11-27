package lapr.project.model;

import lapr.project.controller.GerirEntregasController;
import lapr.project.data.EnderecoDataHandler;
import lapr.project.model.graph.adjacencyMap.GPSAlgorithms;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.FisicaAlgoritmos;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScooterTest {

    @Test
    void refinarCaminhoScooterTeste() throws IOException, SQLException {
        GerirEntregasController controller = new GerirEntregasController ();

        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "Trindade", "n1", "42342", "234", "wetr", 	41.15227, -8.60929, 104, true );
        Endereco e2 = new Endereco ( "Cais da Ribeira", "n2", "42342", "234", "wetr", 41.14063, -8.61118, 25, false );

        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", FisicaAlgoritmos.distancia ( e1,e2 ), 0,30, 0.004, FisicaAlgoritmos.angulo ( e1,e2 ) );
        Rua r2 = new Rua ( "Cais da Ribeira - Trindade", FisicaAlgoritmos.distancia ( e1,e2 ), 0,150, 0.004, FisicaAlgoritmos.angulo ( e2,e1 ) );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",e2,new CartaoCredito ( "cc",1, LocalDate.now () ) );

        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO);

        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );

        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );
        //public MeioTransporte(int capacidadeBateria, Double altura, Double largura, Double peso, Double velocidadeMaxima, String descricao){

        int maxEnergia=200;
        double energiaAtual=200;
        Scooter mt=new Scooter ( maxEnergia,0.5d,1d,40d,80d );

        Entrega entrega=new Entrega ( estafeta,mt );
        entrega.addEncomenda (lEncomendas);

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( e1 );
        le.add ( e2 );

        Endereco inicio=e1;
        double velocidade=10;
        double massa=entrega.getPeso ()+estafeta.getPeso ()+mt.getPeso ();

        g.insertEdge ( e1, e2, r1, FisicaAlgoritmos.energiaTotalScooter ( massa,r1.getInclinacao (),mt.getArea (),velocidade, r1.getVelocidadeVento (),r1.getAnguloVento (), r1.getComprimento (),r1.getCoeficienteResistencia () ));
        g.insertEdge ( e2, e1, r2, FisicaAlgoritmos.energiaTotalScooter ( massa,r1.getInclinacao (),mt.getArea (),velocidade, r1.getVelocidadeVento (),r1.getAnguloVento (), r1.getComprimento (),r1.getCoeficienteResistencia () ));
        controller.calcularGastoEnergetico (g,mt,massa,velocidade);

        List<Label> infoViagem=new LinkedList<> ();
        Tuple<Double,Double, Double> tent= GPSAlgorithms.shortestPathComEnergiaLimitada ( g,energiaAtual,maxEnergia,le, infoViagem,false );
        Tuple<List<Label>,Double, Double> t2 =controller.rotaMaisEficiente ( g,mt,le,inicio,false );
        double tempoViagem=mt.refinarCaminho ( g, t2,entrega,velocidade);

        for (Label label : infoViagem) {
            StringBuilder s=new StringBuilder();
            s.append ( label.getEnderecoInicio ().getNumPorta () );
            if(label.getEnderecoInicio ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "->" ).append ( label.getEnderecoFim ().getNumPorta () );

            if(label.getEnderecoFim ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "\n\tEnergia gasta:" ).append ( String.format("%.3f",label.getEnergiaGasta ()) ).append ( " Wh;");
            System.out.println (s.toString ());
        }

        System.out.println ("\nDados da viagem:");
        System.out.printf ("\tEnergia gasta: %.3f KWh\n",t2.get2nd ());
        System.out.printf ("\tdistância percorrida: %.2f m\n",t2.get3rd ());
        System.out.printf ("\tvelocidade média: %.1f Km/h\n",velocidade*3.6);
        System.out.printf ("\tTempo de viagem: %.2f min\n",tempoViagem/60);
        System.out.print ( "\tNecessitou de carregamento: " );

        assertEquals ( 2,t2.get1st ().size () );
        assertEquals ( 29,t2.get2nd ()*1000,1 );
        assertEquals ( 0,tent.get3rd (),100 );
    }
    @Test
    void calcularGastosEnergeticosTeste(){
        double expected, result, delta;
        int maxEnergia=200;
        double velocidade=5.5;
        double massa=50;
        double angulo=Math.PI/10;
        Scooter mt=new Scooter ( maxEnergia,0.5d,1d,40d,80d );
        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", 200, 0,0, 0.004, angulo );
        expected=0.022;
        result=mt.calcularGastosEnergeticos ( r1, massa,velocidade,angulo);
        delta=0.001;
        assertEquals ( expected,result,delta );
    }
}