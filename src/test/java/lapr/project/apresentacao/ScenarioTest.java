package lapr.project.apresentacao;

import com.google.zxing.WriterException;
import lapr.project.controller.*;
import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Constantes;
import lapr.project.utils.FisicaAlgoritmos;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;
import oracle.ucp.util.Pair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScenarioTest {

    private GerirComprasClienteController comprasClienteController;

    private RegistoFarmaciaController registoFarmaciaController;

    private GerirProdutosController produtosController;

    private GerirTransporteController transporteController;

    private EstacionarTransporteController estacionarController;

    private GerirClienteController clienteController;

    private GerirProdutosFarmaciaController produtosFarmaciaController;

    private GerirEstafetaController estafetaController;

    private GerirEncomendasController encomendasController;

    private GerirEntregasController entregasController;

    private DroneHandler droneHandler;

    private ScooterHandler scooterHandler;

    private UserController userController;

    private EstafetaHandler estafetaHandler;

    private BufferedWriter writeToFile;

    private File resultFile;

    private EnderecoDataHandler enderecoDataHandler;

    private EncomendaDataHandler encomendaDataHandler;

    private GestorHandler gestorHandler;


    private static List<Integer> idFarmaciasList = new ArrayList<>();
    private static List<Integer> idProdutosList = new ArrayList<>();
    private static List<Integer> idDronesList = new ArrayList<>();
    private static List<Integer> idScootersList = new ArrayList<>();
    private static Map<String, Endereco> enderecoMap = new HashMap<>();
    private static Map<String, Map<Farmacia, Map<Produto, Integer>>> produtosCliente = new HashMap<>();
    private static Map<String, Integer> nifClientes = new HashMap<>();
    private static Map<Integer, List<String>> estafetaFarmacia = new HashMap<>();
    private static List<Integer> idFarmaciasEncomendas = new ArrayList<>();
    private static List<Pair<Tuple<Endereco, Endereco, String>, Tuple<String, Double, Double>>> enderecosAdjacentes =
            new ArrayList<>();
    private static int inputsEntrega = 0;
    private static int inputsEntregaCreditos = 0;
    private static int estFarmacia = -1;
    private static int estScooter = -1;


    public ScenarioTest() throws IOException, SQLException {
        comprasClienteController = new GerirComprasClienteController();
        registoFarmaciaController = new RegistoFarmaciaController();
        produtosController = new GerirProdutosController();
        transporteController = new GerirTransporteController();
        estacionarController = new EstacionarTransporteController();
        clienteController = new GerirClienteController();
        produtosFarmaciaController = new GerirProdutosFarmaciaController();
        estafetaController = new GerirEstafetaController();
        encomendasController = new GerirEncomendasController();
        userController = new UserController();
        enderecoDataHandler = new EnderecoDataHandler();
        encomendaDataHandler = new EncomendaDataHandler();
        entregasController = new GerirEntregasController();
        droneHandler = new DroneHandler();
        scooterHandler = new ScooterHandler();
        estafetaHandler = new EstafetaHandler();
        gestorHandler = new GestorHandler();
        resultFile = new File("output.txt");
        writeToFile = new BufferedWriter(new FileWriter(resultFile, true));
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        clean();
        new FileWriter("output.txt").close();
        File f = new File("email.txt");
        if (f.exists())
            new FileWriter(f).close();
    }

    @ParameterizedTest
    @Order(1)
    @CsvFileSource(resources = "/inputFarmacias.csv", delimiter = ';')
    void registarFarmaciaCapacidadeEstacionamento(String nome, String idEndereco, String rua, String numPorta, String codPostal, String localidade,
                                                  String pais, double latitude, double longitude, double altitude, int maximoScooters, int numCargaScooters, int maximoDrones,
                                                  int numCargaDrones, double potenciaMaxima) throws IOException {
        writeToFile.append("----------------------REGISTAR FARMACIA E ESTACIONAMENTO----------------------\n\n");
        registoFarmaciaController.novaFarmacia(nome, rua, numPorta, codPostal, localidade, pais, latitude, longitude, altitude);
        int idFarmacia = registoFarmaciaController.registarCapacidadeEstacionamento(maximoScooters, numCargaScooters, maximoDrones, numCargaDrones, potenciaMaxima);
        idFarmaciasList.add(idFarmacia);
        Farmacia f = registoFarmaciaController.getFarmacia(idFarmacia);
        String farmacia = String.format("Farmacia %d:\n\tNome: %s\n\tEndereço:\n\t\tRua: %s\n\t\tNúmero da Porta: %s\n\t\t" +
                        "Código Postal: %s\n\t\tLocalidade: %s\n\t\tPaís: %s\n\t\tLatitude: %.2f\n\t\tLongitude: %.2f\n\t\tAltitude: %.2f\n\n", idFarmacia, f.getNome(),
                f.getEndereco().getRua(), f.getEndereco().getNumPorta(), f.getEndereco().getCodPostal(), f.getEndereco().getLocalidade(), f.getEndereco().getPais(),
                f.getEndereco().getLatitude(), f.getEndereco().getLongitude(), f.getEndereco().getAltitude());
        enderecoMap.put(idEndereco, f.getEndereco());
        writeToFile.append(farmacia);
        String output = String.format("Estacionamento da Farmácia %d:\n\tCapacidade máxima de Scooters: %d\n\tNúmero de Slots de Carregamento para Scooters: %d\n\t" +
                        "Capacidade máxima de Drones: %d\n\tNúmero de Slots de Carregamento para Drones: %d\n\tPotência Máxima dos Slots de Carregamento: %.2f\n\n", idFarmacia,maximoScooters, numCargaScooters,
                maximoDrones, numCargaDrones, potenciaMaxima);
        writeToFile.append(output);
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(2)
    @CsvFileSource(resources = "/inputAdicionarSlotScooter.csv", delimiter = ';')
    void adicionarSlotsScooter(int quantidade, int numSlotsCarga, int farmacia){
        int idFarmacia = idFarmaciasList.get(farmacia - 1);
        registoFarmaciaController.addSlotsScooter(idFarmacia, quantidade, numSlotsCarga);
    }

    @ParameterizedTest
    @Order(3)
    @CsvFileSource(resources = "/inputAdicionarSlotDrone.csv", delimiter = ';')
    void adicionarSlotsDrone(int quantidade, int numSlotsCarga, int farmacia) {
        int idFarmacia = idFarmaciasList.get(farmacia - 1);
        registoFarmaciaController.addSlotsDrone(idFarmacia, quantidade, numSlotsCarga);
    }

    @ParameterizedTest
    @Order(4)
    @CsvFileSource(resources = "/inputGestorFarmacia.csv", delimiter = ';')
    void registarGestorFarmacia(String username, String email, int farmacia) throws IOException {
        writeToFile.append("----------------------REGISTAR GESTOR DA FARMACIA----------------------\n\n");
        int idFarmacia = idFarmaciasList.get(farmacia - 1);
        String output = registoFarmaciaController.registarGestorFarmacia(idFarmacia, username, email);
        writeToFile.append(output).append("\n\n");
        GestorFarmacia g = registoFarmaciaController.getGestorFarmaciaByEmail(email);
        String gestor = String.format("Gestor da Farmácia %d: \n\tE-mail: %s\n\n",
                g.getFarmacia().getId(), g.getEmail());
        writeToFile.append(gestor);
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(5)
    @CsvFileSource(resources = "/inputAdicionarEstafeta.csv", delimiter = ';')
    void adicionarEstafeta(String usermame, String email, String nome, double cargaMaxima, int nif, double peso,  int farmacia) throws IOException {
        writeToFile.append("----------------------REGISTAR ESTAFETA----------------------\n\n");
        int idFarmacia = idFarmaciasList.get(farmacia - 1);
        String output = estafetaController.adicionarEstafeta(idFarmacia,usermame, email, nome, cargaMaxima, nif, peso);
        writeToFile.append(output).append("\n\n").append(String.format("Estafeta Adicionado a Farmacia %d:\n\tE-mail: %s\n\n", idFarmacia, email));
        if (!output.equals("erro no registo")){
            estafetaFarmacia.putIfAbsent(idFarmacia, new ArrayList<>());
            estafetaFarmacia.get(idFarmacia).add(email);
        }
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(6)
    @CsvFileSource(resources = "/inputNovoCliente.csv", delimiter = ';')
    void novoCliente(String username, String password, String email, String nome , String numeroCartao, int ccv , int ano, int mes, int dia,
                     String idEndereco,String rua, String numPorta, String codPostal, String localidade,
                     String pais, double latitude, double longitude , double altitude) throws IOException {
        writeToFile.append("----------------------REGISTAR CLIENTE----------------------\n\n");
        boolean isAdicionado = clienteController.novoCliente(username, password, email, nome, numeroCartao, ccv, LocalDate.of(ano,mes,dia), rua, numPorta, codPostal,
                localidade,pais, latitude, longitude, altitude);
        User u = userController.getUserByEmail(email);
        Cliente c = clienteController.getInformacaoCliente(u.getIdUser());
        enderecoMap.put(idEndereco, c.getEndereco());
        String output = String.format("Cliente:\n\tDados Pessoais:\n\t\tUsername: %s\n\t\tEmail: %s\n\t\tNome: %s\n\tDados Cartão de Crédito:" +
                        "\n\t\tNúmero do Cartão: %s\n\t\tCCV: %d\n\t\tData de validade: %s\n\tEndereço:\n\t\tRua: %s\n\t\tNúmero da Porta: %s\n\t\t" +
                        "Código Postal: %s\n\t\tLocalidade: %s\n\t\tPaís: %s\n\t\tLatitude: %.2f\n\t\tLongitude: %.2f\n\t\tAltitude: %.2f" +
                        "\n\tEstado Conta: %s\n\n",
                c.getUsername(),c.getEmail(), c.getNome(), c.getCartaoCredito().getNumero(), c.getCartaoCredito().getCcv(), c.getCartaoCredito().getDataValidade().toString(),
                c.getEndereco().getRua(), c.getEndereco().getNumPorta(), c.getEndereco().getCodPostal(), c.getEndereco().getLocalidade(),
                c.getEndereco().getPais(), latitude, longitude, altitude, c.getEstado().getDescricaoEstado());
        writeToFile.append(String.format("Cliente %d adicionado com Sucesso: %b\n\n", c.getIdUser(), isAdicionado)).append(output);
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(7)
    @CsvFileSource( resources= "/inputScooter.csv", delimiter = ';')
    void adicionarScooter(int capacidadeBateria, int cargaAtual, double altura, double largura, double peso, double velocidade,
                             boolean isCargaSlot, int farmacia) throws IOException, WriterException  {
        String output;
        int idFarmacia = idFarmaciasList.get(farmacia - 1);
        int idScooter = transporteController.adicionarScooter(capacidadeBateria, cargaAtual, altura, largura, peso, velocidade,
                 idFarmacia, isCargaSlot);
        writeToFile.append("----------------------ADICIONAR SCOOTER----------------------\n\n");
        if (idScooter != -1){
        idScootersList.add(idScooter);
        Scooter sc = transporteController.getScooter(idScooter);
        output = String.format("Scooter %d:\n\tCapacidade da Bateria: %d\n\tAltura: %.2f\n\tLargura: %.2f\n\tPeso: %.2f" +
                        "\n\tVelocidade: %.2f\n\tCarga Atual: %d\n\tEstado: %s\n\n",
                idScooter, sc.getCapacidadeBateria(), sc.getAltura(), sc.getLargura(), sc.getPeso(), sc.getVelocidadeMaxima(),
                sc.getCargaAtual(), sc.getEstado().getDescricaoEstado());
        } else {
            output = "Erro no registo da Scooter";
        }
        writeToFile.append(output);
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(8)
    @CsvFileSource( resources= "/inputDrone.csv", delimiter = ';')
    void adicionarDrone(int capacidadeBateria, int cargaAtual, double altura, double largura, double peso, double velocidade,
                        boolean isCargaSlot, int farmacia,double comprimento, double velocidadeLevantamento)
            throws IOException, WriterException  {
        String output;
        int idFarmacia = idFarmaciasList.get(farmacia - 1);
        int idDrone = transporteController.adicionarDrone(capacidadeBateria, cargaAtual, altura, largura, peso, velocidade, comprimento,
                velocidadeLevantamento, idFarmacia, isCargaSlot);
        writeToFile.append("----------------------ADICIONAR DRONE----------------------\n\n");
        if (idDrone != -1) {
            idDronesList.add(idDrone);
            Drone dr = transporteController.getDrone(idDrone);
             output = String.format("Drone %d:\n\tCapacidade da Bateria: %d\n\tAltura: %.2f\n\tLargura: %.2f\n\tPeso: %.2f" +
                            "\n\tVelocidade: %.2f\n\tComprimento: %.2f\n\tVelocidade de Levantamento: %.2f\n\tCarga Atual: " +
                            "%d\n\tEstado: %s\n\n",
                    idDrone, capacidadeBateria, altura, largura, peso, velocidade, comprimento, velocidadeLevantamento,
                    dr.getCargaAtual(), dr.getEstado().getDescricaoEstado());
        }else{
            output = "Erro no registo do Drone";
        }
        writeToFile.append(output);
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(9)
    @CsvFileSource(resources = "/inputAdicionarProduto.csv", delimiter = ';')
    void adicionarProduto(String nome, double valorUnitario, double preco, double peso, String descricao) throws IOException {
        writeToFile.append("----------------------ADICIONAR PRODUTO AO SISTEMA----------------------\n\n");
        Produto p = new Produto(nome, valorUnitario, preco, peso, descricao);
        String produto = String.format("Produto a Adicionar:\n\tNome: %s\n\tValor Unitário: %.2f\n\tPreço(com iva): %.2f" +
                "\n\tPeso: %.2f\n\tDescrição: %s\n\n", nome, valorUnitario, preco, peso, descricao);
        writeToFile.append(produto);
        produtosController.addProduto(p);
        idProdutosList.add(p.getId());
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(10)
    @CsvFileSource(resources = "/inputAdicionarProdutoFarmacia.csv", delimiter = ';')
    void addProdutoFarmacia(int quantidade, String email, int produto) throws IOException {
        writeToFile.append("----------------------ADICIONAR PRODUTO A FARMACIA----------------------\n\n");
        int idProduto = idProdutosList.get(produto - 1);
        Produto p = produtosController.getProduto(idProduto);
        boolean isAdicionado = produtosFarmaciaController.addProdutoFarmacia(idProduto, quantidade, email);
        int idFarmacia = gestorHandler.getFarmaciaFromGestorEmail(email);
        writeToFile.append(String.format("Produto %s (%d) adicionado com sucesso a Farmacia %d: %b\n\n", p.getNome(), idProduto, idFarmacia, isAdicionado));
        writeToFile.close();
    }


    @ParameterizedTest
    @Order(11)
    @CsvFileSource(resources = "/inputCaminhoTerrestre.csv", delimiter = ';')
    void adicionarCaminhoTerrestre(String idEndereco1, String idEndereco2, String sentido, String condicaoPiso,
                                   double velocidadeVento, double anguloVento) {
        Endereco e1 = enderecoMap.get(idEndereco1);
        Endereco e2 = enderecoMap.get(idEndereco2);
        if (e1 != null && e2 != null ) {
            enderecosAdjacentes.add(new Pair<>(Tuple.create(e1, e2, sentido), Tuple.create(condicaoPiso, velocidadeVento, anguloVento)));
        }
    }

    @Test
    @Order(12)
    void registarCaminhoTerrestre(){
        enderecoDataHandler.insertEnderecosAdjacentes(enderecosAdjacentes, "Terrestre");
        enderecosAdjacentes.clear();
    }

    @ParameterizedTest
    @Order(13)
    @CsvFileSource(resources = "/inputCaminhoAereo.csv", delimiter = ';')
    void adicionarCaminhoAereo(String idEndereco1, String idEndereco2, String sentido, double velocidadeVento, double anguloVento){
        Endereco e1 = enderecoMap.get(idEndereco1);
        Endereco e2 = enderecoMap.get(idEndereco2);
        if (e1 != null && e2 != null ) {
            enderecosAdjacentes.add(new Pair<>(Tuple.create(e1, e2, sentido), Tuple.create(null, velocidadeVento, anguloVento)));
        }
    }

    @Test
    @Order(14)
    void registarCaminhoAereo(){
        enderecoDataHandler.insertEnderecosAdjacentes(enderecosAdjacentes, "Aereo");
        enderecosAdjacentes.clear();
    }

    void addProdutoCesta(int quantidade, String email, int farmacia, int produto, int nif) throws IOException {
        writeToFile.append("----------------------ADICIONAR PRODUTO AO CARRINHO DE COMPRAS----------------------\n\n");
        int idProduto = idProdutosList.get(produto-1);
        Produto p = produtosController.getProduto(idProduto);
        User u = userController.getUserByEmail(email);
        Cliente c = clienteController.getInformacaoCliente(u.getIdUser());
        Farmacia f = registoFarmaciaController.getFarmacia(idFarmaciasList.get(farmacia - 1));
        if (f != null) {
            nifClientes.putIfAbsent(email, nif);
            produtosCliente.putIfAbsent(email, new HashMap<>());
            produtosCliente.get(email).putIfAbsent(f, new HashMap<>());
            produtosCliente.get(email).get(f).put(p, quantidade);
        }
        writeToFile.append(String.format("Carrinho de Compras do Cliente %s (%d):\n\t%s\n\n", c.getNome(),
                c.getIdUser(),produtosCliente.get(email).toString()));
        writeToFile.close();
    }

    void gerarEncomendas(boolean usarCreditos) throws IOException {
        for (String email : produtosCliente.keySet()){
            User u = userController.getUserByEmail(email);
            if (u != null){
                Cliente c = clienteController.getInformacaoCliente(u.getIdUser());
                int id = comprasClienteController.gerarEncomenda(produtosCliente.get(email),nifClientes.get(email),
                        c, usarCreditos);
                if (id != -1) {
                    writeToFile.append("----------------------GERAR ENCOMENDA----------------------\n\n");
                    if (!idFarmaciasEncomendas.contains(id)) {
                        idFarmaciasEncomendas.add(id);
                    }
                    writeToFile.append(String.format("Encomenda para a Farmacia %d efetuada com sucesso.\n\n", id));
                }
            }
        }
        writeToFile.close();
    }

    void gerarEntregas(String tipoEntrega) throws IOException {
        for (Integer idFarmaciaMaisProxima : idFarmaciasEncomendas) {
            writeToFile.append("----------------------ENTREGA----------------------\n\n")
                    .append(String.format("Entrega da farmacia %d\n\n", idFarmaciaMaisProxima));
            Map<Integer, Encomenda> encomendaMap = encomendaDataHandler.getMapEncomendasPorEntregar(idFarmaciaMaisProxima);
            List<Encomenda> l = new ArrayList<>(encomendaMap.values());
            Entrega e = new Entrega(l);
            List<Endereco> lenderecos = entregasController.getEnderecosFromEncomendas(l);
            Estafeta es = estafetaHandler.getEstafetaByEmail(estafetaFarmacia.get(idFarmaciaMaisProxima).get(0));
            e.setEstafeta(es);
            Farmacia f = registoFarmaciaController.getFarmacia(idFarmaciaMaisProxima);
            Drone drone = droneHandler.getListaDronesFarmacia(f).get(0);
            Scooter scooter = scooterHandler.getListaScootersFarmacia(f).get(0);
            Tuple<List<Label>, Double, Double> rota = new Tuple<>(new ArrayList<>(), 0.0, 0.0);
            Tuple<List<Label>, Double, Double> maisRapidoTerrestre = new Tuple<>(new ArrayList<>(), 0.0, 0.0);
            Tuple<List<Label>, Double, Double> maisEficienteTerrestre = new Tuple<>(new ArrayList<>(), 0.0, 0.0);
            Tuple<List<Label>, Double, Double> maisRapidoAereo = new Tuple<>(new ArrayList<>(), 0.0, 0.0);
            Tuple<List<Label>, Double, Double> maisEficienteAereo = new Tuple<>(new ArrayList<>(), 0.0, 0.0);
            double tempoRapidoTerrestre;
            double tempoEficienteTerrestre;
            double tempoRapidoAereo;
            double tempoEficienteAereo;
            String veiculo = null;
            String result;
            if (tipoEntrega.equals("Terrestre") || tipoEntrega.equals("Automatico")) {
                veiculo = Constantes.SCOOTER;
                e.setMeioTransporte(scooter);
                tempoRapidoTerrestre = entregasController.rotaTerrestreMaisRapida(f.getEndereco(), lenderecos, e,
                        scooter, Constantes.VELOCIDADE_MAXIMA_LEGAL, maisRapidoTerrestre);
                result = getInformacaoEntrega(maisRapidoTerrestre, veiculo, tempoRapidoTerrestre);
                writeToFile.append("Caminho mais RAPIDO terrestre:\n\n").append(result);
                tempoEficienteTerrestre = entregasController.rotaTerrestreMaisEficiente(f.getEndereco(), lenderecos, e,
                        scooter, Constantes.VELOCIDADE_MAXIMA_LEGAL, maisEficienteTerrestre);
                result = getInformacaoEntrega(maisEficienteTerrestre, veiculo, tempoEficienteTerrestre);
                writeToFile.append("Caminho mais EFICIENTE terrestre:\n\n").append(result);
            }
            if (tipoEntrega.equals("Aereo") || tipoEntrega.equals("Automatico")) {
                e.setMeioTransporte(drone);
                veiculo = Constantes.DRONE;
                tempoRapidoAereo = entregasController.rotaAereaMaisRapida(f.getEndereco(), lenderecos, e,
                        drone, drone.getVelocidadeMaxima(), maisRapidoAereo);
                result = getInformacaoEntrega(maisRapidoAereo, veiculo, tempoRapidoAereo);
                writeToFile.append("Caminho mais RAPIDO aereo:\n\n").append(result);
                tempoEficienteAereo = entregasController.rotaAereaMaisEficiente(f.getEndereco(), lenderecos, e,
                        drone, drone.getVelocidadeMaxima(), maisEficienteAereo);
                result = getInformacaoEntrega(maisEficienteAereo, veiculo, tempoEficienteAereo);
                writeToFile.append("Caminho mais EFICIENTE aereo:\n\n").append(result);
            }
            if (tipoEntrega.equals("Automatico")) {
                veiculo = entregasController.determinarMelhorRotaEntrega(f, e, scooter, Constantes.VELOCIDADE_MAXIMA_LEGAL, drone, drone.getVelocidadeMaxima(), rota);
                writeToFile.append(String.format("O veiculo mais eficiente para a rota foi %s\n\n", veiculo));
            }
            if (veiculo == null) continue;
            if (veiculo.equals(Constantes.DRONE)){
                e.setEstafeta(null);
                e.setMeioTransporte(drone);
            } else {
                e.setMeioTransporte(scooter);
                if (estFarmacia == -1) estFarmacia = idFarmaciaMaisProxima;
                if (estScooter == -1) estScooter = scooter.getId();
            }
            if (entregasController.criarEntrega(e)) {
                boolean creditosGerados = entregasController.gerarCreditosEntrega(e);
                writeToFile.append("Creditos Gerados: "+creditosGerados+"\n\n");
                entregasController.enviarEmailEncomendas(e);
            }
        }
        idFarmaciasEncomendas.clear();
        produtosCliente.clear();
        nifClientes.clear();
        writeToFile.close();
    }

    @ParameterizedTest
    @Order(15)
    @CsvFileSource( resources= "/inputAddProdutoCesta.csv", delimiter = ';')
    void adicionarProdutoCesta(int quantidade, String email, int farmacia, int produto, int nif) throws IOException {
        addProdutoCesta(quantidade, email, farmacia, produto, nif);
    }

    @Test
    @Order(16)
    void gerarEncomenda() throws IOException {
        gerarEncomendas(false);
    }

    @ParameterizedTest
    @Order(17)
    @CsvFileSource(resources = "/inputGerarEntrega.csv", delimiter = ';')
    void gerarEntrega(String tipoEntrega) throws IOException {
        if (inputsEntrega == 0) gerarEntregas(tipoEntrega);
        inputsEntrega++;
    }

    private String getInformacaoEntrega(Tuple<List<Label>,Double,Double> infoViagem, String veiculo, double tempo){
        if (infoViagem.get1st() == null || infoViagem.get2nd() == null ||
                infoViagem.get3rd() == null || infoViagem.get1st().isEmpty()) return "Não é possível definir o trajeto.\n\n";
        StringBuilder output = new StringBuilder();
        for (Label label : infoViagem.get1st()) {
            StringBuilder s=new StringBuilder();
            s.append ( label.getEnderecoInicio ().getRua() );
            if(label.getEnderecoInicio ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "->" ).append ( label.getEnderecoFim ().getRua() );

            if(label.getEnderecoFim ().isFarmacia ()){
                s.append ( "(farmácia)" );
            }
            s.append ( "\n\tEnergia gasta:" ).append ( String.format("%.2f",label.getEnergiaGasta ()*1000) ).append ( " Wh;\n\tEnergia Restante: " )
                    .append (String.format("%.2f", label.getEnergiaRestante () )).append ( " KWh;" ).append ( "\n\tDistância percorrida: " )
                    .append ( String.format("%.2f",label.getDistancia ()) ).append ( " m" )
                    .append(String.format("\n\tDescrição do Caminho: %s", label.getCaminho().getDescricao()))
                    .append(String.format("\n\tComprimento do Caminho: %s m", label.getCaminho().getComprimento()))
                    .append(String.format("\n\tÂngulo do Vento no Caminho: %s graus", label.getCaminho().getAnguloVento()))
                    .append(String.format("\n\tVelocidade do Vento no Caminho: %s m/s", label.getCaminho().getVelocidadeVento()));

            if (veiculo.equalsIgnoreCase("Scooter")){
                s.append(String.format("\n\tCoeficiente de Resistência: %.3f",((Rua)label.getCaminho()).getCoeficienteResistencia()))
                        .append(String.format("\n\tInclinação da Rua: %.3f rad\n\n", (((Rua) label.getCaminho()).getInclinacao())));
            }

           output.append("\n").append(s.toString());

        }
        output.append("\nDados da viagem:\n").append(String.format("\t%.2f Wh gastos\n",infoViagem.get2nd()*1000))
                .append(String.format("\tDistância Percorrida: %.2f m\n",infoViagem.get3rd ()))
                .append(String.format("\tVelocidade: %.2f m/s\n", Constantes.VELOCIDADE_MAXIMA_LEGAL))
                .append(String.format("\tTempo de Viagem: %.2f min\n", tempo/60))
                .append(String.format("\tDensidade do Ar: %.2f kg/m^3\n", FisicaAlgoritmos.D))
                .append(String.format("\tAceleração Gravítica: %.2f m/s^2\n", FisicaAlgoritmos.G));
        if (veiculo.equals(Constantes.DRONE)){
            output.append(String.format("\tCoeficiente de Arrasto: %.2f\n", FisicaAlgoritmos.CD_DRONE))
            .append(String.format("\tAltitude de Voo: %.2f m\n\n", FisicaAlgoritmos.ALTURA_LEVANTAMENTO_DRONE));
        }else{
            output.append(String.format("\tCoeficiente de Arrasto: %.2f\n\n", FisicaAlgoritmos.CD_SCOOTER));
        }
        return output.toString();
    }

    @Test
    @Order(18)
    void notificarEstacionamento() {
        if (estFarmacia != -1 && estScooter != -1) {
            File data = new File("src-other/estimates/estimate_2021_01_26_00_00_00.data");
            File flag = new File("src-other/estimates/estimate_2021_01_26_00_00_00.data.flag");
            try (FileWriter fw = new FileWriter(data)) {
                fw.write(String.format("%d %d %d", estScooter, estFarmacia, 5));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                flag.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            estacionarController.registarEstacionamentos();
            data.delete();
        }
    }

    @ParameterizedTest
    @Order(19)
    @CsvFileSource( resources= "/inputAddProdutoCestaCreditos.csv", delimiter = ';')
    void adicionarProdutoCestaCreditos(int quantidade, String email, int farmacia, int produto, int nif) throws IOException {
        addProdutoCesta(quantidade, email, farmacia, produto, nif);
    }


    @Test
    @Order(20)
    void gerarEncomendaCreditos() throws IOException {
        gerarEncomendas(true);
    }

    @ParameterizedTest
    @Order(21)
    @CsvFileSource(resources = "/inputGerarEntrega.csv", delimiter = ';')
    void gerarEntregaCreditos(String tipoEntrega) throws IOException {
        if (inputsEntregaCreditos == 0) gerarEntregas(tipoEntrega);
        inputsEntregaCreditos++;
    }

    private static void clean() throws IOException {
        DBHandler handler = new DBHandler();
        try {
            handler.openConnection();
            try (CallableStatement callStmt = handler.getConnection().prepareCall("{ call delete_data() }")) {
                callStmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                handler.closeAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
