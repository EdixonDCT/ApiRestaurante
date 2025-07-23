package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClienteDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtener todos los clientes de la tabla
    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            // Conexión a la base de datos
            conn = DBConnection.getConnection();

            // Consulta SQL para seleccionar todos los registros
            String sql = "SELECT * FROM clientes";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCorreo(rs.getString("correo"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
                lista.add(cliente);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR CLIENTES: " + e.getMessage());
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
    public Cliente obtenerPorCorreo(String correo) {
        Cliente cliente = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM clientes WHERE correo = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, correo);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCorreo(rs.getString("correo"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER CLIENTE POR CORREO: " + e.getMessage());
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

        return cliente;
    }

    // MÉTODO: Insertar un nuevo oficio
    public boolean crear(Cliente cliente) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO clientes (correo,cedula,telefono) VALUES (?, ?,?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cliente.getCorreo());
            prepStmt.setString(2, cliente.getCedula());
            prepStmt.setString(3, cliente.getTelefono());

            // Ejecutar inserción y verificar si se insertó al menos una fila
            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR CLIENTE: " + e.getMessage());
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
    public boolean actualizar(Cliente cliente) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE clientes SET cedula = ?, telefono = ? WHERE correo = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cliente.getCedula());
            prepStmt.setString(2, cliente.getTelefono());
            prepStmt.setString(3, cliente.getCorreo());
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR CLIENTE: " + e.getMessage());
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

    public boolean eliminar(String correo) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM clientes WHERE correo = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, correo);

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR CLIENTE: " + e.getMessage());
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
