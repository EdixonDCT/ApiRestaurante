package BD; // Define el paquete donde se encuentra la clase

import org.glassfish.jersey.server.ResourceConfig; // Importa la clase base para configurar la aplicación Jersey
import org.glassfish.jersey.media.multipart.MultiPartFeature; // Importa la clase que habilita soporte para multipart/form-data

import javax.ws.rs.ApplicationPath; // Importa la anotación para definir la ruta base de la API

@ApplicationPath("api") // Establece que la API estará disponible a partir de la ruta /api
public class Configuracion extends ResourceConfig { // Clase que configura la aplicación REST y extiende ResourceConfig

    // Constantes de colores para imprimir en consola
    public static final String RESET = "\u001B[0m"; // Código ANSI para resetear color en consola
    public static final String GREEN = "\u001B[32m"; // Código ANSI para texto verde
    public static final String RED = "\u001B[31m"; // Código ANSI para texto rojo

    public Configuracion() { // Constructor de la clase Configuracion
        try { // Bloque para intentar ejecutar la configuración
            System.out.println(GREEN + "=================================================="); // Imprime línea decorativa en verde
            System.out.println(GREEN + "Configuracion iniciada correctamente." + RESET); // Imprime mensaje indicando que la configuración fue exitosa
            System.out.println(GREEN + "=================================================="); // Imprime línea decorativa en verde
            packages("CONTROLADOR"); // Registra el paquete donde están los controladores REST
            register(MultiPartFeature.class); // Habilita soporte para envío de archivos mediante multipart/form-data
        } catch (Exception e) { // Captura cualquier excepción que ocurra
            System.out.println(RED + "=================================================="); // Imprime línea decorativa en rojo
            System.out.println(RED + "Error con Configuracion: " + RESET); // Imprime mensaje indicando error en configuración
            System.out.println(RED + "=================================================="); // Imprime línea decorativa en rojo
            e.printStackTrace(); // Muestra el seguimiento del error en consola
        }
    }
}
