package lapr.project.data;

import lapr.project.model.User;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;
/**
 * Classe responsável pela operações com a base de dados que envolvam os utilizadores da plataforma.
 */
public class UserHandler extends  DBHandler{
    /**
     * Logger da classe atual
     */
    private static Logger logger = Logger.getLogger(UserHandler.class.getName());
    /**
     * Mensagem a ser mostrada quando ocorre um erro nas ligacoes
     */
    public static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Constrói uma instância de UserHandler
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public UserHandler () throws IOException {
        // constructor para apenas lancar a excecao
    }

    /**
     * Método que verifica se o email e a password registada no sistema estão registadas
     * @param email Email do utilizador que pretende fazer o login
     * @param password Password do utilizador que pretende fazer login.
     * @return True se estiver registado no sistema.
     */
    public boolean login(String email , String password ){
            try (CallableStatement callSmt = getConnection().prepareCall("{ ? = call autenticarUser(?,?) }")) { // mudar o nome
                callSmt.registerOutParameter(1, OracleTypes.INTEGER);
                // definir o primeiro parametro como email
                callSmt.setString(2,email);
                // definir o segundo parametro como Password
                callSmt.setString(3,password);
                // executa a funcao
                callSmt.execute();
                return  (callSmt.getInt(1)==1);
        }catch (SQLException e){
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
     * Método que devolve o tipo de user atualmente a usar a aplicação
     * @param email Email da pessoa a usar atualmente a aplicação
     * @return Tipo de user atualmente a usar a plataforma.
     */
    public String getTipoUserLogado (String email){
            try (CallableStatement callSmt = getConnection().prepareCall("{ ? = call obterTipoUser(?) }")) { // mudar o nome
                callSmt.registerOutParameter(1, OracleTypes.VARCHAR);
                // definir o primeiro parametro como userName
                callSmt.setString(2,email);
                // definir o segundo parametro como Password
                // executa a funcao
                callSmt.execute();
                return callSmt.getString(1);
        }catch (SQLException e){
             logger.severe(e.getMessage());
            return null;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }

    }

    /**
     * Método que devolve um ser com o email passado como parametro.
     * @param email Email  do user que se pretende saber informação.
     * @return User com email passado como parametro.
     */
    public User getUserComEmail (String email){
        User user=null;
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call get_user_com_email(?) }")) {
                callStmt.registerOutParameter(1,OracleTypes.CURSOR);

                callStmt.setString(2,email);

                callStmt.execute();

                try (ResultSet rSet = (ResultSet) callStmt.getObject(1)) {
                    if(rSet.next()){
                        user = new User(rSet.getInt(1),rSet.getString(2),rSet.getString(3),rSet.getString(4),rSet.getString(5));
                    }
                }
            return user;
        }catch (SQLException e){
                logger.severe(e.getMessage());
           return user;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     * Atualiza a password do utilizador recebendo por parâmetro:
     * @param idUser o id do utilizador
     * @param novaPassword a nova password
     * @return true se a atualização da password for bem sucedida, caso contrário devolve false
     */
    public boolean atualizarPassword(int idUser, String novaPassword) {
            try (CallableStatement callStmt = getConnection().prepareCall("{call proc_atualizarPassword(?,?) }")) {
                callStmt.setInt(1, idUser);
                callStmt.setString(2, novaPassword);
                callStmt.execute();
            return true;
        }catch (SQLException e){
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
     * Método que devolve o id do utilizador com o email passado como parametro .
     * @param email Email do utilizador.
     * @return id do user com esse emai..
     */
    public int getIdUtilizador(String email){
        int idUtilizador = -1;
            String query = "select id_user\n" +
                    "from utilizador u\n" +
                    "where u.email = ?";
            try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idUtilizador = rs.getInt(1);
                        getUserComEmail(email).setIdUser(idUtilizador);
                    }
                }

            return idUtilizador;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return idUtilizador;
        }finally {
            try {
                closeAll();
            } catch (SQLException e) {
                logger.severe(MSG);
            }
        }
    }
}
