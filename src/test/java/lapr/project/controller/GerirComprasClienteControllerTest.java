package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import static org.junit.jupiter.api.Assertions.*;

class GerirComprasClienteControllerTest {
    private  ClientesHandler clientesHandlerMock;
    private  ClientesHandler clientesHandlerMock1;
    private  InstanciaAtual instanciaAtual;
    private GerirComprasClienteController controller;
    private FarmaciaHandler farmaciaHandler;
    private ProdutosFarmaciaHandler produtosFarmaciaHandler;
    private ProdutoHandler produtoHandler;
    private EncomendaDataHandler encomendaDataHandler;
    private EmailHandler emailHandler;
    private GerirEntregasController entregasController;

    public GerirComprasClienteControllerTest() {
        try {
            controller = new GerirComprasClienteController();
            entregasController =Mockito.mock(GerirEntregasController.class);
            controller.setEntregasController(entregasController);
            instanciaAtual = InstanciaAtual.getInstance();
            this.clientesHandlerMock = Mockito.mock(ClientesHandler.class);
            this.clientesHandlerMock1 = Mockito.mock(ClientesHandler.class);
            instanciaAtual.setHandler(clientesHandlerMock1);
            farmaciaHandler = Mockito.mock(FarmaciaHandler.class);
            produtosFarmaciaHandler = Mockito.mock(ProdutosFarmaciaHandler.class);
            encomendaDataHandler = Mockito.mock(EncomendaDataHandler.class);
            emailHandler = Mockito.mock(EmailHandler.class);
            produtoHandler = Mockito.mock(ProdutoHandler.class);
            controller.setEmailHandler(emailHandler);
            controller.setEncomendaDataHandler(encomendaDataHandler);
            controller.setFarmaciaHandler(farmaciaHandler);
            controller.setProdutosFarmaciaHandler(produtosFarmaciaHandler);
            controller.setProdutoDB(produtoHandler);
            controller.setClientesHandler(clientesHandlerMock);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getListaFarmacias() {
        List<Farmacia> expected = new ArrayList<>();
        Mockito.when(farmaciaHandler.getListaFarmacias()).thenReturn(expected);
        List<Farmacia> result = controller.getListaFarmacias();
        assertEquals(expected,result);

        Endereco endereco = new Endereco("a","a","a","a","a",1,1,1);
        Farmacia farmacia2 = new Farmacia("nome",endereco);

        expected.add(farmacia2);
        Mockito.when(farmaciaHandler.getListaFarmacias()).thenReturn(expected);
        result = controller.getListaFarmacias();
        assertEquals(expected,result);
    }


    @Test
    void getProdutosFarmacia() {
        Endereco endereco = new Endereco("a","a","a","a","a",1,1,1);
        Farmacia farmacia2 = new Farmacia("nome",endereco);
        List<Produto> expected = new ArrayList<>();
        List<Produto> result = controller.getListaProdutos();
        assertEquals(expected,result);

        Produto produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        expected.add(produto1);
        Mockito.when(produtoHandler.getListaProdutos()).thenReturn(expected);
        result = controller.getListaProdutos();
        assertEquals(expected,result);
    }

    @Test
    void adicionarProdutoCesta() {
        Field instance= null;
        try {
            instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
            instance.setAccessible ( true );
            instance.set ( null,null );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        User user = new User(1,"cliente1","username","cliente","valido@gmail.com");
        InstanciaAtual.getInstance().setUserLogado(user);
        Map<Farmacia, Map<Produto,Integer>> expected = new TreeMap<>();
        Map<Farmacia, Map<Produto,Integer>> result = controller.getCarrinhoCompras();
        assertEquals(expected,result);
        Endereco endereco = new Endereco("a","a","a","a","a",1,1,1);
        Farmacia farmacia2 = new Farmacia("nome",endereco);
        Produto produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        controller.adicionarProdutoCesta(produto1,2,farmacia2);
        Map<Produto,Integer> subMapa = new TreeMap<>();
        subMapa.put(produto1,2);
        expected.put(farmacia2, subMapa);
        result = controller.getCarrinhoCompras();
        assertEquals(expected,result);
       boolean flag = controller.adicionarProdutoCesta(produto1,2,farmacia2);
       assertEquals(true,flag);
       flag = controller.adicionarProdutoCesta(produto1,20,farmacia2);

       assertEquals(true,flag);
       flag = controller.adicionarProdutoCesta(produto1,200,farmacia2);
       assertEquals(false,flag);
    }


    @Test
    void getClienteLogado() {
        Field instance= null;
        try {
            instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
            instance.setAccessible ( true );
            instance.set ( null,null );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente expected = new Cliente("cliente1","username","valido@gmail.com","eduardo",endereco,cartao);
        User user = new User(1,"cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Mockito.when(clientesHandlerMock1.getClienteLogado(user)).thenReturn(expected);
        Cliente result = instanciaAtual.getClienteLogado();
        assertEquals(expected,result);
    }

    @Test
    void removerProdutoCarrinho01(){
        Field instance;
        try {
            instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
            instance.setAccessible ( true );
            instance.set ( null,null );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        User user = new User(1,"cliente","password","cliente","cliente@gmail.com");
        InstanciaAtual.getInstance().setUserLogado(user);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.putIfAbsent(p1, 2);
        expected.putIfAbsent(f, produtoQuantidade);
        controller.adicionarProdutoCesta(p1, 2, f);
        controller.adicionarProdutoCesta(p2, 1, f);

        boolean obtained = controller.removerProdutoCarrinho(f, p2);

        assertEquals(expected, controller.getCarrinhoCompras());
        assertTrue(obtained);
    }

    @Test
    void removerProdutoCarrinho02(){
        Field instance;
        try {
            instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
            instance.setAccessible ( true );
            instance.set ( null,null );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        User user = new User(1,"cliente","password","cliente","cliente@gmail.com");
        InstanciaAtual.getInstance().setUserLogado(user);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Produto p3 = new Produto("p3", 5.2, 6.7,2.3, "teste3");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.putIfAbsent(p1, 2);
        produtoQuantidade.putIfAbsent(p2, 1);
        expected.putIfAbsent(f,produtoQuantidade);
        controller.adicionarProdutoCesta(p1, 2, f);
        controller.adicionarProdutoCesta(p2, 1, f);

        boolean obtained = controller.removerProdutoCarrinho(f, p3);

        assertEquals(expected, controller.getCarrinhoCompras());

    }

    @Test
    void removerProdutoCarrinho03(){
        Field instance;
        try {
            instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
            instance.setAccessible ( true );
            instance.set ( null,null );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        User user = new User(1,"cliente","password","cliente","cliente@gmail.com");
        InstanciaAtual.getInstance().setUserLogado(user);
        Map<Farmacia,Map<Produto,Integer>> unexpected = new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Produto p3 = new Produto("p3", 5.2, 6.7,2.3, "teste3");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.putIfAbsent(p1, 2);
        produtoQuantidade.putIfAbsent(p2, 1);
        produtoQuantidade.putIfAbsent(p3, 5);
        unexpected.putIfAbsent(f,produtoQuantidade);
        controller.adicionarProdutoCesta(p1, 2, f);
        controller.adicionarProdutoCesta(p2, 1, f);

        boolean obtained = controller.removerProdutoCarrinho(f, p3);

        assertNotEquals(unexpected, controller.getCarrinhoCompras());
    }

    @Test
    void removerProdutoCarrinho04(){
        Field instance;
        try {
            instance = InstanciaAtual.class.getDeclaredField ( "singleton" );
            instance.setAccessible ( true );
            instance.set ( null,null );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        User user = new User(1,"cliente","password","cliente","cliente@gmail.com");
        InstanciaAtual.getInstance().setUserLogado(user);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        expected.putIfAbsent(f,produtoQuantidade);
        assertFalse(controller.removerProdutoCarrinho(f, p1));
    }

    @Test
    void calcularTaxaEntrega() {
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);
        double expected = 15.0;
        double result = controller.calcularTaxaEntrega(cliente1,false);
        assertEquals(expected,result);
        Mockito.when(clientesHandlerMock.retirarCreditos(cliente1,10)).thenReturn(true);
        expected = 0;
        result = controller.calcularTaxaEntrega(cliente1,true);
        assertEquals(expected,result);
        expected = 15.0;
        Mockito.when(clientesHandlerMock.retirarCreditos(cliente1,10)).thenReturn(false);
        result = controller.calcularTaxaEntrega(cliente1,true);
        assertEquals(expected,result);
    }

    @Test
    void gerarEncomenda() {
        int expected = -1;
        Map<Farmacia, Map<Produto,Integer>> map = new TreeMap<>();
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);

        int result = controller.gerarEncomenda(map,123456789,cliente1,false);

        assertEquals(expected,result);
        // Custo 0

        Map<Farmacia,Map<Produto,Integer>> mapa= new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 20,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 5,0.9, "teste2");
        Produto p3 = new Produto("p3", 5.2, 6.7,2.3, "teste3");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.put(p1, 2);
        produtoQuantidade.put(p2, 1);
        //produtoQuantidade.put(p3, 5);
        Farmacia farmacia2 = new Farmacia("nome",endereco);
        mapa.put(farmacia2,produtoQuantidade);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p1,2,farmacia2)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p2,1,farmacia2)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p3,5,farmacia2)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.removerStock(farmacia2,p1,2)).thenReturn(true);
        //Mockito.when(produtosFarmaciaHandler.removerStock(farmacia2,p2,1)).thenReturn(true);
        //Mockito.when(produtosFarmaciaHandler.removerStock(farmacia2,p3,5)).thenReturn(true);

        Encomenda en1 = new Encomenda(123456789,produtoQuantidade, Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmacia2,55.0,cliente1);
        Mockito.when(encomendaDataHandler.gerarEncomenda(en1)).thenReturn(false);



         result = controller.gerarEncomenda(mapa,123456789,cliente1,false);

        assertEquals(expected,result);
    }

    @Test
    void gerarEncomenda1() {

        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();


        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Produto p3 = new Produto("p3", 5.2, 6.7,2.3, "teste3");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        Farmacia f2 = new Farmacia(2, "farmacia2");
        produtoQuantidade.put(p1, 2);
        produtoQuantidade.put(p2, 1);
        produtoQuantidade.put(p3, 5);

        Map<Produto,Integer> clean = new TreeMap<>();
        clean.put(p2,1000);
        expected.put(f,produtoQuantidade);
        expected.put(f2,clean);

        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p1,2,f)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p2,1,f)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p3,5,f)).thenReturn(true);

        Mockito.when(produtosFarmaciaHandler.removerStock(f,p1,2)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.removerStock(f,p2,1)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.removerStock(f,p3,5)).thenReturn(true);

        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p2,1000,f2)).thenReturn(false);

        Mockito.when(entregasController.getFarmaciaMaisPertoDeUser(cliente1.getEndereco())).thenReturn(f);
        Encomenda encomendaEnviar = new Encomenda(123456789,produtoQuantidade, Encomenda.EstadoEncomenda.ENVIO_PRONTO,f,85.5,cliente1);
        Mockito.when(encomendaDataHandler.gerarEncomenda(encomendaEnviar)).thenReturn(true);
        int ex = 1;
        int result= controller.gerarEncomenda(expected,123456789,cliente1,false);
        assertEquals(ex,result);

        Mockito.when(encomendaDataHandler.gerarEncomenda(encomendaEnviar)).thenReturn(false);

        ex =-1;
        result= controller.gerarEncomenda(expected,123456789,cliente1,false);
        assertEquals(ex,result);
    }

    @Test
    void gerarEncomenda2() {

        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();


        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Produto p3 = new Produto("p3", 5.2, 6.7,2.3, "teste3");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        Farmacia f2 = new Farmacia(2, "farmacia2");
        produtoQuantidade.put(p1, 2);
        produtoQuantidade.put(p2, 1);
        produtoQuantidade.put(p3, 5);

        Map<Produto,Integer> clean = new TreeMap<>();
        clean.put(p2,1000);
        expected.put(f,produtoQuantidade);
        expected.put(f2,clean);

        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p1,2,f)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p2,1,f)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p3,5,f)).thenReturn(true);

        Mockito.when(produtosFarmaciaHandler.removerStock(f,p1,2)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.removerStock(f,p2,1)).thenReturn(true);
        Mockito.when(produtosFarmaciaHandler.removerStock(f,p3,5)).thenReturn(true);

        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p2,1000,f2)).thenReturn(false);

        Mockito.when(entregasController.getFarmaciaMaisPertoDeUser(cliente1.getEndereco())).thenReturn(f2);
        Encomenda encomendaEnviar = new Encomenda(123456789,produtoQuantidade, Encomenda.EstadoEncomenda.ENVIO_PRONTO,f2,85.5,cliente1);

        Mockito.when(encomendaDataHandler.gerarEncomenda(encomendaEnviar)).thenReturn(false);

         int ex = -1;
        int result= controller.gerarEncomenda(expected,123456789,cliente1,false);

        assertEquals(ex,result);
    }

    @Test
    void  removerStock(){
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Produto p3 = new Produto("p3", 5.2, 6.7,2.3, "teste3");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.put(p1, 2);
        produtoQuantidade.put(p2, 1);
        produtoQuantidade.put(p3, 5);
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);
        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p3,6,f)).thenReturn(false);
        Mockito.when(entregasController.realizarTransferenciaProduto(f,p3,1)).thenReturn(false);
        String conteudo = String.format("Caro %s, retirou-se o produto %s que possua em carrinho uma vez que nao possuimos stock para cumprir pedido", cliente1.getNome(), p3);
        String assunto = "Aviso relativo ao seu carrinho";
        Mockito.when(emailHandler.sendEmail(cliente1.getEmail(), assunto, conteudo)).thenReturn(true);
        double precoTotal=controller.verificarStockProduto(f,p3,5,cliente1);

        assertEquals(0,precoTotal);



        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p3,6,f)).thenReturn(false);
        Mockito.when(entregasController.realizarTransferenciaProduto(f,p3,6)).thenReturn(true);
         precoTotal=controller.verificarStockProduto(f,p3,6,cliente1);
         assertEquals(40.2,precoTotal);


        Mockito.when(produtosFarmaciaHandler.verificarStockProduto(p3,5,f)).thenReturn(true);
        precoTotal = controller.verificarStockProduto(f,p3,5,cliente1);
        assertEquals(33.5,precoTotal);




    }
}