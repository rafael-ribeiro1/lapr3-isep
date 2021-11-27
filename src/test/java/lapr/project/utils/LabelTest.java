package lapr.project.utils;

import static org.junit.jupiter.api.Assertions.*;
import lapr.project.model.Endereco;
import lapr.project.model.Rua;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class LabelTest {


    @Test
    public void construtorTeste(){
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        double energiaGasta=1, energiaRestante=1, distancia=1;
        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", FisicaAlgoritmos.distancia ( e1,e2 ), 0,0, 0.004, FisicaAlgoritmos.angulo ( e1,e2 ) );
        Label l=new Label ( e1,e2,energiaGasta,energiaRestante,r1 );
        assertNotNull ( l );
    }

    @Test
    void getTeste(){
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        double energiaGasta=1, energiaRestante=1, distancia=1;
        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", distancia, 0,0, 0.004, FisicaAlgoritmos.angulo ( e1,e2 ) );
        Label l=new Label ( e1,e2,energiaGasta,energiaRestante,r1 );

        assertEquals ( energiaGasta,l.getEnergiaGasta () );
        assertEquals ( energiaRestante,l.getEnergiaRestante () );
        assertEquals ( distancia,l.getDistancia () );
    }

    @Test
    void equalsTeste(){
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 232344.001, 6734, 1);
        double energiaGasta=1, energiaRestante=1;
        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", FisicaAlgoritmos.distancia ( e1,e2 ), 0,0, 0.004, FisicaAlgoritmos.angulo ( e1,e2 ) );
        Label l1=new Label ( e1,e2,energiaGasta,energiaRestante,r1 );

        assertEquals(l1,l1);

        assertNotEquals(l1,null);

        assertNotEquals(l1, new Endereco ());
        Label l2=new Label ( e1,e2,energiaGasta,energiaRestante,r1 );

        assertEquals ( l1, l2 );

        l2=new Label ( e1,e1,energiaGasta,energiaRestante,r1);
        assertNotEquals ( l1,l2 );

        l2=new Label ( e1,e2,0d,energiaRestante,r1 );
        assertNotEquals ( l1,l2 );

        Rua r2 = new Rua ( "Trindade - Cais da Ribeira", 56, 0,0, 1, FisicaAlgoritmos.angulo ( e1,e2 ) );
        l2=new Label ( e1,e2,energiaGasta,0d,r1 );
        assertNotEquals ( l1,l2 );

        l2=new Label ( e1,e2,energiaGasta,energiaRestante,r2);
        assertNotEquals ( l1,l2 );
    }

    @Test
    void hashCodeTeste(){
        Endereco e1 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 234.001, 234, 1);
        Endereco e2 = new Endereco("Endereco1", "n1", "42342", "234", "wetr", 232344.001, 6734, 1);
        double energiaGasta=1, energiaRestante=1;
        Rua r1 = new Rua ( "Trindade - Cais da Ribeira", FisicaAlgoritmos.distancia ( e1,e2 ), 0,0, 0.004, FisicaAlgoritmos.angulo ( e1,e2 ) );

        Label l1=new Label ( e1,e2,energiaGasta,energiaRestante,r1 );

        int expected= Objects.hash (l1.getEnderecoInicio (),l1.getEnderecoFim (),l1.getEnergiaGasta (),l1.getEnergiaRestante (),r1);
        int result=l1.hashCode ();
        
        assertEquals ( expected,result );
    }
}