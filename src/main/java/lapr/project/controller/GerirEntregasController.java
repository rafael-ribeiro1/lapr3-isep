package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.model.graph.adjacencyMap.AdjacencyMapAlgorithms;
import lapr.project.model.graph.adjacencyMap.Edge;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.*;
import lapr.project.model.graph.adjacencyMap.GPSAlgorithms;
import oracle.ucp.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import static lapr.project.utils.Constantes.*;

/**
 * Controller das entregas
 */
public class GerirEntregasController {
    /**
     * Grafo aéreo
     */
    private Graph<Endereco, Caminho> gAereo;
    /**
     * Grafo Terrestre.
     */
    private Graph<Endereco, Rua> gTerrestre;
    /**
     * User a usar controller .
     */
    private User user;
    /**
     * Handler das scooters.
     */
    private ScooterHandler sctHandler;
    /**
     * Handler das farmácias
     */
    private FarmaciaHandler farmaciaHandler;
    /**
     * Handler dos drones
     */
    private DroneHandler droneHandler;
    /**
     * Handler dos endereços.
     */
    private EnderecoDataHandler enderecoHandler;
    /**
     * Handler de gerir encomendas.
     */
    private GerirEncomendasController encomendasController;
    /**
     * Handler dos produtos da farmácia.
     */
    private ProdutosFarmaciaHandler produtosFarmaciaHandler;
    /**
     * Handler das Entregas.
     */
    private EntregaDataHandler entregaDataHandler;
    /**
     * Handler dos emails
     */
    private EmailHandler emailHandler;

    /**
     * Logger utilizado na classe
     */
    private static final Logger logger =Logger.getLogger ( GerirEntregasController.class.getName () );

    //<editor-fold default-state="collapsed" name="construtor e setter do handler">

    /**
     * Construtor
     * @throws IOException caso ocorra algum erro na ligação à base de dados
     */
    public GerirEntregasController() throws IOException,SQLException {
        this.sctHandler=new ScooterHandler ();
        this.farmaciaHandler = new FarmaciaHandler();
        this.droneHandler=new DroneHandler ();
        this.enderecoHandler=new EnderecoDataHandler ();
        this.produtosFarmaciaHandler = new ProdutosFarmaciaHandler();
        this.encomendasController = new GerirEncomendasController();
        this.entregaDataHandler = new EntregaDataHandler ();
        this.emailHandler =new EmailHandler ();
    }

    /**
     * Setter do user do controller
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter do handler dos produtos da farmacia (Apenas utilizados para testes unitários)..
     * @param produtosFarmaciaHandler
     */
    public void setProdutoFarmaciaHandler(ProdutosFarmaciaHandler produtosFarmaciaHandler) { this.produtosFarmaciaHandler = produtosFarmaciaHandler; }

    /**
     * Setter do handler das Scooters (Apenas utilizados para testes unitários).
     * @param sctHandler Handler das scooters.
     */
    public void setSctHandler(ScooterHandler sctHandler) { this.sctHandler = sctHandler; }

    /**
     * Setter do handler das farmacias.(Apenas utilizados para testes unitários).
     * @param farmaciaHandler handler das farmacias.
     */
    public void setFarmaciaDB(FarmaciaHandler farmaciaHandler) { this.farmaciaHandler = farmaciaHandler; }

    /**
     * Setter do handler dos drones. (Apenas utilizados para testes unitários).
     * @param droneHandler Handler dos drones.
     */
    public void setDroneHandler(DroneHandler droneHandler) { this.droneHandler = droneHandler; }

    /**
     * Setter do handler dos enderecos (Apenas utilizados para testes unitários).
     * @param enderecoHandler Handler dos endereços.
     */
    public void setEnderecoHandler(EnderecoDataHandler enderecoHandler) { this.enderecoHandler = enderecoHandler; }

    /**
     *  Setter do controller das encomendas (Apenas utilizados para testes unitários).
     * @param encomendasController Encomendas Controller.
     */
    public void setEncomendasController(GerirEncomendasController encomendasController) { this.encomendasController = encomendasController; }
    /**
     *  Setter do controller das entregas (Apenas utilizados para testes unitários).
     * @param entregaDataHandler entregasController.
     */
    public void setEntregaDataHandler(EntregaDataHandler entregaDataHandler) { this.entregaDataHandler = entregaDataHandler; }

    //</editor-fold>

