package lapr.project.controller;

import lapr.project.data.EmailHandler;
import lapr.project.data.EstafetaHandler;
import lapr.project.data.FarmaciaHandler;
import lapr.project.model.Endereco;
import lapr.project.model.Estafeta;
import lapr.project.model.Farmacia;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class GerirEstafetaControllerTest {

    private static GerirEstafetaController controller;


    private static Estafeta estafeta;

    @BeforeAll
    static void setUp() throws IOException {
        FarmaciaHandler farmaciaHandler = mock(FarmaciaHandler.class);
        EstafetaHandler estafetaHandler = mock(EstafetaHandler.class);
        EmailHandler sender = mock(EmailHandler.class);
        controller = new GerirEstafetaController();
        controller.setFarmaciadb(farmaciaHandler);
        controller.setEstafetadb(estafetaHandler);
        controller.setSender(sender);
        estafeta =  new Estafeta(1,"estafeta1",   "estafeta1@gmail.com", "estafeta1",  2.6,  12356789,  1,60);
        Estafeta estafeta2 =  new Estafeta(2,"estafeta2",   "estafeta1@gmail.com", "estafeta2",  2.6,  12356769,  1,60);

        when(estafetaHandler.addEstafeta(estafeta)).thenReturn(true);
        when(estafetaHandler.addEstafeta(estafeta2)).thenReturn(false);

        Estafeta estafeta1= new Estafeta(1,"estafeta1",2.6,12356789,2 ,60);
        when(estafetaHandler.atualizarEstafeta(estafeta1,2)).thenReturn(true);

        Estafeta estafeta3 = new Estafeta(3,"estafeta3",   "estafeta3@gmail.com", "estafeta3",  -1,  12356789,  1,60);

        when(estafetaHandler.atualizarEstafeta(estafeta3,3)).thenReturn(false);

        ArrayList<Farmacia> farmacias = new ArrayList<>();
        farmacias.add(new Farmacia(1, "Farmacia A", new Endereco("Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3,50)));
        when(farmaciaHandler.getListaFarmacias()).thenReturn(farmacias);
        ArrayList<Estafeta> estafetas = new ArrayList<>();
        estafetas.add(estafeta);
        when(estafetaHandler.getListaEstafetas()).thenReturn(estafetas);

        when(sender.sendEmail(eq("estafeta1@gmail.com"), any(String.class), any(String.class))).thenReturn(true);
        when(sender.sendEmail(eq("estafeta3@gmail.com"), any(String.class), any(String.class))).thenReturn(true);
        when(sender.sendEmail(eq("not@an.email"), any(String.class), any(String.class))).thenReturn(false);

        Estafeta estafeta4 = new Estafeta(4,"estafeta4",   "not@an.email", "estafeta4",  2.6,  12356789,  1,60);
        when(estafetaHandler.addEstafeta(estafeta4)).thenReturn(true);

    }

    @Test
    void adicionarEstafeta() {


        String result =controller.adicionarEstafeta( 1, "estafeta1","estafeta1@gmail.com",
                "estafeta1",  2.6,  12356789 ,60);
       assertEquals("sucesso no registo", result);
    }

    @Test
    void adicionarEstafeta2() {


       controller.adicionarEstafeta( 1, "estafeta1","estafeta1@gmail.com",
                "estafeta1",  2.6,  12356789 ,60);
       String result = controller.adicionarEstafeta( 1, "estafeta2","estafeta1@gmail.com",
               "estafeta2",  2.6,  12356769 ,60);
        assertEquals("erro no registo", result);
    }

    @Test
    void adicionarEstafeta3() {
        String result = controller.adicionarEstafeta(1, "estafeta4", "not@an.email",
                "estafeta4", 2.6,  12356789,60);
        assertEquals("erro ao enviar email", result);
    }

    @Test
    void atualizarEstafeta() {

        controller.adicionarEstafeta(1, "estafeta1","estafeta1@gmail.com",
                "estafeta1",  2.6,  12356789 ,60);
        boolean result = controller.atualizarEstafeta("estafeta1",12356789,2.6,2,1,60);
        assertTrue(result);

    }
    @Test
    void atualizarEstafeta2() {

        controller.adicionarEstafeta(1, "estafeta3","estafeta3@gmail.com",
                "estafeta3",  2.6,  12356789,60);
        boolean result = controller.atualizarEstafeta("estafeta3",12356789,-1,3,3,60);
        assertFalse(result);

    }



    @Test
    void getListaFarmacias() {
        ArrayList<Farmacia> expected = new ArrayList<>();
        expected.add(new Farmacia(1, "Farmacia A", new Endereco("Rua 1", "123", "1234-123", "Porto", "Portugal", 1.5, 1.3,50)));
        List<Farmacia> result = controller.getListaFarmacias();
        assertEquals(expected, result);
    }

    @Test
    void getListaEstafetas() {
        controller.adicionarEstafeta(1, "estafeta1","estafeta1@gmail.com",
                "estafeta1",  2.6,  12356789 ,60);
        ArrayList<Estafeta> expected = new ArrayList<>();
        expected.add(estafeta);
       List<Estafeta> result =controller.getListaEstafetas();
        assertEquals(expected.size(),result.size());
        for (int i = 0; i < expected.size(); i++)
            assertEquals(expected.get(i), result.get(i));

    }
}
