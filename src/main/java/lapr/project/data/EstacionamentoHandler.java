package lapr.project.data;

import lapr.project.model.SlotEstacionamento;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Classe responsável pela operações com a base de dados que envolvam estacionamento
 */
public class EstacionamentoHandler extends DBHandler {
    /**
     * Logger da classe atual.
     */
    private static Logger logger=Logger.getLogger(EstacionamentoHandler.class.getName());
    /**
     * Mensagem de erro a ser mostrada quando ocorre um erro.
     */
    private static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Construtor da classe.
     * @throws IOException
     */
    public EstacionamentoHandler() throws IOException {
        // Construtor que inicia a classe
    }

    /**
     * Devolve a potencia maxima do estacionamento de uma farmacia
     * @param idFarmacia id da farmácia
     * @return a potencia máxima no estacionamento
     */
    public double getPotenciaMaxima(int idFarmacia) {

            String query = "SELECT potencia_maxima FROM estacionamento " +
                    "INNER JOIN farmacia " +
                    "ON estacionamento.id_estacionamento = farmacia.id_estacionamento " +
                    "WHERE farmacia.id_farmacia = ?";
            try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
                stmt.setInt(1, idFarmacia);

                double potencia;
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next())
                        potencia = rs.getDouble(1);
                    else
                        potencia = -1;

                return potencia;
            }
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return -1;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     * Regista estacionamento de meio de transporte
     * @param idFarmacia id da farmacia
     * @param idTransporte id do transporte
     * @param carga indica se é slot de carga
     * @return true se for registado com sucesso, falso caso contrário
     */
    public boolean estacionar(int idFarmacia, int idTransporte, boolean carga) {

            try(CallableStatement callStmt = getConnection().prepareCall("{ call estacionar(?,?,?) }")) {

                callStmt.setInt(1, idFarmacia);
                callStmt.setInt(2, idTransporte);
                callStmt.setInt(3, carga ? 1 : 0);

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
     *  Método que devolve o email da ultima pessoa a utilizar o transporte com o id passado como parametro.
     * @param idTransporte id do transporte que se pretende saber a informação.
     * @return Email da ultima pessoa a utilizar o transporte.
     */
    public String emailFromTransporte(int idTransporte) {
            String email;
            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call email_from_transporte(?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
                callStmt.setInt(2, idTransporte);

                callStmt.execute();

                email = callStmt.getString(1);

            return email;
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
     * Adiciona slots de estacionamento a uma farmácia
     * @param idFarmacia id da farmácia
     * @param tipoSlot tipos de veiculo do slot
     * @param quantidade qunatidade de slots a adicionar
     * @param numCarga numero de slots que sao de carga
     * @return true se forem adicionados, false caso contrario
     */
    public boolean adicionarSlotsEstacionamento(int idFarmacia, String tipoSlot, int quantidade, int numCarga) {

            try (CallableStatement callStmt = getConnection().prepareCall("{ call insert_slots(?,?,?,?) }")) {
                callStmt.setInt(1, idFarmacia);
                callStmt.setString(2, tipoSlot);
                callStmt.setInt(3, quantidade);
                callStmt.setInt(4, numCarga);
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
     * Método que devolve um slot de estacionamento fornecendo o seu id e o id do estacionamento a qual pertence.
     * @param idSlot id do slot de estacionamento
     * @param idEstacionamento id do estacionamento a qual pertence.
     * @return Slot de estacionamento a qual pertence.
     */
    public SlotEstacionamento getSlotEstacionamento(int idSlot, int idEstacionamento){
        if (idSlot < 0 || idEstacionamento < 0) return null;
        SlotEstacionamento slot = null;

            String query = " select slot_carga\n" +
                    " from slot_estacionamento s\n" +
                    " where s.id_estacionamento = ? and s.id_slot = ?";
            try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
                stmt.setInt(1, idEstacionamento);
                stmt.setInt(1, idSlot);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next())
                        slot = new SlotEstacionamento(idSlot, rs.getInt(1) == 1);
                }

            return slot;
        } catch (SQLException e) {
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

}
