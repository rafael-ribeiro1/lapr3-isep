package lapr.project.controller;

import lapr.project.data.EmailHandler;
import lapr.project.data.EstacionamentoHandler;
import lapr.project.data.FarmaciaHandler;
import lapr.project.data.GestorHandler;
import lapr.project.model.Endereco;
import lapr.project.model.Estacionamento;
import lapr.project.model.Farmacia;
import lapr.project.model.GestorFarmacia;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class RegistoFarmaciaControllerTest {

    private static RegistoFarmaciaController controller;
    private static FarmaciaHandler mock;
    private static Farmacia farmacia;
    private static GestorHandler gestorHandler = mock(GestorHandler.class);
    private static String[] info =  new String [9];
    private static String[] info2 = new String[9];

    @BeforeAll
    static void setUp() throws IOException {
        mock = mock(FarmaciaHandler.class);

        farmacia = new Farmacia(1,"Farmacia A", new Endereco("Rua 1", "123", "1234-123",
                "Porto", "Portugal", 1.5, 1.3, 50));
        Estacionamento est = new Estacionamento(10, 15, 2);
        farmacia.setEstacionamento(est);
        when(mock.addFarmacia(farmacia, 5, 8)).thenReturn(1);
        when(mock.addFarmacia(farmacia, 1, 1)).thenReturn(0);
        when(mock.addFarmacia(farmacia, -1, 3)).thenReturn(-1);

        ArrayList<Farmacia> farmacias = new ArrayList<>();
        farmacias.add(farmacia);
        when(mock.getListaFarmacias()).thenReturn(farmacias);



        when(gestorHandler.addGestor(eq("user1"), any(String.class), eq("gestor"), eq("user@gmail.com"),eq( 1))).thenReturn(true);
        when(gestorHandler.addGestor(eq("user2"), any(String.class), eq("gestor"), eq("user@gmail.com"),eq( 1))).thenReturn(false);
        when(gestorHandler.addGestor(eq("user3"), any(String.class), eq("gestor"), eq("user3@gmail.com"),eq( 1))).thenReturn(true);

        EmailHandler sender = mock(EmailHandler.class);
        when(sender.sendEmail(eq("user@gmail.com"), any(String.class), any(String.class))).thenReturn(true);
        when(sender.sendEmail(eq("user3@gmail.com"), any(String.class), any(String.class))).thenReturn(false);

        when(mock.atualizarFarmacia(info,new Farmacia(1))).thenReturn(true);
        when(mock.atualizarFarmacia(info,new Farmacia(-1))).thenReturn(false);
        when(mock.atualizarFarmacia(info2,new Farmacia(1))).thenReturn(true);

        EstacionamentoHandler estacionamentoHandler = mock(EstacionamentoHandler.class);
        when(estacionamentoHandler.adicionarSlotsEstacionamento(1, "Scooter", 10, 5)).thenReturn(true);
        when(estacionamentoHandler.adicionarSlotsEstacionamento(1, "Scooter", 5, 10)).thenReturn(false);

        when(estacionamentoHandler.adicionarSlotsEstacionamento(1, "Drone", 15, 10)).thenReturn(true);
        when(estacionamentoHandler.adicionarSlotsEstacionamento(1, "Drone", 10, 15)).thenReturn(false);

        controller = new RegistoFarmaciaController();
        controller.setFarmaciaDB(mock);
        controller.setGestorDB(gestorHandler);
        controller.setEstacionamentoDB(estacionamentoHandler);
        controller.setSender(sender);

        info [0]= "NomeFarmacia";
        info[1]= "Rua";
        info[2]= "NumeroDaPorta";
        info[3]= "codigoPostal";
        info[4]= "locadidade";
        info[5]= "pais";
        info[6]= "304.5";
        info[7]= "200";
        info[8]="300";

        info2[1]= "Rua";
        info2[2]= "NumeroDaPorta";
        info2[3]= "codigoPostal";
        info2[4]= "locadidade";
        info2[5]= "pais";
        info2[6]= "304.5";
        info2[7]= "200";
        info2[8]="300";
    }

    @Test
    void registarFarmaciaTest() {
        controller.novaFarmacia("Farmacia A", "Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3, 50);
        int res = controller.registarCapacidadeEstacionamento(10, 5, 15, 8, 2);
        assertFalse(res > 0);
    }

    @Test
    void registarFarmaciaInvalidTest1() {
        controller.novaFarmacia("Farmacia A", "Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3, 50);
        int res = controller.registarCapacidadeEstacionamento(10, -1, 15, 3, 2);
        assertFalse(res > 0);
    }

    @Test
    void registarFarmaciaInvalidTest5() {
        int res = controller.registarCapacidadeEstacionamento(10, -1, 15, 3, 2);
        assertTrue(res==-1);
    }

    @Test
    void name() {
        controller.novaFarmacia("Farmacia A", "Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3, 50);

       Endereco e = new Endereco("Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3, 50);
        Farmacia f = new Farmacia("Farmacia A", e);
        Estacionamento estacionamento = new Estacionamento (10,15,2);
        Mockito.when(mock.addFarmacia(f,2,3)).thenReturn(2);
        int res = controller.registarCapacidadeEstacionamento(10, 2, 15, 3, 2);
        int expected = 2;
        assertEquals(expected,res);

    }

    @Test
    void igual0() {
        controller.novaFarmacia("Farmacia A", "Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3, 50);
        int res = controller.registarCapacidadeEstacionamento(0, 0, 0, 0, 2);
        assertTrue(res == 0);
    }

    @Test
    void getListaFarmaciasTest() {
        controller.novaFarmacia("Farmacia A", "Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3, 50);
        controller.registarCapacidadeEstacionamento(10, 5, 15, 8, 2);
        ArrayList<Farmacia> farmacias = new ArrayList<>();
        farmacias.add(farmacia);
        List<Farmacia> res = controller.getListaFarmacias();
        assertEquals(farmacias.size(), res.size());
        for (int i = 0; i < farmacias.size(); i++)
            assertEquals(farmacias.get(i), res.get(i));
    }

    @Test
    void registarGestorFarmaciaTest() {
        String res = controller.registarGestorFarmacia(1, "user1", "user@gmail.com");
        assertEquals("Sucesso no registo de gestor", res);
    }

    @Test
    void registarGestorFarmaciaEmailRepetidoTest() {
        controller.registarGestorFarmacia(1, "user1", "user@gmail.com");
        String res = controller.registarGestorFarmacia(1, "user2", "user@gmail.com");
        assertNotNull(res);
        assertEquals("Erro ao registar gestor", res);
    }

    @Test
    void registarGestorFarmaciaErroEmailTest() {
        String res = controller.registarGestorFarmacia(1, "user3", "user3@gmail.com");
        assertNotNull(res);
        assertEquals("Erro ao enviar email", res);
    }


    @Test
    void atualizarFarmacia() {
         Farmacia f = new Farmacia(1,new Endereco());
         boolean res= controller.atualizarFarmacia(info,f);
         assertTrue(res);
    }

    @Test
    void atualizarFarmaciaInvalido() {
        Farmacia f = new Farmacia(-1,new Endereco());
        boolean res= controller.atualizarFarmacia(info,f);
        assertFalse(res);
    }

    @Test
    void atualizarFarmacia1N() {
        Farmacia f = new Farmacia(1,new Endereco());
        boolean res= controller.atualizarFarmacia(info,f);
        assertTrue(res);
    }
    @Test
    void atualizarFarmaciaInfoNull() {
        Farmacia f = new Farmacia(1,new Endereco());

        boolean res= controller.atualizarFarmacia(null,f);
        assertFalse(res);
    }

    @Test
    void atualizarFarmaciaTamanhoInfo() {

        String[] info =  new String [5];
        Farmacia f = new Farmacia(1,new Endereco());
        boolean res= controller.atualizarFarmacia(info,f);
        assertFalse(res);
    }

    @Test
    void addSlotsScooterTest() {
        boolean res = controller.addSlotsScooter(1, 10, 5);
        assertTrue(res);
    }

    @Test
    void addSlotsScooterInvalidTest() {
        boolean res = controller.addSlotsScooter(1, 5, 10);
        assertFalse(res);
    }

    @Test
    void addSlotsDroneTest() {
        boolean res = controller.addSlotsDrone(1, 15, 10);
        assertTrue(res);
    }

    @Test
    void addSlotsDroneInvalidTest() {
        boolean res = controller.addSlotsDrone(1, 10, 15);
        assertFalse(res);
    }

    @Test
    void getFarmacia() {
        Mockito.when(mock.getFarmacia(farmacia.getId())).thenReturn(farmacia);
        assertEquals(farmacia,controller.getFarmacia(farmacia.getId()));
    }

    @Test
    void getGestor() {
        GestorFarmacia gestor = new GestorFarmacia("a","a","a",1);
        System.out.println(farmacia.getId());

    }

    @Test
    void getGestorFarmaciaByEmail(){
        GestorFarmacia gestor = new GestorFarmacia("a","a","a",1);
        Mockito.when(gestorHandler.getGestorFarmaciaByEmail("a")).thenReturn(gestor);

        GestorFarmacia g = controller.getGestorFarmaciaByEmail("a");
        assertEquals(g,gestor);


    }
}
