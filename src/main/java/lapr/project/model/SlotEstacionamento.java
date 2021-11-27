package lapr.project.model;
import java.util.Objects;

/**
 * Classe que representa um slot de um estacionamento
 */
public class SlotEstacionamento {
    /**
     * Id do estacionamento
     */
    private int id;
    /**
     * Indica se se trata de um slot de carregamento ou não
     */
    private boolean slotCarga;
    /**
     *  Estacionamento a que pertence o slot
     */
    private Estacionamento estacionamento;
    /**
     *  String se diz se o slot é de estacionamento de drone ou de scooter
     */
    private String tipoEstacionamento;

    /**
     * Constructor com parametro
     * @param id identificador do estacionamento.
     * @param slotCarga  indicar se o estacionamento carregar ou nao.
     */

    public SlotEstacionamento(int id, boolean slotCarga) {
        this.id = id;
        this.slotCarga = slotCarga;
    }

    /**
     * Constrói uma instância de SlotEstacionamento recebendo por parâmetro:
     * @param slotCarga Indica se se trata de um slot de carregamento ou não
     */
    public SlotEstacionamento(boolean slotCarga) {
        this.slotCarga = slotCarga;
    }

    /**
     * Devolve o id do Slot de Estacionamento
     * @return o id do slot de estacionamento
     */
    public int getId() {
        return id;
    }

    /**
     * Devolve um boolean que indica se se trata de um slot de carregamento ou não
     * @return um boolean que indica se se trata de um slot de carregamento ou não
     */
    public boolean isSlotCarga() {
        return slotCarga;
    }

    /**
     * Verifica se o objeto passado por parâmetro é igual ao slot de estacionamento.
     * Se os objetos partilharem a mesma referência, devolve true. Se o objeto recebido
     * por parâmetro for nulo ou as classes dos objetos forem diferentes, devolve false.
     * Por fim, efetua um downcasting do objeto recebido por parâmetro para o tipo SlotEstacionamento,
     * comparando os identificadores únicos de cada um.
     * @param o o objeto a comparar
     * @return true se forem iguais, caso contrário retorna false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotEstacionamento that = (SlotEstacionamento) o;
        return id == that.id;
    }

    /**
     * Devolve o valor do hash code para o objeto
     * @return o valor do hash code para o objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
