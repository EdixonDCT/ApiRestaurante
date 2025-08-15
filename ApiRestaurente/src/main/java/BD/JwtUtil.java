package BD;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {
    
    private static final String SECRET_KEY = "ApiRestaurante_Clave_Super_SECRET";
   
    // Tiempo de expiración para el token de acceso (1 hora en milisegundos)
    private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000;

    // Tiempo de expiración para el refresh token (7 días en milisegundos)
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    /**
     * Genera un token de acceso con duración de 1 hora.
     * @param idUsuario ID del usuario.
     * @param rol Rol del usuario (por ejemplo, "admin", "cliente").
     * @return Token JWT como String.
     */
    
    public static String generarAccessToken(int idUsuario, String rol) {
        return JWT.create()
                .withSubject(String.valueOf(idUsuario))
                .withClaim("rol", rol)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Genera un refresh token con duración de 7 días.
     * @param idUsuario ID del usuario.
     * @return Refresh token JWT como String.
     */
    public static String generarRefreshToken(int idUsuario) {
        return JWT.create()
                .withSubject(String.valueOf(idUsuario))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Verifica y decodifica un token JWT.
     * @param token Token JWT como String.
     * @return Objeto DecodedJWT si es válido.
     */
    
    public static DecodedJWT verificarToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        return verifier.verify(token);
    }

    /**
     * Obtiene el ID del usuario desde un token ya decodificado.
     * @param decodedJWT Token decodificado.
     * @return ID del usuario como int.
     */
    public static int obtenerIdUsuario(DecodedJWT decodedJWT) {
        return Integer.parseInt(decodedJWT.getSubject());
    }

    /**
     * Obtiene el rol del usuario desde un token ya decodificado.
     * @param decodedJWT Token decodificado.
     * @return Rol como String.
     */
    public static String obtenerRolUsuario(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("rol").asString();
    }
}