// Define el paquete al que pertenece la clase.
package DAO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import MODELO.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Oficio.
 * Se encarga de todas las operaciones de persistencia (CRUD) para la tabla "oficios".
 */
public class RolesDAO {
    // Variables a nivel de clase para gestionar la conexión, la sentencia preparada y los resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Obtiene una lista completa de todos los oficios de la base de datos.
     *
     * @return Una lista de objetos Oficio.
     */
    public ArrayList<Roles> listarTodos() {
        ArrayList<Roles> lista = new ArrayList<>();

        try {
            // 1. Establece la conexión a la base de datos a través de la clase auxiliar DBConnection.
            conn = DBConnection.getConnection();

            // 2. Prepara la consulta SQL para seleccionar todos los oficios.
            String sql = "SELECT * FROM roles";
            prepStmt = conn.prepareStatement(sql);
            
            // 3. Ejecuta la consulta y guarda el resultado en un ResultSet.
            rs = prepStmt.executeQuery();

            // 4. Itera sobre cada registro (fila) del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto Oficio para cada fila.
                Roles roles = new Roles();
                // Mapea los valores de las columnas del ResultSet a las propiedades del objeto Oficio.
                roles.setId(rs.getString("id"));
                roles.setNombre(rs.getString("nombre"));
                roles.setSalario(rs.getString("salario"));
                // Agrega el oficio a la lista.
                lista.add(roles);
            }

        } catch (Exception e) {
            // Manejo de errores: imprime el mensaje de error en la consola.
            System.err.println("ERROR AL LISTAR OFICIOS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Cierra los recursos (ResultSet, PreparedStatement, Connection) en el bloque finally.
            // Esto asegura que los recursos se liberen siempre, incluso si ocurre una excepción.
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
}
