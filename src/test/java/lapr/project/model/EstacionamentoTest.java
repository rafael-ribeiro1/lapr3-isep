package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstacionamentoTest {
   private final Estacionamento estacionamento1;

 public EstacionamentoTest() {
  estacionamento1 = new Estacionamento(1,2,2);
  estacionamento1.setId(1);
 }

 @Test
    void getCapacidadeMaxima() {
    int expected = 1;
    int result = estacionamento1.getCapacidadeMaximaScooters();

    assertEquals(expected,result);
    expected=2;
    assertNotEquals(expected,result);

    }

    @Test
    void getPotenciaMaxima() {
     double expected = 2;
     double result = estacionamento1.getPotenciaMaxima();

     assertEquals(expected,result);
     expected=3;
     assertNotEquals(expected,result);
    }

    @Test
    void getCapacidadeMaximaDrones() {
        int expected = 2;
        int result = estacionamento1.getCapacidadeMaximaDrones();

        assertEquals(expected,result);
        expected=1;
        assertNotEquals(expected,result);

    }

    @Test
    void getId(){
         assertEquals(1, estacionamento1.getId());
         assertNotEquals(4, estacionamento1.getId());
    }

    @Test
    void setId(){
        estacionamento1.setId(3);
        assertEquals(3, estacionamento1.getId());
        assertNotEquals(2, estacionamento1.getId());
    }
}