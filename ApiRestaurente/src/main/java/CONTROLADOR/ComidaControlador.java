// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

// Importa las clases necesarias
import MODELO.Comida;
import MODELO.ComidaDAO;
import MODELO.Trabajador;
import CONTROLADOR.Middlewares;

import java.util.List;

// Librerías de JAX-RS necesarias para trabajar con servicios REST
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Define la ruta base para acceder a los métodos del controlador
@Path("comidas")
// Indica que las respuestas serán en formato JSON
@Produces(MediaType.APPLICATION_JSON)
// Indica que las peticiones deben ser en formato JSON
@Consumes(MediaType.APPLICATION_JSON)
public class ComidaControlador {

    // Instancia del DAO para acceder a la base de datos
    private ComidaDAO comidaDAO = new ComidaDAO();

    // Método GET para listar todas las comidas
    @GET
    public Response listarComidas() {
        try {
            List<Comida> lista = comidaDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    // Método GET para obtener una comida por ID
    @GET
    @Path("/{id}")
    public Response obtenerComida(@PathParam("id") String id) {
        try {
            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            Comida comida = comidaDAO.obtenerPorId(id);
            if (comida != null) {
                return Response.ok(comida).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: no se pudo obtener COMIDA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("Error: Error interno en el servidor.")
                    .build();
        }
    }

    // Método POST para crear una nueva comida
    @POST
    public Response crearComida(Comida comida) {
        try {
            String validaNombre = Middlewares.validarString(comida.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(comida.getPrecio(), "precio");
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            String validaTipo = Middlewares.validarString(comida.getTipo(), "tipo");
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }
            String[] resultado = comidaDAO.crear(comida);

            if (!resultado[1].equals("-1")) {
                String json = String.format("{\"mensaje\": \"%s\", \"id\": \"%s\"}", resultado[0], resultado[1]);
                return Response.status(Response.Status.CREATED).entity(json).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el comida.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    // Método PUT para actualizar una comida existente
    @PUT
    @Path("/{id}")
    public Response actualizarComida(@PathParam("id") String id, Comida comida) {
        try {
            comida.setId(id);

            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            String validaNombre = Middlewares.validarString(comida.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(String.valueOf(comida.getPrecio()), "precio");
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            String validaTipo = Middlewares.validarString(comida.getTipo(), "tipo");
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }

            boolean actualizado = comidaDAO.actualizar(comida);
            if (actualizado) {
                return Response.ok().entity("Comida: " + comida.getNombre() + " actualizada con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: comida NO ENCONTRADA o NO ACTUALIZADA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/imagen/{id}")
    public Response actualizarImagenComida(@PathParam("id") String id, Comida comida) {
        try {
            comida.setId(id); // Establece el ID al objeto que se va a actualizar

            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            // Actualiza si todo está bien
            boolean actualizado = comidaDAO.actualizarImagen(comida);
            if (actualizado) {
                return Response.ok().entity("Comida: actualizacion de imagen con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: comida NO ENCONTRADO o NO ACTUALIZADO.")
                        .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response actualizarDisponibilidadComida(@PathParam("id") String id, Comida comida) {
        try {
            comida.setId(id); // Establece el ID al objeto que se va a actualizar

            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }
            String validaDisponiblidad = Middlewares.validarBooleano(comida.getDisponible(), "disponibilidad");
            if (!validaDisponiblidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponiblidad).build();
            }

            // Actualiza si todo está bien
            boolean actualizado = comidaDAO.actualizarEstado(comida);
            if (actualizado) {
                return Response.ok().entity("Comida: actualizacion de imagen con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: comida NO ENCONTRADO o NO ACTUALIZADO.")
                        .build(); // Retorna error 404
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    // Método DELETE para eliminar una comida por su ID
    @DELETE
    @Path("/{id}")
    public Response eliminarComida(@PathParam("id") String id) {
        try {
            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaID).build();
            }

            boolean eliminado = comidaDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Comida: Eliminada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: comida NO ENCONTRADA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
