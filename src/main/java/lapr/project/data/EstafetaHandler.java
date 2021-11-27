package lapr.project.data;

import lapr.project.model.*;
import oracle.jdbc.OracleTypes;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

/**
 * Classe que estabelece a comunicação com a base de dados remota e devolve as informacoes de estafetas.
 */
public class EstafetaHandler extends DBHandler {
    /**
     * Logger da classe atual.
     */
    private static Logger logger =Logger.getLogger (EstafetaHandler.class.getName () );
    /**
     * Mensagem a ser mostrada quando acontece um erro.
     */
    public static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Obtém os dados de acesso à DB através do ficheiro application.properties
     *
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public EstafetaHandler() throws IOException {
        super();
    }

    /**
     * Regista um novo gestor na base de dados
     * @param estafeta gestor a ser registado
     * @return true se for adicionado, falso caso ocorra erro
     */
    public boolean addEstafeta(Estafeta estafeta) {
            try(CallableStatement callStmt = getConnection().prepareCall("{ call insert_estafeta(?,?,?,?,?,?,?,?,?) }")) {

                callStmt.setString(1, estafeta.getUsername());
                callStmt.setString(2, estafeta.getPassword());
                callStmt.setString(3, estafeta.getTipoUser());
                callStmt.setString(4, estafeta.getEmail());
                callStmt.setInt(5, estafeta.getFarmacia().getId());
                callStmt.setString(6, estafeta.getNome());
                callStmt.setInt(7, estafeta.getNif());
                callStmt.setDouble(8, estafeta.getCargaMaxima());
                callStmt.setDouble(9, estafeta.getPeso());

                callStmt.execute();
            return true;
        } catch (SQLException e) {
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
     * Método que atualiza a informação de um estafeta passado como parametro
     * @param estafeta Estafeta a ser atualizado
     * @param idFarmacia id da farmácia a qual  o estafeta pertence.
     * @return Sucesso se houve a atualização com sucesso.
     */
    public boolean atualizarEstafeta(Estafeta estafeta, int idFarmacia) {


            try(CallableStatement callStmt = getConnection().prepareCall("{ call update_estafeta(?,?,?,?,?,?) }")) {

                callStmt.setInt(1, estafeta.getIdUser());
                callStmt.setInt(2, idFarmacia);
                callStmt.setString(3, estafeta.getNome());
                callStmt.setInt(4, estafeta.getNif());
                callStmt.setDouble(5, estafeta.getCargaMaxima());
                callStmt.setDouble(6, estafeta.getPeso());

                callStmt.execute();

                return true;

        } catch (SQLException e) {
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
     * Devolve uma lista de Estafetas com o seu id e nome
     * @return ArrayList de estafetas ou null se ocorrer um erro ao aceder à DB
     */
    public List<Estafeta> getListaEstafetas() {

            List<Estafeta> estafetas = new ArrayList<>();
            try(Statement st = getConnection().createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT id_estafeta, id_farmacia, nome,nif,carga_Maxima,peso FROM estafeta")) {
                    while (rs.next()) {
                        estafetas.add(new Estafeta(rs.getInt("id_estafeta"),
                                rs.getString("nome"),
                                rs.getDouble("carga_Maxima"),
                                rs.getInt("nif"),
                                rs.getInt("id_farmacia"),
                                rs.getDouble("peso")));
                    }
                }

            return estafetas;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return null;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }

    /**
     * Método que devolve o estafeta fornecendo o email como parametro.
     * @param email email do estafeta que se pretende saber.
     * @return Estafeta com o email passado como parametro.
     */
    public Estafeta getEstafetaByEmail(String email) {
        Estafeta estafeta = null;
            try(CallableStatement callStmt = getConnection().prepareCall("{?=call get_estafeta(?)}")) {
                callStmt.registerOutParameter(1,OracleTypes.CURSOR);
                callStmt.setString(2,email);
                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);
                if(rSet.next()){
                    estafeta = new Estafeta(rSet.getInt(1),rSet.getDouble(2));
                }
                rSet.close();
                return estafeta;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return estafeta;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }

    }

}




