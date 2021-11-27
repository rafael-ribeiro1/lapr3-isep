package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class EncomendaTest {
    private final Encomenda encomenda1;
    private final Encomenda encomenda2;
    private final Encomenda encomenda3;
    private final Encomenda encomenda4;
    public EncomendaTest() {
        Map<Produto,Integer> mapa = new TreeMap<>();
        Produto produto = new Produto(1,"produto",100,123,1,"descricao");
        mapa.put(produto,1);
        Farmacia farmaciaEncomenda = new Farmacia(1,"farmacia");
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);

        encomenda1= new Encomenda(1,123456789, Encomenda.EstadoEncomenda.ENVIO_PRONTO);
        encomenda2 = new Encomenda(1,123456789);
        encomenda3 = new Encomenda(1,123456789,mapa,Encomenda.EstadoEncomenda.ENVIO_PRONTO);
        encomenda4 = new Encomenda(123456789,mapa,Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmaciaEncomenda,10.0,cliente1);
    }

    @Test
    void getId() {
        int expected = 1 ;
        int result = encomenda1.getId();

        assertEquals(expected,result);

        result=0;
        assertNotEquals(expected,result);

        result = encomenda2.getId();

        assertEquals(expected,result);

        result=0;
        assertNotEquals(expected,result);

        result = encomenda3.getId();

        assertEquals(expected,result);

        result=0;
        assertNotEquals(expected,result);

    }

    @Test
    void getEstadoEncomenda() {
        String expected = "Pronto a enviar";
        String result = encomenda1.getEstadoEncomenda().getDescricao();

        assertEquals(expected,result);

        result= "erro";
        assertNotEquals(expected,result);


        Encomenda.EstadoEncomenda r = encomenda2.getEstadoEncomenda();

        assertNull(r);


        expected = "Pronto a enviar";
        result = encomenda4.getEstadoEncomenda().getDescricao();

        assertEquals(expected,result);

        result= "erro";
        assertNotEquals(expected,result);
    }

    @Test
    void getProdutosEncomendados() {
        Map<Produto,Integer> expected = new TreeMap<>();
        Produto produto = new Produto(1,"produto",100,123,1,"descricao");
        expected.put(produto,1);
        Map<Produto,Integer> result = encomenda3.getProdutosEncomendados();
        assertEquals(expected,result);

        expected.clear();
        result=encomenda1.getProdutosEncomendados();
        assertEquals(expected,result);

        result=encomenda2.getProdutosEncomendados();
        assertEquals(expected,result);

    }

    @Test
    void addProduto() {
        Produto produto = new Produto(1,"produto",100,123,1,"descricao");
        encomenda1.addProduto(produto,2);
        Map<Produto,Integer> expected = new TreeMap<>();
        expected.put(produto,2);
        Map<Produto,Integer> result = encomenda1.getProdutosEncomendados();

        assertEquals(expected,result);

        encomenda2.addProduto(produto,2);
        result = encomenda2.getProdutosEncomendados();
        assertEquals(expected,result);

        encomenda3.addProduto(produto,2);
        result = encomenda2.getProdutosEncomendados();
        assertEquals(expected,result);
    }

    @Test
    void setPeso() {
        Map<Produto,Integer> mapa = new TreeMap<>();
        Produto produto = new Produto(1,"produto",100,123,1,"descricao");
        mapa.put(produto,1);
        encomenda1.setPeso(mapa);
        assertEquals(1,encomenda1.getPeso());
        encomenda2.setPeso(mapa);
        assertEquals(1,encomenda2.getPeso());
    }

    @Test
    void getNifCliente() {
        int expected = 123456789;
        assertEquals(expected,encomenda1.getNifEncomenda());
        assertEquals(expected,encomenda2.getNifEncomenda());
        assertEquals(expected,encomenda3.getNifEncomenda());
        assertEquals(expected,encomenda4.getNifEncomenda());
        expected = 123456389;
        assertNotEquals(expected,encomenda4.getNifEncomenda());
    }

    @Test
    void getFarmaciaEncomenda() {

        Farmacia farmaciaEncomenda = new Farmacia(1,"farmacia");
        assertEquals(farmaciaEncomenda,encomenda4.getFarmaciaEncomenda());
        assertNull(encomenda1.getFarmaciaEncomenda());
    }

    @Test
    void getCusto() {
        double expected = 10;
        assertEquals(expected,encomenda4.getCusto());
        assertNull(encomenda1.getCusto());
    }

    @Test
    void getCliente() {
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);
        assertEquals(cliente1,encomenda4.getCliente());
    }

    @Test
    void getPeso() {
        Map<Produto,Integer> mapa = new TreeMap<>();
        Produto produto = new Produto(1,"produto",100,123,1,"descricao");
        mapa.put(produto,1);
        encomenda1.setPeso(mapa);
        assertEquals(1,encomenda1.getPeso());
        encomenda2.setPeso(mapa);
        assertEquals(1,encomenda2.getPeso());
        assertEquals(1,encomenda4.getPeso());
    }

    @Test
    void setPesoTest(){
        Map<Produto,Integer> mapa = new TreeMap<>();
        Produto produto = new Produto(1,"produto",100,123,11,"descricao");
        mapa.put(produto,2);
        encomenda4.setPeso(mapa);
        assertEquals(22, encomenda4.getPeso());
        assertFalse(encomenda4.getPeso() < produto.getPeso());
    }

    @Test
    void equalsTest(){
        Map<Produto,Integer> mapa = new TreeMap<>();
        Produto produto = new Produto(1,"produto",100,123,1,"descricao");
        mapa.put(produto,1);
        Farmacia farmaciaEncomenda = new Farmacia(1,"farmacia");
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        Cliente cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);
        Farmacia fe = new Farmacia(2, "farmacia");
        assertEquals(encomenda4, encomenda4);
        assertEquals(encomenda4, new Encomenda(123456789,mapa, Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmaciaEncomenda,10.0,cliente1));
        assertNotEquals(encomenda1, new Estafeta(1, "nome", 23, 123456789, 1, 76));

        encomenda4.setPeso(mapa);
        assertNotEquals(encomenda4,  new Encomenda(987654321,mapa, Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmaciaEncomenda,10.0,cliente1));
        assertNotEquals(encomenda4,  new Encomenda(123456789,mapa, Encomenda.EstadoEncomenda.ENTREGUE,farmaciaEncomenda,10.0,cliente1));
        assertNotEquals(encomenda4,  new Encomenda(123456789,mapa, Encomenda.EstadoEncomenda.ENVIO_PRONTO,fe,10.0,cliente1));
        assertNotEquals(encomenda4,  new Encomenda(123456789,mapa, Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmaciaEncomenda,5.6,cliente1));
        mapa.put(new Produto("produto novo", 12.4), 3);
        assertNotEquals(encomenda4,  new Encomenda(123456789,mapa, Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmaciaEncomenda,10.0,cliente1));

    }


    @Test
    void hashCodeTest(){
        int expected =Objects.hash(encomenda1.getNifEncomenda(),encomenda1. getEstadoEncomenda(), encomenda1.getFarmaciaEncomenda(),
                encomenda1.getCusto(), encomenda1.getPeso());
        assertEquals(expected, encomenda1.hashCode());
        assertNotEquals(encomenda2.hashCode(), expected);
    }
}