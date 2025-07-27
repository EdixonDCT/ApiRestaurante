package CONTROLADOR;

import MODELO.Caja;
import MODELO.CajaDAO;
import MODELO.Trabajador;
import CONTROLADOR.Middlewares;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("caja")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CajaControlador {

    private CajaDAO cajaDAO = new CajaDAO();

    @GET
    public Response listar() {
        try {
            List<Caja> lista = cajaDAO.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
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

            Caja caja = cajaDAO.obtenerPorId(id);
            if (caja == null) {
                return Response.status(400).entity("Error: no se puede obtener CAJA.").build();
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
            String validaMontoApertura = Middlewares.validarDouble(caja.getMontoApertura(), "monto_apertura");
            if (!validaMontoApertura.equals("ok"))
                return Response.status(400).entity(validaMontoApertura).build();

            String validaCedulaTrabajador = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula_trabajador");
            if (!validaCedulaTrabajador.equals("ok"))
                return Response.status(400).entity(validaCedulaTrabajador).build();

            boolean creado = cajaDAO.crear(caja);
            if (creado)
                return Response.status(201).entity("Caja: apertura creada con EXITO").build();

            return Response.status(400)
                    .entity("Error: no se pudo crear TRABAJADOR.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") String id, Caja caja) {
        try {
            caja.setId(id);

            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok"))
                return Response.status(400).entity(validaID).build();

            String validaFechaApertura = Middlewares.validarFecha(caja.getFechaApertura(), "fecha_apertura");
            if (!validaFechaApertura.equals("ok"))
                return Response.status(400).entity(validaFechaApertura).build();

            String validaHoraApertura = Middlewares.validarHora(caja.getHoraApertura(), "hora_apertura");
            if (!validaHoraApertura.equals("ok"))
                return Response.status(400).entity(validaHoraApertura).build();

            String validaMontoApertura = Middlewares.validarDouble(caja.getMontoApertura(), "monto_apertura");
            if (!validaMontoApertura.equals("ok"))
                return Response.status(400).entity(validaMontoApertura).build();

            String validaFechaCierre = Middlewares.validarFecha(caja.getFechaCierre(), "fecha_cierre");
            if (!validaFechaCierre.equals("ok"))
                return Response.status(400).entity(validaFechaCierre).build();

            String validaHoraCierre = Middlewares.validarHora(caja.getHoraCierre(), "hora_cierre");
            if (!validaHoraCierre.equals("ok"))
                return Response.status(400).entity(validaHoraCierre).build();

            String validaMontoCierre = Middlewares.validarDouble(caja.getMontoCierre(), "monto_cierre");
            if (!validaMontoCierre.equals("ok"))
                return Response.status(400).entity(validaMontoCierre).build();

            String validaCedulaTrabajador = Middlewares.validarEntero(caja.getCedulaTrabajador(), "cedula_trabajador");
            if (!validaCedulaTrabajador.equals("ok"))
                return Response.status(400).entity(validaCedulaTrabajador).build();

            boolean actualizado = cajaDAO.actualizar(caja);
            if (actualizado)
                return Response.ok("Caja: actualizada con EXITO.").build();

            return Response.status(404).entity("Caja: caja NO ENCONTRADO o NO ACTUALIZADO.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response parchearCierre(@PathParam("id") String id, Caja caja){
        try {
            caja.setId(id);

            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok"))
                return Response.status(400).entity(validaID).build();
        
            String validaMontoCierre = Middlewares.validarDouble(caja.getMontoCierre(), "monto_apertura");
            if (!validaMontoCierre.equals("ok"))
                return Response.status(400).entity(validaMontoCierre).build();

            boolean parchear = cajaDAO.actualizarCierre(caja);
            if (parchear)
                return Response.status(201).entity("Caja: cierre parcheado con EXITO").build();

            return Response.status(400)
                    .entity("Error: no se pudo parchear CAJA de CIERRE.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @PATCH
    @Path("/apertura/{id}")
    public Response parchearApertura(@PathParam("id") String id, Caja caja){
        try {
            caja.setId(id);

            String validaID = Middlewares.validarEntero(id, "id");
            if (!validaID.equals("ok"))
                return Response.status(400).entity(validaID).build();

            String validaMontoApertura = Middlewares.validarDouble(caja.getMontoApertura(), "monto_apertura");
            if (!validaMontoApertura.equals("ok"))
                return Response.status(400).entity(validaMontoApertura).build();

            boolean parchear = cajaDAO.actualizarApertura(caja);
            if (parchear)
                return Response.status(201).entity("Caja: cierre parcheado con EXITO").build();

            return Response.status(400)
                    .entity("Error: no se pudo parchear CAJA de APERTURA.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") String id) {
        try {
            String validaId = Middlewares.validarEntero(id, "id");
            if (!validaId.equals("ok"))
                return Response.status(400).entity(validaId).build();

            boolean eliminado = cajaDAO.eliminar(id);
            if (eliminado)
                return Response.ok("Caja: eliminado EXITOSAMENTE.").build();

            return Response.status(404).entity("Error: caja NO ENCONTRADO.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error: Error interno en el servidor.").build();
        }
    }
}
