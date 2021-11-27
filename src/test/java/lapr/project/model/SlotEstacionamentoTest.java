package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class SlotEstacionamentoTest {

    private final SlotEstacionamento slot1;
    private final SlotEstacionamento slot2;

    public SlotEstacionamentoTest() {
        slot1 = new SlotEstacionamento(1, true);
        slot2 = new SlotEstacionamento(false);
    }

    @Test
    public void getIdTest() {
        Assertions.assertEquals(1, slot1.getId());
        Assertions.assertNotEquals(3, slot1.getId());
    }

    @Test
    public void isSlotCargaTest() {
        Assertions.assertTrue(slot1.isSlotCarga());
        Assertions.assertFalse(slot2.isSlotCarga());
    }

    @Test
    void equalsTest() {
        SlotEstacionamento s = slot1;
        Assertions.assertEquals(s, slot1);
        Assertions.assertNotEquals(s, null);
        Assertions.assertNotEquals(null, slot1);
        Assertions.assertNotEquals(s, new Farmacia(1));
        Assertions.assertEquals(s, new SlotEstacionamento(1, true));
        Assertions.assertNotEquals(s, new SlotEstacionamento(2, false));
        Assertions.assertNotEquals(s, new SlotEstacionamento(3, true));
    }

    @Test
    void hashCodeTest() {
        SlotEstacionamento s = new SlotEstacionamento(3, true);
        int expected = Objects.hash(s.getId());
        Assertions.assertEquals(expected, s.hashCode());
    }
}
