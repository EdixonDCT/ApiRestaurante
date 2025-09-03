// Paquete al que pertenece la clase
package DAO;

// Importa las clases necesarias para la conexión a la base de datos
import BD.DBConnection;
import MODELO.Caja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Define la clase CajaDAO para la persistencia de datos del objeto Caja
public class CajaDAO {

    // Declara variables para la conexión, sentencia preparada y resultado
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // Método para obtener todos los registros de la tabla 'caja'
    public ArrayList<Caja> listarTodos() {
        // Crea un ArrayList para almacenar los objetos Caja
        ArrayList<Caja> lista = new ArrayList<>();

        try {
            // Obtiene la conexión a la base de datos
            conn = DBConnection.getConnection();
            // Define la consulta SQL con un JOIN para obtener el nombre del cajero
            String sql = "SELECT c.*, t.nombre AS nombre_cajero FROM caja c INNER JOIN trabajadores t ON c.cedula_trabajador = t.cedula";
            // Prepara la sentencia SQL
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y obtiene el resultado
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del resultado
            while (rs.next()) {
                // Crea un nuevo objeto Caja
                Caja caja = new Caja();
                // Asigna los valores de las columnas del resultado al objeto Caja
                caja.setId(rs.getString("id"));
                caja.setFechaApertura(rs.getString("fecha_apertura"));
                caja.setHoraApertura(rs.getString("hora_apertura"));
                caja.setMontoApertura(rs.getString("monto_apertura"));
                caja.setFechaCierre(rs.getString("fecha_cierre"));
                caja.setHoraCierre(rs.getString("hora_cierre"));
                caja.setMontoCierre(rs.getString("monto_cierre"));
                caja.setCedulaTrabajador(rs.getString("cedula_trabajador"));
                caja.setNombreCajero(rs.getString("nombre_cajero"));
                // Agrega el objeto Caja a la lista
                lista.add(caja);
            }

        } catch (Exception e) {
            // Manejo de errores: imprime un mensaje y la traza de la excepción
            System.err.println("ERROR AL LISTAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque para cerrar los recursos (ResultSet, PreparedStatement, Connection)
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

        // Retorna la lista de cajas
        return lista;
    }

    // Método para obtener un registro de caja por su ID
    public Caja obtenerPorId(String id) {
        // Inicializa el objeto Caja a null
        Caja caja = null;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL con un JOIN para obtener el nombre del cajero, filtrando por ID
            String sql = "SELECT c.*, t.nombre AS nombre_cajero FROM caja c INNER JOIN trabajadores t ON c.cedula_trabajador = t.cedula WHERE c.id = ?";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna el valor del ID al primer parámetro de la consulta
            prepStmt.setInt(1, Integer.parseInt(id));
            // Ejecuta la consulta
            rs = prepStmt.executeQuery();

            // Si se encuentra un resultado
            if (rs.next()) {
                // Crea un nuevo objeto Caja
                caja = new Caja();
                // Asigna los valores del resultado al objeto
                caja.setId(rs.getString("id"));
                caja.setFechaApertura(rs.getString("fecha_apertura"));
                caja.setHoraApertura(rs.getString("hora_apertura"));
                caja.setMontoApertura(rs.getString("monto_apertura"));
                caja.setFechaCierre(rs.getString("fecha_cierre"));
                caja.setHoraCierre(rs.getString("hora_cierre"));
                caja.setMontoCierre(rs.getString("monto_cierre"));
                caja.setCedulaTrabajador(rs.getString("cedula_trabajador"));
                caja.setNombreCajero(rs.getString("nombre_cajero"));
            }

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL OBTENER CAJA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna el objeto Caja encontrado o null si no existe
        return caja;
    }

    // Método para crear un nuevo registro de caja
    public boolean crear(Caja caja) {
        // Inicializa la variable de éxito a false
        boolean creado = false;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL para insertar una nueva caja
            String sql = "INSERT INTO caja (monto_apertura, cedula_trabajador) VALUES (?, ?)";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setInt(2, Integer.parseInt(caja.getCedulaTrabajador()));

            // Ejecuta la inserción y obtiene el número de filas afectadas
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la creación fue exitosa
            creado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL CREAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna si la creación fue exitosa
        return creado;
    }

    // Método para actualizar un registro de caja
    public boolean actualizar(Caja caja) {
        // Inicializa la variable de éxito a false
        boolean actualizado = false;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar la caja
            String sql = "UPDATE caja SET monto_apertura = ?, monto_cierre = ?, cedula_trabajador = ? WHERE id = ?";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setDouble(2, Double.parseDouble(caja.getMontoCierre()));
            prepStmt.setInt(3, Integer.parseInt(caja.getCedulaTrabajador()));
            prepStmt.setInt(4, Integer.parseInt(caja.getId()));

            // Ejecuta la actualización y obtiene el número de filas afectadas
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna si la actualización fue exitosa
        return actualizado;
    }

    // Método para actualizar todos los campos de una caja
    public boolean actualizarTotal(Caja caja) {
        // Inicializa la variable de éxito a false
        boolean actualizado = false;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar todos los campos de la caja
            String sql = "UPDATE caja SET fecha_apertura = ?, hora_apertura = ?, monto_apertura = ?, fecha_cierre = ?, hora_cierre = ?, monto_cierre = ?, cedula_trabajador = ? WHERE id = ?";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros
            prepStmt.setString(1, caja.getFechaApertura());
            prepStmt.setString(2, caja.getHoraApertura());
            prepStmt.setDouble(3, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setString(4, caja.getFechaCierre());
            prepStmt.setString(5, caja.getHoraCierre());
            prepStmt.setDouble(6, Double.parseDouble(caja.getMontoCierre()));
            prepStmt.setInt(7, Integer.parseInt(caja.getCedulaTrabajador()));
            prepStmt.setInt(8, Integer.parseInt(caja.getId()));

            // Ejecuta la actualización
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna si la actualización fue exitosa
        return actualizado;
    }

    // Método para actualizar solo el monto de apertura de una caja
    public boolean actualizarApertura(Caja caja) {
        // Inicializa la variable de éxito a false
        boolean actualizado = false;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar el monto de apertura
            String sql = "UPDATE caja SET monto_apertura = ? WHERE id = ?";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoApertura()));
            prepStmt.setInt(2, Integer.parseInt(caja.getId()));

            // Ejecuta la actualización
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna si la actualización fue exitosa
        return actualizado;
    }

    // Método para actualizar los datos de cierre de una caja
    public boolean actualizarCierre(Caja caja) {
        // Inicializa la variable de éxito a false
        boolean actualizado = false;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar la fecha, hora y monto de cierre
            // Usa funciones SQL para obtener la fecha y hora actual del servidor
            String sql = "UPDATE caja SET fecha_cierre = CURRENT_DATE(), hora_cierre = CURRENT_TIME(), monto_cierre = ? WHERE id = ?";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna los valores a los parámetros
            prepStmt.setDouble(1, Double.parseDouble(caja.getMontoCierre()));
            prepStmt.setInt(2, Integer.parseInt(caja.getId()));

            // Ejecuta la actualización
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa
            actualizado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL ACTUALIZAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna si la actualización fue exitosa
        return actualizado;
    }

    public Boolean cajaEnPedido(String numero) {
        Boolean mesaPedido = false;
        try {
            conn = DBConnection.getConnection();
            // Consulta SQL que busca por el número de la mesa.
            String sql = "SELECT 1 AS existe FROM pedidos WHERE id_caja = ?";
            prepStmt = conn.prepareStatement(sql);
            // Asigna el número como parámetro, convirtiéndolo a entero.
            prepStmt.setInt(1, Integer.parseInt(numero));
            rs = prepStmt.executeQuery();
            if (rs.next()) { // Si hay al menos una fila
                mesaPedido = true;
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER CAJA POR ID: " + e.getMessage());
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
        return mesaPedido;
    }

    // Método para eliminar un registro de caja por su ID
    public boolean eliminar(String id) {
        // Inicializa la variable de éxito a false
        boolean eliminado = false;

        try {
            // Obtiene la conexión
            conn = DBConnection.getConnection();
            // Define la consulta SQL para eliminar un registro
            String sql = "DELETE FROM caja WHERE id = ?";
            // Prepara la sentencia
            prepStmt = conn.prepareStatement(sql);
            // Asigna el valor del ID al parámetro
            prepStmt.setInt(1, Integer.parseInt(id));

            // Ejecuta la eliminación
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la eliminación fue exitosa
            eliminado = filas > 0;

        } catch (Exception e) {
            // Manejo de errores
            System.err.println("ERROR AL ELIMINAR CAJA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierra los recursos
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

        // Retorna si la eliminación fue exitosa
        return eliminado;
    }

    public Boolean verDisponibleCaja() { // Método para buscar un trabajador por su cédula.
        boolean existe = false; // Inicializa el objeto trabajador como nulo.

        try {
            conn = DBConnection.getConnection(); // Establece la conexión.
            String sql = "SELECT 1 FROM caja WHERE fecha_cierre IS null AND hora_cierre IS null AND monto_cierre IS null"; // Consulta con un parámetro.
            prepStmt = conn.prepareStatement(sql); // Asigna el valor de la cédula al parámetro de la consulta.
            rs = prepStmt.executeQuery();
            if (rs.next()) { // Si hay al menos una fila
                existe = true;
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER MESA POR NUMERO: " + e.getMessage());
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
        return existe; // Devuelve el objeto trabajador o nulo si no se encontró.
    }

    public Boolean CerrarCajaPedidos(String id) {
    boolean existe = false;

    try {
        conn = DBConnection.getConnection(); 
        String sql = "UPDATE pedidos SET facturado = 1 WHERE id_caja = ?";
        prepStmt = conn.prepareStatement(sql);
        prepStmt.setInt(1, Integer.parseInt(id));

        int filas = prepStmt.executeUpdate(); // cuántas filas fueron actualizadas
        existe = filas > 0;

    } catch (Exception e) {
        System.err.println("ERROR AL CERRAR CAJA PEDIDOS: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (prepStmt != null) prepStmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
        }
    }

    return existe; 
}


}
