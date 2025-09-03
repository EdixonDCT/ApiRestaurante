package CONTROLADOR; // Declara el paquete donde se encuentra esta clase controladora.

import MODELO.Caja; // Importa la clase 'Caja' que representa el modelo de datos de una caja.
import DAO.CajaDAO; // Importa la clase 'CajaDAO' para interactuar con la base de datos.
import DAO.PedidoDAO;
import MODELO.Trabajador; // Importa la clase 'Trabajador', aunque no se usa directamente en este código.
import UTILS.Middlewares; // Importa la clase 'Middlewares' para las validaciones.

import javax.ws.rs.*; // Importa todas las anotaciones de JAX-RS para servicios web RESTful.
import javax.ws.rs.core.*; // Importa todas las clases de JAX-RS para manejar respuestas HTTP.
import java.util.List; // Importa la clase 'List' para manejar colecciones de objetos.

@Path("caja") // Mapea esta clase a la ruta base '/caja' para peticiones HTTP.
@Produces(MediaType.APPLICATION_JSON) // Configura que los métodos de esta clase producirán respuestas en formato JSON.
@Consumes(MediaType.APPLICATION_JSON) // Configura que los métodos consumirán datos en formato JSON.
public class CajaControlador { // Definición de la clase principal del controlador de caja.

    private CajaDAO cajaDAO = new CajaDAO(); // Crea una instancia de 'CajaDAO' para gestionar la persistencia de datos.
    private PedidoDAO pedidoDAO = new PedidoDAO();

    @GET // Anotación para manejar peticiones HTTP GET.
    public Response listar() { // Método para obtener una lista de todas las cajas.
        try { // Inicia un bloque try-catch para manejar errores.
            List<Caja> lista = cajaDAO.listarTodos(); // Llama al DAO para obtener todas las cajas de la base de datos.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de cajas.
        } catch (Exception e) { // Captura cualquier excepción que pueda ocurrir.
            e.printStackTrace(); // Imprime la traza del error para depuración.
            return Response.serverError().build(); // Retorna un error 500 (Internal Server Error).
        }
    }

