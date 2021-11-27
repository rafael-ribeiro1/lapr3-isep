package lapr.project.model;

import java.util.Objects;

/**
 * Classe que representa um produto.
 */
public class Produto implements Comparable<Produto> {
    /**
     * Identificador do produto
     */
    private int id;
    /**
     * Nome do produto
     */
    private String nome;
    /**
     * Valor unitário
     */
    private double valorUnitario;
    /**
     * Preco do produto
     */
    private double preco;
    /**
     * Peso do produto
     */
    private double peso;
    /**
     * Descricoo do produto
     */
    private String descricao;

    /**
     * Constrói uma instância de Produto recebendo por parâmetro:
     * @param id Identificador do produto
     * @param nome Nome do produto
     * @param valorUnitario O Valor Unitário
     * @param preco Preco do produto
     * @param peso Peso do produto
     * @param descricao Descricao do produto
     */
    public Produto(int id, String nome, double valorUnitario, double preco, double peso, String descricao) {
        this.id = id;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.preco = preco;
        this.peso = peso;
        this.descricao = descricao;
    }

    /**
     * Constrói uma instância de Produto recebendo por parâmetro:
     * @param nome Nome do produto
     * @param valorUnitario O Valor Unitário
     * @param preco Preco do produto
     * @param peso Peso do produto
     * @param descricao Descricao do produto
     */
    public Produto(String nome, double valorUnitario, double preco, double peso, String descricao) {
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.preco = preco;
        this.peso = peso;
        this.descricao = descricao;
    }

    /**
     * Constrói uma instância de Produto recebendo por parâmetro:
     * @param nome Nome do Produto
     * @param peso Peso do Produto
     */
    public Produto(String nome, double peso) {
        this.nome = nome;
        this.peso = peso;
    }

    /**
     * Constrói uma instância de Produto recebendo por parâmetro:
     * @param preco Preço do Produto
     * @param nome Nome do Produto
     */
    public Produto(double preco, String nome) {
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Constrói uma instância de Produto recebendo por parâmetro:
     * @param id O identificador único do Produto
     * @param nome O nome do Produto
     * @param preco O preço do Produto
     */
    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Devolve o identificador único do Produto
     * @return o identificador único do Produto
     */
    public int getId() {
        return id;
    }

    /**
     * Devolve o nome do Produto
     * @return o nome do Produto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve o peso do Produto
     * @return o peso do Produto
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Devolve o valor unitário do Produto
     * @return o valor unitário do Produto
     */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Devolve o preço do Produto
     * @return o preço do Produto
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Devolve a descrição do Produto
     * @return a descrição do Produto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Modifica o identificador único do Produto
     * @param id o identificador único do Produto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Modifica o nome do Produto
     * @param nome o nome do Produto
     */
    public void setNome(String nome) {
        this.nome= nome;
    }

    /**
     * Modifica o valor unitário do Produto
     * @param valorUnitario o valor unitário do Produto
     */
    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario= valorUnitario;
    }

    /**
     * Modifica o preço do Produto
     * @param preco o preço do Produto
     */
    public void setPreco(double preco) {
        this.preco= preco;
    }

    /**
     * Modifica o peso do Produto
     * @param peso o peso do Produto
     */
    public void setPeso(double peso) {
        this.peso= peso;
    }

    /**
     * Modifica a descrição do Produto
     * @param descricao a descrição do Produto
     */
    public void setDescricao(String descricao) {
        this.descricao= descricao;
    }

    /**
     * Verifica se o objeto passado por parâmetro é igual ao Produtoo.
     * Se os objetos partilharem a mesma referência, devolve true. Se o objeto recebido
     * por parâmetro for nulo ou as classes dos objetos forem diferentes, devolve false.
     * Por fim, efetua um downcasting do objeto recebido por parâmetro para o tipo Produto,
     * comparando o valor unitário, preço e nome de cada um.
     * @param o o objeto a comparar
     * @return true se forem iguais, caso contrário retorna false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Double.compare(produto.valorUnitario, valorUnitario) == 0
                && Double.compare(produto.preco, preco) == 0 && Double.compare(produto.peso, peso) == 0
                && nome.equals(produto.nome) && descricao.equals(produto.descricao);
    }

    /**
     * Devolve o valor do hash code para o objeto
     * @return o valor do hash code para o objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, valorUnitario, preco, peso, descricao);
    }

    /**
     * Devolve a representação textual do Produto
     * @return a representação textual do Produto
     */
    @Override
    public String toString() {
        return String.format("%s custa %.2f ",nome,preco);
    }

    /**
     * Compara dois produtos pelo nome, ordenando alfabeticamente.
     *
     * @param o  o produto a comparar
     * @return 1 se o nome surge primeiro que o nome passado por parâmetro, 0 se forem iguais, -1 caso contrário
     */
    @Override
    public int compareTo(Produto o) {
        return nome.compareTo(o.nome);
    }
}
