// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa el modelo Pedido y su DAO (Data Access Object)
import MODELO.Pedido;
import DAO.PedidoDAO;
// Importa la clase de validación (Middlewares)
import Utils.Middlewares;

import java.util.List;

// Importa las librerías de JAX-RS para crear servicios web RESTful
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para este controlador. Todas las peticiones a "/pedidos"
// serán manejadas por esta clase.
@Path("pedidos")
// Indica que el controlador produce respuestas en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que el controlador consume peticiones en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoControlador {

    // Instancia del DAO para interactuar con la base de datos de pedidos
    private PedidoDAO pedidoDAO = new PedidoDAO();

    // --- Métodos GET ---

    // Maneja la petición GET a "/pedidos"
    // Retorna una lista de todos los pedidos
    @GET
    public Response listarPedidos() {
        try {
            // Llama al DAO para obtener todos los pedidos
            List<Pedido> lista = pedidoDAO.listarTodos();
            // Retorna una respuesta 200 (OK) con la lista de pedidos en JSON
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, retorna una respuesta 500 (Internal Server Error)
            return Response.serverError().build();
        }
    }

    // Maneja la petición GET a "/pedidos/{id}"
    // Retorna un pedido específico por su ID
    @GET
    @Path("/{id}")
    public Response obtenerPedido(@PathParam("id") String id) {
        try {
            // Valida que el ID del pedido sea un número entero válido
            String validaId = Middlewares.validarEntero(id, "ID");
            if (!validaId.equals("ok")) {
                // Si el ID es inválido, retorna una respuesta 400 (Bad Request)
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            // Busca el pedido por su ID
            Pedido pedido = pedidoDAO.obtenerPorId(id);
            if (pedido != null) {
                // Si se encuentra, retorna una respuesta 200 (OK) con el pedido
                return Response.ok(pedido).build();
            } else {
                // Si no se encuentra, retorna una respuesta 404 (Not Found)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // --- Métodos POST ---

    // Maneja la petición POST a "/pedidos"
    // Crea un nuevo pedido a partir de los datos recibidos en el cuerpo de la petición
    @POST
    public Response crearPedido(Pedido pedido) {
        try {
            // Valida cada uno de los campos del objeto Pedido
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

            // Si todas las validaciones son correctas, intenta crear el pedido
            String[] resultado = pedidoDAO.crear(pedido);

            // El resultado del DAO contiene un mensaje y el nuevo ID.
            if (!resultado[1].equals("-1")) {
                // Si se creó, retorna una respuesta 201 (Created) con un JSON que incluye el ID
                String json = String.format("{\"mensaje\": \"%s\", \"id\": \"%s\"}", resultado[0], resultado[1]);
                return Response.status(Response.Status.CREATED).entity(json).build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el pedido.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Maneja la petición POST a "/pedidos/reserva"
    // Crea un pedido a partir de una reserva, validando los campos específicos para esta acción
    @POST
    @Path("/reserva")
    public Response crearPedidoReserva(Pedido pedido) {
        try {
            // Validaciones para los campos de reserva
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

            // Llama al DAO para crear el pedido de reserva
            boolean creado = pedidoDAO.crearReserva(pedido);
            if (creado) {
                // Retorna una respuesta 201 (Created)
                return Response.status(Response.Status.CREATED)
                        .entity("Pedido: reserva creada EXITOSAMENTE.").build();
            } else {
                // Retorna una respuesta 500 si la creación falla
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el pedido reserva.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // --- Métodos PUT y PATCH (Actualizaciones) ---

    // Maneja la petición PUT a "/pedidos/{id}"
    // Actualiza todos los campos de un pedido existente
    @PUT
    @Path("/{id}")
    public Response actualizarPedido(@PathParam("id") String id, Pedido pedido) {
        try {
            // Asigna el ID del path al objeto Pedido
            pedido.setId(id);

            // Realiza las validaciones de todos los campos que se pueden actualizar
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

            // Intenta actualizar el pedido
            boolean actualizado = pedidoDAO.actualizar(pedido);
            if (actualizado) {
                // Si la actualización es exitosa, retorna 200 (OK)
                return Response.ok().entity("Pedido: actualizado EXITOSAMENTE.").build();
            } else {
                // Si el pedido no existe, retorna 404 (Not Found)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido no encontrado o no actualizado.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Maneja la petición PUT a "/pedidos/reserva/{id}"
    // Actualiza un pedido de reserva existente
    @PUT
    @Path("/reserva/{id}")
    public Response actualizarPedidoReserva(@PathParam("id") String id, Pedido pedido) {
        try {
            pedido.setId(id); // Asigna el ID del path

            // Realiza las validaciones para los campos de reserva
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

            // Llama al DAO para actualizar la reserva
            boolean actualizado = pedidoDAO.actualizarReserva(pedido);
            if (actualizado) {
                // Si se actualiza, retorna 200 (OK)
                return Response.ok().entity("Pedido: reserva actualizada EXITOSAMENTE.").build();
            } else {
                // Si no se encuentra, retorna 404 (Not Found)
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: pedido reserva no encontrado o no actualizada.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Maneja la petición PUT a "/pedidos/reservaEditar/{id}"
    // Actualiza campos específicos de un pedido de reserva
    @PUT
    @Path("/reservaEditar/{id}")
    public Response EditarPedidoReserva(@PathParam("id") String id, Pedido pedido) {
        try {
            pedido.setId(id);

            // Validaciones para los campos a editar
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

            // Llama al DAO para editar la reserva
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

    // Maneja la petición PATCH a "/pedidos/{id}"
    // Actualiza el total de un pedido
    @PATCH
    @Path("/{id}")
    public Response PatchTotal(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            // Llama al DAO para actualizar el total del pedido
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

    // Maneja la petición PATCH a "/pedidos/facturar/{id}"
    // Marca un pedido como facturado
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
    
    // Maneja la petición PATCH a "/pedidos/eliminadosi/{id}"
    // Realiza un "soft delete" (eliminación suave) de un pedido
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
    
    // Maneja la petición PATCH a "/pedidos/eliminadono/{id}"
    // Revierte un "soft delete" (restaura un pedido)
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

    // --- Métodos DELETE ---

    // Maneja la petición DELETE a "/pedidos/{id}"
    // Elimina un pedido de forma permanente por su ID
    @DELETE
    @Path("/{id}")
    public Response eliminarPedido(@PathParam("id") String id) {
        try {
            // Valida el ID antes de eliminar
            String r = Middlewares.validarEntero(id, "ID");
            if (!r.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(r).build();
            }

            // Llama al DAO para eliminar el pedido
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
