package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FarmaciaTest {

    private final Farmacia farmacia1;
    private final Farmacia farmacia2;
    private final Farmacia farmacia3;
    private final Farmacia farmacia4;
    private final Farmacia farmacia5;
    private final Farmacia farmacia6;
    public FarmaciaTest() {
        farmacia1 = new Farmacia(1);
        Endereco endereco = new Endereco("a","a","a","a","a",1,1,1);
        farmacia2 = new Farmacia("nome",endereco);
        farmacia3 = new Farmacia(1,"nome");
        farmacia4 = new Farmacia(1,endereco);
        farmacia5 = new Farmacia(5);
        farmacia6 = new Farmacia(1, "nome", endereco);
    }

    @Test
    void setEstacionamento() {
        Estacionamento expected = new Estacionamento(1,1,1);
        farmacia1.setEstacionamento(expected);
        Estacionamento result = farmacia1.getEstacionamento();
        assertEquals(expected,result);
        farmacia2.setEstacionamento(expected);
        farmacia3.setEstacionamento(expected);
        result = farmacia2.getEstacionamento();
        assertEquals(expected,result);
        result = farmacia3.getEstacionamento();
        assertEquals(expected,result);
        farmacia4.setEstacionamento(expected);
        result = farmacia4.getEstacionamento();
        assertEquals(expected,result);
    }

    @Test
    void getId() {
        int expected = 1;
        int result = farmacia1.getId();
        assertEquals(expected,result);
        result = farmacia3.getId();
        assertEquals(expected,result);
        expected=0;
        result= farmacia2.getId();
        assertEquals(expected,result);

        assertNotEquals(expected,farmacia1.getId());
        expected = 1;
        result= farmacia4.getId();
        assertEquals(expected,result);
        expected=0;
        assertNotEquals(expected,farmacia1.getId());

        farmacia1.setId(2);
        expected=2;
        assertEquals(expected,2);
    }

    @Test
    void getNome() {
        assertNull(farmacia1.getNome());
        String expected = "nome";
        String result = farmacia2.getNome();
        assertEquals(expected,result);
        result =farmacia3.getNome();
        assertEquals(expected,result);
        assertNull(farmacia4.getNome());
    }

    @Test
    void getEstacionamento() {
        assertNull(farmacia1.getEstacionamento());
        assertNull(farmacia2.getEstacionamento());
        assertNull(farmacia3.getEstacionamento());
        assertNull(farmacia4.getEstacionamento());
        Estacionamento expected = new Estacionamento(1,1,1);
        farmacia1.setEstacionamento(expected);
        Estacionamento result = farmacia1.getEstacionamento();
        assertEquals(expected,result);
        farmacia2.setEstacionamento(expected);
        farmacia3.setEstacionamento(expected);
        result = farmacia2.getEstacionamento();
        assertEquals(expected,result);
        result = farmacia3.getEstacionamento();
        assertEquals(expected,result);
        farmacia4.setEstacionamento(expected);
        result = farmacia4.getEstacionamento();
        assertEquals(expected,result);
    }

    @Test
    void getEndereco() {
        Endereco expected = new Endereco("a","a","a","a","a",1,1,1);
        Endereco result = farmacia2.getEndereco();
        assertEquals(expected,result);
        assertNull(farmacia1.getEndereco());
        assertNull(farmacia1.getEndereco());
        result = farmacia4.getEndereco();
        assertEquals(expected,result);
    }

    @Test
    void testToString() {
        assertNull(farmacia1.toString());
        assertEquals(farmacia2.getNome(),farmacia2.toString());
        assertEquals(farmacia3.getNome(),farmacia3.toString());
        assertNull(farmacia4.toString());
    }

    @Test
    void compareTo() {
      Farmacia  farmacia1 = new Farmacia(1);
        Endereco endereco = new Endereco("a","a","a","a","a",1,1,1);
       Farmacia farmacia2 = new Farmacia("nome",endereco);
       Farmacia farmacia3 = new Farmacia(1,"aome");

        assertThrows(NullPointerException.class,() ->farmacia1.compareTo(farmacia2));
        assertThrows(NullPointerException.class,() ->farmacia1.compareTo(farmacia3));
        assertThrows(NullPointerException.class,() ->farmacia4.compareTo(farmacia3));

         int expected = -13 ;
         assertEquals(expected,farmacia3.compareTo(farmacia2));

         expected = 13;
         assertEquals(expected,farmacia2.compareTo(farmacia3));

        expected = 0;
        assertEquals(expected,farmacia2.compareTo(farmacia2));
    }

    @Test
    void testEquals() {
        Endereco endereco = new Endereco("a","a","a","a","a",1,1,1);
        Farmacia farmacia2 = new Farmacia("nome",endereco);
        Farmacia farmacia3 = new Farmacia(1,"nome2");
        Farmacia farmacia4 = new Farmacia(1,"a");
        Farmacia f = new Farmacia(1, "nome");
        assertEquals(farmacia2,farmacia2);
        double random = 1 ;
        assertNotEquals(farmacia2,random);
        assertNotEquals(farmacia2,farmacia3);
        assertNotEquals(farmacia2,farmacia4);
        assertEquals(farmacia6,f);
        assertNotEquals(farmacia2,farmacia4);
        assertEquals(farmacia4,farmacia4);
        assertNotEquals(farmacia1,farmacia5);
        Farmacia faNull = null;
        String f123 = null;
        assertNotEquals(faNull,farmacia4);
        assertNotEquals(f123,farmacia2);

    }

    @Test
    void setNome() {
        assertNull(farmacia1.getNome());
        String expected = "nome";
        String result = farmacia2.getNome();
        assertEquals(expected,result);
        result =farmacia3.getNome();
        assertEquals(expected,result);
        assertNull(farmacia4.getNome());
        farmacia1.setNome(expected);
        farmacia4.setNome(expected);
        result =farmacia1.getNome();
        assertEquals(expected,result);
        result =farmacia4.getNome();
        assertEquals(expected,result);

    }

    @Test
    void setEndereco() {
        Endereco expected = new Endereco("a","a","a","a","a",1,1,1);
        Endereco result = farmacia2.getEndereco();
        assertEquals(expected,result);
        assertNull(farmacia1.getEndereco());
        farmacia1.setEndereco(expected);
        result = farmacia1.getEndereco();
        assertEquals(expected,result);
        assertNull(farmacia3.getEndereco());
        farmacia3.setEndereco(expected);
        result = farmacia3.getEndereco();
        assertEquals(expected,result);
        result = farmacia4.getEndereco();
        assertEquals(expected,result);
    }

    @Test
    void hashCodeTest() {
        int expected = 992;
        int result = farmacia1.hashCode();
        assertEquals(expected,result);

    }
}