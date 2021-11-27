package lapr.project.model;

import java.util.*;

/**
 * Classe que representa uma farmacia.
 */
public class Farmacia implements Comparable<Farmacia> {

    /**
     * id da Farmacia
     */
    private int id;
    /**
     * Nome da farmácia
     */
    private String nome;
    /**
     * Estacionamento da farmacia
     */
    private Estacionamento estacionamento;
    /**
     * Endereco da farmacia
     */
    private Endereco endereco;
    /**
     * Estafetas associados a farmacia
     */
    private Set<Estafeta> estafetasEmpregados;
    /**
     * Produtos da farmacai.
     */
    private Map<Produto, Integer> produtosFarmacia;

    /**
     * Instantiates a new Farmacia.
     *
     * @param id  id da farmacia
     */
    public Farmacia(int id) {
        this.id = id;
    }

    /**
     * Instantiates a new Farmacia.
     *
     * @param id     id da farmacia
      * @param endereco  endereco da farmacia
     */
    public Farmacia(int id, Endereco endereco) {
        this.id = id;
        this.endereco = endereco;
    }

    /**
     * Instantiates a new Farmacia.
     *
     * @param nome     the nome
     * @param endereco endereco da farmacia
     */
    public Farmacia(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.estafetasEmpregados = new HashSet<>();
        this.produtosFarmacia = new HashMap<>();
    }


    /**
     * Instantiates a new Farmacia.
     *
     * @param id   id da farmacia
     * @param nome nome da farmacia
     */
    public Farmacia(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Instantiates a new Farmácia.
     *
     * @param id       id da farmácia
     * @param nome     nome da farmácia
     * @param endereco  endereço da farmácia
     */
    public Farmacia(int id, String nome, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    /**
     * Sets estacionamento.
     *
     * @param estacionamento the estacionamento
     */
    public void setEstacionamento(Estacionamento estacionamento) {
        this.estacionamento = estacionamento;
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
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gets estacionamento.
     *
     * @return the estacionamento
     */
    public Estacionamento getEstacionamento() {
        return estacionamento;
    }

    /**
     * Gets endereco.
     *
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Sets endereco.
     *
     * @param endereco the endereco
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public int compareTo(Farmacia o) {
        return nome.compareTo(o.nome);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Farmacia)) return false;
        Farmacia farmacia = (Farmacia) o;
        if (nome != null && ((Farmacia) o).nome != null)
            return getId() == farmacia.getId() && getNome().equals(farmacia.getNome());
        return getId() == farmacia.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome());
    }
}
