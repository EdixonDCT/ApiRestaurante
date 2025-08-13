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
            String sql = "SELECT ic.id,ic.id_ingrediente, i.nombre AS nombre_ingrediente,ic.id_comida,c.nombre AS nombre_comida FROM ingredientes_comida AS ic INNER JOIN comidas c ON ic.id_comida = c.id INNER JOIN ingredientes i ON ic.id_ingrediente = i.id";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                IngredientesComida ingCom = new IngredientesComida();
                ingCom.setId(rs.getString("id"));
                ingCom.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCom.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCom.setIdComida(rs.getString("id_comida"));
                ingCom.setNombreComida(rs.getString("nombre_comida"));
                lista.add(ingCom);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR INGREDIENTES_COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return lista;
    }

    // MÉTODO: Obtener un registro por ID
    public IngredientesComida obtenerPorId(String id) {
        IngredientesComida ingCom = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT ic.id, ic.id_ingrediente, i.nombre AS nombre_ingrediente, ic.id_comida, c.nombre AS nombre_comida FROM ingredientes_comida AS ic INNER JOIN comidas c ON ic.id_comida = c.id INNER JOIN ingredientes i ON ic.id_ingrediente = i.id WHERE ic.id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                ingCom = new IngredientesComida();
                ingCom.setId(rs.getString("id"));
                ingCom.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCom.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCom.setIdComida(rs.getString("id_comida"));
                ingCom.setNombreComida(rs.getString("nombre_comida"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COMIDA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return ingCom;
    }

    public ArrayList<IngredientesComida> obtenerPorComida(String id) {
        ArrayList<IngredientesComida> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT ic.id, ic.id_ingrediente, i.nombre AS nombre_ingrediente, ic.id_comida, c.nombre AS nombre_comida FROM ingredientes_comida AS ic INNER JOIN comidas c ON ic.id_comida = c.id INNER JOIN ingredientes i ON ic.id_ingrediente = i.id WHERE ic.id_comida = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                IngredientesComida ingCom = new IngredientesComida();
                ingCom = new IngredientesComida();
                ingCom.setId(rs.getString("id"));
                ingCom.setIdIngrediente(rs.getString("id_ingrediente"));
                ingCom.setNombreIngrediente(rs.getString("nombre_ingrediente"));
                ingCom.setIdComida(rs.getString("id_comida"));
                ingCom.setNombreComida(rs.getString("nombre_comida"));
                lista.add(ingCom);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER INGREDIENTES_COMIDA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return lista;
    }

    // MÉTODO: Crear un nuevo registro
    public boolean crear(IngredientesComida ingCom) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO ingredientes_comida (id_ingrediente, id_comida) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(ingCom.getIdIngrediente()));
            prepStmt.setInt(2, Integer.parseInt(ingCom.getIdComida()));

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR INGREDIENTES_COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
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
            String sql = "DELETE FROM ingredientes_comida WHERE id_comida = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR INGREDIENTES_COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return eliminado;
    }
}
