package lapr.project.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Classe que representa uma Encomenda.
 */
public class Encomenda {
    /**
     * id da encomenda
     */
    private int id;
    /**
     * O nif do cliente
     */
    private final int nifEncomenda;
    /**
     * Produtos encomendados
     */
    private Map<Produto,Integer> produtosEncomendados;
    /**
     * Estado associado com a encomenda
     */
    private EstadoEncomenda estadoEncomenda;
    /**
     * Farmacia de onde provem a encomenda
     */
    private Farmacia farmaciaEncomenda;
    /**
     * Custo da encomenda
     */
    private Double custo;
    /**
     * Cliente que encomendou
     */
    private Cliente cliente;
    /***
     * Peso da encomenda
     */
    private Double peso;

    /**
     * The enum Estado encomenda.
     */
    public enum EstadoEncomenda{
        /**
         * The Pagamento pendente.
         */
        PAGAMENTO_PENDENTE("Pagamento pendente"),
        /**
         * The Envio pronto estafeta.
         */
        ENVIO_PRONTO("Pronto a enviar"),
        /**
         * Enviado estado encomenda.
         */
        ENVIADO("Enviado"),
        /**
         * Entregue estado encomenda.
         */
        ENTREGUE("Entregue"),
        /**
         * The Entregue creditos.
         */
        ENTREGUE_CREDITOS("Entregue e créditos gerados");

        private String descricao;

        EstadoEncomenda(String descricao) {
            this.descricao = descricao;
        }

        /**
         * Gets descricao.
         *
         * @return the descricao
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Instantiates a new Encomenda.
     *
     * @param id   id da encomenda
     * @param nifEncomenda   nif da encomenda
     * @param estadoEncomenda the estado encomenda
     */
    public Encomenda(int id, int nifEncomenda, EstadoEncomenda estadoEncomenda) {
        this.id = id;
        this.nifEncomenda = nifEncomenda;
        this.estadoEncomenda = estadoEncomenda;
        this.produtosEncomendados = new HashMap<>();
        this.peso=0d;
    }

    public Encomenda(int id, int nifEncomenda, Cliente cliente) {
        this.id = id;
        this.nifEncomenda = nifEncomenda;
        this.cliente = cliente;
        this.produtosEncomendados = new HashMap<>();
        this.peso = 0d;
    }

    /**
     * Instantiates a new Encomenda.
     *
     * @param id                   the id
     * @param nifEncomenda           the nif cliente
     * @param produtosEncomendados the produtos encomendados
     * @param estadoEncomenda      the estado encomenda
     */
    public Encomenda(int id, int nifEncomenda, Map<Produto, Integer> produtosEncomendados, EstadoEncomenda estadoEncomenda) {
        this.id = id;
        this.nifEncomenda = nifEncomenda;
        this.produtosEncomendados = produtosEncomendados;
        this.estadoEncomenda = estadoEncomenda;
        setPeso(produtosEncomendados);
    }

    /**
     * Instantiates a new Encomenda.
     *
     * @param id         the id
     * @param nifEncomenda the nif cliente
     */
    public Encomenda(int id, int nifEncomenda) {
        this.id = id;
        this.nifEncomenda = nifEncomenda;
        this.produtosEncomendados = new HashMap<>();
        this.peso=0d;
    }

    /**
     * Instantiates a new Encomenda.
     *
     * @param nifEncomenda           the nif cliente
     * @param produtosEncomendados the produtos encomendados
     * @param estadoEncomenda      the estado encomenda
     * @param farmaciaEncomenda    the farmacia encomenda
     * @param custo                the custo
     * @param cliente              the cliente
     */
    public Encomenda(int nifEncomenda, Map<Produto, Integer> produtosEncomendados, EstadoEncomenda estadoEncomenda, Farmacia farmaciaEncomenda, Double custo, Cliente cliente) {
        this.nifEncomenda = nifEncomenda;
        this.produtosEncomendados = produtosEncomendados;
        this.estadoEncomenda = estadoEncomenda;
        this.farmaciaEncomenda = farmaciaEncomenda;
        this.custo = custo;
        this.cliente = cliente;
        setPeso(produtosEncomendados);
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
     * Sets peso da encomenda.
     *
     * @param produtosEncomendados produtos encomendados
     */
    public void setPeso(Map<Produto, Integer> produtosEncomendados) {
        this.peso=0.0;
        for(Map.Entry<Produto, Integer> entry:produtosEncomendados.entrySet ()){
            //peso+= peso do produto * quantidade
            peso+=(entry.getKey ().getPeso () * entry.getValue ());
        }
    }

    /**
     * Gets nif encomenda.
     *
     * @return o nif da encomenda
     */
    public int getNifEncomenda() {
        return nifEncomenda;
    }

    /**
     * Gets farmacia encomenda.
     *
     * @return the farmacia encomenda
     */
    public Farmacia getFarmaciaEncomenda() {
        return farmaciaEncomenda;
    }

    /**
     * Gets custo da encomenda.
     *
     * @return the custo
     */
    public Double getCusto() {
        return custo;
    }

    /**
     * Gets cliente.
     *
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Gets peso da encomenda.
     *
     * @return the peso
     */
    public Double getPeso() {
        return peso;
    }

    /**
     * Gets estado encomenda.
     *
     * @return the estado encomenda
     */
    public EstadoEncomenda getEstadoEncomenda() {
        return estadoEncomenda;
    }

    /**
     * Gets produtos encomendados.
     *
     * @return the produtos encomendados
     */
    public Map<Produto, Integer> getProdutosEncomendados() {
        return produtosEncomendados;
    }

    /**
     * Sets cliente.
     *
     * @param cliente cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Adiciona produto a encomenda.
     *
     * @param p   produto
     * @param qtd quantidade
     */
    public void addProduto(Produto p, int qtd){
        produtosEncomendados.put ( p,qtd );
        this.peso+=(p.getPeso ()*qtd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Encomenda)) return false;
        Encomenda encomenda = (Encomenda) o;
        return getNifEncomenda() == encomenda.getNifEncomenda() && getEstadoEncomenda().equals(encomenda.getEstadoEncomenda())
                && getFarmaciaEncomenda().equals(encomenda.getFarmaciaEncomenda()) && getCusto().equals(encomenda.getCusto())
                && getPeso().equals(encomenda.getPeso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNifEncomenda(), getEstadoEncomenda(), getFarmaciaEncomenda(), getCusto(), getPeso());
    }
}
