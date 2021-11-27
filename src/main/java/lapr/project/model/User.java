package lapr.project.model;
/**
 * Classe que representa um user  da plataforma.
 */
public class User {
    /**
     * Id do user
     */
    private int idUser;
    /**
     * Username do User
     */
    private String username;
    /**
     *  Password do User
     */
    private String password;
    /**
     * Tipo de user
     */
    private String tipoUser;
    /**
     *  Email do user
     */
    private String email;

    /**
     *  Construtor para user em que nao se sabe o seu ID.
     * @param username username do cliente.
     * @param password password do cliente.
     * @param tipoUser tipo de user do cliente.
     * @param email email do tipo de user.
     */
    public User(String username, String password, String tipoUser, String email) {
        this.username = username;
        this.password = password;
        this.tipoUser = tipoUser;
        this.email = email;
    }

    /**
     * Construtor para user em que se sabe o seu id
     * @param idUser id do utilizador
     * @param username username do cliente.
     * @param password password do cliente.
     * @param tipoUser tipo de user do cliente.
     * @param email email do cliente.
     */
    public User(int idUser, String username, String password, String tipoUser, String email) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.tipoUser = tipoUser;
        this.email = email;
    }

    /**
     *  Construtor para user em que sabe apenas o seu id
     * @param idUser id do user.
     */
    public User(int idUser) {
        this.idUser = idUser;
    }

    /**
     *   Método de getter do username do utilizador
     * @return username do utilizador.
     */

    public String getUsername() {
        return username;
    }

    /**
     *  Método de getter do password do utilizador.
     * @return password do utiliziador.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Método de getter do tipo de utilizador
     *
     * @return tipo de utilizador.
     */
    public String getTipoUser() {
        return tipoUser;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Sets id user.
     *
     * @param idUser the id user
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
