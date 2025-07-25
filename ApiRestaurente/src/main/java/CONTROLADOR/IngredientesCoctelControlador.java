package CONTROLADOR;

import MODELO.IngredientesCoctel;
import MODELO.IngredientesCoctelDAO;
import CONTROLADOR.Middlewares;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ingredientesCoctel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientesCoctelControlador {

    private IngredientesCoctelDAO dao = new IngredientesCoctelDAO();

    @GET
    public Response listar() {
        try {
            List<IngredientesCoctel> lista = dao.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al listar registros.").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            IngredientesCoctel ic = dao.obtenerPorId(id);
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

    @POST
    public Response crear(IngredientesCoctel ic) {
        try {
            String v1 = Middlewares.validarEntero(ic.getIdIngrediente(), "id_ingrediente");
            String v2 = Middlewares.validarEntero(ic.getIdCoctel(), "id_coctel");

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
