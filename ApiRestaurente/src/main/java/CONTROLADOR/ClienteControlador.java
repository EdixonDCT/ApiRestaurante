// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

import MODELO.Cliente; // Importa la clase 'Cliente' para el modelo de datos.
import DAO.ClienteDAO; // Importa la clase 'ClienteDAO' para la persistencia de datos.
import MODELO.Mesa; // Importa la clase 'Mesa', aunque no se usa en este controlador.
// Importa el middleware de validación para Oficio
import UTILS.Middlewares; // Importa la clase 'Middlewares' para realizar validaciones de datos.

import java.util.List; // Importa la clase 'List' para manejar colecciones de objetos.

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*; // Importa todas las anotaciones de JAX-RS.
import javax.ws.rs.core.MediaType; // Importa 'MediaType' para especificar el tipo de contenido.
import javax.ws.rs.core.Response; // Importa 'Response' para construir respuestas HTTP.

// Define la ruta base para acceder a los métodos del controlador
@Path("clientes") // Mapea esta clase a la ruta '/clientes'.
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON) // Establece que los métodos devolverán JSON.
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON) // Establece que los métodos consumirán JSON.
public class ClienteControlador { // Definición de la clase del controlador.

    private ClienteDAO clienteDAO = new ClienteDAO(); // Crea una instancia de 'ClienteDAO'.

    // Método GET para obtener todos los oficios
    @GET // Anotación para manejar peticiones HTTP GET.
    public Response listarClientes() { // Método para obtener la lista de clientes.
        try { // Bloque try-catch para manejo de errores.
            List<Cliente> lista = clienteDAO.listarTodos(); // Llama al DAO para obtener todos los clientes.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista en JSON.
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().build(); // Retorna un error 500 (Internal Server Error).
        }
    }

