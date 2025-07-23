package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MesaDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Mesa> listarTodos() {
        ArrayList<Mesa> lista = new ArrayList<>();

        try {
            // Conexión a la base de datos
            conn = DBConnection.getConnection();

            // Consulta SQL para seleccionar todos los registros
            String sql = "SELECT * FROM mesas";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setNumero(rs.getString("numero"));
                mesa.setCapacidad(rs.getString("capacidad"));
                mesa.setDisponible(rs.getString("disponible"));
                lista.add(mesa);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR MESAS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar conexiones
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

    // MÉTODO: Buscar un oficio por su ID
    public Mesa obtenerPorNumero(String numero) {
        Mesa mesa = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM mesas WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(numero));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                mesa = new Mesa();
                mesa.setNumero(rs.getString("numero"));
                mesa.setCapacidad(rs.getString("capacidad"));
                mesa.setDisponible(rs.getString("disponible"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER MESA POR NUMERO: " + e.getMessage());
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

        return mesa;
    }

    // MÉTODO: Insertar un nuevo oficio
    public boolean crear(Mesa mesa) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO mesas (numero,capacidad) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(mesa.getNumero()));
            prepStmt.setInt(2, Integer.parseInt(mesa.getCapacidad()));

            // Ejecutar inserción y verificar si se insertó al menos una fila
            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR MESA: " + e.getMessage());
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

    // MÉTODO: Actualizar un oficio existente
    public boolean actualizar(Mesa mesa) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE mesas SET capacidad = ?, disponible = ? WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(mesa.getCapacidad()));
            prepStmt.setInt(2, Integer.parseInt(mesa.getDisponible()));
            prepStmt.setInt(3, Integer.parseInt(mesa.getNumero()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR MESA: " + e.getMessage());
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

    public boolean eliminar(String numero) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM mesas WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(numero));

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR MESA: " + e.getMessage());
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

    public boolean actualizarEstado(Mesa mesa) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE mesas SET disponible = ? WHERE numero = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(mesa.getDisponible()));
            prepStmt.setInt(2, Integer.parseInt(mesa.getNumero()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD MESA: " + e.getMessage());
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
