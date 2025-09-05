// Paquete que agrupa a todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Usuarios.
import MODELO.Usuarios;
// Importa la clase DAO que gestiona la base de datos para Usuarios.
import DAO.TrabajadorDAO;
// Importa el middleware de validación para Usuarios.
import UTILS.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST.
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador.
@Path("register")
// Indica que las respuestas serán en formato JSON.
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON.
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterControlador {
    private TrabajadorDAO trabajadorDAO = new TrabajadorDAO();
   // Método POST para crear un nuevo trabajador.
    @POST
    // El objeto Usuarios se deserializa automáticamente del cuerpo de la petición.
    public Response crearTrabajador(Usuarios usuarios) {
        try {
            // Valida el campo 'cedula' del objeto trabajador.
            String validaCedula = Middlewares.validarEntero(usuarios.getCedula(), "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            
            boolean repetido = trabajadorDAO.obtenerPorCedulaBoolean(usuarios.getCedula());
            if (repetido) return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Trabajador con cc."+usuarios.getCedula()+" ya Existe\"}").build();
            
            // Valida el campo 'nombre'.
            String validarNombre = Middlewares.validarString(usuarios.getNombre(), "nombre");
            if (!validarNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNombre).build();
            }

            // Valida el campo 'apellido'.
            String validarApellido = Middlewares.validarString(usuarios.getApellido(), "apellido");
            if (!validarApellido.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarApellido).build();
            }

            // Valida el campo 'nacimiento' (formato de fecha).
            String validarNacimiento = Middlewares.validarFecha(usuarios.getNacimiento(), "nacimiento");
            if (!validarNacimiento.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarNacimiento).build();
            }

            // Valida que el campo 'contraseña' no esté vacío.
            String validarContrasena = Middlewares.ContraseñaVacia(usuarios.getContrasena(), "contraseña");
            if (!validarContrasena.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validarContrasena).build();
            }

            // Si todas las validaciones son exitosas, llama al método 'crear' del DAO.
            boolean creado = trabajadorDAO.crear(usuarios);
            if (creado) {
                String mensaje = usuarios.getNombre() + " " + usuarios.getApellido() + " creado con EXITO.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\""+mensaje+"\"}")
                        .build();
            } else {
                // Si la creación falla, retorna un 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo crear trabajador.\"}")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }
    @PATCH
    @Path("/{cedula}")
    public Response actualizarFotoTrabajador(@PathParam("cedula") String cedula, Usuarios trabajador) {
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
                String mensaje = "Foto actualizada con EXITO.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\""+mensaje+"\"}")
                        .build();
            } else {
                // Si la creación falla, retorna un 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo crear trabajador\"}")
                        .build();
            }
        } catch (Exception e) {
            // Imprime la traza de la excepción.
            e.printStackTrace();
            // Retorna un error 500 (Internal Server Error).
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

}
