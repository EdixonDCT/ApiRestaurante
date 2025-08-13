// Define el paquete al que pertenece la clase.
package MODELO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Oficio.
 * Se encarga de todas las operaciones de persistencia (CRUD) para la tabla "oficios".
 */
public class OficioDAO {
    // Variables a nivel de clase para gestionar la conexión, la sentencia preparada y los resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Obtiene una lista completa de todos los oficios de la base de datos.
     *
     * @return Una lista de objetos Oficio.
     */
    public ArrayList<Oficio> listarTodos() {
        ArrayList<Oficio> lista = new ArrayList<>();

        try {
            // 1. Establece la conexión a la base de datos a través de la clase auxiliar DBConnection.
            conn = DBConnection.getConnection();

            // 2. Prepara la consulta SQL para seleccionar todos los oficios.
            String sql = "SELECT * FROM oficios";
            prepStmt = conn.prepareStatement(sql);
            
            // 3. Ejecuta la consulta y guarda el resultado en un ResultSet.
            rs = prepStmt.executeQuery();

            // 4. Itera sobre cada registro (fila) del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto Oficio para cada fila.
                Oficio oficio = new Oficio();
                // Mapea los valores de las columnas del ResultSet a las propiedades del objeto Oficio.
                oficio.setCodigo(rs.getString("codigo"));
                oficio.setTipo(rs.getString("tipo"));
                oficio.setSalario(rs.getString("salario"));
                // Agrega el oficio a la lista.
                lista.add(oficio);
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

    /**
     * Busca un oficio específico por su código (ID).
     *
     * @param id El código del oficio a buscar.
     * @return Un objeto Oficio si se encuentra, o null si no existe.
     */
    public Oficio obtenerPorId(String id) {
        Oficio oficio = null;

        try {
            conn = DBConnection.getConnection();
            // 1. Consulta SQL parametrizada para evitar inyección de SQL. El '?' es un marcador de posición.
            String sql = "SELECT * FROM oficios WHERE codigo = ?";
            prepStmt = conn.prepareStatement(sql);
            
            // 2. Asigna el valor del parámetro, convirtiendo el String 'id' a un entero.
            prepStmt.setInt(1, Integer.parseInt(id));
            
            // 3. Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // 4. Si se encuentra un resultado, lo mapea al objeto Oficio.
            if (rs.next()) {
                oficio = new Oficio();
                oficio.setCodigo(rs.getString("codigo"));
                oficio.setTipo(rs.getString("tipo"));
                oficio.setSalario(rs.getString("salario"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER OFICIO POR ID: " + e.getMessage());
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

        return oficio;
    }

    /**
     * Inserta un nuevo oficio en la base de datos.
     *
     * @param oficio El objeto Oficio con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean crear(Oficio oficio) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            // 1. Consulta SQL para insertar datos. El 'codigo' se asume que es auto-incrementable.
            String sql = "INSERT INTO oficios (tipo, salario) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            
            // 2. Asigna los parámetros del objeto Oficio a la sentencia preparada.
            prepStmt.setString(1, oficio.getTipo());
            prepStmt.setDouble(2, Double.parseDouble(oficio.getSalario()));

            // 3. Ejecuta la sentencia de actualización (INSERT, UPDATE, DELETE).
            int filas = prepStmt.executeUpdate();
            
            // 4. La operación es exitosa si se afectó al menos una fila.
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR OFICIO: " + e.getMessage());
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
     * Actualiza la información de un oficio existente.
     *
     * @param oficio El objeto Oficio con los datos actualizados y el código para identificarlo.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar(Oficio oficio) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // 1. Consulta SQL para actualizar los campos 'tipo' y 'salario' basándose en el 'codigo'.
            String sql = "UPDATE oficios SET tipo = ?, salario = ? WHERE codigo = ?";
            prepStmt = conn.prepareStatement(sql);
            
            // 2. Asigna los nuevos valores y el código del oficio a actualizar.
            prepStmt.setString(1, oficio.getTipo());
            prepStmt.setDouble(2, Double.parseDouble(oficio.getSalario()));
            prepStmt.setInt(3, Integer.parseInt(oficio.getCodigo()));
            
            // 3. Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            
            // 4. La operación es exitosa si se afectó al menos una fila.
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR OFICIO: " + e.getMessage());
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

    /**
     * Elimina un oficio de la base de datos por su código.
     *
     * @param codOficio El código del oficio a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminar(String codOficio) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            // 1. Consulta SQL para eliminar un registro basándose en su código.
            String sql = "DELETE FROM oficios WHERE codigo = ?";
            prepStmt = conn.prepareStatement(sql);
            
            // 2. Asigna el código del oficio como parámetro.
            prepStmt.setInt(1, Integer.parseInt(codOficio));

            // 3. Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            
            // 4. La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR OFICIO: " + e.getMessage());
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
