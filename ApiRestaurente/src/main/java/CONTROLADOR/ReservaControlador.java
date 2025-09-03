// Define el paquete donde se encuentra esta clase, en este caso 'CONTROLADOR'.
package CONTROLADOR;

// Importa las clases necesarias para el funcionamiento del controlador.
import DAO.ClienteDAO;
import DAO.MesaDAO;
import MODELO.Reserva;        // Importa la clase del modelo que representa una reserva.
import DAO.ReservaDAO;     // Importa la clase de acceso a datos para las reservas.
import UTILS.Servicios;  // Importa la clase de servicios que contiene la lógica de negocio.
import UTILS.Middlewares;// Importa la clase con funciones de validación de datos.

// Importaciones de la librería JAX-RS para crear servicios web RESTful.
import javax.ws.rs.*;             // Anotaciones como @Path, @GET, @POST, etc.
import javax.ws.rs.core.MediaType;  // Clase para especificar tipos de medios (ej. application/json).
import javax.ws.rs.core.Response;   // Clase para construir y devolver respuestas HTTP.
import java.util.List;              // Para trabajar con listas de objetos.

// Anotación que define la ruta base del recurso, en este caso "/reservas".
@Path("reservas")
// Anotación que especifica el formato de los datos que producirá el servicio, aquí es JSON.
@Produces(MediaType.APPLICATION_JSON)
// Anotación que especifica el formato de los datos que consume el servicio, también es JSON.
@Consumes(MediaType.APPLICATION_JSON)
// Definición de la clase del controlador.
public class ReservaControlador {

    // Instancia de la clase ReservaDAO para interactuar con la capa de datos.
    private ReservaDAO reservaDAO = new ReservaDAO();
    private MesaDAO mesaDAO = new MesaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO(); // Crea una instancia de 'ClienteDAO'.

    // Método que maneja las solicitudes GET para listar todas las reservas.
    @GET
    public Response listar() {
        try { // Inicia un bloque try-catch para manejar posibles excepciones.
            // Llama al método listarTodos() del DAO para obtener todas las reservas.
            List<Reserva> lista = reservaDAO.listarTodos();
            // Retorna una respuesta HTTP 200 (OK) con la lista de reservas como cuerpo.
            return Response.ok(lista).build();
        } catch (Exception e) { // Captura cualquier excepción genérica.
            // Retorna una respuesta HTTP 500 (Internal Server Error) si algo falla.
            return Response.serverError().build();
        }
    }

    // Método que maneja las solicitudes GET para obtener una reserva por su ID.
    @GET
    // Define una sub-ruta que incluye un parámetro de path dinámico llamado 'id'.
    @Path("/{id}")
    // Anotación para obtener el valor del 'id' de la URL.
    public Response obtener(@PathParam("id") String id) {
        // Valida que el ID recibido sea un número entero.
        String validar = Middlewares.validarEntero(id, "id");
        // Si la validación falla (no retorna "ok")...
        if (!validar.equals("ok")) // ...retorna una respuesta 400 (Bad Request) con el mensaje de error.
        {
            return Response.status(400).entity(validar).build();
        }

        // Llama al DAO para buscar la reserva por el ID.
        Reserva r = reservaDAO.obtenerPorId(id);
        // Si la reserva se encuentra (no es null)...
        if (r != null) // ...retorna una respuesta HTTP 200 (OK) con la reserva.
        {
            return Response.ok(r).build();
        }
        // Si la reserva no se encuentra...
        // ...retorna una respuesta 404 (Not Found) con un mensaje de error.
        return Response.status(404).entity("Reserva no encontrada.").build();
    }

    // Método que maneja las solicitudes POST para crear una nueva reserva.
    @POST
    // El objeto Reserva se deserializa automáticamente del cuerpo de la petición JSON.
    public Response crear(Reserva reserva) {
        try {
            String[] partes = reserva.getCantidadTentativa().split("/");
            // Llama a una función de middleware para validar la cantidad tentativa.
            reserva.setCantidadTentativa(partes[0]);
            String validaCantidadTentativa = Middlewares.validarEntero(reserva.getCantidadTentativa(), "Cantidad Tentativa");
            // Si la validación falla...
            if (!validaCantidadTentativa.equals("ok")) // ...retorna un 400 (Bad Request) con el mensaje de error.
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCantidadTentativa)
                        .build();
            }
            // Llama a un servicio para calcular el precio de la reserva.
            reserva.setPrecio(Servicios.CalcularReserva(reserva.getCantidadTentativa()));
            // Valida el precio calculado.
            String validaPrecio = Middlewares.validarDouble(reserva.getPrecio(), "precio");
            // Si la validación falla...
            if (!validaPrecio.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            // Valida el formato de la fecha tentativa.
            String validarFecha = Middlewares.validarFecha(reserva.getFechaTentativa(), "fecha_tentativa");
            // Si la validación falla...
            if (!validarFecha.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarFecha).build();
            }

