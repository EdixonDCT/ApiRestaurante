// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Oficio
import MODELO.Mesa;
// Importa la clase DAO que gestiona la base de datos para Oficio
import MODELO.MesaDAO;
// Importa el middleware de validación para Oficio
import CONTROLADOR.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("mesas")
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class MesaControlador {

    private MesaDAO mesaDAO = new MesaDAO();

    // Método GET para obtener todos los oficios
    @GET
    public Response listarMesas() {
        try {
            List<Mesa> lista = mesaDAO.listarTodos(); // Llama al DAO
            return Response.ok(lista).build(); // Retorna lista en JSON
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500
        }
    }

    // Método GET para obtener un oficio por su ID
    @GET
    @Path("/{numero}")
    public Response obtenerMesa(@PathParam("numero") String numero) {
        try {
            String validaNumero = Middlewares.validarEntero(numero, "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }

            Mesa mesa = mesaDAO.obtenerPorNumero(numero); // Busca por ID
            if (mesa != null) {
                return Response.ok(mesa).build(); // Retorna oficio
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener MESA.")
                        .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build(); // Retorna error 500
        }
    }

    // Método POST para crear un nuevo oficio
    @POST
    public Response crearMesa(Mesa mesa) {
        try {
            // Valida el campo tipo
            String validaNumero = Middlewares.validarEntero(mesa.getNumero(), "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }
            // Valida el campo tipo
            String validaCapacidad = Middlewares.validarEntero(mesa.getCapacidad(), "capacidad");
            if (!validaCapacidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCapacidad).build();
            }
            // Crea el oficio si pasa las validaciones
            boolean creado = mesaDAO.crear(mesa);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Mesa: #" + mesa.getNumero() + " creado con EXITO.")
                        .build(); // Retorna código 201
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear MESA #" + mesa.getNumero())
                        .build(); // Retorna error 500
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar un oficio existente
    @PUT
    @Path("/{numero}")
    public Response actualizarMesa(@PathParam("numero") String numero, Mesa mesa) {
        try {
            mesa.setNumero(numero); // Establece el ID al objeto que se va a actualizar

            String validaNumero = Middlewares.validarEntero(mesa.getNumero(), "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }

            String validaCapacidad = Middlewares.validarEntero(mesa.getCapacidad(), "capacidad");
            if (!validaCapacidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCapacidad).build();
            }

            String validaDisponiblidad = Middlewares.validarBooleano(mesa.getDisponible(), "disponibilidad");
            if (!validaDisponiblidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponiblidad).build();
            }

            // si todo es correcto accede actualizar
            boolean actualizado = mesaDAO.actualizar(mesa);
            if (actualizado) {
                return Response.ok().entity("Mesa: #" + mesa.getNumero() + " actualizado con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: mesa NO ENCONTRADO o NO ACTUALIZADO.")
                        .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/{numero}")
    public Response actualizarDisponibilidadMesa(@PathParam("numero") String numero, Mesa mesa) {
        try {
            mesa.setNumero(numero);// Establece el ID al objeto que se va a actualizar
            String validaNumero = Middlewares.validarEntero(numero, "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }
            String validaDisponiblidad = Middlewares.validarBooleano(mesa.getDisponible(), "disponibilidad");
            if (!validaDisponiblidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponiblidad).build();
            }
            // Actualiza si todo está bien
            boolean actualizado = mesaDAO.actualizarEstado(mesa);
            if (actualizado) {
                return Response.ok().entity("Mesa: actualizacion de #"+numero+" Disponibilidad con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: mesa NO ENCONTRADO o NO ACTUALIZADO.")
                        .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar un oficio por su ID
    @DELETE
    @Path("/{numero}")
    public Response eliminarOficio(@PathParam("numero") String numero) {
        try {
            String validaNumero = Middlewares.validarEntero(numero, "numero");
            if (!validaNumero.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNumero).build();
            }

            boolean eliminado = mesaDAO.eliminar(numero);
            if (eliminado) {
                return Response.ok().entity("Mesa: #" + numero + " Eliminada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: mesa NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
