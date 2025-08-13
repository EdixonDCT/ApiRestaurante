package CONTROLADOR;

import MODELO.IngredientesCoctel;
import MODELO.IngredientesCoctelDAO;
import MODELO.CoctelDAO;
import MODELO.IngredienteDAO;
import CONTROLADOR.Middlewares;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ingredientesCoctel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredientesCoctelControlador {

    private IngredientesCoctelDAO IngCocDAO = new IngredientesCoctelDAO();
    private IngredienteDAO IngDAO = new IngredienteDAO();
    private CoctelDAO CocDAO = new CoctelDAO();

    @GET
    public Response listar() {
        try {
            List<IngredientesCoctel> lista = IngCocDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
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

            IngredientesCoctel ingCoc = IngCocDAO.obtenerPorId(id);
            if (ingCoc != null) {
                return Response.ok(ingCoc).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener Ingrediente Coctel.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    @GET
    @Path("/coctel/{id}")
    public Response obtenerPorCoctel(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            List<IngredientesCoctel> lista = IngCocDAO.obtenerPorCoctel(id);
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500
        }
    }

    @POST
    public Response crear(IngredientesCoctel ingCoc) {
        try {
            String validarIdIngrediente = Middlewares.validarEntero(ingCoc.getIdIngrediente(), "id ingrediente");
            if (!validarIdIngrediente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdIngrediente).build();
            }

            String validarIdCoctel = Middlewares.validarEntero(ingCoc.getIdCoctel(), "id coctel");
            if (!validarIdCoctel.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdCoctel).build();
            }
            boolean ing = IngDAO.existeIngredientePorId(ingCoc.getIdIngrediente());
            boolean coc = CocDAO.existeCoctelPorId(ingCoc.getIdCoctel());
            String mensaje = "";
            boolean creado = false;
            if (ing && coc) {
               mensaje = "Ingrediente #"+ingCoc.getIdIngrediente()+" y Coctel #"+ingCoc.getIdCoctel()+" encontrados, creando...)";
               creado = IngCocDAO.crear(ingCoc);
            } else {
               mensaje = "Error: no se pudo crear INGREDIENTE Coctel.";
            }
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity(mensaje).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
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

            boolean eliminado = IngCocDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Ingrediente Coctel: Eliminado EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente coctel NO ENCONTRADO").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
