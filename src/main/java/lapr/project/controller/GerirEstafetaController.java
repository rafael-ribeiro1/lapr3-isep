package lapr.project.controller;
import lapr.project.data.EmailHandler;
import lapr.project.data.EstafetaHandler;
import lapr.project.data.FarmaciaHandler;
import lapr.project.model.Estafeta;
import lapr.project.model.Farmacia;
import lapr.project.utils.PasswordGenerator;

import java.io.IOException;
import java.util.List;

/***
 * Controller que trata dos estafetas.
 */
public class GerirEstafetaController {
    /**
     * Handler associado com os estafetas da plataforma.
     */
    private EstafetaHandler estafetadb;
    /**
     * Handler associado com as  farmácias da plataforma.
     */
    private FarmaciaHandler farmaciadb;
    /**
     * Handler do email.
     */
    private EmailHandler sender;

    /**
     * Contrutor do GerirEstafetaController
     *
     * @throws IOException the io exception
     */
    public GerirEstafetaController() throws IOException {
        estafetadb = new EstafetaHandler();
        farmaciadb = new FarmaciaHandler();
        sender = new EmailHandler();
    }
    /**
     * Método para mudar a db do Estafeta (Apenas utilizados para testes unitários)
     *
     * @param estafetadb the estafetadb
     */
    public void setEstafetadb(EstafetaHandler estafetadb) {
        this.estafetadb = estafetadb;
    }
    /**
     * Método para mudar a db da Farmacia (Apenas utilizados para testes unitários)
     *
     * @param farmaciadb the farmaciadb
     */
    public void setFarmaciadb(FarmaciaHandler farmaciadb) {
        this.farmaciadb = farmaciadb;
    }
    /**
     * Método para mudar a handler do EmailHandler (Apenas utilizados para testes unitários)
     *
     * @param sender the sender
     */
    public void setSender(EmailHandler sender) {
        this.sender = sender;
    }

    /**
     * Regista um estafeta no sistema
     * @param idFarmacia id da farmácia do estafeta
     * @param usermame   username do gestor
     * @param email      email do gestor
     * @return mensagem de erro ou null caso não ocorram erros
     */
    public String adicionarEstafeta(int idFarmacia, String usermame, String email, String nome, double cargaMaxima, int nif, double peso) {
        String password = PasswordGenerator.generatePassword(8);
        Estafeta estafeta = new Estafeta(usermame, password, email, nome, cargaMaxima, nif, idFarmacia,peso);
        if (!estafetadb.addEstafeta(estafeta))
            return "erro no registo";
        String subject = "Registo como estafeta";
        String content = String.format("Foi registado na plataforma como estafeta.\n" +
                        "As suas credenciais de acesso são as seguintes.\nUsername: %s\nPassword: %s",
                        usermame, password);
        if (!sender.sendEmail(email, subject, content))
            return "erro ao enviar email";
        return "sucesso no registo";
    }

    /**
     * Método que serve para atualizar um estafeta .
     *
     * @param nome   Nome do estafeta.
     * @param nif       Nif do estafeta.
     * @param cargaMaxima Carga maxima do estafeta.
     * @param idFarmacia  Id da farmácia do estafeta.
     * @param idEstafeta  Id do estafeta.
     * @param peso    Novo peso do estafeta.
     * @return  True se foi atualizado as informações do estafeta com sucesso.
     */
    public boolean atualizarEstafeta(String nome,int nif, double cargaMaxima,int idFarmacia,int idEstafeta, double peso) {
            Estafeta estafeta= new Estafeta(idEstafeta,nome, cargaMaxima,nif,idFarmacia,peso);

            if (!estafetadb.atualizarEstafeta(estafeta,idFarmacia))
                return false;
            return true;

    }
        /**
         * Retorna a lista de farmácias no sistema
         * @return ArrayList de farmácias ou null se ocorrer um erro
         */
        public List<Farmacia> getListaFarmacias () {
                return farmaciadb.getListaFarmacias();
                 }

        /**
         * Retorna a lista de estafetas no sistema
         * @return ArrayList de farmácias ou null se ocorrer um erro
         */
        public List<Estafeta> getListaEstafetas () {
            return estafetadb.getListaEstafetas();

        }



}





