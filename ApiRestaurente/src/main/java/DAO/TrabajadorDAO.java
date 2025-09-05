package DAO; // Define el paquete del proyecto al que pertenece esta clase.

import BD.DBConnection; // Importa la clase para la conexión a la base de datos.
import MODELO.Usuarios;
import java.sql.Connection; // Importa la clase que gestiona la conexión a la base de datos.
import java.sql.PreparedStatement; // Importa la clase para sentencias SQL precompiladas.
import java.sql.ResultSet; // Importa la clase para manejar los resultados de una consulta.
import java.util.ArrayList; // Importa la clase ArrayList para crear listas de objetos.

public class TrabajadorDAO { // La clase `TrabajadorDAO` (Data Access Object) se encarga de las operaciones con la tabla de trabajadores.

    private Connection conn = null; // Variable para la conexión a la base de datos, inicializada en nulo.
    private PreparedStatement prepStmt = null; // Variable para la sentencia SQL preparada, inicializada en nulo.
    private ResultSet rs = null; // Variable para el conjunto de resultados de la consulta, inicializada en nulo.

    // MÉTODO: Obtener todos los trabajadores de la tabla
    public ArrayList<Usuarios> listarTodos() { // Método que retorna una lista de todos los trabajadores.
        ArrayList<Usuarios> lista = new ArrayList<>(); // Crea una nueva lista para almacenar los objetos Usuarios.

        try { // Bloque try-catch para manejar errores de la base de datos.
            // Conexión a la base de datos
            conn = DBConnection.getConnection(); // Obtiene una conexión a la base de datos.

            // Consulta SQL para seleccionar todos los registros, uniendo la tabla de trabajadores y oficios.
            String sql = "SELECT u.id,u.cedula,u.nombre,u.apellido,u.nacimiento,u.foto,u.contrasena,u.activo,u.eliminado,r.nombre AS nombre_rol FROM usuarios u INNER JOIN rolesUsuarios ru ON u.id=ru.id_usuario INNER JOIN roles r ON ru.id_rol=r.id WHERE ru.id_rol NOT IN (1,2)";
            prepStmt = conn.prepareStatement(sql); // Prepara la sentencia SQL.
            rs = prepStmt.executeQuery(); // Ejecuta la consulta y almacena los resultados.

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) { // Itera a través de cada fila del resultado.
                Usuarios usuario = new Usuarios(); // Crea un nuevo objeto Usuarios.
                usuario.setId(rs.getString("id")); // Asigna el valor de la columna 'id' al objeto.
                usuario.setCedula(rs.getString("cedula")); // Asigna el valor de la columna 'cedula' al objeto.
                usuario.setNombre(rs.getString("nombre")); // Asigna el valor de la columna 'nombre'.
                usuario.setApellido(rs.getString("apellido")); // Asigna el valor de la columna 'apellido'.
                usuario.setNacimiento(rs.getString("nacimiento")); // Asigna el valor de la columna 'nacimiento'.
                usuario.setFoto(rs.getString("foto")); // Asigna el valor de la columna 'foto'.
                usuario.setContrasena(rs.getString("contrasena")); // Asigna el valor de la columna 'contrasena'.
                usuario.setActivo(rs.getString("activo")); // Asigna el valor de la columna 'activo'.
                usuario.setEliminado(rs.getString("eliminado"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                lista.add(usuario); // Agrega el objeto a la lista.
            }

        } catch (Exception e) { // Si ocurre un error, se ejecuta este bloque.
            System.err.println("ERROR AL LISTAR TRABAJADORES: " + e.getMessage()); // Muestra el mensaje de error.
            e.printStackTrace(); // Imprime la traza completa del error para depuración.
        } finally { // Bloque `finally` que se ejecuta siempre.
            // Cerrar conexiones
            try { // Intenta cerrar los recursos de la base de datos.
                if (rs != null) // Si el ResultSet no es nulo, lo cierra.
                {
                    rs.close();
                }
                if (prepStmt != null) // Si el PreparedStatement no es nulo, lo cierra.
                {
                    prepStmt.close();
                }
                if (conn != null) // Si la conexión no es nula, la cierra.
                {
                    conn.close();
                }
            } catch (Exception ex) { // Captura errores al cerrar los recursos.
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage()); // Muestra el error.
            }
        }

        return lista; // Devuelve la lista de trabajadores.
    }
    
