package lapr.project.data;

import lapr.project.model.Farmacia;

import lapr.project.model.Scooter;
import lapr.project.model.SlotEstacionamento;
import oracle.jdbc.OracleTypes;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
/**
 * Classe responsável pela operações com a base de dados que envolvam as scooters da plataforma.
 */
public class ScooterHandler extends DBHandler{
    /**
     * Logger da classe atual
     */
    private static final Logger logger =Logger.getLogger ( ScooterHandler.class.getName () );
    /**
     * String associada com a scooter.
     */
    private static final String SCOOTER = "Scooter";

    /**
     * Obtém os dados de acesso à DB através do ficheiro application.properties
     *
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public ScooterHandler() throws IOException {
        super();
    }

    /**
     * Método que maraca a scooter passada como parametro para usar pelo estafeta a utilizar a plataforma atualmente.,
     * @param idVeiculo id do veiculo a ser marcado .
     * @return Sucesso se realizou a operacao com sucesso .
     */
    public boolean marcarScooterComoAUsar(int idVeiculo) {
        String sql="UPDATE MEIO_TRANSPORTE SET ID_SLOT=NULL WHERE ID_SCOOTER=?";
        try(Connection con=getConnection ();
            PreparedStatement ps=con.prepareStatement ( sql )){
            ps.setInt ( 1,idVeiculo );
            ps.executeUpdate ();
            return true;
        } catch (SQLException throwables) {
            logger.severe(throwables.getMessage());
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage () );
                e.printStackTrace ();
            }
        }
    }

    /**
     * Método que retorna a lista de scooters da farmácia passada como parametro
     * @param farmacia Farmácia que se pretende saber a sua lista de scooters.
     * @return Lista de scooters da farmácia passada como parâmetro.
     */
    public List<Scooter> getListaScootersFarmacia(Farmacia farmacia){
        List<Scooter>lScooters=new ArrayList<>();

        try(CallableStatement stmt = getConnection().prepareCall("{?  = call get_scooters_farmacia (?)}")){
            stmt.registerOutParameter(1,OracleTypes.CURSOR);
            stmt.setInt(2,farmacia.getId());
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);

            if(rs.next()){
                Scooter s=new Scooter(rs.getInt ( 1 ), //id
                        rs.getInt ( 2 ),//capacidade máxima
                        rs.getInt ( 3 ),//carga atual
                        rs.getDouble ( 4 ), //altura
                        rs.getDouble ( 5 ),//largura
                        rs.getDouble ( 6 ),//peso
                        rs.getDouble ( 7 ));
                lScooters.add ( s );
            }
            return lScooters;

        }catch (SQLException e){
            logger.severe(e.getMessage());
            return lScooters;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage ());
                e.printStackTrace ();
            }
        }
    }

    /**
     * Adiciona um Meio de Transporte ao Sistema, devolvendo o id gerado pelo mesmo.
     *
     * @param scooter o Meio de Transporte a adicionar ao sistema
     * @return o id gerado pelo sistema
     */
    public int adicionarScooter(Scooter scooter, int idFarmacia, boolean isCargaSlot) {
        int id = -1;
        int idSlot;
        try (CallableStatement callStmt = getConnection().prepareCall("{? = call funcAdicionarVeiculo(?,?,?,?,?,?,?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, scooter.getCapacidadeBateria());
            callStmt.setInt(3, scooter.getCargaAtual());
            callStmt.setDouble(4, scooter.getAltura());
            callStmt.setDouble(5, scooter.getLargura());
            callStmt.setDouble(6, scooter.getPeso());
            callStmt.setDouble(7, scooter.getVelocidadeMaxima());
            callStmt.setString(8, SCOOTER);
            callStmt.setNull ( 9,OracleTypes.NUMBER );
            callStmt.setNull ( 10,OracleTypes.NUMBER );

            callStmt.execute();
            id = callStmt.getInt(1);

            try (CallableStatement c = getConnection().prepareCall("{? = call funcObterSlotDisponivel(?,?,?) }")) {
                c.registerOutParameter(1, OracleTypes.INTEGER);
                c.setInt(2,id);
                c.setInt(3, idFarmacia);
                c.setInt(4, isCargaSlot ? 1 : 0);
                c.execute();
                idSlot = c.getInt(1);
            }
            scooter.setSlotEstacionamento(new SlotEstacionamento(idSlot, isCargaSlot));
            scooter.setId(id);

            return id;
        }catch (SQLException e) {
            logger.severe(e.getMessage());
            return id;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage () );
                e.printStackTrace ();
            }
        }
    }

    /**
     * Obtém os dados do meio de transporte recorrendo ao id do mesmo.
     * @param id o id do meio de transporte
     * @return devolve as informações do Meio de Transporte com o id igual ao passado por parâmetro.
     */
    public Scooter getScooter(int id){
        Scooter scooter = null;
        String query = "select m.capacidade_bateria, m.carga_atual, e.descricao, m.altura, m.largura,m.peso, m.velocidade_maxima\n" +
                "from meio_transporte m\n" +
                "inner join estado_meio_transporte e\n" +
                "on m.id_estado = e.id_estado\n" +
                "where id_transporte = ?";

        try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    scooter = new Scooter(id, rs.getInt(1), rs.getString(3).toUpperCase(Locale.ROOT),
                            rs.getInt(2),
                            rs.getDouble(4), rs.getDouble(5),
                            rs.getDouble(6), rs.getDouble(7));
            }

            String querySlot = "select s.id_slot, s.slot_carga\n" +
                    "from slot_estacionamento s\n" +
                    "inner join meio_transporte m \n" +
                    "on m.id_slot = s.id_slot\n" +
                    "where id_transporte = ?";

            try(PreparedStatement s = getConnection().prepareStatement(querySlot)) {
                s.setInt(1, id);
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next() && scooter != null) {
                        scooter.setSlotEstacionamento(new SlotEstacionamento (rset.getInt(1),
                                rset.getInt(2) == 1));
                    }
                }
            }
            return scooter;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return null;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage () );
                e.printStackTrace ();
            }
        }
    }
}