    /**
     * Determina a melhor rota de entrega segundo uma lista de encomendas.
     * São consideradas as rotas terrestres e aereas mais rapida e eficiente.
     * Guarda na variavel melhorTrajeto a informacao relativa ao trajeto escolhido.
     *
     * @param farmacia A farmacia onde ira ser iniciada a entrega.
     * @param entrega A entrega a ser executada.
     * @param scooter A scooter para fazer o percurso terrestre.
     * @param velocidadeScooter A velocidade media da scooter.
     * @param drone O drone para fazer o percurso aereo.
     * @param velocidadeDrone A velocidade media do drone.
     * @param melhorTrajeto Tuple que vai guardar a informação relativa ao melhor trajeto. No primeiro parametro tem uma
     *                     lista com informacao aos trocos percorridos, no segundo a energia total gasta e no terceiro a distancia total percorrida.
     * @return String do veiculo mais eficaz.
     */
    public String determinarMelhorRotaEntrega(Farmacia farmacia, Entrega entrega, MeioTransporte scooter, double velocidadeScooter,MeioTransporte drone, double velocidadeDrone, Tuple<List<Label>,Double,Double> melhorTrajeto){
        double bestScore=Double.MAX_VALUE;
        String veiculo = "";
        List<Endereco> lEnderecos=getEnderecosFromEncomendas ( entrega.getListEncomendas () );
        //Drone
        //calcular rota mais rapida, em segundos
        if(drone != null) {
            entrega.setMeioTransporte ( drone );
            gerarGrafoAereo (drone, entrega.getPeso () + entrega.getEstafeta ().getPeso (), velocidadeDrone);
            Tuple<List<Label>,Double,Double> droneTempoMelhorTrajeto=criarTupleVazia ();
            double tempoPercursoMaisRapidoDrone=determinarMelhorRotaEntrega ( gAereo,farmacia.getEndereco (),lEnderecos,entrega,drone,velocidadeDrone,droneTempoMelhorTrajeto,true );
            double droneTempoScore=calcularScorePercurso ( tempoPercursoMaisRapidoDrone, droneTempoMelhorTrajeto.get2nd ());
            //calcular mais eficiente em termos energéticos, em KWh

            Tuple<List<Label>,Double,Double> droneEnergiaMelhorTrajeto=criarTupleVazia ();

            double tempoPercursoMaisEnergeticoDrone=determinarMelhorRotaEntrega ( gAereo,farmacia.getEndereco (),lEnderecos,entrega,drone,velocidadeDrone,droneEnergiaMelhorTrajeto,false );
            double droneEnergiaScore=calcularScorePercurso ( tempoPercursoMaisEnergeticoDrone, droneEnergiaMelhorTrajeto.get2nd ());

            if(droneTempoScore<droneEnergiaScore) {
                melhorTrajeto.set (droneTempoMelhorTrajeto);
                bestScore = tempoPercursoMaisRapidoDrone;
            }else{
                melhorTrajeto.set (droneEnergiaMelhorTrajeto);
                bestScore=tempoPercursoMaisEnergeticoDrone;
            }
            veiculo=DRONE;
        }
        //Scooter
        if(scooter!=null){
            entrega.setMeioTransporte (scooter);
            //tempo em segundos
            gerarGrafoTerrestre (scooter, entrega.getPeso ()+ entrega.getEstafeta ().getPeso (), velocidadeScooter);
            Tuple<List<Label>,Double,Double> scooterTempoMelhorTrajeto = criarTupleVazia ();
            double tempoPercursoMaisRapidoScooter=determinarMelhorRotaEntrega(gTerrestre,farmacia.getEndereco (),lEnderecos,entrega,scooter,velocidadeScooter,scooterTempoMelhorTrajeto,true);
            double scooterScoreTempo= calcularScorePercurso ( tempoPercursoMaisRapidoScooter, scooterTempoMelhorTrajeto.get2nd ());

            if(scooterScoreTempo<bestScore) {
                melhorTrajeto.set (scooterTempoMelhorTrajeto);
                bestScore = tempoPercursoMaisRapidoScooter;
                veiculo= SCOOTER;
            }

            //energia em KWh
            Tuple<List<Label>,Double,Double> scooterEnergiaMelhorTrajeto= criarTupleVazia ();

            double tempoPercursoMaisEnergeticoScooter=determinarMelhorRotaEntrega ( gTerrestre,farmacia.getEndereco (),lEnderecos,entrega,scooter,velocidadeScooter,scooterEnergiaMelhorTrajeto,false );
            double scooterEnergiaScore=calcularScorePercurso ( tempoPercursoMaisEnergeticoScooter, scooterEnergiaMelhorTrajeto.get2nd ());

            if(scooterEnergiaScore<bestScore) {
                melhorTrajeto.set (scooterEnergiaMelhorTrajeto);
                veiculo=SCOOTER;
            }
            if(veiculo.equals ( DRONE )){
                entrega.setMeioTransporte (drone);
            }
        }
        return veiculo;
    }

    /**
     * Gera grafo terrestre e calcula os gastos energéticos para todas as possíveis arestas
     * segundo uma certa massa e velocidade.
     * @param mt O meio de transporte a ser usado.
     * @param massa A massa a ser carregada pelo meio de transporte.
     * @param velocidade A velocidade media do meio de transporte.
     */
    private void gerarGrafoTerrestre(MeioTransporte mt, double massa, double velocidade){
        if(gTerrestre==null) gTerrestre=enderecoHandler.criarGrafoEnderecos ();
        calcularGastoEnergetico (gTerrestre, mt,massa,velocidade);
    }

