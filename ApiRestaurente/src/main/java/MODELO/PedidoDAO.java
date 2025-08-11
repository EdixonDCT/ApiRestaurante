package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PedidoDAO {

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Pedido> listarTodos() {
        ArrayList<Pedido> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM pedidos";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getString("id"));
                pedido.setNumeroMesa(rs.getString("numero_mesa"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(rs.getString("valor_total"));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(rs.getString("numero_clientes"));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
                pedido.setFacturado(rs.getString("facturado"));
                pedido.setEliminado(rs.getString("eliminado"));

                lista.add(pedido);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR PEDIDOS: " + e.getMessage());
            e.printStackTrace();
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

    public Pedido obtenerPorId(String id) {
        Pedido pedido = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM pedidos WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                pedido = new Pedido();
                pedido.setId(rs.getString("id"));
                pedido.setNumeroMesa(rs.getString("numero_mesa"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(rs.getString("valor_total"));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(rs.getString("numero_clientes"));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));

            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER PEDIDO: " + e.getMessage());
            e.printStackTrace();
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

        return pedido;
    }

    public String[] crear(Pedido pedido) {
        String[] resultado = new String[2];
        resultado[0] = "Pedido: no se pudo crear."; // Mensaje por defecto
        resultado[1] = "-1"; // ID por defecto si falla

        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO pedidos (numero_mesa, id_caja, numero_clientes, correo_cliente, metodo_pago, fecha, hora) "
                    + "VALUES (?, ?, ?, ?, ?, CURRENT_DATE(), CURRENT_TIME())";

            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getIdCaja()));
            prepStmt.setInt(3, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setString(4, pedido.getCorreoCliente());
            prepStmt.setString(5, pedido.getMetodoPago());

            int filas = prepStmt.executeUpdate();

            if (filas > 0) {
                rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    pedido.setId(String.valueOf(idGenerado)); // si lo manejas como String
                    resultado[0] = "Pedido: creado EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
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

        return resultado;
    }

public boolean crearReserva(Pedido pedido) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO pedidos (numero_mesa,id_reserva,correo_cliente) VALUES (?,?,?)";

            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getIdReserva()));
            prepStmt.setString(3, pedido.getCorreoCliente());

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR PEDIDO: " + e.getMessage());
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

    public boolean actualizar(Pedido pedido) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE pedidos SET numero_mesa = ?, fecha = ?, hora = ?, id_caja = ?, numero_clientes = ?, id_reserva = ?, nota = ?, correo_cliente = ?, metodo_pago = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setString(2, pedido.getFecha());
            prepStmt.setString(3, pedido.getHora());
            prepStmt.setInt(4, Integer.parseInt(pedido.getIdCaja()));
            prepStmt.setInt(5, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setString(6, pedido.getIdReserva());
            prepStmt.setString(7, pedido.getCorreoCliente());
            prepStmt.setString(8, pedido.getMetodoPago());
            prepStmt.setInt(9, Integer.parseInt(pedido.getId()));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR PEDIDO: " + e.getMessage());
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

        return actualizado;
    }

    public boolean actualizarReserva(Pedido pedido) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE pedidos SET fecha = CURRENT_DATE(), hora = CURRENT_TIME(), id_caja = ?, numero_clientes = ?, nota = ?, metodo_pago = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getIdCaja()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setString(3, pedido.getMetodoPago());
            prepStmt.setInt(4, Integer.parseInt(pedido.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR PEDIDO: " + e.getMessage());
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

        return actualizado;
    }

  public boolean PatchTotal(String id) {
    boolean actualizado = false;
    Connection conn = null;
    PreparedStatement prepStmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();

        // 1. Calcular el total del pedido desde detalle_pedido y relaciones
        String consultaTotal = "SELECT SUM(IFNULL(c.precio * dp.cantidad_comida, 0) + IFNULL(b.precio * dp.cantidad_bebida, 0) + IFNULL(co.precio * dp.cantidad_coctel, 0)) AS total_pedido FROM detalle_pedido AS dp LEFT JOIN comidas AS c ON dp.id_comida = c.id LEFT JOIN bebidas AS b ON dp.id_bebida = b.id LEFT JOIN cocteles AS co ON dp.id_coctel = co.id WHERE dp.id_pedido = ? GROUP BY dp.id_pedido";

        prepStmt = conn.prepareStatement(consultaTotal);
        prepStmt.setInt(1, Integer.parseInt(id));
        rs = prepStmt.executeQuery();

        if (!rs.next()) {
            System.out.println("Pedido: no hay pedidos con el id " + id+".");
        } else {
            double total = rs.getDouble("total_pedido");

            // 2. Actualizar la tabla pedidos con el valor total calculado
            String actualizarSQL = "UPDATE pedidos SET valor_total = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(actualizarSQL);
            prepStmt.setDouble(1, total);
            prepStmt.setInt(2, Integer.parseInt(id));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;
        }

    } catch (Exception e) {
        System.err.println("ERROR AL ACTUALIZAR TOTAL DEL PEDIDO: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (prepStmt != null) prepStmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
        }
    }

    return actualizado;
}


    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();

            // Paso 1: Buscar si hay registros en detalle_pedido
            String sqlBuscar = "SELECT id FROM detalle_pedido WHERE id_pedido = ?";
            prepStmt = conn.prepareStatement(sqlBuscar);
            prepStmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = prepStmt.executeQuery();

            // Si encuentra al menos un resultado, elimina los detalle_pedido
            if (rs.next()) {
                String sqlEliminarDetalle = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
                prepStmt = conn.prepareStatement(sqlEliminarDetalle);
                prepStmt.setInt(1, Integer.parseInt(id));
                prepStmt.executeUpdate();
            }

            // Paso 2: Eliminar el pedido
            String sql = "DELETE FROM pedidos WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR PEDIDO: " + e.getMessage());
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

        return eliminado;
    }

}
