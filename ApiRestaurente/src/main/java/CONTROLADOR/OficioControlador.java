// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Oficio
import MODELO.Oficio;
// Importa la clase DAO que gestiona la base de datos para Oficio
import MODELO.OficioDAO;
// Importa el middleware de validación para Oficio
import CONTROLADOR.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("oficios") // La ruta base para este controlador es "/oficios".
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON) // Los métodos producirán respuestas en formato JSON.
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Los métodos consumirán peticiones en formato JSON.
public class OficioControlador {

    // Instancia del DAO para poder acceder a la base de datos
    private OficioDAO oficioDAO = new OficioDAO();

    // Método GET para obtener todos los oficios
    @GET // Mapea este método a las peticiones GET en "/oficios".
    public Response listarOficios() {
        try {
            List<Oficio> lista = oficioDAO.listarTodos(); // Llama al DAO para obtener la lista de oficios.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista en formato JSON.
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción para depuración.
            return Response.serverError().build(); // Retorna una respuesta 500 (Internal Server Error).
        }
    }

    // Método GET para obtener un oficio por su ID
    @GET // Mapea este método a las peticiones GET.
    @Path("/{id}") // La ruta incluye un parámetro de path dinámico llamado "id".
    public Response obtenerOficio(@PathParam("id") String id) {
        try {
            // Valida que el ID proporcionado sea un número entero
            String validaCodigo = Middlewares.validarEntero(id, "codigo");
            if (!validaCodigo.equals("ok")) {
                // Si la validación falla, retorna una respuesta 400 (Bad Request).
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigo).build();
            }

            Oficio oficio = oficioDAO.obtenerPorId(id); // Busca el oficio por su ID.
            if (oficio != null) {
                // Si el oficio es encontrado, retorna una respuesta 200 (OK) con el objeto.
                return Response.ok(oficio).build();
            } else {
                // Si el oficio no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener OFICIO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    // Método POST para crear un nuevo oficio
    @POST // Mapea este método a las peticiones POST.
    public Response crearOficio(Oficio oficio) {
        try {
            // Valida el campo 'tipo' del oficio
            String validaTipo = Middlewares.validarString(oficio.getTipo(), "tipo");
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }

            // Valida el campo 'salario' del oficio
            String validaSalario = Middlewares.validarDouble(String.valueOf(oficio.getSalario()), "salario");
            if (!validaSalario.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaSalario).build();
            }

            // Si las validaciones son correctas, intenta crear el oficio en la base de datos
            boolean creado = oficioDAO.crear(oficio);
            if (creado) {
                // Si se crea exitosamente, retorna una respuesta 201 (Created).
                return Response.status(Response.Status.CREATED)
                        .entity("Oficio: " + oficio.getTipo() + " creado con EXITO.")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear OFICIO " + oficio.getTipo()+".")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar un oficio existente
    @PUT // Mapea este método a las peticiones PUT.
    @Path("/{id}") // La ruta incluye un parámetro de path para el ID.
    public Response actualizarOficio(@PathParam("id") String id, Oficio oficio) {
        try {
            oficio.setCodigo(id); // Establece el ID del parámetro de path en el objeto oficio.

            // Valida el código del oficio
            String validaCodigo = Middlewares.validarEntero(oficio.getCodigo(), "codigo");
            if (!validaCodigo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigo).build();
            }

            // Valida el campo 'tipo' del oficio
            String validaTipo = Middlewares.validarString(oficio.getTipo(), "tipo");
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }

            // Valida el campo 'salario' del oficio
            String validaSalario = Middlewares.validarDouble(String.valueOf(oficio.getSalario()), "salario");
            if (!validaSalario.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaSalario).build();
            }

            // Si todas las validaciones son correctas, intenta actualizar el oficio.
            boolean actualizado = oficioDAO.actualizar(oficio);
            if (actualizado) {
                // Si la actualización es exitosa, retorna una respuesta 200 (OK).
                return Response.ok().entity("Oficio: " + oficio.getTipo() + " actualizado con EXITO.").build();
            } else {
                // Si el oficio no se encuentra o no se actualiza, retorna 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: oficio NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar un oficio por su ID
    @DELETE // Mapea este método a las peticiones DELETE.
    @Path("/{id}") // La ruta incluye un parámetro de path para el ID.
    public Response eliminarOficio(@PathParam("id") String id) {
        try {
            // Valida el ID antes de intentar la eliminación
            String validaCodigo = Middlewares.validarEntero(id, "codigo");
            if (!validaCodigo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigo).build();
            }

            // Intenta eliminar el oficio por ID
            boolean eliminado = oficioDAO.eliminar(id);
            if (eliminado) {
                // Si se elimina exitosamente, retorna una respuesta 200 (OK).
                return Response.ok().entity("Oficio: Eliminado EXITOSAMENTE.").build();
            } else {
                // Si el oficio no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: oficio NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
