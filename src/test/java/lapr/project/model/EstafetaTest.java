package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EstafetaTest {

    private final Estafeta estafeta1;
    private final Estafeta estafeta2;
    private final Estafeta estafeta3;

    public EstafetaTest() {
        estafeta1 = new Estafeta("username","password","email","nome",20,20,20,60);
        estafeta2 = new Estafeta(1,"nome",20,20,20,60);
        estafeta3 = new Estafeta(2,"username","email","nome",20,20,20,60);
    }

    @Test
    void getUsername() {
        String expected = "username";
        String result = estafeta1.getUsername();
        assertEquals(expected,result);

        assertNull(estafeta2.getUsername());

    }

    @Test
    void getPassword() {

        String expected = "password";
        String result = estafeta1.getPassword();
        assertEquals(expected,result);

        assertNull(estafeta2.getPassword());
    }

    @Test
    void getTipoUser() {
        String expected = "estafeta";
        String result = estafeta1.getTipoUser();
        assertEquals(expected,result);

        assertNull(estafeta2.getTipoUser());
    }

    @Test
    void getEmail() {
        String expected = "email";
        String result = estafeta1.getEmail();
        assertEquals(expected,result);

        assertNull(estafeta2.getEmail());
    }

    @Test
    void getIdUser() {
        int expected = 0;
        int result = estafeta1.getIdUser();
        assertEquals(expected,result);
        expected=1;
        result= estafeta2.getIdUser();
        assertEquals(expected,result);
    }

    @Test
    void getCargaMaxima() {
        double expected =20;
        double result= estafeta1.getCargaMaxima();

        assertEquals(expected,result);

        result=estafeta2.getCargaMaxima();
        assertEquals(expected,result);
    }

    @Test
    void getNif() {
        int expected =20;
        int result= estafeta1.getNif();

        assertEquals(expected,result);

        result=estafeta2.getNif();
        assertEquals(expected,result);
    }

    @Test
    void getNome() {
        String expected = "nome";
        String result = estafeta1.getNome();

        assertEquals(expected,result);

        result=estafeta2.getNome();
        assertEquals(expected,result);
    }

    @Test
    void setNome() {
        String expected = "nome1";
        estafeta1.setNome("nome1");
        String result = estafeta1.getNome();
        assertEquals(expected,result);
        estafeta2.setNome("nome1");
        result=estafeta2.getNome();
        assertEquals(expected,result);
    }

    @Test
    void setCargaMaxima() {
        Double expected = 87.0;
        estafeta1.setCargaMaxima(87.0);
        Double result = estafeta1.getCargaMaxima();
        assertEquals(expected,result);
        estafeta2.setCargaMaxima(87.0);
        result=estafeta2.getCargaMaxima();
        assertEquals(expected,result);
    }

    @Test
    void setNif() {
        int expected = 21;
        estafeta1.setNif(21);
        int result = estafeta1.getNif();
        assertEquals(expected,result);
        estafeta2.setNif(21);
        result=estafeta2.getNif();
        assertEquals(expected,result);
    }

    @Test
    void setFarmacia() {
        Farmacia farmacia = new Farmacia(1);
        estafeta1.setFarmacia(farmacia);
        estafeta2.setFarmacia(farmacia);
        Farmacia result = estafeta2.getFarmacia();
        assertEquals(farmacia,result);
        result = estafeta1.getFarmacia();
        assertEquals(farmacia,result);
    }

    @Test
    void getFarmacia() {
        Farmacia farmacia = new Farmacia(1);
        estafeta1.setFarmacia(farmacia);
        estafeta2.setFarmacia(farmacia);
        Farmacia result = estafeta2.getFarmacia();
        assertEquals(farmacia,result);
        result = estafeta1.getFarmacia();
        assertEquals(farmacia,result);
    }

    @Test
    void equalsTest() {
        Estafeta e = estafeta1;
        Assertions.assertEquals(e, estafeta1);
        Assertions.assertNotEquals(null, estafeta1);
        Assertions.assertNotEquals(e, new Farmacia(1));

        Assertions.assertEquals(e, new Estafeta("username","password","email","nome",20,20,20,60));
        Assertions.assertNotEquals(e,new Estafeta("username","password","email","nome1",20,20,20,60));
        Assertions.assertNotEquals(e, new Estafeta("username","password","email","nome",23,20,20,60));
        Assertions.assertNotEquals(e, new Estafeta("username","password","email","nome",23,20,20,23));
        Assertions.assertNotEquals(e, new Estafeta("username","password","email","nome",20,123456789,20,60));
    }

    @Test
    void hashCodeTes() {
        int expected = Objects.hash(estafeta3.getNome(), estafeta3.getCargaMaxima(), estafeta3.getNif(), estafeta3.getFarmacia());
        int obtained = estafeta3.hashCode();
        assertEquals(expected, obtained);
    }

    @Test
    void getPeso() {
        Double expected = 60.0;
        Double result = estafeta1.getPeso();
        assertEquals(expected,result);
        result = estafeta2.getPeso();
        assertEquals(expected,result);
        result = estafeta3.getPeso();
        assertEquals(expected,result);
    }

    @Test
    void setPeso() {
        Double expected = 60.0;
        Double result = estafeta1.getPeso();
        assertEquals(expected,result);
        result = estafeta2.getPeso();
        assertEquals(expected,result);
        result = estafeta3.getPeso();
        assertEquals(expected,result);
        expected=40.0;
        estafeta1.setPeso(40);
        result = estafeta1.getPeso();
        assertEquals(expected,result);
        estafeta2.setPeso(40);
        result = estafeta2.getPeso();
        assertEquals(expected,result);
        estafeta3.setPeso(40);
        result = estafeta3.getPeso();
        assertEquals(expected,result);
    }

    @Test
    void testToString() {
        String expected ="nome pertencente a farmacia de id (20)";
        String result = estafeta1.toString();
        assertEquals(expected,result);
        result = estafeta2.toString();
        assertEquals(expected,result);
        result = estafeta3.toString();
        assertEquals(expected,result);
    }
}