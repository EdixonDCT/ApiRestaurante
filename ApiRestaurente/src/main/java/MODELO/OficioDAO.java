package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase que maneja todas las operaciones CRUD para la tabla "oficios"
public class OficioDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtener todos los oficios de la tabla
    public ArrayList<Oficio> listarTodos() {
        ArrayList<Oficio> lista = new ArrayList<>();

        try {
            // Conexión a la base de datos
            conn = DBConnection.getConnection();

            // Consulta SQL para seleccionar todos los registros
            String sql = "SELECT * FROM oficios";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) {
                Oficio oficio = new Oficio();
                oficio.setCodigo(rs.getString("codigo"));
                oficio.setTipo(rs.getString("tipo"));
                oficio.setSalario(rs.getString("salario"));
                lista.add(oficio);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR OFICIOS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar conexiones
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

    // MÉTODO: Buscar un oficio por su ID
    public Oficio obtenerPorId(String id) {
        Oficio oficio = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM oficios WHERE codigo = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                oficio = new Oficio();
                oficio.setCodigo(rs.getString("codigo"));
                oficio.setTipo(rs.getString("tipo"));
                oficio.setSalario(rs.getString("salario"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER OFICIO POR ID: " + e.getMessage());
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

        return oficio;
    }

    // MÉTODO: Insertar un nuevo oficio
    public boolean crear(Oficio oficio) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO oficios (tipo, salario) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, oficio.getTipo());
            prepStmt.setDouble(2, Double.parseDouble(oficio.getSalario()));

            // Ejecutar inserción y verificar si se insertó al menos una fila
            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR OFICIO: " + e.getMessage());
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

    // MÉTODO: Actualizar un oficio existente
    public boolean actualizar(Oficio oficio) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE oficios SET tipo = ?, salario = ? WHERE codigo = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, oficio.getTipo());
            prepStmt.setDouble(2, Double.parseDouble(oficio.getSalario()));
            prepStmt.setInt(3, Integer.parseInt(oficio.getCodigo()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR OFICIO: " + e.getMessage());
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

    // MÉTODO: Eliminar un oficio por su código
    public boolean eliminar(String codOficio) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM oficios WHERE codigo = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(codOficio));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR OFICIO: " + e.getMessage());
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
