package lapr.project.ui;

import lapr.project.controller.GerirClienteController;
import lapr.project.ui.Utils.UtilsRead;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Classe responsável pela interação com o utilizador nao registado durante o registo do cliente.
 */
public class RegistoClienteUI {

    /**
     * Controller
     */
    private final GerirClienteController controller;
    /**
     * Construtor que inicializa o controller e o scanner do teclado
     */
    public RegistoClienteUI() throws IOException {
        this.controller = new GerirClienteController();
    }
    /**
     * Processo completo de registo de cliente
     */
    public void registarCliente(){
        System.out.println("----------Registo de Cliente-------------");
        if(obterDadosCliente()){
            System.out.println("--------------Sucesso----------------");
        }else{
            System.out.println("----------------Erro-----------------");
        }
    }

    /**
     * Processo de registo de dados de um cliente
     * @return true se ocorreu sucesso
     */

    private boolean obterDadosCliente(){


        String username = UtilsRead.readLineFromConsole("Username pretendido:");
        String password = UtilsRead.readLineFromConsole("password:");
        String email = UtilsRead.readLineFromConsole("Email[yyyyy@xxxxxx.com]:");
        String nome = UtilsRead.readLineFromConsole("Nome:");

        // Cartao
        String numeroCartao = UtilsRead.readLineFromConsole("Numéro do cartao:");
        int ccv = UtilsRead.readIntegerFromConsole("ccv:");
        LocalDate date = UtilsRead.readDateFromConsole("Validade do cartao [YYYY-MM-DD]:");

        // Endereco

        String rua = UtilsRead.readLineFromConsole("Rua:");
        String numPorta = UtilsRead.readLineFromConsole("Numéro da porta:");
        String codPostal = UtilsRead.readLineFromConsole("Código postal:");
        String localidade = UtilsRead.readLineFromConsole("Localidade:");
        String pais = UtilsRead.readLineFromConsole("País:");
        double latitude= UtilsRead.readDoubleFromConsole("latitude:");
        double longitude= UtilsRead.readDoubleFromConsole("longitude:");
        double altitude = UtilsRead.readDoubleFromConsole("altitude:");

        if(!UtilsRead.confirma("Confirma a criacao da conta? [S/N]"))return false;

        return controller.novoCliente(username, password, email, nome, numeroCartao, ccv, date, rua, numPorta, codPostal, localidade, pais, latitude, longitude, altitude);
    }



}
