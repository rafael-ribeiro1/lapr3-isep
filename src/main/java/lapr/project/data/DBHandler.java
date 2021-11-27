package lapr.project.data;

import oracle.jdbc.pool.OracleDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Super classe que realiza a conexão com a base de dados Oracle, com dados de acesso no ficheiro application.properties
 * Esta classe deve ser extended pelas classes que vão realizar operações na base de dados
 */
public class DBHandler {

    /**
     * URL da DB
     */
    private String jdbcUrl;
    /**
     * Username para acesso à DB
     */
    private String username;
    /**
     * Password do utilizador
     */
    private String password;
    /**
     * Indicador de autocommit
     */
    private boolean autocommit;

    /**
     * Ligação à DB
     */
    private static Connection connection;
    /**
     * Invocação de "stored procedures"
     */
    private CallableStatement callStmt;
    /**
     * Resultados retornados em "stores procedures"
     */
    private ResultSet rSet;

    /**
     * Obtém os dados de acesso à DB através do ficheiro application.properties
     * @throws IOException lançada caso ocorra erro ao ler o ficheiro
     */
    public DBHandler() throws IOException {
        Properties appProps = new Properties();
        String propFileName = "application.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null)
            appProps.load(inputStream);
        else
            throw new FileNotFoundException("Application property file not found in classpath");
        inputStream.close();

        jdbcUrl = appProps.getProperty("db.host");
        username = appProps.getProperty("db.username");
        password = appProps.getProperty("db.password");
        String strAutocommit = appProps.getProperty("db.autocommit");
        autocommit = strAutocommit.equals("true");
        connection = null;
        callStmt = null;
        rSet = null;
    }

    /**
     * Estabelece ligação à DB
     * @throws SQLException lançada caso ocorra um erro ao conectar à DB
     */
    public void openConnection() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(jdbcUrl);
        connection = dataSource.getConnection(username, password);
        connection.setAutoCommit(autocommit);
    }

    /**
     * Retorna a conexão à DB. Estabelece ligação se ainda não tiver sido feita
     * @return ligação à base de dados
     * @throws SQLException lançada caso ocorra algum erro ao ligar à DB
     */
    public Connection getConnection() throws SQLException {
        if (connection == null)
            openConnection();
        return connection;
    }

    /**
     * Fecha as instâncias de CallableStatement e ResultSet e também a ligação à DB
     * @throws SQLException lançada caso ocorra algum erro ao fechar as ligações
     */
    public void closeAll() throws SQLException {
        if (rSet != null) {
            rSet.close();
            rSet = null;
        }
        if (callStmt != null) {
            callStmt.close();
            callStmt = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Indica se o autocommit está ativado
     * @return indicador do autocommit
     */
    protected boolean isAutocommitAct() {
        return autocommit;
    }

}
