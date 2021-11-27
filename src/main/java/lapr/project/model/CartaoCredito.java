package lapr.project.model;
import java.time.LocalDate;
import java.util.Objects;


/**
 * Classe que representa um cartao credito.
 */
public class CartaoCredito {
    /**
     * id do cartao de credito
     */
    private int id;
    /**
     * Número do cartao
     */
    private final String numero;
    /**
     * Número de ccv
     */
    private final int ccv;
    /**
     * Data de validade do cartao
     */
    private final LocalDate dataValidade;

    /**
     * Constrói uma instância de CartaoCredito recebendo por parâmetro:
     * @param id o id do cartão de crédito
     * @param numero o número do cartão de crédito
     * @param ccv o ccv do cartão de crédito
     * @param dataValidade a data de validade do cartão de crédito
     */
    public CartaoCredito(int id, String numero, int ccv, LocalDate dataValidade) {
        this.id = id;
        this.numero = numero;
        this.ccv = ccv;
        this.dataValidade = dataValidade;
    }

    /**
     * @param numero número do cartao
     * @param ccv número de ccv
     * @param dataValidade data de validade do cartao
     */
    public CartaoCredito(String numero, int ccv, LocalDate dataValidade) {
        this.numero = numero;
        this.ccv = ccv;
        this.dataValidade = dataValidade;
    }

    /**
     * Devolve o identificador único do Cartão de Crédito
     * @return o identificador único do Cartão de Crédito
     */
    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    /**
     * Devolve o CCV do Cartão de Crédito
     * @return o CCV do Cartão de Crédito
     */
    public int getCcv() {
        return ccv;
    }

    /**
     * Devolve a data de validade do Cartão de Crédito
     * @return a data de validade do Cartão de Crédito
     */
    public LocalDate getDataValidade() {
        return dataValidade;
    }

    /**
     * Verifica se o objeto passado por parâmetro é igual ao cartão de crédito.
     * Se os objetos partilharem a mesma referência, devolve true. Se o objeto recebido
     * por parâmetro for nulo ou as classes dos objetos forem diferentes, devolve false.
     * Por fim, efetua um downcasting do objeto recebido por parâmetro para o tipo CartaoCredito,
     * comparando os diferentes atributos da classe.
     * @param o o objeto a comparar
     * @return true se forem iguais, caso contrário retorna false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartaoCredito)) return false;
        CartaoCredito that = (CartaoCredito) o;
        return ccv == that.ccv && Objects.equals(numero, that.numero) && Objects.equals(dataValidade, that.dataValidade);
    }

    /**
     * Devolve o hash code do objeto
     * @return o hash code do objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero, ccv, dataValidade);
    }
}
