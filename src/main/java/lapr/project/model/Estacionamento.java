package lapr.project.model;


/**
 * Classe que representa um  estacionamento de uma farmácia.
 */
public class Estacionamento {
    /**
     * id do estacionamento
     */
    private int id;
    /**
     * Numero de slots scooters existentes no estacionamento
     */
    private int capacidadeMaximaScooters;
    /**
     * Numero de slots de drones existentes no estacionamento.
     */
    private int capacidadeMaximaDrones;
    /**
     * Potencia maxima fornecida pelo parque
     */
    private double potenciaMaxima;
    /**
     * Instantiates a new Estacionamento.
     *
     * @param capacidadeMaximaScooters Numero de slots scooters existentes no estacionamento
     * @param capacidadeMaximaDrones  Numero de slots de drones existentes no estacionamento.
     * @param potenciaMaxima          Potencia maxima fornecida pelo parque
     */
    public Estacionamento(int capacidadeMaximaScooters,int capacidadeMaximaDrones, double potenciaMaxima) {
        this.capacidadeMaximaScooters = capacidadeMaximaScooters;
        this.capacidadeMaximaDrones =capacidadeMaximaDrones;
        this.potenciaMaxima = potenciaMaxima;
    }

    /**
     * Gets capacidade maxima scooters.
     *
     * @return the capacidade maxima scooters
     */
    public int getCapacidadeMaximaScooters() {
        return capacidadeMaximaScooters;
    }

    /**
     * Gets potencia maxima.
     *
     * @return the potencia maxima
     */
    public double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    /**
     * Gets capacidade maxima drones.
     *
     * @return the capacidade maxima drones
     */
    public int getCapacidadeMaximaDrones() {
        return capacidadeMaximaDrones;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }
}
