package lapr.project.model;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private final Cliente cliente1;

    private final Cliente cliente2;

    private final Cliente cliente3;

    private final Cliente cliente4;

    public ClienteTest(){
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        cliente1 = new Cliente(1,"username","pass","email@gmail.com","nome",123456789,endereco,cartao);

        cliente2 = new Cliente("username","pass","email@gmail.com","nome",endereco,cartao);

        cliente3 = new Cliente("username","pass","email@gmail.com","nome","Rua","numPorta","codPostal","localidade","pais",100,100,1,"numero",1,dateValidade);

        cliente4 = new Cliente(1,"username","pass","email@gmail.com","nome",endereco,cartao);
    }

    @Test
    void getUsername() {
        String expected = "username";
        String result = cliente1.getUsername();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente2.getUsername();
        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente3.getUsername();
        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente4.getUsername();
        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

    }

    @Test
    void getPassword() {
        String expected = "pass";
        String result = cliente1.getPassword();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente2.getPassword();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente3.getPassword();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente4.getPassword();

        assertEquals(expected,result);

        result= "different";
        assertNotEquals(expected,result);


    }

    @Test
    void getEmail() {
        String expected = "email@gmail.com";
        String result = cliente1.getEmail();

        assertEquals(expected,result);

        expected = "different";
        assertNotEquals(expected,result);

        expected = "email@gmail.com";
        result = cliente2.getEmail();

        assertEquals(expected,result);

        expected = "different";
        assertNotEquals(expected,result);

        expected = "email@gmail.com";
        result = cliente3.getEmail();

        assertEquals(expected,result);

        expected = "different";
        assertNotEquals(expected,result);

        expected = "email@gmail.com";
        result = cliente4.getEmail();

        assertEquals(expected,result);

        expected = "different";
        assertNotEquals(expected,result);
    }

    @Test
    void getIdUser() {
        int expected = 1;
        int result = cliente1.getIdUser();

        assertEquals(expected,result);

        result = 2;
        assertNotEquals(expected,result);

        expected=0;
        result = cliente2.getIdUser();

        assertEquals(expected,result);

        result = 2;
        assertNotEquals(expected,result);


        result = cliente3.getIdUser();

        assertEquals(expected,result);

        result= 2;
        assertNotEquals(expected,result);
        expected=1;
        result = cliente4.getIdUser();

        assertEquals(expected,result);

        result = 2;
        assertNotEquals(expected,result);
    }

    @Test
    void getNome() {
        String expected = "nome";
        String result = cliente1.getNome();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente2.getNome();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente3.getNome();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);

        result = cliente4.getNome();

        assertEquals(expected,result);

        result = "different";
        assertNotEquals(expected,result);
    }

    @Test
    void getNif() {
        int expected = 123456789;
        int result = cliente1.getNif();

        assertEquals(expected,result);

        expected = -1;
        assertNotEquals(expected,result);

        expected=0;
        result=cliente2.getNif();
        assertEquals(expected,result);

        expected=0;
        result=cliente3.getNif();
        assertEquals(expected,result);

        expected=0;
        result=cliente4.getNif();
        assertEquals(expected,result);
    }

    @Test
    void getCreditos() {
        int expected =0;
        int result = cliente1.getCreditos();

        assertEquals(expected,result);

        result = -1;
        assertNotEquals(expected,result);

        result = cliente2.getCreditos();
        assertEquals(expected,result);


        result = cliente3.getCreditos();
        assertEquals(expected,result);

        expected=3;
        cliente4.setCreditos(3);
        result = cliente4.getCreditos();
        assertEquals(expected,result);
    }

    @Test
    void getEndereco() {
        Endereco expected = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        Endereco result = cliente1.getEndereco();

        assertEquals(expected,result);

        result = new Endereco(1,"Different","numPort1","codPostal","localidade","pais",100,100,1);

        assertNotEquals(expected,result);

        expected = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        result = cliente2.getEndereco();

        assertEquals(expected,result);

        result = cliente3.getEndereco();

        assertEquals(expected,result);

        result = cliente4.getEndereco();

        assertEquals(expected,result);
    }

    @Test
    void getCartaoCredito() {
        LocalDate dateValidade= LocalDate.of(2020,11,20);

        CartaoCredito expected = new CartaoCredito("numero",1,dateValidade);
        CartaoCredito result = cliente1.getCartaoCredito();
        assertEquals(expected,result);

        result = new CartaoCredito(2,"numero1",1,dateValidade);
        assertNotEquals(expected,result);

        result = cliente1.getCartaoCredito();
        assertEquals(expected,result);

        result = cliente3.getCartaoCredito();
        assertEquals(expected,result);

        result = cliente4.getCartaoCredito();
        assertEquals(expected,result);
    }

    @Test
    void getEstado(){
        assertEquals("Ativo", cliente1.getEstado().getDescricaoEstado());
        assertNotEquals("Removido", cliente1.getEstado().getDescricaoEstado());
    }

    @Test
    void setEstado(){
        cliente1.setEstado(Cliente.Estado.REMOVIDO);
        assertEquals("Removido", cliente1.getEstado().getDescricaoEstado());
        assertNotEquals("Ativo", cliente1.getEstado().getDescricaoEstado());
        cliente1.setEstado(Cliente.Estado.ATIVO);
        assertEquals("Ativo", cliente1.getEstado().getDescricaoEstado());
        assertNotEquals("Removido", cliente1.getEstado().getDescricaoEstado());
    }

    @Test
    void setCreditos() {
        int expected = 3;


        cliente1.setCreditos(3);
        int result = cliente1.getCreditos();
        assertEquals(expected,result);

        cliente2.setCreditos(3);
        result = cliente2.getCreditos();
        assertEquals(expected,result);

        cliente3.setCreditos(3);
        result = cliente3.getCreditos();
        assertEquals(expected,result);

        cliente4.setCreditos(3);
        result = cliente4.getCreditos();
        assertEquals(expected,result);

        expected=-2;
        assertNotEquals(expected,result);
    }

    @Test
    void testEquals() {
        assertEquals(cliente1, cliente1);
        assertNotEquals(cliente1, new Farmacia(1));
        Endereco endereco = new Endereco(1,"Rua","numPorta","codPostal","localidade","pais",100,100,1);
        LocalDate dateValidade= LocalDate.of(2020,11,20);
        CartaoCredito cartao = new CartaoCredito(1,"numero",1,dateValidade);
        assertNotEquals(cliente1, new Cliente(2,"username","pass","email@gmail.com","nome",987654321,endereco,cartao));
    }

    @Test
    void testHashCode() {
        int expected = 123456820;
        int result = cliente1.hashCode();
        assertEquals(expected, result);
    }
}