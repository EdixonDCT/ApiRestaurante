// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa las clases necesarias para el manejo de archivos e I/O
import java.io.*; // Para trabajar con flujos de entrada/salida.
import java.util.UUID; // Para generar identificadores únicos para los nombres de archivo.
import javax.servlet.ServletContext; // Para obtener información del contexto de la aplicación web.
import javax.ws.rs.*; // Anotaciones para definir endpoints REST.
import javax.ws.rs.core.*; // Clases para construir respuestas HTTP.
import org.glassfish.jersey.media.multipart.*; // Clases de Jersey para manejar datos de formularios multipart.

// Define la ruta base para acceder a los métodos de este controlador
@Path("imagen") // Todos los endpoints de esta clase estarán bajo la ruta "/imagen".
public class ImagenControlador {

    // Inyecta el contexto del servlet para obtener rutas del sistema de archivos
    @Context
    ServletContext contexto;

    // Método POST para subir una imagen al servidor
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) // Indica que el endpoint consume datos de formulario multipart.
    @Produces(MediaType.APPLICATION_JSON) // Indica que la respuesta será en formato JSON.
    public Response subirImagen(
            @FormDataParam("imagen") InputStream archivoInputStream, // Recibe el flujo de datos del archivo.
            @FormDataParam("imagen") FormDataContentDisposition fileDetail) { // Recibe los metadatos del archivo.

        // Obtiene la ruta base del despliegue de la aplicación (ej. ".../target/ApiRestaurente-1.0-SNAPSHOT/")
        String rutaBase = contexto.getRealPath("/");
        // Sube dos niveles para llegar a la raíz del proyecto (fuera de la carpeta 'target')
        File carpetaProyecto = new File(rutaBase).getParentFile().getParentFile();
        // Concatena la ruta para guardar la imagen en "src/main/webapp/IMAGENES"
        String rutaImagenes = new File(carpetaProyecto, "src/main/webapp/IMAGENES").getAbsolutePath();
        
        // Genera un nombre de archivo único para evitar colisiones
        String nombreOriginal = fileDetail.getFileName();
        String nombreFinal = UUID.randomUUID().toString() + "_" + nombreOriginal;

        try {
            // Crea la carpeta de destino si no existe
            File carpeta = new File(rutaImagenes);
            if (!carpeta.exists()) {
                carpeta.mkdirs(); // Crea la carpeta y sus directorios padre si es necesario.
            }

            // Crea el archivo de destino y escribe los bytes del flujo de entrada
            File archivoDestino = new File(carpeta, nombreFinal);
            try (OutputStream out = new FileOutputStream(archivoDestino)) {
                byte[] buffer = new byte[1024]; // Buffer para leer y escribir el archivo.
                int bytesLeidos;
                // Lee del flujo de entrada y escribe en el de salida
                while ((bytesLeidos = archivoInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }
            }

            // Construye la URL pública para acceder a la imagen
            String urlPublica = "http://localhost:8080/ApiRestaurante/IMAGENES/" + nombreFinal;

            System.out.println("✅ Imagen subida. Ruta: " + archivoDestino.getAbsolutePath());

            // Retorna una respuesta exitosa con el nombre del archivo y la URL
            return Response.ok("{"
                    + "\"nombre\":\"" + nombreFinal + "\","
                    + "\"url\":\"" + urlPublica + "\""
                    + "}").build();

        } catch (Exception e) {
            // En caso de error, imprime la traza y retorna un error 500
            e.printStackTrace();
            return Response.status(500)
                    .entity("{\"Error\":\"No se pudo guardar la imagen.\"}")
                    .build();
        }
    }

    // Método DELETE para eliminar una imagen por su nombre
    @DELETE
    @Path("/{nombre}") // La ruta incluye un parámetro dinámico con el nombre del archivo.
    @Produces(MediaType.TEXT_PLAIN) // Indica que la respuesta será texto plano.
    public Response eliminarImagen(@PathParam("nombre") String nombreArchivo) { // Recibe el nombre del archivo de la URL.
        
        // Construye la ruta completa del archivo de imagen
        String rutaBase = contexto.getRealPath("/");
        File carpetaProyecto = new File(rutaBase).getParentFile().getParentFile();
        String rutaImagenes = new File(carpetaProyecto, "src/main/webapp/IMAGENES").getAbsolutePath();

        File archivo = new File(rutaImagenes, nombreArchivo);

        // Verifica si el archivo existe
        if (!archivo.exists()) {
            return Response.status(Response.Status.NOT_FOUND) // Si no existe, retorna 404 (Not Found).
                    .entity("La imagen no existe.")
                    .build();
        }

        // Intenta eliminar el archivo
        if (archivo.delete()) {
            System.out.println("🗑️ Imagen eliminada: " + archivo.getAbsolutePath());
            return Response.ok("Imagen eliminada exitosamente.").build(); // Retorna 200 (OK) si la eliminación fue exitosa.
        } else {
            return Response.status(500) // Si falla la eliminación, retorna 500 (Internal Server Error).
                    .entity("No se pudo eliminar la imagen.")
                    .build();
        }
    }

}
