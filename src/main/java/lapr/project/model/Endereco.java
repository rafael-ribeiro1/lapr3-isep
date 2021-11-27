package lapr.project.model;


import java.util.Objects;

/**
 * Classe que representa um endereço da vida real.
 */
public class Endereco implements Comparable<Endereco>{
    /**
     * id do endereço
     */
    private int id;
    /**
     * Nome da rua
     */
    private String rua;
    /**
     * Número da porta
     */
    private String numPorta;
    /**
     * Codigo postal
     */
    private String codPostal;
    /**
     * Localidade
     */
    private String localidade;
    /**
     * Pais
     */
    private String pais;
    /**
     * latitude
     */
    private double latitude;
    /**
     * longitude
     */
    private double longitude;
    /**
     *  altitude
     */
    private double altitude;

    /**
     * Indica se o endereço correspondente pertence a uma farmácia
     */
    private boolean isFarmacia;

    /**
     * Instantiates a new Endereco.
     *
     * @param id         the id
     * @param rua        the rua
     * @param numPorta   the num porta
     * @param codPostal  the cod postal
     * @param localidade the localidade
     * @param pais       the pais
     * @param latitude   the latitude
     * @param longitude  the longitude
     * @param altitude   the altitude
     */
    public Endereco(int id, String rua, String numPorta, String codPostal, String localidade, String pais, double latitude, double longitude , double altitude) {
        this.id = id;
        this.rua = rua;
        this.numPorta = numPorta;
        this.codPostal = codPostal;
        this.localidade = localidade;
        this.pais = pais;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.isFarmacia = false;
    }

    /**
     * Instantiates a new Endereco.
     *
     * @param rua        Nome da rua
     * @param numPorta   Número da porta
     * @param codPostal  Codigo postal
     * @param localidade Localidade
     * @param pais       Pais
     * @param latitude   latitude
     * @param longitude  longitude
     * @param altitude   the altitude
     */
    public Endereco(String rua, String numPorta, String codPostal, String localidade, String pais, double latitude, double longitude,double altitude) {
        this.rua = rua;
        this.numPorta = numPorta;
        this.codPostal = codPostal;
        this.localidade = localidade;
        this.pais = pais;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.isFarmacia = false;
    }

    /**
     * Instantiates a new Endereco.
     *
     * @param rua        Nome da rua
     * @param numPorta   Número da porta
     * @param codPostal  Codigo postal
     * @param localidade Localidade
     * @param pais       Pais
     * @param latitude   latitude
     * @param longitude  longitude
     * @param altitude   the altitude
     * @param isFarmacia Endereco pertence a uma farmacia
     */
    public Endereco(String rua, String numPorta, String codPostal, String localidade, String pais, double latitude, double longitude,double altitude, boolean isFarmacia) {
        this.rua = rua;
        this.numPorta = numPorta;
        this.codPostal = codPostal;
        this.localidade = localidade;
        this.pais = pais;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.isFarmacia = isFarmacia;
    }

    /**
     * Instantiates a new Endereco.
     *
     * @param id         id da farmacia
     * @param rua        Nome da rua
     * @param numPorta   Número da porta
     * @param codPostal  Codigo postal
     * @param localidade Localidade
     * @param pais       Pais
     * @param latitude   latitude
     * @param longitude  longitude
     * @param altitude   the altitude
     * @param isFarmacia Endereco pertence a uma farmacia
     */
    public Endereco(int id,String rua, String numPorta, String codPostal, String localidade, String pais, double latitude, double longitude,double altitude, boolean isFarmacia) {
        this.id = id;
        this.rua = rua;
        this.numPorta = numPorta;
        this.codPostal = codPostal;
        this.localidade = localidade;
        this.pais = pais;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.isFarmacia = isFarmacia;
    }

    /**
     * Empty constructor
     */
    public Endereco() {
    }

    /**
     * Se o endereco é de uma farmacia boolean.
     *
     * @return Verdadeiro se for de uma farmcia
     */
    public boolean isFarmacia(){
        return isFarmacia;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets rua.
     *
     * @return the rua
     */
    public String getRua() {
        return rua;
    }

    /**
     * Gets num porta.
     *
     * @return the num porta
     */
    public String getNumPorta() {
        return numPorta;
    }

    /**
     * Gets cod postal.
     *
     * @return the cod postal
     */
    public String getCodPostal() {
        return codPostal;
    }

    /**
     * Gets localidade.
     *
     * @return the localidade
     */
    public String getLocalidade() {
        return localidade;
    }

    /**
     * Gets pais.
     *
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets altitude.
     *
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets rua.
     *
     * @param rua the rua
     */
    public void setRua(String rua) {
        this.rua = rua;
    }

    /**
     * Sets num porta.
     *
     * @param numPorta the num porta
     */
    public void setNumPorta(String numPorta) {
        this.numPorta = numPorta;
    }

    /**
     * Sets cod postal.
     *
     * @param codPostal the cod postal
     */
    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    /**
     * Sets localidade.
     *
     * @param localidade the localidade
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    /**
     * Sets pais.
     *
     * @param pais the pais
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Sets altitude.
     *
     * @param altitude the altitude
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public int compareTo(Endereco o) {
        int res=Double.compare (latitude,o.latitude);
        if(res==0) {
            res=Double.compare ( longitude,o.longitude );
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco endereco = (Endereco) o;
        return Double.compare(endereco.getLatitude(), getLatitude()) == 0 && Double.compare(endereco.getLongitude(),
                getLongitude()) == 0 && getRua().equals(endereco.getRua()) && getNumPorta().equals(endereco.getNumPorta())
                && getCodPostal().equals(endereco.getCodPostal()) && getLocalidade().equals(endereco.getLocalidade())
                && getPais().equals(endereco.getPais());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRua(), getNumPorta(), getCodPostal(), getLocalidade(), getPais(), getLatitude(), getLongitude(), getAltitude());
    }

}
