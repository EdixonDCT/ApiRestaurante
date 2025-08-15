package CONTROLADOR;

import CONTROLADOR.Middlewares;
import MODELO.Login;

import BD.JwtUtil;
import javax.ws.rs.*; // Importa todas las clases del paquete JAX-RS para crear servicios web RESTful.
import javax.ws.rs.core.MediaType; // Importa la clase MediaType para especificar los tipos de datos (ej. JSON).
import javax.ws.rs.core.Response; // Importa la clase Response para construir las respuestas HTTP.


@Path("/login")
@Consumes(MediaType.APPLICATION_JSON) // Indica que consume JSON
@Produces(MediaType.APPLICATION_JSON) // Indica que produce JSON

public class LoginControlador {

    @GET
    public Response login() 
    {
        Login login = new Login();
        String cedula = "1140424793";
        Integer cedulaInt = Integer.parseInt(cedula); 
        String accessToken = JwtUtil.generarAccessToken(cedulaInt,"Admin");
        String refreshToken = JwtUtil.generarRefreshToken(1140424793);
        login.setCedula(cedula);
        login.setContrasena("ADSO");
        login.setRol("Administrador");
        login.setToken(accessToken);
        login.setRefreshToken(refreshToken);
        return Response.ok(login).build();
    }
}
