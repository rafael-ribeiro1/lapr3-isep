package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.model.graph.adjacencyMap.Edge;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.Algoritmos;
import lapr.project.utils.Constantes;
import lapr.project.utils.Label;
import lapr.project.utils.Tuple;
import oracle.ucp.util.Pair;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance ( TestInstance.Lifecycle.PER_CLASS )
public class GerirEntregasControllerTest {

    private Estafeta estafeta;
    private GerirEntregasController controller;
    @Mock private EncomendaDataHandler encMock;
    @Mock private ScooterHandler scthandler;
    @Mock private EnderecoDataHandler enderecoMock;
    @Mock private DroneHandler droneMock;
    @Mock private FarmaciaHandler farmaciaMock;
    @Mock private ProdutosFarmaciaHandler produtosFarmaciaMock;
    @Mock private EntregaDataHandler entregaMock;
    @Mock private GerirEncomendasController gerirEncomendasController;
    @BeforeEach
    public void setup() throws IllegalAccessException, NoSuchFieldException {
        //Reset do singleton
        Field instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
        instance.setAccessible ( true );
        instance.set ( null, null );
        try {
            this.controller = new GerirEntregasController();
            this.estafeta = new Estafeta(123, "123", "email", "nome", 20, 1, 1, 1);
            this.gerirEncomendasController = Mockito.mock(GerirEncomendasController.class);
            this.encMock = Mockito.mock(EncomendaDataHandler.class);
            this.scthandler = Mockito.mock(ScooterHandler.class);
            this.droneMock = Mockito.mock(DroneHandler.class);
            this.enderecoMock = Mockito.mock(EnderecoDataHandler.class);
            this.farmaciaMock = Mockito.mock(FarmaciaHandler.class);
            this.produtosFarmaciaMock= Mockito.mock(ProdutosFarmaciaHandler.class);
            this.controller = new GerirEntregasController ();
            this.estafeta = new Estafeta ( 123, "123", "email", "nome", 20, 1, 1, 1 );

            this.encMock = Mockito.mock ( EncomendaDataHandler.class );
            this.scthandler = Mockito.mock ( ScooterHandler.class );
            this.droneMock = Mockito.mock ( DroneHandler.class );
            this.enderecoMock = Mockito.mock ( EnderecoDataHandler.class );
            this.farmaciaMock = Mockito.mock ( FarmaciaHandler.class );
            this.produtosFarmaciaMock = Mockito.mock ( ProdutosFarmaciaHandler.class );
            this.entregaMock=Mockito.mock ( EntregaDataHandler.class );

            this.controller.setUser(estafeta);
            this.controller.setSctHandler(scthandler);
            this.controller.setEnderecoHandler(enderecoMock);
            this.controller.setDroneHandler(droneMock);
            this.controller.setFarmaciaDB(farmaciaMock);
            this.controller.setProdutoFarmaciaHandler(produtosFarmaciaMock);
            this.controller.setEncomendasController(gerirEncomendasController);
            this.controller.setUser ( estafeta );
            this.controller.setSctHandler ( scthandler );
            this.controller.setEnderecoHandler ( enderecoMock );
            this.controller.setDroneHandler ( droneMock );
            this.controller.setFarmaciaDB ( farmaciaMock );
            this.controller.setProdutoFarmaciaHandler ( produtosFarmaciaMock );
            this.controller.setEntregaDataHandler ( entregaMock );
        } catch (IOException | SQLException e) {
            System.err.println(this.getClass().getSimpleName() + " -> Construtor: Falha no setup");
        }
    }

    @Nested
    @DisplayName("Criar Entrega")
    class CriarEntregaTestes {
        @Test
        @DisplayName("Verificar Estafeta Atual")
        void getEstafetaAtualTeste() {
            Estafeta result;
            GerirEntregasController mock = mock ( GerirEntregasController.class );
            Estafeta expected = new Estafeta ( 123, "123", "email", "nome", 20, 1, 1, 234 );

            when ( mock.getEstafetaAtual () ).thenReturn ( null );
            result = mock.getEstafetaAtual ();
            assertNull ( result );

            when ( mock.getEstafetaAtual () ).thenReturn ( expected );
            result = mock.getEstafetaAtual ();
            assertEquals ( expected, result );
        }

        @Test
        void inserirEntregaTeste(){
            Entrega e= new Entrega ( 0,LocalDate.now (),null );
            when(controller.criarEntrega ( e)).thenReturn ( true );
            assertTrue ( controller.criarEntrega ( e ) );
            when(controller.criarEntrega ( e)).thenReturn ( false );
            assertFalse (controller.criarEntrega ( e ));
        }

        @Test
        void marcarVeiculoComoAUsarSucessoTeste() {
            when(scthandler.marcarScooterComoAUsar(1)).thenReturn(true);
            assertTrue(controller.marcarVeiculoComoAUsar(1));
        }

        @Test
        void marcarVeiculoComoAUsarFalhaTeste() {
            when(scthandler.marcarScooterComoAUsar(1)).thenReturn(false);
            assertFalse(controller.marcarVeiculoComoAUsar(1));
        }

        @Nested
        @DisplayName("Obter Rota de Menor Custo Energético")
        class RotaMenorCustoTestes {

            @Test
            void getEstafetaAtualTeste() {
                System.out.println("getEstafetaAtualTeste");
                Estafeta estafeta = new Estafeta(123, "123", "email", "nome", 20, 1, 1, 70);
                GerirEntregasController cMock = Mockito.spy(GerirEntregasController.class);
                Mockito.when(cMock.getEstafetaAtual()).thenReturn(estafeta);
            }

            @Test
            void obterRotaMenorCustoEnergeticoTeste2() {
                double peso = 0, area = 1.8, velocidade = 20;
                int id = 1;

                System.out.println("obterRotaMenorCustoEnergeticoTeste2");

                Estafeta estafeta = new Estafeta(123, "123", "email", "nome", 20, 1, 1, 234);
                Endereco end = new Endereco("r1", "n1", "42342", "234", "wetr", 234, 234, 234);
                estafeta.setFarmacia(new Farmacia("nome", end));
                GerirEntregasController spy = Mockito.spy(controller);

                Map<Integer, Encomenda> l = new HashMap<>();
                l.put(id, new Encomenda(id, 1, Encomenda.EstadoEncomenda.ENVIO_PRONTO));
                l.put(2, new Encomenda(2, 2, Encomenda.EstadoEncomenda.ENVIO_PRONTO));

                Mockito.when(encMock.getMapEncomendasPorEntregar(1)).thenReturn(l);
                Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(new Graph<>(true));
                Mockito.when(spy.getEstafetaAtual()).thenReturn(estafeta);
            }

            @Nested
            @DisplayName("Obter Encomendas")
            class ObterEncomendas {

                private Map<Integer, Encomenda> simularListaEncomendasPorEntregar() {
                    Map<Integer, Encomenda> map = new HashMap<>();
                    Encomenda e1, e2, e3;
                    Produto p1, p2, p3;

                    p1 = new Produto(1, "vacina", 3.5, 4.2, 0.5, "covid");
                    p2 = new Produto(2, "aspirina", 7.5, 7.5 * 1.23, 0.4, "aspirina");
                    p3 = new Produto(3, "Xarope", 14, 14 * 1.23, 1.2, "Xarope");

                    e1 = new Encomenda(1, 1);
                    e1.addProduto(p1, 3);
                    e1.addProduto(p2, 4);

                    e2 = new Encomenda(2, 2);
                    e2.addProduto(p2, 1);

                    e3 = new Encomenda(3, 3);
                    e3.addProduto(p1, 1);
                    e3.addProduto(p2, 2);
                    e3.addProduto(p3, 3);

                    map.put(1, e1);
                    map.put(2, e2);
                    map.put(3, e3);

                    return map;
                }
            }

            @Nested
            @DisplayName("Encomenda com Drones")
            class EntregaDrones {

            }
        }

        @Nested
        class finalTeste {