    public ArrayList<Usuarios> listarTodosInactivos() { // Método que retorna una lista de todos los trabajadores.
        ArrayList<Usuarios> lista = new ArrayList<>(); // Crea una nueva lista para almacenar los objetos Usuarios.

        try { // Bloque try-catch para manejar errores de la base de datos.
            // Conexión a la base de datos
            conn = DBConnection.getConnection(); // Obtiene una conexión a la base de datos.

            // Consulta SQL para seleccionar todos los registros, uniendo la tabla de trabajadores y oficios.
            String sql = "SELECT u.id,u.cedula,u.nombre,u.apellido,u.nacimiento,u.foto,u.contrasena,u.activo,u.eliminado,r.nombre AS nombre_rol FROM usuarios u INNER JOIN rolesUsuarios ru ON u.id=ru.id_usuario INNER JOIN roles r ON ru.id_rol=r.id WHERE ru.id_rol NOT IN (2,3,4,5) AND u.activo = 0";
            prepStmt = conn.prepareStatement(sql); // Prepara la sentencia SQL.
            rs = prepStmt.executeQuery(); // Ejecuta la consulta y almacena los resultados.

            // Recorrer resultados y agregarlos a la lista
            while (rs.next()) { // Itera a través de cada fila del resultado.
                Usuarios usuario = new Usuarios(); // Crea un nuevo objeto Usuarios.
                usuario.setId(rs.getString("id")); // Asigna el valor de la columna 'id' al objeto.
                usuario.setCedula(rs.getString("cedula")); // Asigna el valor de la columna 'cedula' al objeto.
                usuario.setNombre(rs.getString("nombre")); // Asigna el valor de la columna 'nombre'.
                usuario.setApellido(rs.getString("apellido")); // Asigna el valor de la columna 'apellido'.
                usuario.setNacimiento(rs.getString("nacimiento")); // Asigna el valor de la columna 'nacimiento'.
                usuario.setFoto(rs.getString("foto")); // Asigna el valor de la columna 'foto'.
                usuario.setContrasena(rs.getString("contrasena")); // Asigna el valor de la columna 'contrasena'.
                usuario.setActivo(rs.getString("activo")); // Asigna el valor de la columna 'activo'.
                usuario.setEliminado(rs.getString("eliminado"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                lista.add(usuario); // Agrega el objeto a la lista.
            }

        } catch (Exception e) { // Si ocurre un error, se ejecuta este bloque.
            System.err.println("ERROR AL LISTAR TRABAJADORES: " + e.getMessage()); // Muestra el mensaje de error.
            e.printStackTrace(); // Imprime la traza completa del error para depuración.
        } finally { // Bloque `finally` que se ejecuta siempre.
            // Cerrar conexiones
            try { // Intenta cerrar los recursos de la base de datos.
                if (rs != null) // Si el ResultSet no es nulo, lo cierra.
                {
                    rs.close();
                }
                if (prepStmt != null) // Si el PreparedStatement no es nulo, lo cierra.
                {
                    prepStmt.close();
                }
                if (conn != null) // Si la conexión no es nula, la cierra.
                {
                    conn.close();
                }
            } catch (Exception ex) { // Captura errores al cerrar los recursos.
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage()); // Muestra el error.
            }
        }

        return lista; // Devuelve la lista de trabajadores.
    }

    // MÉTODO: Buscar un Usuarios por su cédula
    public Usuarios obtenerPorCedula(String cedula) { // Método para buscar un trabajador por su cédula.
        Usuarios usuario = null; // Inicializa el objeto trabajador como nulo.

        try {
            conn = DBConnection.getConnection(); // Establece la conexión.
            String sql = "SELECT u.id,u.cedula,u.nombre,u.apellido,u.nacimiento,u.foto,u.contrasena,u.activo,u.eliminado,r.nombre AS nombre_rol FROM usuarios u INNER JOIN rolesUsuarios ru ON u.id=ru.id_usuario INNER JOIN roles r ON ru.id_rol=r.id WHERE ru.id_rol NOT IN (1,2) AND u.cedula = ?"; // Consulta con un parámetro.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula); // Asigna el valor de la cédula al parámetro de la consulta.
            rs = prepStmt.executeQuery();

            if (rs.next()) { // Si se encuentra un registro.
                usuario = new Usuarios(); // Crea un nuevo objeto Usuarios.
                usuario.setId(rs.getString("id")); // Asigna el valor de la columna 'id' al objeto.
                usuario.setCedula(rs.getString("cedula")); // Asigna el valor de la columna 'cedula' al objeto.
                usuario.setNombre(rs.getString("nombre")); // Asigna el valor de la columna 'nombre'.
                usuario.setApellido(rs.getString("apellido")); // Asigna el valor de la columna 'apellido'.
                usuario.setNacimiento(rs.getString("nacimiento")); // Asigna el valor de la columna 'nacimiento'.
                usuario.setFoto(rs.getString("foto")); // Asigna el valor de la columna 'foto'.
                usuario.setContrasena(rs.getString("contrasena")); // Asigna el valor de la columna 'contrasena'.
                usuario.setActivo(rs.getString("activo")); // Asigna el valor de la columna 'activo'.
                usuario.setEliminado(rs.getString("eliminado"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER TRABAJADOR POR CEDULA: " + e.getMessage());
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
        return usuario; // Devuelve el objeto trabajador o nulo si no se encontró.
    }

    public Boolean obtenerPorCedulaBoolean(String cedula) { // Método para buscar un trabajador por su cédula.
        boolean existe = false; // Inicializa el objeto trabajador como nulo.

        try {
            conn = DBConnection.getConnection(); // Establece la conexión.
            String sql = "SELECT 1 FROM usuarios u INNER JOIN rolesUsuarios ru ON u.id = ru.id_usuario WHERE ru.id_rol = 1 AND u.cedula = ?"; // Consulta con un parámetro.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula); // Asigna el valor de la cédula al parámetro de la consulta.
            rs = prepStmt.executeQuery();
            if (rs.next()) { // Si hay al menos una fila
                existe = true;
            }
        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER TRABAJADOR POR CEDULA: " + e.getMessage());
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

    public boolean crear(Usuarios usuario) {
        boolean creado = false;
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sqlUsuario = "INSERT INTO usuarios(cedula, nombre, apellido, nacimiento, contrasena) VALUES (?,?,?,?,?)";
            prepStmt = conn.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);  
            prepStmt.setString(1, usuario.getCedula());
            prepStmt.setString(2, usuario.getNombre());
            prepStmt.setString(3, usuario.getApellido());
            prepStmt.setString(4, usuario.getNacimiento());
            prepStmt.setString(5, usuario.getContrasena());
            int filas = prepStmt.executeUpdate();

            if (filas > 0) {
                rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    int idUsuario = rs.getInt(1);
                    String sqlRol = "INSERT INTO rolesUsuarios(id_rol, id_usuario) VALUES (1,?)";
                    try (PreparedStatement prepStmtRol = conn.prepareStatement(sqlRol)) {
                        prepStmtRol.setInt(1, idUsuario);
                        prepStmtRol.executeUpdate();
                    }
                }
                creado = true;
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR USUARIO: " + e.getMessage());
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
        return creado;
    }

    public String obtenerIdUsuario(String cedula) { // Método para eliminar un trabajador.
        String id = "";

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT u.id FROM usuarios u INNER JOIN rolesUsuarios ru ON u.id = ru.id_usuario WHERE ru.id_rol = 1 AND u.cedula = ?"; // Consulta de eliminación.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, cedula); // Asigna la cédula al parámetro.
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                id = (rs.getString("id"));
            }
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR TRABAJADOR: " + e.getMessage());
            e.printStackTrace();
        } finally {
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
        return id; // Devuelve `true` o `false`.
    }

// MÉTODO: Actualizar un Usuarios existente
    public boolean actualizar(Usuarios usuario) { // Método para actualizar la información de un trabajador.
        boolean actualizado = false; // Variable para saber si la actualización fue exitosa.

        try {
            String id = obtenerIdUsuario(usuario.getCedula());
            conn = DBConnection.getConnection();
            String sql = "UPDATE usuarios SET nombre = ?,apellido = ?,nacimiento = ?,contrasena = ? WHERE id = ?"; // Consulta de actualización.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, usuario.getNombre()); // Asigna los nuevos valores a los parámetros.
            prepStmt.setString(2, usuario.getApellido());
            prepStmt.setString(3, usuario.getNacimiento());
            prepStmt.setString(4, usuario.getContrasena());
            prepStmt.setInt(5, Integer.parseInt(id)); // Usa el ID para identificar el registro.

            int filas = prepStmt.executeUpdate(); // Ejecuta la actualización.
            actualizado = filas > 0; // Verifica si se afectó al menos una fila.

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR TRABAJADOR: " + e.getMessage());
            e.printStackTrace();
        } finally {
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

        return actualizado; // Devuelve `true` o `false`.
    }

    // MÉTODO: Eliminar un trabajador por su cédula
//    public boolean eliminar(String cedula) { // Método para eliminar un trabajador.
//        boolean eliminado = false; // Variable para saber si la eliminación fue exitosa.
//
//        try {
//            conn = DBConnection.getConnection();
//            String sql = "DELETE FROM trabajadores WHERE cedula = ?"; // Consulta de eliminación.
//            prepStmt = conn.prepareStatement(sql);
//            prepStmt.setInt(1, Integer.parseInt(cedula)); // Asigna la cédula al parámetro.
//
//            int filas = prepStmt.executeUpdate(); // Ejecuta la eliminación.
//            eliminado = filas > 0; // Si se afectó al menos una fila, fue exitoso.
//
//        } catch (Exception e) {
//            System.err.println("ERROR AL ELIMINAR TRABAJADOR: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            try {
//                if (prepStmt != null) {
//                    prepStmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (Exception ex) {
//                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
//            }
//        }
//
//        return eliminado; // Devuelve `true` o `false`.
//    }
    // MÉTODO: Actualizar la foto de un Usuarios existente
    public boolean actualizarFoto(Usuarios usuario) { // Método para actualizar solo el campo de la foto.
        boolean actualizarFoto = false; // Variable de estado de la operación.

        try {
            String id = obtenerIdUsuario(usuario.getCedula());
            conn = DBConnection.getConnection();
            String sql = "UPDATE usuarios SET foto = ? WHERE id = ?"; // Consulta para actualizar solo la foto.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, usuario.getFoto()); // Asigna la nueva URL o ruta de la foto.
            prepStmt.setInt(2, Integer.parseInt(id)); // Usa la cédula para el `WHERE`.

            int filas = prepStmt.executeUpdate();
            actualizarFoto = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR FOTO TRABAJADOR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR FOTO CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizarFoto; // Devuelve `true` o `false`.
    }

    public boolean activarTrabajador(Usuarios usuario) { // Método para actualizar el oficio y el estado de un trabajador.
        boolean activar = false; // Variable de estado.

        try {
            String id = obtenerIdUsuario(usuario.getCedula());
            String rol = usuario.getActivo();
            conn = DBConnection.getConnection();
            String sql = "UPDATE usuarios SET activo = 1 WHERE id = ?"; // Consulta para actualizar oficio y estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id)); // Usa el ID para el `WHERE`.

            int filas = prepStmt.executeUpdate();
            activar = filas > 0;
            if (activar) {
                activarDarleRol(id, rol);
            }
        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return activar; // Devuelve `true` o `false`.
    }

    public boolean activarDarleRol(String id, String rol) { // Método para actualizar el oficio y el estado de un trabajador.
        boolean activar = false; // Variable de estado.
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO rolesUsuarios(id_rol,id_usuario) values (?,?)"; // Consulta para actualizar oficio y estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(rol)); // Asigna el nuevo estado (activo/inactivo).
            prepStmt.setInt(2, Integer.parseInt(id)); // Usa la cédula para el `WHERE`.

            int filas = prepStmt.executeUpdate();
            activar = filas > 0;
        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return activar; // Devuelve `true` o `false`.
    }

    public boolean cambiarEstado(Usuarios usuario) { // Método para cambiar el estado de un trabajador.
        boolean activar = false; // Variable de estado.

        try {
            String id = obtenerIdUsuario(usuario.getCedula());
            conn = DBConnection.getConnection();
            String sql = "UPDATE usuarios SET eliminado = ? WHERE id = ?"; // Consulta para actualizar solo el estado.
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(usuario.getEliminado())); // Asigna el nuevo estado.
            prepStmt.setInt(2, Integer.parseInt(id)); // Usa el ID para el `WHERE`.

            int filas = prepStmt.executeUpdate();
            activar = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR ESTADO: " + ex.getMessage());
            }
        }

        return activar; // Devuelve `true` o `false`.
    }
}
