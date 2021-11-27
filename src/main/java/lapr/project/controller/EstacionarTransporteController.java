package lapr.project.controller;

import lapr.project.data.EmailHandler;
import lapr.project.data.EstacionamentoHandler;
import lapr.project.model.SlotEstacionamento;
import java.io.*;
import java.util.Scanner;

/**
 * Classe responsável por fazer a ligação entre a UI e o sistema durante o estacionamento de uma scooter
 */
public class EstacionarTransporteController {
    /**
     * Handler do estacionamento .
     */
    private EstacionamentoHandler estacionamentoHandler;
    /**
     * Handler de email.
     */
    private EmailHandler sender;

    /**
     * Inicializa o controller
     */
    public EstacionarTransporteController() throws IOException {
        estacionamentoHandler = new EstacionamentoHandler();
        sender = new EmailHandler();
    }

    /**
     * Define o handler de estacionamento (para fim de testes)
     * @param estacionamentoHandler handler DB de estacionamento
     */
    public void setEstacionamentoDB(EstacionamentoHandler estacionamentoHandler) {
        this.estacionamentoHandler = estacionamentoHandler;
    }

    /**
     * Define o handler de emails (para fins de testes)
     * @param sender handler de emails
     */
    public void setSender(EmailHandler sender) {
        this.sender = sender;
    }

    /**
     * Verifica existência de ficheiros estimate e regista/envia email para o responsável do meio de transporte
     * @return nr de ficheiros lidos com sucesso
     */
    public int registarEstacionamentos() {
        String dir = "src-other/estimates";
        File estFolder = new File(dir);
        File[] filesList = estFolder.listFiles();
        int counter = 0;
        if (filesList == null) return counter;
        for (File file : filesList) {
            if (!file.isFile() || !file.getName().matches("estimate_\\d{4}_\\d{2}_\\d{2}_\\d{2}_\\d{2}_\\d{2}.data.flag"))
                continue;
            String estFile = dir + "/" + file.getName().substring(0, file.getName().length() - 5);
            if (!file.delete()) continue;
            int idTransporte;
            int idFarmacia;
            int estimativa;
            try {
                try (Scanner scanner = new Scanner(new File(estFile))) {
                    if (scanner.hasNextLine()) {
                        String[] data = scanner.nextLine().split(" ");
                        if (data.length != 3) throw new IllegalArgumentException();
                        idTransporte = Integer.parseInt(data[0]);
                        idFarmacia = Integer.parseInt(data[1]);
                        estimativa = Integer.parseInt(data[2]);
                    } else throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException | FileNotFoundException e) {
                continue;
            }
            String email = estacionamentoHandler.emailFromTransporte(idTransporte);
            if (email == null) continue;
            if (!estacionamentoHandler.estacionar(idFarmacia, idTransporte, true)) continue;
            String subject = "Estimativa do tempo de carga de scooter";
            String content = String.format("O meio de transporte com id %d deverá estar totalmente carregado daqui a %d horas", idTransporte, estimativa);
            if (sender.sendEmail(email, subject, content)) counter++;
        }
        return counter;
    }

    /**
     * Devolve as informações de um slot de estacionamento
     * @param idSlot id do slot
     * @param idEstacionamento id do estacionamento que faz parte o slot
     * @return o slot pretendido
     */
    public SlotEstacionamento getSlotEstacionamento(int idSlot, int idEstacionamento){
        return estacionamentoHandler.getSlotEstacionamento(idSlot, idEstacionamento);
    }

}
