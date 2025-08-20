package CONTROLADOR;

import BD.JwtUtil;
import MODELO.Login;
import MODELO.LoginDAO;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginControlador {

    private LoginDAO loginDAO = new LoginDAO();

    @GET
    public Response listarTokens() {
        Login login = new Login();
        login.setCedula("114024793");
        return Response.ok(login).build();
          }
    @POST
    public Response logearse(Login login){
        boolean validar = loginDAO.autenticar(login.getCedula(), login.getContrasena());
        if (!validar) return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Credenciales inválidas\"}").build();
        String rol = loginDAO.obtenerRol(login.getCedula());
        login.setRol(rol);
        List<String> permisos = loginDAO.obtenerPermisos(login.getCedula());
        login.setPermisos(permisos);
        String token = JwtUtil.generarAccessToken();
        String refreshToken = JwtUtil.generarRefreshToken();
        login.setToken(token);
        login.setRefreshToken(refreshToken);
        return Response.ok(login).build();
    }
    
    @POST
    @Path("/validar")
    public Response validarToken(Login login) {
        String accessToken = login.getToken();
        String refreshToken = login.getRefreshToken();

        // Validar y renovar si aplica
        JwtUtil.TokenValidationResult resultado = JwtUtil.validarYRenovarAccessToken(accessToken, refreshToken);

        if (!resultado.isValido()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Error: Token inválido o expirado.")
                    .build();
        }

// Actualizamos el token en el objeto
        login.setToken(resultado.getNuevoToken());
        return Response.ok(login).build();

    }
}