    /**
     * Gera grafo aereo e calcula os gastos energéticos para todas as possíveis arestas
     * segundo uma certa massa e velocidade.
     * @param mt O meio de transporte a ser usado.
     * @param massa A massa a ser carregada pelo meio de transporte.
     * @param velocidade A velocidade media do meio de transporte.
     */
    private void gerarGrafoAereo(MeioTransporte mt, double massa, double velocidade){
        if(gAereo==null) gAereo=enderecoHandler.criarGrafoEspacoAereo ();
        calcularGastoEnergetico (gAereo, mt,massa,velocidade);
    }

    /**
     * Calcula a aerea  mais rapida.
     * Caso o grafo ainda não exista, este é gerado antes de invocar o metodo determinarMelhorRotaEntrega.
     * @param inicio O endereço de início da viagem.
     * @param lEnderecos A lista de endereços a percorrer.
     * @param entrega Entrega a ser feita.
     * @param mt O meio de transporte a ser utilizado.
     * @param velocidade A velocidade média do meio de transporte.
     * @param melhorTrajeto Tuple que guarda uma lista com a informação de todos os troços, a energia total gasta e a distância total percorrida.
     * @return O tempo total gasto na viagem.
     */
    public double rotaAereaMaisRapida(Endereco inicio,List<Endereco> lEnderecos, Entrega entrega, MeioTransporte mt,
                                      double velocidade, Tuple<List<Label>,Double,Double> melhorTrajeto){
        gerarGrafoAereo (mt, entrega.getPeso () + entrega.getEstafeta ().getPeso (), velocidade);
        return determinarMelhorRotaEntrega ( gAereo,inicio,lEnderecos,entrega,mt, velocidade,melhorTrajeto,true );
    }
    /**
     * Calcula a rota aerea energeticamente mais eficiente.
     * Caso o grafo ainda não exista, este é gerado antes de invocar o metodo determinarMelhorRotaEntrega.
     * @param inicio O endereço de início da viagem.
     * @param lEnderecos A lista de endereços a percorrer.
     * @param entrega Entrega a ser feita.
     * @param mt O meio de transporte a ser utilizado.
     * @param velocidade A velocidade média do meio de transporte.
     * @param melhorTrajeto Tuple que guarda uma lista com a informação de todos os troços, a energia total gasta e a distância total percorrida.
     * @return O tempo total gasto na viagem.
     */
    public double rotaAereaMaisEficiente(Endereco inicio,List<Endereco> lEnderecos, Entrega entrega, MeioTransporte mt,
                                         double velocidade, Tuple<List<Label>,Double,Double> melhorTrajeto){
        gerarGrafoAereo (mt, entrega.getPeso () + entrega.getEstafeta ().getPeso (), velocidade);
        return determinarMelhorRotaEntrega ( gAereo,inicio,lEnderecos,entrega,mt, velocidade,melhorTrajeto,false );
    }

    /**
     * Calcula a rota terrestre mais rapido.
     * Caso o grafo ainda não exista, este é gerado antes de invocar o metodo determinarMelhorRotaEntrega.
     * @param inicio O endereço de início da viagem.
     * @param lEnderecos A lista de endereços a percorrer.
     * @param entrega Entrega a ser feita.
     * @param mt O meio de transporte a ser utilizado.
     * @param velocidade A velocidade média do meio de transporte.
     * @param melhorTrajeto Tuple que guarda uma lista com a informação de todos os troços, a energia total gasta e a distância total percorrida.
     * @return O tempo total gasto na viagem.
     */
    public double rotaTerrestreMaisRapida(Endereco inicio,List<Endereco> lEnderecos, Entrega entrega, MeioTransporte mt,
                                          double velocidade, Tuple<List<Label>,Double,Double> melhorTrajeto){
        gerarGrafoTerrestre (mt, entrega.getPeso () + entrega.getEstafeta ().getPeso (), velocidade);
        return determinarMelhorRotaEntrega ( gTerrestre,inicio,lEnderecos,entrega,mt, velocidade,melhorTrajeto,true );
    }

    /**
     * Calcula a rota terrestre energeticamente mais eficiente.
     * Caso o grafo ainda não exista, este é gerado antes de invocar o metodo determinarMelhorRotaEntrega.
     * @param inicio O endereço de início da viagem.
     * @param lEnderecos A lista de endereços a percorrer.
     * @param entrega Entrega a ser feita.
     * @param mt O meio de transporte a ser utilizado.
     * @param velocidade A velocidade média do meio de transporte.
     * @param melhorTrajeto Tuple que guarda uma lista com a informação de todos os troços, a energia total gasta e a distância total percorrida.
     * @return O tempo total gasto na viagem.
     */
    public double rotaTerrestreMaisEficiente(Endereco inicio,List<Endereco> lEnderecos, Entrega entrega, MeioTransporte mt,
                                          double velocidade, Tuple<List<Label>,Double,Double> melhorTrajeto){
        gerarGrafoTerrestre (mt, entrega.getPeso () + entrega.getEstafeta ().getPeso (), velocidade);
        return determinarMelhorRotaEntrega ( gTerrestre,inicio,lEnderecos,entrega,mt, velocidade,melhorTrajeto,false );
    }

