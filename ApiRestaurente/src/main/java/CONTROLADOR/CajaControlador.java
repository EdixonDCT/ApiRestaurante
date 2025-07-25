package CONTROLADOR;

import MODELO.Caja;
import MODELO.CajaDAO;
import CONTROLADOR.Middlewares;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("caja")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CajaControlador {

    private CajaDAO dao = new CajaDAO();

    @GET
    public Response listar() {
        try {
            List<Caja> lista = dao.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") String id) {
        try {
            String validacion = Middlewares.validarEntero(id, "id");
            if (!validacion.equals("ok")) {
                return Response.status(400).entity(validacion).build();
            }

            Caja caja = dao.obtenerPorId(id);
            if (caja == null) {
                return Response.status(404).entity("Error: Caja no encontrada.").build();
            }

            return Response.ok(caja).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor").build();
        }
    }

    @POST
    public Response crear(Caja caja) {
        try {
            String v1 = Middlewares.validarFecha(caja.getFechaApertura(), "fecha_apertura");
            if (!v1.equals("ok")) return Response.status(400).entity(v1).build();

            String v2 = Middlewares.validarHora(caja.getHoraApertura(), "hora_apertura");
            if (!v2.equals("ok")) return Response.status(400).entity(v2).build();

            String v3 = Middlewares.validarDouble(caja.getMontoApertura(), "monto_apertura");
            if (!v3.equals("ok")) return Response.status(400).entity(v3).build();

            String v4 = Middlewares.validarFecha(caja.getFechaCierre(), "fecha_cierre");
            if (!v4.equals("ok")) return Response.status(400).entity(v4).build();

            String v5 = Middlewares.validarHora(caja.getHoraCierre(), "hora_cierre");
            if (!v5.equals("ok")) return Response.status(400).entity(v5).build();

            String v6 = Middlewares.validarDouble(caja.getMontoCierre(), "monto_cierre");
            if (!v6.equals("ok")) return Response.status(400).entity(v6).build();

            String v7 = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula_trabajador");
            if (!v7.equals("ok")) return Response.status(400).entity(v7).build();

            Integer.parseInt(caja.getCedulaTrabajador()); // Forzamos parseo seguro

            boolean creado = dao.crear(caja);
            if (creado) return Response.status(201).entity("Caja creada con éxito").build();

            return Response.status(500).entity("Error interno al crear caja").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, Caja caja) {
        try {
            caja.setId(id);

            String v0 = Middlewares.validarEntero(id, "id");
            if (!v0.equals("ok")) return Response.status(400).entity(v0).build();

            String v1 = Middlewares.validarFecha(caja.getFechaApertura(), "fecha_apertura");
            if (!v1.equals("ok")) return Response.status(400).entity(v1).build();

            String v2 = Middlewares.validarHora(caja.getHoraApertura(), "hora_apertura");
            if (!v2.equals("ok")) return Response.status(400).entity(v2).build();

            String v3 = Middlewares.validarDouble(caja.getMontoApertura(), "monto_apertura");
            if (!v3.equals("ok")) return Response.status(400).entity(v3).build();

            String v4 = Middlewares.validarFecha(caja.getFechaCierre(), "fecha_cierre");
            if (!v4.equals("ok")) return Response.status(400).entity(v4).build();

            String v5 = Middlewares.validarHora(caja.getHoraCierre(), "hora_cierre");
            if (!v5.equals("ok")) return Response.status(400).entity(v5).build();

            String v6 = Middlewares.validarDouble(caja.getMontoCierre(), "monto_cierre");
            if (!v6.equals("ok")) return Response.status(400).entity(v6).build();

            String v7 = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula_trabajador");
            if (!v7.equals("ok")) return Response.status(400).entity(v7).build();

            Integer.parseInt(caja.getCedulaTrabajador());

            boolean actualizado = dao.actualizar(caja);
            if (actualizado) return Response.ok("Caja actualizada correctamente").build();

            return Response.status(404).entity("Caja no encontrada o no actualizada").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            String validacion = Middlewares.validarEntero(id, "id");
            if (!validacion.equals("ok")) return Response.status(400).entity(validacion).build();

            Integer.parseInt(id);

            boolean eliminado = dao.eliminar(id);
            if (eliminado) return Response.ok("Caja eliminada exitosamente").build();

            return Response.status(404).entity("Caja no encontrada").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error interno en el servidor").build();
        }
    }
}
