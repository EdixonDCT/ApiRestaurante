package BD;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static final String DB_USERNAME = "edixon_adso2894667";
    public static final String DB_PASSWORD = "adso";
    public static final String DB_NAME = "FacturacionRestaurante";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    
    public static Connection getConnection(){
        Connection conn = null;
        System.out.println(GREEN + "\n==================================================" + RESET);
        System.out.println(GREEN + "        INICIANDO CONEXION A LA BASE DE DATOS" + RESET);
        System.out.println(GREEN + "==================================================" + RESET);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println(GREEN + "  >> CONEXION ESTABLECIDA CORRECTAMENTE." + RESET);
        }
        catch(Exception ex)
        {
            System.out.println(RED + ">> ERROR AL CONECTAR A LA BASE DE DATOS." + RESET);
            System.out.println(RED + " MOTIVO    : " + ex.getMessage() + RESET);
            System.out.println(RED + "--------------------------------------------------" + RESET);
            ex.printStackTrace();
        }
        System.out.println(GREEN + "==================================================\n" + RESET);
        return conn;
    }
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
}
