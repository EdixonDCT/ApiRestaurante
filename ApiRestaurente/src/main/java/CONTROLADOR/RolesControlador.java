// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa la clase modelo Oficio
import MODELO.Roles;
// Importa la clase DAO que gestiona la base de datos para Oficio
import DAO.RolesDAO;
// Importa el middleware de validación para Oficio
import UTILS.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("roles") // La ruta base para este controlador es "/oficios".
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON) // Los métodos producirán respuestas en formato JSON.
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Los métodos consumirán peticiones en formato JSON.
public class RolesControlador {

    // Instancia del DAO para poder acceder a la base de datos
    private RolesDAO rolesDAO = new RolesDAO();

    // Método GET para obtener todos los oficios
    @GET // Mapea este método a las peticiones GET en "/oficios".
    public Response listarRoles() {
        try {
            List<Roles> lista = rolesDAO.listarTodos(); // Llama al DAO para obtener la lista de oficios.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista en formato JSON.
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción para depuración.
            return Response.serverError().build(); // Retorna una respuesta 500 (Internal Server Error).
        }
    }
}
