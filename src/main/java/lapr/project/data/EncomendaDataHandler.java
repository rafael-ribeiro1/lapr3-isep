package lapr.project.data;

import lapr.project.model.*;
import oracle.jdbc.OracleTypes;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Classe que estabelece a comunicação com a base de dados remota e devolve as informacoes de encomendas.
 */
public class EncomendaDataHandler extends DBHandler {
    /**
     * Logger da classe atual
     */
    private static final Logger logger =Logger.getLogger ( EncomendaDataHandler.class.getName () );

    /**
     * Construtor da classe atual
     * @throws IOException
     */
    public EncomendaDataHandler() throws IOException {
        super();
    }

    /**
     * Método que devolve as encomendas a ser entregue
     * @param idFarmacia id das farmacia que se pretende saber as encomendas
     * @return lista de encomendas da farmacia por entregar
     */
    //<editor-fold default-state="collapsed" name="UC 19">
    public Map<Integer,Encomenda> getMapEncomendasPorEntregar(int idFarmacia) {
        //id -> encomenda
        Map<Integer, Encomenda> map=new HashMap<> ();

        String query = "select e.id_encomenda, e.nif_cliente,p.nome, p.peso, pe.quantidade, c.ID_CLIENTE," +
                "en.RUA, en.NUM_PORTA, en.COD_POSTAL, en.LOCALIDADE, en.PAIS, en.LATITUDE, en.LONGITUDE, en.ALTITUDE, s.email \n" +
                "        from produto_encomenda pe\n" +
                "                 inner join encomenda e on pe.id_encomenda = e.id_encomenda\n" +
                "                 inner join produto p on p.id_produto = pe.id_produto\n" +
                "                 inner join cliente c on e.ID_CLIENTE = c.ID_CLIENTE" +
                "                 inner join endereco en on c.ID_ENDERECO = en.ID_ENDERECO" +
                "                 inner join utilizador s on s.id_user = c.ID_CLIENTE" +
                "        where e.id_estado=2 and e.id_farmacia=?";

        try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, idFarmacia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int nif = rs.getInt(2);
                    String nome = rs.getString(3);
                    double peso = rs.getDouble(4);
                    int qtd = rs.getInt(5);
                    int idCliente = rs.getInt(6);
                    String rua = rs.getString(7);
                    String numPorta = rs.getString(8);
                    String codPostal = rs.getString(9);
                    String localidade = rs.getString(10);
                    String pais = rs.getString(11);
                    double latitude = rs.getDouble(12);
                    double longitude = rs.getDouble(13);
                    double altitude = rs.getDouble(14);
                    String email = rs.getString(15);
                    Cliente c = new Cliente(idCliente, new Endereco(rua, numPorta, codPostal, localidade, pais, latitude, longitude, altitude));
                    c.setEmail(email);
                    map.putIfAbsent(id, new Encomenda(id, nif,c ));
                    map.get(id).addProduto(new Produto(nome, peso), qtd);
                }
            }
        }catch(SQLException e){
            logger.severe ( e.getMessage () );
            e.printStackTrace ();
            return new LinkedHashMap<> (); //devolver map vazio
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage () );
                e.printStackTrace ();
            }
        }
        return map;
    }

    /**
     * Método que muda o estado de encomenda quando se entrega esta ao cliente.
     * @param l Lista de encomendas a mudar o estado
     * @return True se foi possivel mudar com sucesso.
     */
    public boolean setEstadoEncomendasParaAEntregar(List<Encomenda> l) {
        String sql="UPDATE encomenda SET id_estado= ? where id_encomenda= ?";
        try(Connection con = getConnection (); PreparedStatement ps=con.prepareStatement(sql)){
            for (Encomenda e : l) {
                ps.setInt ( 1,3 ); //-> encomenda no estado de a ser entregue
                ps.setInt ( 2,e.getId () );
                ps.executeUpdate ();
            }
            return true;
        }catch (SQLException e){
            logger.severe ( e.getMessage () );
            e.printStackTrace ();
            return false;
        }finally{
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage () );
                e.printStackTrace ();
            }
        }
    }

    //</editor-fold>

    /**
     * Obter a lista de clientes através das encomendas
     * @param encs Lista de encomendas
     * @return Lista de clientes
     */
    public Map<Endereco,Encomenda> enderecosEncomendas(List<Encomenda> encs) {
        Map<Endereco,Encomenda> mappedEncsEndrs=new LinkedHashMap<> ();
        Endereco end;
        for (Encomenda enc : encs) {
            end = obterEnderecoCliente (  enc.getNifEncomenda()); //retorna null se der erro
            if(end!=null){
                mappedEncsEndrs.put ( end,enc );
            }
        }
        return mappedEncsEndrs;
    }

    /**
     * Devolver um objeto de cliente com nome e morada a partir do seu nif
     * @param nif Nif do cliente
     * @return Cliente respetivo ao nif
     */
    public Endereco obterEnderecoCliente(int nif) {
        String sql="select e.* from cliente c " +
                "inner join endereco e on c.id_endereco = e.id_endereco " +
                "where c.nif=?";
        try(Connection con=getConnection (); PreparedStatement ps=con.prepareStatement ( sql )){
            ps.setInt ( 1,nif );
            try(ResultSet rs=ps.executeQuery ()){
                return EnderecoDataHandler.criarEndereco ( rs,false );
            }
        }catch (SQLException e){
            logger.severe ( e.getMessage () );
            e.printStackTrace ();
            return null;
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
     * Método que devolve um mapa com o id da encomenda na key e o produto no seu value.
     * @param idEncomenda Produtos da encomenda
     * @return mapa com o id da encomenda na key e o produto no seu value.
     */
    public Map<Integer,Produto> getProdutosEncomenda(int idEncomenda) {
        Map<Integer, Produto> map = new HashMap<>();
        String sql = "select p.id_produto, e.nif_cliente,p.nome, p.preco, pe.quantidade " +
                "from produto_encomenda pe " +
                "inner join encomenda e on pe.id_encomenda = e.id_encomenda " +
                "inner join produto p on p.id_produto = pe.id_produto " +
                "where e.id_encomenda=?";
        try(Connection con=getConnection (); PreparedStatement ps = con.prepareStatement (sql)) {
            ps.setInt ( 1,idEncomenda );
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idProd = rs.getInt(1);
                    int nif = rs.getInt(2);
                    String nome = rs.getString(3);
                    double preco = rs.getDouble(4);
                    int qtd = rs.getInt(5);

                    map.put(idProd, new Produto(preco, nome));
                }
            }
        } catch (SQLException e) {
            logger.severe ( e.getMessage() );
        } finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( e.getMessage ());
            }
        }
        return map;
    }

    /**
     * Devolve um encomenda
     * @return encomenda ou null se ocorrer um erro ao aceder à DB
     */
    public Encomenda getEncomenda(int idEncomenda) {
        Encomenda encomenda;
            String sql = "SELECT e.id_encomenda,e.nif_cliente,es.estado FROM encomenda e " +
                    "INNER JOIN estado_encomenda es on " +
                    "e.id_estado = es.id_estado WHERE id_encomenda =?";
            try(PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setInt(1, idEncomenda);
                try(ResultSet rs = stmt.executeQuery()) {
                    encomenda = new Encomenda(rs.getInt(1), rs.getInt(2),
                            Encomenda.EstadoEncomenda.valueOf(rs.getString(3).toUpperCase(Locale.ROOT)));
                }
            return encomenda;
        } catch (SQLException e) {
            return null;
        }finally {
                try {
                    closeAll();
                } catch (SQLException throwables) {
                    logger.severe(throwables.getMessage());
                }
            }
    }

    /**
     * Método que  guarda na base de dados a encomenda
     * @param encomendaEnviar encomenda a ser guardada
     * @return true se for generada
     */
    public boolean gerarEncomenda(Encomenda encomendaEnviar) {

            try(CallableStatement callStmt = getConnection().prepareCall("{ ? = call criar_encomenda(?,?,?,?,?)  }")) {

                callStmt.registerOutParameter(1, OracleTypes.INTEGER);

                callStmt.setInt(2, encomendaEnviar.getNifEncomenda());
                callStmt.setInt(3, encomendaEnviar.getFarmaciaEncomenda().getId());
                callStmt.setInt(4, encomendaEnviar.getCliente().getIdUser());
                callStmt.setString(5, encomendaEnviar.getEstadoEncomenda().getDescricao());
                callStmt.setDouble(6,encomendaEnviar.getPeso());
                callStmt.execute();
                int idGerado = callStmt.getInt(1);
                if(idGerado==-1)return false;
                for (Map.Entry<Produto, Integer> p : encomendaEnviar.getProdutosEncomendados().entrySet()) {
                    int quantidade = p.getValue();
                    criarProdutoEncomenda(p.getKey(), idGerado, quantidade);
                }
                criarFatura(encomendaEnviar.getNifEncomenda(), encomendaEnviar.getCusto(), idGerado);
                return true;
        }catch (SQLException e){
            logger.severe(e.getMessage());
            return false;
        }finally {
                try {
                    closeAll();
                } catch (SQLException throwables) {
                    logger.severe(throwables.getMessage());
                }
            }
    }

    /**
     * Método que guarda a fatura na base de dados
     * @param nif Nif associado a fatura
     * @param custoTotal Custo total da fatura
     * @param idEncomenda id da encomenda
     */
    private void criarFatura(int nif, double custoTotal, int idEncomenda) {

            try(CallableStatement callSmt = getConnection().prepareCall(" { call  insert_fatura(?,?,?)}")) {

                callSmt.setInt(1, nif);
                callSmt.setDouble(2, custoTotal);
                callSmt.setInt(3, idEncomenda);

                callSmt.execute();
        }catch (SQLException e){
            logger.severe(e.getMessage());
        }finally {
                try {
                    closeAll();
                } catch (SQLException e) {
                    logger.severe(e.getMessage());
                }
            }
    }

    /**
     * Método que guarda os produtos comprados de uma encomenda.
     * @param produto produto comprado
     * @param idEncomenda id Encomenda
     * @param quantidade Quantidade comprada desse produto
     */
    private void criarProdutoEncomenda(Produto produto,int idEncomenda, int quantidade ){

            try(CallableStatement callSmt = getConnection().prepareCall("{ call insert_produto_encomenda(?,?,?) }")) {


                callSmt.setInt(1, produto.getId());
                callSmt.setInt(2, idEncomenda);
                callSmt.setInt(3, quantidade);

                callSmt.execute();


        }catch (SQLException e){
                logger.severe(e.getMessage());
        }finally {
                try {
                    closeAll();
                } catch (SQLException throwables) {
                    logger.severe(throwables.getMessage());
                }
            }

    }

    /**
     * Método que devolve todas encomendas do sistema.
     * @return Lista com todas as encomendas do sistema
     */
    public List<Encomenda> getTodasEncomendas() {
        List<Encomenda> encomendas = new ArrayList<>();
        try(Statement st = getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT id_encomenda, nif_cliente FROM ENCOMENDA")) {
                while (rs.next()) {
                    encomendas.add(new Encomenda(rs.getInt("id_encomenda"),rs.getInt("nif_cliente")));
                }
            }

            if (encomendas.size() == 0)
                return null;
            return encomendas;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            return null;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( "SQL EXCEPTION");
            }
        }
    }
}
