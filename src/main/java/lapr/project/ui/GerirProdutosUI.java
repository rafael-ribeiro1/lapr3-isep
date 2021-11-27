package lapr.project.ui;

import lapr.project.controller.GerirProdutosController;
import lapr.project.model.Produto;
import lapr.project.ui.Utils.UtilsRead;
import java.io.IOException;
import java.util.List;
public class GerirProdutosUI {
    /**
     * Controller
     */
    private GerirProdutosController controller;
    /**
     * Construtor que inicializa o controller e o scanner do teclado
     */
    public GerirProdutosUI() {
        try{
            controller = new GerirProdutosController();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Método que adiciona um produto ao sistema
     */
    public void addProduto(){
        System.out.println("--- Processo de adicionar Produto---");
        String nome = UtilsRead.readLineFromConsole("Nome do produto:");
        double precoUnitario = UtilsRead.readDoubleFromConsole("Preço unitário do produto:");
        double preco = UtilsRead.readDoubleFromConsole("Preco do produto:");
        double peso = UtilsRead.readDoubleFromConsole("Peso do produto:");
        String descricao = UtilsRead.readLineFromConsole("Descrição do produto:");
        int add = controller.addProduto(new Produto(nome,precoUnitario ,preco,peso,descricao));
        if (add != -1)
            System.out.println("Produto registado com sucesso!");
        else
            System.out.println("Produto não foi registado!");
    }
    /**
     * Método que atualiza as informacoes de um produto
     */
    public void atualizarProduto(){
        String[] info = new String[5];
        System.out.println("--- Processo de atualizar Produto---");
        List<Produto> produtos = controller.getListaProdutos();
        if (produtos == null || produtos.isEmpty()) return;
        System.out.println("- Lista de produtos -");
        for (Produto p : produtos)
            System.out.printf("%d - %s\n", p.getId(), p.getNome());
        int idprod = UtilsRead.readIntegerFromConsole("Id do produto que deseja atualizar");
        // nome,valor_unitario,preco,peso,descricao
        info[0] = UtilsRead.readLineFromConsole("Novo nome do produto:");
        info[1] =UtilsRead.readLineFromConsole("Preco unitário do produto");
        info[2] = UtilsRead.readLineFromConsole("Preco do produto:");
        info[3] = UtilsRead.readLineFromConsole("Peso do produto:");
        info[4] =UtilsRead.readLineFromConsole("Descricao do produto:");
        boolean at = controller.atualizarProduto(info, controller.getHandler().getProduto(idprod));
        if(at){
            System.out.println("Produto atualizado com sucesso");
        }else{
            System.out.println("Erro na atualização do produto");
        }
    }
}