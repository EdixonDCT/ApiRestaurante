// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa las clases necesarias
import MODELO.Ingrediente; // Clase que representa el objeto Ingrediente.
import DAO.IngredienteDAO; // Clase DAO para interactuar con la base de datos de ingredientes.
import Utils.Middlewares; // Clase que contiene métodos para validar datos.

import java.util.List; // Interfaz para manejar listas de objetos.
import javax.ws.rs.*; // Anotaciones para definir endpoints REST.
import javax.ws.rs.core.MediaType; // Para especificar tipos de medios (ej. JSON).
import javax.ws.rs.core.Response; // Para construir y devolver respuestas HTTP.

// Define la ruta base para acceder a los métodos del controlador
@Path("ingredientes") // Todos los endpoints de esta clase estarán bajo la ruta "/ingredientes".
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON) // Configura el tipo de respuesta a JSON.
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Configura el tipo de petición que se espera a JSON.
public class IngredienteControlador {

    // Instancia del DAO para acceder a la base de datos
    private IngredienteDAO ingredienteDAO = new IngredienteDAO();

    // Método GET para listar todos los ingredientes
    @GET
    public Response listarIngredientes() {
        try { // Bloque try-catch para manejar errores.
            List<Ingrediente> lista = ingredienteDAO.listarTodos(); // Llama al DAO para obtener todos los ingredientes.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de ingredientes.
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza del error para depuración.
            return Response.serverError().build(); // Retorna una respuesta 500 (Internal Server Error).
        }
    }

    // Método GET para obtener un ingrediente por ID
    @GET
    @Path("/{id}") // La ruta incluye un parámetro dinámico llamado "id".
    public Response obtenerIngrediente(@PathParam("id") String id) { // El valor del "id" de la URL se inyecta en la variable 'id'.
        try {
            // Se valida que el id sea un número entero
            String validaId = Middlewares.validarEntero(id, "id"); // Valida que el ID sea un número entero.
            if (!validaId.equals("ok")) { // Si la validación no es exitosa...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // ...retorna una respuesta 400 (Bad Request).
            }

            Ingrediente ingrediente = ingredienteDAO.obtenerPorId(id); // Busca el ingrediente por el ID.
            if (ingrediente != null) { // Si se encuentra el ingrediente...
                return Response.ok(ingrediente).build(); // ...retorna una respuesta 200 (OK) con el objeto Ingrediente.
            } else { // Si no se encuentra...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna una respuesta 404 (Not Found).
                        .entity("Error: ingrediente NO ENCONTRADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Método POST para crear un nuevo ingrediente
    @POST
    public Response crearIngrediente(Ingrediente ingrediente) { // Recibe un objeto Ingrediente del cuerpo de la petición.
        try {
            // Valida que el nombre no sea nulo ni esté vacío
            String validaNombre = Middlewares.validarString(ingrediente.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            boolean creado = ingredienteDAO.crear(ingrediente); // Llama al DAO para crear el ingrediente.
            if (creado) { // Si la creación es exitosa...
                return Response.status(Response.Status.CREATED) // ...retorna una respuesta 201 (Created).
                        .entity("Ingrediente: " + ingrediente.getNombre() + " creado con ÉXITO.")
                        .build();
            } else { // Si la creación falla...
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // ...retorna una respuesta 500.
                        .entity("Error: no se pudo crear el ingrediente.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar un ingrediente existente
    @PUT
    @Path("/{id}") // Ruta con un parámetro de ID.
    public Response actualizarIngrediente(@PathParam("id") String id, Ingrediente ingrediente) { // Recibe el ID de la URL y el objeto del cuerpo.
        try {
            ingrediente.setId(id); // Asigna el ID de la URL al objeto 'ingrediente'.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            String validaNombre = Middlewares.validarString(ingrediente.getNombre(), "nombre"); // Valida el nombre.
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            boolean actualizado = ingredienteDAO.actualizar(ingrediente); // Llama al DAO para actualizar el ingrediente.
            if (actualizado) { // Si la actualización es exitosa...
                return Response.ok().entity("Ingrediente: " + ingrediente.getNombre() + " actualizado con ÉXITO.").build(); // ...retorna una respuesta 200.
            } else { // Si el ingrediente no se encuentra o no se actualiza...
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente NO ENCONTRADO o NO ACTUALIZADO.")
                        .build(); // ...retorna una respuesta 404.
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar un ingrediente por su ID
    @DELETE
    @Path("/{id}") // Ruta con un parámetro de ID.
    public Response eliminarIngrediente(@PathParam("id") String id) { // Recibe el ID de la URL.
        try {
            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean eliminado = ingredienteDAO.eliminar(id); // Llama al DAO para eliminar el ingrediente.
            if (eliminado) { // Si la eliminación fue exitosa...
                return Response.ok().entity("Ingrediente eliminado con ÉXITO.").build(); // ...retorna una respuesta 200.
            } else { // Si el ingrediente no se encuentra...
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente NO ENCONTRADO.")
                        .build(); // ...retorna una respuesta 404.
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }
}
