package lapr.project.controller;

import lapr.project.data.EmailHandler;
import lapr.project.data.EstacionamentoHandler;
import lapr.project.data.FarmaciaHandler;
import lapr.project.data.GestorHandler;
import lapr.project.model.*;
import lapr.project.utils.PasswordGenerator;

import java.io.IOException;
import java.util.List;

/**
 * Classe responsável por fazer a ligação entre a UI e o sistema durante o registo de farmácia
 */
public class RegistoFarmaciaController {

    /**
     * Farmácia associada a instância do controller atual.
     */
    private Farmacia farmacia;
    /**
     * String associado ao tipo de transporte Scooter.
     */
    private static final String SCOOTER = "Scooter";
    /**
     * String associada ao tipo de transporte Drone.
     */
    private static final String DRONE = "Drone";
    /**
     * Handler da farmácia do sistema
     */
    private FarmaciaHandler farmaciaHandler;
    /**
     * Handler do gestor das farmácias .
     */
    private GestorHandler gestorHandler;
    /**
     * Handler do estacionamento do sistema.
     */
    private EstacionamentoHandler estacionamentoHandler;
    /**
     * Handler associado ao email.
     */
    private EmailHandler sender;

    /**
     * Inicializa o controller
     */
    public RegistoFarmaciaController() throws IOException {
        farmacia = null;
        farmaciaHandler = new FarmaciaHandler();
        gestorHandler = new GestorHandler();
        estacionamentoHandler = new EstacionamentoHandler();
        sender = new EmailHandler();
    }
    /**
     * Setter do handler das farmacias.(Apenas utilizados para testes unitários).
     * @param farmaciaHandler handler das farmacias.
     */
    public void setFarmaciaDB(FarmaciaHandler farmaciaHandler) {
        this.farmaciaHandler = farmaciaHandler;
    }
    /**
     * Setter do handler dos gestores.(Apenas utilizados para testes unitários).
     * @param gestorHandler handler dos gestores.
     */
    public void setGestorDB(GestorHandler gestorHandler) {
        this.gestorHandler = gestorHandler;
    }
    /**
     * Setter do handler dos estacionamentos.(Apenas utilizados para testes unitários).
     * @param estacionamentoHandler handler dos estacionamentos.
     */
    public void setEstacionamentoDB(EstacionamentoHandler estacionamentoHandler) {
        this.estacionamentoHandler = estacionamentoHandler;
    }
    /**
     * Setter dos emails(Apenas utilizados para testes unitários).
     * @param sender handler dos emails.
     */
    public void setSender(EmailHandler sender) {
        this.sender = sender;
    }

    /**
     * Cria e guarda temporariamente a farmácia com as informações digitadas
     * @param nome nome da farmácia
     * @param rua rua do endereço
     * @param numPorta num da porta da farmácia
     * @param codPostal codigo postal da farmacia
     * @param localidade localidade da farmacia
     * @param pais país da farmacia
     * @param latitude latitude da localização da farmacia
     * @param longitude longitude da localização da farmacia
     * @param altitude altitude da localização da farmácia
     */
    public void novaFarmacia(String nome, String rua, String numPorta, String codPostal, String localidade,
                             String pais, double latitude, double longitude, double altitude) {
        Endereco endereco = new Endereco(rua, numPorta, codPostal, localidade, pais, latitude, longitude, altitude);
        farmacia = new Farmacia(nome, endereco);
    }

    /**
     * Conclui o registo de uma farmácia, com a capacidade do estacionamento adicionada e o nr de slots de carga
     * @param maximoScooters capacidade máxima do estacionamento de scooters da farmácia
     * @param numCargaScooters número de slots de carga de scooters no estacionamento
     * @param maximoDrones capacidade máxima do estacionamento de drones da farmácia
     * @param numCargaDrones número de slots de carga de drones no estacionamento
     * @param potenciaMaxima potência máxima do estacionamento
     * @return true se tiver sido registada a farmacia, false caso contrario
     */
    public int registarCapacidadeEstacionamento(int maximoScooters, int numCargaScooters, int maximoDrones, int numCargaDrones, double potenciaMaxima) {
        if(farmacia==null) return -1;
        Estacionamento estacionamento = new Estacionamento(maximoScooters, maximoDrones, potenciaMaxima);
        farmacia.setEstacionamento(estacionamento);
        int id = farmaciaHandler.addFarmacia(farmacia, numCargaScooters, numCargaDrones);
        farmacia = null;
        return id;
    }

    /**
     * Método que atualiza as farmácias
     * @param info Informações a ser atualizadas.
     * @param farmacia Farmácia em qual as informações vao ser atualizadas.
     * @return True se for realizado com sucesso as mudanças.
     */
    public boolean atualizarFarmacia(String[] info,Farmacia farmacia) {
        if(info == null || info.length!=9) return false;
        return farmaciaHandler.atualizarFarmacia(info,farmacia);
    }


    /**
     * Retorna a lista de farmácias no sistema
     * @return ArrayList de farmácias ou null se ocorrer um erro
     */
    public List<Farmacia> getListaFarmacias() {
        return farmaciaHandler.getListaFarmacias();
    }

    /**
     * Regista um gestor de farmácias no sistema
     * @param idFarmacia id da farmácia do gestor
     * @param username username do gestor
     * @param email email do gestor
     * @return mensagem de sucesso ou erro
     */
    public String registarGestorFarmacia(int idFarmacia, String username, String email) {
        String password = PasswordGenerator.generatePassword(8);
        if (!gestorHandler.addGestor(username, password, "gestor", email, idFarmacia))
            return "Erro ao registar gestor";
        String subject = "Registo como gestor de farmácia";
        String content = String.format("Foi registado na plataforma como gestor de farmácia.\n" +
                    "As suas credenciais de acesso são as seguintes.\nUsername: %s\nPassword: %s",
                    username, password);
        if (!sender.sendEmail(email, subject, content))
            return "Erro ao enviar email";
        return "Sucesso no registo de gestor";
    }

    /**
     * Adiciona slots de estacionamento de scooters
     * @param idFarmacia id da farmacia
     * @param quantidade quantidade de slots
     * @param numCarga numero de slots que são de carga
     * @return true se forem adicionados com sucesso, falso caso contrario
     */
    public boolean addSlotsScooter(int idFarmacia, int quantidade, int numCarga) {
        return estacionamentoHandler.adicionarSlotsEstacionamento(idFarmacia, SCOOTER, quantidade, numCarga);
    }

    /**
     * Adiciona slots de estacionamento de drones
     * @param idFarmacia id da farmacia
     * @param quantidade quantidade de slots
     * @param numCarga numero de slots que são de carga
     * @return true se forem adicionados com sucesso, falso caso contrario
     */
    public boolean addSlotsDrone(int idFarmacia, int quantidade, int numCarga) {
        return estacionamentoHandler.adicionarSlotsEstacionamento(idFarmacia, DRONE, quantidade, numCarga);
    }

    /**
     * Método que devolve a farmacia com o id passado como parametro.
     * @param idFarmacia id da farmacia que se pretende obter.
     * @return Farmacia pretendida.
     */
    public Farmacia getFarmacia(int idFarmacia){
        return  farmaciaHandler.getFarmacia(idFarmacia);
    }

    /**
     * Método que devolve o gestor da farmácia através do seu email
.     * @param email Email do gestor da farmácia.,
     * @return Gestor da farmácia .
     */
    public GestorFarmacia getGestorFarmaciaByEmail(String email){
        return gestorHandler.getGestorFarmaciaByEmail(email);
    }

}
