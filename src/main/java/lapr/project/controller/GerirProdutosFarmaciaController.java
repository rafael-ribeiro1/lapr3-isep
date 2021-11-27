package lapr.project.controller;

import lapr.project.data.GestorHandler;
import lapr.project.data.ProdutoHandler;
import lapr.project.data.ProdutosFarmaciaHandler;
import lapr.project.model.Farmacia;
import lapr.project.model.Produto;
import lapr.project.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Controller que gere os produtos de uma farmácia.
 */
public class GerirProdutosFarmaciaController {
    /**
     * Handler dos produtos de uma farmácia.
     */
    private ProdutosFarmaciaHandler farmaciaHandler;
    /**
     * Handler do gestor de farmácia.
     */
    private GestorHandler gestorHandler;
    /**
     * Handler dos produtos da farmácia.
     */
    private ProdutoHandler produtoHandler;
    /**
     * Contrutor do GerirProdutosFarmaciaController
     * @throws IOException
     */
    public GerirProdutosFarmaciaController() throws  IOException {
        farmaciaHandler=new ProdutosFarmaciaHandler();
        gestorHandler= new GestorHandler();
        produtoHandler = new ProdutoHandler();
    }
    /**
     * Método para mudar o Handler da Farmacia
     * @param farmaciaHandler
     */
    public void setFarmaciaHandler(ProdutosFarmaciaHandler farmaciaHandler) {
        this.farmaciaHandler = farmaciaHandler;
    }
    /**
     * Método para mudar o Handler de Gestores
     * @param gestorHandler
     */
    public void setGestorHandler(GestorHandler gestorHandler) {
        this.gestorHandler = gestorHandler;
    }
    /**
     * Método para mudar o handler de produtos (Apenas utilizado em testes unitarios)
     */
        public void setProdutoHandler(ProdutoHandler produtoHandler){this.produtoHandler=produtoHandler;}
    /**
     * Método que serve para adicionar um produto na farmácia já existente no sistema
     * @param idProduto id do produto a adicionar na farmácia
     * @param quantidade quantidade de stock a adicionar.
     * @param email Email do gestor da farmácia.
     * @return true se foi atualizado com sucesso o stock.
     */
    public boolean addProdutoFarmacia(int idProduto, int quantidade, String email) {
            int idFarmacia = gestorHandler.getFarmaciaFromGestorEmail(email);
            return farmaciaHandler.addProdutoFarmacia(idProduto, idFarmacia, quantidade);
    }

    /**
     * Método que serve para remover um produto da farmacia
     * @param idProduto id do produto a ser removido.
     * @param email email do gestor da farmacia.
     * @return true se foi removido, false se houve algum erro
     */
    public boolean removerProdutoFarmacia(int idProduto, String email) {
            int idFarmacia = gestorHandler.getFarmaciaFromGestorEmail(email);
            return farmaciaHandler.removerProdutoFarmacia(idProduto, idFarmacia);
    }

    /**
     * Retorna a lista de produtos no sistema
     * @return ArrayList de produtos ou null se ocorrer um erro
     */
    public List<Produto> getListaProdutos () {
        return produtoHandler.getListaProdutos();
    }

    /**
     * Métood que devolve um lista de produtos de uma certa farmácia
     * @param user Gestor da farmácia a atualizar a aplicação.
     * @return lista de produtos de uma farmácia
     */
    public List<Produto> getProdutosFarmaciaLoggado(User user) {
            InstanciaAtual.getInstance().setUserLogado(user);
            String email = user.getEmail();
            Farmacia farmacia = new Farmacia(gestorHandler.getFarmaciaFromGestorEmail(email));
            return farmaciaHandler.getProdutosFarmacia(farmacia);
    }


    /**
     * Método que atualiza o stock de uma farmácia
     * @param stock stock a ser atualizado na farmácia.
     * @param idProduto id do produto a ser atualizado.
     * @param email email do gestor da farmácia.
     * @return true se atualizou, false se correu algo mal
     */
    public boolean updateStock(int stock,int idProduto, String email) {
        int idFarmacia = gestorHandler.getFarmaciaFromGestorEmail(email);
        return farmaciaHandler.updateStock(idProduto,idFarmacia, stock);
    }




}










