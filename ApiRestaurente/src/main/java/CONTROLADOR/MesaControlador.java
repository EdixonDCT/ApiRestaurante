// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Mesa
import MODELO.Mesa;
// Importa la clase DAO que gestiona la base de datos para Mesa
import DAO.MesaDAO;
// Importa la clase de validación (middlewares)
import UTILS.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("mesas") // La URL base para este controlador será "/mesas".
// Indica que las respuestas de los métodos de esta clase serán en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones que reciba esta clase deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class MesaControlador {

    // Instancia del DAO para interactuar con la base de datos de mesas
    private MesaDAO mesaDAO = new MesaDAO();

    // Método GET para obtener todas las mesas
    @GET
    public Response listarMesas() {
        try {
            List<Mesa> lista = mesaDAO.listarTodos(); // Llama al DAO para obtener todas las mesas.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de mesas en formato JSON.
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error para fines de depuración.
            return Response.serverError().build(); // Retorna una respuesta 500 (Internal Server Error) genérica.
        }
    }

    // Método GET para obtener una mesa por su número
    @GET
    @Path("/{numero}") // La URL incluye un parámetro dinámico llamado "numero".
    public Response obtenerMesa(@PathParam("numero") String numero) {
        try {
            // Se valida que el parámetro "numero" sea un número entero
            String validaNumero = Middlewares.validarEntero(numero, "numero");
            if (!validaNumero.equals("ok")) {
                // Si la validación falla, retorna una respuesta 400 (Bad Request).
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }

            Mesa mesa = mesaDAO.obtenerPorNumero(numero); // Busca la mesa por su número en la base de datos.
            if (mesa != null) {
                return Response.ok(mesa).build(); // Si la mesa se encuentra, retorna una respuesta 200 (OK) con la mesa.
            } else {
                // Si la mesa no se encuentra, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener MESA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Retorna una respuesta 500 (Internal Server Error) si ocurre un error inesperado.
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    // Método POST para crear una nueva mesa
    @POST
    public Response crearMesa(Mesa mesa) { // El objeto Mesa se recibe del cuerpo de la petición.
        try {
            // Valida que el número de la mesa sea un entero
            String validaNumero = Middlewares.validarEntero(mesa.getNumero(), "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }
            // Valida que la capacidad de la mesa sea un entero
            String validaCapacidad = Middlewares.validarEntero(mesa.getCapacidad(), "capacidad");
            if (!validaCapacidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCapacidad).build();
            }
            
            // Si las validaciones son correctas, intenta crear la mesa en la base de datos
            boolean creado = mesaDAO.crear(mesa);
            if (creado) {
                // Si la creación es exitosa, retorna una respuesta 201 (Created).
                return Response.status(Response.Status.CREATED)
                        .entity("Mesa: #" + mesa.getNumero() + " creado con EXITO.")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear MESA #" + mesa.getNumero())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar una mesa existente
    @PUT
    @Path("/{numero}")
    public Response actualizarMesa(@PathParam("numero") String numero, Mesa mesa) {
        try {
            mesa.setNumero(numero); // Asigna el número de la URL al objeto mesa para la actualización.

            // Valida que el número de la mesa sea un entero
            String validaNumero = Middlewares.validarEntero(mesa.getNumero(), "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }

            // Valida que la capacidad de la mesa sea un entero
            String validaCapacidad = Middlewares.validarEntero(mesa.getCapacidad(), "capacidad");
            if (!validaCapacidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCapacidad).build();
            }
            
            // Intenta actualizar la mesa en la base de datos
            boolean actualizado = mesaDAO.actualizar(mesa);
            if (actualizado) {
                // Si la actualización es exitosa, retorna una respuesta 200 (OK).
                return Response.ok().entity("Mesa: #" + mesa.getNumero() + " actualizado con EXITO.").build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: mesa NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
    
    // Método PATCH para actualizar parcialmente el estado de disponibilidad de una mesa
    @PATCH
    @Path("/{numero}")
    public Response actualizarDisponibilidadMesa(@PathParam("numero") String numero, Mesa mesa) {
        try {
            mesa.setNumero(numero); // Asigna el número de la URL al objeto mesa.
            // Valida que el número de la mesa sea un entero
            String validaNumero = Middlewares.validarEntero(numero, "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }
            // Valida que el valor de disponibilidad sea un booleano válido
            String validaDisponiblidad = Middlewares.validarBooleano(mesa.getDisponible(), "disponibilidad");
            if (!validaDisponiblidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponiblidad).build();
            }
            
            // Si las validaciones son correctas, actualiza el estado de la mesa
            boolean actualizado = mesaDAO.actualizarEstado(mesa);
            if (actualizado) {
                // Si la actualización es exitosa, retorna una respuesta 200 (OK).
                return Response.ok().entity("Mesa: actualizacion de #" + numero + " Disponibilidad con EXITO.").build();
            } else {
                // Si no se encuentra o no se actualiza, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: mesa NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar una mesa por su número
    @DELETE
    @Path("/{numero}")
    public Response eliminarOficio(@PathParam("numero") String numero) {
        try {
            // Valida que el número de la mesa sea un entero
            String validaNumero = Middlewares.validarEntero(numero, "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }

            boolean eliminado = mesaDAO.eliminar(numero); // Intenta eliminar la mesa.
            if (eliminado) {
                // Si se elimina con éxito, retorna una respuesta 200 (OK).
                return Response.ok().entity("Mesa: #" + numero + " Eliminada EXITOSAMENTE.").build();
            } else {
                // Si no se encuentra la mesa para eliminar, retorna una respuesta 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: mesa NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
