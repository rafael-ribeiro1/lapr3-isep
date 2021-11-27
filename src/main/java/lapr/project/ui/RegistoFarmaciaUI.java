package lapr.project.ui;

import lapr.project.controller.RegistoFarmaciaController;
import lapr.project.model.Endereco;
import lapr.project.model.Farmacia;
import lapr.project.ui.Utils.UtilsRead;
import java.io.IOException;
import java.util.List;
/**
 * Classe responsável pela interação com o administrador durante o registo de uma farmácia
 */
public class RegistoFarmaciaUI {
    /**
     * Controller
     */
    private RegistoFarmaciaController controller;
    /**
     * Construtor que inicializa o controller e o scanner do teclado
     */
    public RegistoFarmaciaUI() throws IOException {
        controller = new RegistoFarmaciaController();
    }
    /**
     * Processo completo de registo de farmácia
     */
    public void registarFarmacia() {
        System.out.println("--- Processo de registo de farmácia ---");
        if (obterDadosFarmacia()) {
            terminarRegistoFarmacia();
        }
    }
    /**
     * Parte do processo de registo de farmácia (UC4) em que são pedidas as informações da farmácia
     */
    private boolean obterDadosFarmacia() {
        String nome = UtilsRead.readLineFromConsole("Nome da farmacia:");
        String rua = UtilsRead.readLineFromConsole("Rua:");
        String numPorta = UtilsRead.readLineFromConsole("Numéro da porta:");
        String codPostal = UtilsRead.readLineFromConsole("Codigo postal:");
        String localidade = UtilsRead.readLineFromConsole("Localidade:");
        String pais = UtilsRead.readLineFromConsole("Pais:");
        double latitude = UtilsRead.readDoubleFromConsole("Latitude:");
        double longitude = UtilsRead.readDoubleFromConsole("Longitude:");
        double altitude=UtilsRead.readDoubleFromConsole("Altitude:");
        controller.novaFarmacia(nome, rua, numPorta, codPostal, localidade, pais, latitude, longitude, altitude);
        return true;
    }

    /**
     * Parte do processo de registo de farmácia (UC5) em que são registadas as informações do estacionamento
     */
    private void terminarRegistoFarmacia() {
        int capacidadeMaximaScooters = UtilsRead.readIntegerFromConsole("Número máximo de slots de scooters no estacionamento:");
        int numCargaScooters= UtilsRead.readIntegerFromConsole("Número de slots de carga de scooters no estacionamento: ");
        int capacidadeMaximaDrones= UtilsRead.readIntegerFromConsole("Número de slots de drones no estacionamento: ");
        int numCargaDrones = UtilsRead.readIntegerFromConsole("Número de slots de carga de drones no estacionamento: ");
        double potencia = UtilsRead.readDoubleFromConsole("Potência elétrica máxima no estacionamento: ");
        if (controller.registarCapacidadeEstacionamento(capacidadeMaximaScooters, numCargaScooters, capacidadeMaximaDrones, numCargaDrones, potencia) > 0)
            System.out.println("Farmácia registada com sucesso!");
        else
            System.out.println("Erro ao registar farmácia!");
    }

