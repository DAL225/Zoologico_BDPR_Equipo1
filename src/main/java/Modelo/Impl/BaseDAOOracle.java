package Modelo.Impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que define los metodos de conexion a la BD.
 *
 * @author amiss
 */
public abstract class BaseDAOOracle {

    // Configuración de la BD
    private String URL;
    private String USER;
    private String PASS;
    private final String DRIVER = "oracle.jdbc.OracleDriver";

    // Conexión reutilizable
    protected Connection connection;

    /**
     * Constructor de la clase.
     *
     * @throws Exception si ocurre alguna excepcion al implementarlo.
     */
    public BaseDAOOracle() throws Exception {
        cargarConfiguracion();
        conectar();
    }

    /**
     * Carga la configuración desde db.properties.
     *
     * @throws Exception si ocurre un error al cargar el archivo
     */
    private void cargarConfiguracion() throws Exception {

        Properties props = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new Exception("No se encontró el archivo db.properties");
            }

            props.load(input);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");

        } catch (Exception e) {
            throw new Exception("Error al cargar db.properties", e);
        }
    }

    /**
     * Método para conectar (combinado).
     *
     * @throws Exception si ocurre alguna excepcion al intentar conectar
     */
    protected void conectar() throws Exception {
        try {
            // Cargar driver
            Class.forName(DRIVER);

            // Validar conexión (lo mejor de BaseSQL)
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Conexión establecida correctamente");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver: " + e.getMessage());
            throw new Exception("Error de acceso a la BD, intente mas tarde");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            throw new Exception("Error de acceso a la BD, intente mas tarde");
        }
    }

    /**
     * Obtiene la conexión activa a la base de datos. Si la conexión no existe o
     * está cerrada, se crea nuevamente.
     *
     * @return conexión activa a la base de datos
     *
     * @throws SQLException si ocurre un error al verificar el estado de la
     * conexión
     * @throws Exception si ocurre un error al establecer la conexión
     */
    protected Connection getConexion() throws SQLException, Exception {
        if (connection == null || connection.isClosed()) {
            conectar();
        }
        return connection;
    }

}