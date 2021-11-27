package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaminhoTest {
    private Caminho caminho ;

    public CaminhoTest() {
        caminho = new Caminho("a",10.0,1,1);
    }

    @Test
    void getDescricaoTeste() {
        String expected = "a";
        assertEquals(expected,caminho.getDescricao());
    }

    @Test
    void getComprimentoTeste() {
        assertEquals(10.0,caminho.getComprimento());
    }

    @Test
    void getVelocidadeVentoTeste() {
        assertEquals(1,caminho.getVelocidadeVento());
    }

    @Test
    void getAnguloVentoTeste(){

        assertEquals ( 1,caminho.getAnguloVento () );
    }

    @Test
    void EqualsMethod (){
        Caminho c =  new Caminho("a",10.0,1,1);
        assertEquals(c,c);
        assertEquals(c,new Caminho("a",10.0,1,1));
        assertNotEquals(c, new Scooter(1, 23, 70, "FUNCIONAL"));
        assertNotEquals(c,new Caminho("b",10.0,1,1));
        assertNotEquals(c,new Caminho("a",15.0,1,1));
        assertNotEquals(c,new Caminho("a",10.0,2,1));
        assertNotEquals(c,new Caminho("a",10.0,1,6));
        assertNotEquals(c,new Caminho("a",10.0,2,4));
        assertNotEquals(c,new Caminho("b",12.0,1,1));
        assertNotEquals(c,new Caminho("b",10.0,4,1));
        assertNotEquals(c,new Caminho("a",14.0,1,2));

    }
}