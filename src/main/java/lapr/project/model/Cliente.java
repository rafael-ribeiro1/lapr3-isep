package lapr.project.model;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa um cliente.
 */
public class Cliente extends User {
    /**
     * Tipo user .
     */
    static final String TIPO_USER = "cliente";
        /**
         * Nome do cliente
         */
        private String nome;
        /**
         * Nif do cliente
         */
        private int nif;
        /**
         * Creditos do cliente
         */
        private int creditos;
        /**
         * Endereco do cliente
         */
        private Endereco endereco;
        /**
         * Cartao de credito do cliente
         */
        private CartaoCredito cartaoCredito;
    /**
     * Estado da conta do cliente
     */
    private Estado estado;

    /**
     * Enum com os estados possiveis que uma conta pode ter.
     */
    public enum Estado{
        /**
         * Ativo estado.
         */
        ATIVO("Ativo"),
        /**
         * Removido estado.
         */
        REMOVIDO("Removido");
            /**
             * Estado da conta do cliente
             */
            private String descricaoEstado;

            Estado(String descricaoEstado) {
                this.descricaoEstado = descricaoEstado;
            }

        /**
         * Gets descricao estado.
         *
         * @return the descricao estado
         */
        public String getDescricaoEstado() {
                return descricaoEstado;
            }
        }

    /**
     * Instantiates a new Cliente.
     *
     * @param username      the username
     * @param password      the password
     * @param email         Email do cliente
     * @param nome          Nome do cliente
     * @param endereco      Endereco do cliente
     * @param cartaoCredito Cartao do credito
     */
    public Cliente(String username, String password, String email, String nome, Endereco endereco, CartaoCredito cartaoCredito) {
            super(username, password,TIPO_USER , email);
            this.nome = nome;
            this.endereco = endereco;
            this.cartaoCredito = cartaoCredito;
            this.creditos=0;
            this.estado = Estado.ATIVO;
        }

    /**
     * Instantiates a new Cliente.
     *
     * @param username     the username
     * @param password     the password
     * @param email        Email do cliente
     * @param nome         Nome do cliente
     * @param rua          Rua do cliente
     * @param numPorta     Número de porta
     * @param codPostal    Código postal
     * @param localidade   Localidade do cliente
     * @param pais         Pais do cliente
     * @param latitude     Latitude
     * @param longitude    Longitude
     * @param altitude     the altitude
     * @param numeroCartao Numéro do cartao
     * @param ccv          ccv do cartao
     * @param dataValidade data da validade do cartao
     */
    public Cliente(String username, String password, String email, String nome,String rua , String numPorta ,
                   String codPostal , String localidade , String pais , double latitude ,  double longitude ,
                   double altitude ,String numeroCartao , int ccv , LocalDate dataValidade  ){
            super(username, password, TIPO_USER, email);
            this.nome = nome;
            this.creditos = 0;
            this.endereco = new Endereco(rua,numPorta,codPostal,localidade,pais,latitude,longitude,altitude);
            this.cartaoCredito = new CartaoCredito(numeroCartao,ccv,dataValidade);
            this.estado = Estado.ATIVO;
        }

    /**
     * Instantiates a new Cliente.
     *
     * @param idUser        id do user
     * @param username      username do cliente
     * @param password      password do cliente
     * @param email         email do cliente
     * @param nome          nome do cliente
     * @param endereco      endereco do cliente
     * @param cartaoCredito cartao de Credito do cliente
     */
    public Cliente(int idUser, String username, String password, String email, String nome, Endereco endereco,
                   CartaoCredito cartaoCredito) {
            super(idUser, username, password, TIPO_USER, email);
            this.nome = nome;
            this.creditos = 0;
            this.endereco = endereco;
            this.cartaoCredito = cartaoCredito;
            this.estado = Estado.ATIVO;
        }
    /**
     * Instantiates a new Cliente.
     *
     * @param idUser        id do user
     * @param username      username do cliente
     * @param password      password do cliente
     * @param email         email do cliente
     * @param nome          nome do cliente
     * @param nif           nif do cliente
     * @param endereco      endereco do cliente
     * @param cartaoCredito cartao de Credito do cliente
     */
    public Cliente(int idUser, String username, String password, String email, String nome, int nif,
                   Endereco endereco, CartaoCredito cartaoCredito) {
            super(idUser, username, password, TIPO_USER, email);
            this.nome = nome;
            this.nif = nif;
            this.creditos = 0;
            this.endereco = endereco;
            this.cartaoCredito = cartaoCredito;
            this.estado = Estado.ATIVO;
        }

    /**
     *  Constrói uma instância do cliente com o seu id e o seu endereço.
     * @param idUser
     * @param endereco
     */
    public Cliente(int idUser, Endereco endereco) {
        super(idUser);
        this.endereco = endereco;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
            return nome;
        }

    /**
     * Gets nif.
     *
     * @return the nif
     */
    public int getNif() {
            return nif;
        }

    /**
     * Gets creditos.
     *
     * @return the creditos
     */
    public int getCreditos() {
            return creditos;
        }

    /**
     * Gets endereco.
     *
     * @return the endereco
     */
    public Endereco getEndereco() {
            return endereco;
        }

    /**
     * Gets cartao credito.
     *
     * @return the cartao credito
     */
    public CartaoCredito getCartaoCredito() {
            return cartaoCredito;
        }

    /**
     * Gets estado.
     *
     * @return the estado
     */
    public Estado getEstado() {
            return estado;
        }

    /**
     * Sets estado.
     *
     * @param estado the estado
     */
    public void setEstado(Estado estado) {
            this.estado = estado;
        }

    /**
     * Sets creditos.
     *
     * @param creditos the creditos
     */
    public void setCreditos(int creditos) {
            this.creditos = creditos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cliente)) return false;
            Cliente cliente = (Cliente) o;
            return getNif() == cliente.getNif();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getNif());
        }
}
