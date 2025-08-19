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
    public Login autenticar(String cedula, String contrasena) {
        Login login = null;
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT cedula, rol FROM TB_TRABAJADOR WHERE cedula = ? AND contrasena = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula);
            prepStmt.setString(2, contrasena);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                login = new Login();
                login.setCedula(rs.getString("cedula"));
                login.setContrasena(contrasena);
                login.setRol(rs.getString("rol"));

                // Obtener permisos
                List<String> permisos = obtenerPermisos(cedula);
                login.setPermisos(permisos);
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
        return login; // null si no existe
    }

    /**
     * Devuelve los permisos asociados a la cédula desde la tabla permisos.
     */
    private List<String> obtenerPermisos(String cedula) {
        List<String> permisos = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT p.nombre_permiso " +
                         "FROM TB_PERMISOS p " +
                         "INNER JOIN TB_USUARIO_PERMISOS up ON p.id_permiso = up.id_permiso " +
                         "WHERE up.cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                permisos.add(rs.getString("nombre_permiso"));
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
