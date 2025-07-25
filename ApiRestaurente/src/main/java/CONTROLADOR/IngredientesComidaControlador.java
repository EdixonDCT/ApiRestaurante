package CONTROLADOR;

import MODELO.IngredientesComida;
import MODELO.IngredientesComidaDAO;
import CONTROLADOR.Middlewares;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ingredientesComida")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientesComidaControlador {

    private IngredientesComidaDAO dao = new IngredientesComidaDAO();

    // GET: Listar todos
    @GET
    public Response listar() {
        try {
            List<IngredientesComida> lista = dao.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al listar registros.").build();
        }
    }

    // GET: Obtener por ID
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            IngredientesComida ic = dao.obtenerPorId(id);
            if (ic != null) {
                return Response.ok(ic).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontró el registro con ID: " + id)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al buscar registro.").build();
        }
    }

    // POST: Crear nuevo registro
    @POST
    public Response crear(IngredientesComida ic) {
        try {
            String v1 = Middlewares.validarEntero(ic.getIdIngrediente(), "id_ingrediente");
            String v2 = Middlewares.validarEntero(ic.getIdComida(), "id_comida");

            if (!v1.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(v1).build();
            }
            if (!v2.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(v2).build();
            }

            boolean creado = dao.crear(ic);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Registro creado con éxito.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al crear el registro.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al crear registro.").build();
        }
    }

    // DELETE: Eliminar por ID
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            boolean eliminado = dao.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Registro eliminado exitosamente.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Registro no encontrado o no eliminado.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al eliminar registro.").build();
        }
    }
}
