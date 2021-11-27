package lapr.project.controller;

import com.google.zxing.WriterException;
import lapr.project.data.DroneHandler;
import lapr.project.data.ScooterHandler;
import lapr.project.data.TransporteHandler;
import lapr.project.model.*;

import java.io.IOException;

/**
 * Controller do transporte da plataforma.
 */
public class GerirTransporteController {

    /**
     * O handler de GerirVeiculoController
     */
    private TransporteHandler handler;
    /**
     * Handler da scooter da plataforma.
     */
    private ScooterHandler scooterHandler;
    /**
     * Handler dos drones da platform.
     */
    private DroneHandler droneHandler;

    /**
     * Cria uma instância de GerirVeiculoController inicializando o VeiculoHandler
     * @throws IOException  lançada caso ocorra erro ao ler o ficheiro
     */
    public GerirTransporteController() throws IOException {
        handler = new TransporteHandler();
        scooterHandler = new ScooterHandler();
        droneHandler = new DroneHandler();
    }

    /**
     * Devolve o handler
     * @return o VeiculoHandler
     */
    public TransporteHandler getHandler() {
        return handler;
    }

    /**
     * Modifica o valor do handler
     * @param handler o TransporteHandler
     */
    public void setHandler(TransporteHandler handler) {
        this.handler = handler;
    }

    /**
     * Modifica o valor do handler
     * @param scooterHandler o ScooterHandler
     */
    public void setScooterHandler(ScooterHandler scooterHandler) {
        this.scooterHandler = scooterHandler;
    }

    /**
     * Método de setter do handler dos drones,
     * @param droneHandler Handler dos drones,
     */
    public void setDroneHandler(DroneHandler droneHandler) {
        this.droneHandler = droneHandler;
    }

    /**
     * Método que adiciona uma Scooter a base de dados.
     * @param capacidadeBateria  Capacidade de bateria do transporte.
     * @param cargaAtual Carga atual do transporte.
     * @param altura Altura do transporte.
     * @param largura Largura do transporte.
     * @param peso Peso do transporte.
     * @param velocidadeMaxima Velocidade maxima do transporte.
     * @param idFarmacia Id da farmácia.
     * @param isCargaSlot Boolean a dizer que o slot em que se encontra transporte é de carregar ou na o
     * @return
     * @throws IOException
     * @throws WriterException
     */
    public int adicionarScooter(int capacidadeBateria, int cargaAtual, double altura, double largura,
                                double peso, double velocidadeMaxima, int idFarmacia, boolean isCargaSlot) throws IOException, WriterException {
        if (capacidadeBateria <= 0 || cargaAtual <= 0 || altura <= 0 || largura <= 0 || peso <= 0 || velocidadeMaxima <=0)
            return -1;
        Scooter sc = new Scooter(capacidadeBateria, cargaAtual, altura, largura, peso, velocidadeMaxima);
        int id = scooterHandler.adicionarScooter(sc, idFarmacia, isCargaSlot);
        if (id > 0) {
            String ficheiro = String.format("codigosQR/scooterID%d.png", id);
            CodigoQR.gerarCodigoQR(Integer.toString(id), ficheiro, 300, 300);
        }
        return id;
    }

    /**
     * Método que adiciona um drone a base de dados.
     * @param capacidadeBateria  Capacidade de bateria do transporte.
     * @param cargaAtual Carga atual do transporte.
     * @param altura Altura do transporte.
     * @param largura Largura do transporte.
     * @param peso Peso do transporte.
     * @param velocidadeMaxima Velocidade maxima do transporte.
     * @param idFarmacia Id da farmácia.
     * @param isCargaSlot Boolean a dizer que o slot em que se encontra transporte é de carregar ou na o
     * @return O id do drone gerado.
     * @throws IOException
     * @throws WriterException
     */
    public int adicionarDrone(int capacidadeBateria, int cargaAtual, double altura, double largura, double peso,
                              double velocidadeMaxima,double comprimento, double velocidadeLevantamento,
                              int idFarmacia, boolean isCargaSlot) throws IOException, WriterException {
        Drone dr = new Drone(capacidadeBateria, cargaAtual, altura, largura, peso, velocidadeMaxima, comprimento,
                velocidadeLevantamento);
        int id = droneHandler.adicionarDrone(dr, idFarmacia, isCargaSlot);
        if (id > 0) {
            String ficheiro = String.format("codigosQR/droneID%d.png", id);
            CodigoQR.gerarCodigoQR(Integer.toString(id), ficheiro, 300, 300);
        }
        return id;
    }

    /**
     * Remove o Meio de Transporte, atualizando o estado para "Avariada"
     * @param id o id da Meio de Transporte
     * @return true se a remoção for bem sucedida, caso contrário, retorna false
     */
    public boolean removerTransporte(int id) {
        if (id <= 0) return false;
        return handler.removerTransporte(id);
    }

    /**
     * Atualiza a Meio de Transporte, recebendo por parâmetro dados para a identificação da mesma e os dados
     * a modificar:
     * @param id o id da Meio de Transporte
     * @param cargaAtual a carga atual da Meio de Transporte
     * @param slot o slot de estacionamento
     * @return true se a atualização for bem sucedida, caso contrário, retorna false
     */
    public boolean atualizarTransporte(int id, int cargaAtual, SlotEstacionamento slot){
        if (cargaAtual < 0) return false;
        if (id <= 0) return false;
        return handler.atualizarTransporte(id, cargaAtual, slot);
    }

    /**
     * Método que devolve um drone dando o seu id a base de dados.
     * @param id id do drone,
     * @return Drone
     */
    public Drone getDrone(int id){
        return droneHandler.getDrone(id);
    }

    /**
     * Método que devolve uma scooter dando o seu id a base de dados.
     * @param id id da scooter
     * @return Scooter com esse id.
     */
    public Scooter getScooter(int id){
        return scooterHandler.getScooter(id);
    }
}
