package lapr.project.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EntregaTest {

    @Test
    void EntregaTest01(){
        LocalDate data = LocalDate.of(2021,2, 25 );
        Estafeta e = new Estafeta(1, "teste1", 35, 123456789, 1,60);
        Entrega e1 = new Entrega(1,  data, e);
        Scooter m1 = new Scooter(2, 23, 100, "FUNCIONAL");
        Drone m = new Drone(1,400,100,1.0,1.0,50.0,30d,10d,1d);

        Entrega e2 = new Entrega(e,m1);
        Entrega e3 = new Entrega(m);

        int expected = 0;
        assertEquals(expected,e2.getId());
        assertEquals(expected,e3.getId());


        List<Encomenda> lista = new ArrayList<>();
        assertEquals(lista,e2.getListEncomendas());
        assertEquals(lista,e3.getListEncomendas());

        assertEquals(1, e1.getId());
        assertEquals(data, e1.getDataEntrega());
        assertEquals(e, e1.getEstafeta());
    }

    @Test
    void EntregaTest02(){
        LocalDate data = LocalDate.of(2021,4, 13 );
        Estafeta e = new Estafeta(2, "teste2", 35, 987654321, 2,60);
        Entrega e1 = new Entrega(data, e);
        assertEquals(data, e1.getDataEntrega());
        assertEquals(e, e1.getEstafeta());
    }

    @Test
    void EntregaTest03(){
        LocalDate data = LocalDate.of(2021,4, 13 );
        Estafeta e = new Estafeta(2, "teste2", 35, 987654321, 2,60);
        Encomenda en = new Encomenda(1, 123456780);
        Encomenda en1 = new Encomenda(2, 123456781);
        Encomenda en2 = new Encomenda(3, 123456782);
        Encomenda en3 = new Encomenda(4, 123456783);
        Encomenda en4 = new Encomenda(5, 123456784);
        Encomenda en5 = new Encomenda(6, 123456785);
        List<Encomenda> l = new ArrayList<>(Arrays.asList(en, en1, en2, en3, en4, en5));
        Entrega e1 = new Entrega(2,l, data, e);

        assertEquals(2, e1.getId());

        assertEquals(l, e1.getListEncomendas());
        assertEquals(data, e1.getDataEntrega());
        assertEquals(e, e1.getEstafeta());
    }

    @Test
    void EntregaTest04(){
        LocalDate data = LocalDate.of(2021,5, 10 );
        Estafeta e = new Estafeta(2, "teste2", 35, 987654321, 2,60);
        Encomenda en = new Encomenda(1, 123456786);
        Encomenda en1 = new Encomenda(2, 123456787);
        Encomenda en2 = new Encomenda(3, 123456788);
        Encomenda en3 = new Encomenda(4, 123456789);
        Encomenda en4 = new Encomenda(5, 123456790);
        Encomenda en5 = new Encomenda(6, 123456791);
        List<Encomenda> l = new ArrayList<>(Arrays.asList(en, en1, en2, en3, en4, en5));
        Entrega e1 = new Entrega(l, data, e);

        Encomenda en6 = new Encomenda(7,1234678534);
        Produto produto = new Produto("Produto",2d,2.0,3,"adsa");
        Map<Produto,Integer> produtoIntegerMap = new TreeMap<>();
        produtoIntegerMap.put(produto,2);
        en6.setPeso(produtoIntegerMap);
        assertFalse(e1.addEncomenda(en1));


        assertEquals(l, e1.getListEncomendas());
        assertEquals(data, e1.getDataEntrega());
        assertEquals(e, e1.getEstafeta());
        List<Encomenda> empty = new ArrayList<>();
        boolean flag = e1.addEncomenda(empty);
        assertFalse(flag);
        empty.add(en5);
        flag = e1.addEncomenda(empty);
        assertTrue(flag);

        flag = e1.addEncomenda(en5);
        assertFalse(flag);

        assertTrue(e1.addEncomenda(en6));
    }

    @Test
    void EntregaTest05(){
        Encomenda en = new Encomenda(1, 123456780);
        Encomenda en1 = new Encomenda(2, 123456781);
        Encomenda en2 = new Encomenda(3, 123456782);
        Encomenda en3 = new Encomenda(4, 123456783);
        Encomenda en4 = new Encomenda(5, 123456784);
        Encomenda en5 = new Encomenda(6, 123456785);
        List<Encomenda> l = new ArrayList<>(Arrays.asList(en, en1, en2, en3, en4, en5));
        Entrega e = new Entrega(l);
        List<Encomenda> unexpected = new ArrayList<>(Arrays.asList(en, en1, en2));
        assertNotEquals(unexpected, e.getListEncomendas());
        assertEquals(l, e.getListEncomendas());
    }

    @Test
    void EntregaTest06(){
        Estafeta estafeta = new Estafeta(2, "teste2", 35, 987654321, 2,60);
        Estafeta estafeta1 = new Estafeta(1, "teste1", 33, 123456789, 1,55);
        Entrega e = new Entrega(estafeta);
        assertEquals(estafeta, e.getEstafeta());
        assertNotEquals(estafeta1, e.getEstafeta());
    }

    @Test
    void setEstafeta(){
        Estafeta estafeta = new Estafeta(2, "teste2", 35, 987654321, 2,60);
        Estafeta estafeta1 = new Estafeta(1, "teste1", 33, 123456789, 1,55);
        Entrega e = new Entrega(estafeta);
        assertEquals(estafeta, e.getEstafeta());
        assertNotEquals(estafeta1, e.getEstafeta());
        e.setEstafeta(estafeta1);
        assertNotEquals(estafeta, e.getEstafeta());
        assertEquals(estafeta1, e.getEstafeta());
    }

    @Test
    void setMeioTransporte(){
        Estafeta estafeta = new Estafeta(2, "teste2", 35, 987654321, 2,60);
        Scooter s = new Scooter(2, 23, 100, "FUNCIONAL");
        Drone d = new Drone(1,400,100,1.0,1.0,50.0,30d,10d,1d);
        Entrega e = new Entrega(estafeta, s);
        assertEquals(s, e.getMeioTransporte());
        assertNotEquals(d, e.getMeioTransporte());
        e.setMeioTransporte(d);
        assertEquals(d, e.getMeioTransporte());
        assertNotEquals(s, e.getMeioTransporte());
    }
}