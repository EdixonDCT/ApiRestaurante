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
    
public ArrayList<DetallePedido> obtenerPorPedidoId(String id) {//es el metodo que trae los datos del pedido para la factura
    ArrayList<DetallePedido> lista = new ArrayList<>();//

    try {
                    // Establece la conexión.
        conn = DBConnection.getConnection();

        String sql = "SELECT dp.id, dp.id_pedido, p.fecha, p.hora, p.numero_mesa, p.metodo_pago, "//seleccionamos los datos del pedido para no realizar mas consultas
                   + "c.id AS id_comida, c.nombre AS comida, c.precio AS precio_comida, dp.cantidad_comida, dp.nota_comida, "//pasamos la informacion de la comida
                   + "(c.precio * dp.cantidad_comida) AS total_comida, "//pasamos el precio de la comida ya multiplicada
                   + "b.id AS id_bebida, b.nombre AS bebida, b.precio AS precio_bebida, dp.cantidad_bebida, dp.nota_bebida, "//pasamos la informacion de la bebida
                   + "(b.precio * dp.cantidad_bebida) AS total_bebida, "//pasamos la precio de la bebida ya multiplicada
                   + "co.id AS id_coctel, co.nombre AS coctel, co.precio AS precio_coctel, dp.cantidad_coctel, dp.nota_coctel, "//pasamos la informacion del coctel
                   + "(co.precio * dp.cantidad_coctel) AS total_coctel, "//pasamos la informacion del coctel ya multiplicado
                   + "p.id_reserva, r.precio AS precio_reserva, t.nombre AS nombre_cajero "//informacion de reserva si existe y su precio, ademas del nombre del cajero
                   + "FROM detalle_pedido dp "//la tabla principal que se busca
                   + "LEFT JOIN comidas c ON c.id = dp.id_comida "//si hay coincidencia devuelve sino null en comidas
                   + "LEFT JOIN bebidas b ON b.id = dp.id_bebida "//si hay coincidencia devuelve sino null en comidas
                   + "LEFT JOIN cocteles co ON co.id = dp.id_coctel "//si hay coincidencia devuelve sino null en cocteles
                   + "INNER JOIN pedidos p ON p.id = dp.id_pedido "//si hay coincidencia devuelve sino null en comidas
                   + "LEFT JOIN reservas r ON r.id = p.id_reserva "//si hay coincidencia devuelve sino null en reservas
                   + "LEFT JOIN caja ca ON ca.id = p.id_caja "//si hay coincidencia devuelve sino null en caja pero igual siempre tendra una caja 
                   + "LEFT JOIN trabajadores t ON t.cedula = ca.cedula_trabajador "//si hay coincidencia devuelve sino null pero lo mismo siempre habra un trabajador
                   + "WHERE dp.id_pedido = ?";//todo donde el id pedido 

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setInt(1, Integer.parseInt(id));//se envia el parametro
        rs = prepStmt.executeQuery();

        while (rs.next()) {
            //usa un modelo detallePedido que esta diseñado para tener todos los datos
            DetallePedido d = new DetallePedido();

            // Datos generales
            d.setId(rs.getString("id"));//se obtiene el id del detalle pedido pero pueden ser varios por eso es un arrayList
            d.setId_pedido(rs.getString("id_pedido"));//se obtiene el id del pedido
            d.setFecha(rs.getString("fecha"));//se obtiene la fecha del pedido
            d.setHora(rs.getString("hora"));//se obtiene la hora del pedido
            d.setNumero_mesa(rs.getString("numero_mesa"));//se obtiene la mesa del pedido
            d.setMetodo_pago(rs.getString("metodo_pago"));//se obtiene el metodo de pago

            // Comida
            d.setId_comida(rs.getString("id_comida"));//se obtiene el id de la comida
            d.setComida(rs.getString("comida"));//se obtiene el nombre de la comida
            d.setPrecio_comida(rs.getString("precio_comida"));//se obtiene el precio normal de la comida
            d.setCantidad_comida(rs.getString("cantidad_comida"));//se obtiene la cantidad de comida solicitada
            d.setNota_comida(rs.getString("nota_comida"));//nota comida puede ser nulo pero es informacion adicional
            d.setTotal_comida(rs.getString("total_comida"));//se obtinee el resultado de precio comida por cantidad comida

            // Bebida
            d.setId_bebida(rs.getString("id_bebida"));//se obtiene el id de la bebida
            d.setBebida(rs.getString("bebida"));//se obtiene el nombre de la bebida
            d.setPrecio_bebida(rs.getString("precio_bebida"));//se obtiene el precio de la bebida
            d.setCantidad_bebida(rs.getString("cantidad_bebida"));//se se obtiene la cantidad de bebida solicitada
            d.setNota_bebida(rs.getString("nota_bebida"));//nota bebida puede ser nulo pero es informacion adicional
            d.setTotal_bebida(rs.getString("total_bebida"));//se obtiene por el resultado de precio bebida y la cantidad bebida

            // Cóctel
            d.setId_coctel(rs.getString("id_coctel"));//se obtiene el id de coctel
            d.setCoctel(rs.getString("coctel"));//se obtiene el nombre del coctel
            d.setPrecio_coctel(rs.getString("precio_coctel"));//se obtiene el precio del coctel
            d.setCantidad_coctel(rs.getString("cantidad_coctel"));//se obtiene la cantidad del coctel solicitada
            d.setNota_coctel(rs.getString("nota_coctel"));//nota coctel puede ser nulo pero es informacion adicional
            d.setTotal_coctel(rs.getString("total_coctel"));//se obtiene por el resultado del precio coctel y la cantidad coctel

            // Reserva y cajero
            d.setId_reserva(rs.getString("id_reserva"));//id de la reserva pero puede ser nulo
            d.setPrecio_reserva(rs.getString("precio_reserva"));//precio de la reserva pero si el anterior es nulo este tambien pero es un calculo hecho al realizar la reserva
            d.setNombre_cajero(rs.getString("nombre_cajero"));//nombre del cajero que facturo

            lista.add(d);
        }
    } catch (Exception e) {
        System.err.println("ERROR AL LISTAR DETALLE_PEDIDO: " + e.getMessage());
    } finally {
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
