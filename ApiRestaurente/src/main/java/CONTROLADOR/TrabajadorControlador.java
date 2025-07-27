// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Oficio
import MODELO.Trabajador;
// Importa la clase DAO que gestiona la base de datos para Oficio
import MODELO.TrabajadorDAO;
// Importa el middleware de validación para Oficio
import CONTROLADOR.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("trabajadores")
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class TrabajadorControlador {

    // Instancia del DAO para poder acceder a la base de datos
    private TrabajadorDAO trabajadorDAO = new TrabajadorDAO();

    // Método GET para obtener todos los oficios
    @GET
    public Response listarTrabajadores() {
        try {
            List<Trabajador> lista = trabajadorDAO.listarTodos(); // Llama al DAO
            return Response.ok(lista).build(); // Retorna lista en JSON
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500 
        }
    }
    
    // Método GET para obtener un oficio por su ID
    @GET
    @Path("/{cedula}")
    public Response obtenerTrabajador(@PathParam("cedula") String cedula){
        try {
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            
            Trabajador trabajador = trabajadorDAO.obtenerPorCedula(cedula); // Busca por cedula
            if (trabajador != null) {
                return Response.ok(trabajador).build(); // Retorna trabajador
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: no se pudo obtener TRABAJADOR.")
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
    public Response crearTrabajador(Trabajador trabajador) {
        try {

            // Valida el campo Cedula
            String validaCedula = Middlewares.validarEntero(trabajador.getCedula(), "cedula");
            if (!validaCedula.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            
            // Valida el campo Nombre
            String validarNombre = Middlewares.validarString(trabajador.getNombre(), "nombre");
            if (!validarNombre.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNombre).build();

            // Valida el campo Apellido
            String validarApellido = Middlewares.validarString(trabajador.getApellido(), "apellido");
            if (!validarApellido.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarApellido).build();

            // Valida el campo Nacimiento
            String validarNacimiento = Middlewares.validarFecha(trabajador.getNacimiento(), "nacimiento");
            if (!validarNacimiento.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNacimiento).build();
            
            // Valida el campo Codigo Oficio
            String validaCodigoOficio = Middlewares.validarEntero(trabajador.getIdOficio(), "idOficio");
            if (!validaCodigoOficio.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigoOficio).build();
                        
            // Crea el oficio si pasa las validaciones
            boolean creado = trabajadorDAO.crear(trabajador);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                               .entity("Trabajador: " + trabajador.getNombre()+" "+trabajador.getApellido()+" creado con EXITO.")
                               .build(); // Retorna código 201
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("Error: no se pudo crear TRABAJADOR.")
                               .build(); // Retorna error 500
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/{cedula}")
    public Response actualizarTrabajador(@PathParam("cedula") String cedula, Trabajador trabajador) {
        try {
            trabajador.setCedula(cedula); // Establece el ID al objeto que se va a actualizar
            // Actualiza si todo está bien
            
            // Valida el campo Cedula
             String validaCedula = Middlewares.validarEntero(trabajador.getCedula(), "cedula");
            if (!validaCedula.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            
            // Valida el campo Nombre
            String validarNombre = Middlewares.validarString(trabajador.getNombre(), "nombre");
            if (!validarNombre.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNombre).build();

            // Valida el campo Apellido
            String validarApellido = Middlewares.validarString(trabajador.getApellido(), "apellido");
            if (!validarApellido.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarApellido).build();

            // Valida el campo Nacimiento
            String validarNacimiento = Middlewares.validarFecha(trabajador.getNacimiento(), "nacimiento");
            if (!validarNacimiento.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNacimiento).build();
            
            // Valida el campo Codigo Oficio
            String validaCodigoOficio = Middlewares.validarEntero(trabajador.getIdOficio(), "idOficio");
            if (!validaCodigoOficio.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigoOficio).build();

            boolean actualizado = trabajadorDAO.actualizar(trabajador);
            if (actualizado) {
                return Response.ok().entity("Trabajador: " + trabajador.getNombre()+" "+trabajador.getApellido()+" actualizado con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: trabajador NO ENCONTRADO o NO ACTUALIZADO.")
                               .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/{cedula}")
    public Response actualizarFotoTrabajador(@PathParam("cedula") String cedula, Trabajador trabajador) {
        try {
            trabajador.setCedula(cedula); // Establece el ID al objeto que se va a actualizar
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            // Actualiza si todo está bien
            boolean actualizado = trabajadorDAO.actualizarFoto(trabajador);
            if (actualizado) {
                return Response.ok().entity("Trabajador: actualizacion de foto con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: trabajador NO ENCONTRADO o NO ACTUALIZADO.")
                               .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
    // Método DELETE para eliminar un oficio por su ID
    @DELETE
    @Path("/{cedula}")
    public Response eliminarTrabajador(@PathParam("cedula") String cedula) {
        try {
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            boolean eliminado = trabajadorDAO.eliminar(cedula);
            if (eliminado) {
                return Response.ok().entity("Trabajador: eliminado EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Error: trabajador NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}