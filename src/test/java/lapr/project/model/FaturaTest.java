package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaturaTest {
    private Fatura fatura1;
    private Fatura fatura2;

    public FaturaTest() {
        fatura1 = new Fatura(1,1,1);
        fatura2 = new Fatura(1,1);
    }

    @Test
    void getId() {
        int expected = 1 ;
        int result = fatura1.getId();
        assertEquals(expected,result);
        result=fatura2.getId();
        expected=0;
        assertEquals(expected,result);
    }

    @Test
    void getNif() {
        int expected = 1 ;
        int result = fatura1.getNif();
        assertEquals(expected,result);
        result=fatura2.getNif();
        assertEquals(expected,result);
    }

    @Test
    void getValorTotal() {
        Double expected = 1.0 ;
        Double result = fatura1.getValorTotal();
        assertEquals(expected,result);
        result=fatura2.getValorTotal();
        assertEquals(expected,result);
    }
}