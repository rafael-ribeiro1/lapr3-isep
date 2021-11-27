package lapr.project.data;

import lapr.project.model.Encomenda;
import lapr.project.model.Entrega;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

/**
 *  Classe que estabelece a comunicação com a base de dados remota e devolve as informacoes de entregas.
 */
public class EntregaDataHandler extends DBHandler{

    /**
     * O logger da Classe EntregaDataHandler
     */
    private static Logger logger =Logger.getLogger ( EntregaDataHandler.class.getName () );

    /**
     * A mensagem do logger quando ocorrer erro a fechar ligações
     */
    private static final String MSG = "Erro ao fechar as ligações." ;

    /**
     * Obtém os dados de acesso à DB através do ficheiro application.properties
     *
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public EntregaDataHandler() throws IOException {
        //Construtor
    }

    /**
     * Método que insere uma entrega na base de dados.
     * @param e Entrega a ser introduzida na base de dados.
     * @return Sucesso se foi registada como sucesso na base de dados remota.
     */
    public boolean inserirEntrega(Entrega e){
        try(Connection con=getConnection ()) {
            try(CallableStatement cs=con.prepareCall ( "{? = call inserirEntrega(?,?,?) }")){
                cs.registerOutParameter ( 1,OracleTypes.INTEGER );
                cs.setDate ( 2, Date.valueOf ( e.getDataEntrega () ) );
                if(e.getEstafeta ()!=null){
                    cs.setInt ( 3,e.getEstafeta ().getIdUser () );
                }else{
                    cs.setNull(3, OracleTypes.INTEGER);
                }
                cs.setInt ( 4,e.getMeioTransporte ().getId () );
                cs.execute ();
                int id = cs.getInt(1);
                String sql="INSERT INTO ENTREGA_ENCOMENDAS VALUES (?,?)";
                try(PreparedStatement ps=con.prepareStatement(sql)){
                    ps.setInt ( 1,id );
                    for (Encomenda enc : e.getListEncomendas ()) {
                        ps.setInt ( 2,enc.getId () );
                        ps.executeUpdate ();
                    }
                }
            }
        } catch (SQLException throwables) {
            Logger.getAnonymousLogger().severe(throwables.getMessage());
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException ex){
                logger.severe ( MSG);
            }
        }
        return true;
    }
}
