package MODELO; // Define el paquete del proyecto al que pertenece esta clase.

import BD.DBConnection; // Importa la clase para la conexión a la base de datos.
import java.sql.Connection; // Importa la clase que gestiona la conexión a la base de datos.
import java.sql.PreparedStatement; // Importa la clase para sentencias SQL precompiladas.
import java.sql.ResultSet; // Importa la clase para manejar los resultados de una consulta.
import java.util.ArrayList; // Importa la clase ArrayList para crear listas de objetos.

public class TrabajadorDAO { // La clase `TrabajadorDAO` (Data Access Object) se encarga de las operaciones con la tabla de trabajadores.
    private Connection conn = null; // Variable para la conexión a la base de datos, inicializada en nulo.
    private PreparedStatement prepStmt = null; // Variable para la sentencia SQL preparada, inicializada en nulo.
    private ResultSet rs = null; // Variable para el conjunto de resultados de la consulta, inicializada en nulo.

    // MÉTODO: Obtener todos los trabajadores de la tabla
    public ArrayList<Trabajador> listarTodos() { // Método que retorna una lista de todos los trabajadores.
        ArrayList<Trabajador> lista = new ArrayList<>(); // Crea una nueva lista para almacenar los objetos Trabajador.

        try { // Bloque try-catch para manejar errores de la base de datos.
            // Conexión a la base de datos
            conn = DBConnection.getConnection(); // Obtiene una conexión a la base de datos.

            // Consulta SQL para seleccionar todos los registros, uniendo la tabla de trabajadores y oficios.
            String sql = "SELECT t.cedula,t.nombre,t.apellido,t.nacimiento,t.foto,t.contrasena,o.codigo,o.tipo,t.activo,t.adminTemporalInicio,t.adminTemporalFin FROM trabajadores AS t JOIN oficios AS o ON t.id_oficio = o.codigo";
            prepStmt = conn.prepareStatement(sql); // Prepara la sentencia SQL.
            rs = prepStmt.executeQuery(); // Ejecuta la consulta y almacena los resultados.

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) { // Itera a través de cada fila del resultado.
                Trabajador trabajador = new Trabajador(); // Crea un nuevo objeto Trabajador.
                trabajador.setCedula(rs.getString("cedula")); // Asigna el valor de la columna 'cedula' al objeto.
                trabajador.setNombre(rs.getString("nombre")); // Asigna el valor de la columna 'nombre'.
                trabajador.setApellido(rs.getString("apellido")); // Asigna el valor de la columna 'apellido'.
                trabajador.setNacimiento(rs.getString("nacimiento")); // Asigna el valor de la columna 'nacimiento'.
                trabajador.setFoto(rs.getString("foto")); // Asigna el valor de la columna 'foto'.
                trabajador.setContrasena(rs.getString("contrasena")); // Asigna el valor de la columna 'contrasena'.
                trabajador.setIdOficio(rs.getString("codigo")); // Asigna el ID del oficio de la tabla 'oficios'.
                trabajador.setNombreOficio(rs.getString("tipo")); // Asigna el nombre del oficio de la tabla 'oficios'.
                trabajador.setActivo(rs.getString("activo")); // Asigna el valor de la columna 'activo'.
                trabajador.setAdminTemporalInicio(rs.getString("adminTemporalInicio"));//Asigna el valor de la columna 'Admin temporal inicio fecha'
                trabajador.setAdminTemporalFin(rs.getString("adminTemporalFin"));//Asigna el valor de la columna 'Admin temporal fin fecha'
                lista.add(trabajador); // Agrega el objeto a la lista.
            }

        } catch (Exception e) { // Si ocurre un error, se ejecuta este bloque.
            System.err.println("ERROR AL LISTAR TRABAJADORES: " + e.getMessage()); // Muestra el mensaje de error.
            e.printStackTrace(); // Imprime la traza completa del error para depuración.
        } finally { // Bloque `finally` que se ejecuta siempre.
            // Cerrar conexiones
            try { // Intenta cerrar los recursos de la base de datos.
                if (rs != null) // Si el ResultSet no es nulo, lo cierra.
                    rs.close();
                if (prepStmt != null) // Si el PreparedStatement no es nulo, lo cierra.
                    prepStmt.close();
                if (conn != null) // Si la conexión no es nula, la cierra.
                    conn.close();
            } catch (Exception ex) { // Captura errores al cerrar los recursos.
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage()); // Muestra el error.
            }
        }

        return lista; // Devuelve la lista de trabajadores.
    }

    // MÉTODO: Buscar un Trabajador por su cédula
    public Trabajador obtenerPorCedula(String cedula) { // Método para buscar un trabajador por su cédula.
        Trabajador trabajador = null; // Inicializa el objeto trabajador como nulo.

        try {
            conn = DBConnection.getConnection(); // Establece la conexión.
            String sql = "SELECT t.cedula,t.nombre,t.apellido,t.nacimiento,t.foto,t.contrasena,o.codigo,o.tipo,t.activo,t.adminTemporalInicio,t.adminTemporalFin FROM trabajadores AS t JOIN oficios AS o ON t.id_oficio = o.codigo WHERE t.cedula = ?"; // Consulta con un parámetro.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(cedula)); // Asigna el valor de la cédula al parámetro de la consulta.
            rs = prepStmt.executeQuery();

            if (rs.next()) { // Si se encuentra un registro.
                trabajador = new Trabajador(); // Crea un nuevo objeto Trabajador.
                trabajador.setCedula(rs.getString("cedula")); // Asigna los valores de la base de datos al objeto.
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setApellido(rs.getString("apellido"));
                trabajador.setNacimiento(rs.getString("nacimiento"));
                trabajador.setFoto(rs.getString("foto"));
                trabajador.setContrasena(rs.getString("contrasena"));
                trabajador.setIdOficio(rs.getString("codigo"));
                trabajador.setNombreOficio(rs.getString("tipo"));
                trabajador.setActivo(rs.getString("activo"));
                trabajador.setAdminTemporalInicio(rs.getString("adminTemporalInicio"));
                trabajador.setAdminTemporalFin(rs.getString("adminTemporalFin"));
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
        return trabajador; // Devuelve el objeto trabajador o nulo si no se encontró.
    }

    // MÉTODO: Insertar un nuevo trabajador
    public boolean crear(Trabajador trabajador) { // Método para crear un nuevo trabajador.
        boolean creado = false; // Variable para saber si la operación fue exitosa.

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO trabajadores(cedula, nombre, apellido, nacimiento, contrasena, id_oficio) values (?,?,?,?,?,?)"; // Consulta de inserción.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1,Integer.parseInt(trabajador.getCedula())); // Asigna la cédula.
            prepStmt.setString(2, trabajador.getNombre()); // Asigna el nombre.
            prepStmt.setString(3, trabajador.getApellido()); // Asigna el apellido.
            prepStmt.setString(4, trabajador.getNacimiento()); // Asigna la fecha de nacimiento.
            prepStmt.setString(5, trabajador.getContrasena()); // Asigna la contraseña.
            prepStmt.setInt(6,Integer.parseInt(trabajador.getIdOficio())); // Asigna el ID del oficio.

            // Ejecutar inserción y verificar si se insertó al menos una fila
            int filas = prepStmt.executeUpdate(); // Ejecuta la inserción.
            creado = filas > 0; // Si el número de filas afectadas es mayor que 0, fue exitoso.

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
        return creado; // Devuelve `true` si se creó, `false` si falló.
    }

    // MÉTODO: Actualizar un Trabajador existente
    public boolean actualizar(Trabajador trabajador) { // Método para actualizar la información de un trabajador.
        boolean actualizado = false; // Variable para saber si la actualización fue exitosa.

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET nombre = ?, apellido = ?, nacimiento = ?, contrasena = ?, id_oficio = ? WHERE cedula = ?"; // Consulta de actualización.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, trabajador.getNombre()); // Asigna los nuevos valores a los parámetros.
            prepStmt.setString(2, trabajador.getApellido());
            prepStmt.setString(3, trabajador.getNacimiento());
            prepStmt.setString(4, trabajador.getContrasena());
            prepStmt.setInt(5, Integer.parseInt(trabajador.getIdOficio()));
            prepStmt.setInt(6, Integer.parseInt(trabajador.getCedula())); // Usa la cédula para identificar el registro.

            int filas = prepStmt.executeUpdate(); // Ejecuta la actualización.
            actualizado = filas > 0; // Verifica si se afectó al menos una fila.

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

        return actualizado; // Devuelve `true` o `false`.
    }

    // MÉTODO: Eliminar un trabajador por su cédula
    public boolean eliminar(String cedula) { // Método para eliminar un trabajador.
        boolean eliminado = false; // Variable para saber si la eliminación fue exitosa.

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM trabajadores WHERE cedula = ?"; // Consulta de eliminación.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(cedula)); // Asigna la cédula al parámetro.

            int filas = prepStmt.executeUpdate(); // Ejecuta la eliminación.
            eliminado = filas > 0; // Si se afectó al menos una fila, fue exitoso.

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

        return eliminado; // Devuelve `true` o `false`.
    }

    // MÉTODO: Actualizar la foto de un Trabajador existente
    public boolean actualizarFoto(Trabajador trabajador) { // Método para actualizar solo el campo de la foto.
        boolean actualizarFoto = false; // Variable de estado de la operación.

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET foto = ? WHERE cedula = ?"; // Consulta para actualizar solo la foto.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, trabajador.getFoto()); // Asigna la nueva URL o ruta de la foto.
            prepStmt.setInt(2, Integer.parseInt(trabajador.getCedula())); // Usa la cédula para el `WHERE`.

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

        return actualizarFoto; // Devuelve `true` o `false`.
    }
    
    public boolean activarTrabajador(Trabajador trabajador) { // Método para actualizar el oficio y el estado de un trabajador.
        boolean activar = false; // Variable de estado.

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET id_oficio = ?,activo = ? WHERE cedula = ?"; // Consulta para actualizar oficio y estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(trabajador.getIdOficio())); // Asigna el nuevo ID de oficio.
            prepStmt.setInt(2, Integer.parseInt(trabajador.getActivo())); // Asigna el nuevo estado (activo/inactivo).
            prepStmt.setInt(3, Integer.parseInt(trabajador.getCedula())); // Usa la cédula para el `WHERE`.

            int filas = prepStmt.executeUpdate();
            activar = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return activar; // Devuelve `true` o `false`.
    }
    
    public boolean cambiarEstado(Trabajador trabajador) { // Método para cambiar el estado de un trabajador.
        boolean activar = false; // Variable de estado.

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET activo = ? WHERE cedula = ?"; // Consulta para actualizar solo el estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(trabajador.getActivo())); // Asigna el nuevo estado.
            prepStmt.setInt(2, Integer.parseInt(trabajador.getCedula())); // Usa la cédula para el `WHERE`.

            int filas = prepStmt.executeUpdate();
            activar = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return activar; // Devuelve `true` o `false`.
    }
    public boolean activarAdminTemporalTrabajador(Trabajador trabajador) { // Método para actualizar el oficio y el estado de un trabajador.
        boolean activar = false; // Variable de estado.

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET adminTemporalInicio = ?,adminTemporalFin = ? WHERE cedula = ?"; // Consulta para actualizar oficio y estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, trabajador.getAdminTemporalInicio());
            prepStmt.setString(2,trabajador.getAdminTemporalFin());
            prepStmt.setString(3, trabajador.getCedula()); // Asigna el nuevo ID de oficio.
            int filas = prepStmt.executeUpdate();
            activar = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return activar; // Devuelve `true` o `false`.
    }
    public boolean desactivarAdminTemporalTrabajador(Trabajador trabajador) { // Método para actualizar el oficio y el estado de un trabajador.
        boolean desactivar = false; // Variable de estado.

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE trabajadores SET adminTemporalInicio = null,adminTemporalFin = null,activo = 0 WHERE cedula = ?"; // Consulta para actualizar oficio y estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, trabajador.getCedula()); // Asigna el nuevo ID de oficio.
            int filas = prepStmt.executeUpdate();
            desactivar = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return desactivar; // Devuelve `true` o `false`.
}
}