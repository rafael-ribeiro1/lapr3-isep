package lapr.project.model;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    private final Endereco endereco1;
    private final Endereco endereco2;

    public EnderecoTest() {
        endereco1 = new Endereco(1, "rua", "numPorta", "codPostal", "localidade", "pais", 200, 201, 0);
        endereco2 = new Endereco("rua", "numPorta", "codPostal", "localidade", "pais", 200, 201, 0);
    }


    @Test
    void enderecoTest(){
        //int id,String rua, String numPorta, String codPostal, String localidade, String pais, double latitude, double longitude,double altitude, boolean isFarmacia
        Endereco e = new Endereco(1, "Rua", "1", "5400-234", "Localidade", "Pais",
                34.5, 45.6, 98.3, true);
        assertEquals(98.3, e.getAltitude());
        assertEquals(34.5, e.getLatitude());
        assertEquals(45.6, e.getLongitude());
        assertEquals("Rua", e.getRua());
        assertEquals("1", e.getNumPorta());
        assertEquals("5400-234", e.getCodPostal());
        assertEquals("Localidade", e.getLocalidade());
        assertEquals("Pais", e.getPais());
        assertEquals(1, e.getId());

    }

    @Test
    void getId() {
        int expected = 1;
        int result = endereco1.getId();
        assertEquals(expected, result);

        expected = 0;
        result = endereco2.getId();
        assertEquals(expected, result);
    }

    @Test
    void getRua() {
        String expected = "rua";
        String result = endereco1.getRua();
        assertEquals(expected, result);

        result = endereco2.getRua();
        assertEquals(expected, result);
    }

    @Test
    void getNumPorta() {
        String expected = "numPorta";
        String result = endereco1.getNumPorta();
        assertEquals(expected, result);

        result = endereco2.getNumPorta();
        assertEquals(expected, result);
    }

    @Test
    void getCodPostal() {
        String expected = "codPostal";
        String result = endereco1.getCodPostal();
        assertEquals(expected, result);

        result = endereco2.getCodPostal();
        assertEquals(expected, result);
    }

    @Test
    void getLocalidade() {
        String expected = "localidade";
        String result = endereco1.getLocalidade();
        assertEquals(expected, result);

        result = endereco2.getLocalidade();
        assertEquals(expected, result);
    }

    @Test
    void getPais() {
        String expected = "pais";
        String result = endereco1.getPais();
        assertEquals(expected, result);

        result = endereco2.getPais();
        assertEquals(expected, result);
    }

    @Test
    void getLatitude() {
        double expected = 200;
        double result = endereco1.getLatitude();

        assertEquals(expected, result);

        result = endereco2.getLatitude();
        assertEquals(expected, result);

    }

    @Test
    void getLongitude() {
        double expected = 201;
        double result = endereco1.getLongitude();

        assertEquals(expected, result);

        result = endereco2.getLongitude();
        assertEquals(expected, result);
    }

    @Test
    void getAltitude() {
        double expected = 0;
        double result = endereco1.getAltitude();
        assertEquals(expected, result);
    }


    @Test
    void setId() {
        int expected = 3;
        endereco1.setId(3);
        int result = endereco1.getId();
        assertEquals(expected, result);

        endereco2.setId(3);
        result = endereco2.getId();
        assertEquals(expected, result);

        expected = -2;
        assertNotEquals(expected, result);
    }


    @Test
    void setRua() {
        String expected = "rua1";
       endereco1.setRua("rua1");
       String result=endereco1.getRua();
       assertEquals(expected,result);

        endereco2.setRua("rua1");
        result= endereco2.getRua();

        expected="rua4";
        assertNotEquals(expected,result);
    }

    @Test
    void setNumPorta() {
        String expected = "numeroPorta";
        endereco1.setNumPorta("numeroPorta");
        String result=endereco1.getNumPorta();
        assertEquals(expected,result);

        endereco2.setNumPorta("numeroPorta");
        result= endereco2.getNumPorta();

        expected="numInv";
        assertNotEquals(expected,result);
    }

    @Test
    void setCodPostal(){
        String expected= "CodigoPostalTeste";
        endereco1.setCodPostal("CodigoPostalTeste");
        String result = endereco1.getCodPostal();
        assertEquals(expected,result);

        endereco2.setCodPostal("CodigoPostalTeste");
        result= endereco2.getCodPostal();
        assertEquals(expected,result);

        expected="codPostalInv";
        assertNotEquals(expected,result);

    }
    @Test
    void setLocalidade(){
        String expected= "localidade";
        endereco1.setLocalidade("localidade");
        String result = endereco1.getLocalidade();
        assertEquals(expected,result);

        endereco2.setLocalidade("localidade");
        result= endereco2.getLocalidade();
        assertEquals(expected,result);

        expected="localidadeInv";
        assertNotEquals(expected,result);
    }

    @Test
    void setPais(){
        String expected= "pais";
        endereco1.setPais("pais");
        String result = endereco1.getPais();
        assertEquals(expected,result);

        endereco2.setPais("pais");
        result= endereco2.getPais();
        assertEquals(expected,result);

        expected="paisInv";
        assertNotEquals(expected,result);
    }


    @Test
    void setLatitude(){
       double expect = 2.0;
       endereco1.setLatitude(2.0);
       double result= endereco1.getLatitude();
       assertEquals(expect,result);

       endereco2.setLatitude(2.0);
       result=endereco2.getLatitude();
       assertEquals(expect,result);

       expect=4.0;
       assertNotEquals(expect,result);
    }

    @Test
    void setLongitude(){
        double expect = 2.0;
        endereco1.setLongitude(2.0);
        double result= endereco1.getLongitude();
        assertEquals(expect,result);

        endereco2.setLongitude(2.0);
        result=endereco2.getLongitude();
        assertEquals(expect,result);

        expect=4.0;
        assertNotEquals(expect,result);
    }


    @Test
    void setAltitude(){
        double expect = 2.0;
        endereco1.setAltitude(2.0);
        double result= endereco1.getAltitude();
        assertEquals(expect,result);

        endereco2.setAltitude(2.0);
        result=endereco2.getAltitude();
        assertEquals(expect,result);

        expect=4.0;
        assertNotEquals(expect,result);
    }
    
    @Test
        void compareTo () {
            Endereco endereco2 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 201, 201, 1);
            Endereco endereco3 = new Endereco(2, "rua", "numPorta", "codPostal", "localidade", "pais", 200, 201, 1);

            int expected = 1;
            int result = endereco2.compareTo(endereco3);
            assertEquals(expected, result);

            expected = -1;
            result = endereco3.compareTo(endereco2);
            assertEquals(expected, result);
            endereco3 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 201, 200, 1);
            result = endereco3.compareTo(endereco2);
            assertEquals(expected, result);

            expected = 1;
            result = endereco2.compareTo(endereco3);
            assertEquals(expected, result);

            expected = 0;
            result = endereco2.compareTo(endereco2);
            assertEquals(expected, result);

        }
        @Test
        void isFarmaciaTeste(){
            boolean isFarmacia=true;
            Endereco endereco = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 200, 201, 1,isFarmacia);
            assertEquals ( isFarmacia,endereco.isFarmacia () );
        }

        @Test
        void testEquals () {
            Endereco endereco2 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 200, 201, 1);
            Endereco endereco3 = new Endereco(2, "rua", "numPorta", "codPostal", "localidade", "pais", 200, 201, 1);
            Endereco endereco4 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 201, 201, 1);
            Endereco endereco5 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais", 200, 202, 1);
            Endereco endereco6 = new Endereco("rua1", "numPorta", "codPostal", "localidade", "pais1", 200, 202, 1);
            Endereco endereco7 = new Endereco("rua1", "numPorta", "codPostal", "localidade1", "pais1", 200, 202, 1);
            Endereco endereco8 = new Endereco("rua1", "numPorta", "codPostal1", "localidade1", "pais1", 200, 202, 1);
            Endereco endereco9 = new Endereco("rua1", "numPorta1", "codPostal1", "localidade1", "pais1", 200, 202, 1);
            assertEquals(endereco3, endereco3);

            assertNotEquals(endereco1, "candido");

            assertNotEquals(endereco1, endereco2);

            assertNotEquals(endereco2, endereco4);

            assertNotEquals(endereco5, endereco2);

            assertNotEquals(endereco5, endereco6);

            assertNotEquals(endereco6, endereco7);

            assertNotEquals(endereco8, endereco7);

            assertNotEquals(endereco9, endereco8);

            assertEquals(endereco3, endereco1);


        }

        @Test
        void hashCodeTest(){
            int expected = Objects.hash(endereco1.getRua(), endereco1.getNumPorta(), endereco1.getCodPostal(), endereco1.getLocalidade(),
                    endereco1.getPais(), endereco1.getLatitude(), endereco1.getLongitude(),endereco1.getAltitude());
            assertEquals(expected, endereco1.hashCode());
        }

    }
