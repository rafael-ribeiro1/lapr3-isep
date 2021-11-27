package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuaTest {
    private Rua rua;

    @Test
    void getCoeficienteResistencia() {
        double coefResistencia=1;
        rua= new Rua("rua",1,1,1,coefResistencia,1);
        assertEquals(coefResistencia,rua.getCoeficienteResistencia ());
    }

    @Test
    void getInclinacao() {
        double inclinacao=1;
        rua= new Rua("rua",1,1,1,1,inclinacao);
        assertEquals(inclinacao,rua.getInclinacao ());
    }
    @Test
    void testEquals(){
        rua= new Rua("rua",1,1,1,1,2);
        assertEquals(rua,rua);

        boolean expected = false;
        String rua1 = null;
        boolean result = rua.equals(rua1);
        assertEquals(expected,result);
        Endereco en1 = null;
        result= rua.equals(en1);
        assertEquals(expected,result);
        Rua rua0 =  new Rua("rua2",1,1,1,1,2);
        result= rua.equals(rua0);
        assertEquals(expected,result);

        Rua rua2 =  new Rua("rua",1,1,1,1,2);
        expected=true;
        result=rua.equals(rua2);
        assertEquals(expected,result);

        expected= false;
        rua2 =  new Rua("rua",1,1,1,1,3);
        result=rua.equals(rua2);
        assertEquals(expected,result);
        rua2 =  new Rua("rua",1,1,1,2,3);
        result=rua.equals(rua2);
        assertEquals(expected,result);
        rua2 =  new Rua("rua",1,1,1,2,2);
        result=rua.equals(rua2);
        assertEquals(expected,result);

    }
    @Test
    void  hashCode1(){
        rua= new Rua("rua",1,1,1,1,2);
        int expected = -1913924638;
        int result = rua.hashCode();
        assertEquals(expected,result);
    }


}