    @GET // Anotación para manejar peticiones HTTP GET.
    @Path("/{id}") // Mapea a la ruta '/caja/{id}', donde '{id}' es un parámetro de la URL.
    public Response obtener(@PathParam("id") String id) { // Método para obtener una caja por su ID.
        try { // Inicia el bloque try-catch.
            String validacion = Middlewares.validarEntero(id, "id"); // Valida que el ID proporcionado sea un número entero.
            if (!validacion.equals("ok")) { // Si la validación falla...
                return Response.status(400).entity(validacion).build(); // ...retorna un error 400 (Bad Request).
            }

            Caja caja = cajaDAO.obtenerPorId(id); // Busca la caja en la base de datos usando el ID.
            if (caja != null) {
                return Response.ok(caja).build(); // Si la mesa se encuentra, retorna una respuesta 200 (OK) con la mesa.
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No se pudo obtener Mesa.\"}")
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

    @GET
    @Path("verDisponible") // La URL incluye un parámetro dinámico llamado "numero".
    public Response verDisponible() {
        try {
            Boolean disponibles = cajaDAO.verDisponibleCaja(); // Busca la mesa por su número en la base de datos.
            if (disponibles) {
                String mensaje = "Si hay Cajas Disponibles.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"Error\":\"No hay Cajas Disponibles.\"}")
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

    @POST // Anotación para manejar peticiones HTTP POST para crear un recurso.
    public Response crear(Caja caja) { // Método para crear una nueva caja.
        try { // Inicia el bloque try-catch.
            String validaMontoApertura = Middlewares.validarDoubleCaja(caja.getMontoApertura(), "monto apertura"); // Valida el monto de apertura.
            if (!validaMontoApertura.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoApertura).build(); // ...retorna un error 400.
            }
            String validaCedulaTrabajador = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula trabajador"); // Valida la cédula del trabajador.
            if (!validaCedulaTrabajador.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaCedulaTrabajador).build(); // ...retorna un error 400.
            }
            boolean creado = cajaDAO.crear(caja); // Llama al DAO para crear la caja.
            if (creado) // Si la creación fue exitosa...
            {
                String mensaje = "Caja Aperturada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo aperturar Caja.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    @PUT // Anotación para manejar peticiones HTTP PUT para actualizar un recurso completo.
    @Path("/{id}") // Mapea a la ruta '/caja/{id}'.
    public Response actualizar(@PathParam("id") String id, Caja caja) { // Método para actualizar una caja.
        try { // Inicia el bloque try-catch.
            caja.setId(id); // Asigna el ID de la URL al objeto 'caja'.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida que el ID sea un entero.
            if (!validaID.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaID).build(); // ...retorna un error 400.
            }
            String validaMontoApertura = Middlewares.validarDoubleCaja(caja.getMontoApertura(), "monto_apertura"); // Valida el monto de apertura.
            if (!validaMontoApertura.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoApertura).build(); // ...retorna un error 400.
            }
            String validaMontoCierre = Middlewares.validarDoubleCaja(caja.getMontoCierre(), "monto_cierre"); // Valida el monto de cierre.
            if (!validaMontoCierre.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoCierre).build(); // ...retorna un error 400.
            }
            String validaCedulaTrabajador = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula_trabajador"); // Valida la cédula del trabajador.
            if (!validaCedulaTrabajador.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaCedulaTrabajador).build(); // ...retorna un error 400.
            }
            boolean actualizado = cajaDAO.actualizar(caja); // Llama al DAO para actualizar la caja.
            if (actualizado) // Si la actualización fue exitosa...
            {
                String mensaje = "Caja actualizada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo actualizar Caja.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    @PUT // Anotación para peticiones HTTP PUT.
    @Path("total/{id}") // Mapea a la ruta '/caja/total/{id}'.
    public Response actualizarTotal(@PathParam("id") String id, Caja caja) { // Método para actualizar el total.
        try { // Inicia el bloque try-catch.
            caja.setId(id); // Asigna el ID de la URL al objeto 'caja'.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaID).build(); // ...retorna un error 400.
            }
            String validaFechaApertura = Middlewares.validarFecha(caja.getFechaApertura(), "fecha_apertura"); // Valida la fecha de apertura.
            if (!validaFechaApertura.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaFechaApertura).build(); // ...retorna un error 400.
            }
            String validaHoraApertura = Middlewares.validarHora(caja.getHoraApertura(), "hora_apertura"); // Valida la hora de apertura.
            if (!validaHoraApertura.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaHoraApertura).build(); // ...retorna un error 400.
            }
            String validaMontoApertura = Middlewares.validarDouble(caja.getMontoApertura(), "monto_apertura"); // Valida el monto de apertura.
            if (!validaMontoApertura.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoApertura).build(); // ...retorna un error 400.
            }
            String validaFechaCierre = Middlewares.validarFecha(caja.getFechaCierre(), "fecha_cierre"); // Valida la fecha de cierre.
            if (!validaFechaCierre.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaFechaCierre).build(); // ...retorna un error 400.
            }
            String validaHoraCierre = Middlewares.validarHora(caja.getHoraCierre(), "hora_cierre"); // Valida la hora de cierre.
            if (!validaHoraCierre.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaHoraCierre).build(); // ...retorna un error 400.
            }
            String validaMontoCierre = Middlewares.validarDouble(caja.getMontoCierre(), "monto_cierre"); // Valida el monto de cierre.
            if (!validaMontoCierre.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoCierre).build(); // ...retorna un error 400.
            }
            String validaCedulaTrabajador = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula_trabajador"); // Valida la cédula del trabajador.
            if (!validaCedulaTrabajador.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaCedulaTrabajador).build(); // ...retorna un error 400.
            }
            boolean actualizado = cajaDAO.actualizarTotal(caja); // Llama al DAO para actualizar el total de la caja.
            if (actualizado) // Si la actualización fue exitosa...
            {
                String mensaje = "Caja Actualizada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo actualizar Caja.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    @PATCH // Anotación para peticiones HTTP PATCH para actualizaciones parciales.
    @Path("/{id}") // Mapea a la ruta '/caja/{id}'.
    public Response parchearCierre(@PathParam("id") String id, Caja caja) { // Método para actualizar solo el cierre de la caja.
        try { // Inicia el bloque try-catch.
            caja.setId(id); // Asigna el ID de la URL al objeto 'caja'.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaID).build(); // ...retorna un error 400.
            }
            String validaMontoCierre = Middlewares.validarDoubleCaja(caja.getMontoCierre(), "monto_cierre"); // Valida el monto de cierre.
            if (!validaMontoCierre.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoCierre).build(); // ...retorna un error 400.
            }
            boolean pedidosEn0DeLaCaja = pedidoDAO.verFacturadosCerrarCaja(caja.getId());
            if (pedidosEn0DeLaCaja) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"Hay pedidos de la caja que estan sin platillos,Eliminar pedidos o facturar platillos antes de Cerrar Caja.\"}")
                        .build();
            }

            boolean parchear = cajaDAO.actualizarCierre(caja); // Llama al DAO para actualizar el cierre.
            if (parchear) // Si la actualización fue exitosa...
            {
                boolean facturadoPedidos = cajaDAO.CerrarCajaPedidos(caja.getId());
                String mensaje = "Caja Cerrada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo cerrar Caja.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    @PATCH // Anotación para peticiones HTTP PATCH.
    @Path("/apertura/{id}") // Mapea a la ruta '/caja/apertura/{id}'.
    public Response parchearApertura(@PathParam("id") String id, Caja caja) { // Método para actualizar solo el monto de apertura.
        try { // Inicia el bloque try-catch.
            caja.setId(id); // Asigna el ID de la URL al objeto 'caja'.

            String validaID = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaID.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaID).build(); // ...retorna un error 400.
            }
            String validaMontoApertura = Middlewares.validarDoubleCaja(caja.getMontoApertura(), "monto apertura"); // Valida el monto de apertura.
            if (!validaMontoApertura.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaMontoApertura).build(); // ...retorna un error 400.
            }
            boolean parchear = cajaDAO.actualizarApertura(caja); // Llama al DAO para actualizar la apertura.
            if (parchear) // Si la actualización fue exitosa...
            {
                String mensaje = "Caja monto apertura actualizada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo actualizar monto apertura Caja.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    @DELETE // Anotación para manejar peticiones HTTP DELETE.
    @Path("/{id}") // Mapea a la ruta '/caja/{id}'.
    public Response eliminar(@PathParam("id") String id) { // Método para eliminar una caja.
        try { // Inicia el bloque try-catch.
            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) // Si la validación falla...
            {
                return Response.status(400).entity(validaId).build(); // ...retorna un error 400.
            }
            boolean cajaPedido = cajaDAO.cajaEnPedido(id);
            if (cajaPedido) {
                String mensaje = "Caja #" + id + " no se puede eliminar porque esta relacionado a un Pedido.";
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"" + mensaje + "\"}").build();
            }
            boolean eliminado = cajaDAO.eliminar(id); // Llama al DAO para eliminar la caja.
            if (eliminado) // Si la eliminación fue exitosa...
            {
                String mensaje = "Caja eliminada Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo eliminar Caja.\"}")
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
