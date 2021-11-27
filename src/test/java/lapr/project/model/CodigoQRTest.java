package lapr.project.model;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CodigoQRTest {

    @BeforeAll
    static void beforeAll() throws IOException, WriterException {
        CodigoQR.gerarCodigoQR("teste", "teste1.png", 200, 200);
        CodigoQR.gerarCodigoQR("123456", "teste2.png", 300, 300);
    }

    @Test
    void testGerarCodigoQR01() throws FormatException, ChecksumException, NotFoundException, IOException {
        System.out.println("testGerarCodigoQR01");
        Path p = FileSystems.getDefault().getPath("teste1.png");
        boolean obtained = Files.exists(p);
        assertTrue(obtained);
        assertEquals("teste", CodigoQR.lerCodigoQR("teste1.png"));
    }

    @Test
    void testGerarCodigoQR02() throws FormatException, ChecksumException, NotFoundException, IOException {
        System.out.println("testGerarCodigoQR02");
        Path p = FileSystems.getDefault().getPath("teste2.png");
        boolean obtained = Files.exists(p);
        assertTrue(obtained);
        assertEquals("123456", CodigoQR.lerCodigoQR("teste2.png"));
    }

    @Test
    void testLerCodigoQR01() throws FormatException, ChecksumException, NotFoundException, IOException {
        System.out.println("testLerCodigoQR01");
        String expected = "teste";
        String obtained = CodigoQR.lerCodigoQR("teste1.png");
        assertEquals(expected, obtained);
    }


    @Test
    void testLerCodigoQR02() throws FormatException, ChecksumException, NotFoundException, IOException {
        System.out.println("testLerCodigoQR02");
        String expected = "123456";
        String obtained = CodigoQR.lerCodigoQR("teste2.png");
        assertEquals(expected, obtained);
    }

    @Test
    void testGerarCodigoQR03() {
        System.out.println("testGerarCodigoQR03");
        assertThrows(IllegalArgumentException.class,
                ()->CodigoQR.gerarCodigoQR("123456", "teste.png", -200, 200));
    }

    @Test
    void testLerCodigoQR03() {
        System.out.println("testLerCodigoQR03");
        assertThrows(IOException.class,
                ()->CodigoQR.lerCodigoQR("fail.png"));
    }
    @AfterAll
    static void afterAll() throws IOException {
        Path p = FileSystems.getDefault().getPath("teste1.png");
        Files.deleteIfExists(p);

        Path p2 = FileSystems.getDefault().getPath("teste2.png");
        Files.deleteIfExists(p2);


    }
    
}