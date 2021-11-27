package lapr.project.data;
import lapr.project.model.Drone;
import lapr.project.model.Farmacia;
import lapr.project.model.SlotEstacionamento;
import oracle.jdbc.OracleTypes;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Classe que estabelece a comunicação com a base de dados remota e devolve as informacoes de drones.
 */
public class DroneHandler extends DBHandler {
    /**
     * Tipo de meio de transporte da classe
     */
    private static final String DRONE = "Drone";
    /**
     * Logger da classe
     */
    private static Logger logger = Logger.getLogger(DroneHandler.class.getName());
    /**
     * Mensagem a ser mostrada quando acontece um erro.
     */
    private static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Construtor para inicializar classe
     * @throws IOException
     */
    public DroneHandler() throws IOException {
        super();
    }

    /**
     * Adiciona um Meio de Transporte ao Sistema, devolvendo o id gerado pelo mesmo.
     *
     * @param drone o Meio de Transporte a adicionar ao sistema
     * @return o id gerado pelo sistema
     */
    public int adicionarDrone(Drone drone, int idFarmacia, boolean isCargaSlot) {
        int id = -1;
        int idSlot;
        try (CallableStatement callStmt = getConnection().prepareCall("{? = call funcAdicionarVeiculo(?,?,?,?,?,?,?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, drone.getCapacidadeBateria());
            callStmt.setInt(3, drone.getCargaAtual());
            callStmt.setDouble(4, drone.getAltura());
            callStmt.setDouble(5, drone.getLargura());
            callStmt.setDouble(6, drone.getPeso());
            callStmt.setDouble(7, drone.getVelocidadeMaxima());
            callStmt.setString(8, DRONE);
            callStmt.setDouble(9, drone.getComprimento());
            callStmt.setDouble(10, drone.getVelocidadeLevantamento());

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
            drone.setSlotEstacionamento(new SlotEstacionamento(idSlot, isCargaSlot));
            drone.setId(id);

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
     * ObtÃ©m os dados do meio de transporte recorrendo ao id do mesmo.
     * @param id o id do meio de transporte
     * @return devolve as informaÃ§Ãµes do Meio de Transporte com o id igual ao passado por parÃ¢metro.
     */
    public Drone getDrone(int id){
        Drone transporte = null;
        String query = "select m.capacidade_bateria, m.carga_atual, e.descricao, m.altura, m.largura,m.peso, m.velocidade_maxima, d.comprimento, d.velocidade_levantamento\n" +
                "from meio_transporte m\n" +
                "inner join estado_meio_transporte e\n" +
                "on m.id_estado = e.id_estado\n" +
                "inner join drone d\n" +
                "on m.ID_TRANSPORTE = d.ID_DRONE\n" +
                "where id_transporte = ?";

        try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    transporte = new Drone(id, rs.getInt(1), rs.getString(3).toUpperCase(Locale.ROOT),
                            rs.getInt(2),
                            rs.getDouble(4), rs.getDouble(5),
                            rs.getDouble(6), rs.getDouble(7),
                            rs.getDouble(8), rs.getDouble(9));
            }

            String querySlot = "select s.id_slot, s.slot_carga\n" +
                    "from slot_estacionamento s\n" +
                    "inner join meio_transporte m \n" +
                    "on m.id_slot = s.id_slot\n" +
                    "where id_transporte = ?";

            try(PreparedStatement s = getConnection().prepareStatement(querySlot)) {
                s.setInt(1, id);
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next() && transporte != null) {
                        transporte.setSlotEstacionamento(new SlotEstacionamento (rset.getInt(1),
                                rset.getInt(2) == 1));
                    }
                }
            }
            return transporte;
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

    /**
     * Método que fornece a lista de drones estacionados numa farmacia
     * @param farmacia farmacia que se pretende saber
     * @return
     */
    public List<Drone> getListaDronesFarmacia(Farmacia farmacia){
        List<Drone>lDrone=new ArrayList<>();
        try{
            openConnection();
            try(CallableStatement stmt = getConnection().prepareCall("{?  = call get_drones_farmacia(?)}")){
                stmt.registerOutParameter(1,OracleTypes.CURSOR);
                stmt.setInt(2,farmacia.getId());
                stmt.execute();

                ResultSet rs = (ResultSet) stmt.getObject(1);

                if(rs.next()){
                    Drone d=new Drone(
                            rs.getInt ( 1 ), //id
                            rs.getInt ( 2 ),//capacidade máxima
                            rs.getInt ( 3 ),//carga atual
                            rs.getDouble ( 4 ), //altura
                            rs.getDouble ( 5 ),//largura
                            rs.getDouble ( 6 ),//peso
                            rs.getDouble ( 7 ),//velocidade maxima
                            rs.getDouble(8), //comprimento
                            rs.getDouble(9)); //velocidade de levantamento
                    lDrone.add ( d );
                }
                return lDrone;
            }
        }catch (SQLException e){
            logger.severe(e.getMessage());
            return lDrone;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }
}