    /**
     * Retorna um tuple com valores default de calculo de percurso mais eficiente.
     * @return Tuple com valores default de calculo de percurso mais eficiente.
     */
    private Tuple<List<Label>,Double,Double> criarTupleVazia(){
        return Tuple.create ( new LinkedList<> (), Double.MAX_VALUE, Double.MAX_VALUE );
    }

    /**
     * Calcula a melhor rota de entrega, caso o principal critério seja tempo ou energia gasta.
     * Em primeiro lugar obtém o melhor trajeto. Por fim, refina este percurso, tendo em conta
     * o tempo gasto em possíveis paragens para recarregamento, assim como as perdas graduais
     * de peso quando uma entrega é feita.
     * Guarda a informação necessária da viagem no Tuple passado por parâmetro, retornando o tempo
     * de viagem total.
     * @param grafo Grafo representativo do mapa a percorrer.
     * @param inicio O endereço de início da viagem.
     * @param lEnderecos A lista de endereços a percorrer.
     * @param entrega Entrega a ser feita.
     * @param mt O meio de transporte a ser utilizado.
     * @param velocidade A velocidade média do meio de transporte.
     * @param melhorTrajeto Tuple que guarda uma lista com a informação de todos os troços, a energia total gasta e a distância total percorrida.
     * @param tempo True se for para obter o percurso mais rapido. False caso contrario.
     * @return O tempo total gasto na viagem.
     */
    public double determinarMelhorRotaEntrega(Graph<Endereco, ? extends Caminho> grafo, Endereco inicio,List<Endereco> lEnderecos,Entrega entrega, MeioTransporte mt, double velocidade, Tuple<List<Label>,Double,Double> melhorTrajeto, boolean tempo){
        melhorTrajeto.set(rotaMaisEficiente (grafo,mt,lEnderecos,inicio,tempo));
        return mt.refinarCaminho ( grafo,melhorTrajeto, entrega,velocidade);
    }

    /**
     * Devolve o score de um dado percurso segundo o tempo de viagem e a energia gasta.
     * @param tempo Tempo da viagem.
     * @param energia Energia gasta.
     * @return Score.
     */
    public double calcularScorePercurso(double tempo, double energia){
        return 0.5 * tempo + 0.5 * energia;
    }

    /**
     * Determina a rota mais eficiente para uma entrega. Funciona tanto para termos energéticos como temporais.
     * O método calcula as varias permutacoes possiveis para as entregas.
     * De seguida, chama o metodo shortestPathComEnergiaLimitada da classe GPSAlgorithms
     * para calcular o caminho mais eficiente para cada permutação.
     * Se não encontrar nenhum caminho possível, devolve um Tuple com uma lista vazia,
     * a energia total gasta igual a infinito,assim como a distância total percorrida.
     * @param grafo Grafo representativo do mapa em questão.
     * @param mt Meio de transporte a usar para a viagem.
     * @param lEnderecos Lista de endereços como paragem.
     * @param inicio Endereço que será o início da viagem.
     * @param tempo True se for para calcular o caminho mais rápido. False caso contrário.
     * @return Tuple, onde o 1º parâmetro é uma lista com informações sobre cada troço, o 2º é a energia total gasta e o 3º é a distância total percorrida.
     */
    public Tuple<List<Label>, Double,Double> rotaMaisEficiente(Graph<Endereco,? extends Caminho> grafo, MeioTransporte mt, List<Endereco> lEnderecos, Endereco inicio, boolean tempo){
        List<LinkedList<Endereco>> permutacoesEnderecos=Algoritmos.permutacoesComComecoIgual ( lEnderecos,inicio,true);
        List<Label> infoViagem = new LinkedList<>();
        LinkedList<Label> tempInfoViagem;
        //1->energia total da viagem; 2->Energia restante; 3-> distancia total percorrida
        Tuple<Double,Double,Double> metaDadosViagem= Tuple.create ( Double.MAX_VALUE,Double.MIN_VALUE,Double.MAX_VALUE );

        for (LinkedList<Endereco> p : permutacoesEnderecos) {
            tempInfoViagem=new LinkedList<> ();
            Tuple<Double,Double,Double> tempMetaDados=GPSAlgorithms.shortestPathComEnergiaLimitada(grafo,mt.getCapacidadeEficaz (),mt.getCapacidadeBateria (),p,tempInfoViagem,tempo);
            //se endereço final nao for igual ao inicial é porque não fez o percurso até chegar à farmácia inicial
            if(!tempInfoViagem.isEmpty () && !tempInfoViagem.getLast ().getEnderecoFim ().equals ( tempInfoViagem.getFirst ().getEnderecoInicio () )
                || tempMetaDados.get2nd ()<0){ //energia restante é menor que 0, o que significa que não há energia no drone para fazer o percurso
                continue;
            }
            if(tempo && tempMetaDados.get3rd ()< metaDadosViagem.get3rd ()){
                // como a velocidade é constante, o tempo da viagem depende unicamente da distância percorrida
                infoViagem=tempInfoViagem;
                metaDadosViagem=tempMetaDados;
            }
            if(!tempo && tempMetaDados.get1st ()<metaDadosViagem.get1st ()){
                infoViagem=tempInfoViagem;
                metaDadosViagem=tempMetaDados;
            }
        }
        //return da informação de cada troço, da energia total gasta e da distância total percorrida
        return Tuple.create ( infoViagem,metaDadosViagem.get1st (),metaDadosViagem.get3rd ());
    }

