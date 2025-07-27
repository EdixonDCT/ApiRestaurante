package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IngredientesCoctelDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<IngredientesCoctel> listarTodos() {
        ArrayList<IngredientesCoctel> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT ic.id,ic.id_ingrediente, i.nombre AS nombre_ingrediente,ic.id_coctel,c.nombre AS nombre_coctel FROM ingredientes_coctel AS ic INNER JOIN cocteles c ON ic.id_coctel = c.id INNER JOIN ingredientes i ON ic.id_ingrediente = i.id";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                IngredientesCoctel ingCoc = new IngredientesCoctel();
                ingCoc.setId(rs.getString("id"));
                ingCoc.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCoc.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCoc.setIdCoctel(rs.getString("id_coctel"));
                ingCoc.setNombreCoctel(rs.getString("nombre_coctel"));
                lista.add(ingCoc);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR INGREDIENTES_COCTEL: " + e.getMessage());
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

    public IngredientesCoctel obtenerPorId(String id) {
        IngredientesCoctel ingCoc = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT ic.id,ic.id_ingrediente, i.nombre AS nombre_ingrediente,ic.id_coctel,c.nombre AS nombre_coctel FROM ingredientes_coctel AS ic INNER JOIN cocteles c ON ic.id_coctel = c.id INNER JOIN ingredientes i ON ic.id_ingrediente = i.id WHERE ic.id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                ingCoc = new IngredientesCoctel();
                ingCoc.setId(rs.getString("id"));
                ingCoc.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCoc.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCoc.setIdCoctel(rs.getString("id_coctel"));
                ingCoc.setNombreCoctel(rs.getString("nombre_coctel"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COCTEL POR ID: " + e.getMessage());
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

        return ingCoc;
    }

    public boolean crear(IngredientesCoctel ingCoc) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO ingredientes_coctel (id_ingrediente, id_coctel) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(ingCoc.getIdIngrediente()));
            prepStmt.setInt(2, Integer.parseInt(ingCoc.getIdCoctel()));

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR INGREDIENTES_COCTEL: " + e.getMessage());
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

    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM ingredientes_coctel WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR INGREDIENTES_COCTEL: " + e.getMessage());
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
