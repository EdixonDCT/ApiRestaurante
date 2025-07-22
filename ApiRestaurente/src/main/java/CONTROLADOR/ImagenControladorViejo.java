package CONTROLADOR;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.*;

// Debes tener la dependencia jersey-media-multipart en tu proyecto

@Path("subirimagenviejo")
public class ImagenControladorViejo {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response subirImagen(@FormDataParam("imagen") InputStream archivoInputStream,
                                @FormDataParam("imagen") FormDataContentDisposition fileDetail) {

        String rutaImagenes = "";

        try {
            // 1. Generar nombre final
            String nombreOriginal = fileDetail.getFileName();
            String nombreFinal = UUID.randomUUID().toString() + "_" + nombreOriginal;

            // 2. Obtener ruta del .class
            String rutaClase = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            String rutaDecodificada = URLDecoder.decode(rutaClase, "UTF-8");
            File clase = new File(rutaDecodificada);
            File rutaBase = clase.getParentFile();

            while (rutaBase != null && !new File(rutaBase, "src").exists()) {
                rutaBase = rutaBase.getParentFile();
            }

            if (rutaBase == null) {
                throw new RuntimeException("❌ No se pudo localizar el raíz del proyecto (src no encontrado).");
            }

            String rutaProyecto = rutaBase.getAbsolutePath();
            rutaImagenes = rutaProyecto + File.separator + "src" + File.separator +
                           "main" + File.separator + "java" + File.separator + "IMAGENES";

            File carpeta = new File(rutaImagenes);
            if (!carpeta.exists()) carpeta.mkdirs();

            // 3. Guardar imagen
            File imagenFinal = new File(carpeta, nombreFinal);
            try (OutputStream out = new FileOutputStream(imagenFinal)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = archivoInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            String rutaBonita = imagenFinal.getAbsolutePath().replace("\\", "/");
            String rutaRelativa = "IMAGENES/" + nombreFinal;

            // 4. Imprimir ruta
            System.out.println("✅ Imagen guardada en: " + imagenFinal.getAbsolutePath());
            System.out.println("🌐 Ruta bonita: " + rutaBonita);
            System.out.println("🗃 Ruta relativa: " + rutaRelativa);

            // 5. Respuesta JSON
            return Response.ok("{"
                    + "\"nombre\":\"" + nombreFinal + "\","
                    + "\"ruta\":\"" + rutaBonita + "\","
                    + "\"relativa\":\"" + rutaRelativa + "\""
                    + "}").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                    .entity("{\"error\":\"No se pudo guardar la imagen. Ruta tentativa: " + rutaImagenes.replace("\\", "/") + "\"}")
                    .build();
        }
    }
}