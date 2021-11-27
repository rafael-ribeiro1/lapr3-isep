package lapr.project.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MeioTransporteTest {

    private static MeioTransporte m;
    private static MeioTransporte m1;
    private static MeioTransporte m2;
    private static MeioTransporte m3;
    private static MeioTransporte m4;
    private static MeioTransporte m5;
    @BeforeAll
    static void beforeAll() {
        m = new Drone(13, 23, 1.2, 0.8, 23.0, 120.0,10.0,1.0);
        m1 = new Scooter(2, 23, 100, "FUNCIONAL");
        m2 = new Drone(25, 1.2, 0.9, 14.5, 235.0,10.0,1.0);
        m3 = new Scooter(18, 3.4, 0.9, 23.5, 235.0);
        m4 = new Drone(3, 13, "FUNCIONAL", 34, 0.7, 0.3, 3.4,
                10.3,10.0,1.0);
        m5 = new Scooter(1,10,"FUNCIONAL",10,1.0,1.0,1.0,20.0);

    }

    @Test
    void getId() {
        m1.setId(2);
        int obtained = m1.getId();
        assertEquals(2, obtained);
        assertNotEquals(-1, obtained);
        m.setId(1);
        obtained = m.getId();
        assertEquals(1, obtained);
        assertNotEquals(-1, obtained);
    }

    @Test
    void getSlotEstacionamento() {
        SlotEstacionamento expected = new SlotEstacionamento(2, false);
        m.setSlotEstacionamento(expected);
        SlotEstacionamento obtained = m.getSlotEstacionamento();
        assertEquals(expected, obtained);
        assertNotEquals(new SlotEstacionamento(55, true), obtained);
    }

    @Test
    void getCapacidadeBateria(){
        assertEquals(13, m.getCapacidadeBateria());
        assertNotEquals(-5, m.getCapacidadeBateria());

        assertEquals(18, m3.getCapacidadeBateria());
        assertNotEquals(-18, m3.getCapacidadeBateria());
    }

    @Test
    void getCapacidadeEficaz() {
        double expected = 23;
        double result = m1.getCapacidadeEficaz();
        assertEquals(expected,result);
    }

    @Test
    void getEstado(){
        assertEquals("Funcional", m1.getEstado().getDescricaoEstado());
        assertNotEquals("Avariada", m1.getEstado().getDescricaoEstado());
    }

    @Test
    void getCargaAtual() {
        int obtained = m1.getCargaAtual();
        assertEquals(100, obtained);
        assertNotEquals(55, obtained);
    }

    @Test
    void getAltura() {
        double obtained = m2.getAltura();
        assertEquals(1.2,obtained);
        assertNotEquals(9.0,obtained);
    }

    @Test
    void getLargura() {
        double obtained = m2.getLargura();
        assertEquals(0.9,obtained);
        assertNotEquals(0,obtained);
    }

    @Test
    void getPeso() {
        double obtained = m2.getPeso();
        assertEquals(14.5,obtained);
        assertNotEquals(23.0,obtained);
    }

    @Test
    void getVelocidadeMaxima() {
        double obtained = m2.getVelocidadeMaxima();
        assertEquals(235.0,obtained);
        assertNotEquals(239.0,obtained);
    }

    @Test
    void getArea(){
        assertEquals(0.21, m4.getArea());
        assertNotEquals(0.5, m4.getArea());
    }

    @Test
    void setSlotEstacionamento() {
        m.setSlotEstacionamento(new SlotEstacionamento(1, true));
        assertEquals(new SlotEstacionamento(1, true), m.getSlotEstacionamento());
        assertNotEquals(new SlotEstacionamento(55, true), m.getSlotEstacionamento());
    }

    @Test
    void setId() {
        m1.setId(1);
        assertEquals(1, m1.getId());
        m1.setId(2);
        assertEquals(2, m1.getId());
        assertNotEquals(-1, m1.getId());
    }

    @Test
    void setCapacidadeBateria() {
        m1.setCapacidadeBateria(25);
        assertEquals(25, m1.getCapacidadeBateria());
        assertNotEquals(20, m1.getCapacidadeBateria());
    }

    @Test
    void setEstado() {
        m.setEstado(MeioTransporte.Estado.AVARIADA);
        assertEquals("Avariada", m.getEstado().getDescricaoEstado());
        assertNotEquals("Funcional", m.getEstado().getDescricaoEstado());
        m1.setEstado(MeioTransporte.Estado.FUNCIONAL);
        assertEquals("Funcional", m1.getEstado().getDescricaoEstado());
        assertNotEquals("Avariada", m1.getEstado().getDescricaoEstado());
    }

    @Test
    void testEquals() {

        assertNotEquals(null, m); //comparar com nulo
        assertEquals(m, m); //comparar com o próprio
        assertNotEquals(m, new SlotEstacionamento(1, true));
        assertEquals(m3, new Scooter(18, 3.4, 0.9, 23.5,
                235.0)); //comparar com clone

        assertNotEquals(m2, new Drone(23, 1.2, 0.9, 14.5, 235.0,10.0,1.0));
        assertNotEquals(m2, new Drone(25, 0.5, 0.9, 14.5, 235.0,10.0,1.0));
        assertNotEquals(m2, new Drone(25, 1.2, 1.3, 14.5, 235.0,10.0,1.0));
        assertNotEquals(m2, new Drone(25, 1.2, 0.9, 12.3, 235.0,10.0,1.0));
        assertNotEquals(m2, new Drone(25, 1.2, 0.9, 14.5, 321.2,10.0,1.0));

        assertNotEquals(m1, new Scooter(2, 31, 100, "FUNCIONAL"));

        assertNotEquals(m1, new Scooter(2, 23, 100, "AVARIADA"));
        assertEquals(m1.getEstado().getDescricaoEstado(), m2.getEstado().getDescricaoEstado());
        m1.setSlotEstacionamento(new SlotEstacionamento(1, true));
        m4.setSlotEstacionamento(new SlotEstacionamento(1, true));
        m3.setSlotEstacionamento(new SlotEstacionamento(3, false));
        assertEquals(m1.getSlotEstacionamento(), m4.getSlotEstacionamento());
        assertNotEquals(m1.getSlotEstacionamento(), m3.getSlotEstacionamento());
    }

    @Test
    void testHashCode() {
        int expected =  Objects.hash(m3.getCapacidadeBateria(), m3.getAltura(), m3.getLargura(), m3.getPeso(),
                m3.getVelocidadeMaxima());
        assertEquals(expected, m3.hashCode());
        assertNotEquals(6, m3.hashCode());
    }

    @AfterAll
    static void afterAll() throws IOException{
        Path p = FileSystems.getDefault().getPath("transporteID0");
        Files.deleteIfExists(p);

    }
}