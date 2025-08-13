// Define el paquete al que pertenece la clase.
package MODELO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos de la relación entre Ingrediente y Coctel.
// Gestiona las operaciones CRUD para la tabla 'ingredientes_coctel'.
public class IngredientesCoctelDAO {

    // Variables a nivel de clase para gestionar la conexión, la sentencia y los resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Obtiene una lista de todas las relaciones entre ingredientes y cócteles.
     * Utiliza JOINs para obtener los nombres de los ingredientes y cócteles
     * junto con los IDs, para una visualización más útil.
     * @return Una lista de objetos IngredientesCoctel.
     */
    public ArrayList<IngredientesCoctel> listarTodos() {
        ArrayList<IngredientesCoctel> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();
            // Consulta SQL que une las tablas para obtener toda la información necesaria.
            String sql = "SELECT ic.id, ic.id_ingrediente, i.nombre AS nombre_ingrediente, " +
                         "ic.id_coctel, c.nombre AS nombre_coctel " +
                         "FROM ingredientes_coctel AS ic " +
                         "INNER JOIN cocteles c ON ic.id_coctel = c.id " +
                         "INNER JOIN ingredientes i ON ic.id_ingrediente = i.id";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene los resultados.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto y asigna los valores del resultado.
                IngredientesCoctel ingCoc = new IngredientesCoctel();
                ingCoc.setId(rs.getString("id"));
                ingCoc.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCoc.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCoc.setIdCoctel(rs.getString("id_coctel"));
                ingCoc.setNombreCoctel(rs.getString("nombre_coctel"));
                lista.add(ingCoc);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR INGREDIENTES_COCTEL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra todos los recursos en el bloque finally para evitar fugas.
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
     * Obtiene una relación específica entre ingrediente y cóctel por su ID.
     * @param id El ID de la relación en la tabla 'ingredientes_coctel'.
     * @return El objeto IngredientesCoctel correspondiente o null si no se encuentra.
     */
    public IngredientesCoctel obtenerPorId(String id) {
        IngredientesCoctel ingCoc = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL con el JOIN para obtener los nombres.
            String sql = "SELECT ic.id,ic.id_ingrediente, i.nombre AS nombre_ingrediente, " +
                         "ic.id_coctel, c.nombre AS nombre_coctel " +
                         "FROM ingredientes_coctel AS ic " +
                         "INNER JOIN cocteles c ON ic.id_coctel = c.id " +
                         "INNER JOIN ingredientes i ON ic.id_ingrediente = i.id " +
                         "WHERE ic.id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID como parámetro, convirtiéndolo a entero.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si se encuentra un resultado, lo mapea al objeto.
            if (rs.next()) {
                ingCoc = new IngredientesCoctel();
                ingCoc.setId(rs.getString("id"));
                ingCoc.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCoc.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCoc.setIdCoctel(rs.getString("id_coctel"));
                ingCoc.setNombreCoctel(rs.getString("nombre_coctel"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COCTEL POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return ingCoc;
    }

    /**
     * Obtiene todos los ingredientes asociados a un cóctel específico.
     * @param id El ID del cóctel.
     * @return Una lista de objetos IngredientesCoctel.
     */
    public ArrayList<IngredientesCoctel> obtenerPorCoctel(String id) {
        ArrayList<IngredientesCoctel> lista = new ArrayList<>();

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL que filtra por el ID del cóctel.
            String sql = "SELECT ic.id,ic.id_ingrediente, i.nombre AS nombre_ingrediente, " +
                         "ic.id_coctel, c.nombre AS nombre_coctel " +
                         "FROM ingredientes_coctel AS ic " +
                         "INNER JOIN cocteles c ON ic.id_coctel = c.id " +
                         "INNER JOIN ingredientes i ON ic.id_ingrediente = i.id " +
                         "WHERE ic.id_coctel = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID del cóctel como parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Itera sobre los resultados y los agrega a la lista.
            while (rs.next()) {
                IngredientesCoctel ingCoc = new IngredientesCoctel();
                ingCoc.setId(rs.getString("id"));
                ingCoc.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCoc.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCoc.setIdCoctel(rs.getString("id_coctel"));
                ingCoc.setNombreCoctel(rs.getString("nombre_coctel"));
                lista.add(ingCoc);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COCTEL POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return lista;
    }

    /**
     * Crea una nueva relación entre un ingrediente y un cóctel en la base de datos.
     * @param ingCoc El objeto IngredientesCoctel con los IDs de ingrediente y cóctel.
     * @return true si la creación fue exitosa, false en caso contrario.
     */
    public boolean crear(IngredientesCoctel ingCoc) {
        boolean creado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para la inserción.
            String sql = "INSERT INTO ingredientes_coctel (id_ingrediente, id_coctel) VALUES (?, ?)";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los IDs de ingrediente y cóctel como parámetros.
            prepStmt.setInt(1, Integer.parseInt(ingCoc.getIdIngrediente()));
            prepStmt.setInt(2, Integer.parseInt(ingCoc.getIdCoctel()));

            // Ejecuta la inserción.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se insertó al menos una fila.
            creado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR INGREDIENTES_COCTEL: " + e.getMessage());
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

        return creado;
    }

    /**
     * Elimina todas las relaciones de un cóctel específico.
     * @param id El ID del cóctel cuyos ingredientes se desean eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar por el ID del cóctel.
            String sql = "DELETE FROM ingredientes_coctel WHERE id_coctel = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID del cóctel como parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));

            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR INGREDIENTES_COCTEL: " + e.getMessage());
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
}
