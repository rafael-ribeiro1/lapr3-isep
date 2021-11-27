package lapr.project.ui;

import lapr.project.ui.Utils.UtilsRead;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o menu principal da aplicação
 */
public class MenuUI {

    /**
     * Primeiro método a ser invocado que representa o menu principal da aplicacao
     */
    public void run()  {

        // email = "admin@farmacia.com" ;
        // ADMIN_PASS = "adminPass";
        List<String> options = new ArrayList<>();
        options.add("Login");
        options.add("Registo de cliente");
        int opcao = 0;
        do
        {
            opcao = UtilsRead.apresentaESelecionaIndex(options, "\n\nMenu Principal");

            switch(opcao)
            {
                case 0:
                    UserUI uiLogin = new UserUI();
                    uiLogin.run();
                    break;
                case 1:
                    try{
                        RegistoClienteUI ui = new RegistoClienteUI();
                        ui.registarCliente();
                    }catch (IOException e){
                        System.out.println("Erro fatal");
                    }
                    break;
                default:
                    System.out.println("Opcao invalida");
                    run();
            }
        }
        while (opcao != -1 );
    }




}
