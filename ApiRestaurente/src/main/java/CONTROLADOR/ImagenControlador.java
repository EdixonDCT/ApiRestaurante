package CONTROLADOR;

import java.io.*;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.glassfish.jersey.media.multipart.*;

@Path("subirimagen")
public class ImagenControlador {

    @Context
    ServletContext contexto;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirImagen(@FormDataParam("imagen") InputStream archivoInputStream,
            @FormDataParam("imagen") FormDataContentDisposition fileDetail) {
        String rutaBase = contexto.getRealPath("/"); // Te da algo como: .../target/ApiRestaurente-1.0-SNAPSHOT/
        File carpetaProyecto = new File(rutaBase).getParentFile().getParentFile(); // Sube 2 carpetas (fuera del target)
        String rutaImagenes = new File(carpetaProyecto, "src/main/webapp/IMAGENES").getAbsolutePath();
        String nombreOriginal = fileDetail.getFileName();
        String nombreFinal = UUID.randomUUID().toString() + "_" + nombreOriginal;

        try {
            File carpeta = new File(rutaImagenes);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivoDestino = new File(carpeta, nombreFinal);
            try (OutputStream out = new FileOutputStream(archivoDestino)) {
                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = archivoInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }
            }

            String urlPublica = "http://localhost:8080/ApiRestaurente/IMAGENES/" + nombreFinal;

            System.out.println("✅ Imagen subida. Ruta: " + archivoDestino.getAbsolutePath());

            return Response.ok("{"
                    + "\"nombre\":\"" + nombreFinal + "\","
                    + "\"url\":\"" + urlPublica + "\""
                    + "}").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                    .entity("{\"error\":\"No se pudo guardar la imagen.\"}")
                    .build();
        }
    }
}
