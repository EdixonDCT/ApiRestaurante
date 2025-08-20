package MODELO;

import BD.DBConnection; // usamos la misma clase que MesaDAO
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Valida si la cédula y contraseña existen en la base de datos.
     */
    public boolean autenticar(String cedula, String contrasena) {
        boolean creado = false;
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT cedula,contrasena FROM trabajadores WHERE cedula = ? AND contrasena = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula);
            prepStmt.setString(2, contrasena);
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                String cedulaSQL = rs.getString("cedula");
                String contrasenaSQL = rs.getString("contrasena");
                if (cedula.equals(cedulaSQL) && contrasena.equals(contrasenaSQL)) creado = true;
            }
        } catch (Exception e) {
            System.err.println("ERROR AL AUTENTICAR: " + e.getMessage());
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
        return creado;
    }
    public String obtenerRol(String cedula) {
        String rol = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT o.tipo AS rol FROM trabajadores AS t JOIN oficios AS o ON t.id_oficio = o.codigo WHERE cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula);
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                rol = rs.getString("rol");
            }
        } catch (Exception e) {
            System.err.println("ERROR AL AUTENTICAR: " + e.getMessage());
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
        return rol;
    }
    /**
     * Devuelve los permisos asociados a la cédula desde la tabla permisos.
     */
    public List<String> obtenerPermisos(String cedula) {
        List<String> permisos = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT * FROM ingredientes WHERE id != ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1,cedula );
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                permisos.add(rs.getString("nombre"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER PERMISOS: " + e.getMessage());
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
        return permisos;
    }
}
