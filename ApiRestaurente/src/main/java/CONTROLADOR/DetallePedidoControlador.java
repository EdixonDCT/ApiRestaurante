// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa las clases necesarias para el modelo, DAO y validaciones
import MODELO.DetallePedido; // Clase que representa el objeto DetallePedido.
import DAO.DetallePedidoDAO; // Clase DAO para interactuar con la base de datos de detalles de pedido.
import DAO.BebidaDAO; // Clase DAO para bebidas, usada para validaciones.
import DAO.ComidaDAO; // Clase DAO para comidas, usada para validaciones.
import DAO.CoctelDAO; // Clase DAO para cócteles, usada para validaciones.
import DAO.PedidoDAO; // Clase DAO para pedidos, usada para validaciones.
import UTILS.Middlewares; // Clase que contiene métodos para validar datos.

import java.util.List; // Interfaz para manejar listas de objetos.
import javax.ws.rs.*; // Anotaciones para definir endpoints REST.
import javax.ws.rs.core.MediaType; // Para especificar tipos de medios (ej. JSON).
import javax.ws.rs.core.Response; // Para construir y devolver respuestas HTTP.

// Define la ruta base para acceder a los métodos del controlador
@Path("detallePedido") // Todos los endpoints de esta clase estarán bajo la ruta "/detallePedido".
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON) // Configura el tipo de respuesta a JSON.
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Configura el tipo de petición que se espera a JSON.
public class DetallePedidoControlador {

    // Instancias de los DAOs para interactuar con la base de datos
    private DetallePedidoDAO DetllPedDAO = new DetallePedidoDAO();
    private PedidoDAO PedDAO = new PedidoDAO();
    private ComidaDAO comDAO = new ComidaDAO();
    private BebidaDAO bebDAO = new BebidaDAO();
    private CoctelDAO cocDAO = new CoctelDAO();

    // GET: Listar todos los detalles de pedidos
    @GET
    public Response listarTodos() {
        try { // Bloque try-catch para manejar errores.
            List<DetallePedido> lista = DetllPedDAO.listarTodos(); // Llama al DAO para obtener todos los detalles de pedidos.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista.
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza del error para depuración.
            return Response.serverError().entity("Error interno al listar.").build(); // Retorna una respuesta 500 (Internal Server Error).
        }
    }

    // GET: Obtener un detalle de pedido por ID de pedido
    @GET
    @Path("/{id}") // La ruta incluye un parámetro dinámico llamado "id".
    public Response obtenerPorId(@PathParam("id") String id) { // El valor del "id" de la URL se inyecta en la variable 'id'.
        try {
            // Se valida que el id sea un número entero
            String validacion = Middlewares.validarEntero(id, "id pedido"); // Valida que el ID sea un número entero.
            if (!validacion.equals("ok")) { // Si la validación no es exitosa...
                return Response.status(Response.Status.BAD_REQUEST).entity(validacion).build(); // ...retorna una respuesta 400 (Bad Request).
            }

            List<DetallePedido> detallePedido = DetllPedDAO.obtenerPorId(id); // Busca el detalle de pedido por el ID.
            if (detallePedido != null) { // Si se encuentra el detalle de pedido...
                return Response.ok(detallePedido).build(); // ...retorna una respuesta 200 (OK) con el objeto DetallePedido.
            } else { // Si no se encuentra...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna una respuesta 404 (Not Found).
                        .entity("DetallePedido: pedido id #" + id + " no encontrada.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno al obtener.").build();
        }
    }

    // POST: Crear nuevo detalle de pedido
    @POST
    public Response crearDetalle(DetallePedido detallePedido) { // Recibe un objeto DetallePedido del cuerpo de la petición.
        try {
            
            // Validar id_pedido (obligatorio y debe ser un entero)
            String validaIdPedido = Middlewares.validarEntero(detallePedido.getId_pedido(), "id_pedido");
            if (!validaIdPedido.equals("ok")) {
                return Response.status(400).entity(validaIdPedido).build();
            }
            
            // Verificar si el pedido existe
            boolean pedido = PedDAO.existePedidoPorId(detallePedido.getId_pedido());
            String mensaje = "Pedido: #" + detallePedido.getId_pedido() + " Existe";
            String mensajeNoExisteLosdemas = "Pedido: #" + detallePedido.getId_pedido() + " Existe";
            
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
                if (!validaCantidadComida.equals("ok")) {
                    return Response.status(400).entity(validaCantidadComida).build();
                }
                boolean comida = comDAO.existePorId(detallePedido.getId_comida());
                if (comida) {
                    mensaje += ", Comida : #" + detallePedido.getId_comida() + " Existe";
                }
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
                if (!validaCantidadBebida.equals("ok")) {
                    return Response.status(400).entity(validaCantidadBebida).build();
                }
                boolean bebida = bebDAO.existeBebidaPorId(detallePedido.getCantidad_bebida());
                if (bebida) {
                    mensaje += ", Bebida : #" + detallePedido.getId_bebida() + " Existe";
                }
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
                if (!validaCantidadCoctel.equals("ok")) {
                    return Response.status(400).entity(validaCantidadCoctel).build();
                }
                boolean coctel = cocDAO.existeCoctelPorId(detallePedido.getCantidad_coctel());
                if (coctel) {
                    mensaje += ", Coctel : #" + detallePedido.getCantidad_coctel() + " Existe";
                }
            }
            
            boolean creado = false;
            // Solo se intenta crear si se encontró alguna comida, bebida o cóctel
            if (!mensaje.equals(mensajeNoExisteLosdemas)) {
                creado = DetllPedDAO.crear(detallePedido);
                mensaje += ", Encontrados creando...";
            } else {
                mensaje = "Error: no se pudo crear DETALLE PEDIDO, ya que no se encontraron items.";
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

    // DELETE: Eliminar detalle por ID de pedido
    @DELETE
    @Path("/{id}")
    public Response eliminarDetalle(@PathParam("id") String id) {
        try {
            // Validar el ID
            String validacion = Middlewares.validarEntero(id, "id");
            if (!validacion.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validacion).build();
            }

            // Llama al DAO para eliminar el detalle
            boolean eliminado = DetllPedDAO.eliminar(id);
            if (eliminado) { // Si la eliminación fue exitosa...
                return Response.ok("DetallePedido ID " + id + " eliminado exitosamente.").build();
            } else { // Si no se encuentra...
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
