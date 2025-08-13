// Paquete que agrupa a todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Trabajador.
import MODELO.Trabajador;
// Importa la clase DAO que gestiona la base de datos para Trabajador.
import MODELO.TrabajadorDAO;
// Importa el middleware de validación para Trabajador.
import CONTROLADOR.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST.
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador.
@Path("trabajadores")
// Indica que las respuestas serán en formato JSON.
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON.
@Consumes(MediaType.APPLICATION_JSON)
public class TrabajadorControlador {

    // Instancia del DAO para poder acceder a la base de datos.
    private TrabajadorDAO trabajadorDAO = new TrabajadorDAO();

    // Método GET para obtener todos los trabajadores.
    @GET
    public Response listarTrabajadores() {
        try {
            // Llama al método listarTodos() del DAO para obtener la lista de trabajadores.
            List<Trabajador> lista = trabajadorDAO.listarTodos();
            // Retorna una respuesta 200 (OK) con la lista de trabajadores en formato JSON.
            return Response.ok(lista).build();
        } catch (Exception e) {
            // Imprime la traza de la excepción para fines de depuración.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error) genérico.
            return Response.serverError().build();
        }
    }

    // Método GET para obtener un trabajador por su cédula.
    @GET
    // Define una sub-ruta con un parámetro de path dinámico llamado 'cedula'.
    @Path("/{cedula}")
    // Obtiene el valor de 'cedula' de la URL.
    public Response obtenerTrabajador(@PathParam("cedula") String cedula) {
        try {
            // Valida que el campo 'cedula' sea un número entero.
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                // Si la validación falla, retorna un 400 (Bad Request).
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }

            // Llama al DAO para buscar el trabajador por cédula.
            Trabajador trabajador = trabajadorDAO.obtenerPorCedula(cedula);
            if (trabajador != null) {
                // Si el trabajador se encuentra, retorna un 200 (OK) con el objeto Trabajador.
                return Response.ok(trabajador).build();
            } else {
                // Si el trabajador no se encuentra, retorna un 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener TRABAJADOR.")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    // Método POST para crear un nuevo trabajador.
    @POST
    // El objeto Trabajador se deserializa automáticamente del cuerpo de la petición.
    public Response crearTrabajador(Trabajador trabajador) {
        try {
            // Valida el campo 'cedula' del objeto trabajador.
            String validaCedula = Middlewares.validarEntero(trabajador.getCedula(), "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }

            // Valida el campo 'nombre'.
            String validarNombre = Middlewares.validarString(trabajador.getNombre(), "nombre");
            if (!validarNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNombre).build();
            }

            // Valida el campo 'apellido'.
            String validarApellido = Middlewares.validarString(trabajador.getApellido(), "apellido");
            if (!validarApellido.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarApellido).build();
            }

            // Valida el campo 'nacimiento' (formato de fecha).
            String validarNacimiento = Middlewares.validarFecha(trabajador.getNacimiento(), "nacimiento");
            if (!validarNacimiento.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNacimiento).build();
            }

            // Valida que el campo 'contraseña' no esté vacío.
            String validarContrasena = Middlewares.ContraseñaVacia(trabajador.getContrasena(), "contraseña");
            if (!validarContrasena.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarContrasena).build();
            }

            // Valida el campo 'idOficio'.
            String validaCodigoOficio = Middlewares.validarEntero(trabajador.getIdOficio(), "idOficio");
            if (!validaCodigoOficio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigoOficio).build();
            }

            // Si todas las validaciones son exitosas, llama al método 'crear' del DAO.
            boolean creado = trabajadorDAO.crear(trabajador);
            if (creado) {
                // Si la creación fue exitosa, retorna un 201 (Created) con un mensaje.
                return Response.status(Response.Status.CREATED)
                        .entity("Trabajador: " + trabajador.getNombre() + " " + trabajador.getApellido() + " creado con EXITO.")
                        .build();
            } else {
                // Si la creación falla, retorna un 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear TRABAJADOR.")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar un trabajador existente.
    @PUT
    // La ruta incluye la cédula del trabajador a actualizar.
    @Path("/{cedula}")
    public Response actualizarTrabajador(@PathParam("cedula") String cedula, Trabajador trabajador) {
        try {
            // Asigna la cédula de la URL al objeto trabajador.
            trabajador.setCedula(cedula);

            // Valida el campo 'cedula'.
            String validaCedula = Middlewares.validarEntero(trabajador.getCedula(), "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }

            // Valida el campo 'nombre'.
            String validarNombre = Middlewares.validarString(trabajador.getNombre(), "nombre");
            if (!validarNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNombre).build();
            }

            // Valida el campo 'apellido'.
            String validarApellido = Middlewares.validarString(trabajador.getApellido(), "apellido");
            if (!validarApellido.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarApellido).build();
            }

            // Valida el campo 'nacimiento'.
            String validarNacimiento = Middlewares.validarFecha(trabajador.getNacimiento(), "nacimiento");
            if (!validarNacimiento.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNacimiento).build();
            }

            // Valida el campo 'idOficio'.
            String validaCodigoOficio = Middlewares.validarEntero(trabajador.getIdOficio(), "idOficio");
            if (!validaCodigoOficio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCodigoOficio).build();
            }

