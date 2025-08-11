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

    private IngredientesComidaDAO IngComDAO = new IngredientesComidaDAO();

    // GET: Listar todos
    @GET
    public Response listarIngredientesComida() {
        try {
            List<IngredientesComida> lista = IngComDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500
        }
    }

    // GET: Obtener por ID
    @GET
    @Path("/{id}")
    public Response obtenerIngredientesComida(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            IngredientesComida IngCom = IngComDAO.obtenerPorId(id);
            if (IngCom != null) {
                return Response.ok(IngCom).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener Ingrediente Comida.")
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
    @Path("/comida/{id}")
    public Response obtenerIngredientesComidaPorComida(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }
            List<IngredientesComida> lista = IngComDAO.obtenerPorComida(id);
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500
        }
    }

    // POST: Crear nuevo registro
    @POST
    public Response crearIngredientesComida(IngredientesComida IngCom) {
        try {
            String validarIdIngrediente = Middlewares.validarEntero(IngCom.getIdIngrediente(), "id ingrediente");
            if (!validarIdIngrediente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdIngrediente).build();
            }

            String validarIdComida = Middlewares.validarEntero(IngCom.getIdComida(), "id comida");
            if (!validarIdComida.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarIdComida).build();
            }

            boolean creado = IngComDAO.crear(IngCom);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Ingrediente Comida: creado con Exito.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear Ingrediente Comida.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // DELETE: Eliminar por ID
    @DELETE
    @Path("/{id}")
    public Response eliminarIngredientesComida(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            }

            boolean eliminado = IngComDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Ingrediente Comida: Eliminado EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: ingrediente comida NO ENCONTRADO").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
