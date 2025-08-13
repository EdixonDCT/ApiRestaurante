// Define el paquete al que pertenece la clase.
package MODELO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos del objeto Ingrediente, implementando el patrón DAO.
// Esta clase gestiona las operaciones CRUD (Crear, Leer, Actualizar, Borrar) para la tabla de ingredientes.
public class IngredienteDAO {
    // Variables a nivel de clase para gestionar la conexión a la base de datos,
    // la sentencia SQL preparada y el conjunto de resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtiene una lista de todos los ingredientes de la base de datos.
    public ArrayList<Ingrediente> listarTodos() {
        // Inicializa un ArrayList para almacenar los objetos Ingrediente.
        ArrayList<Ingrediente> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para seleccionar todos los registros.
            String sql = "SELECT * FROM ingredientes";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene el conjunto de resultados.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto Ingrediente.
                Ingrediente ingrediente = new Ingrediente();
                // Asigna los valores de las columnas a las propiedades del objeto.
                ingrediente.setId(rs.getString("id"));
                ingrediente.setNombre(rs.getString("nombre"));
                // Agrega el objeto a la lista.
                lista.add(ingrediente);
            }
        } catch (Exception e) {
            // Captura y muestra cualquier error que ocurra durante la consulta.
            System.err.println("ERROR AL LISTAR INGREDIENTES: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque 'finally' para asegurar el cierre de todos los recursos.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna la lista de ingredientes.
        return lista;
    }

    // MÉTODO: Obtiene un ingrediente específico por su ID.
    public Ingrediente obtenerPorId(String id) {
        // Inicializa el objeto Ingrediente como nulo.
        Ingrediente ingrediente = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para buscar un ingrediente por su ID.
            String sql = "SELECT * FROM ingredientes WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte el ID de String a Int y lo asigna al parámetro de la consulta.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si se encuentra un registro, se crea el objeto.
            if (rs.next()) {
                ingrediente = new Ingrediente();
                ingrediente.setId(rs.getString("id"));
                ingrediente.setNombre(rs.getString("nombre"));
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL OBTENER INGREDIENTE POR ID: " + e.getMessage());
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

        return ingrediente;
    }

    // MÉTODO: Verifica si un ingrediente existe en la base de datos por su ID.
    public boolean existeIngredientePorId(String id) {
        boolean existe = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define una consulta optimizada para verificar la existencia (selecciona un valor constante).
            String sql = "SELECT 1 FROM ingredientes WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID al parámetro de la consulta.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si el conjunto de resultados tiene al menos una fila, el ingrediente existe.
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL VERIFICAR INGREDIENTE POR ID: " + e.getMessage());
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

        return existe;
    }

    // MÉTODO: Crea un nuevo registro de ingrediente en la base de datos.
    public boolean crear(Ingrediente ingrediente) {
        boolean creado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para insertar un nuevo registro.
            String sql = "INSERT INTO ingredientes (nombre) VALUES (?)";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el nombre del ingrediente al parámetro de la consulta.
            prepStmt.setString(1, ingrediente.getNombre());

            // Ejecuta la inserción y obtiene el número de filas afectadas.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se insertó al menos una fila.
            creado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL CREAR INGREDIENTE: " + e.getMessage());
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

    // MÉTODO: Actualiza un ingrediente existente en la base de datos.
    public boolean actualizar(Ingrediente ingrediente) {
        boolean actualizado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar el nombre de un ingrediente por su ID.
            String sql = "UPDATE ingredientes SET nombre = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el nuevo nombre y el ID al parámetro de la consulta.
            prepStmt.setString(1, ingrediente.getNombre());
            prepStmt.setInt(2, Integer.parseInt(ingrediente.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se actualizó al menos una fila.
            actualizado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR INGREDIENTE: " + e.getMessage());
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

        return actualizado;
    }

    // MÉTODO: Elimina un ingrediente de la base de datos por su ID.
    public boolean eliminar(String idIngrediente) {
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar un registro por su ID.
            String sql = "DELETE FROM ingredientes WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID del ingrediente a eliminar.
            prepStmt.setInt(1, Integer.parseInt(idIngrediente));
            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ELIMINAR INGREDIENTE: " + e.getMessage());
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
