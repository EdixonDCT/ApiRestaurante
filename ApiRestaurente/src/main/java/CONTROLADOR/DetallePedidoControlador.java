package CONTROLADOR;

import MODELO.DetallePedido;
import MODELO.DetallePedidoDAO;
import MODELO.BebidaDAO;
import MODELO.ComidaDAO;
import MODELO.CoctelDAO;
import MODELO.PedidoDAO;
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
    private PedidoDAO PedDAO = new PedidoDAO();
    private ComidaDAO comDAO = new ComidaDAO();
    private BebidaDAO bebDAO = new BebidaDAO();
    private CoctelDAO cocDAO = new CoctelDAO();
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
            boolean pedido = PedDAO.existePedidoPorId(detallePedido.getId_pedido());
            String mensaje = "Pedido: #"+detallePedido.getId_pedido()+" Existe";
            String mensajeNoExisteLosdemas = "Pedido: #"+detallePedido.getId_pedido()+" Existe";
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
                boolean comida = comDAO.existePorId(detallePedido.getId_comida());
                if(comida) mensaje += ",Comida : #"+detallePedido.getId_comida()+" Existe";
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
                boolean bebida = bebDAO.existeBebidaPorId(detallePedido.getCantidad_bebida());
                if(bebida) mensaje += ",Bebida : #"+detallePedido.getId_bebida()+" Existe";
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
                boolean coctel = cocDAO.existeCoctelPorId(detallePedido.getCantidad_coctel());
                if(coctel) mensaje += ",Coctel : #"+detallePedido.getCantidad_coctel()+" Existe";
            }
            boolean creado = false;
            if(mensaje != mensajeNoExisteLosdemas)
            {
                creado = DetllPedDAO.crear(detallePedido);
                mensaje += ",Encontrados creando...";
            }
            else
            {
                mensaje = "Error: no se pudo crear DETALLE PEDIDO";
            }
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity(mensaje)
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(mensaje)
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
