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
            String validaId = Middlewares.validarEntero(id, "ID");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
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
            String validaNumeroMesa = Middlewares.validarEntero(pedido.getNumeroMesa(), "numero mesa");
            if (!validaNumeroMesa.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroMesa).build();
            }
            String validaIdCaja = Middlewares.validarEntero(pedido.getIdCaja(), "id caja");
            if (!validaIdCaja.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaIdCaja).build();
            }
            String validaNumeroClientes = Middlewares.validarEntero(pedido.getNumeroClientes(), "numero de clientes");
            if (!validaNumeroClientes.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroClientes).build();
            }
            String validaCorreoCliente = Middlewares.validarCorreo(pedido.getCorreoCliente(), "correo cliente");
            if (!validaCorreoCliente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreoCliente).build();
            }
            String validaMetodoPago = Middlewares.validarString(pedido.getMetodoPago(), "numero mesa");
            if (!validaMetodoPago.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaMetodoPago).build();
            }

            String[] resultado = pedidoDAO.crear(pedido);

            if (!resultado[1].equals("-1")) {
                String json = String.format("{\"mensaje\": \"%s\", \"id\": \"%s\"}", resultado[0], resultado[1]);
                return Response.status(Response.Status.CREATED).entity(json).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el pedido.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @POST
    @Path("/reserva")
    public Response crearPedidoReserva(Pedido pedido) {
        try {
            String validaNumeroMesa = Middlewares.validarEntero(pedido.getNumeroMesa(), "numero mesa");
            if (!validaNumeroMesa.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroMesa).build();
            }
            String validaNumeroClientes = Middlewares.validarEntero(pedido.getNumeroClientes(), "numero de clientes");
            if (!validaNumeroClientes.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroClientes).build();
            }
            String validaIdReserva = Middlewares.validarEntero(pedido.getIdReserva(), "id reserva");
            if (!validaIdReserva.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaIdReserva).build();
            }
            String validaCorreoCliente = Middlewares.validarCorreo(pedido.getCorreoCliente(), "correo cliente");
            if (!validaCorreoCliente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreoCliente).build();
            }

            boolean creado = pedidoDAO.crearReserva(pedido);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Pedido: reserva creada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el pedido reserva.").build();
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

            String validaNumeroMesa = Middlewares.validarEntero(pedido.getNumeroMesa(), "numero mesa");
            if (!validaNumeroMesa.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroMesa).build();
            }
            String validaIdCaja = Middlewares.validarEntero(pedido.getIdCaja(), "id caja");
            if (!validaIdCaja.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaIdCaja).build();
            }
            String validaNumeroClientes = Middlewares.validarEntero(pedido.getNumeroClientes(), "numero de clientes");
            if (!validaNumeroClientes.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroClientes).build();
            }
            String validaCorreoCliente = Middlewares.validarCorreo(pedido.getCorreoCliente(), "correo cliente");
            if (!validaCorreoCliente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreoCliente).build();
            }
            String validaMetodoPago = Middlewares.validarString(pedido.getMetodoPago(), "numero mesa");
            if (!validaMetodoPago.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaMetodoPago).build();
            }
            String validaId = Middlewares.validarEntero(pedido.getId(), "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean actualizado = pedidoDAO.actualizar(pedido);
            if (actualizado) {
                return Response.ok().entity("Pedido: actualizado EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado o no actualizado.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/reserva/{id}")
    public Response actualizarPedidoReserva(@PathParam("id") String id, Pedido pedido) {
        try {
            pedido.setId(id); // Asegurar que el ID venga desde la URL

            String validaIdCaja = Middlewares.validarEntero(pedido.getIdCaja(), "id caja");
            if (!validaIdCaja.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaIdCaja).build();
            }
            String validaNumeroClientes = Middlewares.validarEntero(pedido.getNumeroClientes(), "numero de clientes");
            if (!validaNumeroClientes.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroClientes).build();
            }
            String validaMetodoPago = Middlewares.validarString(pedido.getMetodoPago(), "metodo de pago");
            if (!validaMetodoPago.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaMetodoPago).build();
            }
            String validaId = Middlewares.validarEntero(pedido.getId(), "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean actualizado = pedidoDAO.actualizarReserva(pedido);
            if (actualizado) {
                return Response.ok().entity("Pedido: reserva actualizada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido reserva no encontrado o no actualizada.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/reservaEditar/{id}")
    public Response EditarPedidoReserva(@PathParam("id") String id, Pedido pedido) {
        try {
            pedido.setId(id); // Asegurar que el ID venga desde la URL

            String validaNumeroClientes = Middlewares.validarEntero(pedido.getNumeroClientes(), "numero de clientes");
            if (!validaNumeroClientes.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroClientes).build();
            }
            String validaNumeroMesa = Middlewares.validarEntero(pedido.getNumeroMesa(), "numero mesa");
            if (!validaNumeroMesa.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroMesa).build();
            }
            String validaCorreoCliente = Middlewares.validarCorreo(pedido.getCorreoCliente(), "correo cliente");
            if (!validaCorreoCliente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreoCliente).build();
            }
            String validaId = Middlewares.validarEntero(pedido.getId(), "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean actualizado = pedidoDAO.EditarReserva(pedido);
            if (actualizado) {
                return Response.ok().entity("Pedido: reserva actualizada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido reserva no encontrado o no actualizada.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response PatchTotal(@PathParam("id") String id) {
        try {

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean actualizado = pedidoDAO.PatchTotal(id);
            if (actualizado) {
                return Response.ok().entity("Pedido: total del pedido ACTUALIZADO EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado o no actualizado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("facturar/{id}")
    public Response PatchFacturar(@PathParam("id") String id) {
        try {

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }
            boolean actualizado = pedidoDAO.patchFacturar(id);
            if (actualizado) {
                return Response.ok().entity("Pedido: facturado EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado o no actualizado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }
    @PATCH
    @Path("eliminadosi/{id}")
    public Response PatchEliminadoSI(@PathParam("id") String id) {
        try {

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }
            boolean actualizado = pedidoDAO.patchBorradoSi(id);
            if (actualizado) {
                return Response.ok().entity("Pedido: eliminado suave EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado o no actualizado.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }
    @PATCH
    @Path("eliminadono/{id}")
    public Response PatchEliminadoNO(@PathParam("id") String id) {
        try {

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }
            boolean actualizado = pedidoDAO.patchBorradoNo(id);
            if (actualizado) {
                return Response.ok().entity("Pedido: rescatado del eliminado suave EXITOSAMENTE.").build();
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
