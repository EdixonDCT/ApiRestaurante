// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Oficio
import MODELO.Oficio;
// Importa la clase DAO que gestiona la base de datos para Oficio
import MODELO.OficioDAO;
// Importa el middleware de validación para Oficio
import CONTROLADOR.MiddlewareOficio;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("oficios")
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class OficioControlador {

    // Instancia del DAO para poder acceder a la base de datos
    private OficioDAO oficioDAO = new OficioDAO();

    // Método GET para obtener todos los oficios
    @GET
    public Response listarOficios() {
        try {
            List<Oficio> lista = oficioDAO.listarTodos(); // Llama al DAO
            return Response.ok(lista).build(); // Retorna lista en JSON
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500
        }
    }

    // Método GET para obtener un oficio por su ID
    @GET
    @Path("/{id}")
    public Response getOficio(@PathParam("id") int id) {
        try {
            Oficio oficio = oficioDAO.obtenerPorId(id); // Busca por ID
            if (oficio != null) {
                return Response.ok(oficio).build(); // Retorna oficio
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: no se pudo obtener OFICIO.")
                               .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                           .entity("Error: Error interno en el servidor.")
                           .build(); // Retorna error 500
        }
    }

    // Método POST para crear un nuevo oficio
    @POST
    public Response createOficio(Oficio oficio) {
        try {
            // Valida el campo tipo
            String validaTipo = MiddlewareOficio.textoValido(oficio.getTipo(), "tipo");
            if (!validaTipo.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();

            // Valida el campo salario
            String validaSalario = MiddlewareOficio.numeroMayorA0(String.valueOf(oficio.getSalario()), "salario");
            if (!validaSalario.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaSalario).build();

            // Crea el oficio si pasa las validaciones
            boolean creado = oficioDAO.crear(oficio);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                               .entity("Oficio: " + oficio.getTipo() + " creado con EXITO.")
                               .build(); // Retorna código 201
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("Error: no se pudo crear OFICIO.")
                               .build(); // Retorna error 500
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar un oficio existente
    @PUT
    @Path("/{id}")
    public Response actualizarOficio(@PathParam("id") int id, Oficio oficio) {
        try {
            oficio.setCodigo(id); // Establece el ID al objeto que se va a actualizar

            // Validaciones
            String validaTipo = MiddlewareOficio.textoValido(oficio.getTipo(), "tipo");
            if (!validaTipo.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();

            String validaSalario = MiddlewareOficio.numeroMayorA0(String.valueOf(oficio.getSalario()), "salario");
            if (!validaSalario.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaSalario).build();

            // Actualiza si todo está bien
            boolean actualizado = oficioDAO.actualizar(oficio);
            if (actualizado) {
                return Response.ok().entity("Oficio: " + oficio.getTipo() + " actualizado con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: oficio NO ENCONTRADO o NO ACTUALIZADO.")
                               .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar un oficio por su ID
    @DELETE
    @Path("/{id}")
    public Response eliminarOficio(@PathParam("id") int id) {
        try {
            boolean eliminado = oficioDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Oficio: Eliminado EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: oficio NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
