package lapr.project.controller;

import lapr.project.data.EmailHandler;
import lapr.project.data.EstacionamentoHandler;
import lapr.project.model.SlotEstacionamento;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EstacionarTransporteControllerTest {

    private static EstacionarTransporteController controller;

    private static ArrayList<File> flagsList = new ArrayList<>();
    private static ArrayList<File> testFiles = new ArrayList<>();

    @BeforeAll
    static void setUp() throws IOException {
        File estFolder = new File("src-other/estimates");
        File[] filesList = estFolder.listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile() && file.getName().matches("estimate_\\d{4}_\\d{2}_\\d{2}_\\d{2}_\\d{2}_\\d{2}.data.flag")) {
                    flagsList.add(file);
                    file.delete();
                }
            }
        }

        String[] content = {"1 1 7", "3 5", "", "1 a 8", "1 3 5", "1 2 3", "2 1 9", "3 1 5"};
        String[] datetime = {"2021_01_01_12_00_00", "2021_01_02_12_00_00", "2021_01_03_12_00_00", "2021_01_04_12_00_00",
                "2021_01_05_12_00_00", "2021_01_06_12_00_00", "2021_01_07_12_00_00", "2021_01_08_12_00_00"};
        for (int i = 0; i < content.length; i++) {
            File estFile = new File(String.format("src-other/estimates/estimate_%s.data", datetime[i]));
            try (FileWriter fw = new FileWriter(estFile)) {
                fw.write(content[i]);
            }
            File estFlag = new File(String.format("src-other/estimates/estimate_%s.data.flag", datetime[i]));
            estFlag.createNewFile();
            testFiles.add(estFile);
            testFiles.add(estFlag);
        }

        EstacionamentoHandler estacionamentoHandler = mock(EstacionamentoHandler.class);
        when(estacionamentoHandler.emailFromTransporte(1)).thenReturn("estafeta@gmail.com");
        when(estacionamentoHandler.emailFromTransporte(2)).thenReturn("not@an.email");
        when(estacionamentoHandler.emailFromTransporte(3)).thenReturn("admin@farmacia.com");

        when(estacionamentoHandler.estacionar(1, 1, true)).thenReturn(true);
        when(estacionamentoHandler.estacionar(3, 1, true)).thenReturn(false);
        when(estacionamentoHandler.estacionar(2, 1, true)).thenReturn(true);
        when(estacionamentoHandler.estacionar(1, 2, true)).thenReturn(true);
        when(estacionamentoHandler.estacionar(1, 3, true)).thenReturn(true);

        when(estacionamentoHandler.getSlotEstacionamento(1,1)).thenReturn(new SlotEstacionamento(1,true));

        EmailHandler sender = mock(EmailHandler.class);
        when(sender.sendEmail(eq("estafeta@gmail.com"), any(String.class), any(String.class))).thenReturn(true);
        when(sender.sendEmail(eq("not@an.email"), any(String.class), any(String.class))).thenReturn(false);
        when(sender.sendEmail(eq("admin@farmacia.com"), any(String.class), any(String.class))).thenReturn(true);

        controller = new EstacionarTransporteController();
        controller.setEstacionamentoDB(estacionamentoHandler);
        controller.setSender(sender);
    }

    @AfterAll
    static void afterAll() throws IOException {
        for (File testFile : testFiles) testFile.delete();
        for (File file : flagsList) file.createNewFile();
    }

    @Test
    void registarEstacionamentoTest() {
        int res = controller.registarEstacionamentos();
        assertEquals(3, res);
    }

    @Test
    void getSlotEstacionamento(){
        int id_slot = 1;
        int idEstacionamento = 1;
        SlotEstacionamento slotEstacionamento = new SlotEstacionamento(1,true);

        SlotEstacionamento slot = controller.getSlotEstacionamento(id_slot,idEstacionamento);

        assertEquals(slot,slotEstacionamento);

    }


    @Test
    void getSlotEstacionamentoNull(){
        int id_slot = 1;
        int idEstacionamento = -1;
        SlotEstacionamento slotEstacionamento = null;

        SlotEstacionamento slot = controller.getSlotEstacionamento(id_slot,idEstacionamento);

        assertEquals(slot,slotEstacionamento);

    }


}
