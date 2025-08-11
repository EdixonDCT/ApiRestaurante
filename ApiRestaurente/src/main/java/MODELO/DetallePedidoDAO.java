package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DetallePedidoDAO {

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Listar todos los detalles de pedidos
    public ArrayList<DetallePedido> listarTodos() {
        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM detalle_pedido";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
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
                lista.add(d);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR DETALLE_PEDIDO: " + e.getMessage());
        } finally {
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

    // MÉTODO: Obtener un detalle por ID
    public ArrayList<DetallePedido> obtenerPorId(String id) {
        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            while (rs.next()) {
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
                lista.add(d);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR DETALLE_PEDIDO: " + e.getMessage());
        } finally {
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

    public boolean crear(DetallePedido d) {
        boolean creado = false;

        try {
    conn = DBConnection.getConnection();
    String sql = "INSERT INTO detalle_pedido (id_pedido, id_comida, cantidad_comida, nota_comida, id_bebida, cantidad_bebida, nota_bebida, id_coctel, cantidad_coctel, nota_coctel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    prepStmt = conn.prepareStatement(sql);

    // id_pedido (obligatorio)
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

    int filas = prepStmt.executeUpdate();
    creado = filas > 0;
} catch (Exception e) {
    System.err.println("ERROR AL CREAR DETALLE_PEDIDO: " + e.getMessage());
    e.printStackTrace();
} finally {
    try {
        if (prepStmt != null)
            prepStmt.close();
        if (conn != null)
            conn.close();
    } catch (Exception ex) {
        System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
    }
}

return creado;

}

// MÉTODO: Eliminar detalle por ID
public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR DETALLE_PEDIDO: " + e.getMessage());
        } finally {
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