    // Método GET para obtener un oficio por su ID
    @GET // Anotación para peticiones HTTP GET.
    @Path("/{correo}") // Mapea a la ruta '/clientes/{correo}'.
    public Response obtenerCliente(@PathParam("correo") String correo) { // Método para obtener un cliente por su correo.
        try { // Bloque try-catch.
            String validaCorreo = Middlewares.validarCorreo(correo, "correo"); // Valida el formato del correo.
            if (!validaCorreo.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build(); // ...retorna un 400 (Bad Request).
            }

            Cliente cliente = clienteDAO.obtenerPorCorreo(correo); // Busca el cliente por su correo.
            if (cliente != null) { // Si el cliente es encontrado...
                return Response.ok(cliente).build(); // ...retorna una respuesta 200 con el objeto cliente.
            } else { // Si el cliente no es encontrado...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404 (Not Found).
                        .entity("Error: no se pudo obtener CLIENTE.") // Agrega un mensaje de error.
                        .build(); // Construye y retorna la respuesta.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError() // Retorna un 500 (Internal Server Error).
                    .entity("Error: Error interno en el servidor.") // Agrega un mensaje de error.
                    .build(); // Construye y retorna la respuesta.
        }
    }

    @GET // Anotación para peticiones HTTP GET.
    @Path("verificar/{correo}") // Mapea a la ruta '/clientes/{correo}'.
    public Response verificarSiExisteCliente(@PathParam("correo") String correo) { // Método para obtener un cliente por su correo.
        try { // Bloque try-catch.
            String validaCorreo = Middlewares.validarCorreo(correo, "correo"); // Valida el formato del correo.
            if (!validaCorreo.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build(); // ...retorna un 400 (Bad Request).
            }

            boolean existe = clienteDAO.obtenerPorCorreoBoolean(correo);
            if (existe) { // Si la creación es exitosa...
                String mensaje = "Cliente " + correo + " si Existe.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"Cliente no Existe.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método POST para crear un nuevo oficio
    @POST // Anotación para peticiones HTTP POST (crear).
    public Response crearCliente(Cliente cliente) { // Método para crear un nuevo cliente.
        try { // Bloque try-catch.
            // Valida el campo tipo
            String validaCorreo = Middlewares.validarCorreo(cliente.getCorreo(), "correo"); // Valida el correo del cliente.
            if (!validaCorreo.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build(); // ...retorna un 400.
            }

            boolean repetido = clienteDAO.obtenerPorCorreoBoolean(cliente.getCorreo());
            if (repetido) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Correo del cliente(" + cliente.getCorreo() + ") ya Existe\"}").build();
            }

            // Valida el campo tipo
            String validaCedula = Middlewares.validarCantidad6o10(cliente.getCedula(), "cedula"); // Valida la cédula.
            if (!validaCedula.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build(); // ...retorna un 400.
            }
            String validaTelefono = Middlewares.validarCantidad9o10(cliente.getTelefono(), "telefono"); // Valida el teléfono.
            if (!validaTelefono.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTelefono).build(); // ...retorna un 400.
            }
            // Crea el oficio si pasa las validaciones
            boolean creado = clienteDAO.crear(cliente); // Llama al DAO para crear el cliente.
            if (creado) { // Si la creación es exitosa...
                String mensaje = "Cliente " + cliente.getCorreo() + " creado Exitosamente.";
                return Response.status(Response.Status.CREATED)
                        .entity("{\"Ok\":\"" + mensaje + "\"}")
                        .build();
            } else {
                // Si la creación falla, retorna una respuesta 500 (Internal Server Error).
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"Error\":\"No se pudo crear Cliente.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"Error\":\"Error interno en el servidor.\"}")
                    .build();
        }
    }

    // Método PUT para actualizar un oficio existente
    @PUT // Anotación para peticiones HTTP PUT (actualizar).
    @Path("/{correo}") // Mapea a la ruta '/clientes/{correo}'.
    public Response actualizarCliente(@PathParam("correo") String correo, Cliente cliente) { // Método para actualizar un cliente.
        try { // Bloque try-catch.
            cliente.setCorreo(correo);// Establece el ID al objeto que se va a actualizar

            // Valida el campo tipo
            String validaCorreo = Middlewares.validarCorreo(cliente.getCorreo(), "correo"); // Valida el correo.
            if (!validaCorreo.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build(); // ...retorna un 400.
            }
            // Valida el campo tipo
            String validaCedula = Middlewares.validarCantidad9o10(cliente.getCedula(), "cedula"); // Valida la cédula.
            if (!validaCedula.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCedula).build(); // ...retorna un 400.
            }
            String validaTelefono = Middlewares.validarCantidad9o10(cliente.getTelefono(), "telefono"); // Valida el teléfono.
            if (!validaTelefono.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTelefono).build(); // ...retorna un 400.
            }
            // si todo es correcto accede a actualizar
            boolean actualizado = clienteDAO.actualizar(cliente); // Llama al DAO para actualizar el cliente.
            if (actualizado) { // Si la actualización es exitosa...
                return Response.ok().entity("Cliente: " + cliente.getCorreo() + " actualizado con EXITO.").build(); // ...retorna un 200 con un mensaje de éxito.
            } else { // Si la actualización falla...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404 (Not Found).
                        .entity("Error: cliente NO ENCONTRADO o NO ACTUALIZADO.") // Agrega un mensaje de error.
                        .build(); // Retorna la respuesta.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error: Error interno en el servidor.").build(); // Retorna un 500 con un mensaje.
        }
    }

    // Método DELETE para eliminar un oficio por su ID
    @DELETE // Anotación para peticiones HTTP DELETE (eliminar).
    @Path("/{correo}") // Mapea a la ruta '/clientes/{correo}'.
    public Response eliminarOficio(@PathParam("correo") String correo) { // Método para eliminar un cliente por su correo.
        try { // Bloque try-catch.
            String validaCorreo = Middlewares.validarCorreo(correo, "correo"); // Valida el correo.
            if (!validaCorreo.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCorreo).build(); // ...retorna un 400.
            }

            boolean eliminado = clienteDAO.eliminar(correo); // Llama al DAO para eliminar el cliente.
            if (eliminado) { // Si la eliminación es exitosa...
                return Response.ok().entity("Cliente: " + correo + " Eliminada EXITOSAMENTE.").build(); // ...retorna un 200 con un mensaje.
            } else { // Si la eliminación falla...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404.
                        .entity("Error: cliente NO ENCONTRADO.").build(); // Agrega un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error: Error interno en el servidor.").build(); // Retorna un 500 con un mensaje.
        }
    }
}
