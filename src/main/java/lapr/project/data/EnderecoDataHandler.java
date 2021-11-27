package lapr.project.data;

import lapr.project.model.Caminho;
import lapr.project.model.Endereco;
import lapr.project.model.Rua;
import lapr.project.model.graph.adjacencyMap.Graph;
import lapr.project.utils.FisicaAlgoritmos;
import lapr.project.utils.Tuple;
import oracle.jdbc.internal.OracleTypes;
import oracle.ucp.util.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Classe que estabelece a comunicação com a base de dados remota e devolve os enderecos registados no sistema.
 */
public class EnderecoDataHandler  extends DBHandler{
    /**
     * Mensagem de erro a ser mostrada qauando ocorre algum erro.
     */
    public static final String MSG_ERRO = "Erro na ligação à base de dados";
    /**
     * Logger da classe atual.
     */
    private static Logger logger =Logger.getLogger ( EnderecoDataHandler.class.getName () );

    /**
     * Obtém os dados de acesso à DB através do ficheiro application.properties
     *
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public EnderecoDataHandler() throws IOException {
        super();
    }

    /**
     * Método que devolve um mapa com todos enderecos na base de dados remota.
     * @returnMétodo que devolve um mapa com todos enderecos na base de dados remota.
     */
    private Map<Integer, Endereco> getEnderecos(){
        Map<Integer, Endereco> enderecoMap = new HashMap<> ();
        Endereco end;
        //obter os enderecos das farmacias
        String sql="select e.* from endereco e " +
                    "inner join FARMACIA f on e.ID_ENDERECO=f.ID_ENDERECO";

        try(Connection con=getConnection ();
            PreparedStatement ps1=con.prepareStatement ( sql );
            ResultSet rs=ps1.executeQuery (sql)){
            while (rs.next ()) {
                end = criarEndereco ( rs, true );
                enderecoMap.put ( end.getId (), end );
            }
        }catch (SQLException e){
            logger.severe ( MSG_ERRO );
            return new HashMap<> (); //devolver mapa vazio
        }finally {
            try{
                closeAll ();
            }catch (SQLException throwables) {
                logger.severe ( MSG_ERRO );
            }
        }
        //obter endereços de não farmácias
        sql="select * from endereco minus (select e.* from endereco e " +
            "inner join FARMACIA f on e.ID_ENDERECO=f.ID_ENDERECO)";

        try(Connection con=getConnection ();
            PreparedStatement ps2=con.prepareStatement ( sql );
            ResultSet rs=ps2.executeQuery (sql)){
            while (rs.next ()) {
                end = criarEndereco ( rs, false );
                enderecoMap.put ( end.getId (), end );
            }
        }catch (SQLException e){
            logger.severe ( MSG_ERRO );
            return new HashMap<> (); //devolver mapa vazio
        }finally {
            try{
                closeAll ();
            }catch (SQLException throwables) {
                logger.severe ( MSG_ERRO );
            }
        }
        return enderecoMap;
    }

