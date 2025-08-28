package CONTROLADOR;

import UTILS.JwtUtil;
import MODELO.Login;
import DAO.LoginDAO;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenControlador{

    private LoginDAO loginDAO = new LoginDAO();

    @POST
    public Response validarToken(Login login) {
        String accessToken = login.getToken();
        String refreshToken = login.getRefreshToken();

        // Validar y renovar si aplica
        JwtUtil.TokenValidationResult resultado = JwtUtil.validarYRenovarAccessToken(accessToken, refreshToken);

        if (!resultado.EsValido()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(resultado).build();
        }
        return Response.ok(resultado).build();

    }
}