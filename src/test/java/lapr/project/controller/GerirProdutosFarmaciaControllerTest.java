package lapr.project.controller;

import lapr.project.data.GestorHandler;
import lapr.project.data.ProdutoHandler;
import lapr.project.data.ProdutosFarmaciaHandler;
import lapr.project.model.Endereco;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import lapr.project.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GerirProdutosFarmaciaControllerTest {
    private ProdutosFarmaciaHandler produtosFarmaciaMock;
    private GestorHandler gestorHandlerMock;
    private GerirProdutosFarmaciaController controller;
    private ProdutoHandler produtoHandlerMock;
    public GerirProdutosFarmaciaControllerTest() {
        gestorHandlerMock= Mockito.mock(GestorHandler.class);
        produtosFarmaciaMock=Mockito.mock(ProdutosFarmaciaHandler.class);
        produtoHandlerMock = Mockito.mock(ProdutoHandler.class);
       try{
           controller= new GerirProdutosFarmaciaController();
           controller.setFarmaciaHandler(produtosFarmaciaMock);
           controller.setGestorHandler(gestorHandlerMock);
           controller.setProdutoHandler(produtoHandlerMock);
       }catch (IOException e){
           System.out.println("Erro fatal");
       }

    }

    @Test
    void getProdutosFarmaciaLoggadoInvalido() {
        User user = new User(-1,"ecouto.com","a","b","a");
        Endereco endereco = new Endereco("rua", "numPorta", "codPostal", "localidade", "pais", -1, -1,50);
        InstanciaAtual.getInstance().setUserLogado(user);
        Farmacia farmacia = new Farmacia(-2, "nome",endereco);
        List<Produto> emptyListPro = new ArrayList<>();
        Mockito.when(produtosFarmaciaMock.getProdutosFarmacia(farmacia)).thenReturn(emptyListPro);
        List<Produto> result = controller.getProdutosFarmaciaLoggado(user);
        assertEquals(emptyListPro,result);
    }

    @Test
    void getProdutosFarmaciaLoggadoValido() {
        User user = new User(5,"a","a","Gestor","ecouto@gmail.com");
        Endereco endereco = new Endereco("rua", "numPorta", "codPostal", "localidade", "pais", -1, -1,50);
        InstanciaAtual.getInstance().setUserLogado(user);
        Farmacia farmacia = new Farmacia(5, "nome",endereco);
        List<Produto> expected = new ArrayList<>();
        Produto produto = new Produto(1, "nome", 5, 5, 5,"descricao");
        expected.add(produto);
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("ecouto@gmail.com")).thenReturn(5);
        Mockito.when(produtosFarmaciaMock.getProdutosFarmacia(farmacia)).thenReturn(expected);
        List<Produto> result = controller.getProdutosFarmaciaLoggado(user);
        assertEquals(expected,result);
    }

    @Test
    void addProdutoFarmaciaTest1() {
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("gestor1@gmail.com")).thenReturn(1);
        Mockito.when(produtosFarmaciaMock.addProdutoFarmacia(1, 1, 3)).thenReturn(true);
        boolean res = controller.addProdutoFarmacia(1, 3, "gestor1@gmail.com");
        assertTrue(res);
    }

    @Test
    void addProdutoFarmaciaTest2() {
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("gestor2@gmail.com")).thenReturn(-1);
        Mockito.when(produtosFarmaciaMock.addProdutoFarmacia(1, -1, 3)).thenReturn(false);
        boolean res = controller.addProdutoFarmacia(1, 3, "gestor2@gmail.com");
        assertFalse(res);
    }

    @Test
    void removerProdutoFarmaciaTest1() {
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("gestor1@gmail.com")).thenReturn(1);
        Mockito.when(produtosFarmaciaMock.removerProdutoFarmacia(1, 1)).thenReturn(true);
        boolean res = controller.removerProdutoFarmacia(1, "gestor1@gmail.com");
        assertTrue(res);
    }

    @Test
    void removerProdutoFarmaciaTest2() {
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("gestor2@gmail.com")).thenReturn(-11);
        Mockito.when(produtosFarmaciaMock.removerProdutoFarmacia(1, -1)).thenReturn(false);
        boolean res = controller.removerProdutoFarmacia(1, "gestor2@gmail.com");
        assertFalse(res);
    }

    @Test
    void updateStock() {
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("gestor1@gmail.com")).thenReturn(1);
        Mockito.when(produtosFarmaciaMock.updateStock(1,1, 3)).thenReturn(true);
        boolean res = controller.updateStock(3,1, "gestor1@gmail.com");
        assertTrue(res);
    }

    @Test
    void updateStock2() {
        Mockito.when(gestorHandlerMock.getFarmaciaFromGestorEmail("gestor2@gmail.com")).thenReturn(1);
        Mockito.when(produtosFarmaciaMock.updateStock(-1,1, -3)).thenReturn(false);
        boolean res = controller.updateStock(-3,-1, "gestor2@gmail.com");
        assertFalse(res);
    }

    @Test
    void listaprodutosTest(){
        List<Produto> expected = new ArrayList<>();

       Mockito.when(produtoHandlerMock.getListaProdutos()).thenReturn(new ArrayList<>());
        List<Produto> listaProduto = controller.getListaProdutos();
        assertEquals(expected,listaProduto);

        expected.add(new Produto(1,"p1",23.0));
        Mockito.when(produtoHandlerMock.getListaProdutos()).thenReturn(expected);
        listaProduto = controller.getListaProdutos();
        assertEquals(expected,listaProduto);



    }

}