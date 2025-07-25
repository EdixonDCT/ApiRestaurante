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
                pedido.setId(String.valueOf(rs.getInt("id")));
                pedido.setNumeroMesa(String.valueOf(rs.getInt("numero_mesa")));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(String.valueOf(rs.getDouble("valor_total")));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(String.valueOf(rs.getInt("numero_clientes")));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setNota(rs.getString("nota"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
                lista.add(pedido);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR PEDIDOS: " + e.getMessage());
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
                pedido.setId(String.valueOf(rs.getInt("id")));
                pedido.setNumeroMesa(String.valueOf(rs.getInt("numero_mesa")));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(String.valueOf(rs.getDouble("valor_total")));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(String.valueOf(rs.getInt("numero_clientes")));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setNota(rs.getString("nota"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER PEDIDO: " + e.getMessage());
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

        return pedido;
    }

    public boolean crear(Pedido pedido) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO pedidos (numero_mesa, fecha, hora, valor_total, id_caja, numero_clientes, id_reserva, nota, correo_cliente, metodo_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setString(2, pedido.getFecha());
            prepStmt.setString(3, pedido.getHora());
            prepStmt.setDouble(4, Double.parseDouble(pedido.getValorTotal()));
            
            if (pedido.getIdCaja() == null || pedido.getIdCaja().isEmpty()) {
                prepStmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                prepStmt.setInt(5, Integer.parseInt(pedido.getIdCaja()));
            }

            prepStmt.setInt(6, Integer.parseInt(pedido.getNumeroClientes()));

            if (pedido.getIdReserva() == null || pedido.getIdReserva().isEmpty()) {
                prepStmt.setNull(7, java.sql.Types.INTEGER);
            } else {
                prepStmt.setInt(7, Integer.parseInt(pedido.getIdReserva()));
            }

            prepStmt.setString(8, pedido.getNota());
            prepStmt.setString(9, pedido.getCorreoCliente());
            prepStmt.setString(10, pedido.getMetodoPago());

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR PEDIDO: " + e.getMessage());
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

    public boolean actualizar(Pedido pedido) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE pedidos SET numero_mesa = ?, fecha = ?, hora = ?, valor_total = ?, id_caja = ?, numero_clientes = ?, id_reserva = ?, nota = ?, correo_cliente = ?, metodo_pago = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setString(2, pedido.getFecha());
            prepStmt.setString(3, pedido.getHora());
            prepStmt.setDouble(4, Double.parseDouble(pedido.getValorTotal()));

            if (pedido.getIdCaja() == null || pedido.getIdCaja().isEmpty()) {
                prepStmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                prepStmt.setInt(5, Integer.parseInt(pedido.getIdCaja()));
            }

            prepStmt.setInt(6, Integer.parseInt(pedido.getNumeroClientes()));

            if (pedido.getIdReserva() == null || pedido.getIdReserva().isEmpty()) {
                prepStmt.setNull(7, java.sql.Types.INTEGER);
            } else {
                prepStmt.setInt(7, Integer.parseInt(pedido.getIdReserva()));
            }

            prepStmt.setString(8, pedido.getNota());
            prepStmt.setString(9, pedido.getCorreoCliente());
            prepStmt.setString(10, pedido.getMetodoPago());
            prepStmt.setInt(11, Integer.parseInt(pedido.getId()));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
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
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return eliminado;
    }
}