    /**
     * Método que cria o grafo de espaço aéreo com os endereços guardados na base de dados remota.
     * @return Grafo de espaço aéreo com endereços guardados na base de dados remota.
     */
    public Graph<Endereco, Caminho> criarGrafoEspacoAereo(){
        Graph<Endereco, Caminho> g=new Graph<> ( true );
        //id, Endereco
        Map<Integer, Endereco> enderecoMap;

        String sql="select EA.ID_ENDERECO1,EA.ID_ENDERECO2,EA.ID_DIRECAO, EA.VELOCIDADE_VENTO, EA.ANGULO_VENTO " +
                "from ENDERECOS_ADJACENTES EA " +
                "where EA.ID_TIPO=2";

        enderecoMap=getEnderecos (); // obter enderecos

        try(Connection con=getConnection ();
            PreparedStatement ps=con.prepareStatement ( sql );
            ResultSet rs=ps.executeQuery (sql)) {

            //obter as associacoes entre os enderecos
            while(rs.next ()){
                int id1=rs.getInt ( 1 );
                int id2=rs.getInt ( 2 );
                int idDirecao=rs.getInt ( 3 );
                double velocidadeVento=rs.getDouble(4);
                double anguloVento=rs.getDouble(5);

                Endereco e1=enderecoMap.get ( id1 );
                Endereco e2=enderecoMap.get ( id2 );
                double distancia= FisicaAlgoritmos.distancia ( e1,e2 );

                Caminho c=new Caminho("Aéreo",distancia,velocidadeVento,anguloVento);
                if(idDirecao==1){ // sentido único e1 -> e2
                    g.insertEdge ( e1,e2,c,c.getComprimento () );
                }else if(idDirecao==2){ // sentido único e2 -> e1
                    g.insertEdge ( e2,e1,c,c.getComprimento () );
                }else{ // duplo sentido
                    g.insertEdge ( e1,e2,c,c.getComprimento () );
                    if(anguloVento>0) anguloVento=180-anguloVento;
                    else anguloVento+=180;
                    c=new Caminho ( "Aéreo",distancia,velocidadeVento,anguloVento );
                    g.insertEdge ( e2,e1,c,c.getComprimento () );
                }
            }
        }catch (SQLException e){
            logger.severe ( MSG_ERRO );
            return g;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( "Erro ao fechar as ligações." );
            }
        }
        return g;
    }
    /**
     * Método que cria o grafo de espaço terreste com os endereços guardados na base de dados remota.
     * @return Grafo de espaço terreste com endereços guardados na base de dados remota.
     */
    public Graph<Endereco, Rua> criarGrafoEnderecos() {
        Graph<Endereco, Rua> g=new Graph<> ( true );
        //id, Endereco
        Map<Integer, Endereco> enderecoMap;

        String sql="select EA.ID_ENDERECO1,EA.ID_ENDERECO2,EA.ID_DIRECAO,CR.DESCRICAO,CR.COEFICIENTE_RESISTENCIA, EA.VELOCIDADE_VENTO, EA.ANGULO_VENTO " +
                "from ENDERECOS_ADJACENTES EA " +
                "inner join CONDICOES_RUA CR on EA.ID_CONDICOES = CR.ID_CONDICOES " +
                "where EA.ID_TIPO=1";

        enderecoMap=getEnderecos (); // obter enderecos

        try(Connection con=getConnection ();
            PreparedStatement ps=con.prepareStatement ( sql );
            ResultSet rs=ps.executeQuery (sql)) {

            //obter as associacoes entre os enderecos
            while(rs.next ()){
                int id1=rs.getInt ( 1 );
                int id2=rs.getInt ( 2 );
                int idDirecao=rs.getInt ( 3 );
                String descricao=rs.getString ( 4 );
                double coeficiente = rs.getDouble ( 5 );
                double velocidadeVento=rs.getDouble(6);
                double anguloVento=rs.getDouble(7);

                Endereco e1=enderecoMap.get ( id1 );
                Endereco e2=enderecoMap.get ( id2 );

                double distancia= FisicaAlgoritmos.distancia ( e1,e2 );
                double inclinacao= FisicaAlgoritmos.angulo (e1,e2 );

                Rua r=new Rua(descricao,distancia,velocidadeVento,anguloVento,coeficiente,inclinacao);
                if(idDirecao==1){ // sentido único e1 -> e2
                    g.insertEdge ( e1,e2,r,r.getComprimento () );
                }else if(idDirecao==2){ // sentido único e2 -> e1
                    g.insertEdge ( e2,e1,r,r.getComprimento () );
                }else{ // duplo sentido
                    g.insertEdge ( e1,e2,r,r.getComprimento () );
                    if(anguloVento>0) anguloVento=180-anguloVento;
                    else anguloVento+=180;
                    r=new Rua(descricao,distancia,velocidadeVento,anguloVento,coeficiente,inclinacao);
                    g.insertEdge ( e2,e1,r,r.getComprimento () );
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            logger.severe ( MSG_ERRO );
            return g;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( "Erro ao fechar as ligações." );
            }
        }
        return g;
    }

    /**
     * Método que devolve um endereço guardado na base de dados.
     * @param rs Result Set da query a base de dados
     * @param isFarmacia True se o endereço for de uma farmacia.
     * @return Objeto que representa o endereço
     * @throws SQLException
     */
    public static Endereco criarEndereco(ResultSet rs, boolean isFarmacia) throws SQLException{
        int id=rs.getInt(1);
        String rua=rs.getString ( 2 );
        String numPorta=rs.getString ( 3 );
        String codPostal=rs.getString ( 4 );
        String localidade=rs.getString ( 5 );
        String pais = rs.getString ( 6 );
        double latitude=rs.getDouble ( 7 );
        double longitude=rs.getDouble ( 8 );
        double altitude = rs.getDouble(9);
        return new Endereco ( id,rua,numPorta,codPostal,localidade,pais,latitude,longitude,altitude, isFarmacia );
    }

    /**
     * Método que insere os enderecos adjacentes ou seja as arestas do grafo  no grafo passado como parametro..
     * @param enderecosAdjacentes arestas do grafo.
     * @param tipoConexao Tipo de conexao do grafo.
     * @return Grafo com arestas.
     */

    public boolean insertEnderecosAdjacentes(List<Pair<Tuple<Endereco, Endereco, String>, Tuple<String, Double, Double>>> enderecosAdjacentes, String tipoConexao) {
            try{
                boolean sucesso = true;
                for (Pair<Tuple<Endereco, Endereco, String>, Tuple<String, Double, Double>> p : enderecosAdjacentes) {
                    try (CallableStatement callStmt = getConnection().prepareCall("{ call insert_enderecos_adjacentes(?,?,?,?,?,?,?,?,?) }")) {
                        callStmt.setDouble(1, p.get1st().get1st().getLatitude());
                        callStmt.setDouble(2,  p.get1st().get1st().getLongitude());
                        callStmt.setDouble(3,  p.get1st().get2nd().getLatitude());
                        callStmt.setDouble(4,  p.get1st().get2nd().getLongitude());
                        callStmt.setString(5, tipoConexao);
                        callStmt.setString(6,  p.get1st().get3rd()); //sentido
                        String condicoes = p.get2nd().get1st();
                        if (condicoes != null)
                            callStmt.setString(7, condicoes);
                        else
                            callStmt.setNull(7, OracleTypes.VARCHAR);
                        callStmt.setDouble(8, p.get2nd().get2nd()); //velocidade do vento
                        callStmt.setDouble(9, p.get2nd().get3rd()); //ângulo do vento

                        callStmt.execute();
                    }catch (SQLException e) {
                        logger.severe(e.getMessage());
                        sucesso = false;
                        continue;
                    }
                }
            return sucesso;
        }finally {
            try {
                closeAll();
            } catch (SQLException e) {
                logger.severe("Erro ao fechar as ligações.");
            }
        }
    }

}