            @Test
            public void addEncomendaTeste() {
                boolean result;
                double pesoMaximo = 5d;
                int idEncomenda1 = 1, idEncomenda2 = 2;
                Entrega entrega = new Entrega(1, LocalDate.now(), new Estafeta(1, "Estafeta", 1d, 1, 1, 1d));
                Map<Integer, Encomenda> mEncomendas = new HashMap<>();
                Encomenda e1 = new Encomenda(idEncomenda1, 1, Encomenda.EstadoEncomenda.ENVIO_PRONTO);
                e1.addProduto(new Produto("nome", 1d), 2);
                mEncomendas.put(idEncomenda1, e1);

                result = controller.addEncomenda(entrega, idEncomenda1, mEncomendas, pesoMaximo);
                assertTrue(result);

                //Voltar a adicionar a mesma encomenda. Dá false porque foi eliminada do map das encomendas
                result = controller.addEncomenda(entrega, idEncomenda1, mEncomendas, pesoMaximo);
                assertFalse(result);

                Encomenda e2 = new Encomenda(idEncomenda2, 1, Encomenda.EstadoEncomenda.ENVIO_PRONTO);
                e2.addProduto(new Produto("nome", 5d), 1);
                mEncomendas.put(idEncomenda2, e2);

                result = controller.addEncomenda(entrega, idEncomenda2, mEncomendas, pesoMaximo);
                assertFalse(result);
            }

            @Test
            public void getEnderecosFromEncomendasTeste() {
                List<Encomenda> lEncomendas = new ArrayList<>();
                List<Endereco> result;
                result = controller.getEnderecosFromEncomendas(lEncomendas);
                assertTrue(result.isEmpty());

                Cliente c = getCliente();
                Encomenda encomenda = new Encomenda(1, new HashMap<>(), Encomenda.EstadoEncomenda.PAGAMENTO_PENDENTE, null, 1d, c);
                lEncomendas.add(encomenda);
                result = controller.getEnderecosFromEncomendas(lEncomendas);
                assertEquals(1, result.size());
            }

            @Test
            public void isPesoAbaixoDoMaximoTesteLimite() {
                double pesoEncomenda, pesoEntrega, pesoMaximo;
                pesoMaximo = 3d;
                pesoEncomenda = 2;
                pesoEntrega = 1;
                boolean result = controller.isPesoEntregaAbaixoDoMaximo(pesoEncomenda, pesoEntrega, pesoMaximo);
                assertTrue(result);
            }

            @Test
            public void getFarmaciaUser() {
                int idFarmacia = 1;
                Farmacia result;
                Estafeta e = getEstafeta(idFarmacia);
                GestorFarmacia g = getGestor(idFarmacia);

                controller.setUser(e);
                result = controller.getFarmaciaUser();
                assertEquals(idFarmacia, result.getId());

                controller.setUser(g);
                result = controller.getFarmaciaUser();
                assertEquals(idFarmacia, result.getId());

                Cliente c = getCliente();
                controller.setUser(c);
                result = controller.getFarmaciaUser();
                assertNull(result);
            }

            @Test
            public void calcularGastosEnergeticosTeste() {
                MeioTransporte mt = getScooter();
                Graph<Endereco, Rua> g = getGrafoRuas();

                double massa = 100;
                double velocidade = 40;

                controller.calcularGastoEnergetico (g, mt, massa, velocidade);
                Iterable<Edge<Endereco, Rua>> result = g.edges();
                for (Edge<Endereco, Rua> rua : result) {
                    if (rua.getWeight() == 0) {
                        fail();
                    }
                }
                assertTrue(true);
            }

        private Cliente getCliente() {
            return new Cliente("c", "c", "c", "c", new Endereco(), new CartaoCredito("#cc", 1, LocalDate.now()));
        }

        private GestorFarmacia getGestor(int idFarmacia) {
            return new GestorFarmacia("1", "1", "1", idFarmacia);
        }

        private Estafeta getEstafeta(int idFarmacia) {
            return new Estafeta(1, "1", "1", "1", 1d, 1, idFarmacia, 2d);
        }

        private Scooter getScooter() {
            return new Scooter(1, 50, 10d, 10d, 20d, 100d);
        }

        private Graph<Endereco, Rua> getGrafoRuas() {
            Graph<Endereco, Rua> g = new Graph<>(true);
            Endereco e1 = new Endereco("r1", "n1", "42342", "234", "wetr", 234, 234, 1);
            Endereco e2 = new Endereco("r2", "n2", "42342", "234", "wetr", 234, 234, 1);
            Endereco e3 = new Endereco("r3", "n3", "42342", "234", "wetr", 234, 234, 1);
            Endereco e4 = new Endereco("r4", "n4", "42342", "234", "wetr", 234, 234, 1);
            Endereco e5 = new Endereco("r5", "n5", "42342", "234", "wetr", 234, 234, 1);
            Endereco e6 = new Endereco("r6", "n6", "42342", "234", "wetr", 234, 234, 1);

            Rua r1 = new Rua("rua1", 3d, 0, 1, 0,0);
            Rua r2 = new Rua("rua2", 3d, 0, 1, 0,0);
            Rua r3 = new Rua("rua3", 20d, 0, 1, 0,0);
            Rua r4 = new Rua("rua4", 1d, 0, 1, 0,0);
            Rua r5 = new Rua("rua5", 5d, 0, 1, 0,0);
            Rua r6 = new Rua("rua6", 1d, 0, 1, 0,0);
            Rua r7 = new Rua("rua7", 0.5d, 0, 1, 0,0);
            Rua r8 = new Rua("rua8", 2d, 0, 1, 0,0);
            Rua r9 = new Rua("rua9", 3d, 0, 1, 0,0);

            g.insertEdge(e1, e2, r1, 3);
            g.insertEdge(e2, e1, r2, 3);
            g.insertEdge(e1, e3, r3, 20);
            g.insertEdge(e1, e4, r4, 1);
            g.insertEdge(e2, e3, r5, 5);
            g.insertEdge(e3, e5, r6, 1);
            g.insertEdge(e3, e6, r7, 0.5);
            g.insertEdge(e5, e4, r8, 2);
            g.insertEdge(e4, e1, r9, 3);
            return g;
        }
    }

    @Test
    void getFarmaciaMaisPerto() {
         Graph<Endereco, Rua> g = new Graph<>(true);
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
        Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
        Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
        Endereco e5 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);

