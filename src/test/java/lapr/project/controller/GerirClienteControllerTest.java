package lapr.project.controller;

import lapr.project.data.*;
import lapr.project.model.CartaoCredito;
import lapr.project.model.Cliente;
import lapr.project.model.Endereco;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GerirClienteControllerTest {

    private GerirClienteController controller;
    private ClientesHandler mock;
    private EmailHandler emailHandler;
    
    public GerirClienteControllerTest() {
        try{
            this.controller =new GerirClienteController();
            this.mock= Mockito.mock(ClientesHandler.class);
            this.emailHandler = Mockito.mock(EmailHandler.class);
            controller.setHandler(mock);
            controller.setEmailHandler(emailHandler);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Test
    void novoClienteInValido() {
        LocalDate date = LocalDate.of(2031, 11,20 );
        CartaoCredito cartaoCredito = new CartaoCredito("1",1,date);
        Endereco endereco = new Endereco("rua", "numPorta", "codPostal", "localidade", "pais", -1, -1,-1);
        Cliente cliente = new Cliente(-1,"username","password","email@gmail.com","nome",endereco,cartaoCredito);
        Mockito.when ( mock.addNovoCliente(cliente)).thenReturn(false);
        boolean result = controller.novoCliente("username","password","123132.com","nome","1",1,date,"rua", "numPorta", "codPostal", "localidade", "pais", -230, 100,100);
        Mockito.verify(mock, Mockito.times(1)).addNovoCliente(cliente);
        assertFalse(result);

    }

    @Test
    void novoClienteInvalido01() {
        //id = 0
        LocalDate date = LocalDate.of(2029, 11,20 );
        CartaoCredito cartaoCredito = new CartaoCredito("1",1,date);
        Endereco endereco = new Endereco("rua", "numPorta", "codPostal", "localidade", "pais", 2, 2,2);
        Cliente cliente = new Cliente(0,"username","password","email@gmail.com","nome",endereco,cartaoCredito);
        String assunto = String.format("Registo na platforma - Senhor cliente %s", cliente.getNome());
        String conteudo= String.format("Obrigado %s  pelo seu registo na platforma. %n------DADOS PESSOAIS REGISTADO------%n----------------------------------------%nUsername: %s%nemail: %s%npassword: %s%n----------------------------------------%n    Grupo 24 LAPR3",
                cliente.getNome(),cliente.getUsername(),cliente.getEmail(),cliente.getPassword());

        Mockito.when (emailHandler.sendEmail(cliente.getEmail(), assunto, conteudo)).thenReturn(false);
        boolean result = controller.novoCliente("username","password","email@gmail.com","nome","1",1,date,"rua", "numPorta", "codPostal", "localidade", "pais", 2, 2,2);
        assertFalse(result);
        Mockito.when(mock.addNovoCliente(cliente)).thenReturn(true);
         result = controller.novoCliente("username","password","email@gmail.com","nome","1",1,date,"rua", "numPorta", "codPostal", "localidade", "pais", 2, 2,2);
        assertTrue(result);
    }

    @Test
    void novoClienteValido() {
        LocalDate date = LocalDate.of(2029, 11,20 );
        CartaoCredito cartaoCredito = new CartaoCredito("3",3,date);
        Endereco endereco = new Endereco("rua", "numPorta", "codPostal", "localidade", "pais", 2, 2,2);
        Cliente cliente = new Cliente(1,"username1","password","email@gmail.com","nome",endereco,cartaoCredito);
        String assunto = String.format("Registo na platforma - Senhor cliente %s", cliente.getNome());
        String conteudo= String.format("Obrigado %s  pelo seu registo na platforma. %n------DADOS PESSOAIS REGISTADO------%n----------------------------------------%nUsername: %s%nemail: %s%npassword: %s%n----------------------------------------%n    Grupo 24 LAPR3",
                cliente.getNome(),cliente.getUsername(),cliente.getEmail(),cliente.getPassword());
        Mockito.when(mock.addNovoCliente(cliente)).thenReturn(true);
        Mockito.when(emailHandler.sendEmail(cliente.getEmail(), assunto, conteudo)).thenReturn(true);
        boolean result = controller.novoCliente("username","password","email@gmail.com","nome","3",3,date,"rua", "numPorta", "codPostal", "localidade", "pais", 2, 2,2);
        assertTrue(result);
    }

    @Test
    void atualizarCartaoCreditoTest01(){
        //Válido
        CartaoCredito c = new CartaoCredito("11223344556678", 12345, LocalDate.of(2023, 12, 21));
        Mockito.when(mock.atualizarCartaoCredito(c.getNumero(), c.getCcv(), c.getDataValidade(), c.getId())).thenReturn(true);
        boolean flag = controller.atualizarCartaoCredito(c.getNumero(), c.getCcv(), c.getDataValidade(), c.getId());
        Mockito.verify(mock, Mockito.times(1)).atualizarCartaoCredito(c.getNumero(), c.getCcv(), c.getDataValidade(), c.getId());
        assertTrue(flag);
    }

    @Test
    void atualizarCartaoCreditoTest02(){
        //Data inválida
        CartaoCredito c1 = new CartaoCredito("9988898989", 90909, LocalDate.of(2019, 12, 21));
        Mockito.when(mock.atualizarCartaoCredito(c1.getNumero(), c1.getCcv(), c1.getDataValidade(), c1.getId())).thenReturn(false);
        boolean obtained = controller.atualizarCartaoCredito(c1.getNumero(), c1.getCcv(), c1.getDataValidade(), c1.getId());
        Mockito.verify(mock, Mockito.times(0)).atualizarCartaoCredito(c1.getNumero(), c1.getCcv(), c1.getDataValidade(), c1.getId());
        assertFalse(obtained);
    }

    @Test
    void atualizarCartaoCreditoTest03(){
        //Um possível erro na ligação à BD
        CartaoCredito c1 = new CartaoCredito("9988898989", 90909, LocalDate.of(2024, 12, 21));
        Mockito.when(mock.atualizarCartaoCredito(c1.getNumero(), c1.getCcv(), c1.getDataValidade(), c1.getId())).thenReturn(false);
        boolean obtained = controller.atualizarCartaoCredito(c1.getNumero(), c1.getCcv(), c1.getDataValidade(), c1.getId());
        Mockito.verify(mock, Mockito.times(1)).atualizarCartaoCredito(c1.getNumero(), c1.getCcv(), c1.getDataValidade(), c1.getId());
        assertFalse(obtained);
    }

    @Test
    void getInformacaoClienteTest01(){
        //Id válido > 0
        CartaoCredito c = new CartaoCredito("234567890", 12345, LocalDate.of(2023, 10, 21));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(1,"username1","password","email1@gmail.com","nome1",1122233344, e,c);
        Mockito.when(mock.getInformacaoCliente(cl.getIdUser())).thenReturn(cl);
        Cliente obtained = controller.getInformacaoCliente(1);
        Mockito.verify(mock, Mockito.times(1)).getInformacaoCliente(1);
        assertEquals(cl, obtained);
    }

    @Test
    void getInformacaoClienteTest02(){
        //Id inválido < 0
        CartaoCredito c = new CartaoCredito("234567890", 12345, LocalDate.of(2023, 10, 21));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(-1,"username1","password","email1@gmail.com","nome1",1122233344, e,c);
        Cliente obtained = controller.getInformacaoCliente(cl.getIdUser());
        Mockito.verify(mock, Mockito.times(0)).getInformacaoCliente(cl.getIdUser());
        assertNull(obtained);
    }

    @Test
    void getInformacaoClienteTest03(){
        //Id Inválido = 0
        CartaoCredito c = new CartaoCredito("234567890", 12345, LocalDate.of(2023, 10, 21));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(0,"username0","password","email0@gmail.com","nome0",567567567, e,c);
        Mockito.when(mock.getInformacaoCliente(cl.getIdUser())).thenReturn(cl);
        Cliente obtained = controller.getInformacaoCliente(0);
        Mockito.verify(mock, Mockito.times(0)).getInformacaoCliente(0);
        assertNull(obtained);
    }

    @Test
    void getInformacaoClienteTest04(){
        //Um possível erro na ligação à BD
        CartaoCredito c = new CartaoCredito("234567890", 12345, LocalDate.of(2023, 10, 21));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(1,"username0","password","email0@gmail.com","nome0",567567567, e,c);
        Mockito.when(mock.getInformacaoCliente(cl.getIdUser())).thenReturn(null);
        Cliente obtained = controller.getInformacaoCliente(1);
        Mockito.verify(mock, Mockito.times(1)).getInformacaoCliente(1);
        assertNull(obtained);
    }

    @Test
    void atualizarInformacaoClienteTest01(){
        //Id Válido > 0
        CartaoCredito c = new CartaoCredito("234567890", 12345, LocalDate.of(2023, 10, 21));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(1,"username1","password","email1@gmail.com","nome1",1122233344, e,c);
        Mockito.when(mock.atualizarInformacaoCliente(cl.getIdUser(),cl.getNome(), e)).thenReturn(true);
        boolean obtained = controller.atualizarInformacaoCliente(1,"nome1", new Endereco("RuaX",
                "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5));
        Mockito.verify(mock, Mockito.times(1)).atualizarInformacaoCliente(cl.getIdUser(),cl.getNome(), e);
        assertTrue(obtained);
    }

    @Test
    void atualizarInformacaoClienteTest02(){
        //Id Inválido < 0
        Endereco e = new Endereco("rua", "12",
                "4200-345", "LLLLL", "PPP", 234.3, 2.9,5);
        boolean obtained = controller.atualizarInformacaoCliente(-1, "A", e);
        Mockito.verify(mock, Mockito.times(0)).atualizarInformacaoCliente(-1, "A", e);
        assertFalse(obtained);
    }

    @Test
    void atualizarInformacaoClienteTest03(){
        //Id Inválido = 0
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Mockito.when(mock.atualizarInformacaoCliente(0,"teste", e)).thenReturn(false);
        boolean obtained = controller.atualizarInformacaoCliente(0,"teste", new Endereco("RuaX",
                "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5));
        Mockito.verify(mock, Mockito.times(0)).atualizarInformacaoCliente(0,"teste", e);
        assertFalse(obtained);
    }

    @Test
    void atualizarInformacaoClienteTest04(){
        //Um possível erro na ligação à BD
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Mockito.when(mock.atualizarInformacaoCliente(1,"nome1", e)).thenReturn(false);
        boolean obtained = controller.atualizarInformacaoCliente(1,"nome1", new Endereco("RuaX",
                "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5));
        Mockito.verify(mock, Mockito.times(1)).atualizarInformacaoCliente(1,"nome1", e);
        assertFalse(obtained);
    }


    @Test
    void removerPerfilClienteTest(){
        //Id Válido > 0
        CartaoCredito c = new CartaoCredito("234567890", 12345, LocalDate.of(2023, 10, 21));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(1,"username1","password","email1@gmail.com","nome1",1122233344, e,c);
        Mockito.when(mock.removerPerfilCliente(cl.getIdUser())).thenReturn(true);
        boolean obtained = controller.removerPerfilCliente(cl.getIdUser());
        Mockito.verify(mock, Mockito.times(1)).removerPerfilCliente(cl.getIdUser());
        assertTrue(obtained);
    }

    @Test
    void removerPerfilClienteTest01(){
        //Id Inválido < 0
        CartaoCredito c = new CartaoCredito("000000000", 980, LocalDate.of(2025, 2, 13));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(-1,"username","password","email@gmail.com","nome",957463201, e,c);
        Mockito.when(mock.removerPerfilCliente(cl.getIdUser())).thenReturn(false);
        boolean obtained = controller.removerPerfilCliente(cl.getIdUser());
        Mockito.verify(mock, Mockito.times(0)).removerPerfilCliente(cl.getIdUser());
        assertFalse(obtained);
    }

    @Test
    void removerPerfilClienteTest02(){
        //Id Inválido = 0
        CartaoCredito c = new CartaoCredito("999999999", 567, LocalDate.of(2024, 8, 9));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(0,"username","password","email@gmail.com","nome",765765567, e,c);
        Mockito.when(mock.removerPerfilCliente(cl.getIdUser())).thenReturn(false);
        boolean obtained = controller.removerPerfilCliente(cl.getIdUser());
        Mockito.verify(mock, Mockito.times(0)).removerPerfilCliente(cl.getIdUser());
        assertFalse(obtained);
    }

    @Test
    void removerPerfilClienteTest03(){
        //Um possível erro na ligação à BD
        CartaoCredito c = new CartaoCredito("999999999", 567, LocalDate.of(2024, 8, 9));
        Endereco e = new Endereco("RuaX", "PortaX","5000-123", "XXXX","Portugal", 292.2, 99.2,5);
        Cliente cl = new Cliente(1,"username","password","email@gmail.com","nome",765765567, e,c);
        Mockito.when(mock.removerPerfilCliente(cl.getIdUser())).thenReturn(false);
        boolean obtained = controller.removerPerfilCliente(cl.getIdUser());
        Mockito.verify(mock, Mockito.times(1)).removerPerfilCliente(cl.getIdUser());
        assertFalse(obtained);
    }


}