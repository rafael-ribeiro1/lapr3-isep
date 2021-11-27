package lapr.project.ui;
import lapr.project.ui.Utils.UtilsRead;
/**
 * Classe que representa a interface gráfica de um gestor
 */
public class GestorUI {
    /**
     *  Interface grãfica no qual o gestor será redirecionado.
     */
    GerirProdutosFarmaciaUI ui ;
    /**
     * Constructor que inicia os atributos da classe necessarios
     */
    public GestorUI() {
        ui = new GerirProdutosFarmaciaUI();
    }
    /**
     * Método que representa a interface grafica de um menu para o  gestor
     */
    public void menuInicial(){
        opcoesGestor();
        int opcao = UtilsRead.readIntegerFromConsole("Introduza a opção que deseja:");
        switch (opcao){
            case 1:
                ui.addProduto();
                menuInicial();
                break;
            case 2:
                ui.removerProduto();
                menuInicial();
                break;
            case  3:
                ui.updateStockFarmacia();
                menuInicial();
                break;
            case 0:
                break;
            default:
                System.out.println("Numéro invalido");
                menuInicial();
        }
    }
    /**
     * Método que imprime todas as opcoes do menu do gestor
     */
    private void opcoesGestor(){
        System.out.println("-------------Menu Gestor--------------");
        System.out.println("1- adicionar produtos farmacia\n2-remover produtos farmacia\n3-Atualizar produtos farmacia \n0- Exit");
    }
}

