package lapr.project.ui;
import lapr.project.controller.InstanciaAtual;
import lapr.project.ui.Utils.UtilsRead;
/**
 *  Classe que representa uma interface gráfica mostrada quando um cliente da plataforma realiza login
 */
public class ClienteUI {
    /**
     *  Método que representa o menu inicial do Cliente.
     */
    public void menuInicial(){
        loginInfo();
        int opcao = UtilsRead.readIntegerFromConsole("Opção que deseja:");
        GerirComprasClienteUI ui = new GerirComprasClienteUI();
        switch (opcao){
            case 1:
                ui = new GerirComprasClienteUI();
                ui.adicionarProdutoCarrinho();
                menuInicial();
                break;
            case 2:
                ui.imprimirCarrinho(InstanciaAtual.getInstance().getCarrinhoCompras());
                menuInicial();
                break;
            case 3:
                ui.realizarCheckout();
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
     * Método que imprime as informacoes do  menu do cliente
     */
    private void loginInfo(){
        System.out.println("-------------Menu Cliente--------------");
        System.out.println("1- Ver lista de produtos \n2- Ver carrinho de compras \n3- Realizar checkout \n0- Exit");
    }
}
