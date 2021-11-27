package lapr.project.data;

import lapr.project.model.*;
import lapr.project.utils.PasswordGenerator;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Classe que estabelece a comunicação com a base de dados remota e devolve as informacoes de clientes.
 */
public class ClientesHandler extends DBHandler {
    /**
     * Handler dos user do sistema
     */
    private UserHandler userHandler;
    /**
     * Logger da classe atual .
     */
    private static Logger logger =Logger.getLogger ( ClientesHandler.class.getName () );
    /**
     * Mensagem a ser mostrada quando acontece um erro.
     */
    private static final String MSG = "Erro ao fechar as ligações." ;
    /**
     * Construtor que inicia a classe
     * @throws IOException Excecao que é lancada devido a conexao
     */
    public ClientesHandler()throws IOException {
        userHandler = new UserHandler();
    }

    /**
     *  Método que adiciona um cliente a base de dados
     * @param cliente Cliente a ser adicionado
     * @return verdade se adicinou o cliente com sucesso
     */
    public boolean addNovoCliente(Cliente cliente) {
            try(CallableStatement callStmt = getConnection().prepareCall("{call insert_cliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }")){
                callStmt.setString(1, cliente.getUsername());
                callStmt.setString(2, cliente.getPassword());
                callStmt.setString(3, cliente.getEmail());
                callStmt.setString(4, cliente.getTipoUser());
                callStmt.setString(5, cliente.getNome());
                callStmt.setString(6, cliente.getCartaoCredito().getNumero());
                callStmt.setInt(7, cliente.getCartaoCredito().getCcv());
                callStmt.setDate(8,java.sql.Date.valueOf(cliente.getCartaoCredito().getDataValidade()));
                callStmt.setString(9, cliente.getEndereco().getRua());
                callStmt.setString(10, cliente.getEndereco().getNumPorta());
                callStmt.setString(11, cliente.getEndereco().getCodPostal());
                callStmt.setString(12, cliente.getEndereco().getLocalidade());
                callStmt.setString(13, cliente.getEndereco().getPais());
                callStmt.setDouble(14, cliente.getEndereco().getLatitude());
                callStmt.setDouble(15 , cliente.getEndereco().getLongitude());
                callStmt.setDouble(16,cliente.getEndereco().getAltitude());
                callStmt.execute();
                cliente.setIdUser(userHandler.getIdUtilizador(cliente.getEmail()));
            return true;
        }catch (SQLException e){
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }

    /**
     * Método que retorna o cliente passado como parametro um cliente
     * @param userLogado User a ser realizado o login
     * @return cliente Logado
     */
    public Cliente getClienteLogado(User userLogado) {
        Cliente cliente = null;
            try(CallableStatement callStmt = getConnection().prepareCall("{? = call get_cliente_logado(?) }")){

                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt.setInt(2,userLogado.getIdUser());

                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if(rSet.next()){
                    Endereco endereco = new Endereco(rSet.getString(3),rSet.getString(4),rSet.getString(5),rSet.getString(6),rSet.getString(7),rSet.getDouble(8),rSet.getDouble(9),rSet.getDouble(10),false);
                    LocalDate localDate = rSet.getObject(13,LocalDate.class);
                    CartaoCredito cartaoCredito = new CartaoCredito(rSet.getString(11),rSet.getInt(12),localDate);
                    cliente = new Cliente(rSet.getInt(1),userLogado.getUsername(),userLogado.getPassword(), userLogado.getEmail(),rSet.getString(2),endereco,cartaoCredito);
                }
                rSet.close();
            return cliente;
        }catch (SQLException e){
           logger.severe(e.getMessage());
                return cliente;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     *  Método que adiciona Creditos ao cliente
     * @param idEncomenda id da Encomenda entregue
     * @return
     */
    public int addCreditosCliente(int idEncomenda ){

       try (CallableStatement callStmt = getConnection().prepareCall("{? = call gerar_Creditos(?)}")){
           callStmt.registerOutParameter(1,OracleTypes.INTEGER);
           callStmt.setInt(2,idEncomenda);
           callStmt.execute();
           int result = callStmt.getInt(1);
           return result;
       } catch (SQLException e){
           logger.severe(e.getMessage());
           return 0;
       }finally {
           try{
               closeAll ();
           }catch (SQLException e){
               logger.severe ( MSG );
           }
       }

    }

    /**
     * Atualiza os dados do cartão de crédito.
     * @param numero o número do cartão de crédito
     * @param ccv o ccv do cartão de crédito
     * @param dataValidade a data de validade do cartão de crédito
     * @param idCartao o id do cartão de crédito
     * @return true se a atualização for bem sucedida, caso contrário, retorna false
     */
    public boolean atualizarCartaoCredito(String numero, int ccv, LocalDate dataValidade, int idCartao){

            try (CallableStatement callStmt = getConnection().prepareCall("{call atualizarCartaoCredito(?,?,?,?) }")) {
                callStmt.setString(1, numero);
                callStmt.setInt(2, ccv);
                callStmt.setDate(3, java.sql.Date.valueOf(dataValidade));
                callStmt.setInt(4, idCartao);
                callStmt.executeUpdate();
                return true;
        }catch(SQLException e){
                logger.severe(e.getMessage());
                return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }

    }

    /***
     * Método que devolve um cliente com informacoes
     * @param id id do cliente que se pretende
     * @return Cliente com informacao
     */
    public Cliente getInformacaoCliente(int id) {
        Cliente c = null;


            String query = "SELECT u.username, c.nome, u.email, c.nif, cc.numero, cc.ccv, cc.data_validade,\n" +
                    "        e.rua, e.num_porta, e.cod_postal, e.localidade, e.pais, e.latitude,\n" +
                    "        e.longitude, e.altitude, es.descricao\n" +
                    "                    FROM cliente c\n" +
                    "                    INNER JOIN utilizador u ON u.id_user = c.id_cliente \n" +
                    "                    INNER JOIN cartao_credito cc ON c.id_cartao = cc.id_cartao\n" +
                    "                    INNER JOIN endereco e ON e.id_endereco = c.id_endereco\n" +
                    "                    INNER JOIN estado_cliente es ON es.id_estado = c.id_estado" +
                    "                    WHERE c.id_cliente = ?";
            try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next())
                        c = new Cliente(id,rs.getString(1),PasswordGenerator.generatePassword(8),
                                rs.getString(3), rs.getString(2), rs.getInt(4),
                                new Endereco(rs.getString(8), rs.getString(9),
                                rs.getString(10), rs.getString(11), rs.getString(12),
                                rs.getDouble(13), rs.getDouble(14),rs.getDouble(15),false),
                                new CartaoCredito(rs.getString(5), rs.getInt(6),
                                rs.getDate(7).toLocalDate()));
                        if (c != null)
                            c.setEstado(Cliente.Estado.valueOf(rs.getString(16).toUpperCase(Locale.ROOT)));
                }
            return c;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return c;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     *  Método que atualiza as informacoes de um cliente
     * @param id id de um cliente
     * @param nome nome do cliente
     * @param en novo endereco
     * @return true se for atualizado com sucesso
     */
    public boolean atualizarInformacaoCliente(int id, String nome, Endereco en){
            try (CallableStatement callStmt = getConnection().prepareCall("{call atualizarInformacaoCliente(?,?,?,?,?,?,?,?,?,?) }")) {
                callStmt.setInt(1, id);
                callStmt.setString(2, nome);
                callStmt.setString(3, en.getRua());
                callStmt.setString(4, en.getNumPorta());
                callStmt.setString(5, en.getCodPostal());
                callStmt.setString(6, en.getLocalidade());
                callStmt.setString(7, en.getPais());
                callStmt.setDouble(8, en.getLatitude());
                callStmt.setDouble(9, en.getLongitude());
                callStmt.setDouble(10, en.getAltitude());
                callStmt.execute();
                return true;
        }catch (SQLException e) {
                logger.severe(e.getMessage());
                return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }
    /**
     * Remove o perfil do Cliente, atualizando o estado para "Removido"
     * @param id o id do cliente
     * @return true se a remoção for bem sucedida, caso contrário, retorna false
     */
    public boolean removerPerfilCliente(int id){

            try(CallableStatement callStmt = getConnection().prepareCall("{call removerPerfilCliente(?,?) }")){
                callStmt.setInt(1, 2);
                callStmt.setInt(2, id);
                callStmt.executeUpdate();
                getInformacaoCliente(id).setEstado(Cliente.Estado.REMOVIDO);
                return true;
            }catch(SQLException e){
                logger.severe(e.getMessage());
                return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }
    /**
     *  Método que  retira os créditos ao cliente passado como parametro
     * @param cliente Cliente a ser retirado os créditos
     * @param creditosRetirar Numéro de creditos a ser retirados
     * @return True se foi realizado com sucesso
     */
    public boolean retirarCreditos(Cliente cliente , int creditosRetirar){
            try(CallableStatement callStmt = getConnection().prepareCall("{? = call retirar_creditos_cliente(?,?) }")){
                callStmt.registerOutParameter(1,OracleTypes.INTEGER);
                callStmt.setInt(2,cliente.getIdUser());
                callStmt.setInt(3,creditosRetirar);
                callStmt.execute();
                return  (callStmt.getInt(1) == 1);
        }catch (SQLException e){
            logger.severe(e.getMessage());
           return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage());
            }
        }

    }



}
