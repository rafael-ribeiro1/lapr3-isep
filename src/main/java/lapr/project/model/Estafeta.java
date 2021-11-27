package lapr.project.model;


import java.util.Objects;

/**
 * Classe que representa um estafeta da plataforma.
 */
public class Estafeta extends User {
    /**
     * Nome do estafeta
     */
    private String nome;
    /**
     * Peso da carga maxima
     */
    private double cargaMaxima;
    /**
     * Nif de do estafeta
     */
    private int nif;
    /**
     * Peso do estafeta
     */
    private double peso;
    /**
     * Farmacia para qual o estafeta trabalha
     */
    private Farmacia farmacia;
    /**
     *  Constrói uma instância de Estafeta recebendo por parametro
     *
     * @param id_user     the id user
     * @param username    the username
     * @param email       the email
     * @param nome        the nome
     * @param cargaMaxima the carga maxima
     * @param nif         the nif
     * @param idFarmacia  the id farmacia
     * @param peso        the peso
     */
    public Estafeta(int id_user, String username, String email, String nome, double cargaMaxima, int nif, int idFarmacia,double peso) {
        super(id_user);
        this.nome = nome;
        this.cargaMaxima = cargaMaxima;
        this.nif = nif;
        farmacia = new Farmacia(idFarmacia);
        this.peso=peso;
    }

    /**
     *  Constrói uma instância de Estafeta recebendo por parametro
     *
     * @param username    the username
     * @param password    the password
     * @param email       the email
     * @param nome        Nome do estafeta
     * @param cargaMaxima Tamanho da carga maxima
     * @param nif         Nif de do estafeta
     * @param idFarmacia  the id farmacia
     * @param peso        the peso
     */
    public Estafeta(String username, String password, String email, String nome,  double cargaMaxima, int nif,int idFarmacia,double peso) {
        super(username, password, "estafeta", email);
        this.nome = nome;
        this.cargaMaxima = cargaMaxima;
        this.nif = nif;
        farmacia = new Farmacia(idFarmacia);
        this.peso=peso;
    }

    /**
     *  Constrói uma instância de Estafeta recebendo por parametro.
     *
     * @param id_user     the id user
     * @param nome        the nome
     * @param cargaMaxima the carga maxima
     * @param nif         the nif
     * @param idFarmacia  the id farmacia
     * @param peso        the peso
     */
    public Estafeta(int id_user, String nome,  double cargaMaxima, int nif,int idFarmacia, double peso) {
        super(id_user);
        this.nome = nome;
        this.cargaMaxima = cargaMaxima;
        this.nif = nif;
        farmacia = new Farmacia(idFarmacia);
        this.peso=peso;
    }

    /**
     * Constrói uma instância de estafeta
     * @param id id do estafeta
     * @param peso peso do estafeta
     */
    public Estafeta(int id, double peso) {
        super(id);
        this.peso = peso;
    }

    /**
     * Gets carga maxima.
     *
     * @return the carga maxima
     */
    public double getCargaMaxima() {
        return cargaMaxima;
    }

    /**
     * Devolve o nif do estafeta
     *
     * @return o nif do estafeta
     */
    public int getNif() {
        return nif;
    }


    /**
     * Devolve o nome do estafeta
     *
     * @return o nome do estafeta
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve o peso do estafeta
     *
     * @return o peso do estafeta
     */
    public double getPeso() { return peso;}

    /**
     * Devolve a  farmacia.
     *
     * @return a farmacia
     */
    public Farmacia getFarmacia() {
        return farmacia;
    }

    /**
     * Modifica o nome do estafeta
     *
     * @param nome o nome do estafeta
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Modifica a carga máxima do estafeta
     *
     * @param cargaMaxima a carga máxima do estafeta
     */
    public void setCargaMaxima(double cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    /**
     * Modifica o nif  do estafeta
     *
     * @param nif o nif do estafeta
     */
    public void setNif(int nif) {
        this.nif = nif;
    }

    /**
     * Modifica a farmacia  do estafeta
     *
     * @param farmacia a farmacia do estafeta
     */
    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    /**
     * Modifica o peso  do estafeta
     *
     * @param peso o peso do estafeta
     */
    public void setPeso(double peso) { this.peso = peso; }


    /**
     * Verifica se o objeto passado por parâmetro é igual ao estafeta.
     * Se os objetos partilharem a mesma referência, devolve true. Se o objeto recebido
     * por parâmetro for nulo ou as classes dos objetos forem diferentes, devolve false.
     * Por fim, efetua um downcasting do objeto recebido por parâmetro para o estafeta,
     * comparando os diferentes atributos da classe.
     * @param o o objeto a comparar
     * @return true se forem iguais, caso contrário retorna false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estafeta)) return false;
        Estafeta estafeta = (Estafeta) o;
        return Double.compare(estafeta.cargaMaxima, cargaMaxima) == 0 &&
                nif == estafeta.nif &&
                Double.compare(estafeta.peso, peso) == 0 &&
                Objects.equals(nome, estafeta.nome) &&
                Objects.equals(farmacia, estafeta.farmacia);
    }
    /**
     * Devolve o valor do hash code para o objeto
     * @return o valor do hash code para o objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, cargaMaxima, nif, farmacia);
    }

    @Override
    public String toString() {
        return String.format("%s pertencente a farmacia de id (%d)",nome,farmacia.getId());
    }
}








