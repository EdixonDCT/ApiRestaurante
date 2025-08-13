// Define el paquete al que pertenece la clase.
package MODELO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos (JDBC).
import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos del objeto Comida, implementando el patrón DAO.
public class ComidaDAO {

    // Variables a nivel de clase para gestionar la conexión a la base de datos,
    // la sentencia SQL preparada y el conjunto de resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtiene una lista de todas las comidas de la base de datos.
    public ArrayList<Comida> listarTodos() {
        // Inicializa un ArrayList para almacenar los objetos Comida.
        ArrayList<Comida> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos utilizando la clase de conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para seleccionar todos los registros de la tabla 'comidas'.
            String sql = "SELECT * FROM comidas";
            // Prepara la sentencia SQL para su ejecución.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y almacena los resultados en el ResultSet.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto Comida.
                Comida comida = new Comida();
                // Asigna los valores de las columnas a las propiedades del objeto Comida.
                comida.setId(rs.getString("id"));
                comida.setNombre(rs.getString("nombre"));
                comida.setPrecio(rs.getString("precio"));
                comida.setTipo(rs.getString("tipo"));
                comida.setImagen(rs.getString("imagen"));
                comida.setDisponible(rs.getString("disponible"));
                // Agrega el objeto Comida a la lista.
                lista.add(comida);
            }
        } catch (Exception e) {
            // Captura y muestra cualquier error que ocurra durante la consulta.
            System.err.println("ERROR AL LISTAR COMIDAS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque 'finally' para asegurar que todos los recursos se cierren correctamente.
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
                // Manejo de errores al cerrar los recursos.
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna la lista de comidas.
        return lista;
    }

    // MÉTODO: Obtiene un objeto Comida específico por su ID.
    public Comida obtenerPorId(String id) {
        // Inicializa el objeto a null.
        Comida comida = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para buscar por ID, usando un marcador de posición '?'.
            String sql = "SELECT * FROM comidas WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte el ID de String a Int y lo establece como el primer parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si se encuentra una fila...
            if (rs.next()) {
                // ...crea un nuevo objeto Comida y asigna los valores.
                comida = new Comida();
                comida.setId(rs.getString("id"));
                comida.setNombre(rs.getString("nombre"));
                comida.setPrecio(rs.getString("precio"));
                comida.setTipo(rs.getString("tipo"));
                comida.setImagen(rs.getString("imagen"));
                comida.setDisponible(rs.getString("disponible"));
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL OBTENER COMIDA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos en el bloque 'finally'.
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
        // Retorna el objeto Comida o null si no se encontró.
        return comida;
    }

    // MÉTODO: Verifica si un registro de comida con un ID dado existe.
    public boolean existePorId(String id) {
        boolean existe = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta optimizada para verificar la existencia. 'SELECT 1' es más eficiente.
            String sql = "SELECT 1 FROM comidas WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Establece el parámetro ID.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si el ResultSet tiene al menos una fila, el registro existe.
            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL VERIFICAR COMIDA POR ID: " + e.getMessage());
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

        return existe;
    }

    // MÉTODO: Crea un nuevo registro de comida en la base de datos.
    public String[] crear(Comida comida) {
        // Inicializa un array de String para el resultado con valores por defecto en caso de fallo.
        String[] resultado = new String[2];
        resultado[0] = "Reserva: no se pudo crear."; // Mensaje por defecto
        resultado[1] = "-1"; // ID por defecto si falla

        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para insertar un nuevo registro, especificando las columnas.
            String sql = "INSERT INTO comidas (nombre, precio, tipo) VALUES (?, ?, ?)";
            // Prepara la sentencia, solicitando que devuelva las claves generadas (ID).
            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Asigna los valores a los parámetros de la consulta.
            prepStmt.setString(1, comida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(comida.getPrecio()));
            prepStmt.setString(3, comida.getTipo());

            // Ejecuta la inserción y obtiene el número de filas afectadas.
            int filas = prepStmt.executeUpdate();

            // Si se insertó al menos un registro...
            if (filas > 0) {
                // ...obtiene las claves generadas.
                rs = prepStmt.getGeneratedKeys();
                // Si existe un ID generado...
                if (rs.next()) {
                    // ...lo obtiene, lo asigna al objeto y actualiza el array de resultado.
                    int idGenerado = rs.getInt(1);
                    comida.setId(String.valueOf(idGenerado));
                    resultado[0] = "Comida: creado EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL CREAR COMIDA: " + e.getMessage());
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

        return resultado;
    }

    // MÉTODO: Actualiza los campos de nombre, precio y tipo de un registro de comida.
    public boolean actualizar(Comida comida) {
        boolean actualizado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar un registro por su ID.
            String sql = "UPDATE comidas SET nombre = ?, precio = ?, tipo = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los nuevos valores.
            prepStmt.setString(1, comida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(comida.getPrecio()));
            prepStmt.setString(3, comida.getTipo());
            // Asigna el ID para el 'WHERE'.
            prepStmt.setInt(4, Integer.parseInt(comida.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se actualizó al menos una fila.
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
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

        return actualizado;
    }

    // MÉTODO: Elimina un registro de comida de la base de datos por su ID.
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar un registro por su ID.
            String sql = "DELETE FROM comidas WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID del registro a eliminar.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se eliminó al menos una fila.
            eliminado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ELIMINAR COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
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

    // MÉTODO: Actualiza la URL de la imagen de una comida.
    public boolean actualizarImagen(Comida comida) {
        boolean actualizarImagen = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar solo el campo 'imagen' por ID.
            String sql = "UPDATE comidas SET imagen = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna la nueva URL de la imagen.
            prepStmt.setString(1, comida.getImagen());
            // Asigna el ID del registro a actualizar.
            prepStmt.setInt(2, Integer.parseInt(comida.getId()));

            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se actualizó al menos una fila.
            actualizarImagen = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR IMAGEN COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR IMAGEN CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizarImagen;
    }

    // MÉTODO: Actualiza el estado de disponibilidad de un registro de comida.
    public boolean actualizarEstado(Comida comida) {
        boolean actualizado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar solo el campo 'disponible' por ID.
            String sql = "UPDATE comidas SET disponible = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte y asigna el nuevo estado de disponibilidad.
            prepStmt.setInt(1, Integer.parseInt(comida.getDisponible()));
            // Asigna el ID del registro.
            prepStmt.setInt(2, Integer.parseInt(comida.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación es exitosa si se actualizó al menos una fila.
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre de recursos.
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
        return actualizado;
    }
}
