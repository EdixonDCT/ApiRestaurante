// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

import MODELO.Cliente;
import MODELO.ClienteDAO;
import MODELO.Mesa;
// Importa el middleware de validación para Oficio
import CONTROLADOR.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("clientes")
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteControlador {

    private ClienteDAO clienteDAO = new ClienteDAO();

    // Método GET para obtener todos los oficios
    @GET
    public Response listarClientes() {
        try {
            List<Cliente> lista = clienteDAO.listarTodos(); // Llama al DAO
            return Response.ok(lista).build(); // Retorna lista en JSON
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build(); // Retorna error 500
        }
    }

    // Método GET para obtener un oficio por su ID
    @GET
    @Path("/{correo}")
    public Response obtenerCliente(@PathParam("correo") String correo) {
        try {
            String validaCorreo = Middlewares.validarCorreo(correo, "correo");
            if (!validaCorreo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build();
            }

            Cliente cliente = clienteDAO.obtenerPorCorreo(correo); // Busca por ID
            if (cliente != null) {
                return Response.ok(cliente).build(); // Retorna oficio
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener CLIENTE.")
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
    public Response crearCliente(Cliente cliente) {
        try {
            // Valida el campo tipo
            String validaCorreo = Middlewares.validarCorreo(cliente.getCorreo(), "correo");
            if (!validaCorreo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build();
            }
            // Valida el campo tipo
            String validaCedula = Middlewares.validarCantidad9o10(cliente.getCedula(), "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            String validaTelefono = Middlewares.validarCantidad9o10(cliente.getTelefono(), "telefono");
            if (!validaTelefono.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTelefono).build();
            }
            // Crea el oficio si pasa las validaciones
            boolean creado = clienteDAO.crear(cliente);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Cliente: " + cliente.getCorreo() + " creado con EXITO.")
                        .build(); // Retorna código 201
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear CLIENTE " + cliente.getCorreo())
                        .build(); // Retorna error 500
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor,"+e).build();
        }
    }
    // Método PUT para actualizar un oficio existente
    @PUT
    @Path("/{correo}")
    public Response actualizarCliente(@PathParam("correo") String correo, Cliente cliente) {
        try {
            cliente.setCorreo(correo);// Establece el ID al objeto que se va a actualizar

            // Valida el campo tipo
            String validaCorreo = Middlewares.validarCorreo(cliente.getCorreo(), "correo");
            if (!validaCorreo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build();
            }
            // Valida el campo tipo
            String validaCedula = Middlewares.validarCantidad9o10(cliente.getCedula(), "cedula");
            if (!validaCedula.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build();
            }
            String validaTelefono = Middlewares.validarCantidad9o10(cliente.getTelefono(), "telefono");
            if (!validaTelefono.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTelefono).build();
            }
            // si todo es correcto accede actualizar
            boolean actualizado = clienteDAO.actualizar(cliente);
            if (actualizado) {
                return Response.ok().entity("Cliente: " + cliente.getCorreo()+ " actualizado con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: cliente NO ENCONTRADO o NO ACTUALIZADO.")
                        .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar un oficio por su ID
    @DELETE
    @Path("/{correo}")
    public Response eliminarOficio(@PathParam("correo") String correo) {
        try {
            String validaCorreo = Middlewares.validarCorreo(correo, "correo");
            if (!validaCorreo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build();
            }

            boolean eliminado = clienteDAO.eliminar(correo);
            if (eliminado) {
                return Response.ok().entity("Cliente: " + correo + " Eliminada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: cliente NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
