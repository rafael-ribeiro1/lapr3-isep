package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GestorFarmaciaTest {

    private final GestorFarmacia gestor1;

    private final GestorFarmacia gestor2;

    public GestorFarmaciaTest() {
        gestor1 = new GestorFarmacia("user1", "pass", "email@gmail.com", 1);
        gestor2 = new GestorFarmacia("user2", "pass", "email@outlook.pt", 2);
    }

    @Test
    public void getFarmaciaTest() {
        System.out.println("Test getFarmacia");
        Farmacia f1 = gestor1.getFarmacia();
        Farmacia f2 = gestor2.getFarmacia();
        Assertions.assertNotEquals(f1.getId(), f2.getId());
        Assertions.assertEquals(1, f1.getId());
        Assertions.assertEquals(2, f2.getId());
    }

}
