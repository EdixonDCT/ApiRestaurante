// Define el paquete al que pertenece la clase.
package MODELO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos de la relación entre Ingrediente y Comida.
// Gestiona las operaciones CRUD para la tabla 'ingredientes_comida'.
public class IngredientesComidaDAO {

    // Variables a nivel de clase para gestionar la conexión, la sentencia y los resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Obtiene una lista de todas las relaciones entre ingredientes y comidas.
     * Utiliza JOINs para obtener los nombres de los ingredientes y comidas
     * junto con los IDs, para una visualización más útil.
     * @return Una lista de objetos IngredientesComida.
     */
    public ArrayList<IngredientesComida> listarTodos() {
        ArrayList<IngredientesComida> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();
            // Consulta SQL que une las tablas para obtener toda la información necesaria.
            String sql = "SELECT ic.id, ic.id_ingrediente, i.nombre AS nombre_ingrediente, " +
                         "ic.id_comida, c.nombre AS nombre_comida " +
                         "FROM ingredientes_comida AS ic " +
                         "INNER JOIN comidas c ON ic.id_comida = c.id " +
                         "INNER JOIN ingredientes i ON ic.id_ingrediente = i.id";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene los resultados.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto y asigna los valores del resultado.
                IngredientesComida ingCom = new IngredientesComida();
                ingCom.setId(rs.getString("id"));
                ingCom.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCom.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCom.setIdComida(rs.getString("id_comida"));
                ingCom.setNombreComida(rs.getString("nombre_comida"));
                lista.add(ingCom);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR INGREDIENTES_COMIDA: " + e.getMessage());
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
     * Obtiene una relación específica entre ingrediente y comida por su ID.
     * @param id El ID de la relación en la tabla 'ingredientes_comida'.
     * @return El objeto IngredientesComida correspondiente o null si no se encuentra.
     */
    public IngredientesComida obtenerPorId(String id) {
        IngredientesComida ingCom = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL con el JOIN para obtener los nombres.
            String sql = "SELECT ic.id, ic.id_ingrediente, i.nombre AS nombre_ingrediente, " +
                         "ic.id_comida, c.nombre AS nombre_comida " +
                         "FROM ingredientes_comida AS ic " +
                         "INNER JOIN comidas c ON ic.id_comida = c.id " +
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
                ingCom = new IngredientesComida();
                ingCom.setId(rs.getString("id"));
                ingCom.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCom.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCom.setIdComida(rs.getString("id_comida"));
                ingCom.setNombreComida(rs.getString("nombre_comida"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COMIDA POR ID: " + e.getMessage());
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

        return ingCom;
    }

    /**
     * Obtiene todos los ingredientes asociados a una comida específica.
     * @param id El ID de la comida.
     * @return Una lista de objetos IngredientesComida.
     */
    public ArrayList<IngredientesComida> obtenerPorComida(String id) {
        ArrayList<IngredientesComida> lista = new ArrayList<>();

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL que filtra por el ID de la comida.
            String sql = "SELECT ic.id, ic.id_ingrediente, i.nombre AS nombre_ingrediente, " +
                         "ic.id_comida, c.nombre AS nombre_comida " +
                         "FROM ingredientes_comida AS ic " +
                         "INNER JOIN comidas c ON ic.id_comida = c.id " +
                         "INNER JOIN ingredientes i ON ic.id_ingrediente = i.id " +
                         "WHERE ic.id_comida = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID de la comida como parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Itera sobre los resultados y los agrega a la lista.
            while (rs.next()) {
                IngredientesComida ingCom = new IngredientesComida();
                ingCom.setId(rs.getString("id"));
                ingCom.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCom.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCom.setIdComida(rs.getString("id_comida"));
                ingCom.setNombreComida(rs.getString("nombre_comida"));
                lista.add(ingCom);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COMIDA POR ID: " + e.getMessage());
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
     * Crea una nueva relación entre un ingrediente y una comida en la base de datos.
     * @param ingCom El objeto IngredientesComida con los IDs de ingrediente y comida.
     * @return true si la creación fue exitosa, false en caso contrario.
     */
    public boolean crear(IngredientesComida ingCom) {
        boolean creado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para la inserción.
            String sql = "INSERT INTO ingredientes_comida (id_ingrediente, id_comida) VALUES (?, ?)";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los IDs de ingrediente y comida como parámetros.
            prepStmt.setInt(1, Integer.parseInt(ingCom.getIdIngrediente()));
            prepStmt.setInt(2, Integer.parseInt(ingCom.getIdComida()));

            // Ejecuta la inserción.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se insertó al menos una fila.
            creado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR INGREDIENTES_COMIDA: " + e.getMessage());
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
     * Elimina todas las relaciones de una comida específica.
     * @param id El ID de la comida cuyos ingredientes se desean eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar por el ID de la comida.
            String sql = "DELETE FROM ingredientes_comida WHERE id_comida = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID de la comida como parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));

            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR INGREDIENTES_COMIDA: " + e.getMessage());
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
