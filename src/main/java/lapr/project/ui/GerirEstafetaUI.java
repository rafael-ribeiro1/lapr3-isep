package lapr.project.ui;
import lapr.project.controller.GerirEstafetaController;
import lapr.project.model.Estafeta;
import lapr.project.model.Farmacia;
import lapr.project.ui.Utils.UtilsRead;

import java.io.IOException;
import java.util.List;

/**
 *  Classe que representa a inferface associada a gestao de um estafeta
 */
public class GerirEstafetaUI {
        /**
         * Controller associado a esta UI
         */
        private GerirEstafetaController controller;
        /**
         * Construtor que inicializa o controller
         */
    public GerirEstafetaUI() throws IOException {
        controller = new GerirEstafetaController();
    }
    /**
     * Método que adiciona uma estafeta a uma farmacia
     */
   public void addEstafeta(){
       List<Farmacia> farmacias = controller.getListaFarmacias();
       if (farmacias == null || farmacias.isEmpty()){
           System.out.println("Nao existem farmacias registadas!") ;
           return;
       }

       Farmacia farmacia = (Farmacia) UtilsRead.apresentaESeleciona(farmacias,"--- Processo de adicionar Estafeta---\n--- Lista de farmácias ---\n");
       if(farmacia == null)return;
       int idFarmacia = farmacia.getId();
       String username = UtilsRead.readLineFromConsole("Username do estafeta:");
       String email = UtilsRead.readLineFromConsole("Email do estafeta:");
       String nome = UtilsRead.readLineFromConsole("Nome do estafeta:");
       double cargaMaxima = UtilsRead.readDoubleFromConsole("Carga maxima do estafeta [KG]: ");
       int nif = UtilsRead.readIntegerFromConsole("nif do estafeta[9 digitos]:");
       double peso =UtilsRead.readDoubleFromConsole("Peso do estafeta[kG]");
       String add = controller.adicionarEstafeta(idFarmacia,  username , email, nome,cargaMaxima, nif,peso);
       if (add == null)
           System.out.println("Estafeta registado com sucesso|");
       else
           System.out.println(add);
   }
    /**
     * Método que atualiza as informacoes de um estafeta.
     */
    public void atualizarEstafeta(){
        List<Estafeta> estafetas = controller.getListaEstafetas();
        if (estafetas == null || estafetas.isEmpty()){
            System.out.println("Nao existem estafetas registados!") ;
            return;
        }
        Estafeta estafeta = (Estafeta)UtilsRead.apresentaESeleciona(estafetas,"--- Processo de atualizar Estafeta---\n--- Lista de estafetas ---");
        if(estafeta == null)return;
        int idUser = estafeta.getIdUser();
        String nome = UtilsRead.readLineFromConsole("Novo nome do estafeta:");
        int nif = UtilsRead.readIntegerFromConsole("Novo nif do estafeta");
        double cargaMaxima = UtilsRead.readDoubleFromConsole("Nova carga maxima do estafeta");
        double peso=UtilsRead.readDoubleFromConsole(("Novo peso do estafeta"));
        List<Farmacia> farmacias = controller.getListaFarmacias();
        if (farmacias == null || farmacias.isEmpty()){
            System.out.println("Nao existem farmacias registadas!") ;
            return;
        }
        Farmacia farmacia = (Farmacia) UtilsRead.apresentaESeleciona(farmacias,"\n--- Lista de farmácias disponiveis ---");
        int idFarmacia = farmacia.getId();
        System.out.println(String.format("NOME:%s\nNIF:%d\nCargaMaxima:%.2f\nidFarmacia:%d\nidEstafeta:%d",nome,nif,cargaMaxima,idFarmacia,idUser));
        boolean at = controller.atualizarEstafeta(nome,nif,cargaMaxima,idFarmacia,idUser,peso );
       if(at){
           System.out.println("Estafeta atualizado com sucesso");
       }else{
           System.out.println("Erro na atualização do estafeta");
       }
    }

   }





