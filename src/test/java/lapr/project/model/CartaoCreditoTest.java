package lapr.project.model;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CartaoCreditoTest {

    private final CartaoCredito cartaoCredito1;
    private final CartaoCredito cartaoCredito2;

    public CartaoCreditoTest(){

        LocalDate dateValidade = LocalDate.of(2020,11 , 20);
        cartaoCredito1 = new CartaoCredito(1,"12345",123,dateValidade);
        cartaoCredito2 = new CartaoCredito("12345",123,dateValidade);

    }

    @Test
    void getId() {
        int expected=1;
        int result= cartaoCredito1.getId();
        assertEquals(expected, result);

        expected=0;
        assertNotEquals(expected,result);

        expected=0;
        result= cartaoCredito2.getId();
        assertEquals(expected,result);

    }

    @Test
    void getNumero() {
        String expected="12345";
        String result= cartaoCredito1.getNumero();
        assertEquals(expected, result);

        expected = "";
        assertNotEquals(expected,result);

        expected="12345";
        result= cartaoCredito2.getNumero();
        assertEquals(expected, result);

        expected = "";
        assertNotEquals(expected,result);

    }

    @Test
    void getCcv() {
        int expected=123;
        int result= cartaoCredito1.getCcv();
        assertEquals(expected, result);

        expected=0;
        assertNotEquals(expected,result);

         expected=123;
         result= cartaoCredito2.getCcv();
         assertEquals(expected, result);

        expected=0;
        assertNotEquals(expected,result);
    }

    @Test
    void getDataValidade() {
        LocalDate expected = LocalDate.of(2020,11 , 20);
        LocalDate result = cartaoCredito1.getDataValidade();
        assertEquals(expected, result);

        result = LocalDate.of(2010,2 , 20);
        assertNotEquals(expected,result);

        result=cartaoCredito2.getDataValidade();
        assertEquals(expected, result);

        expected = LocalDate.of(2010,2 , 20);
        assertNotEquals(expected,result);


    }

    @Test
    void testEquals() {

        LocalDate dateValidade = LocalDate.of(2020,11 , 20);
        CartaoCredito expected = new CartaoCredito(1,"12344",123,dateValidade);
        CartaoCredito cartaoCredito1 = new CartaoCredito(2,"12333",123,dateValidade);
        CartaoCredito cartaoCredito2 = new CartaoCredito(1,"12344",123,dateValidade);
        CartaoCredito cartaoCredito3 = new CartaoCredito(1,"12344",133,dateValidade);
        LocalDate dateValidade1 = LocalDate.of(2020,12 , 20);
        CartaoCredito cartaoCredito4 = new CartaoCredito(1,"12344",133,dateValidade1);
        //objetos iguais
        assertEquals(expected,expected);

        //objetos classes diferentes
        assertNotEquals(expected,dateValidade);

        //assertThrows(NullPointerException.class,() ->assertNotEquals(expected,null));

        //objetos da mesma classe ids diferentes
        assertNotEquals(expected,cartaoCredito1);

        assertNotEquals(cartaoCredito3,cartaoCredito2);

        assertNotEquals(cartaoCredito3,cartaoCredito4);

        //objetos da mesma classe ids iguais
        assertEquals(expected,cartaoCredito2);
    }

    @Test
    void hashCodeTest(){
        LocalDate dateValidade = LocalDate.of(2020,11 , 20);
        CartaoCredito cartaoCredito1 = new CartaoCredito(2,"12333",123,dateValidade);
        int expected = Objects.hash(cartaoCredito1.getNumero(), cartaoCredito1.getCcv(), cartaoCredito1.getDataValidade());
        assertEquals(expected, cartaoCredito1.hashCode());

    }

}
