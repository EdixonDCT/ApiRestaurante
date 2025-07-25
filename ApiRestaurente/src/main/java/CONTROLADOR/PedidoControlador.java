package CONTROLADOR;

import MODELO.Pedido;
import MODELO.PedidoDAO;
import CONTROLADOR.Middlewares;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoControlador {

    private PedidoDAO pedidoDAO = new PedidoDAO();

    @GET
    public Response listarPedidos() {
        try {
            List<Pedido> lista = pedidoDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerPedido(@PathParam("id") String id) {
        try {
            String r = Middlewares.validarEntero(id, "ID");
            if (!r.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            Pedido pedido = pedidoDAO.obtenerPorId(id);
            if (pedido != null) {
                return Response.ok(pedido).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @POST
    public Response crearPedido(Pedido pedido) {
        try {
            // Validaciones una por una
            String r;

            r = Middlewares.validarEntero(pedido.getNumeroMesa(), "Número de mesa");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarFecha(pedido.getFecha(), "Fecha");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarHora(pedido.getHora(), "Hora");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarDouble(pedido.getValorTotal(), "Valor total");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            if (!pedido.getIdCaja().isEmpty()) {
                r = Middlewares.validarEntero(pedido.getIdCaja(), "ID caja");
                if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            r = Middlewares.validarEntero(pedido.getNumeroClientes(), "Número de clientes");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            if (!pedido.getIdReserva().isEmpty()) {
                r = Middlewares.validarEntero(pedido.getIdReserva(), "ID reserva");
                if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            r = Middlewares.validarCorreo(pedido.getCorreoCliente(), "Correo cliente");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarString(pedido.getMetodoPago(), "Método de pago");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            // Si pasa las validaciones, crear
            boolean creado = pedidoDAO.crear(pedido);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Pedido creado exitosamente.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el pedido.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarPedido(@PathParam("id") String id, Pedido pedido) {
        try {
            pedido.setId(id); // Asegurar que el ID venga desde la URL

            // Validaciones una por una
            String r;

            r = Middlewares.validarEntero(pedido.getId(), "ID");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarEntero(pedido.getNumeroMesa(), "Número de mesa");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarFecha(pedido.getFecha(), "Fecha");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarHora(pedido.getHora(), "Hora");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarDouble(pedido.getValorTotal(), "Valor total");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            if (!pedido.getIdCaja().isEmpty()) {
                r = Middlewares.validarEntero(pedido.getIdCaja(), "ID caja");
                if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            r = Middlewares.validarEntero(pedido.getNumeroClientes(), "Número de clientes");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            if (!pedido.getIdReserva().isEmpty()) {
                r = Middlewares.validarEntero(pedido.getIdReserva(), "ID reserva");
                if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            r = Middlewares.validarCorreo(pedido.getCorreoCliente(), "Correo cliente");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            r = Middlewares.validarString(pedido.getMetodoPago(), "Método de pago");
            if (!r.equals("ok")) return Response.status(Response.Status.BAD_REQUEST).entity(r).build();

            // Actualizar
            boolean actualizado = pedidoDAO.actualizar(pedido);
            if (actualizado) {
                return Response.ok().entity("Pedido actualizado exitosamente.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado o no actualizado.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarPedido(@PathParam("id") String id) {
        try {
            String r = Middlewares.validarEntero(id, "ID");
            if (!r.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            boolean eliminado = pedidoDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Pedido eliminado exitosamente.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }
}
