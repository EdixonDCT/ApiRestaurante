package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrabajadorDAO {
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtener todos los trabajadores de la tabla
    public ArrayList<Trabajador> listarTodos() {
        ArrayList<Trabajador> lista = new ArrayList<>();

        try {
            // Conexión a la base de datos
            conn = DBConnection.getConnection();

            // Consulta SQL para seleccionar todos los registros
            String sql = "SELECT t.cedula,t.nombre,t.apellido,t.nacimiento,t.foto,t.contrasena,o.codigo,o.tipo FROM trabajadores AS t JOIN oficios AS o ON t.id_oficio = o.codigo";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) {
                Trabajador trabajador = new Trabajador();
                trabajador.setCedula(rs.getInt("cedula"));
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setApellido(rs.getString("apellido"));
                trabajador.setNacimiento(rs.getString("nacimiento"));
                trabajador.setFoto(rs.getString("foto"));
                trabajador.setContrasena(rs.getString("contrasena"));
                trabajador.setIdOficio(rs.getInt("codigo"));
                trabajador.setNombreOficio(rs.getString("tipo"));
                lista.add(trabajador);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR TRABAJADORES: " + e.getMessage());
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

    // MÉTODO: Buscar un Trabajador por su ID
    public Trabajador obtenerPorCedula(int cedula) {
        Trabajador trabajador = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT t.cedula,t.nombre,t.apellido,t.nacimiento,t.foto,t.contrasena,o.codigo,o.tipo FROM trabajadores AS t JOIN oficios AS o ON t.id_oficio = o.codigo WHERE t.cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, cedula);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                trabajador = new Trabajador();
                trabajador.setCedula(rs.getInt("cedula"));
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setApellido(rs.getString("apellido"));
                trabajador.setNacimiento(rs.getString("nacimiento"));
                trabajador.setFoto(rs.getString("foto"));
                trabajador.setContrasena(rs.getString("contrasena"));
                trabajador.setIdOficio(rs.getInt("codigo"));
                trabajador.setNombreOficio(rs.getString("tipo"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER TRABAJADOR POR CEDULA: " + e.getMessage());
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
        return trabajador;
    }

    // MÉTODO: Insertar un nuevo oficio
    public boolean crear(Trabajador trabajador) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO trabajadores(cedula, nombre, apellido, nacimiento, contrasena, id_oficio) values (?,?,?,?,?,?)";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, trabajador.getCedula());
            prepStmt.setString(2, trabajador.getNombre());
            prepStmt.setString(3, trabajador.getApellido());
            prepStmt.setString(4, trabajador.getNacimiento());
            prepStmt.setString(5, trabajador.getContrasena());
            prepStmt.setInt(6, trabajador.getIdOficio());

            // Ejecutar inserción y verificar si se insertó al menos una fila
            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR TRABAJADOR: " + e.getMessage());
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

    // MÉTODO: Actualizar un Trabajador existente
    public boolean actualizar(Trabajador trabajador) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET nombre = ?, apellido = ?, nacimiento = ?, contrasena = ?, id_oficio = ? WHERE cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, trabajador.getNombre());
            prepStmt.setString(2, trabajador.getApellido());
            prepStmt.setString(3, trabajador.getNacimiento());
            prepStmt.setString(4, trabajador.getContrasena());
            prepStmt.setInt(5, trabajador.getIdOficio());
            prepStmt.setInt(6, trabajador.getCedula());

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR TRABAJADOR: " + e.getMessage());
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

    // MÉTODO: Eliminar un oficio por su código
    public boolean eliminar(int cedula) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM trabajadores WHERE cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, cedula);

            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR TRABAJADOR: " + e.getMessage());
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

    // MÉTODO: Actualizar un Trabajador existente
    public boolean actualizarFoto(Trabajador trabajador) {
        boolean actualizarFoto = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET foto = ? WHERE cedula = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, trabajador.getFoto());
            prepStmt.setInt(2, trabajador.getCedula());

            int filas = prepStmt.executeUpdate();
            actualizarFoto = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR FOTO TRABAJADOR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR FOTO CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizarFoto;
    }
}
