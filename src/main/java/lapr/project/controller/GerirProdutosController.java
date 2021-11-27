package lapr.project.controller;

import lapr.project.data.ProdutoHandler;
import lapr.project.model.Produto;

import java.io.IOException;
import java.util.List;

/**
 *Controller dos produtos do sistema.
 */
public class GerirProdutosController {
    /**
     * Handler dos produtos do sistema.
     */
    private ProdutoHandler handler;

    /**
     * contrutor do controller
     * @throws IOException
     */
    public GerirProdutosController() throws IOException {
        handler = new ProdutoHandler();
    }

    /**
     * Métood que retorna o handler do ProdutoDB
     * @return
     */
    public ProdutoHandler getHandler() {
        return handler;
    }

    /**
     * Método para mudar o handler do ProdutoDB
     * @param handler
     */
    public void setHandler(ProdutoHandler handler) {
        this.handler = handler;
    }


    /**
     * Método que adiciona um produto no sistema
     * @param prod
     * @return devolve um inteiro com o numero do id do produto ou então -1 caso não seja adicionado
     */
    public int addProduto(Produto prod) {
        int id = -1;
        if (prod.getPeso() < 0 || prod.getPreco() <0 || prod.getValorUnitario() < 0){
            return id;
        }

        id = handler.addProduto(prod);

        return id;
    }


    /**
     * Método que serve para atualizar um produto no sistema
     * @param info
     * @param produto
     * @return true se atualizou, false se correu alguma coisa mal
     */
    public Boolean atualizarProduto(String[] info,Produto produto) {
        return handler.updateProduto(info,produto);
    }


    /**
     * Retorna a lista de produtos no sistema
     * @return ArrayList de produtos ou null se ocorrer um erro
     */
    public List<Produto> getListaProdutos () {
            return handler.getListaProdutos();
    }

    /**
     * Método que devolve um produto do sistema
     * @param idProduto
     * @return produto caso exista, null caso não exista
     */
    public Produto getProduto(int idProduto){
        return handler.getProduto(idProduto);
    }


}
