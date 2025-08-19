package CONTROLADOR;

import BD.JwtUtil;
import MODELO.Login;
import MODELO.LoginDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginControlador {

    private LoginDAO loginDAO = new LoginDAO();

    /**
     * Endpoint para autenticarse y generar tokens.
     */
    @POST
    public Response login(Login credenciales) {
        Login login = loginDAO.autenticar(credenciales.getCedula(), credenciales.getContrasena());

        if (login == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Credenciales inválidas\"}")
                    .build();
        }

        // Generar tokens
        String token = JwtUtil.generarAccessToken();
        String refreshToken = JwtUtil.generarRefreshToken();

        login.setToken(token);
        login.setRefreshToken(refreshToken);

        return Response.ok(login).build();
    }

    /**
     * Endpoint para validar o renovar token.
     */
    @POST
    @Path("validar")
    public Response validarToken(Login login) {
        String accessToken = login.getToken();
        String refreshToken = login.getRefreshToken();

        // Validar y renovar si aplica
        JwtUtil.TokenValidationResult resultado = JwtUtil.validarYRenovarAccessToken(accessToken, refreshToken);

        if (!resultado.isValido()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"valido\":false, \"mensaje\":\"Token inválido o expirado\"}")
                    .build();
        }

// Actualizamos el token en el objeto
        login.setToken(resultado.getNuevoToken());

        return Response.ok(login).build();

    }
}
