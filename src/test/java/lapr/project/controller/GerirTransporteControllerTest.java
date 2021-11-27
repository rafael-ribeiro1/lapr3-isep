package lapr.project.controller;

import com.google.zxing.WriterException;
import lapr.project.data.DroneHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.data.TransporteHandler;
import lapr.project.model.Drone;
import lapr.project.model.Scooter;
import lapr.project.model.SlotEstacionamento;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

import static org.junit.jupiter.api.Assertions.*;

class GerirTransporteControllerTest {

    @Mock
    private TransporteHandler handler;

    @Mock
    private ScooterHandler scooterHandler;

    @Mock
    private DroneHandler droneHandler;

    private GerirTransporteController c;

    public GerirTransporteControllerTest() throws IOException {
        handler = Mockito.mock(TransporteHandler.class);
        droneHandler = Mockito.mock(DroneHandler.class);
        scooterHandler = Mockito.mock(ScooterHandler.class);
        c = new GerirTransporteController();
        c.setHandler(handler);
        c.setDroneHandler(droneHandler);
        c.setScooterHandler(scooterHandler);
    }

    @Test
    void getHandler() {
        assertEquals(handler, c.getHandler());
    }

    @Test
    void setHandler() {
        c.setHandler(handler);
        assertEquals(handler, c.getHandler());
    }

