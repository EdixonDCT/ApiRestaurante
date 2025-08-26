// Define el paquete al que pertenece la clase.
package DAO;

// Importa las clases necesarias para trabajar con la base de datos.
import BD.DBConnection;
import MODELO.Bebida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos del objeto Bebida (DAO - Data Access Object).
public class BebidaDAO {
    // Declaración de variables para la conexión, sentencia preparada y resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // Método para obtener todas las bebidas de la base de datos.
    public ArrayList<Bebida> listarTodos() {
        // Crea una lista para almacenar los objetos Bebida.
        ArrayList<Bebida> lista = new ArrayList<>();

        try {
            // Obtiene la conexión a la base de datos.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para seleccionar todos los registros de la tabla 'bebidas'.
            String sql = "SELECT * FROM bebidas";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene el conjunto de resultados.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del resultado.
            while (rs.next()) {
                // Crea un nuevo objeto Bebida.
                Bebida bebida = new Bebida();
                // Asigna los valores de las columnas del resultado al objeto Bebida.
                bebida.setId(rs.getString("id"));
                bebida.setNombre(rs.getString("nombre"));
                bebida.setPrecio(rs.getString("precio"));
                bebida.setUnidad(rs.getString("unidad"));
                bebida.setTipo(rs.getString("tipo"));
                bebida.setImagen(rs.getString("imagen"));
                bebida.setDisponible(rs.getString("disponible"));
                // Agrega el objeto a la lista.
                lista.add(bebida);
            }
        } catch (Exception e) {
            // Manejo de errores: imprime un mensaje de error y la traza de la excepción.
            System.err.println("ERROR AL LISTAR BEBIDAS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque finally para asegurar que los recursos (ResultSet, PreparedStatement, Connection) se cierren.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        // Retorna la lista de bebidas.
        return lista;
    }

    // Método para obtener una bebida por su ID.
    public Bebida obtenerPorId(String id) {
        // Inicializa el objeto Bebida a null.
        Bebida bebida = null;

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para seleccionar una bebida por su ID.
            String sql = "SELECT * FROM bebidas WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID al primer parámetro de la consulta.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta y obtiene el resultado.
            rs = prepStmt.executeQuery();

            // Si encuentra un registro, crea un objeto Bebida y le asigna los valores.
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
            // Manejo de errores.
            System.err.println("ERROR AL OBTENER BEBIDA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna el objeto Bebida o null si no se encontró.
        return bebida;
    }

    // Método para verificar si una bebida existe por su ID.
    public boolean existeBebidaPorId(String id) {
        // Inicializa la variable de existencia a false.
        boolean existe = false;

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define una consulta optimizada que solo verifica la existencia de un registro.
            String sql = "SELECT 1 FROM bebidas WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID al parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si el resultado tiene una fila, la bebida existe.
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL VERIFICAR BEBIDA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna el resultado de la verificación.
        return existe;
    }

    // Método para crear una nueva bebida.
    public String[] crear(Bebida bebida) {
        // Crea un array para el resultado (mensaje y ID generado).
        String[] resultado = new String[2];
        resultado[0] = "Reserva: no se pudo crear."; // Mensaje por defecto.
        resultado[1] = "-1"; // ID por defecto.

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para insertar una nueva bebida.
            // Se usa PreparedStatement.RETURN_GENERATED_KEYS para obtener el ID auto-generado.
            String sql = "INSERT INTO bebidas (nombre, precio, unidad, tipo) VALUES (?, ?, ?, ?)";
            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Asigna los valores a los parámetros.
            prepStmt.setString(1, bebida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(bebida.getPrecio()));
            prepStmt.setString(3, bebida.getUnidad());
            prepStmt.setString(4, bebida.getTipo());

            // Ejecuta la inserción y obtiene el número de filas afectadas.
            int filas = prepStmt.executeUpdate();

            // Si la inserción fue exitosa, obtiene el ID generado.
            if (filas > 0) {
                rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    bebida.setId(String.valueOf(idGenerado)); // Asigna el ID al objeto Bebida.
                    resultado[0] = "Bebida: creada EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL CREAR BEBIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
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

        // Retorna el resultado.
        return resultado;
    }

    // Método para actualizar una bebida.
    public boolean actualizar(Bebida bebida) {
        // Inicializa la variable de éxito a false.
        boolean actualizado = false;

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar los campos.
            String sql = "UPDATE bebidas SET nombre = ?, precio = ?, unidad = ?, tipo = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros.
            prepStmt.setString(1, bebida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(bebida.getPrecio()));
            prepStmt.setString(3, bebida.getUnidad());
            prepStmt.setString(4, bebida.getTipo());
            prepStmt.setInt(5, Integer.parseInt(bebida.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa.
            actualizado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR BEBIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna si la actualización fue exitosa.
        return actualizado;
    }

    // Método para eliminar una bebida por su ID.
    public boolean eliminar(String id) {
        // Inicializa la variable de éxito a false.
        boolean eliminado = false;

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para eliminar un registro.
            String sql = "DELETE FROM bebidas WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID al parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la eliminación fue exitosa.
            eliminado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ELIMINAR BEBIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna si la eliminación fue exitosa.
        return eliminado;
    }

    // Método para actualizar la imagen de una bebida.
    public boolean actualizarImagen(Bebida bebida) {
        // Inicializa la variable de éxito a false.
        boolean actualizarImagen = false;

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar la imagen.
            String sql = "UPDATE bebidas SET imagen = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros.
            prepStmt.setString(1, bebida.getImagen());
            prepStmt.setInt(2, Integer.parseInt(bebida.getId()));

            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa.
            actualizarImagen = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR IMAGEN BEBIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR IMAGEN CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna si la actualización de la imagen fue exitosa.
        return actualizarImagen;
    }

    // Método para actualizar la disponibilidad de una bebida.
    public boolean actualizarEstado(Bebida bebida) {
        // Inicializa la variable de éxito a false.
        boolean actualizado = false;

        try {
            // Obtiene la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar el estado de disponibilidad.
            String sql = "UPDATE bebidas SET disponible = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros.
            prepStmt.setInt(1, Integer.parseInt(bebida.getDisponible()));
            prepStmt.setInt(2, Integer.parseInt(bebida.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa.
            actualizado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD BEBIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna si la actualización del estado fue exitosa.
        return actualizado;
    }
}
