package CONTROLADOR;

import MODELO.Ingrediente;
import MODELO.IngredienteDAO;
import CONTROLADOR.Middlewares;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ingredientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredienteControlador {

    private IngredienteDAO ingredienteDAO = new IngredienteDAO();

    @GET
    public Response listarIngredientes() {
        try {
            List<Ingrediente> lista = ingredienteDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerIngrediente(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            Ingrediente ingrediente = ingredienteDAO.obtenerPorId(id);
            if (ingrediente != null) {
                return Response.ok(ingrediente).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente NO ENCONTRADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @POST
    public Response crearIngrediente(Ingrediente ingrediente) {
        try {
            String validaNombre = Middlewares.validarString(ingrediente.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            boolean creado = ingredienteDAO.crear(ingrediente);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Ingrediente: " + ingrediente.getNombre() + " creado con ÉXITO.")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el ingrediente.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarIngrediente(@PathParam("id") String id, Ingrediente ingrediente) {
        try {
            ingrediente.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            String validaNombre = Middlewares.validarString(ingrediente.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            boolean actualizado = ingredienteDAO.actualizar(ingrediente);
            if (actualizado) {
                return Response.ok().entity("Ingrediente: " + ingrediente.getNombre() + " actualizado con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarIngrediente(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean eliminado = ingredienteDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Ingrediente eliminado con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente NO ENCONTRADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }
}
