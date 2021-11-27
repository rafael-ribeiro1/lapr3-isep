package lapr.project.data;
import lapr.project.model.GestorFarmacia;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Classe responsável pela operações com a base de dados que envolvam Gestor de Farmácia
 */
public class GestorHandler extends DBHandler {
    /**
     * Logger da classe
     */
    private static Logger logger= Logger.getLogger ( GestorHandler.class.getName () );

    /**
     * Construtor da classe
     * @throws IOException
     */
    public GestorHandler() throws IOException {
        // constructor para lancar excecao
    }

    /**
     * Regista um novo gestor na base de dados
     * @param username
     * @param password
     * @param tipoUser
     * @param email
     * @param idFarmacia
     * @return
     */
    public boolean addGestor(String username, String password, String tipoUser, String email, int idFarmacia) {


            try (CallableStatement callStmt = getConnection().prepareCall("{ call insert_gestor(?,?,?,?,?) }");) {
                callStmt.setString(1, username);
                callStmt.setString(2, password);
                callStmt.setString(3, tipoUser);
                callStmt.setString(4, email);
                callStmt.setInt(5, idFarmacia);

                callStmt.execute();

            return true;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( "Erro ao fechar as ligações." );
            }
        }
    }

    /**
     * Método que devolve a farmacia atraves do seu gestor
     * @param email Email do gestor da farmacia
     * @return Farmacia com o gestor com o email
     */
    public int getFarmaciaFromGestorEmail(String email) {

            int idFarmacia=-1;

            String query = "select ID_FARMACIA from GESTOR_FARMACIA inner join UTILIZADOR U on U.ID_USER = GESTOR_FARMACIA.ID_GESTOR where u.EMAIL=?";
            try (PreparedStatement stmt = getConnection().prepareStatement(query);) {
                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery();) {
                    if (rs.next())
                        idFarmacia = rs.getInt(1);
                    else
                        idFarmacia = -1;
                }

            return idFarmacia;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return idFarmacia;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( "Erro ao fechar as ligações." );
            }
        }
    }

    /**
     * Método que devolve o gestor atraves do email dele.
     * @param email email do gestor
     * @return Gestor com esse email.
     */
    public GestorFarmacia getGestorFarmaciaByEmail(String email) {
       GestorFarmacia g = null;


            String query = "select u.username, u.PASSWORD, g.ID_FARMACIA from UTILIZADOR u " +
                    "inner join GESTOR_FARMACIA g on U.ID_USER = g.ID_GESTOR" +
                    " where u.EMAIL=?";
            try (PreparedStatement stmt = getConnection().prepareStatement(query);) {
                stmt.setString(1, email);

                try (ResultSet rs = stmt.executeQuery();) {
                    if (rs.next()){
                        g = new GestorFarmacia(rs.getString(1), rs.getString(2),
                                email, rs.getInt(3));
                    }
                }

            return g;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return g;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( "Erro ao fechar as ligações." );
            }
        }
    }
}
