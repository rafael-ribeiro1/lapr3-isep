package lapr.project.ui;
import lapr.project.ui.Utils.UtilsRead;
import java.io.IOException;
/**
 * Classe que representa uma interface gráfica mostrada quando um administrator da plataforma realiza login
 */
public class AdminUI {
    /**
     * Método que representa menu inicial do administrador
     */
    public void menuInicial () {
            loginInfo();
            int opcao = UtilsRead.readIntegerFromConsole("Opção que deseja:");
            switch (opcao){
                case 1:
                    try {
                        GerirEstafetaUI ui =  new GerirEstafetaUI() ;
                        ui.addEstafeta();
                        menuInicial ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        RegistoFarmaciaUI farmaciaUI = new RegistoFarmaciaUI();
                        farmaciaUI.registarFarmacia();
                        menuInicial ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        RegistoFarmaciaUI regGestor = new RegistoFarmaciaUI();
                        regGestor.registarGestor();
                        menuInicial ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        GerirEstafetaUI atEst = new GerirEstafetaUI();
                        atEst.atualizarEstafeta();
                        menuInicial ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        RegistoFarmaciaUI farmaciaUI = new RegistoFarmaciaUI();
                        farmaciaUI.atualizarFarmacia();
                        menuInicial ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opções invalida !");
                    menuInicial();
            }
        }
    /**
     * Método que imprime todas as opções disponiveis do menu do administrador
     */
    private static void loginInfo () {
            System.out.println("Bem-vindo Administrador!");
            System.out.println("----------------------------------------");
            System.out.println("         Menu De Administrador          ");
            System.out.println("----------------------------------------\n");
            System.out.println("1- Adicionar Estafeta\n2- Registar Farmácia\n3- Registar gestor de farmácia\n4- Atualizar Estafeta\n5- Atualizar Farmacia \n0- Exit");
        }
    }







