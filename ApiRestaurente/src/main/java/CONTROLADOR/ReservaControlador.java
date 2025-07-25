package CONTROLADOR;

import MODELO.Reserva;
import MODELO.ReservaDAO;
import CONTROLADOR.Middlewares;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaControlador {

    private ReservaDAO reservaDAO = new ReservaDAO();

    @GET
    public Response listar() {
        try {
            List<Reserva> lista = reservaDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") String id) {
        String validar = Middlewares.validarEntero(id, "id");
        if (!validar.equals("ok")) return Response.status(400).entity(validar).build();

        Reserva r = reservaDAO.obtenerPorId(id);
        if (r != null) return Response.ok(r).build();
        return Response.status(404).entity("Reserva no encontrada.").build();
    }

    @POST
    public Response crear(Reserva r) {
        String v1 = Middlewares.validarDouble(r.getPrecio(), "precio");
        String v2 = Middlewares.validarFecha(r.getFecha(), "fecha");
        String v3 = Middlewares.validarFecha(r.getFechaTentativa(), "fecha_tentativa");
        String v4 = Middlewares.validarHora(r.getHoraTentativa(), "hora_tentativa");

        if (!v1.equals("ok")) return Response.status(400).entity(v1).build();
        if (!v2.equals("ok")) return Response.status(400).entity(v2).build();
        if (!v3.equals("ok")) return Response.status(400).entity(v3).build();
        if (!v4.equals("ok")) return Response.status(400).entity(v4).build();

        boolean creado = reservaDAO.crear(r);
        if (creado) return Response.status(201).entity("Reserva creada con éxito.").build();
        return Response.status(500).entity("Error al crear la reserva.").build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, Reserva r) {
        r.setId(id);
        String v1 = Middlewares.validarEntero(id, "id");
        String v2 = Middlewares.validarDouble(r.getPrecio(), "precio");
        String v3 = Middlewares.validarFecha(r.getFecha(), "fecha");
        String v4 = Middlewares.validarFecha(r.getFechaTentativa(), "fecha_tentativa");
        String v5 = Middlewares.validarHora(r.getHoraTentativa(), "hora_tentativa");

        if (!v1.equals("ok")) return Response.status(400).entity(v1).build();
        if (!v2.equals("ok")) return Response.status(400).entity(v2).build();
        if (!v3.equals("ok")) return Response.status(400).entity(v3).build();
        if (!v4.equals("ok")) return Response.status(400).entity(v4).build();
        if (!v5.equals("ok")) return Response.status(400).entity(v5).build();

        boolean actualizado = reservaDAO.actualizar(r);
        if (actualizado) return Response.ok("Reserva actualizada con éxito.").build();
        return Response.status(404).entity("Reserva no encontrada.").build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        String validar = Middlewares.validarEntero(id, "id");
        if (!validar.equals("ok")) return Response.status(400).entity(validar).build();

        boolean eliminado = reservaDAO.eliminar(id);
        if (eliminado) return Response.ok("Reserva eliminada correctamente.").build();
        return Response.status(404).entity("Reserva no encontrada.").build();
    }
}
