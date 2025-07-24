package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BebidaDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Bebida> listarTodos() {
        ArrayList<Bebida> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bebidas";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                Bebida bebida = new Bebida();
                bebida.setId(rs.getString("id"));
                bebida.setNombre(rs.getString("nombre"));
                bebida.setPrecio(rs.getString("precio"));
                bebida.setUnidad(rs.getString("unidad"));
                bebida.setTipo(rs.getString("tipo"));
                bebida.setImagen(rs.getString("imagen"));
                bebida.setDisponible(rs.getString("disponible"));
                lista.add(bebida);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR BEBIDAS: " + e.getMessage());
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

    public Bebida obtenerPorId(String id) {
        Bebida bebida = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bebidas WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                bebida = new Bebida();
                bebida.setId(rs.getString("id"));
                bebida.setNombre(rs.getString("nombre"));
                bebida.setPrecio(rs.getString("precio"));
                bebida.setUnidad(rs.getString("unidad"));
                bebida.setTipo(rs.getString("tipo"));
                bebida.setImagen(rs.getString("imagen"));
                bebida.setDisponible(rs.getString("disponible"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER BEBIDA POR ID: " + e.getMessage());
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

        return bebida;
    }

    public boolean crear(Bebida bebida) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO bebidas (nombre, precio, unidad, tipo) VALUES (?, ?, ?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, bebida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(bebida.getPrecio()));
            prepStmt.setString(3, bebida.getUnidad());
            prepStmt.setString(4, bebida.getTipo());

            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR BEBIDA: " + e.getMessage());
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

    public boolean actualizar(Bebida bebida) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE bebidas SET nombre = ?, precio = ?, unidad = ?, tipo = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, bebida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(bebida.getPrecio()));
            prepStmt.setString(3, bebida.getUnidad());
            prepStmt.setString(4, bebida.getTipo());
            prepStmt.setInt(5, Integer.parseInt(bebida.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR BEBIDA: " + e.getMessage());
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
            String sql = "DELETE FROM bebidas WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR BEBIDA: " + e.getMessage());
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

    public boolean actualizarImagen(Bebida bebida) {
        boolean actualizarImagen = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE bebidas SET imagen = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1,bebida.getImagen());
            prepStmt.setInt(2, Integer.parseInt(bebida.getId()));

            int filas = prepStmt.executeUpdate();
            actualizarImagen = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR IMAGEN BEBIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR IMAGEN CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizarImagen;
    }

    public boolean actualizarEstado(Bebida bebida) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE bebidas SET disponible = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(bebida.getDisponible()));
            prepStmt.setInt(2, Integer.parseInt(bebida.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD BEBIDA: " + e.getMessage());
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
}
