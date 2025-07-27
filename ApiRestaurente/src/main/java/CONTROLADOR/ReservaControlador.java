package CONTROLADOR;

import MODELO.Reserva;
import MODELO.ReservaDAO;
import CONTROLADOR.Servicios;
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
        if (!validar.equals("ok"))
            return Response.status(400).entity(validar).build();

        Reserva r = reservaDAO.obtenerPorId(id);
        if (r != null)
            return Response.ok(r).build();
        return Response.status(404).entity("Reserva no encontrada.").build();
    }

    @POST
    public Response crear(Reserva reserva) {
        try {
            String validaCantidadTentativa = Middlewares.validarEntero(reserva.getCantidadTentativa(),
                    "Cantidad Tentativa");
            if (!validaCantidadTentativa.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCantidadTentativa)
                        .build();

            reserva.setPrecio(Servicios.CalcularReserva(reserva.getCantidadTentativa()));

            String validaPrecio = Middlewares.validarDouble(reserva.getPrecio(), "precio");
            if (!validaPrecio.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();

            String validarFecha = Middlewares.validarFecha(reserva.getFechaTentativa(), "fecha_tentativa");
            if (!validarFecha.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarFecha).build();

            String validarHora = Middlewares.validarHora(reserva.getHoraTentativa(), "hora_tentativa");
            if (!validarHora.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarHora).build();

            boolean creado = reservaDAO.crear(reserva);
            if (creado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Reserva: creada con EXITO.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: no se pudo crear RESERVA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, Reserva reserva) {
        try {
            reserva.setId(id);
            String validaId = Middlewares.validarEntero(reserva.getId(), "Id");
            if (!validaId.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build();

            String validaCantidadTentativa = Middlewares.validarEntero(reserva.getCantidadTentativa(),
                    "Cantidad Tentativa");
            if (!validaCantidadTentativa.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaCantidadTentativa)
                        .build();

            reserva.setPrecio(Servicios.CalcularReserva(reserva.getCantidadTentativa()));

            String validaPrecio = Middlewares.validarDouble(reserva.getPrecio(), "precio");
            if (!validaPrecio.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build();

            String validarFecha = Middlewares.validarFecha(reserva.getFechaTentativa(), "fecha_tentativa");
            if (!validarFecha.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarFecha).build();

            String validarHora = Middlewares.validarHora(reserva.getHoraTentativa(), "hora_tentativa");
            if (!validarHora.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarHora).build();

            boolean actualizado = reservaDAO.actualizar(reserva);
            if (actualizado) {
                return Response.status(Response.Status.CREATED)
                        .entity("Reserva: actualizada con EXITO.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error: reserva NO ENCONTRADA o NO ACTUALIZADA.")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            String validarId = Middlewares.validarEntero(id, "id");
            if (!validarId.equals("ok"))
                return Response.status(Response.Status.BAD_REQUEST).entity(validarId).build();

            boolean eliminado = reservaDAO.eliminar(id);
            if (eliminado) {
                return Response.ok().entity("Reserva: eliminada EXITOSAMENTE.").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Error: Reserva NO ENCONTRADA.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
