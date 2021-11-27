package lapr.project.data;

import lapr.project.model.*;
import oracle.jdbc.OracleTypes;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Classe responsável pela operações com a base de dados que envolvam Farmacia
 */
public class FarmaciaHandler extends DBHandler {
    /**
     * Logger da classe atual
     */
    private static Logger logger =Logger.getLogger ( FarmaciaHandler.class.getName () );
    /**
     * Mensagem a ser mostrada quando ocorre algum erro na base de dados.
     */
    public static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Construtor da classe.
     * @throws IOException
     */
    public FarmaciaHandler() throws IOException {
        // Construtor que inicia a classe
    }

    /**
     * Adiciona uma nova farmácia ao sistema
     * @param farmacia farmacia a adicionar
     * @param numCargaScooters numero de slots de carga de scooters no estacionamento
     * @param numCargaDrones numero de slots de carga de drones no estacionamento
     * @return true se for adicionada, false caso contrario
     */
    public int addFarmacia(Farmacia farmacia, int numCargaScooters, int numCargaDrones) {
        int id = -1;

            try (CallableStatement callStmt = getConnection().prepareCall("{? = call insert_farmacia(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);
                callStmt.setString(2, farmacia.getNome());
                callStmt.setString(3, farmacia.getEndereco().getRua());
                callStmt.setString(4, farmacia.getEndereco().getNumPorta());
                callStmt.setString(5, farmacia.getEndereco().getCodPostal());
                callStmt.setString(6, farmacia.getEndereco().getLocalidade());
                callStmt.setString(7, farmacia.getEndereco().getPais());
                callStmt.setDouble(8, farmacia.getEndereco().getLatitude());
                callStmt.setDouble(9, farmacia.getEndereco().getLongitude());
                callStmt.setDouble(10, farmacia.getEndereco().getAltitude());
                callStmt.setInt(11, farmacia.getEstacionamento().getCapacidadeMaximaScooters());
                callStmt.setInt(12, numCargaScooters);
                callStmt.setInt(13, farmacia.getEstacionamento().getCapacidadeMaximaDrones());
                callStmt.setInt(14, numCargaDrones);
                callStmt.setDouble(15, farmacia.getEstacionamento().getPotenciaMaxima());
                callStmt.execute();
                id = callStmt.getInt(1);

            farmacia.setId(id);
            return id;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return id;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     * Método que atualiza  a informação da farmácia passada como parametro.
     * @param info informação nova da farmácia
     * @param farmacia Farmacia que se pretende atualizar a informação
     * @return True se foi atualizado com sucesso a informacao da farmacia.
     */
    public boolean atualizarFarmacia(String[] info,Farmacia farmacia)  {
        if ( info[0] != null)
            farmacia.setNome(info[0]);

        if (info[1] != null)
            farmacia.getEndereco().setRua(info[1]);

        if (info[2] != null)
            farmacia.getEndereco().setNumPorta(info[2]);

        if (info[3] != null)
            farmacia.getEndereco().setCodPostal(info[3]);

        if (info[4] != null)
            farmacia.getEndereco().setLocalidade(info[4]);

        if (info[5] != null)
            farmacia.getEndereco().setPais(info[5]);

        if (info[6] != null)
            farmacia.getEndereco().setLatitude(Double.parseDouble(info[6]));

        if (info[7] != null)
            farmacia.getEndereco().setLongitude(Double.parseDouble(info[7]));

        if (info[8] != null)
            farmacia.getEndereco().setAltitude(Double.parseDouble(info[8]));



            try(CallableStatement callStmt = getConnection().prepareCall("{ call update_Farmacia(?,?,?,?,?,?,?,?,?,?) }")) {
                callStmt.setInt(1, farmacia.getId());
                callStmt.setString(2, farmacia.getNome());
                callStmt.setString(3, farmacia.getEndereco().getRua());
                callStmt.setString(4, farmacia.getEndereco().getNumPorta());
                callStmt.setString(5, farmacia.getEndereco().getCodPostal());
                callStmt.setString(6, farmacia.getEndereco().getLocalidade());
                callStmt.setString(7, farmacia.getEndereco().getPais());
                callStmt.setDouble(8, farmacia.getEndereco().getLatitude());
                callStmt.setDouble(9, farmacia.getEndereco().getLongitude());
                callStmt.setDouble(10, farmacia.getEndereco().getAltitude());
                callStmt.execute();

                return true;

        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return false;
        }finally {
            try {
                closeAll();
            } catch (SQLException e) {
                logger.severe(MSG);
            }
        }
    }

    /**
     * Devolve uma lista de farmácias com o seu id e nome
     * @return ArrayList de farmácias ou null se ocorrer um erro ao aceder à DB
     */
    public List<Farmacia> getListaFarmacias() {

            List<Farmacia> farmacias = new ArrayList<>();

            try (ResultSet rs = getConnection().createStatement().executeQuery("SELECT f.id_farmacia, f.nome ,RUA, NUM_PORTA, COD_POSTAL, LOCALIDADE, PAIS, LATITUDE, LONGITUDE, ALTITUDE\n" +
                    "    FROM farmacia f\n" +
                    "        inner join ENDERECO e on e.ID_ENDERECO = f.ID_ENDERECO")) {
                while (rs.next()) {
                    Endereco endereco = new Endereco(rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getDouble(8), rs.getDouble(9),rs.getDouble(10),true);
                    farmacias.add(new Farmacia(rs.getInt(1), rs.getString(2),endereco));
                }
                return  farmacias;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            return farmacias;
        }finally {
            try {
                closeAll();
            } catch (SQLException e) {
                logger.severe(MSG);
            }
        }
    }

    /**
     * Método que devolve uma farmacia atraves do seu endereco
     * @param endereco Endereco da farmacia
     * @return Farmacia com esse endereco.
     */
    public Farmacia getFarmaciaByEndereco(Endereco endereco){
        Farmacia farmacia = null;

            try(CallableStatement callStmt = getConnection().prepareCall("{?=call return_Farmacia_mais_perto(?,?,?,?,?,?,?,?) }")){
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);
                callStmt.setString(2,endereco.getRua());
                callStmt.setString(3,endereco.getNumPorta());
                callStmt.setString(4,endereco.getCodPostal());
                callStmt.setString(5,endereco.getLocalidade());
                callStmt.setString(6,endereco.getPais());
                callStmt.setDouble(7,endereco.getLatitude());
                callStmt.setDouble(8,endereco.getLongitude());
                callStmt.setDouble(9,endereco.getAltitude());

                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if(rSet.next()){
                    Endereco enderecoRecebido = new Endereco(rSet.getInt(3),rSet.getString(4),rSet.getString(5),rSet.getString(6),rSet.getString(7),rSet.getString(8),rSet.getDouble(9),rSet.getDouble(10),rSet.getDouble(11),true);
                    farmacia = new Farmacia(rSet.getInt(1),rSet.getString(2),enderecoRecebido);
                }
                return farmacia;

        }catch (SQLException e){
                logger.severe(e.getMessage());
            return farmacia;
        }finally {
            try {
                closeAll();
            } catch (SQLException e) {
                logger.severe(MSG);
            }
        }

    }

    /**
     * Método que devolve a farmacia com o id passado como parametro.
     * @param idFarmacia id da farmacia que se pretende obter.
     * @return Farmacia com esse endereco.
     */
    public Farmacia getFarmacia(int idFarmacia) {
        Endereco en = null;
        Farmacia f;
        try {
            openConnection();

            String query = "select f.nome, e.rua, e.num_porta, e.cod_postal, e.localidade, e.pais, e.latitude, e.longitude, \n" +
                    "e.altitude, es.maximo_scooters, es.maximo_drones, es.potencia_maxima\n" +
                    "from farmacia f\n" +
                    "inner join endereco e\n" +
                    "on f.id_endereco = e.id_endereco\n" +
                    "inner join ESTACIONAMENTO es\n" +
                    "on f.id_estacionamento = es.id_estacionamento\n" +
                    "where f.id_farmacia = ?";

            try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
                stmt.setInt(1, idFarmacia);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next())
                        en = new Endereco(rs.getString(2), rs.getString(3), rs.getString(4),
                                rs.getString(5), rs.getString(6), rs.getDouble(7),
                                rs.getDouble(8), rs.getDouble(9),true);
                        f = new Farmacia(rs.getString(1), en);
                        f.setEstacionamento(new Estacionamento(rs.getInt(10), rs.getInt(11), rs.getDouble(12)));
                        f.setId(idFarmacia);
                }
            }
            return f;
        } catch (SQLException e) {
            return null;
        }finally {
            try {
                closeAll();
            } catch (SQLException e) {
                logger.severe(MSG);
            }
        }
    }
}
