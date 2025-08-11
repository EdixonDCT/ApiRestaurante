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

    private DetallePedidoDAO DetllPedDAO = new DetallePedidoDAO();

    // GET: Listar todos los detalles
    @GET
    public Response listarTodos() {
        try {
            List<DetallePedido> lista = DetllPedDAO.listarTodos();
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
            String validacion = Middlewares.validarEntero(id, "id pedido");
            if (!validacion.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validacion).build();
            }

            List<DetallePedido> detallePedido = DetllPedDAO.obtenerPorId(id);
            if (detallePedido != null) {
                return Response.ok(detallePedido).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("DetallePedido: pedido id #" + id + " no encontrada.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al obtener.").build();
        }
    }

    // POST: Crear nuevo detalle
    @POST
    public Response crearDetalle(DetallePedido detallePedido) {
        try {
            // Validar id_pedido (obligatorio)
            String validaIdPedido = Middlewares.validarEntero(detallePedido.getId_pedido(), "id_pedido");
            if (!validaIdPedido.equals("ok"))
                return Response.status(400).entity(validaIdPedido).build();

            // Validar id_comida y su cantidad
            String validaIdComida = Middlewares.validarEnteroNulo(detallePedido.getId_comida(), "id_comida");
            if (!validaIdComida.equals("ok")) {
                return Response.status(400).entity(validaIdComida).build();
            } else if (Middlewares.Vacio(detallePedido.getId_comida())) {
                detallePedido.setId_comida(null);
                detallePedido.setCantidad_comida(null);
                detallePedido.setNota_comida(null);
            } else {
                String validaCantidadComida = Middlewares.validarEntero(detallePedido.getCantidad_comida(),
                        "cantidad_comida");
                if (!validaCantidadComida.equals("ok"))
                    return Response.status(400).entity(validaCantidadComida).build();
            }

            // Validar id_bebida y su cantidad
            String validaIdBebida = Middlewares.validarEnteroNulo(detallePedido.getId_bebida(), "id_bebida");
            if (!validaIdBebida.equals("ok")) {
                return Response.status(400).entity(validaIdBebida).build();
            } else if (Middlewares.Vacio(detallePedido.getCantidad_bebida())) {
                detallePedido.setId_bebida(null);
                detallePedido.setCantidad_bebida(null);
                detallePedido.setNota_bebida(null);
            } else {
                String validaCantidadBebida = Middlewares.validarEntero(detallePedido.getCantidad_bebida(),
                        "cantidad_bebida");
                if (!validaCantidadBebida.equals("ok"))
                    return Response.status(400).entity(validaCantidadBebida).build();
            }

            // Validar id_coctel y su cantidad
            String validaIdCoctel = Middlewares.validarEnteroNulo(detallePedido.getId_coctel(), "id_coctel");
            if (!validaIdCoctel.equals("ok")) {
                return Response.status(400).entity(validaIdCoctel).build();
            } else if (Middlewares.Vacio(detallePedido.getId_coctel())) {
                detallePedido.setId_coctel(null);
                detallePedido.setCantidad_coctel(null);
                detallePedido.setNota_coctel(null);
            } else {
                String validaCantidadCoctel = Middlewares.validarEntero(detallePedido.getCantidad_coctel(),
                        "cantidad_coctel");
                if (!validaCantidadCoctel.equals("ok"))
                    return Response.status(400).entity(validaCantidadCoctel).build();
            }

            // Crear detalle
            boolean creado = DetllPedDAO.crear(detallePedido);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Detalle_pedido: creado EXITOSAMENTE.")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Detalle_pedido: error al crear.")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Detalle_pedido: error interno al crear.").build();
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

            boolean eliminado = DetllPedDAO.eliminar(id);
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
