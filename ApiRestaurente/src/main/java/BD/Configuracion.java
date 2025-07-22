package BD;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class Configuracion extends ResourceConfig {

    // Constantes de colores para imprimir en consola
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    public Configuracion() {
        try {
            System.out.println(GREEN + "==================================================");
            System.out.println(GREEN + "Configuracion iniciada correctamente." + RESET);
            System.out.println(GREEN + "==================================================");
            // Registrar el paquete de tus controladores
            packages("CONTROLADOR");

            // Registrar soporte para multipart/form-data (subida de imágenes)
            register(MultiPartFeature.class);

        } catch (Exception e) {
            System.out.println(RED + "==================================================");
            System.out.println(RED + "Error con Configuracion: " + RESET);
            System.out.println(RED + "==================================================");
            e.printStackTrace();
        }
    }
}
