package BD; // Define el paquete donde se encuentra la clase

import java.sql.Connection; // Importa la interfaz para manejar conexiones a bases de datos
import java.sql.DriverManager; // Importa la clase para obtener conexiones a la base de datos

public class DBConnection { // Clase para gestionar la conexión a la base de datos
    public static final String DB_USERNAME = "edixon_adso2894667"; // Usuario de la base de datos
    public static final String DB_PASSWORD = "adso"; // Contraseña del usuario de la base de datos
    public static final String DB_NAME = "FacturacionRestaurante"; // Nombre de la base de datos
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME; // URL de conexión al servidor MySQL con el nombre de la base

    public static Connection getConnection(){ // Método estático para obtener una conexión a la base de datos
        Connection conn = null; // Declara la variable para la conexión, inicialmente nula
        System.out.println(GREEN + "\n==================================================" + RESET); // Imprime línea decorativa en verde
        System.out.println(GREEN + "        INICIANDO CONEXION A LA BASE DE DATOS" + RESET); // Imprime mensaje de inicio de conexión
        System.out.println(GREEN + "==================================================" + RESET); // Imprime línea decorativa en verde
        try{ // Intenta establecer la conexión
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carga el controlador de MySQL
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); // Establece la conexión usando los parámetros definidos
            System.out.println(GREEN + "  >> CONEXION ESTABLECIDA CORRECTAMENTE." + RESET); // Mensaje indicando conexión exitosa
        }
        catch(Exception ex) // Captura cualquier error ocurrido al conectar
        {
            System.out.println(RED + ">> ERROR AL CONECTAR A LA BASE DE DATOS." + RESET); // Mensaje de error
            System.out.println(RED + " MOTIVO    : " + ex.getMessage() + RESET); // Muestra el motivo del error
            System.out.println(RED + "--------------------------------------------------" + RESET); // Línea decorativa en rojo
            ex.printStackTrace(); // Imprime el rastreo del error
        }
        System.out.println(GREEN + "==================================================\n" + RESET); // Línea decorativa final
        return conn; // Devuelve la conexión (puede ser nula si hubo error)
    }
    public static final String RESET = "\u001B[0m"; // Código ANSI para resetear color
    public static final String GREEN = "\u001B[32m"; // Código ANSI para color verde
    public static final String RED = "\u001B[31m"; // Código ANSI para color rojo
}
