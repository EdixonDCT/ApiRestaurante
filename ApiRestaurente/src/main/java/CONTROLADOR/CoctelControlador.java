// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

import MODELO.Coctel;
import MODELO.CoctelDAO;
import CONTROLADOR.Middlewares;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cocteles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoctelControlador {

    private CoctelDAO coctelDAO = new CoctelDAO();

    @GET
    public Response listarCocteles() {
        try {
            List<Coctel> lista = coctelDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerCoctel(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            Coctel coctel = coctelDAO.obtenerPorId(id);
            if (coctel != null) {
                return Response.ok(coctel).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: coctel NO ENCONTRADO.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno del servidor.").build();
        }
    }

    @POST
    public Response crearCoctel(Coctel coctel) {
        try {
            String validaNombre = Middlewares.validarString(coctel.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(coctel.getPrecio(), "precio");
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            boolean creado = coctelDAO.crear(coctel);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Coctel: "+coctel.getNombre()+" creado con EXITO.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear el coctel.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno del servidor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizarCoctel(@PathParam("id") String id, Coctel coctel) {
        try {
            coctel.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            String validaNombre = Middlewares.validarString(coctel.getNombre(), "nombre");
            if (!validaNombre.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build();
            }

            String validaPrecio = Middlewares.validarDouble(coctel.getPrecio(), "precio");
            if (!validaPrecio.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();
            }

            boolean actualizado = coctelDAO.actualizar(coctel);
            if (actualizado) {
                return Response.ok().entity("Coctel: "+coctel.getNombre()+" actualizado con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: coctel NO ENCONTRADO o NO ACTUALIZADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno del servidor.").build();
        }
    }

    @PATCH
    @Path("/imagen/{id}")
    public Response actualizarImagenCoctel(@PathParam("id") String id, Coctel coctel) {
        try {
            coctel.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean actualizado = coctelDAO.actualizarImagen(coctel);
            if (actualizado) {
                return Response.ok().entity("Imagen actualizada con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: coctel NO ENCONTRADO o NO ACTUALIZADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno del servidor.").build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response actualizarDisponibilidadCoctel(@PathParam("id") String id, Coctel coctel) {
        try {
            coctel.setId(id);

            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            String validaDisponible = Middlewares.validarBooleano(coctel.getDisponible(), "disponible");
            if (!validaDisponible.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponible).build();
            }

            boolean actualizado = coctelDAO.actualizarEstado(coctel);
            if (actualizado) {
                return Response.ok().entity("Disponibilidad actualizada con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: coctel NO ENCONTRADO o NO ACTUALIZADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno del servidor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarCoctel(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok")) {
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();
            }

            boolean eliminado = coctelDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Coctel eliminado con EXITO.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Error: coctel NO ENCONTRADO.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno del servidor.").build();
        }
    }
}
