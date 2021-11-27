package lapr.project.data;
import lapr.project.model.SlotEstacionamento;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
/**
 * Classe responsável pela operações com a base de dados que envolvam os Transportes do sistema.
 */
public class TransporteHandler extends DBHandler {
    /**
     * Logger da classe atual,
     */
    private static final Logger logger = Logger.getLogger ( TransporteHandler.class.getName () );
    /**
     * Mensagem a ser mostrada quando ocorre um erro.
     */
    public static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Obtém os dados de acesso à DB através do ficheiro application.properties
     *
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public TransporteHandler() throws IOException {
        //Construtor
    }
    /**
     * Remove o Meio de Transporte cujo id é passado por parâmetro, colocando o estado do Meio de Transporte como "Avariada" no Sistema
     * @param id O id do Meio de Transporte a remover
     * @return true se a remoção for bem sucedida, caso contrário, retorna false
     */
    public boolean removerTransporte(int id) {

        try (CallableStatement callStmt = getConnection().prepareCall("{call procRemoverVeiculo(?) }")) {
            callStmt.setInt(1, id);
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

    /**
     * Atualiza os valores passados por parâmetro do Meio de Transporte
     * @param id o id do Meio de Transporte a atualizar
     * @param cargaAtual novo valor de carga atual
     * @param slot novo valor de slot
     * @return true se a atualização for bem sucedida, caso contrário, devolve false.
     */
    public boolean atualizarTransporte(int id, int cargaAtual, SlotEstacionamento slot) {

        try (CallableStatement callStmt = getConnection().prepareCall("{call proc_AtualizarVeiculo(?,?,?) }")) {

            callStmt.setInt(1, id);
            callStmt.setInt(2, cargaAtual);
            if (slot != null) {
                callStmt.setInt(3, slot.getId());
            }else{
                callStmt.setNull(3, OracleTypes.INTEGER);
            }
            callStmt.execute();

        return true;
        }catch (SQLException e) {
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

}
