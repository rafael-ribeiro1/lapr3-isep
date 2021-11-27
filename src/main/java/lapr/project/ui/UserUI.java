package lapr.project.ui;
import lapr.project.controller.InstanciaAtual;
import lapr.project.controller.UserController;
import lapr.project.ui.Utils.UtilsRead;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Classe que representa a Interface inicial do programa independente do tipo de user
 */
public class UserUI {
    private static final Scanner INPUT = new Scanner(System.in);

    /**
     * Método que invoca a UI
     * @return
     */
    public boolean run() {

        int maxTentativas = 3;
        boolean sucesso = false;
        do
        {
            maxTentativas--;
            String sId = UtilsRead.readLineFromConsole("Efetuar Login:\nIntroduza Email: ");
            String sPwd = UtilsRead.readLineFromConsole("Introduza Palavra-Passe: ");
            try{
                UserController uc = new UserController();
                sucesso = uc.login(sId,sPwd);

                if(sucesso){
                    InstanciaAtual.getInstance().setUserLogado(uc.getUserByEmail(sId));
                    redirecionaParaUI(uc.getTipoUser(sId));

                }else{
                    Logger.getAnonymousLogger().info( String.format("Utilizador ou Palavra-Passe erradas. \nPossui mais  %d tentativas ",maxTentativas ) );
                }
            }catch (IOException e){
                Logger.getAnonymousLogger().severe("Erro fatal");
            }
        } while (!sucesso && maxTentativas > 0);
        return sucesso;
    }

    /**
     * Método que invoca a Interface correta dependo do tipo de user logado.
     * @param tipoUser tipo de user
     */
    private void redirecionaParaUI (String tipoUser){
        switch (tipoUser){
            case "administrador":
                AdminUI adminUI = new AdminUI();
                adminUI.menuInicial();
                break;
            case "gestor":
                GestorUI gestorUI= new GestorUI();
                gestorUI.menuInicial();
                break;
            case "estafeta":
                // invocar a ui de estafeta
                break;
            case "cliente":
                ClienteUI clienteUI = new ClienteUI();
                clienteUI.menuInicial();
                break;
            default:
                throw new IllegalArgumentException("Não possui permissão de acesso");
        }
    }
}
