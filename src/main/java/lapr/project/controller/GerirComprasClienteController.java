package lapr.project.controller;


import lapr.project.data.*;
import lapr.project.model.Cliente;
import lapr.project.model.Encomenda;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import lapr.project.utils.Constantes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Classe que representa um controller das compras de um cliente.
 */
public class GerirComprasClienteController {
    /**
     * Handler dos produtos
     */
    private ProdutoHandler produtoHandler;
    /**
     * Handler da farmácia.
     */
    private FarmaciaHandler farmaciaHandler;
    /**
     * Handler dos produtos de uma farmácia.
     */
    private ProdutosFarmaciaHandler produtosFarmaciaHandler;
    /**
     * Handler das encomendas do sistema.
     */
    private EncomendaDataHandler encomendaDataHandler;
    /**
     * Handler dos emails.
     */
    private EmailHandler emailHandler;
    /**
     * Handler dos clientes.
     */
    private ClientesHandler clientesHandler;
    /**
     *  Controller das entregas do sistema.
     */
    private GerirEntregasController entregasController;

    /**
     * Construtor que instância os handlers necessários
     * @throws IOException
     * @throws SQLException
     */
    public GerirComprasClienteController() throws IOException,SQLException{
        farmaciaHandler = new FarmaciaHandler();
        produtosFarmaciaHandler = new ProdutosFarmaciaHandler();
        emailHandler = new EmailHandler();
        encomendaDataHandler = new EncomendaDataHandler();
        produtoHandler = new ProdutoHandler();
        clientesHandler = new ClientesHandler();
        entregasController = new GerirEntregasController();
    }

    /**
     * Setter do controller das entregas (Usado para testes apenas).
     * @param entregasController controller das entregas
     */
    public void setEntregasController(GerirEntregasController entregasController) {
        this.entregasController = entregasController;
    }

    /**
     * Setter do handler dos clientes (Apenas utilizados para testes unitários).
     * @param clientesHandler Handler dos clientes.
     */
    public void setClientesHandler(ClientesHandler clientesHandler) {
        this.clientesHandler = clientesHandler;
    }

    /**
     * Setter do handler das farmácias (Apenas utilizados para testes unitários).
     * @param farmaciaHandler handler das farmácias.
     */
    public void setFarmaciaHandler(FarmaciaHandler farmaciaHandler) {
        this.farmaciaHandler = farmaciaHandler;
    }

    /**
     * Setter dos produtos da farmacia de um farmacia (Apenas utilizados para testes unitários).
     * @param produtosFarmaciaHandler Handler dos produtos de uma farmacia.
     */
    public void setProdutosFarmaciaHandler(ProdutosFarmaciaHandler produtosFarmaciaHandler) {
        this.produtosFarmaciaHandler = produtosFarmaciaHandler;
    }

    /**
     * Setter do handler das encomendas (Apenas utilizados para testes unitários).
     * @param encomendaDataHandler Handler das encomendas.
     */
    public void setEncomendaDataHandler(EncomendaDataHandler encomendaDataHandler) {
        this.encomendaDataHandler = encomendaDataHandler;
    }

    /**
     * Setter do handler dos emails (Apenas utilizados para testes unitários).
     * @param emailHandler Handler dos emails
     */
    public void setEmailHandler(EmailHandler emailHandler) {
        this.emailHandler = emailHandler;
    }

    /**
     *  Setter do handler dos produtos do sistema(Apenas utilizados para testes unitários).
     * @param produtoHandler handlers dos produtos.
     */
    public void setProdutoDB(ProdutoHandler produtoHandler) {
        this.produtoHandler = produtoHandler;
    }
    /**
     * Método que retorna a lista de farmácias registadas no sistema.
     * @return Lista que possui todas as farmacias registadas no sistema.
     */
    public List<Farmacia> getListaFarmacias() {
            return farmaciaHandler.getListaFarmacias();
    }

    /**
     * Método que retorna a lista de produtos registados no sistema.
     * @return Lista que posssui todos os produtos registados no sistema.
     */
    public List<Produto> getListaProdutos() {
            return produtoHandler.getListaProdutos();
    }

    /**
     * Método que adiciona um produto a cesta do cliente
     * @param produto Produto a adicionar
     * @param quantidade Quantidade a adicionar
     * @param farmacia Farmácia a qual se pretende encomendar este produto
     */
    public boolean adicionarProdutoCesta(Produto produto, int quantidade, Farmacia farmacia) {
        if(produto.getPeso() * quantidade > Constantes.PESO_MAXIMO_ENTREGA)return false;
        InstanciaAtual.getInstance().introduzirProdutoNoCarrinho(produto,quantidade,farmacia);
        return true;
    }

    /**
     *  Método que ira remover o stock de um produto a farmacia
     * @param farmacia farmacia que pertence
     * @param produtoDesejado Produto que se pretende retirar stock
     * @param quantidade quantidade
     * @param cliente cliente atual
     * @return preco
     */
    public double verificarStockProduto(Farmacia farmacia, Produto produtoDesejado , int quantidade, Cliente cliente){
        if(!produtosFarmaciaHandler.verificarStockProduto(produtoDesejado, quantidade, farmacia)){
            if(!entregasController.realizarTransferenciaProduto(farmacia,produtoDesejado,quantidade)){
                String conteudo = String.format("Caro %s, retirou-se o produto %s que possua em carrinho uma vez que nao possuimos stock para cumprir pedido", cliente.getNome(), produtoDesejado);
                String assunto = "Aviso relativo ao seu carrinho";
                emailHandler.sendEmail(cliente.getEmail(), assunto, conteudo);
                return 0;
            }
        }
        return produtoDesejado.getPreco()*quantidade;
    }