    /**
     * Adiciona uma encomenda a uma entrega.
     * Se o peso máximo não for excedido, então a encomenda é adicionada à entrega e retirada do map de encomendas.
     * Caso contrário, apresenta uma mensagem  a informar do insucesso da operaçao.
     * @param entrega Entrega onde irá ser adicionada a encomenda.
     * @param idEncomenda O id da encomenda a adicionar.
     * @param mEncomendas Map onde a chave é o id de uma encomenda e o valor é o objeto da respetiva encomenda.
     * @param pesoMaximo Peso máximo da entrega.
     * @return True de encomenda foi adicionada à entrega. False caso contrario.
     */
    public boolean addEncomenda(Entrega entrega, int idEncomenda, Map<Integer,Encomenda> mEncomendas, double pesoMaximo){
        Encomenda e=mEncomendas.get ( idEncomenda );
        if(e==null){
            String str= "Encomenda com o id " + idEncomenda + " não encontrada.";
            logger.info ( str );
            return false;
        }
        if(isPesoEntregaAbaixoDoMaximo ( e.getPeso (),entrega.getPeso (),pesoMaximo )){
            entrega.addEncomenda ( e );
            mEncomendas.remove ( idEncomenda );
            return true;
        }else{
            //peso maximo excedido
            double pesoComEncomenda=entrega.getPeso () + e.getPeso ();
            double pesoEmExcesso= pesoComEncomenda - pesoMaximo;
            String str= "Peso máximo de " + pesoMaximo + " Kg excedido." +
                    "\nPeso com encomenda selecionada: " + pesoComEncomenda + " Kg." +
                    "\nPeso em excesso: " + pesoEmExcesso + " Kg.";
            logger.info ( str );
            return false;
        }
    }

    /**
     * Retorna os enderecos de uma respetiva lista de encomendas.
     * @param lEncomendas Lista de encomendas.
     * @return Lista de enderecos das encomendas.
     */
    public List<Endereco> getEnderecosFromEncomendas(List<Encomenda> lEncomendas){
        List<Endereco> l =new ArrayList<>(lEncomendas.size ());
        for (Encomenda enc : lEncomendas) {
            l.add ( enc.getCliente ().getEndereco () );
        }
        return l;
    }

    /**
     * Retorna um Map de enderecos com as suas respetivas encomendas.
     * Util quando se quer obter a encomenda de um certo endereco com complexidade constante.
     * @param lEncomendas Lista de encomendas.
     * @return Map de enderecos, onde as chaves sao os enderecos e os valores sao as encomendas.
     */
    public static Map<Endereco, Encomenda> getMapEnderecosEncomendas(List<Encomenda> lEncomendas){
        Map<Endereco, Encomenda> map=new HashMap<>(lEncomendas.size ());
        for (Encomenda e : lEncomendas) {
            map.put ( e.getCliente ().getEndereco (),e );
        }
        return map;
    }

    /**
     * Percorre o grafo passado por parametro e calcula os gastos energeticos segundo um certo veiculo, recorrendo ao
     * metodo abstrato da classe MeioTransporte.
     * @param grafo Grafo com enderecos.
     * @param mt Meio de Transporte que será usado para os calculos,
     * @param massa Massa total a ser transportada pelo meio de transporte, sem contar com a massa do veículo.
     * @param velocidadeTransporte A velocidade média que o transporte ira andar.
     */
    public void calcularGastoEnergetico(Graph<Endereco, ? extends Caminho> grafo, MeioTransporte mt, double massa, double velocidadeTransporte){
        Iterable<? extends Edge<Endereco, ? extends Caminho>> caminhos= grafo.edges ();
        for (Edge<Endereco, ? extends Caminho> caminho : caminhos) {
            double gasto=mt.calcularGastosEnergeticos ( caminho.getElement (),massa,velocidadeTransporte,caminho.getElement ().getAnguloVento () );
            gasto= gasto > 0? gasto : 0;
            caminho.setWeight ( gasto );
        }
    }

    //<editor-fold default-state="collapsed" name="UC18 - Rota de Menor Custo">

    /**
     * Retorna o estafeta logado.
     * @return Estafeta logado. Null de o utilizador nao for estafeta.
     */
    public Estafeta getEstafetaAtual(){
        try{
            return (Estafeta)InstanciaAtual.getInstance ().getCurrentUser ();
        }catch (ClassCastException e){
            logger.info ( "Utilizador não é estafeta." );
            return null;
        }
    }

    //</editor-fold>