    // <editor-fold defaultstate="collapsed" desc="Adicionar Scooter">
    @Test
    void adicionarScooter01() throws IOException, WriterException {
        //Valores Válidos
        Scooter m = new Scooter(20,78, 1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, false)).thenReturn(m.getId());
        int id = c.adicionarScooter( 20, 78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m,1, false);
        assertEquals(m.getId(), id);
    }

   @Test
    void adicionarScooter02() throws IOException, WriterException {
        //Valores inválidos capacidade < 0
        Scooter m = new Scooter(-20, 78,1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, false)).thenReturn(-1);
        int id = c.adicionarScooter( -20, 78, 1.2,0.50, 23.8, 235.0,
                 1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter03() throws IOException, WriterException {
        //Valores inválidos capacidade = 0
        Scooter m = new Scooter(0, 78,1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, false)).thenReturn(-1);
        int id = c.adicionarScooter( 0, 78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter04() throws IOException, WriterException {
        //Valores inválidos comprimento < 0
        Scooter m = new Scooter(20, 78,-1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 8, false)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 ,78, -1.2,0.50, 23.8, 235.0,
                 1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter05() throws IOException, WriterException {
        //Valores Válidos comprimento = 0
        Scooter m = new Scooter(20,78, 0.0, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 8, true)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 78, 0.0,0.50, 23.8, 235.0,
                 1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }


    @Test
    void adicionarScooter06() throws IOException, WriterException {
        //Valores Válidos comprimento > 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,4, false)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 ,78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m,1, false);
        assertEquals(m.getId(), id);
    }

    @Test
    void adicionarScooter07() throws IOException, WriterException {
        //Valores Inválidos largura = 0
        Scooter m = new Scooter(20, 78,1.2, 0.0, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, false)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 78, 1.2,0.0, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m,1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter08() throws IOException, WriterException {
        //Valores Válidos largura > 0
        Scooter m = new Scooter(20,78, 1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, false)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m,1, false);
        assertEquals(m.getId(), id);
    }

    @Test
    void adicionarScooter09() throws IOException, WriterException {
        //Valores Inválidos largura < 0
        Scooter m = new Scooter(20,78, 1.2, -0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, false)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 78, 1.2,-0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m,1, false);
        assertEquals(-1, id);
    }

    @Test
    void  adicionarScooter10() throws IOException, WriterException {
        //Valores Inválidos peso < 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, -23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m,1, true)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, -23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m,1, true);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter11() throws IOException, WriterException {
        //Valores Inválidos peso = 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 0.0,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 3, true)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 0.0, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter12() throws IOException, WriterException {
        //Valores Inválidos peso > 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.0,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 3, true)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.0, 235.0,
                 1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m, 1, false);
        assertEquals(m.getId(), id);
    }

    @Test
    void adicionarScooter13() throws IOException, WriterException {
        //Valores inválidos velocidade < 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.8,
                -235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 2, true)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, -235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter14() throws IOException, WriterException {
        //Valores inválidos velocidade = 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.8,
                0.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 1, false)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, 0.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter15() throws IOException, WriterException {
        //Erro na ligação à BD/outra falha relativa à BD
        Scooter m = new Scooter(20,78, 1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 1, false)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, 235.0,
                 1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter16() throws IOException, WriterException {
        //Carga Atual < 0
        Scooter m = new Scooter(20, -78,1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 1, true)).thenReturn(-1);
        int id = c.adicionarScooter(20 , -78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter17() throws IOException, WriterException {
        //Carga Atual = 0
        Scooter m = new Scooter(20,0, 1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 2, false)).thenReturn(-1);
        int id = c.adicionarScooter(20 , 0, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(0)).adicionarScooter(m, 1, false);
        assertEquals(-1, id);
    }

    @Test
    void adicionarScooter18() throws IOException, WriterException {
        //Carga Atual > 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 2, false)).thenReturn(m.getId());
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m, 1, false);
        assertEquals(m.getId(), id);
    }

    @Test
    void adicionarTransporte19() throws IOException, WriterException {
        //Id = 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.8,
                235.0);
        Mockito.when(scooterHandler.adicionarScooter(m, 2, false)).thenReturn(0);
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, 235.0,
                1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m, 1, false);
        assertEquals(0, id);
    }

    @Test
    void adicionarScooter20() throws IOException, WriterException {
        //Id > 0
        Scooter m = new Scooter(20, 78,1.2, 0.50, 23.8,
                235.0);
        m.setId(1);
        Mockito.when(scooterHandler.adicionarScooter(m, 1, false)).thenReturn(1);
        int id = c.adicionarScooter(20 , 78, 1.2,0.50, 23.8, 235.0,
                 1,false);
        Mockito.verify(scooterHandler, Mockito.times(1)).adicionarScooter(m, 1, false);
        assertEquals(1, id);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Adicionar Drone">
    @Test
    void adicionarDrone01() throws IOException, WriterException {
        Drone d = new Drone(13, 23, 1.2, 0.8, 23.0, 120.0,10.0,1.0);
        Mockito.when(droneHandler.adicionarDrone(d, 2, true)).thenReturn(1);
        int id = c.adicionarDrone(13, 23, 1.2, 0.8,23.0,120.0,10.0,1.0,
                2, true);
        Mockito.verify(droneHandler, Mockito.times(1)).adicionarDrone(d, 2, true);
        assertEquals(1, id);
    }

    @Test
    void adicionarDrone02() throws IOException, WriterException {
        Drone d = new Drone(13, 23, 1.2, 0.8, 23.0, 120.0,10.0,1.0);
        Mockito.when(droneHandler.adicionarDrone(d, 2, true)).thenReturn(-1);
        int id = c.adicionarDrone(13, 23, 1.2, 0.8,23.0,120.0,10.0,1.0,
                2, true);
        Mockito.verify(droneHandler, Mockito.times(1)).adicionarDrone(d, 2, true);
        assertEquals(-1, id);
    }

    @Test
    void adicionarDrone03() throws IOException, WriterException {
        Drone d = new Drone(13, 23, 1.2, 0.8, 23.0, 120.0,10.0,1.0);
        Mockito.when(droneHandler.adicionarDrone(d, 2, true)).thenReturn(0);
        int id = c.adicionarDrone(13, 23, 1.2, 0.8,23.0,120.0,10.0,1.0,
                2, true);
        Mockito.verify(droneHandler, Mockito.times(1)).adicionarDrone(d, 2, true);
        assertEquals(0, id);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Remover Transporte">
    @Test
    void removerTransporte01() {
        //Id inválido
        Scooter s = new Scooter(20,15, 1.0, 0.3, 13.5,
                120.0);
        s.setId(-1);
        Mockito.when(handler.removerTransporte(s.getId())).thenReturn(false);
        boolean obtainedScooter = c.removerTransporte(s.getId());
        Mockito.verify(handler, Mockito.times(0)).removerTransporte(s.getId());
        assertFalse(obtainedScooter);

        Drone d = new Drone(-1, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.removerTransporte(d.getId())).thenReturn(false);
        boolean obtainedDrone = c.removerTransporte(d.getId());
        Mockito.verify(handler, Mockito.times(0)).removerTransporte(d.getId());
        assertFalse(obtainedDrone);
    }

    @Test
    void removerTransporte02() {
        //Id Inválido = 0
        Scooter m = new Scooter(20,15, 1.0, 0.3,
                13.5,120.0);
        m.setId(0);
        Mockito.when(handler.removerTransporte(m.getId())).thenReturn(false);
        boolean obtained = c.removerTransporte(m.getId());
        Mockito.verify(handler, Mockito.times(0)).removerTransporte(m.getId());
        assertFalse(obtained);

        Drone d = new Drone(0, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.removerTransporte(d.getId())).thenReturn(false);
        boolean obtainedDrone = c.removerTransporte(d.getId());
        Mockito.verify(handler, Mockito.times(0)).removerTransporte(d.getId());
        assertFalse(obtainedDrone);
    }

    @Test
    void removerTransporte03() {
        //Id válido > 0
        Scooter sc = new Scooter(20,15, 1.0, 0.3,
                13.5,120.0);
        sc.setId(1);
        Mockito.when(handler.removerTransporte(sc.getId())).thenReturn(true);
        boolean obtained = c.removerTransporte(sc.getId());
        Mockito.verify(handler, Mockito.times(1)).removerTransporte(sc.getId());
        assertTrue(obtained);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.removerTransporte(d.getId())).thenReturn(true);
        boolean obtainedDrone = c.removerTransporte(d.getId());
        Mockito.verify(handler, Mockito.times(1)).removerTransporte(2);
        assertTrue(obtainedDrone);
    }

    @Test
    void removerTransporte04() {
        //Erro na ligação à BD/outra falha relativa à BD
        Scooter s = new Scooter(10,15, 1.0, 0.3, 13.5,
                120.0);
        s.setId(1);
        Mockito.when(handler.removerTransporte(s.getId())).thenReturn(false);
        boolean obtainedScooter = c.removerTransporte(s.getId());
        Mockito.verify(handler, Mockito.times(1)).removerTransporte(s.getId());
        assertFalse(obtainedScooter);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.removerTransporte(d.getId())).thenReturn(false);
        boolean obtainedDrone = c.removerTransporte(d.getId());
        Mockito.verify(handler, Mockito.times(1)).removerTransporte(d.getId());
        assertFalse(obtainedDrone);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Atualizar Transporte">
    @Test
    void atualizarTransporte01() {
        //Válido - Slot null
        Scooter sc = new Scooter(18, 0.7, 0.4, 19.2, 305.2);
        sc.setId(1);
        Mockito.when(handler.atualizarTransporte(sc.getId(),70, null)).thenReturn(true);
        boolean obtained = c.atualizarTransporte(sc.getId(),70, null);
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(sc.getId(),70, null);
        assertTrue(obtained);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), 64, null)).thenReturn(true);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 64, null);
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(d.getId(),64, null);
        assertTrue(obtainedDrone);
    }

    @Test
    void atualizarTransporte02() {
        //Válido - Slot não null
        Scooter sc = new Scooter(23, 1.3, 0.2, 32.0, 150.0);
        sc.setId(1);
        Mockito.when(handler.atualizarTransporte(sc.getId(),70, new SlotEstacionamento(1, true))).thenReturn(true);
        boolean obtained = c.atualizarTransporte(sc.getId(),70, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(sc.getId(),70, new SlotEstacionamento(1, true));
        assertTrue(obtained);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), 64, new SlotEstacionamento(1, true))).thenReturn(true);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 64, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(d.getId(),64, new SlotEstacionamento(1, true));
        assertTrue(obtainedDrone);
    }

    @Test
    void atualizarTransporte03() {
        //Inválido - Carga Atual < 0
        Scooter sc = new Scooter(23, 1.3, 0.2, 32.0, 150.0);
        sc.setId(1);
        Mockito.when(handler.atualizarTransporte(sc.getId(),-70, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtained = c.atualizarTransporte(sc.getId(),-70, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(0)).atualizarTransporte(sc.getId(),-70, new SlotEstacionamento(1, true));
        assertFalse(obtained);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), -64, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), -64, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(0)).atualizarTransporte(sc.getId(),-64, new SlotEstacionamento(1, true));
        assertFalse(obtainedDrone);
    }

    @Test
    void atualizarTransporte04() {
        //Válido - Carga Atual = 0
        Scooter sc = new Scooter(23, 1.3, 0.2, 32.0, 150.0);
        sc.setId(1);
        Mockito.when(handler.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true))).thenReturn(true);
        boolean obtained = c.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertTrue(obtained);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true))).thenReturn(true);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(d.getId(),0, new SlotEstacionamento(1, true));
        assertTrue(obtainedDrone);
    }

    @Test
    void atualizarTransporte05() {
        //Id inválido
        Scooter sc = new Scooter(-1, 23,23, 1.3, 0.2, 32.0, 150.0);
        Mockito.when(handler.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtained = c.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(0)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertFalse(obtained);

        Drone d = new Drone(-1, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(0)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertFalse(obtainedDrone);
    }

    @Test
    void atualizarTransporte06() {
        //Id Inválido = 0
        Scooter sc = new Scooter(0, 23,23, 1.3, 0.2, 32.0, 150.0);
        Mockito.when(handler.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtained = c.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(0)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertFalse(obtained);

        Drone d = new Drone(0, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(0)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertFalse(obtainedDrone);
    }

    @Test
    void atualizarTransporte07() {
        //Id Válido > 0
        Scooter sc = new Scooter(1,10,23, 1.3, 0.2, 32.0, 150.0);
        sc.setId(1);
        Mockito.when(handler.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true))).thenReturn(true);
        boolean obtained = c.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertTrue(obtained);

        Drone d = new Drone(2, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(handler.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true))).thenReturn(true);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(d.getId(),0, new SlotEstacionamento(1, true));
        assertTrue(obtainedDrone);
    }

    @Test
    void atualizarTransporte08() {
        //Erro na ligação à BD/outra falha relativa à BD
        Scooter sc = new Scooter(20,23, 1.3, 0.2, 32.0, 150.0);
        sc.setId(1);
        Mockito.when(handler.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtained = c.atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(sc.getId(),0, new SlotEstacionamento(1, true));
        assertFalse(obtained);

        Drone d = new Drone(15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        d.setId(2);
        Mockito.when(handler.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true))).thenReturn(false);
        boolean obtainedDrone = c.atualizarTransporte(d.getId(), 0, new SlotEstacionamento(1, true));
        Mockito.verify(handler, Mockito.times(1)).atualizarTransporte(d.getId(),0, new SlotEstacionamento(1, true));
        assertFalse(obtainedDrone);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Get Drone">
    @Test
    void getDrone(){
        Drone d = new Drone(1, 15, 78, 1.2, 0.6,11.0,23.4,
                12.1,3.1);
        Mockito.when(droneHandler.getDrone(d.getId())).thenReturn(d);
        Drone obtained = c.getDrone(d.getId());
        assertEquals(d, obtained);
        assertNotEquals(new Drone(2, 23, 34, "FUNCIONAL"), obtained);
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Get Scooter">
    @Test
    void getScooter(){
        Scooter s = new Scooter(23, 1.2, 0.6,11.0,23.4);
        Mockito.when(scooterHandler.getScooter(s.getId())).thenReturn(s);
        Scooter obtained = c.getScooter(s.getId());
        assertEquals(s, obtained);
        assertNotEquals(new Scooter(34, 1.2, 0.6,11.0,23.4), obtained);
    }

    //</editor-fold>

    @AfterAll
    static void afterAll() {
        File dir = new File("codigosQR");
        File[] scooterIDS = dir.listFiles((dir1, name) -> name.startsWith("scooterID") && name.endsWith(".png"));
        File[] droneIDS = dir.listFiles((dir1, name) -> name.startsWith("droneID") && name.endsWith(".png"));
        if (scooterIDS != null && droneIDS != null) {
            for (File f : scooterIDS) {
                f.delete();
            }

            for (File f : droneIDS) {
                f.delete();
            }
        }
    }
}