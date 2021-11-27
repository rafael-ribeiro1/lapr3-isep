package lapr.project.data;

import lapr.project.model.Produto;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Classe responsável pela operações com a base de dados que envolvam os Produtos do sistema
 */
public class ProdutoHandler extends DBHandler {
    /**
     * Logger da classe
     */
    private static Logger logger =  Logger.getLogger(ProdutoHandler.class.getName());
    /**
     * Mensagem a ser mostrada quando ocorre um erro
     */
    public static final String MSG = "Erro ao fechar as ligações.";

    /**
     * Construtor da classe atual.
     * @throws IOException
     */
    public ProdutoHandler() throws IOException {
        //constructor para apenas lancar a excecao
    }

    /**
     * Regista um novo produto na BD
     * @param produto produto a ser registado
     * @return true se for adicionado, falso caso ocorra erro
     */
    public int addProduto(Produto produto) {
        int id=-1;

            try (CallableStatement callStmt = getConnection().prepareCall("{? = call insert_produto(?,?,?,?,?,?) }")) {
                callStmt.setInt(1, id);
                callStmt.registerOutParameter(2, OracleTypes.INTEGER);
                callStmt.setString(3, produto.getNome());
                callStmt.setDouble(4, produto.getValorUnitario());
                callStmt.setDouble(5, produto.getPreco());
                callStmt.setDouble(6, produto.getPeso());
                callStmt.setString(7, produto.getDescricao());

                callStmt.execute();
                id = callStmt.getInt(2);
                produto.setId(id);



            return id;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return id;
        }finally {
        try{
            closeAll ();
        }catch (SQLException e){
            logger.severe ( MSG);
        }
    }
    }



    /**
     * Devolve uma lista de produtos com o seu id e nome
     * @return ArrayList de produtos ou null se ocorrer um erro ao aceder à DB
     */
    public List<Produto> getListaProdutos() {
            List<Produto> produtos = new ArrayList<>();
            try (ResultSet rs = getConnection().createStatement().executeQuery("SELECT id_produto, preco, nome FROM produto")) {
                while (rs.next()) {
                    produtos.add(new Produto(rs.getInt(1), rs.getString(3),rs.getDouble(2)));
                }
            return produtos;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return new ArrayList<>();
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     * Devolve um produto
     * @return produto ou null se ocorrer um erro ao aceder à DB
     */
    public Produto getProduto(int idProduto) {


            Produto produto = null;
            try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM produto WHERE id_produto = ?")) {
                stmt.setInt(1, idProduto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        produto = new Produto(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4),
                                rs.getDouble(5), rs.getString(6));
                    }
                }

            return produto;
        } catch (SQLException e) {
                logger.severe(e.getMessage());
            return produto;
        }finally {
            try{
                closeAll ();
            }catch (SQLException e){
                logger.severe ( MSG );
            }
        }
    }

    /**
     * Atualiza um produto
     * @return true ou null se ocorrer um erro ao aceder à DB
     */
    public Boolean updateProduto(String[] info,Produto prod){
        if (info.length != 5) return false;
        if ( info[0] != null)
            prod.setNome(info[0]);

        if (info[1] != null) {
            prod.setValorUnitario(Double.parseDouble(info[1]));
            if ( Double.parseDouble(info[1])<0)
                return false;
        }

        if (info[2] != null) {
            prod.setPreco(Double.parseDouble(info[2]));
            if ( Double.parseDouble(info[2])<0)
                return false;
        }
        if (info[3] != null) {
            prod.setPeso(Double.parseDouble(info[3]));
            if ( Double.parseDouble(info[3])<0)
                return false;
        }
        if (info[4] != null)
            prod.setDescricao(info[4]);

            String sql = "UPDATE produto SET nome =? , valor_unitario =? , preco = ? , peso=?,descricao=? WHERE id_produto=?";
            try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
                ps.setInt(6,prod.getId());
                ps.setString(1,prod.getNome());
                ps.setDouble( 2,prod.getPreco());
                ps.setDouble(3, prod.getValorUnitario());
                ps.setDouble(4, prod.getPeso());
                ps.setString(5, prod.getDescricao());
                ps.executeUpdate();
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

}

