package DAO;

import BD.DBConnection; // usamos la misma clase que MesaDAO
import MODELO.Login;
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
        public boolean ValidarActivo(String cedula) {
        boolean activo = false;
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT activo,eliminado FROM trabajadores WHERE cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula);
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                String activoSQL = rs.getString("activo");
                String eliminadoSQL = rs.getString("eliminado");
                if (activoSQL.equals("1")&&eliminadoSQL.equals("0")) activo = true;
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
        return activo;
    }
    public Login obtenerDatos(Login Login) {
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT r.nombre AS rol,t.nombre,t.apellido,t.foto AS foto FROM trabajadores AS t JOIN roles AS r ON t.id_rol = r.id WHERE cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, Login.getCedula());
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                Login.setRol(rs.getString("rol"));
                Login.setNombreApellido(rs.getString("nombre")+' '+rs.getString("apellido"));
                Login.setFotoPerfil(rs.getString("foto"));
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
        return Login;
    }
    /**
     * Devuelve los permisos asociados a la cédula desde la tabla permisos.
     */
    public List<String> obtenerPermisos(String cedula) {
        List<String> permisos = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT p.nombre AS permiso FROM trabajadores t INNER JOIN roles r ON t.id_rol = r.id INNER JOIN rolesPermisos rp ON r.id = rp.id_rol INNER JOIN permisos p ON rp.id_permiso = p.id WHERE t.cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1,cedula );
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                permisos.add(rs.getString("permiso"));
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
