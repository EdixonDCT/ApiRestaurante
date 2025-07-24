package CONTROLADOR;

import MODELO.Bebida;
import MODELO.BebidaDAO;
import CONTROLADOR.Middlewares;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("bebidas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BebidaControlador {

    private BebidaDAO bebidaDAO = new BebidaDAO();

    @GET
    public Response listarBebidas() {
        try {
            List<Bebida> lista = bebidaDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerBebida(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            Bebida bebida = bebidaDAO.obtenerPorId(id);
            if (bebida != null) {
                return Response.ok(bebida).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: bebida NO ENCONTRADA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @POST
    public Response crearBebida(Bebida bebida) {
        try {
            String validaNombre = Middlewares.validarString(bebida.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(bebida.getPrecio(), "precio");
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            String validaUnidad = Middlewares.validarUnidadBebida(bebida.getUnidad(), "unidad");
            if (!validaUnidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaUnidad).build();
            }

            String validaTipo = Middlewares.validarString(bebida.getTipo(), "tipo");
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }

            boolean creado = bebidaDAO.crear(bebida);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Bebida: " + bebida.getNombre() + " creada con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear la bebida.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarBebida(@PathParam("id") String id, Bebida bebida) {
        try {
            bebida.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            String validaNombre = Middlewares.validarString(bebida.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(bebida.getPrecio(), "precio");
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            String validaUnidad = Middlewares.validarUnidadBebida(bebida.getUnidad(), "unidad");
            if (!validaUnidad.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaUnidad).build();
            }

            String validaTipo = Middlewares.validarString(bebida.getTipo(), "tipo");
            if (!validaTipo.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build();
            }

            boolean actualizado = bebidaDAO.actualizar(bebida);
            if (actualizado) {
                return Response.ok().entity("Bebida: " + bebida.getNombre() + " actualizada con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: bebida NO ENCONTRADA o NO ACTUALIZADA.").build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/imagen/{id}")
    public Response actualizarImagenBebida(@PathParam("id") String id, Bebida bebida) {
        try {
            bebida.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean actualizado = bebidaDAO.actualizarImagen(bebida);
            if (actualizado) {
                return Response.ok().entity("Imagen de la bebida actualizada con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: bebida NO ENCONTRADA o NO ACTUALIZADA.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response actualizarDisponibilidadBebida(@PathParam("id") String id, Bebida bebida) {
        try {
            bebida.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            String validaDisponible = Middlewares.validarBooleano(bebida.getDisponible(), "disponible");
            if (!validaDisponible.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponible).build();
            }

            boolean actualizado = bebidaDAO.actualizarEstado(bebida);
            if (actualizado) {
                return Response.ok().entity("Disponibilidad de bebida actualizada con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: bebida NO ENCONTRADA o NO ACTUALIZADA.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarBebida(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean eliminado = bebidaDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Bebida eliminada con ÉXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: bebida NO ENCONTRADA.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor.").build();
        }
    }
}
