package lapr.project.ui;
import lapr.project.controller.GerirComprasClienteController;
import lapr.project.model.Cliente;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import lapr.project.ui.Utils.UtilsRead;
import oracle.ucp.util.Pair;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * Classe que representa a UI relacionada com a compra de produtos por parte de um cliente.
 */
public class GerirComprasClienteUI {
    /**
     * Controller associado com a UI
     */
    private GerirComprasClienteController controller;
    /**
     *  Constructor que inicia o controller.
     */
    public GerirComprasClienteUI() {
        try {
            controller = new GerirComprasClienteController();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *   Método que devolve um pair com o produto e a quantidade desejada por um cliente.
     * @return Pair com produto na key e a quantidade desejada no value.
     */
    public Pair<Produto,Integer> getProdutoQuantidade() {
        List<Produto> listaProdutosDisponoveis = controller.getListaProdutos();
        if(listaProdutosDisponoveis.isEmpty()){
            System.out.println("Nao existem produtos no sistema !");
            return null;
        }
        Produto produto = (Produto) UtilsRead.apresentaESeleciona(listaProdutosDisponoveis,"Introduza o produto desejado:");
        if(produto == null)return null;
        int quantidade = UtilsRead.readIntegerFromConsole("Introduza a quantidade desejada:");
        if(quantidade <=0) return null;
        return new Pair<>(produto,quantidade);
    }
    /**
     *  Método que adiciona um produto ao carrinho de compras do cliente logado atualmente.
     */
    public void adicionarProdutoCarrinho(){
        Pair<Produto,Integer> produtoDesejado =  getProdutoQuantidade();
        if(produtoDesejado == null) return;
        List<Farmacia> farmaciasDisponiveis = controller.getListaFarmacias();
        Farmacia farmacia = (Farmacia) UtilsRead.apresentaESeleciona(farmaciasDisponiveis,"Indique a farmacia que pretende fazer o pedido:");
        if(farmacia == null)return;
        controller.adicionarProdutoCesta(produtoDesejado.get1st(),produtoDesejado.get2nd(),farmacia);
    }
    /**
     * Método que imprime um carrinho de compras passado como parametro
     * @param carrinho carrinho de compras que se pretende imprimir.
     */
    public void imprimirCarrinho(Map<Farmacia,Map<Produto,Integer>> carrinho) {
        System.out.println("----------CARRINHO COMPRAS---------");
        for(Farmacia farmacia: carrinho.keySet()){
            Map<Produto,Integer> produtoFarmacia = carrinho.get(farmacia);
            System.out.println(String.format("----------%s---------",farmacia.getNome()));
            for (Produto p : produtoFarmacia.keySet()){
                System.out.println(p.toString());
            }
        }
    }
    public void realizarCheckout(){
        System.out.println("------------CHECKOUT------------");
        int nif = UtilsRead.readIntegerFromConsole("Introduza o nif associado a encomenda");
        Map<Farmacia, Map<Produto, Integer>> carrinhoCompras = controller.getCarrinhoCompras();
        imprimirCarrinho(carrinhoCompras);
        boolean pagarComCreditos = UtilsRead.confirma("Deseja utilizar os seus créditos da plataforma para pagar a taxa de transporte?[s/n]");
        if(UtilsRead.confirma("Confirma o pagamento pelos produtos? [s/n]")){
            Cliente clienteLogado = controller.getClienteLogado();
            if(controller.gerarEncomenda(carrinhoCompras,nif,clienteLogado,pagarComCreditos) != -1){
                System.out.println("Sucesso ao gerar encomenda");
            }else{
                System.out.println("Erro ao gerar encomenda");
            }
        }
    }
}