    //<editor-fold default-state="collapsed" name="UC19 - Obter Encomendas">


    /**
     * Verifica se o peso da entrega esta abaixo do limite máximo com a nova encomenda
     * @param pesoEncomenda Peso da encomenda a adicionar
     * @return True seo peso da entrega esta abaixo do limite máximo com a nova encomenda. False caso contrario.
     */
    public boolean isPesoEntregaAbaixoDoMaximo(double pesoEncomenda, double pesoEntrega, double pesoMaximo){
        return (pesoEncomenda + pesoEntrega <= pesoMaximo);
    }
    //</editor-fold>

    /**
     * Marca o veiculo com o id passado como parametro para o estafeta atual utilizar na entrega
     * @param idVeiculo id do veiculo a utilizar.
     * @return boolean se foi marcado veiculo com sucesso
     */
    public boolean marcarVeiculoComoAUsar(int idVeiculo){
        return sctHandler.marcarScooterComoAUsar(idVeiculo);
    }

    /**
     * Obter o endereço da farmácia do respetivo utilizador
     * @return Endereço da farmácia
     */
    public Farmacia getFarmaciaUser(){
        if(user instanceof Estafeta){
            return ((Estafeta) user).getFarmacia ();
        }else if(user instanceof GestorFarmacia){
            return ((GestorFarmacia) user).getFarmacia ();
        }
        return null;//não é nem estafeta nem Gestor
    }

    /**
     * Obtém o caminho mais curto a partir de um conjunto de rotas.
     * @param grafo Grafo a percorrer.
     * @param rotas Lista de permutações de todos os pontos a visitar.
     * @param mt O meio de transporte a usar.
     * @return
     */
    public Tuple<List<Label>,Double,Double> shortestPath(Graph<Endereco,? extends Caminho> grafo, List<LinkedList<Endereco>> rotas, MeioTransporte mt){
        double minEnergia=Double.MAX_VALUE;
        List<Label> menorPercurso = new ArrayList<>();
        List<Label> tempMenorPercurso ;
        double menorDistancia=0d;
        for (List<Endereco> rota : rotas) {
            tempMenorPercurso = new LinkedList<>();
            Tuple<Double,Double,Double>  tuple=GPSAlgorithms.shortestPathComEnergiaLimitada(grafo,mt.getCapacidadeEficaz(),mt.getCapacidadeBateria(),rota,tempMenorPercurso,true);
            //1º->energia gasta; 2º-> energia restante após fim de viagem; 3º -> distância percorrida no troço
            if(tuple.get1st()<minEnergia){
                minEnergia= tuple.get1st();
                menorDistancia= tuple.get3rd();
            }
        }
        return new Tuple<>(menorPercurso, minEnergia,menorDistancia);
    }

    /**
     *  Método que dando um grafo e um endereco , retorna a farmacia mais perto desse dado endereco tendo em conta os gastos energeticos para o percurso.
     * @param grafo grafo que se pretende utilizar
     * @param end endereco de onde se pretende sair
     * @return Pair em que o primeiro elemento é a farmacia mais perto e o double , é o custo energetico
     */
    public Pair<Farmacia,Double> farmaciaMaisPerto(Graph<Endereco,? extends Caminho> grafo, Endereco end){
        double minEnergia=Double.MAX_VALUE;
        List<Farmacia> lFarmacias= farmaciaHandler.getListaFarmacias ();
        Farmacia farmaciaMaisPerto=null;
        for (Farmacia f : lFarmacias) {
            LinkedList<Endereco> tempShortestPath=new LinkedList<> ();
            double energia=AdjacencyMapAlgorithms.shortestPath ( grafo,end,f.getEndereco(),tempShortestPath);
            if(energia<minEnergia && energia != 0 ){
                farmaciaMaisPerto =f ;
                minEnergia=energia;
            }
        }
        if (farmaciaMaisPerto == null){
            return null;
        }
        return new Pair<> ( farmaciaMaisPerto,minEnergia );
    }

    /**
     *  Método que devolve a farmacia mais perto de um endereco de um user passado como parametro
     * @param enderecoCliente endereco de um cliente
     * @return Farmacia mais perto do endereco do user
     */
    public Farmacia getFarmaciaMaisPertoDeUser(Endereco enderecoCliente ){
       Pair <Farmacia , Double> farmacia =  farmaciaMaisPerto( enderecoHandler.criarGrafoEnderecos(),enderecoCliente);
       if(farmacia==null)return null;
       return farmacia.get1st();
    }

