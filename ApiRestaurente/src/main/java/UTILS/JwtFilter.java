package UTILS;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.equals("login")
                || path.equals("token")
                || path.startsWith("register")
                || path.equals("roles")
                || path.equals("imagen")) {
            return;
        }
        String authHeader = requestContext.getHeaderString("Authorization");
        // Validar que exista y tenga formato "Bearer <token>"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"Error\":\"Header ausente o Invalido\"}")
                            .build()
            );
            return;
        }

        // Extraer token
        String token = authHeader.substring("Bearer".length()).trim();
        try {
            // Validar token (aquí llamas tu clase JwtUtil o lo que uses)
            if (!JwtUtil.validarTokenBoolean(token)) {
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED)
                                .entity("{\"TokenInvalido\":\"Token Invalido\"}")
                                .build()
                );
            }
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"TokenInvalido\":\"Validado Token "+ e.getMessage()+"\"}")
                            .build()
            );
        }
    }
}
