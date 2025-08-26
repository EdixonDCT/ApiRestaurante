// Define el paquete al que pertenece la clase.
package MODELO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos del objeto DetallePedido, implementando el patrón DAO.
// Esta clase gestiona las operaciones CRUD para los detalles de un pedido.
public class DetallePedidoDAO {

    // Variables a nivel de clase para gestionar la conexión a la base de datos,
    // la sentencia SQL preparada y el conjunto de resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtiene una lista de todos los detalles de pedidos de la base de datos.
    public ArrayList<DetallePedido> listarTodos() {
        // Inicializa un ArrayList para almacenar los objetos DetallePedido.
        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para seleccionar todos los registros.
            String sql = "SELECT * FROM detalle_pedido";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene el conjunto de resultados.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto DetallePedido.
                DetallePedido d = new DetallePedido();
                // Asigna los valores de las columnas a las propiedades del objeto.
                d.setId(rs.getString("id"));
                d.setId_pedido(rs.getString("id_pedido"));
                d.setId_comida(rs.getString("id_comida"));
                d.setCantidad_comida(rs.getString("cantidad_comida"));
                d.setNota_comida(rs.getString("nota_comida"));
                d.setId_bebida(rs.getString("id_bebida"));
                d.setCantidad_bebida(rs.getString("cantidad_bebida"));
                d.setNota_bebida(rs.getString("nota_bebida"));
                d.setId_coctel(rs.getString("id_coctel"));
                d.setCantidad_coctel(rs.getString("cantidad_coctel"));
                d.setNota_coctel(rs.getString("nota_coctel"));
                // Agrega el objeto a la lista.
                lista.add(d);
            }
        } catch (Exception e) {
            // Captura y muestra cualquier error que ocurra durante la consulta.
            System.err.println("ERROR AL LISTAR DETALLE_PEDIDO: " + e.getMessage());
        } finally {
            // Bloque 'finally' para asegurar el cierre de todos los recursos.
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

        // Retorna la lista de detalles de pedidos.
        return lista;
    }

    // MÉTODO: Obtiene los detalles de un pedido específico por el ID del pedido.
    public ArrayList<DetallePedido> obtenerPorId(String id) {
        // Inicializa un ArrayList para almacenar los objetos DetallePedido.
        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para buscar detalles por el ID del pedido.
            String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte el ID de String a Int y lo asigna al parámetro de la consulta.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Itera sobre los resultados, ya que un pedido puede tener varios detalles.
            while (rs.next()) {
                // Crea y llena un nuevo objeto DetallePedido.
                DetallePedido d = new DetallePedido();
                d.setId(rs.getString("id"));
                d.setId_pedido(rs.getString("id_pedido"));
                d.setId_comida(rs.getString("id_comida"));
                d.setCantidad_comida(rs.getString("cantidad_comida"));
                d.setNota_comida(rs.getString("nota_comida"));
                d.setId_bebida(rs.getString("id_bebida"));
                d.setCantidad_bebida(rs.getString("cantidad_bebida"));
                d.setNota_bebida(rs.getString("nota_bebida"));
                d.setId_coctel(rs.getString("id_coctel"));
                d.setCantidad_coctel(rs.getString("cantidad_coctel"));
                d.setNota_coctel(rs.getString("nota_coctel"));
                // Agrega el objeto a la lista.
                lista.add(d);
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL LISTAR DETALLE_PEDIDO: " + e.getMessage());
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

        return lista;
    }
    
    public ArrayList<DetallePedido> obtenerPorPedidoId(String id) {
        // Inicializa un ArrayList para almacenar los objetos DetallePedido.
        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para buscar detalles por el ID del pedido.
            String sql = "SELECT dp.id, dp.id_pedido, c.id AS id_comida, c.nombre AS comida, c.precio AS precio_comida, dp.cantidad_comida, dp.nota_comida, (c.precio * dp.cantidad_comida) AS total_comida, b.id AS id_bebida, b.nombre AS bebida, b.precio AS precio_bebida, dp.cantidad_bebida, dp.nota_bebida, (b.precio * dp.cantidad_bebida) AS total_bebida, co.id AS id_coctel, co.nombre AS coctel, co.precio AS precio_coctel, dp.cantidad_coctel, dp.nota_coctel, (co.precio * dp.cantidad_coctel) AS total_coctel FROM detalle_pedido dp LEFT JOIN comidas c ON c.id = dp.id_comida LEFT JOIN bebidas b ON b.id = dp.id_bebida LEFT JOIN cocteles co ON co.id = dp.id_coctel WHERE dp.id_pedido = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte el ID de String a Int y lo asigna al parámetro de la consulta.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Itera sobre los resultados, ya que un pedido puede tener varios detalles.
            while (rs.next()) {
                // Crea y llena un nuevo objeto DetallePedido.
                DetallePedido d = new DetallePedido();
                d.setId(rs.getString("id"));
                d.setId_pedido(rs.getString("id_pedido"));
                d.setId_comida(rs.getString("comida"));
                d.setCantidad_comida(rs.getString("cantidad_comida")+"/"+rs.getString("precio_comida"));
                d.setNota_comida(rs.getString("nota_comida"));
                d.setId_bebida(rs.getString("bebida"));
                d.setCantidad_bebida(rs.getString("cantidad_bebida")+"/"+rs.getString("precio_bebida"));
                d.setNota_bebida(rs.getString("nota_bebida"));
                d.setId_coctel(rs.getString("coctel"));
                d.setCantidad_coctel(rs.getString("cantidad_coctel")+"/"+rs.getString("precio_coctel"));
                d.setNota_coctel(rs.getString("nota_coctel"));
                // Agrega el objeto a la lista.
                lista.add(d);
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL LISTAR DETALLE_PEDIDO: " + e.getMessage());
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

        return lista;
    }
    // MÉTODO: Crea un nuevo registro de detalle de pedido en la base de datos.
public boolean crear(DetallePedido d) {
    boolean creado = false;

    try {
        conn = DBConnection.getConnection();

        // Consulta SQL con validaciones: 
        // - id_pedido siempre debe existir
        // - id_comida, id_bebida, id_coctel pueden ser NULL o deben existir en su tabla
        String sql = 
            "INSERT INTO detalle_pedido " +
            "(id_pedido, id_comida, cantidad_comida, nota_comida, " +
            " id_bebida, cantidad_bebida, nota_bebida, " +
            " id_coctel, cantidad_coctel, nota_coctel) " +
            "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
            "WHERE EXISTS (SELECT 1 FROM pedidos p WHERE p.id = ?) " +
            "  AND (? IS NULL OR EXISTS (SELECT 1 FROM comidas c WHERE c.id = ?)) " +
            "  AND (? IS NULL OR EXISTS (SELECT 1 FROM bebidas b WHERE b.id = ?)) " +
            "  AND (? IS NULL OR EXISTS (SELECT 1 FROM cocteles co WHERE co.id = ?))";

        prepStmt = conn.prepareStatement(sql);

        // ----------------------------
        // 1) Parámetros del INSERT
        // ----------------------------
        // id_pedido
        prepStmt.setInt(1, Integer.parseInt(d.getId_pedido()));

        // id_comida
        if (d.getId_comida() != null && !d.getId_comida().isEmpty()) {
            prepStmt.setInt(2, Integer.parseInt(d.getId_comida()));
        } else {
            prepStmt.setNull(2, java.sql.Types.INTEGER);
        }

        // cantidad_comida
        if (d.getCantidad_comida() != null && !d.getCantidad_comida().isEmpty()) {
            prepStmt.setInt(3, Integer.parseInt(d.getCantidad_comida()));
        } else {
            prepStmt.setNull(3, java.sql.Types.INTEGER);
        }

        // nota_comida
        if (d.getNota_comida() != null && !d.getNota_comida().isEmpty()) {
            prepStmt.setString(4, d.getNota_comida());
        } else {
            prepStmt.setNull(4, java.sql.Types.VARCHAR);
        }

        // id_bebida
        if (d.getId_bebida() != null && !d.getId_bebida().isEmpty()) {
            prepStmt.setInt(5, Integer.parseInt(d.getId_bebida()));
        } else {
            prepStmt.setNull(5, java.sql.Types.INTEGER);
        }

        // cantidad_bebida
        if (d.getCantidad_bebida() != null && !d.getCantidad_bebida().isEmpty()) {
            prepStmt.setInt(6, Integer.parseInt(d.getCantidad_bebida()));
        } else {
            prepStmt.setNull(6, java.sql.Types.INTEGER);
        }

        // nota_bebida
        if (d.getNota_bebida() != null && !d.getNota_bebida().isEmpty()) {
            prepStmt.setString(7, d.getNota_bebida());
        } else {
            prepStmt.setNull(7, java.sql.Types.VARCHAR);
        }

        // id_coctel
        if (d.getId_coctel() != null && !d.getId_coctel().isEmpty()) {
            prepStmt.setInt(8, Integer.parseInt(d.getId_coctel()));
        } else {
            prepStmt.setNull(8, java.sql.Types.INTEGER);
        }

        // cantidad_coctel
        if (d.getCantidad_coctel() != null && !d.getCantidad_coctel().isEmpty()) {
            prepStmt.setInt(9, Integer.parseInt(d.getCantidad_coctel()));
        } else {
            prepStmt.setNull(9, java.sql.Types.INTEGER);
        }

        // nota_coctel
        if (d.getNota_coctel() != null && !d.getNota_coctel().isEmpty()) {
            prepStmt.setString(10, d.getNota_coctel());
        } else {
            prepStmt.setNull(10, java.sql.Types.VARCHAR);
        }

        // ----------------------------
        // 2) Parámetros de validaciones en el WHERE
        // ----------------------------
        // pedido obligatorio
        prepStmt.setInt(11, Integer.parseInt(d.getId_pedido()));

        // comida
        if (d.getId_comida() != null && !d.getId_comida().isEmpty()) {
            prepStmt.setInt(12, Integer.parseInt(d.getId_comida()));
            prepStmt.setInt(13, Integer.parseInt(d.getId_comida()));
        } else {
            prepStmt.setNull(12, java.sql.Types.INTEGER);
            prepStmt.setNull(13, java.sql.Types.INTEGER);
        }

        // bebida
        if (d.getId_bebida() != null && !d.getId_bebida().isEmpty()) {
            prepStmt.setInt(14, Integer.parseInt(d.getId_bebida()));
            prepStmt.setInt(15, Integer.parseInt(d.getId_bebida()));
        } else {
            prepStmt.setNull(14, java.sql.Types.INTEGER);
            prepStmt.setNull(15, java.sql.Types.INTEGER);
        }

        // coctel
        if (d.getId_coctel() != null && !d.getId_coctel().isEmpty()) {
            prepStmt.setInt(16, Integer.parseInt(d.getId_coctel()));
            prepStmt.setInt(17, Integer.parseInt(d.getId_coctel()));
        } else {
            prepStmt.setNull(16, java.sql.Types.INTEGER);
            prepStmt.setNull(17, java.sql.Types.INTEGER);
        }

        // Ejecuta
        int filas = prepStmt.executeUpdate();
        creado = filas > 0; // si filas = 0, significa que no cumplió validaciones

    } catch (Exception e) {
        System.err.println("ERROR AL CREAR DETALLE_PEDIDO: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (prepStmt != null) prepStmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
        }
    }

    return creado;
}


    // MÉTODO: Elimina los detalles de un pedido específico por el ID del pedido.
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar registros por el ID del pedido.
            String sql = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID del pedido a eliminar.
            prepStmt.setInt(1, Integer.parseInt(id));

            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ELIMINAR DETALLE_PEDIDO: " + e.getMessage());
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

        return eliminado;
    }
}
