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
            String sql = "SELECT c.*, t.nombre AS nombre_cajero FROM caja c INNER JOIN trabajadores t ON c.cedula_trabajador = t.cedula";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                Caja caja = new Caja();
                caja.setId(rs.getString("id"));
                caja.setFechaApertura(rs.getString("fecha_apertura"));
                caja.setHoraApertura(rs.getString("hora_apertura"));
                caja.setMontoApertura(rs.getString("monto_apertura"));
                caja.setFechaCierre(rs.getString("fecha_cierre"));
                caja.setHoraCierre(rs.getString("hora_cierre"));
                caja.setMontoCierre(rs.getString("monto_cierre"));
                caja.setCedulaTrabajador(rs.getString("cedula_trabajador"));
                caja.setNombreCajero(rs.getString("nombre_cajero"));
                lista.add(caja);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
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
            String sql = "SELECT c.*, t.nombre AS nombre_cajero FROM caja c INNER JOIN trabajadores t ON c.cedula_trabajador = t.cedula WHERE c.id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                caja = new Caja();
                caja.setId(rs.getString("id"));
                caja.setFechaApertura(rs.getString("fecha_apertura"));
                caja.setHoraApertura(rs.getString("hora_apertura"));
                caja.setMontoApertura(rs.getString("monto_apertura"));
                caja.setFechaCierre(rs.getString("fecha_cierre"));
                caja.setHoraCierre(rs.getString("hora_cierre"));
                caja.setMontoCierre(rs.getString("monto_cierre"));
                caja.setCedulaTrabajador(rs.getString("cedula_trabajador"));
                caja.setNombreCajero(rs.getString("nombre_cajero"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER CAJA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
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
            String sql = "INSERT INTO caja (monto_apertura, cedula_trabajador) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setInt(2, Integer.parseInt(caja.getCedulaTrabajador()));

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR CAJA: " + e.getMessage());
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

    public boolean actualizarApertura(Caja caja) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE caja SET monto_apertura = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setInt(2, Integer.parseInt(caja.getId()));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
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

    public boolean actualizarCierre(Caja caja) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE caja SET fecha_cierre = CURRENT_DATE(), hora_cierre = CURRENT_TIME(), monto_cierre = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoCierre()));
            prepStmt.setInt(2, Integer.parseInt(caja.getId()));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
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
