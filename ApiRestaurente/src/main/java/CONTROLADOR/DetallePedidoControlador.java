package CONTROLADOR;

import MODELO.DetallePedido;
import MODELO.DetallePedidoDAO;
import CONTROLADOR.Middlewares;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("detallePedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DetallePedidoControlador {

    private DetallePedidoDAO dao = new DetallePedidoDAO();

    // GET: Listar todos los detalles
    @GET
    public Response listarTodos() {
        try {
            List<DetallePedido> lista = dao.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al listar.").build();
        }
    }

    // GET: Obtener un detalle por ID
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") String id) {
        try {
            String validacion = Middlewares.validarEntero(id, "id");
            if (!validacion.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validacion).build();
            }

            DetallePedido d = dao.obtenerPorId(id);
            if (d != null) {
                return Response.ok(d).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("DetallePedido con ID " + id + " no encontrado.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al obtener.").build();
        }
    }

    // POST: Crear nuevo detalle
    @POST
    public Response crearDetalle(DetallePedido d) {
        try {
            String val1 = Middlewares.validarEntero(d.getId_pedido(), "id_pedido");
            if (!val1.equals("ok")) return Response.status(400).entity(val1).build();

            String val2 = Middlewares.validarEntero(d.getId_comida(), "id_comida");
            if (!val2.equals("ok")) return Response.status(400).entity(val2).build();

            String val3 = Middlewares.validarEntero(d.getCantidad_comida(), "cantidad_comida");
            if (!val3.equals("ok")) return Response.status(400).entity(val3).build();

            String val4 = Middlewares.validarEntero(d.getId_bebida(), "id_bebida");
            if (!val4.equals("ok")) return Response.status(400).entity(val4).build();

            String val5 = Middlewares.validarEntero(d.getCantidad_bebida(), "cantidad_bebida");
            if (!val5.equals("ok")) return Response.status(400).entity(val5).build();

            String val6 = Middlewares.validarEntero(d.getId_coctel(), "id_coctel");
            if (!val6.equals("ok")) return Response.status(400).entity(val6).build();

            String val7 = Middlewares.validarEntero(d.getCantidad_coctel(), "cantidad_coctel");
            if (!val7.equals("ok")) return Response.status(400).entity(val7).build();

            boolean creado = dao.crear(d);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Detalle del pedido creado exitosamente.")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al crear detalle del pedido.")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al crear.").build();
        }
    }

    // DELETE: Eliminar detalle por ID
    @DELETE
    @Path("/{id}")
    public Response eliminarDetalle(@PathParam("id") String id) {
        try {
            String validacion = Middlewares.validarEntero(id, "id");
            if (!validacion.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validacion).build();
            }

            boolean eliminado = dao.eliminar(id);
            if (eliminado) {
                return Response.ok("DetallePedido ID " + id + " eliminado exitosamente.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("DetallePedido con ID " + id + " no encontrado.")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al eliminar.").build();
        }
    }
}
