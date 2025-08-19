package BD;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "ApiRestaurante_Clave_Super_SECRET";

    // Expiraciones
    private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000; // 1 hora
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 días
    private static final long RENEW_THRESHOLD = 5 * 60 * 1000; // 5 minutos

    /**
     * Genera un access token.
     */
    public static String generarAccessToken() {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Genera un refresh token.
     */
    public static String generarRefreshToken() {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     * Verifica y decodifica un token.
     */
    public static DecodedJWT verificarToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        return verifier.verify(token);
    }

    /**
     * Comprueba si un token aún es válido.
     */
    public static boolean esTokenValido(String token) {
        try {
            DecodedJWT decoded = verificarToken(token);
            return decoded.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Valida y renueva un access token si corresponde.
     * 
     * Escenarios:
     * - Si access token válido y > 5 min → retorna el mismo token.
     * - Si válido pero ≤ 5 min → genera uno nuevo.
     * - Si expiró pero refresh token válido → genera uno nuevo.
     * - Si ambos inválidos → retorna null.
     */
    public static TokenValidationResult validarYRenovarAccessToken(String accessToken, String refreshToken) {
        try {
            DecodedJWT decoded = verificarToken(accessToken);
            Date expira = decoded.getExpiresAt();
            long tiempoRestante = expira.getTime() - System.currentTimeMillis();

            // Si está próximo a expirar (≤ 5 minutos)
            if (tiempoRestante <= RENEW_THRESHOLD && tiempoRestante > 0) {
                return new TokenValidationResult(true, generarAccessToken());
            }

            // Si aún está válido normalmente
            if (tiempoRestante > 0) {
                return new TokenValidationResult(true, accessToken);
            }

        } catch (Exception e) {
            // Access token inválido → probamos con el refresh
            if (esTokenValido(refreshToken)) {
                return new TokenValidationResult(true, generarAccessToken());
            }
        }

        // Ambos inválidos
        return new TokenValidationResult(false, null);
    }

    // Clase auxiliar para retornar resultado y token
    public static class TokenValidationResult {
        private boolean valido;
        private String nuevoToken;

        public TokenValidationResult(boolean valido, String nuevoToken) {
            this.valido = valido;
            this.nuevoToken = nuevoToken;
        }

        public boolean isValido() {
            return valido;
        }

        public String getNuevoToken() {
            return nuevoToken;
        }
    }
}
