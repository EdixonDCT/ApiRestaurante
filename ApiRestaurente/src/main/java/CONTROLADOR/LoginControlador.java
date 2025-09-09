package CONTROLADOR;

import UTILS.JwtUtil;
import MODELO.Login;
import DAO.LoginDAO;
import UTILS.PasswordUtil;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginControlador {

    private LoginDAO loginDAO = new LoginDAO();

    @POST
    public Response logearse(Login login) {
        String contrasenaCodificada = loginDAO.obtenerContrasena(login.getCedula());
        boolean validarContrasenaConvertir = PasswordUtil.validarPassword(login.getContrasena(), contrasenaCodificada);
        if (!validarContrasenaConvertir) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\":\"Credenciales inválidas\"}").build();
        }
        String contrasenaLoginCodificada = PasswordUtil.codificarPassword(login.getContrasena());
        boolean validar = loginDAO.autenticar(login.getCedula(),contrasenaLoginCodificada);
        if (!validar) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\":\"Credenciales inválidas\"}").build();
        }
        boolean activo = loginDAO.ValidarActivo(login.getCedula());
        if (!activo) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"Error\":\"Usuario no activo\"}").build();
        }
        loginDAO.obtenerDatos(login);
        List<String> permisos = loginDAO.obtenerPermisos(login.getCedula());
        login.setPermisos(permisos);
        String token = JwtUtil.generarAccessToken();
        String refreshToken = JwtUtil.generarRefreshToken();
        login.setToken(token);
        login.setRefreshToken(refreshToken);
        return Response.ok(login).build();
    }
}