        Farmacia farmacia1 = new Farmacia("e1", e1);
        Farmacia farmacia2 = new Farmacia("e2", e2);
        Farmacia farmacia3 = new Farmacia("e3", e3);
        Farmacia farmacia4 = new Farmacia("e4", e4);
        List<Farmacia> listaFarmacias = new ArrayList<>();
        listaFarmacias.add(farmacia1);
        listaFarmacias.add(farmacia2);
        listaFarmacias.add(farmacia3);
        listaFarmacias.add(farmacia4);
        Mockito.when(farmaciaMock.getListaFarmacias()).thenReturn(listaFarmacias);

        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e4)).thenReturn(farmacia4);


        Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
        Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
        Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);
        Rua r4 = new Rua("rua4", 120d, 0, 1, 0,0);



        g.insertEdge(e5, e1, r1, 10);
        g.insertEdge(e5, e2, r2, 9);
        g.insertEdge(e5, e3, r3, 7);
        g.insertEdge(e3, e5, r3, 1);
        g.insertEdge(e5, e4, r4, 3);


        Pair <Farmacia,Double> expected = new Pair<>(farmacia4,3.0);
        Pair<Farmacia,Double> result = controller.farmaciaMaisPerto(g,e5);

       // assertEquals(expected,result);
    }

    @Test
    void getFarmaciaMaisPertoDeUser1() {
        Graph<Endereco, Rua> g = new Graph<>(true);
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
        Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
        Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
        Endereco e5 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);

        Farmacia farmacia1 = new Farmacia("e1", e1);
        Farmacia farmacia2 = new Farmacia("e2", e2);
        Farmacia farmacia3 = new Farmacia("e3", e3);
        Farmacia farmacia4 = new Farmacia("e4", e4);
        List<Farmacia> listaFarmacias = new ArrayList<>();
        listaFarmacias.add(farmacia1);
        listaFarmacias.add(farmacia2);
        listaFarmacias.add(farmacia3);
        listaFarmacias.add(farmacia4);
        Mockito.when(farmaciaMock.getListaFarmacias()).thenReturn(listaFarmacias);

        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e3)).thenReturn(farmacia3);


        Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
        Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
        Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);
        Rua r4 = new Rua("rua4", 120d, 0, 1, 0,0);



        g.insertEdge(e5, e1, r1, 10);
        g.insertEdge(e5, e2, r2, 9);
        g.insertEdge(e5, e3, r3, 7);
        g.insertEdge(e3, e5, r3, 1);
        g.insertEdge(e5, e4, r4, 9);
        Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(g);

        Farmacia expected = farmacia3;
        Farmacia result = controller.getFarmaciaMaisPertoDeUser(e5);
        assertEquals(expected,result);
    }



    @Test
    void realizarTransferenciaProduto1() {
        Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
        Graph<Endereco, Caminho> grafoDrone = new Graph<>(true);


        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
        Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
        Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
        Endereco e5 = new Endereco("Endereco5", "n3", "42342", "234", "wetr", 234.5, 234, 1);

        Endereco e6 = new Endereco("Endereco6", "n3", "42342", "234", "wetr", 234.7, 234, 1);
        Endereco e7 = new Endereco("Endereco7", "n3", "42342", "234", "wetr", 234.8, 234, 1);


        Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
        Farmacia farmacia2 = new Farmacia("farmacia2", e2);
        Farmacia farmacia3 = new Farmacia("farmacia3", e3);
        Farmacia farmacia4 = new Farmacia("farmacia4", e4);
        Farmacia farmacia5 = new Farmacia("farmacia5", e5);
        Farmacia farmacia6 = new Farmacia("farmacia6", e6);
        Farmacia farmacia7 = new Farmacia("farmacia7", e7);

        List<Farmacia> listaFarmacias = new ArrayList<>();

        listaFarmacias.add(farmacia2);
        listaFarmacias.add(farmacia3);
        listaFarmacias.add(farmacia4);
        listaFarmacias.add(farmacia5);
        listaFarmacias.add(farmacia7);
        listaFarmacias.add(farmacia6);


        Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        Mockito.when(produtosFarmaciaMock.getFarmaciasComStock(produto1,2)).thenReturn(listaFarmacias);

        Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
        Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
        Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);
        Rua r4 = new Rua("rua4", 120d, 0, 1, 0,0);


        grafoScooter.insertEdge(e1, e2, r1, 7);
        grafoScooter.insertEdge(e2, e1, r2, 1);
        grafoScooter.insertEdge(e1, e3, r4, 1);
        grafoScooter.insertEdge(e3, e1, r2, 1);
        grafoScooter.insertEdge(e1, e4, r3, 7);
        grafoScooter.insertEdge(e4, e1, r2, 7);
        grafoScooter.insertEdge(e1, e5, r4, 10);
        grafoScooter.insertEdge(e5, e1, r2, 11);

        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e4)).thenReturn(farmacia4);
        Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(grafoScooter);

        Caminho c1 = new Caminho("Caminho1",10d,10.0,0);
        Caminho c2 = new Caminho("Caminho1",10d,10.0,0);


        grafoDrone.insertEdge(e6,e1,c2,5);
        grafoDrone.insertEdge(e1,e6,c2,15);
        grafoDrone.insertEdge(e7,e1,c1,1);
        grafoDrone.insertEdge(e1,e7,c2,5);


        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e7)).thenReturn(farmacia7);
        Mockito.when(enderecoMock.criarGrafoEspacoAereo()).thenReturn(grafoDrone);

        // farmacia 2

        Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
        meioTransporte1.setId(1);
        Drone meioTransporte2 = new Drone( 50200, 100,0.5, 1.0, 1.0, 30.0,  10d,1d);
        meioTransporte2.setId(2);
        Drone meioTransporte3 = new Drone( 10021, 100,0.5, 1.0, 0.5, 30.0,  10d,1d);
        meioTransporte3.setId(3);
        Drone meioTransporte4 = new Drone( 15550, 100,0.5, 1.0, 0.5, 50.0,  10d,1d);
        meioTransporte4.setId(4);
        List<Drone> listaDronesFarmacia = new ArrayList<>();
        listaDronesFarmacia.add(meioTransporte1);
        listaDronesFarmacia.add(meioTransporte2);
        listaDronesFarmacia.add(meioTransporte3);
        listaDronesFarmacia.add(meioTransporte4);
        Mockito.when(droneMock.getListaDronesFarmacia(farmaciaInicial)).thenReturn(listaDronesFarmacia);

        Scooter meioTransporte5 = new Scooter(1, 10000, 0.5, 1.0, 1.0, 40.0);
        Scooter meioTransporte6 = new Scooter(2, 50200, 0.5, 1.0, 1.0, 30.0);
        Scooter meioTransporte7 = new Scooter(3, 10021, 0.5, 1.0, 0.5, 30.0);
        Scooter meioTransporte8 = new Scooter(4, 15550, 0.5, 1.0, 0.5, 50.0);
        List<Scooter> listaScootersFarmacia = new ArrayList<>();
        listaScootersFarmacia.add(meioTransporte5);
        listaScootersFarmacia.add(meioTransporte6);
        listaScootersFarmacia.add(meioTransporte7);
        listaScootersFarmacia.add(meioTransporte8);
        Mockito.when(scthandler.getListaScootersFarmacia(farmaciaInicial)).thenReturn(listaScootersFarmacia);
        Mockito.when(produtosFarmaciaMock.realizarTransferenciaProduto(farmaciaInicial,farmacia7,produto1,2,1)).thenReturn(true);
        boolean expected = true;
        boolean result = controller.realizarTransferenciaProduto(farmaciaInicial,produto1,2);
        //assertEquals(expected,result);
    }

    @Test
    void realizarTransferenciaProduto2() {
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
        List<Farmacia> listaFarmacias = new ArrayList<>();
        Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        Mockito.when(produtosFarmaciaMock.getFarmaciasComStock(produto1,2)).thenReturn(listaFarmacias);
        boolean expected = false;
        boolean result = controller.realizarTransferenciaProduto(farmaciaInicial,produto1,2);
        assertEquals(expected,result);
    }

    @Test
    void realizarTransferenciaProduto3() {
        Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
        Graph<Endereco, Caminho> grafoDrone = new Graph<>(true);

        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
        Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
        Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
        Endereco e5 = new Endereco("Endereco5", "n3", "42342", "234", "wetr", 234.5, 234, 1);

        Endereco e6 = new Endereco("Endereco6", "n3", "42342", "234", "wetr", 234.7, 234, 1);
        Endereco e7 = new Endereco("Endereco7", "n3", "42342", "234", "wetr", 234.8, 234, 1);


        Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
        Farmacia farmacia2 = new Farmacia("farmacia2", e2);
        Farmacia farmacia3 = new Farmacia("farmacia3", e3);
        Farmacia farmacia4 = new Farmacia("farmacia4", e4);
        Farmacia farmacia5 = new Farmacia("farmacia5", e5);
        Farmacia farmacia6 = new Farmacia("farmacia6", e6);
        Farmacia farmacia7 = new Farmacia("farmacia7", e7);

        List<Farmacia> listaFarmacias = new ArrayList<>();

        listaFarmacias.add(farmacia2);
        listaFarmacias.add(farmacia3);
        listaFarmacias.add(farmacia4);
        listaFarmacias.add(farmacia5);
        listaFarmacias.add(farmacia7);
        listaFarmacias.add(farmacia6);


        Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        Mockito.when(produtosFarmaciaMock.getFarmaciasComStock(produto1,2)).thenReturn(listaFarmacias);

        Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
        Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
        Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);
        Rua r4 = new Rua("rua4", 120d, 0, 1, 0,0);


        grafoScooter.insertEdge(e1, e2, r1, 7);
        grafoScooter.insertEdge(e2, e1, r2, 1);
        grafoScooter.insertEdge(e1, e3, r4, 1);
        grafoScooter.insertEdge(e3, e1, r2, 1);
        grafoScooter.insertEdge(e1, e4, r3, 7);
        grafoScooter.insertEdge(e4, e1, r2, 7);
        grafoScooter.insertEdge(e1, e5, r4, 10);
        grafoScooter.insertEdge(e5, e1, r2, 11);

        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e4)).thenReturn(farmacia4);
        Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(grafoScooter);

        Caminho c1 = new Caminho("Caminho1",50d,10.0,0);
        Caminho c2 = new Caminho("Caminho1",50d,10.0,0);


        grafoDrone.insertEdge(e6,e1,c2,5);
        grafoDrone.insertEdge(e1,e6,c2,15);
        grafoDrone.insertEdge(e7,e1,c1,1);
        grafoDrone.insertEdge(e1,e7,c2,5);


        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e7)).thenReturn(farmacia7);
        Mockito.when(enderecoMock.criarGrafoEspacoAereo()).thenReturn(grafoDrone);

        // farmacia 2

        Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
        meioTransporte1.setId(1);
        Drone meioTransporte2 = new Drone( 50200, 100,0.5, 1.0, 1.0, 30.0,  10d,1d);
        meioTransporte2.setId(2);
        Drone meioTransporte3 = new Drone( 10021, 100,0.5, 1.0, 0.5, 30.0,  10d,1d);
        meioTransporte3.setId(3);
        Drone meioTransporte4 = new Drone( 15550, 100,0.5, 1.0, 0.5, 50.0,  10d,1d);
        meioTransporte4.setId(4);
        List<Drone> listaDronesFarmacia = new ArrayList<>();
        listaDronesFarmacia.add(meioTransporte1);
        listaDronesFarmacia.add(meioTransporte2);
        listaDronesFarmacia.add(meioTransporte3);
        listaDronesFarmacia.add(meioTransporte4);
        Mockito.when(droneMock.getListaDronesFarmacia(farmaciaInicial)).thenReturn(listaDronesFarmacia);

        Scooter meioTransporte5 = new Scooter(1, 10000, 0.5, 1.0, 1.0, 40.0);
        Scooter meioTransporte6 = new Scooter(2, 50200, 0.5, 1.0, 1.0, 30.0);
        Scooter meioTransporte7 = new Scooter(3, 10021, 0.5, 1.0, 0.5, 30.0);
        Scooter meioTransporte8 = new Scooter(4, 15550, 0.5, 1.0, 0.5, 50.0);
        List<Scooter> listaScootersFarmacia = new ArrayList<>();
        listaScootersFarmacia.add(meioTransporte5);
        listaScootersFarmacia.add(meioTransporte6);
        listaScootersFarmacia.add(meioTransporte7);
        listaScootersFarmacia.add(meioTransporte8);
        Mockito.when(scthandler.getListaScootersFarmacia(farmaciaInicial)).thenReturn(listaScootersFarmacia);
        Mockito.when(produtosFarmaciaMock.realizarTransferenciaProduto(farmaciaInicial,farmacia4,produto1,2,1)).thenReturn(true);
        boolean expected =false;
        boolean result = controller.realizarTransferenciaProduto(farmaciaInicial,produto1,2);
        assertEquals(expected,result);
    }


        @Test
        void realizarTransferenciaProduto4() {
            Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
            Graph<Endereco, Caminho> grafoDrone = new Graph<>(true);


            Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
            Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
            Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
            Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
            Endereco e5 = new Endereco("Endereco5", "n3", "42342", "234", "wetr", 234.5, 234, 1);

            Endereco e6 = new Endereco("Endereco6", "n3", "42342", "234", "wetr", 234.7, 234, 1);
            Endereco e7 = new Endereco("Endereco7", "n3", "42342", "234", "wetr", 234.8, 234, 1);


            Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
            Farmacia farmacia2 = new Farmacia("farmacia2", e2);
            Farmacia farmacia3 = new Farmacia("farmacia3", e3);
            Farmacia farmacia4 = new Farmacia("farmacia4", e4);
            Farmacia farmacia5 = new Farmacia("farmacia5", e5);
            Farmacia farmacia6 = new Farmacia("farmacia6", e6);
            Farmacia farmacia7 = new Farmacia("farmacia7", e7);

            List<Farmacia> listaFarmacias = new ArrayList<>();

            listaFarmacias.add(farmacia2);
            listaFarmacias.add(farmacia3);
            listaFarmacias.add(farmacia4);
            listaFarmacias.add(farmacia5);
            listaFarmacias.add(farmacia7);
            listaFarmacias.add(farmacia6);


            Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
            Mockito.when(produtosFarmaciaMock.getFarmaciasComStock(produto1,2)).thenReturn(listaFarmacias);

            Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
            Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
            Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);
            Rua r4 = new Rua("rua4", 120d, 0, 1, 0,0);


            grafoScooter.insertEdge(e1, e2, r1, 10);
            grafoScooter.insertEdge(e1, e3, r2, 8);
            grafoScooter.insertEdge(e1, e4, r3, 2);
            Mockito.when(farmaciaMock.getFarmaciaByEndereco(e4)).thenReturn(farmacia4);
            grafoScooter.insertEdge(e1, e5, r4, 10);

            Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(grafoScooter);

            Caminho c1 = new Caminho("Caminho1",10d,10.0,0);
            Caminho c2 = new Caminho("Caminho1",10d,10.0,0);

            grafoDrone.insertEdge(e1,e6,c1,8);
            grafoDrone.insertEdge(e1,e7,c1,5);
            Mockito.when(farmaciaMock.getFarmaciaByEndereco(e7)).thenReturn(farmacia7);
            Mockito.when(enderecoMock.criarGrafoEspacoAereo()).thenReturn(grafoDrone);

            // farmacia 4

            Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
            meioTransporte1.setId(1);
            Drone meioTransporte2 = new Drone( 50200, 100,0.5, 1.0, 1.0, 30.0,  10d,1d);
            meioTransporte2.setId(2);
            Drone meioTransporte3 = new Drone( 10021, 100,0.5, 1.0, 0.5, 30.0,  10d,1d);
            meioTransporte3.setId(3);
            Drone meioTransporte4 = new Drone( 15550, 100,0.5, 1.0, 0.5, 50.0,  10d,1d);
            meioTransporte4.setId(4);
            List<Drone> listaDronesFarmacia = new ArrayList<>();
            listaDronesFarmacia.add(meioTransporte1);
            listaDronesFarmacia.add(meioTransporte2);
            listaDronesFarmacia.add(meioTransporte3);
            listaDronesFarmacia.add(meioTransporte4);
            Mockito.when(droneMock.getListaDronesFarmacia(farmaciaInicial)).thenReturn(listaDronesFarmacia);

            Scooter meioTransporte5 = new Scooter(1, 10000, 0.5, 1.0, 1.0, 40.0);
            Scooter meioTransporte6 = new Scooter(2, 50200, 0.5, 1.0, 1.0, 30.0);
            Scooter meioTransporte7 = new Scooter(3, 10021, 0.5, 1.0, 0.5, 30.0);
            Scooter meioTransporte8 = new Scooter(4, 15550, 0.5, 1.0, 0.5, 50.0);
            List<Scooter> listaScootersFarmacia = new ArrayList<>();
            listaScootersFarmacia.add(meioTransporte5);
            listaScootersFarmacia.add(meioTransporte6);
            listaScootersFarmacia.add(meioTransporte7);
            listaScootersFarmacia.add(meioTransporte8);
            Mockito.when(scthandler.getListaScootersFarmacia(farmaciaInicial)).thenReturn(listaScootersFarmacia);

            // 83.49560283 // id 3

            Mockito.when(produtosFarmaciaMock.realizarTransferenciaProduto(farmaciaInicial,farmacia7,produto1,2,1)).thenReturn(true);
            boolean expected = true;
            boolean result = controller.realizarTransferenciaProduto(farmaciaInicial,produto1,2);
            assertEquals(expected,result);
        }

    @Test
    void realizarTransferenciaProduto5() {
        Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
        Graph<Endereco, Caminho> grafoDrone = new Graph<>(true);


        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
        Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
        Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
        Endereco e5 = new Endereco("Endereco5", "n3", "42342", "234", "wetr", 234.5, 234, 1);

        Endereco e6 = new Endereco("Endereco6", "n3", "42342", "234", "wetr", 234.7, 234, 1);
        Endereco e7 = new Endereco("Endereco7", "n3", "42342", "234", "wetr", 234.8, 234, 1);


        Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
        Farmacia farmacia2 = new Farmacia("farmacia2", e2);
        Farmacia farmacia3 = new Farmacia("farmacia3", e3);
        Farmacia farmacia4 = new Farmacia("farmacia4", e4);
        Farmacia farmacia5 = new Farmacia("farmacia5", e5);
        Farmacia farmacia6 = new Farmacia("farmacia6", e6);
        Farmacia farmacia7 = new Farmacia("farmacia7", e7);

        List<Farmacia> listaFarmacias = new ArrayList<>();

        listaFarmacias.add(farmacia2);
        listaFarmacias.add(farmacia3);
        listaFarmacias.add(farmacia4);
        listaFarmacias.add(farmacia5);
        listaFarmacias.add(farmacia7);
        listaFarmacias.add(farmacia6);


        Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        Mockito.when(produtosFarmaciaMock.getFarmaciasComStock(produto1,2)).thenReturn(listaFarmacias);

        Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
        Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
        Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);
        Rua r4 = new Rua("rua4", 120d, 0, 1, 0,0);


        grafoScooter.insertEdge(e1, e2, r1, 10);
        grafoScooter.insertEdge(e1, e3, r2, 8);
        grafoScooter.insertEdge(e1, e4, r3, 2);
        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e4)).thenReturn(farmacia4);
        grafoScooter.insertEdge(e1, e5, r4, 10);

        Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(grafoScooter);

        Caminho c1 = new Caminho("Caminho1",10d,10.0,0);
        Caminho c2 = new Caminho("Caminho1",10d,10.0,0);

        grafoDrone.insertEdge(e1,e6,c1,8);
        grafoDrone.insertEdge(e1,e7,c2,5);
        Mockito.when(farmaciaMock.getFarmaciaByEndereco(e7)).thenReturn(farmacia7);
        Mockito.when(enderecoMock.criarGrafoEspacoAereo()).thenReturn(grafoDrone);

        // farmacia 4


        Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
        meioTransporte1.setId(1);
        Drone meioTransporte2 = new Drone( 50200, 100,0.5, 1.0, 1.0, 30.0,  10d,1d);
        meioTransporte2.setId(2);
        Drone meioTransporte3 = new Drone( 10021, 100,0.5, 1.0, 0.5, 30.0,  10d,1d);
        meioTransporte3.setId(3);
        Drone meioTransporte4 = new Drone( 15550, 100,0.5, 1.0, 0.5, 50.0,  10d,1d);
        meioTransporte4.setId(4);
        List<Drone> listaDronesFarmacia = new ArrayList<>();
        listaDronesFarmacia.add(meioTransporte1);
        listaDronesFarmacia.add(meioTransporte2);
        listaDronesFarmacia.add(meioTransporte3);
        listaDronesFarmacia.add(meioTransporte4);
        Mockito.when(droneMock.getListaDronesFarmacia(farmaciaInicial)).thenReturn(listaDronesFarmacia);

        Scooter meioTransporte5 = new Scooter(1, 10000, 0.5, 1.0, 1.0, 40.0);
        Scooter meioTransporte6 = new Scooter(2, 50200, 0.5, 1.0, 1.0, 30.0);
        Scooter meioTransporte7 = new Scooter(3, 10021, 0.5, 1.0, 0.5, 30.0);
        Scooter meioTransporte8 = new Scooter(4, 15550, 0.5, 1.0, 0.5, 50.0);
        List<Scooter> listaScootersFarmacia = new ArrayList<>();
        listaScootersFarmacia.add(meioTransporte5);
        listaScootersFarmacia.add(meioTransporte6);
        listaScootersFarmacia.add(meioTransporte7);
        listaScootersFarmacia.add(meioTransporte8);
        Mockito.when(scthandler.getListaScootersFarmacia(farmaciaInicial)).thenReturn(listaScootersFarmacia);

        // 83.49560283 // id 3

        Mockito.when(produtosFarmaciaMock.realizarTransferenciaProduto(farmaciaInicial,farmacia4,produto1,2,1)).thenReturn(true);
        boolean expected = true;
        boolean result = controller.realizarTransferenciaProduto(farmaciaInicial,produto1,2);
       //assertEquals(expected,result);

    }

        @Test
        void realizarTransferenciaProduto6() {
            Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
            Graph<Endereco, Caminho> grafoDrone = new Graph<>(true);


            Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
            Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
            Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
            Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
            Endereco e5 = new Endereco("Endereco5", "n3", "42342", "234", "wetr", 234.5, 234, 1);

            Endereco e6 = new Endereco("Endereco6", "n3", "42342", "234", "wetr", 234.7, 234, 1);
            Endereco e7 = new Endereco("Endereco7", "n3", "42342", "234", "wetr", 234.8, 234, 1);


            Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
            Farmacia farmacia2 = new Farmacia("farmacia2", e2);
            Farmacia farmacia3 = new Farmacia("farmacia3", e3);
            Farmacia farmacia4 = new Farmacia("farmacia4", e4);
            Farmacia farmacia5 = new Farmacia("farmacia5", e5);
            Farmacia farmacia6 = new Farmacia("farmacia6", e6);
            Farmacia farmacia7 = new Farmacia("farmacia7", e7);

            List<Farmacia> listaFarmacias = new ArrayList<>();

            listaFarmacias.add(farmacia2);
            listaFarmacias.add(farmacia3);
            listaFarmacias.add(farmacia4);
            listaFarmacias.add(farmacia5);
            listaFarmacias.add(farmacia7);
            listaFarmacias.add(farmacia6);


            Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
            Mockito.when(produtosFarmaciaMock.getFarmaciasComStock(produto1,2)).thenReturn(listaFarmacias);

            Rua r1 = new Rua("rua1", 30232131212300d, 0, 1, 0,0);
            Rua r2 = new Rua("rua2", 303122313000d, 0, 1, 0,0);
            Rua r3 = new Rua("rua3", 1031232131000d, 0, 1, 0,0);
            Rua r4 = new Rua("rua4", 1203231310d, 0, 1, 0,0);


            grafoScooter.insertEdge(e1, e2, r1, 10);
            grafoScooter.insertEdge(e1, e3, r2, 8);
            grafoScooter.insertEdge(e1, e4, r3, 2);
            Mockito.when(farmaciaMock.getFarmaciaByEndereco(e4)).thenReturn(farmacia4);
            grafoScooter.insertEdge(e1, e5, r4, 10);

            Mockito.when(enderecoMock.criarGrafoEnderecos()).thenReturn(grafoScooter);

            Caminho c1 = new Caminho("Caminho1",10321312312312d,10.0,0);
            Caminho c2 = new Caminho("Caminho1",13123123213123210d,10.0,0);

            grafoDrone.insertEdge(e1,e6,c1,8);
            grafoDrone.insertEdge(e1,e7,c1,5);
            Mockito.when(farmaciaMock.getFarmaciaByEndereco(e7)).thenReturn(farmacia7);
            Mockito.when(enderecoMock.criarGrafoEspacoAereo()).thenReturn(grafoDrone);

            // farmacia 4


            Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
            meioTransporte1.setId(1);
            Drone meioTransporte2 = new Drone( 50200, 100,0.5, 1.0, 1.0, 30.0,  10d,1d);
            meioTransporte2.setId(2);
            Drone meioTransporte3 = new Drone( 10021, 100,0.5, 1.0, 0.5, 30.0,  10d,1d);
            meioTransporte3.setId(3);
            Drone meioTransporte4 = new Drone( 15550, 100,0.5, 1.0, 0.5, 50.0,  10d,1d);
            meioTransporte4.setId(4);
            List<Drone> listaDronesFarmacia = new ArrayList<>();
            listaDronesFarmacia.add(meioTransporte1);
            listaDronesFarmacia.add(meioTransporte2);
            listaDronesFarmacia.add(meioTransporte3);
            listaDronesFarmacia.add(meioTransporte4);
            Mockito.when(droneMock.getListaDronesFarmacia(farmaciaInicial)).thenReturn(listaDronesFarmacia);

            Scooter meioTransporte5 = new Scooter(1, 10000, 0.5, 1.0, 1.0, 40.0);
            Scooter meioTransporte6 = new Scooter(2, 50200, 0.5, 1.0, 1.0, 30.0);
            Scooter meioTransporte7 = new Scooter(3, 10021, 0.5, 1.0, 0.5, 30.0);
            Scooter meioTransporte8 = new Scooter(4, 15550, 0.5, 1.0, 0.5, 50.0);
            List<Scooter> listaScootersFarmacia = new ArrayList<>();
            listaScootersFarmacia.add(meioTransporte5);
            listaScootersFarmacia.add(meioTransporte6);
            listaScootersFarmacia.add(meioTransporte7);
            listaScootersFarmacia.add(meioTransporte8);
            Mockito.when(scthandler.getListaScootersFarmacia(farmaciaInicial)).thenReturn(listaScootersFarmacia);

            // 83.49560283 // id 3

            Mockito.when(produtosFarmaciaMock.realizarTransferenciaProduto(farmaciaInicial,farmacia7,produto1,2,1)).thenReturn(true);
            boolean expected = true;
            boolean result = controller.realizarTransferenciaProduto(farmaciaInicial,produto1,2);
           // assertEquals(expected,result);
        }

        @Test
        void transferencia7() {
            Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
            Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
            Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);
            Endereco e4 = new Endereco("Endereco4", "n3", "42342", "234", "wetr", 234.5, 234, 1);
            Endereco e5 = new Endereco("Endereco5", "n3", "42342", "234", "wetr", 234.5, 234, 1);

            Endereco e6 = new Endereco("Endereco6", "n3", "42342", "234", "wetr", 234.7, 234, 1);
            Endereco e7 = new Endereco("Endereco7", "n3", "42342", "234", "wetr", 234.8, 234, 1);


            Farmacia farmaciaInicial = new Farmacia("farmaciaInicial", e1);
            Farmacia farmacia2 = new Farmacia("farmacia2", e2);
            Farmacia farmacia3 = new Farmacia("farmacia3", e3);
            Farmacia farmacia4 = new Farmacia("farmacia4", e4);
            Farmacia farmacia5 = new Farmacia("farmacia5", e5);
            Farmacia farmacia6 = new Farmacia("farmacia6", e6);
            Farmacia farmacia7 = new Farmacia("farmacia7", e7);

            List<Farmacia> listaFarmacias = new ArrayList<>();

            listaFarmacias.add(farmacia2);
            listaFarmacias.add(farmacia3);
            listaFarmacias.add(farmacia4);
            listaFarmacias.add(farmacia5);
            listaFarmacias.add(farmacia7);
            listaFarmacias.add(farmacia6);


            // farmacia 4

            List<Drone> listaDrones = new ArrayList<>();
            List<Scooter> listaScooters = new ArrayList<>();

            Mockito.when(droneMock.getListaDronesFarmacia(farmaciaInicial)).thenReturn(listaDrones);
            Mockito.when(scthandler.getListaScootersFarmacia(farmaciaInicial)).thenReturn(listaScooters);

            // 83.49560283 // id 3
            Produto  produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
            boolean expected = false;
            boolean result = controller.realizarTransferenciaEntreDuasFarmaciasDefinidas(farmaciaInicial,listaFarmacias,produto1,2);
            assertEquals(expected,result);
        }

        @Test
    void shortestPathTeste() {
        Scooter mt=new Scooter ( 400,0.5d,1d,40d,80d );
        Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
        Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);


        Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
        Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
        Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);

        grafoScooter.insertEdge(e1,e2,r1,10);
        grafoScooter.insertEdge(e2,e3,r2,10);
        grafoScooter.insertEdge(e3,e1,r2,10);

        LinkedList <Endereco> desiredPath = new LinkedList<>();

        desiredPath.add(e1);
        desiredPath.add(e2);
        desiredPath.add(e3);

        List<LinkedList<Endereco>> permutRotas= Algoritmos.permutacoesComComecoIgual ( desiredPath, desiredPath.getFirst(),true );
        Tuple<List<Label>,Double,Double> shortestPath =controller.shortestPath(grafoScooter,permutRotas,mt);
