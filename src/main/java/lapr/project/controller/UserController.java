package lapr.project.controller;

import lapr.project.data.UserHandler;
import lapr.project.model.User;

import java.io.IOException;

/**
 * Controller responsável pelas informações dos users da aplicação.
 */
public class UserController {
    private UserHandler handler;

    /**
     * Cria uma instância de UserHandler
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public UserController() throws IOException {
        handler = new UserHandler();
    }

    /**
     * Devolve o handler
     * @return UserHandler
     */
    public UserHandler getHandler() {
        return handler;
    }

    /**
     * Modifica o valor de UserHandler
     * @param handler o UserHandler
     */
    public void setHandler(UserHandler handler) {
        this.handler = handler;
    }

    /**
     * Método que realiza o login do utilizador que pretende entrar no sistema.
     * @param email email do user
     * @param password password do user.
     * @return Sucesso se estiver registado no sistema
     */
    public boolean login (String email , String password){
        return handler.login(email, password);
    }

    /**
     * Método que devolve o tipo de user passado como parametro.
     * @param email Email do user que se pretende saber informação acerca,
     * @return Tipo de user registado no sistema.
     */
    public String getTipoUser(String email){
        return handler.getTipoUserLogado(email);
    }

    /**
     * Método que devolve o utilizador fornecendo o seu email como parametro.
     * @param email Email do utilizador que se pretende saber.
     * @return Utilizador com o email passado como parametro.
     */
    public User getUserByEmail(String email) {
        return handler.getUserComEmail(email);
    }

    /**
     * Atualiza a password do utilizador
     * @param idUser id do utilizador
     * @param novaPassword nova password
     * @return true se a atualização for bem sucedida, caso contrário, devolve false
     */
    public boolean atualizarPassword(int idUser, String novaPassword){
        if (idUser <= 0) return false;
        if (novaPassword.isEmpty()) return false;
        return handler.atualizarPassword(idUser, novaPassword);
    }
}
