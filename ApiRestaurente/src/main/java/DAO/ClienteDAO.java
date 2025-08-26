// Define el paquete al que pertenece la clase.
package DAO;

// Importa las clases necesarias para la conexión y manejo de la base de datos.
import BD.DBConnection;
import MODELO.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// Clase para la persistencia de datos del objeto Cliente (DAO - Data Access Object).
public class ClienteDAO {
    // Declaración de variables a nivel de clase para gestionar la conexión, la sentencia SQL y los resultados.
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    // MÉTODO: Obtiene todos los clientes de la tabla 'clientes' de la base de datos.
    public ArrayList<Cliente> listarTodos() {
        // Inicializa una lista para almacenar los objetos Cliente.
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            // Establece la conexión a la base de datos.
            conn = DBConnection.getConnection();

            // Define la consulta SQL para seleccionar todos los registros.
            String sql = "SELECT * FROM clientes";
            // Prepara la sentencia SQL.
            prepStmt = conn.prepareStatement(sql);
            // Ejecuta la consulta y almacena los resultados en un ResultSet.
            rs = prepStmt.executeQuery();

            // Itera sobre cada fila del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto Cliente por cada fila.
                Cliente cliente = new Cliente();
                // Asigna los valores de las columnas a las propiedades del objeto Cliente.
                cliente.setCorreo(rs.getString("correo"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
                // Agrega el objeto Cliente a la lista.
                lista.add(cliente);
            }
        } catch (Exception e) {
            // Captura y maneja cualquier excepción que ocurra durante el proceso.
            System.err.println("ERROR AL LISTAR CLIENTES: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque 'finally' para asegurar que los recursos de la base de datos se cierren correctamente.
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        // Retorna la lista de clientes.
        return lista;
    }

    // MÉTODO: Obtiene un cliente específico por su dirección de correo electrónico.
    public Cliente obtenerPorCorreo(String correo) {
        // Inicializa el objeto Cliente a null, que se retornará si no se encuentra.
        Cliente cliente = null;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para buscar por correo electrónico.
            // Se utiliza '?' como placeholder para evitar inyecciones SQL.
            String sql = "SELECT * FROM clientes WHERE correo = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Establece el valor del correo en el primer parámetro de la consulta.
            prepStmt.setString(1, correo);
            // Ejecuta la consulta.
            rs = prepStmt.executeQuery();

            // Si se encuentra un registro (el ResultSet no está vacío)...
            if (rs.next()) {
                // ...crea un nuevo objeto Cliente y asigna los valores.
                cliente = new Cliente();
                cliente.setCorreo(rs.getString("correo"));
                cliente.setCedula(rs.getString("cedula"));
                cliente.setTelefono(rs.getString("telefono"));
            }
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL OBTENER CLIENTE POR CORREO: " + e.getMessage());
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

        // Retorna el objeto Cliente encontrado o null.
        return cliente;
    }

    // MÉTODO: Inserta un nuevo cliente en la base de datos.
    public boolean crear(Cliente cliente) {
        // Bandera para indicar si la operación fue exitosa.
        boolean creado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para insertar un nuevo registro.
            String sql = "INSERT INTO clientes (correo, cedula, telefono) VALUES (?, ?, ?)";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Establece los valores de las propiedades del cliente en los parámetros de la consulta.
            prepStmt.setString(1, cliente.getCorreo());
            prepStmt.setString(2, cliente.getCedula());
            prepStmt.setString(3, cliente.getTelefono());

            // Ejecuta la inserción y obtiene el número de filas afectadas.
            int filas = prepStmt.executeUpdate();
            // Si se insertó al menos una fila, la operación fue exitosa.
            creado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL CREAR CLIENTE: " + e.getMessage());
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

        // Retorna el resultado de la operación.
        return creado;
    }

    // MÉTODO: Actualiza los datos de un cliente existente.
    public boolean actualizar(Cliente cliente) {
        // Bandera para indicar si la operación fue exitosa.
        boolean actualizado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para actualizar la cédula y el teléfono del cliente.
            // La actualización se realiza utilizando el correo como identificador único.
            String sql = "UPDATE clientes SET cedula = ?, telefono = ? WHERE correo = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Establece los nuevos valores para la cédula y el teléfono.
            prepStmt.setString(1, cliente.getCedula());
            prepStmt.setString(2, cliente.getTelefono());
            // Establece el correo del cliente a actualizar.
            prepStmt.setString(3, cliente.getCorreo());
            // Ejecuta la actualización.
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la actualización fue exitosa.
            actualizado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ACTUALIZAR CLIENTE: " + e.getMessage());
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

        // Retorna el resultado de la operación.
        return actualizado;
    }

    // MÉTODO: Elimina un cliente por su dirección de correo electrónico.
    public boolean eliminar(String correo) {
        // Bandera para indicar si la operación fue exitosa.
        boolean eliminado = false;

        try {
            // Establece la conexión.
            conn = DBConnection.getConnection();
            // Define la consulta SQL para eliminar un registro.
            String sql = "DELETE FROM clientes WHERE correo = ?";
            // Prepara la sentencia.
            prepStmt = conn.prepareStatement(sql);
            // Establece el correo del cliente a eliminar.
            prepStmt.setString(1, correo);

            // Ejecuta la eliminación.
            int filas = prepStmt.executeUpdate();
            // Si se afectó al menos una fila, la eliminación fue exitosa.
            eliminado = filas > 0;
        } catch (Exception e) {
            // Manejo de errores.
            System.err.println("ERROR AL ELIMINAR CLIENTE: " + e.getMessage());
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

        // Retorna el resultado de la operación.
        return eliminado;
    }
}
