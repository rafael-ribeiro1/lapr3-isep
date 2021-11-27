package lapr.project.model;

/**
 * Classe que representa uma fatura de uma encomenda.
 */
public class Fatura {
    /**
     * Identificador da fatura.
     */
    private int id;
    /**
     * Nif da fatura
     */
    private int nif;
    /**
     * Valor total da fatura
     */
    private double valorTotal;
    /**
     * Construtor da fatura
     *
     * @param id         Identificador da fatura.
     * @param nif        Nif da fatura
     * @param valorTotal Valor total da fatura
     */
    public Fatura(int id, int nif, double valorTotal) {
        this.id = id;
        this.nif = nif;
        this.valorTotal = valorTotal;
    }
    /**
     * Instantiates a new Fatura.
     *
     * @param nif        the nif
     * @param valorTotal the valor total
     */
    public Fatura(int nif, double valorTotal) {
        this.nif = nif;
        this.valorTotal = valorTotal;
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
     * Gets nif.
     *
     * @return the nif
     */
    public int getNif() {
        return nif;
    }

    /**
     * Gets valor total.
     *
     * @return the valor total
     */
    public double getValorTotal() {
        return valorTotal;
    }
}
