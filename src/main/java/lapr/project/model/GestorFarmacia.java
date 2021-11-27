package lapr.project.model;

/**
 * Classe que representa um gestor de uma farmácia
 */
public class GestorFarmacia extends User {
    /**
     * Farmacia associada ao gestor da farmacia
     */
    private Farmacia farmacia;

    /**
     * User que representa um gestor da farmacia.
     * @param username username do gestor
     * @param password password do gestor
     * @param email email do gestor.
     * @param idFarmacia id da Farmacia do gestor
     */
    public GestorFarmacia(String username, String password, String email, int idFarmacia) {
        super(username, password, "gestor", email);
        farmacia = new Farmacia(idFarmacia);
    }
    /**
     * Getter da farmacia do gestor
     * @return
     */
    public Farmacia getFarmacia() {
        return farmacia;
    }
}
