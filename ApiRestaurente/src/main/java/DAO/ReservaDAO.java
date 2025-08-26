package DAO; // Define el paquete del proyecto donde se encuentra esta clase.

import BD.DBConnection; // Importa la clase de conexión a la base de datos.
import MODELO.Reserva;
import java.sql.Connection; // Importa la clase para manejar la conexión con la base de datos.
import java.sql.PreparedStatement; // Importa la clase para sentencias SQL precompiladas y seguras.
import java.sql.ResultSet; // Importa la clase para manejar los resultados de las consultas.
import java.util.ArrayList; // Importa la clase ArrayList para crear listas de objetos.

public class ReservaDAO { // La clase `ReservaDAO` (Data Access Object) se encarga de las operaciones CRUD para las reservas.
    private Connection conn = null; // Variable para la conexión a la base de datos.
    private PreparedStatement prepStmt = null; // Variable para la sentencia SQL preparada.
    private ResultSet rs = null; // Variable para almacenar los resultados de la consulta.

    public ArrayList<Reserva> listarTodos() { // Método para obtener todas las reservas de la base de datos.
        ArrayList<Reserva> lista = new ArrayList<>(); // Crea una lista vacía para guardar las reservas.
        try { // Bloque try para manejar excepciones de la base de datos.
            conn = DBConnection.getConnection(); // Obtiene una nueva conexión a la base de datos.
            String sql = "SELECT * FROM reservas"; // Define la consulta SQL para seleccionar todas las reservas.
            prepStmt = conn.prepareStatement(sql); // Prepara la sentencia SQL para su ejecución.
            rs = prepStmt.executeQuery(); // Ejecuta la consulta y guarda los resultados en `rs`.
            while (rs.next()) { // Itera sobre cada fila del resultado.
                Reserva reserva = new Reserva(); // Crea un nuevo objeto `Reserva`.
                reserva.setId(rs.getString("id")); // Asigna el valor del campo "id".
                reserva.setCantidadTentativa(rs.getString("cantidad_tentativa")); // Asigna el valor del campo "cantidad_tentativa".
                reserva.setPrecio(rs.getString("precio")); // Asigna el valor del campo "precio".
                reserva.setFecha(rs.getString("fecha")); // Asigna el valor del campo "fecha".
                reserva.setFechaTentativa(rs.getString("fecha_tentativa")); // Asigna el valor del campo "fecha_tentativa".
                reserva.setHoraTentativa(rs.getString("hora_tentativa")); // Asigna el valor del campo "hora_tentativa".
                lista.add(reserva); // Agrega el objeto `Reserva` a la lista.
            }
        } catch (Exception e) { // Captura cualquier excepción que ocurra durante la operación.
            System.err.println("ERROR AL LISTAR RESERVAS: " + e.getMessage()); // Imprime el error.
        } finally { // Bloque `finally` que siempre se ejecuta, con o sin error.
            try { // Intenta cerrar los recursos de la base de datos.
                if (rs != null) rs.close(); // Cierra el `ResultSet`.
                if (prepStmt != null) prepStmt.close(); // Cierra el `PreparedStatement`.
                if (conn != null) conn.close(); // Cierra la `Connection`.
            } catch (Exception ex) { // Captura si hay un error al cerrar los recursos.
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage()); // Imprime el error.
            }
        }
        return lista; // Devuelve la lista de reservas.
    }

    public Reserva obtenerPorId(String id) { // Método para buscar una reserva por su ID.
        Reserva reserva = null; // Inicializa la reserva como nula.
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM reservas WHERE id = ?"; // Consulta SQL con un parámetro.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id)); // Asigna el ID al primer parámetro.
            rs = prepStmt.executeQuery();
            if (rs.next()) { // Si se encuentra un registro.
                reserva = new Reserva(); // Crea un nuevo objeto `Reserva`.
                reserva.setId(rs.getString("id")); // Asigna los valores del resultado al objeto.
                reserva.setCantidadTentativa(rs.getString("cantidad_tentativa"));
                reserva.setPrecio(rs.getString("precio"));
                reserva.setFecha(rs.getString("fecha"));
                reserva.setFechaTentativa(rs.getString("fecha_tentativa"));
                reserva.setHoraTentativa(rs.getString("hora_tentativa"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER RESERVA POR ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return reserva; // Devuelve el objeto `Reserva` o `null`.
    }

    public String[] crear(Reserva reserva) { // Método para insertar una nueva reserva.
        String[] resultado = new String[2]; // Array para guardar el mensaje y el ID.
        resultado[0] = "Reserva: no se pudo crear."; // Mensaje de fallo por defecto.
        resultado[1] = "-1"; // ID por defecto en caso de fallo.
        
        Connection conn = null; // Declaración local de las variables de conexión.
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO reservas (cantidad_tentativa,precio, fecha_tentativa, hora_tentativa) VALUES (?,?, ?, ?)"; // Consulta de inserción.
            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // Pide que devuelva las claves generadas (el ID).
            prepStmt.setInt(1, Integer.parseInt(reserva.getCantidadTentativa())); // Asigna los valores al `PreparedStatement`.
            prepStmt.setDouble(2, Double.parseDouble(reserva.getPrecio()));
            prepStmt.setString(3, reserva.getFechaTentativa());
            prepStmt.setString(4, reserva.getHoraTentativa());
            
            int filas = prepStmt.executeUpdate(); // Ejecuta la inserción y obtiene el número de filas afectadas.
            
            if (filas > 0) { // Si se insertó al menos una fila.
                rs = prepStmt.getGeneratedKeys(); // Obtiene las claves generadas.
                if (rs.next()) {
                    int idGenerado = rs.getInt(1); // Obtiene el primer ID generado.
                    reserva.setId(String.valueOf(idGenerado)); // Asigna el nuevo ID al objeto.
                    resultado[0] = "Reserva: creado EXITOSAMENTE"; // Actualiza el mensaje de éxito.
                    resultado[1] = String.valueOf(idGenerado); // Actualiza el ID en el resultado.
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR RESERVA: " + e.getMessage());
            e.printStackTrace(); // Imprime el seguimiento del error para depuración.
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

        return resultado; // Devuelve el resultado de la operación.
    }

    public boolean actualizar(Reserva reserva) { // Método para actualizar una reserva existente.
        boolean actualizado = false; // Variable para saber si la actualización fue exitosa.
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE reservas SET cantidad_tentativa = ?,precio = ?, fecha_tentativa = ?, hora_tentativa = ? WHERE id = ?"; // Consulta de actualización.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(reserva.getCantidadTentativa())); // Asigna los nuevos valores.
            prepStmt.setDouble(2, Double.parseDouble(reserva.getPrecio()));
            prepStmt.setString(3, reserva.getFechaTentativa());
            prepStmt.setString(4, reserva.getHoraTentativa());
            prepStmt.setInt(5, Integer.parseInt(reserva.getId())); // Usa el ID para encontrar la reserva a actualizar.
            int filas = prepStmt.executeUpdate(); // Ejecuta la actualización.
            actualizado = filas > 0; // Si se afectó al menos una fila, la actualización fue exitosa.
        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR RESERVA: " + e.getMessage());
        } finally {
            try {
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return actualizado; // Devuelve true o false.
    }

    public boolean eliminar(String id) { // Método para eliminar una reserva por su ID.
        boolean eliminado = false; // Variable para saber si la eliminación fue exitosa.
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM reservas WHERE id = ?"; // Consulta de eliminación.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id)); // Asigna el ID de la reserva a eliminar.
            int filas = prepStmt.executeUpdate(); // Ejecuta la eliminación.
            eliminado = filas > 0; // Si se afectó al menos una fila, la eliminación fue exitosa.
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR RESERVA: " + e.getMessage());
        } finally {
            try {
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }
        return eliminado; // Devuelve true o false.
    }
}