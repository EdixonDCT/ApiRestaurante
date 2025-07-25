package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IngredientesComidaDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Listar todos los registros
    public ArrayList<IngredientesComida> listarTodos() {
        ArrayList<IngredientesComida> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM ingredientes_comida";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                IngredientesComida ic = new IngredientesComida();
                ic.setId(rs.getString("id"));
                ic.setIdIngrediente(rs.getString("id_ingrediente"));
                ic.setIdComida(rs.getString("id_comida"));
                lista.add(ic);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR INGREDIENTES_COMIDA: " + e.getMessage());
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

    // MÉTODO: Obtener un registro por ID
    public IngredientesComida obtenerPorId(String id) {
        IngredientesComida ic = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM ingredientes_comida WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                ic = new IngredientesComida();
                ic.setId(rs.getString("id"));
                ic.setIdIngrediente(rs.getString("id_ingrediente"));
                ic.setIdComida(rs.getString("id_comida"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COMIDA POR ID: " + e.getMessage());
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

        return ic;
    }

    // MÉTODO: Crear un nuevo registro
    public boolean crear(IngredientesComida ic) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO ingredientes_comida (id_ingrediente, id_comida) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(ic.getIdIngrediente()));
            prepStmt.setInt(2, Integer.parseInt(ic.getIdComida()));

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR INGREDIENTES_COMIDA: " + e.getMessage());
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

    // MÉTODO: Eliminar un registro por ID
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM ingredientes_comida WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR INGREDIENTES_COMIDA: " + e.getMessage());
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
