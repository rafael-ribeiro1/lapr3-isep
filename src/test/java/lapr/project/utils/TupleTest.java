package lapr.project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TupleTest {

    @Test
    void construtorTeste(){
        Tuple<Double,Double,Double> t=new Tuple<> ( 1d,1d,1d );
        assertNotNull ( t );
    }

    @Test
    void createTeste(){
        Tuple<Double,Double,Double> t=Tuple.create ( 1d,1d,1d );
        assertNotNull ( t );
    }

    @Test
    void getTeste(){
        double r=1, s=1, t=1;
        Tuple<Double,Double,Double> tp=Tuple.create ( r,s,t );
        assertEquals ( r,tp.get1st () );
        assertEquals ( s,tp.get2nd () );
        assertEquals ( t,tp.get3rd () );
    }
}