    /**
     *  Método que realiza transferencia de produto para uma farmacia que necessita do produto passado como parametro
     * @param pontoInicial Farmacia que pretende o produto passado como parametro
     * @param prod produto que se pretende que a farmacia obtenha
     * @param quantidade quantidade de produto passada como parametro
     * @return True se houve transferencia de produto e falso se nao se consegiu realizar transferencia
     */
    public boolean realizarTransferenciaProduto(Farmacia pontoInicial,Produto prod,int quantidade){
        List<Farmacia> listaFarmacias = produtosFarmaciaHandler.getFarmaciasComStock(prod,quantidade);
        if (listaFarmacias.isEmpty()) return false;
        return realizarTransferenciaEntreDuasFarmaciasDefinidas(pontoInicial,listaFarmacias,prod,quantidade);
    }
    /**
     * Método que reallza a transferencia de produtos entre duas farmacias já sabendo as farmacias iniciais e finais
     * @param farmaciaRecetora farmacia que recebe o produto
     * @param listaFarmacias Lista de farmacias com a farmacia fornecedora
     * @param prod Produto pretendido
     * @param quantidade quantidade desejada do produto
     * @return
     */
    public boolean realizarTransferenciaEntreDuasFarmaciasDefinidas(Farmacia farmaciaRecetora , List<Farmacia> listaFarmacias,Produto prod,int quantidade){
        MeioTransporte drone = null;
        if(!droneHandler.getListaDronesFarmacia(farmaciaRecetora).isEmpty()) {
                   drone = droneHandler.getListaDronesFarmacia(farmaciaRecetora).get(0);
        }
        MeioTransporte scooter = null;
            if(!sctHandler.getListaScootersFarmacia(farmaciaRecetora).isEmpty()){
                scooter= sctHandler.getListaScootersFarmacia(farmaciaRecetora).get(0);
            }
        if(drone==null&&scooter==null) {
           return false;
        }
        double massaTotalScooter = Constantes.PESO_MEDIO_SCOOTER+Constantes.PESO_MEDIO_ESTAFETA+prod.getPeso()*quantidade;
        double massaTotalDrone = Constantes.PESO_MEDIO_DRONE+prod.getPeso()*quantidade;
        gerarGrafoTerrestre (scooter, massaTotalScooter,VELOCIDADE_MAXIMA_LEGAL);
        gerarGrafoAereo (drone, massaTotalDrone,VELOCIDADE_MAXIMA_LEGAL);
        Pair<Farmacia,Double> pairScooter = obterRotaMaisEficiente(enderecoHandler.criarGrafoEnderecos(), farmaciaRecetora.getEndereco(),listaFarmacias,scooter,(Constantes.PESO_MEDIO_SCOOTER+Constantes.PESO_MEDIO_ESTAFETA+prod.getPeso()*quantidade));
        Pair<Farmacia,Double> pairDrone  = obterRotaMaisEficiente(enderecoHandler.criarGrafoEspacoAereo(),farmaciaRecetora.getEndereco(),listaFarmacias,drone,(Constantes.PESO_MEDIO_DRONE+prod.getPeso()*quantidade));
        Pair<Farmacia,MeioTransporte> par = compararRotas(pairScooter,pairDrone,scooter,drone);
        if(par==null)return false;
        return  produtosFarmaciaHandler.realizarTransferenciaProduto(farmaciaRecetora,par.get1st(),prod,quantidade,par.get2nd().getId());
    }

    /**
     * Método que recebe duas rotas , uma area e outra pelo chao e compara fornecendo a mais eficiente energeticamente.
     * @param scooter Rota pela scooter.
     * @param drone Rota pelo drone.
     * @param scooterTra Scooter utilizada na rota.
     * @param droneTra Drone utilizado no trajeto.
     * @return Pair com a farmácia mais perto e o meio de transporte mais eficiente para chegar a tal farmácia.
     */
    public Pair<Farmacia,MeioTransporte> compararRotas(Pair<Farmacia,Double>  scooter, Pair<Farmacia,Double> drone, MeioTransporte scooterTra   , MeioTransporte droneTra){
        if(scooter == null && drone ==null) return null;
        if(scooter == null) return new Pair<>(drone.get1st(),droneTra);
        if(drone == null) return new Pair<>(scooter.get1st(),scooterTra);

        if(scooter.get2nd()>drone.get2nd()){
            return new Pair<>(drone.get1st(),droneTra);
        }else{
            return  new Pair<>(scooter.get1st(),scooterTra);
        }
    }