//        List<Endereco> caminho = shortestPath.get1st();
//        for(Endereco e : caminho){
//            System.out.println(e.getRua());
//        }
    }

        @Test
        void compararRotas1() {
            Pair<Farmacia,Double>  scooter = null;
            Pair<Farmacia,Double>  drone = null;
            MeioTransporte scotterTra = null;
            MeioTransporte droneTra = null;
            assertNull(controller.compararRotas(scooter,drone,scotterTra,droneTra));
        }

        @Test
        void compararRotas2() {
            Pair<Farmacia,Double>  scooter = null;
            Pair<Farmacia,Double>  drone = new Pair<>(new Farmacia(1,"A"),1.0);
            MeioTransporte scotterTra = null;
            Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
            Pair<Farmacia,MeioTransporte> expected = new Pair<>(new Farmacia(1,"A"),meioTransporte1);
            Pair<Farmacia,MeioTransporte> result = controller.compararRotas(scooter,drone,scotterTra,meioTransporte1);
            assertEquals(expected,result);
         }

        @Test
        void compararRotas3() {
            Pair<Farmacia,Double>  scooter =  new Pair<>(new Farmacia(1,"A"),1.0);
            Pair<Farmacia,Double>  drone = null;
            Scooter scotterTra = new Scooter(1, 1, 0.5, 1.0, 1.0, 40.0);
            MeioTransporte droneTra = null;
            Pair<Farmacia,MeioTransporte> expected = new Pair<>(new Farmacia(1,"A"),scotterTra);
            Pair<Farmacia,MeioTransporte> result = controller.compararRotas(scooter,drone,scotterTra,droneTra);
            assertEquals(expected,result);
        }

        @Test
        void compararRotas4() {
            Pair<Farmacia,Double>  scooter =  new Pair<>(new Farmacia(1,"A"),1.0);
            Pair<Farmacia,Double>  drone = new Pair<>(new Farmacia(2,"B"),2.0);
            Scooter scotterTra = new Scooter(1, 1, 0.5, 1.0, 1.0, 40.0);
            Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
            Pair<Farmacia,MeioTransporte> expected = new Pair<>(new Farmacia(1,"A"),scotterTra);
            Pair<Farmacia,MeioTransporte> result = controller.compararRotas(scooter,drone,scotterTra,meioTransporte1);
            assertEquals(expected,result);
        }

        @Test
        void compararRotas5() {
            Pair<Farmacia,Double>  scooter =  new Pair<>(new Farmacia(1,"A"),3.0);
            Pair<Farmacia,Double>  drone = new Pair<>(new Farmacia(2,"B"),2.0);
            Scooter scotterTra = new Scooter(1, 1, 0.5, 1.0, 1.0, 40.0);
            Drone meioTransporte1 = new Drone( 10000, 100,0.5, 1.0, 1.0, 40.0, 10d,1d);
            Pair<Farmacia,MeioTransporte> expected = new Pair<>(new Farmacia(2,"B"),meioTransporte1);
            Pair<Farmacia,MeioTransporte> result = controller.compararRotas(scooter,drone,scotterTra,meioTransporte1);
            assertEquals(expected,result);
        }

        @Test
        void rotaMaisEfecienteNull() {
            MeioTransporte mt=null;
            Graph<Endereco, Rua> grafoScooter = new Graph<>(true);
            Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
            Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
            Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);


            Rua r1 = new Rua("rua1", 30d, 0, 1, 0,0);
            Rua r2 = new Rua("rua2", 30d, 0, 1, 0,0);
            Rua r3 = new Rua("rua3", 10d, 0, 1, 0,0);

            grafoScooter.insertEdge(e1,e2,r1,10);
            grafoScooter.insertEdge(e2,e3,r2,10);
            grafoScooter.insertEdge(e3,e1,r2,10);

            LinkedList <Endereco> desiredPath = new LinkedList<>();

            desiredPath.add(e1);
            desiredPath.add(e2);
            desiredPath.add(e3);
            List<Farmacia> listaFar = new ArrayList<>();

            List<LinkedList<Endereco>> permutRotas= Algoritmos.permutacoesComComecoIgual ( desiredPath, desiredPath.getFirst(),true );
            Pair<Farmacia,Double> shortestPath =controller.obterRotaMaisEficiente(grafoScooter,e1,listaFar,mt,0.0);
            assertNull(shortestPath);
        }

        @Test
        void rotaMaisEfecienteNull2() {
            MeioTransporte mt=null;
            Graph<Endereco, Caminho> grafoDrone = new Graph<>(true);
            Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
            Endereco e2 = new Endereco("Endereco2", "n2", "42342", "234", "wetr", 234.2, 234, 1);
            Endereco e3 = new Endereco("Endereco3", "n3", "42342", "234", "wetr", 234.3, 234, 1);


            Caminho c1 = new Caminho("Caminho1",10321312312312d,10.0,0);

            grafoDrone.insertEdge(e1,e2,c1,10);
            grafoDrone.insertEdge(e2,e3,c1,10);
            grafoDrone.insertEdge(e3,e1,c1,10);

            LinkedList <Endereco> desiredPath = new LinkedList<>();

            desiredPath.add(e1);
            desiredPath.add(e2);
            desiredPath.add(e3);
            List<Farmacia> listaFar = new ArrayList<>();

            List<LinkedList<Endereco>> permutRotas= Algoritmos.permutacoesComComecoIgual ( desiredPath, desiredPath.getFirst(),true );
            Pair<Farmacia,Double> shortestPath =controller.obterRotaMaisEficiente(grafoDrone,e1,listaFar,mt,0.0);
            assertNull(shortestPath);
        }

    @Test
    void estimarTempoCarregamentoTeste(){
        double energiaAtual=0.3;
        double energiaDesejada=100;
        double expected=7159090;
        double result= controller.estimarTempoCarregamento (energiaAtual,energiaDesejada);
        assertEquals ( expected,result,1 );
    }

    @Test
        void gerirCreditosEntrega(){
            boolean result;
        Endereco e1 = new Endereco ( "Trindade", "n1", "42342", "234", "wetr", 	41.15227, -8.60929, 104, true );
        Endereco e2 = new Endereco ( "Cais da Ribeira", "n2", "42342", "234", "wetr", 41.14063, -8.61118, 25, false );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",e2,new CartaoCredito ( "cc",1,LocalDate.now () ) );

        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO);

        Produto p= new Produto ( 10.0,"produto" );
        Produto p2 = new Produto(5.0,"produto2");
        encomenda2.addProduto ( p,2 );
        encomenda2.addProduto ( p2,1 );

        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );
        LocalDate dataEntrega = LocalDate.now();
        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );
        Entrega entrega = new Entrega(lEncomendas, dataEntrega,estafeta);


        result = controller.gerarCreditosEntrega(entrega);

        assertTrue(result);
    }

    @Test
        void gerirCreditosEntregaNull(){
            Entrega entrega = null;
            boolean result = controller.gerarCreditosEntrega(entrega);
            assertFalse(result);
    }
}
    @Test
    void rotaMaisRapidaTeste(){
        Tuple<List<Label>,Double,Double> resultSet;
        Graph<Endereco, Rua> g = new Graph<> ( true );
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Rua r1 = new Rua ( "rua1", 3d, 0,0, 1, 0 );
        Rua r2 = new Rua ( "rua2", 3d, 0,0, 1, 0 );
        Rua r3 = new Rua ( "rua3", 20d, 0,0, 1, 0 );
        Rua r4 = new Rua ( "rua4", 1d, 0,0, 1, 0 );
        Rua r5 = new Rua ( "rua5", 5d, 0,0, 1, 0 );
        Rua r6 = new Rua ( "rua6", 1d, 0,0, 1, 0 );
        Rua r7 = new Rua ( "rua7", 0.5d, 0,0, 1, 0 );
        Rua r8 = new Rua ( "rua8", 2d, 0,0, 1, 0 );
        Rua r9 = new Rua ( "rua9", 1.5d, 0,0, 1, 0 );

        g.insertEdge ( e1, e2, r1, 3);
        g.insertEdge ( e2, e1, r2, 3);
        g.insertEdge ( e1, e3, r3, 20);
        g.insertEdge ( e1, e4, r4, 1);
        g.insertEdge ( e2, e3, r5, 5);
        g.insertEdge ( e3, e5, r6, 2);
        g.insertEdge ( e3, e6, r7, 0.5);
        g.insertEdge ( e5, e4, r8, 2);
        g.insertEdge ( e4, e1, r9, 3);

        //g.insertEdge ( e4, e2, r9, 1);

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( e1 );
        //le.add ( e2 );
        le.add ( e5 );
        le.add ( e4 );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",e2,new CartaoCredito ( "cc",1,LocalDate.now () ) );
        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO );
        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );
        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );

        int maxEnergia=200;
        double energiaAtual=200;
        Scooter mt=new Scooter ( maxEnergia,0.5d,1d,40d,80d );

        Entrega entrega=new Entrega ( estafeta,mt );
        entrega.addEncomenda (lEncomendas);

        Endereco inicio=e1;

        resultSet=controller.rotaMaisEficiente ( g,mt,le,inicio,true );
        assertEquals (5, resultSet.get1st ().size ());
        assertEquals (15, resultSet.get2nd ()); //energia total gasta
        assertEquals (12.5, resultSet.get3rd ()); //distancia percorrida

        //mudar comprimento da rua3 para 1
        r3 = new Rua ( "rua3", 1d, 0,0, 1, 0 );
        g.removeEdge(e1, e3);
        g.insertEdge ( e1, e3, r3, 20);

        resultSet=controller.rotaMaisEficiente ( g,mt,le,inicio,true );

        System.out.println ("===");
        for (Label label : resultSet.get1st ()) {
            System.out.println (label.getEnderecoInicio ().getNumPorta ()+"->"+label.getEnderecoFim().getNumPorta ());
        }

        assertEquals (4, resultSet.get1st ().size ());
        assertEquals (27, resultSet.get2nd ()); //energia total gasta
        assertEquals (5.5, resultSet.get3rd ()); //distancia percorrida

        resultSet=controller.rotaMaisEficiente ( g,mt,le,inicio,false );
        System.out.println ("===");
        for (Label label : resultSet.get1st ()) {
            System.out.println (label.getEnderecoInicio ().getNumPorta ()+"->"+label.getEnderecoFim().getNumPorta ());
        }

        assertEquals (5, resultSet.get1st ().size ());
        assertEquals (15, resultSet.get2nd ()); //energia total gasta
        assertEquals (12.5, resultSet.get3rd ()); //distancia percorrida
    }

    @Test
    void calcularScorePercursoTeste(){
        double expected, result;
        double tempo=3;
        double energia=2;
        expected=2.5;
        result=controller.calcularScorePercurso ( tempo,energia );
        assertEquals ( expected,result );
    }

    @Test
    void rotaAereaMaisRapidaTeste(){
        double result;

        Tuple<List<Label>,Double,Double> resultSet=Tuple.create ( new LinkedList<> (),0d,0d );
        Graph<Endereco, Caminho> g = new Graph<> ( true );
        List<Endereco> l=criarGrafoAereo ( g );

        //g.insertEdge ( e4, e2, r9, 1);

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( l.get ( 0 ) );
        //le.add ( e2 );
        le.add ( l.get ( 4 ));
        le.add ( l.get ( 3 ) );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",l.get ( 1 ),new CartaoCredito ( "cc",1,LocalDate.now () ) );
        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO );
        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );
        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );

        int maxEnergia=200;
        double energiaAtual=200;
        Drone mt=new Drone ( maxEnergia,0.5d,1d,40d,80d,10,10 );

        Entrega entrega=new Entrega ( estafeta,mt );
        entrega.addEncomenda (lEncomendas);

        Endereco inicio=l.get ( 0 );
        when(enderecoMock.criarGrafoEspacoAereo ()).thenReturn ( g );
        result=controller.rotaAereaMaisRapida ( inicio,le,entrega,mt,10,resultSet );

        assertEquals ( 1.25,result );
        assertEquals ( 0.035,resultSet.get2nd (),0.01 );
        assertEquals ( 12.5,resultSet.get3rd (), 0.1);
    }

    @Test
    void rotaAereaMaisEficienteTeste(){
        double result;

        Tuple<List<Label>,Double,Double> resultSet=Tuple.create ( new LinkedList<> (),0d,0d );
        Graph<Endereco, Caminho> g = new Graph<> ( true );
        List<Endereco> l=criarGrafoAereo ( g );

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( l.get ( 0 ) );
        le.add ( l.get ( 4 ));
        le.add ( l.get ( 3 ) );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",l.get ( 1 ),new CartaoCredito ( "cc",1,LocalDate.now () ) );
        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO );
        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );
        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );

        int maxEnergia=200;
        double energiaAtual=200;
        Drone mt=new Drone ( maxEnergia,0.5d,1d,40d,80d,10,10 );

        Entrega entrega=new Entrega ( estafeta,mt );
        entrega.addEncomenda (lEncomendas);

        Endereco inicio=l.get ( 0 );
        when(enderecoMock.criarGrafoEspacoAereo ()).thenReturn ( g );
        result=controller.rotaAereaMaisEficiente ( inicio,le,entrega,mt,10,resultSet );

        assertEquals ( 1.25,result );
        assertEquals ( 0.035,resultSet.get2nd (),0.01 );
        assertEquals ( 12.5,resultSet.get3rd (), 0.1);
    }

    @Test
    void rotaTerrestreMaisRapidaTeste(){
        double result;

        Tuple<List<Label>,Double,Double> resultSet=Tuple.create ( new LinkedList<> (),0d,0d );
        Graph<Endereco, Rua> g = new Graph<> ( true );
        List<Endereco> l=criarGrafoTerrestre ( g );

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( l.get ( 0 ) );
        le.add ( l.get ( 4 ));
        le.add ( l.get ( 3 ) );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",l.get ( 1 ),new CartaoCredito ( "cc",1,LocalDate.now () ) );
        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO );
        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );
        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );

        int maxEnergia=200;
        Scooter mt=new Scooter ( maxEnergia,50,1d,40d,80,56 );

        Entrega entrega=new Entrega ( estafeta,mt );
        entrega.addEncomenda (lEncomendas);

        Endereco inicio=l.get ( 0 );
        when(enderecoMock.criarGrafoEnderecos ()).thenReturn ( g );
        result=controller.rotaTerrestreMaisRapida ( inicio,le,entrega,mt,10,resultSet );

        assertEquals ( 1.25,result );
        assertEquals ( 0.009,resultSet.get2nd (),0.01 );
        assertEquals ( 12.5,resultSet.get3rd (), 0.1);
    }

    @Test
    void rotaTerrestreMaisEficienteTeste(){
        double result;

        Tuple<List<Label>,Double,Double> resultSet=Tuple.create ( new LinkedList<> (),0d,0d );
        Graph<Endereco, Rua> g = new Graph<> ( true );
        List<Endereco> l=criarGrafoTerrestre ( g );

        LinkedList<Endereco> le=new LinkedList<>();
        //le.add ( l.get ( 0 ) );
        le.add ( l.get ( 4 ));
        le.add ( l.get ( 3 ) );

        Cliente c2=new Cliente ( 1,"c2","nome","nome","nome",l.get ( 1 ),new CartaoCredito ( "cc",1,LocalDate.now () ) );
        Encomenda encomenda2=new Encomenda ( 2,2, Encomenda.EstadoEncomenda.ENVIO_PRONTO );
        Produto p= new Produto ( "produto",10 );
        encomenda2.addProduto ( p,2 );
        encomenda2.setCliente ( c2 );

        List<Encomenda> lEncomendas=new ArrayList<> (3);
        lEncomendas.add ( encomenda2 );

        Estafeta estafeta=new Estafeta ( 1,"estafeta",20,1,1,70 );

        int maxEnergia=200;
        double energiaAtual=200;
        Scooter mt=new Scooter ( maxEnergia,50,1d,40d,80,56 );

        Entrega entrega=new Entrega ( estafeta,mt );
        entrega.addEncomenda (lEncomendas);

        Endereco inicio=l.get ( 0 );
        when(enderecoMock.criarGrafoEnderecos ()).thenReturn ( g );
        result=controller.rotaTerrestreMaisEficiente ( inicio,le,entrega,mt,10,resultSet );

        assertEquals ( 1.25,result );
        assertEquals ( 0.009,resultSet.get2nd (),0.01 );
        assertEquals ( 12.5,resultSet.get3rd (), 0.1);
    }

    private List<Endereco> criarGrafoTerrestre(Graph<Endereco, Rua> g){
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Rua r1 = new Rua ( "rua1", 3d, 0,0, 1, 0 );
        Rua r2 = new Rua ( "rua2", 3d, 0,0, 1, 0 );
        Rua r3 = new Rua ( "rua3", 20d, 0,0, 1, 0 );
        Rua r4 = new Rua ( "rua4", 1d, 0,0, 1, 0 );
        Rua r5 = new Rua ( "rua5", 5d, 0,0, 1, 0 );
        Rua r6 = new Rua ( "rua6", 1d, 0,0, 1, 0 );
        Rua r7 = new Rua ( "rua7", 0.5d, 0,0, 1, 0 );
        Rua r8 = new Rua ( "rua8", 2d, 0,0, 1, 0 );
        Rua r9 = new Rua ( "rua9", 1.5d, 0,0, 1, 0 );

        g.insertEdge ( e1, e2, r1, 3E-3);
        g.insertEdge ( e2, e1, r2, 3E-3);
        g.insertEdge ( e1, e3, r3, 20E-3);
        g.insertEdge ( e1, e4, r4, 1E-3);
        g.insertEdge ( e2, e3, r5, 5E-3);
        g.insertEdge ( e3, e5, r6, 2E-3);
        g.insertEdge ( e3, e6, r7, 0.5E-3);
        g.insertEdge ( e5, e4, r8, 2E-3);
        g.insertEdge ( e4, e1, r9, 3E-3);

        return Arrays.asList ( e1,e2,e3,e4,e5,e6 );
    }

    private List<Endereco> criarGrafoAereo(Graph<Endereco, Caminho> g){
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Endereco e2 = new Endereco ( "r2", "n2", "42342", "234", "wetr", 235, 234, 1, false );
        Endereco e3 = new Endereco ( "r3", "n3", "42342", "234", "wetr", 236, 234, 1, true );
        Endereco e4 = new Endereco ( "r4", "n4", "42342", "234", "wetr", 237, 234, 1, false );
        Endereco e5 = new Endereco ( "r5", "n5", "42342", "234", "wetr", 238, 234, 1, false );
        Endereco e6 = new Endereco ( "r6", "n6", "42342", "234", "wetr", 239, 234, 1, false );

        Caminho r1 = new Caminho ( "rua1", 3d, 0,0);
        Caminho r2 = new Caminho ( "rua2", 3d, 0,0);
        Caminho r3 = new Caminho ( "rua3", 20d, 0,0);
        Caminho r4 = new Caminho ( "rua4", 1d, 0,0);
        Caminho r5 = new Caminho ( "rua5", 5d, 0,0);
        Caminho r6 = new Caminho ( "rua6", 1d, 0,0);
        Caminho r7 = new Caminho ( "rua7", 0.5d, 0,0 );
        Caminho r8 = new Caminho ( "rua8", 2d, 0,0 );
        Caminho r9 = new Caminho ( "rua9", 1.5d, 0,0);

        g.insertEdge ( e1, e2, r1, 3E-3);
        g.insertEdge ( e2, e1, r2, 3E-3);
        g.insertEdge ( e1, e3, r3, 20E-3);
        g.insertEdge ( e1, e4, r4, 1E-3);
        g.insertEdge ( e2, e3, r5, 5E-3);
        g.insertEdge ( e3, e5, r6, 2E-3);
        g.insertEdge ( e3, e6, r7, 0.5E-3);
        g.insertEdge ( e5, e4, r8, 2E-3);
        g.insertEdge ( e4, e1, r9, 3E-3);

        return Arrays.asList ( e1,e2,e3,e4,e5,e6 );
    }

    @Test
    void determinarMelhorRotadeEntrega(){
        int maxEnergia=200;
        double bestscore= Double.MAX_VALUE;
        Endereco e1 = new Endereco ( "r1", "n1", "42342", "234", "wetr", 234, 234, 1, true );
        Farmacia farmacia1 = new Farmacia("e1", e1);
        Drone mtd=new Drone ( maxEnergia,0.5d,1d,40d,80d,10,10 );
        Scooter mts=new Scooter ( maxEnergia,50,1d,40d,80,56 );
        Entrega entrega = new Entrega(1, LocalDate.now(), new Estafeta(1, "Estafeta", 1d, 1, 1, 1d));
        Map<Integer, Encomenda> mEncomendas = new HashMap<>();
        Encomenda e11 = new Encomenda(1, 1, Encomenda.EstadoEncomenda.ENVIO_PRONTO);
        e11.addProduto(new Produto("nome", 1d), 2);
        mEncomendas.put(1, e11);
        List<Endereco> listEndereco = controller.getEnderecosFromEncomendas(entrega.getListEncomendas());


        entrega.setMeioTransporte(mtd);
        Graph<Endereco,Caminho> gAereo = new Graph<>(true);
        criarGrafoAereo(gAereo);
        controller.calcularGastoEnergetico(gAereo,mtd,mtd.getPeso(),mtd.getVelocidadeMaxima());
        Tuple<List<Label>,Double,Double> droneTempoMelhorTrajeto=Tuple.create ( new LinkedList<> (), Double.MAX_VALUE, Double.MAX_VALUE );

        double temporPercursoMaisRapido = controller.determinarMelhorRotaEntrega(gAereo,farmacia1.getEndereco(),listEndereco,entrega,mtd,mtd.getVelocidadeMaxima(),droneTempoMelhorTrajeto,true);
        double tempoScore = controller.calcularScorePercurso(temporPercursoMaisRapido,droneTempoMelhorTrajeto.get2nd());

        System.out.println(temporPercursoMaisRapido +" + " + " " + tempoScore);

        Tuple<List<Label>,Double,Double> droneEnergiaMelhorTrajeto=Tuple.create ( new LinkedList<> (), Double.MAX_VALUE, Double.MAX_VALUE );

        double tempoPercursoMaisEnergeticoDrone=controller.determinarMelhorRotaEntrega ( gAereo,farmacia1.getEndereco (),listEndereco,entrega,mtd,mtd.getVelocidadeMaxima(),droneEnergiaMelhorTrajeto,false );
        double droneEnergiaScore=controller.calcularScorePercurso ( tempoPercursoMaisEnergeticoDrone, droneEnergiaMelhorTrajeto.get2nd ());

        System.out.println(tempoPercursoMaisEnergeticoDrone +" + " + " " + droneEnergiaScore);

        Tuple<List<Label>,Double,Double> melhorTrajeto=Tuple.create ( new LinkedList<> (), Double.MAX_VALUE, Double.MAX_VALUE );

        melhorTrajeto.set(droneEnergiaMelhorTrajeto);
        bestscore = tempoPercursoMaisEnergeticoDrone;
        String veiculo = Constantes.DRONE;

        assertEquals(veiculo,"Drone");

    }


}