    /**
     *  Método que atualiza as informacoes de uma farmacia existente
     */
    public void atualizarFarmacia(){
        String[] info = new String[9];
        List<Farmacia> farmacias = controller.getListaFarmacias();
        if (farmacias == null || farmacias.isEmpty()){
            System.out.println("Nao existem farmacias registadas!") ;
            return;
        }
        Farmacia farmacia = (Farmacia) UtilsRead.apresentaESeleciona(farmacias,"--- Processo de atualizar Farmacia---\n--- Lista de farmacias ---");
        if(farmacia == null)return;
        int idFarmacia = UtilsRead.readIntegerFromConsole("\nid da farmacia que deseja atualizar: ") ;
        info[0] = UtilsRead.readLineFromConsole(" Novo Nome da farmacia: ");
        info[1] =  UtilsRead.readLineFromConsole(" Novo nome de rua da farmacia: ");
        info[2] = UtilsRead.readLineFromConsole(" Novo número da porta: ");
        info[3] = UtilsRead.readLineFromConsole(" Novo codigo postal: ");
        info[4] = UtilsRead.readLineFromConsole(" Nova localidade: ");
        info[5] = UtilsRead.readLineFromConsole(" Novo Pais: ");
        info[6] = UtilsRead.readLineFromConsole("Nova latitude:");
        info[7] = UtilsRead.readLineFromConsole("Nova longitude:");
        info[8] = UtilsRead.readLineFromConsole("Nova altitude :");
    boolean at = controller.atualizarFarmacia(info,new Farmacia(idFarmacia,new Endereco()));
        if(at){
        System.out.println("Farmacia atualizada com sucesso");
    }else{
        System.out.println("Erro na atualização do produto");
    }
}
        /**
         * Processo de registo de Gestor de Farmácia (UC7)
         */
    public void registarGestor() {
        System.out.println("--- Processo de registo de gestor de farmácia ---");
        List<Farmacia> farmacias = controller.getListaFarmacias();
        if (farmacias == null || farmacias.size() == 0) {
            System.out.println("Não existem farmácias registadas");
            return;
        }
        apresentarListaFarmacias();
        int idFarmacia = UtilsRead.readIntegerFromConsole("\nid da farmácia do gestor: ") ;
        String username = UtilsRead.readLineFromConsole("Username do gestor:");
        String email = UtilsRead.readLineFromConsole("Email do gestor: ");
        String res = controller.registarGestorFarmacia(idFarmacia, username, email);
        System.out.println(res);
    }
    /**
     * Método que adiciona slots de scooter a uma farmacia
     */
    public void adicionarSlotsScooter() {
        System.out.println("--- Processo de adição de slots de estacionamento de scooters ---");
        List<Farmacia> farmacias = controller.getListaFarmacias();
        if (farmacias == null || farmacias.size() == 0) {
            System.out.println("Não existem farmácias registadas");
            return;
        }
        apresentarListaFarmacias();
        int idFarmacia=UtilsRead.readIntegerFromConsole("Id da farmácia:");
        int quantidade=UtilsRead.readIntegerFromConsole("Número total de slots a adicionar: ");
        int numCarga=UtilsRead.readIntegerFromConsole("Número de slots que sao de carga:");
        if (controller.addSlotsScooter(idFarmacia, quantidade, numCarga))
            System.out.println("Slots adicionados com sucesso");
        else
            System.out.println("Erro ao adicionar slots");
    }
    /**
     *  Método que adiciona slots de drone a farmacia
     */
    public void adicionarSlotsDrone() {
        System.out.println("--- Processo de adição de slots de estacionamento de drone ---");
        apresentarListaFarmacias();
        int idFarmacia=UtilsRead.readIntegerFromConsole("Id da farmácia:");
        int quantidade=UtilsRead.readIntegerFromConsole("Número total de slots a adicionar: ");
        int numCarga=UtilsRead.readIntegerFromConsole("Número de slots que sao de carga:");
        if (controller.addSlotsDrone(idFarmacia, quantidade, numCarga))
            System.out.println("Slots de drone adicionados com sucesso");
        else
            System.out.println("Erro ao adicionar slots");
    }

    /**
     * Método que imprime a lista de farmacias
     */
    private void apresentarListaFarmacias() {
        List<Farmacia> farmacias = controller.getListaFarmacias();
        if (farmacias == null || farmacias.size() == 0) {
            System.out.println("Não existem farmácias registadas");
            return;
        }
        System.out.println("- Lista de farmácias -");
        for (Farmacia farmacia : farmacias)
            System.out.printf("%d - %s\n", farmacia.getId(), farmacia.getNome());
    }

}