    /**
     * Método que fornece a rota mais eficiente conforme o grafo passado como parametro.
     * @param grafo Grafo que se pretende utilizar , pode ser aéreo ou terrestre.
     * @param pontoPartida Ponto de partida da rota
     * @param farmaciasPretendidas Lista de farmácias , em que se quer descobrir qual destas fica mais perto de ir e voltar.
     * @param meioTransporte Meio de transporte a ser utilizado no trajeto.
     * @param pesoTotal Peso total durante a rota.
     * @return Farmácia mais perto do endereço de ponto de partida e o custo energético de ir e voltar a essa farmácia a partir do ponto inicial.
     */
    public Pair<Farmacia,Double> obterRotaMaisEficiente(Graph<Endereco, ? extends  Caminho> grafo, Endereco pontoPartida , List<Farmacia> farmaciasPretendidas, MeioTransporte meioTransporte, Double pesoTotal ){
       if(meioTransporte ==null){
           return null;
       }
        List<LinkedList<Endereco>> possiveisRotas  = new ArrayList<>();
        // adicionar todas as farmacias
        for(Farmacia f : farmaciasPretendidas){
            LinkedList<Endereco> rota = new LinkedList<>();
            rota.add(pontoPartida);
            rota.add(f.getEndereco());
            possiveisRotas.add(rota);
        }
        calcularGastoEnergetico (grafo,meioTransporte,pesoTotal,Constantes.VELOCIDADE_MAXIMA_LEGAL);
        double energiaMinima= Double.MAX_VALUE;
        Endereco enderecoMaisPerto = null;

        for(LinkedList<Endereco> rota : possiveisRotas){
            List<Label> tempListLabel = new LinkedList<>();
            Tuple<Double,Double,Double> tupleTem = GPSAlgorithms.shortestPathComEnergiaLimitada(grafo,meioTransporte.getCapacidadeEficaz(), meioTransporte.getCapacidadeBateria(),rota,tempListLabel,false);
            Double tempEnergia = tupleTem.get1st();
            if( energiaMinima>tempEnergia&& tupleTem.get2nd()>0 ){
                energiaMinima=tempEnergia;
                enderecoMaisPerto = rota.getLast();
            }
        }
        if(enderecoMaisPerto==null)return null;
        Farmacia farmaciaMaisPerto = farmaciaHandler.getFarmaciaByEndereco(enderecoMaisPerto);
        return new Pair<>(farmaciaMaisPerto,energiaMinima);
    }

    /**
     * Determina tempo estimado de carregamento de um veículo.
     * @param mt MeioTransporte O meio de transporte respetivo ao carregamento.
     * @param energiaTotalGasta Energia total gasta pelo veículo.
     * @return O tempo estimado de carregamento, em segundos.
     */
    public static double determinarTempoCarregamento(MeioTransporte mt, double energiaTotalGasta) {
        double tempoCarregamento=0;
        double energiaMaximaVeiculo = mt.getCapacidadeBateria ();
        double energiaAtualVeiculo = mt.getCapacidadeEficaz ();
        if (energiaTotalGasta > energiaMaximaVeiculo) {
            double tempoCargaCompleta=energiaMaximaVeiculo/Constantes.POTENCIA_MEDIA_CARREGADOR; //determina o tempo necessário para a bateria ficar carregada em horas
            double cargaACarregar=energiaMaximaVeiculo*(1-energiaAtualVeiculo);
            tempoCarregamento = cargaACarregar*tempoCargaCompleta*3600; //devolver tempo em segundos
        }
        return tempoCarregamento;
    }


    /**
     * Devolve em segundos o tempo necessario para carregar até carga maxima
     * @param energiaDesejada A energia desejada na bateria, em Wh
     * @param energiaAtual A energia atual da bateria, em Wh
     * @return Tempo necessário para atingir
     */
    public double estimarTempoCarregamento(double energiaAtual,double energiaDesejada){
        double tempoCargaCompleta=energiaDesejada/Constantes.POTENCIA_MEDIA_CARREGADOR; //determina o tempo necessário para a bateria ficar carregada em horas
        double cargaACarregar=energiaDesejada*(1-energiaAtual);
        return cargaACarregar*tempoCargaCompleta*3600; //devolver tempo em segundos
    }

    /**
     * Insere uma entrega na base de dados.
     * @param e Entrega a inserir
     * @return True se a entrega foi inserida com sucesso. False caso contrário.
     */
    public boolean criarEntrega(Entrega e){
        return entregaDataHandler.inserirEntrega (e);
    }

    /**
     * Gera créditos respetivos a encomendas de uma entrega
     * @param entrega Entrega a gerar créditos
     * @return Sucesso da operação
     */
    public boolean gerarCreditosEntrega(Entrega entrega){
        if (entrega == null) return false;
        for (Encomenda e: entrega.getListEncomendas() ) {
            encomendasController.gerarCreditosCliente(e.getId());
        }
        return true;
    }

    /**
     * Envia e-mail aos clientes respetivos de uma entrega a informar que a sua encomenda está a ser entregue
     * @param e Entrega
     * @return True se enviou com sucesso todos os e-mails. False caso contrário.
     */
    public boolean enviarEmailEncomendas(Entrega e){
        StringBuilder corpo;
        boolean isSuccessful=true;
        for (Encomenda encomenda : e.getListEncomendas ()) {
            corpo=new StringBuilder();
            corpo.append ( "A sua encomenda: " );
            Map<Produto,Integer> produtos=encomenda.getProdutosEncomendados ();
            for (Map.Entry<Produto,Integer> entry : produtos.entrySet ()) {
                corpo.append ( "\n\t" ).append ( entry.getKey ().getNome () )
                        .append ( "->" ).append ( entry.getValue () ).append ( " unidades" );
            }
            corpo.append ( "\nBrevemente será entregue na sua morada." );
            Cliente c=encomenda.getCliente ();
            isSuccessful=emailHandler.sendEmail ( c.getEmail (),"Envio de Encomenda #"+encomenda.getId (),corpo.toString () );
        }
        return isSuccessful;
    }
}