    /**
     * Método que calcula a taxa a pagar pelo cliente para a encomenda.
     * @param cliente Cliente a realizar a encomenda.
     * @param pagarComCreditos Boolean a dizer se o cliente quer pagar com creditos no sistema.
     * @return Valor a pagar pela entrega.
     */
    public double calcularTaxaEntrega(Cliente cliente,Boolean pagarComCreditos) {
        if(pagarComCreditos){
            int creditoParaPagar = Constantes.CREDITOS_PAGAR_TAXA_ENTREGA;
            if(clientesHandler.retirarCreditos(cliente,creditoParaPagar)) return 0;
        }
        return Constantes.CUSTO_ENTREGA;
    }

    /**
     * Método que vai gerar encomenda na base de dados e vai mandar email por cada order que o cliente realizar a uma farmacia
     * @param carrinhoComprasTotal carrinho de compras
     * @param nif nif associado a encomenda
     * @param cliente Cliente que pretende pagar a encomenda
     * @param pagarComCreditos Se o cliente pretende pagar com créditos
     * @return id da Farmácia mais próxima se existir, caso contrário, devolve -1
     */
    public int gerarEncomenda(Map<Farmacia,Map<Produto,Integer>> carrinhoComprasTotal, int nif,Cliente cliente,boolean pagarComCreditos) {
            Farmacia farmacia = null;
            for(Map.Entry<Farmacia,Map<Produto,Integer>> entry: carrinhoComprasTotal.entrySet()){

                double custo=0;
                farmacia = entry.getKey();
                Map<Produto,Integer> produtosFarmacia = entry.getValue();
                Map<Produto,Integer> produtosEncomenda = new TreeMap<>();
                for(Map.Entry<Produto,Integer> produtos : produtosFarmacia.entrySet()){
                    Produto p = produtos.getKey();
                    int quantidade = produtos.getValue();
                    double custoTemp= verificarStockProduto(farmacia,p,quantidade,cliente);
                    if(custoTemp==0){
                        continue;
                    }
                    custo+=custoTemp;
                    produtosEncomenda.put(p,quantidade);
                }
                if(produtosEncomenda.isEmpty()){
                   continue;
               }
                Farmacia farmaciaMaisPerto = entregasController.getFarmaciaMaisPertoDeUser(cliente.getEndereco());
               // caso a farmacia mais perto nao seja a farmacia de qual o cliente encomendou entao ira haver transferencia do produto da farmacia que ele encomendou para a farmacia mais perto
                if(farmaciaMaisPerto!=farmacia && farmaciaMaisPerto != null ){
                        List<Farmacia> farmaciaFornecedora = new ArrayList<>();
                        farmaciaFornecedora.add(farmacia);
                        for (Map.Entry<Produto,Integer> compras : produtosEncomenda.entrySet()){
                            entregasController.realizarTransferenciaEntreDuasFarmaciasDefinidas(farmaciaMaisPerto,farmaciaFornecedora,compras.getKey(),compras.getValue());
                        }
                        farmacia=farmaciaMaisPerto;
               }


                custo+= calcularTaxaEntrega(cliente,pagarComCreditos);

                Encomenda encomendaEnviar = new Encomenda(nif,produtosEncomenda, Encomenda.EstadoEncomenda.ENVIO_PRONTO,farmacia,custo,cliente);
                if(!encomendaDataHandler.gerarEncomenda(encomendaEnviar)) return -1;
                emailHandler.sendEmail(cliente.getEmail(),String.format("Fatura da encomenda realizada pelo %s com o cartao %s",cliente.getNome(),
                        cliente.getCartaoCredito().getNumero()),String.format("------------Compra----------%nCliente:%s %nNIF:%d %nCusto: %.2f%nCartao Credito:%s",
                        cliente.getNome(),nif,custo,cliente.getCartaoCredito().getNumero()));
            }
            return farmacia != null ? farmacia.getId() : -1;
    }

    /**
     * Método que retorna o carrinho de compras do cliente a usar a aplicação atualmente.
     * @return Carrinho de compras do cliente logado atualmente.
     */
    public Map<Farmacia, Map<Produto, Integer>> getCarrinhoCompras() {
        return InstanciaAtual.getInstance().getCarrinhoCompras();
    }

    /**
     * Método que devolve o cliente a usar a aplicação atualmente.
     * @return Cliente a utilizar a plataforma.
     */
    public Cliente getClienteLogado() {
        return InstanciaAtual.getInstance().getClienteLogado();
    }

    /**
     * Remove o produto do carrinho de compras
     * @param farmacia a farmácia relativa ao produto a remover
     * @param produto o produto a remover
     * @return true caso o produto seja removido, caso contrário devolve false
     */
    public boolean removerProdutoCarrinho(Farmacia farmacia, Produto produto){
        return InstanciaAtual.getInstance().removerProdutoCarrinho(farmacia, produto);
    }
}
