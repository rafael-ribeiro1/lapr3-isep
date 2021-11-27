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
 * Classe responsável pela operações com a base de dados que envolvam os Produtos de uma farmacia do sistema
 */
public class ProdutosFarmaciaHandler extends DBHandler {
    /**
     * Logger da classe atual
     */
    private static Logger logger=Logger.getLogger(ProdutosFarmaciaHandler.class.getName());
    /**
     * Mensagem a ser demonstrada quando ocorre um erro.
     */
    public static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Construtor da classe
     * @throws IOException
     */
    public ProdutosFarmaciaHandler() throws IOException {
        // constructor para apenas lancar a excecao
    }

    /**
     * Método que retorna numa lista todos os produtos que uma certa farmacia tem
     * @param farmacia
     * @return lista de produtos ou entao null caso exista um erro na BD
     */
    public List<Produto> getProdutosFarmacia(Farmacia farmacia){
            List<Produto> produtosFarmacia = new ArrayList<>();
            try (CallableStatement callStmt = getConnection().prepareCall("{? = call get_produtos_farmacia(?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt.setInt(2,farmacia.getId());

                callStmt.execute();

                try (ResultSet rSet = (ResultSet) callStmt.getObject(1)) {
                    while (rSet.next()){
                        Produto produto = new Produto(rSet.getInt(1),rSet.getString(2),rSet.getDouble(3),rSet.getDouble(4),rSet.getDouble(5),rSet.getString(6));
                        produtosFarmacia.add(produto);
                    }
                }

            return produtosFarmacia;
        }catch (SQLException e){
                logger.severe(e.getMessage());
            return produtosFarmacia;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }

    }

    /**
     * Método que vê se uma farmacia tem stock suficiente de um certo produto
     * @param produto
     * @param quantidade
     * @param farmacia
     * @return true or false caso exista algum erro na BD
     */
    public boolean verificarStockProduto(Produto produto, int quantidade, Farmacia farmacia) {


            try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call verificar_stock(?,?,?) }")) {
                callStmt.registerOutParameter(1, OracleTypes.INTEGER);

                callStmt.setInt(2,produto.getId());
                callStmt.setInt(3,quantidade);
                callStmt.setInt(4,farmacia.getId());

                callStmt.execute();

                int resultado = callStmt.getInt(1);
                if(resultado==1) return true;

            return false;
        }catch (SQLException e){
                logger.severe(e.getMessage());
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }

    /**
     * Método que adiciona um produto a uma farmacia dado o produto, a farmacia e uma quantidade
     * @param idProduto
     * @param idFarmacia
     * @param quantidade
     * @return true ou false caso exista algum erro na BD
     */
    public boolean addProdutoFarmacia(int idProduto, int idFarmacia,int quantidade) {

            String sql= "INSERT INTO produto_farmacia(id_produto,id_farmacia,stock) VALUES (?,?,?)";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setInt(1,idProduto);
                stmt.setInt(2, idFarmacia);
                stmt.setInt(3, quantidade);

                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }

    /**
     * Método que dado uma farmcia e um produto este produto será removido da farmacia
     * @param idProduto
     * @param idFarmacia
     * @return true ou false caso existir um eror na BD
     */
    public boolean removerProdutoFarmacia(int idProduto, int idFarmacia) {
            String sql= "DELETE FROM produto_farmacia WHERE id_produto = ? AND id_farmacia = ?";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setInt(1,idProduto);
                stmt.setInt(2, idFarmacia);
                stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
          return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }

    /**
     * Método que remove stock de uma farmacia dando um produto e uma quantidade
     * @param farmacia
     * @param produto
     * @param quantidade
     * @return true ou false caso correu algo de mal na BD
     */
    public boolean removerStock(Farmacia farmacia, Produto produto, int quantidade) {


            try (CallableStatement callStmt = getConnection().prepareCall("{ call remover_stock_farmacia(?,?,?)  }")) {
                callStmt.setInt(1, farmacia.getId());
                callStmt.setInt(2, produto.getId());
                callStmt.setInt(3, quantidade);
                callStmt.execute();
            return true;
        }catch (SQLException e){
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
     * Atualiza um stock de um produto
     * @return true ou null se ocorrer um erro ao aceder à DB
     */
    public Boolean updateStock(int idProduto,int idFarmacia,int stock){
            String sql = "UPDATE produto_farmacia SET stock =?  WHERE id_produto=? and id_farmacia=?";
            try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
                ps.setInt(1,stock);
                ps.setInt( 2,idProduto);
                ps.setInt( 3,idFarmacia);

                ps.executeUpdate();
            return true;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return false;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG);
            }
        }
    }

    /**
     * Método uma lista de farmácias onde tem um produto com uma certa quantidade
     * @param produtoDesejado
     * @param quantidade
     * @return lista de farmacias ou null caso não exista ou correu algo mal
     */
    public List<Farmacia> getFarmaciasComStock (Produto produtoDesejado , int quantidade){
        List<Farmacia> listaFarmaciasComStock = new ArrayList<>();

            try(CallableStatement callStmt = getConnection().prepareCall("{? = call get_farmacias_com_stock(?,?) }")){

                callStmt.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt.setInt(2,produtoDesejado.getId());
                callStmt.setInt(3,quantidade);

                callStmt.execute();

                ResultSet rSet = (ResultSet) callStmt.getObject(1);

                if(rSet.next()){
                    Endereco endereco = new Endereco(rSet.getString(4),rSet.getString(5),rSet.getString(6),rSet.getString(7),rSet.getString(8),rSet.getDouble(9),rSet.getDouble(10),rSet.getDouble(11),true);
                    Farmacia farmacia = new Farmacia(rSet.getInt(1),rSet.getString(2),endereco);
                    listaFarmaciasComStock.add(farmacia);
                }
                rSet.close();

        }catch (SQLException e){
                logger.severe(e.getMessage());
            return new ArrayList<>();
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
        return listaFarmaciasComStock;
    }

    /**
     * Método que vai fazer uma trasnferência entre 2 farmacias onde vai ser aumentado o stock de um produto na
     * recebedora e diminuir o stock na que fornece
     * @param farmaciaReceber
     * @param farmaciaFornecedora
     * @param produtoDesejado
     * @param quantidade
     * @param idTransporte
     * @return true ou false se correu algo mal
     */
    public boolean realizarTransferenciaProduto(Farmacia farmaciaReceber , Farmacia farmaciaFornecedora , Produto produtoDesejado , int quantidade , int idTransporte){
            try(CallableStatement calltStmt = getConnection().prepareCall("{call realizar_transferencia_produto(?,?,?,?,?,?)}")){
                calltStmt.setInt(1,farmaciaReceber.getId());
                calltStmt.setInt(2,farmaciaFornecedora.getId());
                calltStmt.setInt(3,produtoDesejado.getId());
                calltStmt.setInt(4,quantidade);
                calltStmt.setInt(5,idTransporte);
                calltStmt.setString(6,"Entregue");

                calltStmt.execute();

            closeAll();
            return true;

        }catch (SQLException e){
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
