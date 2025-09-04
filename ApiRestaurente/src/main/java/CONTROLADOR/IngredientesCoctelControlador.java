// Paquete que agrupa todos los controladores del proyecto.
package CONTROLADOR;

// Importa las clases necesarias para el controlador.
import MODELO.IngredientesCoctel; // Clase que representa la asociación entre un ingrediente y un cóctel.
import DAO.IngredientesCoctelDAO; // Clase DAO para interactuar con la base de datos de IngredientesCoctel.
import DAO.CoctelDAO; // Clase DAO para verificar la existencia de un cóctel.
import DAO.IngredienteDAO; // Clase DAO para verificar la existencia de un ingrediente.
import UTILS.Middlewares; // Clase que contiene métodos para validar datos.

import java.util.List; // Interfaz para manejar listas de objetos.
import javax.ws.rs.*; // Anotaciones para definir endpoints REST.
import javax.ws.rs.core.MediaType; // Para especificar tipos de medios (ej. JSON).
import javax.ws.rs.core.Response; // Para construir y devolver respuestas HTTP.

// Define la ruta base para acceder a los métodos del controlador.
@Path("ingredientesCoctel") // Todos los endpoints de esta clase estarán bajo la ruta "/ingredientesCoctel".
// Indica que las respuestas serán en formato JSON.
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON.
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientesCoctelControlador {

    // Instancias de los DAOs necesarios para interactuar con la base de datos.
    private IngredientesCoctelDAO IngCocDAO = new IngredientesCoctelDAO();
    private IngredienteDAO IngDAO = new IngredienteDAO();
    private CoctelDAO CocDAO = new CoctelDAO();

    // Método GET para listar todos los ingredientes de cóctel.
    @GET
    public Response listar() {
        try {
            List<IngredientesCoctel> lista = IngCocDAO.listarTodos(); // Llama al DAO para obtener todos los registros.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de objetos.
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método GET para obtener un ingrediente de cóctel por su ID.
    @GET
    @Path("/{id}") // La ruta incluye un parámetro dinámico llamado "id".
    public Response obtenerPorId(@PathParam("id") String id) { // El valor del "id" de la URL se inyecta en la variable 'id'.
        try {
            // Se valida que el id sea un número entero.
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            IngredientesCoctel ingCoc = IngCocDAO.obtenerPorId(id); // Busca el registro por el ID.
            if (ingCoc != null) { // Si se encuentra el registro...
                return Response.ok(ingCoc).build(); // ...retorna una respuesta 200 (OK) con el objeto.
            } else { // Si no se encuentra...
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener Ingrediente Coctel.")
                        .build(); // ...retorna una respuesta 404 (Not Found).
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    // Método GET para obtener los ingredientes de un cóctel específico.
    @GET
    @Path("/coctel/{id}") // La ruta incluye el ID del cóctel.
    public Response obtenerPorCoctel(@PathParam("id") String id) { // Recibe el ID del cóctel.
        try {
            // Se valida que el id sea un número entero.
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            List<IngredientesCoctel> lista = IngCocDAO.obtenerPorCoctel(id); // Llama al DAO para obtener la lista de ingredientes del cóctel.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista.
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método POST para crear una nueva asociación entre ingrediente y cóctel.
    @POST
    public Response crear(IngredientesCoctel ingCoc) {
        try {
            // Valida los IDs de ingrediente y cóctel.
            String validarIdIngrediente = Middlewares.validarEntero(ingCoc.getIdIngrediente(), "id ingrediente");
            if (!validarIdIngrediente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdIngrediente).build();
            }

            String validarIdCoctel = Middlewares.validarEntero(ingCoc.getIdCoctel(), "id coctel");
            if (!validarIdCoctel.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdCoctel).build();
            }
            
            // Verifica que el ingrediente y el cóctel existan en la base de datos.
            boolean ing = IngDAO.existeIngredientePorId(ingCoc.getIdIngrediente());
            boolean coc = CocDAO.existeCoctelPorId(ingCoc.getIdCoctel());
            String mensaje = "";
            boolean creado = false;
            
            if (ing && coc) {
                // Si ambos existen, procede a crear la asociación.
                mensaje = "Ingrediente #" + ingCoc.getIdIngrediente() + " y Coctel #" + ingCoc.getIdCoctel() + " encontrados, creando...)";
                creado = IngCocDAO.crear(ingCoc);
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo eliminar Ingrediente.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }


    // Método DELETE para eliminar una asociación por su ID.
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            // Se valida que el id sea un número entero.
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            boolean eliminado = IngCocDAO.eliminar(id); // Llama al DAO para eliminar el registro.
            if (eliminado) { // Si la eliminación fue exitosa...
                String mensaje = "Ingredientes de Coctel eliminados Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo eliminar Ingrediente.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }
}
