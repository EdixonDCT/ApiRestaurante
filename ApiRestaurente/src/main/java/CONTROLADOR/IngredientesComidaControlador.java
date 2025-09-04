// Paquete que agrupa todos los controladores del proyecto.
package CONTROLADOR;

// Importa las clases necesarias para el controlador.
import MODELO.IngredientesComida; // Clase que representa la asociación entre un ingrediente y una comida.
import DAO.IngredientesComidaDAO; // Clase DAO para interactuar con la base de datos de IngredientesComida.
import DAO.IngredienteDAO; // Clase DAO para verificar la existencia de un ingrediente.
import DAO.ComidaDAO; // Clase DAO para verificar la existencia de una comida.
import UTILS.Middlewares; // Clase que contiene métodos para validar datos.

import java.util.List; // Interfaz para manejar listas de objetos.
import javax.ws.rs.*; // Anotaciones para definir endpoints REST.
import javax.ws.rs.core.MediaType; // Para especificar tipos de medios (ej. JSON).
import javax.ws.rs.core.Response; // Para construir y devolver respuestas HTTP.

// Define la ruta base para acceder a los métodos del controlador.
@Path("ingredientesComida") // Todos los endpoints de esta clase estarán bajo la ruta "/ingredientesComida".
// Indica que las respuestas serán en formato JSON.
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON.
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientesComidaControlador {

    // Instancias de los DAOs necesarios para interactuar con la base de datos.
    private IngredientesComidaDAO IngComDAO = new IngredientesComidaDAO();
    private IngredienteDAO IngDAO = new IngredienteDAO();
    private ComidaDAO comDAO = new ComidaDAO();

    // GET: Listar todos los ingredientes de comida.
    @GET
    public Response listarIngredientesComida() {
        try { // Bloque try-catch para manejar errores.
            List<IngredientesComida> lista = IngComDAO.listarTodos(); // Llama al DAO para obtener todos los registros.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de objetos.
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // GET: Obtener una asociación de ingrediente-comida por ID.
    @GET
    @Path("/{id}") // La ruta incluye un parámetro dinámico llamado "id".
    public Response obtenerIngredientesComida(@PathParam("id") String id) { // El valor del "id" de la URL se inyecta en la variable 'id'.
        try {
            // Se valida que el id sea un número entero.
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            IngredientesComida IngCom = IngComDAO.obtenerPorId(id); // Busca el registro por el ID.
            if (IngCom != null) { // Si se encuentra el registro...
                return Response.ok(IngCom).build(); // ...retorna una respuesta 200 (OK) con el objeto.
            } else { // Si no se encuentra...
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener Ingrediente Comida.")
                        .build(); // ...retorna una respuesta 404 (Not Found).
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    // GET: Obtener todos los ingredientes asociados a una comida específica.
    @GET
    @Path("/comida/{id}") // La ruta incluye el ID de la comida.
    public Response obtenerIngredientesComidaPorComida(@PathParam("id") String id) { // Recibe el ID de la comida.
        try {
            // Se valida que el id sea un número entero.
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }
            List<IngredientesComida> lista = IngComDAO.obtenerPorComida(id); // Llama al DAO para obtener la lista.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista.
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // POST: Crear una nueva asociación entre ingrediente y comida.
    @POST
    public Response crearIngredientesComida(IngredientesComida IngCom) {
        try {
            // Valida los IDs de ingrediente y comida.
            String validarIdIngrediente = Middlewares.validarEntero(IngCom.getIdIngrediente(), "id ingrediente");
            if (!validarIdIngrediente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdIngrediente).build();
            }

            String validarIdComida = Middlewares.validarEntero(IngCom.getIdComida(), "id comida");
            if (!validarIdComida.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdComida).build();
            }

            // Verifica que tanto el ingrediente como la comida existan en la base de datos.
            boolean ing = IngDAO.existeIngredientePorId(IngCom.getIdIngrediente());
            boolean com = comDAO.existePorId(IngCom.getIdComida());
            String mensaje = "";
            boolean creado = false;

            if (ing && com) { // Si ambos IDs son válidos y existen...
                mensaje = "Ingrediente #" + IngCom.getIdIngrediente() + " y Comida #" + IngCom.getIdComida() + " encontrados, creando...)";
                creado = IngComDAO.crear(IngCom); // ...se crea el nuevo registro.
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

    // DELETE: Eliminar una asociación por su ID.
    @DELETE
    @Path("/{id}")
    public Response eliminarIngredientesComida(@PathParam("id") String id) {
        try {
            // Se valida que el id sea un número entero.
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            boolean eliminado = IngComDAO.eliminar(id); // Llama al DAO para eliminar el registro.
            if (eliminado) { // Si la eliminación fue exitosa...
                String mensaje = "Ingredientes de Comida eliminados Exitosamente.";
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
