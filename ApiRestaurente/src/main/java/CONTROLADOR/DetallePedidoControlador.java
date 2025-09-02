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
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
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
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo encontrar Detalle Pedido por ID.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // POST: Crear nuevo detalle de pedido
    @POST
    public Response crearDetalle(DetallePedido detallePedido) {
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

            // -------- COMIDA --------
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
                // 🔹 Convertir a String
                detallePedido.setCantidad_comida(String.valueOf(detallePedido.getCantidad_comida()));

                boolean comida = comDAO.existePorId(detallePedido.getId_comida());
                if (comida) {
                    mensaje += ", Comida : #" + detallePedido.getId_comida() + " Existe";
                }
            }

            // -------- BEBIDA --------
            String validaIdBebida = Middlewares.validarEnteroNulo(detallePedido.getId_bebida(), "id_bebida");
            if (!validaIdBebida.equals("ok")) {
                return Response.status(400).entity(validaIdBebida).build();
            } else if (Middlewares.Vacio(detallePedido.getId_bebida())) {
                detallePedido.setId_bebida(null);
                detallePedido.setCantidad_bebida(null);
                detallePedido.setNota_bebida(null);
            } else {
                String validaCantidadBebida = Middlewares.validarEntero(detallePedido.getCantidad_bebida(),
                        "cantidad_bebida");
                if (!validaCantidadBebida.equals("ok")) {
                    return Response.status(400).entity(validaCantidadBebida).build();
                }
                // 🔹 Convertir a String
                detallePedido.setCantidad_bebida(String.valueOf(detallePedido.getCantidad_bebida()));

                boolean bebida = bebDAO.existeBebidaPorId(detallePedido.getId_bebida());
                if (bebida) {
                    mensaje += ", Bebida : #" + detallePedido.getId_bebida() + " Existe";
                }
            }

            // -------- COCTEL --------
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
                // 🔹 Convertir a String
                detallePedido.setCantidad_coctel(String.valueOf(detallePedido.getCantidad_coctel()));

                boolean coctel = cocDAO.existeCoctelPorId(detallePedido.getId_coctel());
                if (coctel) {
                    mensaje += ", Coctel : #" + detallePedido.getId_coctel() + " Existe";
                }
            }

            // -------- CREAR DETALLE --------
            boolean creado = false;
            if (!mensaje.equals(mensajeNoExisteLosdemas)) {
                creado = DetllPedDAO.crear(detallePedido);
                mensaje += " | DetallesPedido Creados con Éxito";
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo crear Detalle Pedido porque no se encontraron ITEMS.\"}")
                        .build();
            }

            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo crear Detalle Pedido.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
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
                String mensaje = "Detalle pedido con el Pedido #" + id + " Eliminado Correctamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo eliminar Detalle Pedido por ID.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }
}
