package lapr.project.controller;

import lapr.project.data.ClientesHandler;
import lapr.project.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class InstanciaAtualTest {

    private  ClientesHandler mock;
    private  InstanciaAtual instanciaAtual;

    public InstanciaAtualTest(){

            instanciaAtual = InstanciaAtual.getInstance();
            this.mock = Mockito.mock(ClientesHandler.class);
            instanciaAtual.setHandler(mock);

    }
    @Test
    void setUserLogadoCliente() {
        Map<Farmacia, Map<Produto,Integer>> expected = new TreeMap<>();
        User user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Map<Farmacia, Map<Produto,Integer>> result = instanciaAtual.getCarrinhoCompras();
        assertEquals(expected,result);
    }
/*
    @Test
    void setUserLogadoDiferente() {
        User user = new User("cliente1","username","administrador","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Map<Farmacia, Map<Produto,Integer>> result = instanciaAtual.getCarrinhoCompras();
        assertNull(result);
    }

 */

    @Test
    void getClienteLogado() {
        User user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente expected = new Cliente("cliente1","username","valido@gmail.com","eduardo",endereco,cartao);
        Mockito.when(mock.getClienteLogado(user)).thenReturn(expected);
        Cliente result = instanciaAtual.getClienteLogado();
        assertEquals(expected,result);
    }
/*
    @Test
    void getCarrinhoCompras() {

        User user = new User("cliente1","username","administrador","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Map<Farmacia, Map<Produto,Integer>> result = instanciaAtual.getCarrinhoCompras();
        assertNull(result);

        Map<Farmacia, Map<Produto,Integer>> expected = new TreeMap<>();
        user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        result = instanciaAtual.getCarrinhoCompras();
        assertEquals(expected,result);
    }

    @Test
    void setCarrinhoCompras() {
        User user = new User("cliente1","username","administrador","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Map<Farmacia, Map<Produto,Integer>> result = instanciaAtual.getCarrinhoCompras();
        assertNull(result);

        Map<Farmacia, Map<Produto,Integer>> expected = new TreeMap<>();
        user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        instanciaAtual.setCarrinhoCompras(expected);
        result = instanciaAtual.getCarrinhoCompras();
        assertEquals(expected,result);
    }

 */

    @Test
    void removerProdutoCarrinho(){
        User user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.putIfAbsent(p1, 2);
        expected.putIfAbsent(f, produtoQuantidade);

        instanciaAtual.introduzirProdutoNoCarrinho(p1, 2, f);
        instanciaAtual.introduzirProdutoNoCarrinho(p2, 4, f);

        boolean obtained = instanciaAtual.removerProdutoCarrinho(f, p2);
        assertEquals(true, obtained);
    }

    @Test
    void removerProdutoCarrinho01(){
        User user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);

        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Farmacia f = new Farmacia(1, "farmaciaTeste");

        boolean obtained = instanciaAtual.removerProdutoCarrinho(f, p1);
        assertEquals(false, obtained);
    }

    @Test
    void removerProdutoCarrinho02(){
        User user = new User("cliente1","username","cliente","valido@gmail.com");
        instanciaAtual.setUserLogado(user);
        Map<Farmacia,Map<Produto,Integer>> expected = new TreeMap<>();
        Map<Produto,Integer> produtoQuantidade = new TreeMap<>();
        Produto p1 = new Produto("p1", 10, 17.3,0.05, "teste1");
        Produto p2 = new Produto("p2", 1, 2.4,0.9, "teste2");
        Farmacia f = new Farmacia(1, "farmaciaTeste");
        produtoQuantidade.putIfAbsent(p1, 2);
        expected.putIfAbsent(f, produtoQuantidade);

        instanciaAtual.introduzirProdutoNoCarrinho(p1, 2, f);

        assertTrue(instanciaAtual.removerProdutoCarrinho(f, p2));
    }


}