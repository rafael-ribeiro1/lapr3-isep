package lapr.project.controller;

import lapr.project.data.UserHandler;
import lapr.project.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserHandler handler;

    private UserController c;

    public UserControllerTest() throws IOException {
        c = new UserController();
        handler = Mockito.mock(UserHandler.class);
        c.setHandler(handler);
    }

    @Test
    void getHandler() {
        assertEquals(handler, c.getHandler());
    }

    @Test
    void loginValido() {
        Mockito.when(handler.login("ecouto93@gmail.com","pass")).thenReturn(true);
        boolean expected = true;
        boolean result = c.login("ecouto93@gmail.com","pass");
        assertEquals(expected,result);
    }

    @Test
    void loginInValido() {
        Mockito.when(handler.login("1.com","@")).thenReturn(false);
        boolean expected = false;
        boolean result = c.login("1.com","@");
        assertEquals(expected,result);
    }

    @Test
    void getTipoUserValido() {
        Mockito.when(handler.getTipoUserLogado("admin@admin.com")).thenReturn("administrador");
        String expected = "administrador";
        String result = c.getTipoUser("admin@admin.com");
        assertEquals(expected,result);
    }

    @Test
    void getTipoUserInvalido() {
        Mockito.when(handler.getTipoUserLogado("xd.com")).thenReturn(null);
        String expected = null;
        String result = c.getTipoUser("xd.com");
        assertEquals(expected,result);
    }

    @Test
    void getUserByEmailNulo() {
        User expected = null;
        Mockito.when(handler.getUserComEmail(null)).thenReturn(null);
        User result = c.getUserByEmail(null);
        assertEquals(expected,result);
    }


    @Test
    void getUserByEmail() {
        User expected = new User(1,"edu","pass","cliente","ecouto93@gmail.com");
        Mockito.when(handler.getUserComEmail("ecouto93@gmail.com")).thenReturn(expected);
        User result = c.getUserByEmail("ecouto93@gmail.com");
        assertEquals(expected,result);
    }

    @Test
    void atualizarPassword01() {
        //iduser > 0

        //passwordisEmpty() -> false
        User u = new User(2,"cl", "12asBalc", "cliente", "cliente@gmail.com");
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "losjoa1A")).thenReturn(true);
        boolean obtained = c.atualizarPassword(u.getIdUser(), "losjoa1A");
        Mockito.verify(handler, Mockito.times(1)).atualizarPassword(u.getIdUser(), "losjoa1A");
        assertTrue(obtained);

        //passwordisEmpty() -> true
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "")).thenReturn(false);
        obtained = c.atualizarPassword(u.getIdUser(), "");
        Mockito.verify(handler, Mockito.times(0)).atualizarPassword(u.getIdUser(), "");
        assertFalse(obtained);
    }

    @Test
    void atualizarPassword02() {
        //iduser = 0
        //passwordisEmpty() -> false
        User u = new User(0,"e", "losjoa1A", "estafeta", "estafeta@gmail.com");
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "opoko123AD")).thenReturn(false);
        boolean obtained = c.atualizarPassword(u.getIdUser(), "opoko123AD");
        Mockito.verify(handler, Mockito.times(0)).atualizarPassword(u.getIdUser(), "opoko123AD");
        assertFalse(obtained);

        //passwordisEmpty() -> true
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "")).thenReturn(false);
        obtained = c.atualizarPassword(u.getIdUser(), "");
        Mockito.verify(handler, Mockito.times(0)).atualizarPassword(u.getIdUser(), "");
        assertFalse(obtained);
    }

    @Test
    void atualizarPassword03() {
        //iduser < 0
        //passwordisEmpty() -> false
        User u = new User(-1,"g", "losjoa1A", "gestor", "gestor@gmail.com");
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "opoko123AD")).thenReturn(false);
        boolean obtained = c.atualizarPassword(u.getIdUser(), "opoko123AD");
        Mockito.verify(handler, Mockito.times(0)).atualizarPassword(u.getIdUser(), "opoko123AD");
        assertFalse(obtained);

        //passwordisEmpty() -> true
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "")).thenReturn(false);
        obtained = c.atualizarPassword(u.getIdUser(), "");
        Mockito.verify(handler, Mockito.times(0)).atualizarPassword(u.getIdUser(), "");
        assertFalse(obtained);
    }

    @Test
    void atualizarPassword04() {
        //Possivel falha na ligação à BD
        //passwordisEmpty() -> false
        User u = new User(1,"g", "losjoa1A", "gestor", "gestor@gmail.com");
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "opoko123AD")).thenReturn(false);
        boolean obtained = c.atualizarPassword(u.getIdUser(), "opoko123AD");
        Mockito.verify(handler, Mockito.times(1)).atualizarPassword(u.getIdUser(), "opoko123AD");
        assertFalse(obtained);

        //passwordisEmpty() -> true
        Mockito.when(handler.atualizarPassword(u.getIdUser(), "")).thenReturn(false);
        obtained = c.atualizarPassword(u.getIdUser(), "");
        Mockito.verify(handler, Mockito.times(0)).atualizarPassword(u.getIdUser(), "");
        assertFalse(obtained);
    }
}