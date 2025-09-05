// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa las clases necesarias
import MODELO.Comida; // La clase que representa el objeto Comida.
import DAO.ComidaDAO; // La clase DAO para interactuar con la base de datos de comidas.
import MODELO.Usuarios; // La clase Usuarios (aunque no se usa directamente en este código, se mantiene el import).
import UTILS.Middlewares; // La clase que contiene métodos para validar datos.

import java.util.List; // La interfaz para manejar listas de objetos.

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*; // Anotaciones para definir endpoints REST.
import javax.ws.rs.core.MediaType; // Para especificar tipos de medios (ej. JSON).
import javax.ws.rs.core.Response; // Para construir y devolver respuestas HTTP.

// Define la ruta base para acceder a los métodos del controlador
@Path("comidas") // Todos los endpoints de esta clase estarán bajo la ruta "/comidas".
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON) // Configura el tipo de respuesta a JSON.
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Configura el tipo de petición que se espera a JSON.
public class ComidaControlador {

    // Instancia del DAO para acceder a la base de datos
    private ComidaDAO comidaDAO = new ComidaDAO(); // Se inicializa el DAO para usarlo en los métodos.

    // Método GET para listar todas las comidas
    @GET // Anotación que mapea este método a peticiones GET.
    public Response listarComidas() {
        try { // Bloque try-catch para manejar errores.
            List<Comida> lista = comidaDAO.listarTodos(); // Llama al método del DAO para obtener todas las comidas.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de comidas.
        } catch (Exception e) { // Captura cualquier excepción que pueda ocurrir.
            e.printStackTrace(); // Imprime la traza del error para depuración.
            return Response.serverError().build(); // Retorna una respuesta 500 (Internal Server Error).
        }
    }

    // Método GET para obtener una comida por ID
    @GET // Mapea a peticiones GET.
    @Path("/{id}") // La ruta incluye un parámetro dinámico llamado "id".
    public Response obtenerComida(@PathParam("id") String id) { // El valor del "id" de la URL se inyecta en la variable 'id'.
        try {
            String validaID = Middlewares.validarEntero(id, "id"); // Valida que el ID sea un número entero.
            if (!validaID.equals("ok")) { // Si la validación no es exitosa...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build(); // ...retorna una respuesta 400 (Bad Request).
            }

            Comida comida = comidaDAO.obtenerPorId(id); // Busca la comida por el ID en la base de datos.
            if (comida != null) { // Si se encuentra la comida...
                return Response.ok(comida).build(); // ...retorna una respuesta 200 (OK) con el objeto Comida.
            } else { // Si no se encuentra la comida...
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo encontrar Comida.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método POST para crear una nueva comida
    @POST // Mapea a peticiones POST.
    public Response crearComida(Comida comida) { // Recibe un objeto Comida del cuerpo de la petición.
        try {
            String validaNombre = Middlewares.validarString(comida.getNombre(), "nombre"); // Valida el nombre.
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(comida.getPrecio(), "precio"); // Valida el precio.
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            String validaTipo = Middlewares.validarString(comida.getTipo(), "tipo"); // Valida el tipo.
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }
            String[] resultado = comidaDAO.crear(comida); // Llama al DAO para crear la comida.

            if (!resultado[1].equals("-1")) { // El DAO devuelve un array con mensaje y ID. Si el ID no es "-1", la creación fue exitosa.
                // Si se creó, retorna una respuesta 201 (Created) con un JSON que incluye "Ok"
                String mensaje = "Comida creada EXITOSAMENTE.";
                String json = String.format("{\"Ok\": \"%s\", \"id\": \"%s\"}", mensaje, resultado[1]);
                
                return Response.status(Response.Status.CREATED)
                        .entity(json)
                        .build();
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo crear Comida.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método PUT para actualizar una comida existente
    @PUT // Mapea a peticiones PUT.
    @Path("/{id}") // Ruta con un parámetro de ID.
    public Response actualizarComida(@PathParam("id") String id, Comida comida) { // Recibe el ID de la URL y el objeto del cuerpo.
        try {
            comida.setId(id); // Asigna el ID de la URL al objeto 'comida'.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            String validaNombre = Middlewares.validarString(comida.getNombre(), "nombre"); // Valida el nombre.
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(String.valueOf(comida.getPrecio()), "precio"); // Valida el precio.
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            String validaTipo = Middlewares.validarString(comida.getTipo(), "tipo"); // Valida el tipo.
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }

            boolean actualizado = comidaDAO.actualizar(comida); // Llama al DAO para actualizar la comida.
            if (actualizado) { // Si la actualización fue exitosa...
                String mensaje = "Comida actualizada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo actualizar Comida.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método PATCH para actualizar solo la imagen de una comida
    @PATCH // Mapea a peticiones PATCH (actualización parcial).
    @Path("/imagen/{id}") // Ruta específica para la actualización de la imagen.
    public Response actualizarImagenComida(@PathParam("id") String id, Comida comida) {
        try {
            comida.setId(id); // Establece el ID al objeto que se va a actualizar.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            // Actualiza si todo está bien
            boolean actualizado = comidaDAO.actualizarImagen(comida); // Llama al DAO para actualizar solo la imagen.
            if (actualizado) {
                String mensaje = "Comida Imagen actualizada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo cambiar la imagen de Comida.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método PATCH para actualizar la disponibilidad de una comida
    @PATCH // Mapea a peticiones PATCH.
    @Path("/{id}") // Ruta con un parámetro de ID.
    public Response actualizarDisponibilidadComida(@PathParam("id") String id, Comida comida) {
        try {
            comida.setId(id); // Establece el ID al objeto que se va a actualizar.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }
            String validaDisponiblidad = Middlewares.validarBooleano(comida.getDisponible(), "disponibilidad"); // Valida el campo 'disponible' como booleano.
            if (!validaDisponiblidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponiblidad).build();
            }

            // Actualiza si todo está bien
            boolean actualizado = comidaDAO.actualizarEstado(comida); // Llama al DAO para actualizar el estado de disponibilidad.
            if (actualizado) {
                String mensaje = "Comida Estado actualizado Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo cambiar el estado de Comida.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método DELETE para eliminar una comida por su ID
    @DELETE // Mapea a peticiones DELETE.
    @Path("/{id}") // Ruta con un parámetro de ID.
    public Response eliminarComida(@PathParam("id") String id) { // Recibe el ID de la URL.
        try {
            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            boolean eliminado = comidaDAO.eliminar(id); // Llama al DAO para eliminar la comida.
            if (eliminado) { // Si la eliminación fue exitosa...
                return Response.ok().entity("Comida: Eliminada EXITOSAMENTE.").build(); // ...retorna una respuesta 200.
            } else { // Si la comida no se encuentra...
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: comida NO ENCONTRADA.")
                        .build(); // ...retorna una respuesta 404.
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
