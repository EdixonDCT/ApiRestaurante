// Define el paquete al que pertenece la clase.
package DAO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import MODELO.Mesa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Clase para la persistencia de datos (DAO) de la entidad Mesa.
 * Se encarga de las operaciones de la base de datos (CRUD) para la tabla 'mesas'.
 */
public class MesaDAO {

    // Variables a nivel de clase para gestionar la conexión, la sentencia y los resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Obtiene una lista de todas las mesas de la base de datos.
     *
     * @return Una lista de objetos Mesa.
     */
    public ArrayList<Mesa> listarTodos() {
        ArrayList<Mesa> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();

            // Consulta SQL para seleccionar todos los registros de la tabla 'mesas'.
            String sql = "SELECT * FROM mesas";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene los resultados.
            rs = prepStmt.executeQuery();

            // Recorre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto Mesa para cada fila.
                Mesa mesa = new Mesa();
                // Asigna los valores de las columnas del resultado al objeto Mesa.
                mesa.setNumero(rs.getString("numero"));
                mesa.setCapacidad(rs.getString("capacidad"));
                mesa.setDisponible(rs.getString("disponible"));
                // Agrega el objeto Mesa a la lista.
                lista.add(mesa);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR MESAS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos (ResultSet, PreparedStatement, Connection) en el bloque finally
            // para asegurar que se liberen, incluso si ocurre una excepción.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return lista;
    }

    /**
     * Obtiene una mesa específica por su número.
     *
     * @param numero El número de la mesa a buscar.
     * @return El objeto Mesa correspondiente o null si no se encuentra.
     */
    public Mesa obtenerPorNumero(String numero) {
        Mesa mesa = null;
        try {
            conn = DBConnection.getConnection();
            // Consulta SQL que busca por el número de la mesa.
            String sql = "SELECT * FROM mesas WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            // Asigna el número como parámetro, convirtiéndolo a entero.
            prepStmt.setInt(1, Integer.parseInt(numero));
            rs = prepStmt.executeQuery();

            // Si se encuentra un resultado, lo mapea al objeto Mesa.
            if (rs.next()) {
                mesa = new Mesa();
                mesa.setNumero(rs.getString("numero"));
                mesa.setCapacidad(rs.getString("capacidad"));
                mesa.setDisponible(rs.getString("disponible"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER MESA POR NUMERO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return mesa;
    }

    /**
     * Crea una nueva mesa en la base de datos.
     *
     * @param mesa El objeto Mesa con los datos a insertar.
     * @return true si la creación fue exitosa, false en caso contrario.
     */
    public boolean crear(Mesa mesa) {
        boolean creado = false;
        try {
            conn = DBConnection.getConnection();
            // Consulta SQL para insertar una nueva mesa.
            String sql = "INSERT INTO mesas (numero, capacidad) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            // Asigna los parámetros del objeto Mesa.
            prepStmt.setInt(1, Integer.parseInt(mesa.getNumero()));
            prepStmt.setInt(2, Integer.parseInt(mesa.getCapacidad()));

            // Ejecuta la inserción.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se insertó al menos una fila.
            creado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR MESA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return creado;
    }

    /**
     * Actualiza la capacidad de una mesa existente.
     *
     * @param mesa El objeto Mesa con la nueva capacidad y el número de la mesa a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar(Mesa mesa) {
        boolean actualizado = false;
        try {
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar la capacidad.
            String sql = "UPDATE mesas SET capacidad = ? WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            // Asigna los parámetros.
            prepStmt.setInt(1, Integer.parseInt(mesa.getCapacidad()));
            prepStmt.setInt(2, Integer.parseInt(mesa.getNumero()));
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se actualizó al menos una fila.
            actualizado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR MESA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return actualizado;
    }

    /**
     * Elimina una mesa de la base de datos por su número.
     *
     * @param numero El número de la mesa a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminar(String numero) {
        boolean eliminado = false;
        try {
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar la mesa.
            String sql = "DELETE FROM mesas WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            // Asigna el número de la mesa como parámetro.
            prepStmt.setInt(1, Integer.parseInt(numero));

            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR MESA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return eliminado;
    }

    /**
     * Actualiza el estado de disponibilidad de una mesa.
     *
     * @param mesa El objeto Mesa con el nuevo estado y el número de la mesa.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarEstado(Mesa mesa) {
        boolean actualizado = false;
        try {
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar el estado de disponibilidad.
            String sql = "UPDATE mesas SET disponible = ? WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            // Asigna los parámetros.
            prepStmt.setInt(1, Integer.parseInt(mesa.getDisponible()));
            prepStmt.setInt(2, Integer.parseInt(mesa.getNumero()));
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se actualizó al menos una fila.
            actualizado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD MESA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return actualizado;
    }
}
