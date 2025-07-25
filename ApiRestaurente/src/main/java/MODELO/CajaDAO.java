package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CajaDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Caja> listarTodos() {
        ArrayList<Caja> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM caja";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                Caja caja = new Caja();
                caja.setId(String.valueOf(rs.getInt("id")));
                caja.setFechaApertura(rs.getString("fecha_apertura"));
                caja.setHoraApertura(rs.getString("hora_apertura"));
                caja.setMontoApertura(String.valueOf(rs.getDouble("monto_apertura")));
                caja.setFechaCierre(rs.getString("fecha_cierre"));
                caja.setHoraCierre(rs.getString("hora_cierre"));
                caja.setMontoCierre(String.valueOf(rs.getDouble("monto_cierre")));
                caja.setCedulaTrabajador(rs.getString("cedula_trabajador"));
                lista.add(caja);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR CAJA: " + e.getMessage());
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

    public Caja obtenerPorId(String id) {
        Caja caja = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM caja WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                caja = new Caja();
                caja.setId(String.valueOf(rs.getInt("id")));
                caja.setFechaApertura(rs.getString("fecha_apertura"));
                caja.setHoraApertura(rs.getString("hora_apertura"));
                caja.setMontoApertura(String.valueOf(rs.getDouble("monto_apertura")));
                caja.setFechaCierre(rs.getString("fecha_cierre"));
                caja.setHoraCierre(rs.getString("hora_cierre"));
                caja.setMontoCierre(String.valueOf(rs.getDouble("monto_cierre")));
                caja.setCedulaTrabajador(rs.getString("cedula_trabajador"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER CAJA POR ID: " + e.getMessage());
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

        return caja;
    }

    public boolean crear(Caja caja) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO caja (fecha_apertura, hora_apertura, monto_apertura, fecha_cierre, hora_cierre, monto_cierre, cedula_trabajador) VALUES (?, ?, ?, ?, ?, ?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, caja.getFechaApertura());
            prepStmt.setString(2, caja.getHoraApertura());
            prepStmt.setDouble(3, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setString(4, caja.getFechaCierre());
            prepStmt.setString(5, caja.getHoraCierre());
            prepStmt.setDouble(6, Double.parseDouble(caja.getMontoCierre()));
            prepStmt.setInt(7, Integer.parseInt(caja.getCedulaTrabajador()));

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR CAJA: " + e.getMessage());
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

    public boolean actualizar(Caja caja) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE caja SET fecha_apertura = ?, hora_apertura = ?, monto_apertura = ?, fecha_cierre = ?, hora_cierre = ?, monto_cierre = ?, cedula_trabajador = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, caja.getFechaApertura());
            prepStmt.setString(2, caja.getHoraApertura());
            prepStmt.setDouble(3, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setString(4, caja.getFechaCierre());
            prepStmt.setString(5, caja.getHoraCierre());
            prepStmt.setDouble(6, Double.parseDouble(caja.getMontoCierre()));
            prepStmt.setInt(7, Integer.parseInt(caja.getCedulaTrabajador()));
            prepStmt.setInt(8, Integer.parseInt(caja.getId()));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
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
            String sql = "DELETE FROM caja WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR CAJA: " + e.getMessage());
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
    