            // Valida el formato de la hora tentativa.
            String validarHora = Middlewares.validarHora(reserva.getHoraTentativa(), "hora_tentativa");
            // Si la validación falla...
            if (!validarHora.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarHora).build();
            }

            String validaCorreoCliente = Middlewares.validarCorreo(partes[2], "correo cliente");
            if (!validaCorreoCliente.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreoCliente).build();
            }

            Boolean correoExiste = clienteDAO.obtenerPorCorreoBoolean(partes[2]);
            if (!correoExiste) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"El correo del Cliente no EXISTE.\"}")
                        .build();
            }

            Integer cantidadMesa = mesaDAO.mesaCantidad(partes[1]);
            Integer cantidadCliente = Integer.parseInt(reserva.getCantidadTentativa());

            String validaNumeroMesa = Middlewares.validarEntero(partes[1], "numero mesa");
            if (!validaNumeroMesa.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumeroMesa).build();
            }

            Boolean esMayorOIgual = cantidadMesa >= cantidadCliente;
            if (!esMayorOIgual) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"Seleccione Mesa que sea igual o mayor a la Cantidad Tentativa de Clientes.\"}")
                        .build();
            }
            // Llama al método 'crear' del DAO, que devuelve un array de strings.
            String[] resultado = reservaDAO.crear(reserva);

            // El segundo elemento del array (el ID) es "-1" si hubo un error.
            if (!resultado[1].equals("-1")) {
                // Si la creación fue exitosa, construye una respuesta JSON.
                String mensaje = "Reserva creada EXITOSAMENTE.";
                String json = String.format("{\"Ok\": \"%s\", \"id\": \"%s\"}", mensaje, resultado[1]);

                return Response.status(Response.Status.CREATED)
                        .entity(json)
                        .build();
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo crear Pedido.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

// Método que maneja las solicitudes PUT para actualizar una reserva.
    @PUT
// La ruta incluye el ID de la reserva a actualizar.
    @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, Reserva reserva) {
        try {
            // Asigna el ID del path al objeto reserva para su uso en el DAO.
            reserva.setId(id);
            // Valida el ID recibido.
            String validaId = Middlewares.validarEntero(reserva.getId(), "Id");
            // Si la validación falla...
            if (!validaId.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            // Valida el formato de la fecha.
            String validarFecha = Middlewares.validarFecha(reserva.getFechaTentativa(), "fecha_tentativa");
            // Si la validación falla...
            if (!validarFecha.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarFecha).build();
            }

            // Valida el formato de la hora.
            String validarHora = Middlewares.validarHora(reserva.getHoraTentativa(), "hora_tentativa");
            // Si la validación falla...
            if (!validarHora.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarHora).build();
            } // Llama al método 'actualizar' del DAO.
            boolean actualizado = reservaDAO.actualizar(reserva);
            // Si el DAO devuelve 'true'...
            if (actualizado) {
                // ...la actualización fue exitosa, retorna un 201 (Created).
                String mensaje = "Fecha de la Reserva actualizada EXITOSAMENTE.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo actualizar fecha de la Reserva.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    @PUT
// La ruta incluye el ID de la reserva a actualizar.
    @Path("todo/{id}")
    public Response actualizarTodo(@PathParam("id") String id, Reserva reserva) {
        try {
            // Asigna el ID del path al objeto reserva para su uso en el DAO.
            reserva.setId(id);
            // Valida el ID recibido.
            String validaId = Middlewares.validarEntero(reserva.getId(), "Id");
            // Si la validación falla...
            if (!validaId.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            // Valida la cantidad tentativa.
            String validaCantidadTentativa = Middlewares.validarEntero(reserva.getCantidadTentativa(),
                    "Cantidad Tentativa");
            // Si la validación falla...
            if (!validaCantidadTentativa.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCantidadTentativa)
                        .build();
            }

            // Recalcula el precio de la reserva.
            reserva.setPrecio(Servicios.CalcularReserva(reserva.getCantidadTentativa()));

            // Valida el precio recalculado.
            String validaPrecio = Middlewares.validarDouble(reserva.getPrecio(), "precio");
            // Si la validación falla...
            if (!validaPrecio.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            // Valida el formato de la fecha.
            String validarFecha = Middlewares.validarFecha(reserva.getFechaTentativa(), "fecha_tentativa");
            // Si la validación falla...
            if (!validarFecha.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarFecha).build();
            }

            // Valida el formato de la hora.
            String validarHora = Middlewares.validarHora(reserva.getHoraTentativa(), "hora_tentativa");
            // Si la validación falla...
            if (!validarHora.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarHora).build();
            } // Llama al método 'actualizar' del DAO.
            boolean actualizado = reservaDAO.actualizarTodo(reserva);
            // Si el DAO devuelve 'true'...
            if (actualizado) {
                // ...la actualización fue exitosa, retorna un 201 (Created).
                // ...la actualización fue exitosa, retorna un 201 (Created).
                String mensaje = "Reserva actualizada EXITOSAMENTE.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo actualizar la Reserva.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método que maneja las solicitudes DELETE para eliminar una reserva.
    @DELETE
// La ruta incluye el ID de la reserva a eliminar.
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            // Valida el ID recibido.
            String validarId = Middlewares.validarEntero(id, "id");
            // Si la validación falla...
            if (!validarId.equals("ok")) // ...retorna un 400 (Bad Request).
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();
            } // Llama al método 'eliminar' del DAO.
            boolean eliminado = reservaDAO.eliminar(id);
            // Si el DAO devuelve 'true'...
            if (eliminado) {
                // ...la reserva fue eliminada con éxito, retorna un 200 (OK).
                // ...la actualización fue exitosa, retorna un 201 (Created).
                String mensaje = "Reserva eliminada EXITOSAMENTE.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo eliminar la Reserva.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }
}
