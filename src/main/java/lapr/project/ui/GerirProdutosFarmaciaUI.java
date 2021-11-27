package lapr.project.ui;

import lapr.project.controller.*;
import lapr.project.model.Produto;
import lapr.project.ui.Utils.UtilsRead;

import java.io.IOException;
import java.util.List;

public class GerirProdutosFarmaciaUI {
    private final String LISTA_PRODUTOS = "- Lista de produtos -";
    private final String FORMATACAO_LISTA = "%d - %s\n";
    /**
     * Controller
     */
    private GerirProdutosFarmaciaController gerirProdutosFarmaciaController;
    /**
     * Controller de gerir produtos de uma farmacia
     */
    private GerirProdutosController gerirProdutosController;

    /**
     * Construtor que inicializa o controller e o scanner do teclado
     */
    public GerirProdutosFarmaciaUI() {
        try{
            gerirProdutosFarmaciaController = new GerirProdutosFarmaciaController();
            gerirProdutosController = new GerirProdutosController();
        }catch (IOException e){
            System.out.println("Erro Fatal");
        }
    }
    /**
     * Método que adiciona um produto do sistema a uma farmacia
     */
    public void addProduto() {
        System.out.println("--- Processo de adicionar Produto---");
        List<Produto> produtos =  gerirProdutosController.getListaProdutos();
        if (produtos == null) return;
        if (produtos.isEmpty()) return;
        System.out.println(LISTA_PRODUTOS);
        for (Produto produto : produtos)
            System.out.printf(FORMATACAO_LISTA, produto.getId(), produto.getNome());
        int idProduto= UtilsRead.readIntegerFromConsole("Digite o id do produto que quer adicionar:");
        int quantidade = UtilsRead.readIntegerFromConsole("quantidade: ");
        String email = InstanciaAtual.getInstance().getCurrentUser().getEmail();
        boolean add = gerirProdutosFarmaciaController.addProdutoFarmacia(idProduto, quantidade, email);
        if (add) {
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Erro na adição do produto");
        }
    }
    /**
     * Método que remove um produto de uma farmacia
     */
    public void removerProduto() {
        System.out.println("--- Processo de remover Produtos---");
        /*
       List<Produto> produtos = gerirProdutosFarmaciaController.getProdutosFarmaciaLoggado();
        if (produtos == null || produtos.isEmpty()) return;
        System.out.println(LISTA_PRODUTOS);
        for (Produto produto : produtos)
            System.out.printf(FORMATACAO_LISTA, produto.getId(), produto.getNome());
        int idProduto= UtilsRead.readIntegerFromConsole("Digite o id do produto que quer remover:");
        String email = InstanciaAtual.getInstance().getCurrentUser().getEmail();
        boolean add = gerirProdutosFarmaciaController.removerProdutoFarmacia(idProduto, email);
        if (add) {
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Erro na remoção do produto");
        }

         */
    }
    /**
     * Método que atualiza o stock de uma farmacia com um produto pretendido
     */
    public void updateStockFarmacia() {
        /*
        System.out.println("--- Processo de atualizar stock do produto---");
        List<Produto> produtos = gerirProdutosFarmaciaController.getProdutosFarmaciaLoggado();
        if (produtos == null || produtos.isEmpty()) return;
        System.out.println(LISTA_PRODUTOS);
        for (Produto produto : produtos)
            System.out.printf(FORMATACAO_LISTA, produto.getId(), produto.getNome());
        int idProduto= UtilsRead.readIntegerFromConsole("Digite o id do produto que quer atualizar stock:");
        String email = InstanciaAtual.getInstance().getCurrentUser().getEmail();
        int stock = UtilsRead.readIntegerFromConsole("Indique o número de stock atualizado:");
        boolean atualizar = gerirProdutosFarmaciaController.updateStock(stock,idProduto, email);
        if (atualizar) {
            System.out.println("Stock atualizado com sucesso!");
        } else {
            System.out.println("Erro na atualização do stock do produto");
        }

         */
    }
}