            // Llama al método 'actualizar' del DAO.
            boolean actualizado = trabajadorDAO.actualizar(trabajador);
            if (actualizado) {
                // Si la actualización es exitosa, retorna un 200 (OK) con un mensaje de éxito.
                return Response.ok().entity("Trabajador: " + trabajador.getNombre() + " " + trabajador.getApellido() + " actualizado con EXITO.").build();
            } else {
                // Si el trabajador no se encuentra, retorna un 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: trabajador NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError().entity("Error: error interno en el servidor.").build();
        }
    }

    // Método PATCH para actualizar solo la foto de un trabajador.
    @PATCH
    // La ruta incluye la cédula del trabajador.
    @Path("/{cedula}")
    public Response actualizarFotoTrabajador(@PathParam("cedula") String cedula, Trabajador trabajador) {
        try {
            // Asigna la cédula de la URL al objeto trabajador.
            trabajador.setCedula(cedula);
            // Valida la cédula.
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }

            // Llama al método 'actualizarFoto' del DAO.
            boolean actualizado = trabajadorDAO.actualizarFoto(trabajador);
            if (actualizado) {
                // Si la actualización es exitosa, retorna un 200 (OK).
                return Response.ok().entity("Trabajador: actualizacion de foto con EXITO.").build();
            } else {
                // Si no se encuentra el trabajador, retorna un 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: trabajador NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PATCH para cambiar el estado 'activo' de un trabajador.
    @PATCH
    // La ruta incluye la cédula y la sub-ruta 'estado'.
    @Path("estado/{cedula}")
    public Response actualizarEstadoTrabajador(@PathParam("cedula") String cedula, Trabajador trabajador) {
        try {
            // Asigna la cédula de la URL al objeto trabajador.
            trabajador.setCedula(cedula);
            // Valida la cédula.
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            // Valida que el estado sea un booleano válido.
            String validaEstado = Middlewares.validarBooleano(trabajador.getActivo(), "estado");
            if (!validaEstado.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaEstado).build();
            }

            // Llama al método 'cambiarEstado' del DAO.
            boolean actualizado = trabajadorDAO.cambiarEstado(trabajador);
            if (actualizado) {
                // Si la actualización es exitosa, retorna un 200 (OK).
                return Response.ok().entity("Trabajador: actualizacion de estado con EXITO.").build();
            } else {
                // Si no se encuentra el trabajador, retorna un 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: trabajador NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método PATCH para activar un trabajador y asignarle un oficio.
    @PATCH
    @Path("activar/{cedula}")
    public Response activarTrabajador(@PathParam("cedula") String cedula, Trabajador trabajador) {
        try {
            // Asigna la cédula de la URL al objeto trabajador.
            trabajador.setCedula(cedula);
            // Valida la cédula.
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            // Valida el 'idOficio'.
            String validaIdOficio = Middlewares.validarEntero(trabajador.getIdOficio(), "id Oficio");
            if (!validaIdOficio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaIdOficio).build();
            }
            // Valida el estado.
            String validaEstado = Middlewares.validarBooleano(trabajador.getActivo(), "estado");
            if (!validaEstado.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaEstado).build();
            }
            // Llama al método 'activarTrabajador' del DAO.
            boolean actualizado = trabajadorDAO.activarTrabajador(trabajador);
            if (actualizado) {
                // Si la actualización es exitosa, retorna un 200 (OK).
                return Response.ok().entity("Trabajador: actualizacion de estado con EXITO.").build();
            } else {
                // Si el trabajador no se encuentra, retorna un 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: trabajador NO ENCONTRADO o NO ACTUALIZADO.")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar un trabajador por su cédula.
    @DELETE
    @Path("/{cedula}")
    public Response eliminarTrabajador(@PathParam("cedula") String cedula) {
        try {
            // Valida la cédula.
            String validaCedula = Middlewares.validarEntero(cedula, "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            // Llama al método 'eliminar' del DAO.
            boolean eliminado = trabajadorDAO.eliminar(cedula);
            if (eliminado) {
                // Si la eliminación es exitosa, retorna un 200 (OK).
                return Response.ok().entity("Trabajador: eliminado EXITOSAMENTE.").build();
            } else {
                // Si el trabajador no se encuentra, retorna un 404 (Not Found).
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: trabajador NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
