package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReservaDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Reserva> listarTodos() {
        ArrayList<Reserva> lista = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM reservas";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getString("id"));
                reserva.setPrecio(rs.getString("precio"));
                reserva.setFecha(rs.getString("fecha"));
                reserva.setFechaTentativa(rs.getString("fecha_tentativa"));
                reserva.setHoraTentativa(rs.getString("hora_tentativa"));
                lista.add(reserva);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR RESERVAS: " + e.getMessage());
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

    public Reserva obtenerPorId(String id) {
        Reserva reserva = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM reservas WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                reserva = new Reserva();
                reserva.setId(rs.getString("id"));
                reserva.setPrecio(rs.getString("precio"));
                reserva.setFecha(rs.getString("fecha"));
                reserva.setFechaTentativa(rs.getString("fecha_tentativa"));
                reserva.setHoraTentativa(rs.getString("hora_tentativa"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER RESERVA POR ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return reserva;
    }

    public boolean crear(Reserva reserva) {
        boolean creado = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO reservas (precio, fecha, fecha_tentativa, hora_tentativa) VALUES (?, ?, ?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setBigDecimal(1, new java.math.BigDecimal(reserva.getPrecio()));
            prepStmt.setString(2, reserva.getFecha());
            prepStmt.setString(3, reserva.getFechaTentativa());
            prepStmt.setString(4, reserva.getHoraTentativa());
            int filas = prepStmt.executeUpdate();
            creado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR RESERVA: " + e.getMessage());
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

    public boolean actualizar(Reserva reserva) {
        boolean actualizado = false;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE reservas SET precio = ?, fecha = ?, fecha_tentativa = ?, hora_tentativa = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setBigDecimal(1, new java.math.BigDecimal(reserva.getPrecio()));
            prepStmt.setString(2, reserva.getFecha());
            prepStmt.setString(3, reserva.getFechaTentativa());
            prepStmt.setString(4, reserva.getHoraTentativa());
            prepStmt.setInt(5, Integer.parseInt(reserva.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR RESERVA: " + e.getMessage());
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
            String sql = "DELETE FROM reservas WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR RESERVA: " + e.getMessage());
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
