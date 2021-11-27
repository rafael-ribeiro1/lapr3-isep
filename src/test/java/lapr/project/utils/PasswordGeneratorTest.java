package lapr.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    public void generatePasswordTest() {
        System.out.println("Generate password test");
        int length = 8;
        String password = PasswordGenerator.generatePassword(length);
        Assertions.assertEquals(password.length(), length);
        Assertions.assertNotEquals(0, password.length());
    }

}
