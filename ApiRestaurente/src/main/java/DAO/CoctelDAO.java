// Define el paquete al que pertenece la clase.
package DAO;

// Importa las clases necesarias para la conexión y el manejo de la base de datos.
import BD.DBConnection;
import MODELO.Coctel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos del objeto Coctel (DAO - Data Access Object).
public class CoctelDAO {

    // Declaración de variables a nivel de clase para gestionar la conexión,
    // la sentencia SQL preparada y el conjunto de resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtiene una lista de todos los cócteles de la base de datos.
    public ArrayList<Coctel> listarTodos() {
        // Inicializa un ArrayList para almacenar los objetos Coctel.
        ArrayList<Coctel> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para seleccionar todos los registros de la tabla 'cocteles'.
            String sql = "SELECT * FROM cocteles";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y almacena los resultados.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del conjunto de resultados.
            while (rs.next()) {
                // Crea un nuevo objeto Coctel por cada fila.
                Coctel coctel = new Coctel();
                // Asigna los valores de las columnas a las propiedades del objeto.
                coctel.setId(rs.getString("id"));
                coctel.setNombre(rs.getString("nombre"));
                coctel.setPrecio(rs.getString("precio"));
                coctel.setImagen(rs.getString("imagen"));
                coctel.setDisponible(rs.getString("disponible"));
                // Agrega el objeto Coctel a la lista.
                lista.add(coctel);
            }
        } catch (Exception e) {
            // Captura y muestra cualquier error que ocurra durante la consulta.
            System.err.println("ERROR AL LISTAR COCTELES: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque 'finally' para asegurar el cierre de todos los recursos (ResultSet, PreparedStatement, Connection).
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
                // Captura y muestra errores al intentar cerrar los recursos.
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna la lista de cócteles.
        return lista;
    }

    // MÉTODO: Obtiene un cóctel específico por su ID.
    public Coctel obtenerPorId(String id) {
        // Inicializa el objeto a null.
        Coctel coctel = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para buscar por ID.
            String sql = "SELECT * FROM cocteles WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte el ID de String a Int y lo establece como parámetro.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si se encuentra una fila...
            if (rs.next()) {
                // ...crea un nuevo objeto Coctel y asigna los valores.
                coctel = new Coctel();
                coctel.setId(rs.getString("id"));
                coctel.setNombre(rs.getString("nombre"));
                coctel.setPrecio(rs.getString("precio"));
                coctel.setImagen(rs.getString("imagen"));
                coctel.setDisponible(rs.getString("disponible"));
            }

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL OBTENER COCTEL POR ID: " + e.getMessage());
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

        // Retorna el objeto Coctel encontrado o null si no existe.
        return coctel;
    }

    // MÉTODO: Verifica si un cóctel con el ID dado existe en la base de datos.
    public boolean existeCoctelPorId(String id) {
        boolean existe = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta optimizada para verificar existencia: 'SELECT 1' es más eficiente que 'SELECT *'.
            String sql = "SELECT 1 FROM cocteles WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte y establece el ID.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si el ResultSet tiene al menos una fila, el cóctel existe.
            if (rs.next()) {
                existe = true;
            }

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL VERIFICAR COCTEL POR ID: " + e.getMessage());
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

    // MÉTODO: Crea un nuevo cóctel y retorna el mensaje de resultado y el ID generado.
    public String[] crear(Coctel coctel) {
        // Inicializa un array para el resultado con mensajes por defecto.
        String[] resultado = new String[2];
        resultado[0] = "Reserva: no se pudo crear.";
        resultado[1] = "-1";

        // Declaración de variables locales para la conexión y recursos.
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para insertar.
            String sql = "INSERT INTO cocteles (nombre, precio) VALUES (?, ?)";
            // Prepara la sentencia, indicando que se deben retornar las claves generadas.
            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Asigna los valores a los parámetros.
            prepStmt.setString(1, coctel.getNombre());
            // Convierte el precio de String a Double.
            prepStmt.setDouble(2, Double.parseDouble(coctel.getPrecio()));

            // Ejecuta la inserción y obtiene el número de filas afectadas.
            int filas = prepStmt.executeUpdate();

            // Si se insertó al menos una fila...
            if (filas > 0) {
                // ...obtiene el conjunto de claves generadas.
                rs = prepStmt.getGeneratedKeys();
                // Si hay una clave generada...
                if (rs.next()) {
                    // ...obtiene el ID y lo asigna al objeto Coctel y al array de resultado.
                    int idGenerado = rs.getInt(1);
                    coctel.setId(String.valueOf(idGenerado));
                    resultado[0] = "Coctel: creado EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL CREAR COCTEL: " + e.getMessage());
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

    // MÉTODO: Actualiza el nombre y el precio de un cóctel.
    public boolean actualizar(Coctel coctel) {
        boolean actualizado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar por ID.
            String sql = "UPDATE cocteles SET nombre = ?, precio = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna los nuevos valores de nombre y precio.
            prepStmt.setString(1, coctel.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(coctel.getPrecio()));
            // Asigna el ID para identificar el registro a actualizar.
            prepStmt.setInt(3, Integer.parseInt(coctel.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación fue exitosa si se afectó al menos una fila.
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR COCTEL: " + e.getMessage());
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

    // MÉTODO: Elimina un cóctel por su ID.
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para eliminar por ID.
            String sql = "DELETE FROM cocteles WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el ID a eliminar.
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // La operación fue exitosa si se afectó al menos una fila.
            eliminado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ELIMINAR COCTEL: " + e.getMessage());
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

    // MÉTODO: Actualiza la URL de la imagen de un cóctel.
    public boolean actualizarImagen(Coctel coctel) {
        boolean actualizarImagen = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar la columna 'imagen' por ID.
            String sql = "UPDATE cocteles SET imagen = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Asigna el nuevo valor de la imagen.
            prepStmt.setString(1, coctel.getImagen());
            // Asigna el ID del cóctel.
            prepStmt.setInt(2, Integer.parseInt(coctel.getId()));

            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación fue exitosa si se afectó al menos una fila.
            actualizarImagen = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR IMAGEN COCTEL: " + e.getMessage());
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

    // MÉTODO: Actualiza el estado de disponibilidad de un cóctel.
    public boolean actualizarEstado(Coctel coctel) {
        boolean actualizado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Consulta SQL para actualizar la columna 'disponible' por ID.
            String sql = "UPDATE cocteles SET disponible = ? WHERE id = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Convierte y asigna el nuevo estado de disponibilidad.
            prepStmt.setInt(1, Integer.parseInt(coctel.getDisponible()));
            // Asigna el ID del cóctel.
            prepStmt.setInt(2, Integer.parseInt(coctel.getId()));
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // La operación fue exitosa si se afectó al menos una fila.
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD COCTEL: " + e.getMessage());
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
