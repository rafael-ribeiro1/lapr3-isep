package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    private final User user1;
    private final User user2;
    private final User user4;

    public UserTest() {
        user1 = new User("a", "p1", "t1", "e1");
        user2 = new User(2, "b", "p2", "t2", "e2");
        user4 = new User(4);
    }

    @Test
    public void getIdUserTest() {
        Assertions.assertEquals(4, user4.getIdUser());
        Assertions.assertNotEquals(1, user4.getIdUser());
    }

    @Test
    public void getUsernameTest() {
        Assertions.assertEquals("a", user1.getUsername());
        Assertions.assertNotEquals("c", user1.getUsername());
    }

    @Test
    public void getPasswordTest() {
        Assertions.assertEquals("p2", user2.getPassword());
        Assertions.assertNotEquals("p4", user2.getPassword());
    }

    @Test
    public void getTipoUserTest() {
        Assertions.assertEquals("t1", user1.getTipoUser());
        Assertions.assertNotEquals("t3", user1.getTipoUser());
    }

    @Test
    public void getEmailTest() {
        Assertions.assertEquals("e2", user2.getEmail());
        Assertions.assertNotEquals("e1", user2.getEmail());
    }

}
