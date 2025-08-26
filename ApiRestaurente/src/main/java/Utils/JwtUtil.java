package Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "ApiRestaurante_Clave_Super_SECRET";

    // Expiraciones
    private static final long ACCESS_TOKEN_EXPIRATION =  10 * 60 * 1000; //10 minutos,60* 60 * 1000 = 1 hora
    private static final long REFRESH_TOKEN_EXPIRATION = 60 * 60 * 1000; //1 hora,7 * 24 * 60 * 60 * 1000 = 7 días
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

    public static String generarAccessRefresh() {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
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
     * Escenarios: - Si access token válido y > 5 min → retorna el mismo token.
     * - Si válido pero ≤ 5 min → genera uno nuevo. - Si expiró pero refresh
     * token válido → genera uno nuevo. - Si ambos inválidos → retorna null.
     */
    public static TokenValidationResult validarYRenovarAccessToken(String accessToken, String refreshToken) {
        try {
            DecodedJWT decoded = verificarToken(accessToken);
            Date expira = decoded.getExpiresAt();
            long tiempoRestante = expira.getTime() - System.currentTimeMillis();

            DecodedJWT decodedRefresh = verificarToken(refreshToken);
            Date expiraRefresh = decodedRefresh.getExpiresAt();
            long tiempoRestanteRefresh = expiraRefresh.getTime() - System.currentTimeMillis();
            
            //si el refresh token esta proximo a expirar y el token normal tambien
            if (tiempoRestanteRefresh <= RENEW_THRESHOLD && tiempoRestanteRefresh > 0 && tiempoRestante <= RENEW_THRESHOLD && tiempoRestante > 0) {
                return new TokenValidationResult(true, generarAccessToken(), generarAccessRefresh());
            }
            if (tiempoRestanteRefresh <= RENEW_THRESHOLD && tiempoRestanteRefresh > 0) {
                return new TokenValidationResult(true, null, generarAccessRefresh());
            }
            // Si está próximo a expirar (≤ 5 minutos)
            if (tiempoRestante <= RENEW_THRESHOLD && tiempoRestante > 0) {
                return new TokenValidationResult(true, generarAccessToken(), null);
            }

            // Si aún está válido normalmente
            if (tiempoRestante > 0) {
                return new TokenValidationResult(true, null, null);
            }

        } catch (Exception e) {
            if (esTokenValido(refreshToken)) {
                return new TokenValidationResult(true, generarAccessToken(), null);
            }
        }

        // Ambos inválidos
        return new TokenValidationResult(false, null, null);
    }

    // Clase auxiliar para retornar resultado y token
    public static class TokenValidationResult {

        private boolean valido;
        private String token;
        private String refreshToken;

        public TokenValidationResult(boolean Sivalido, String Sitoken, String SirefreshToken) {
            this.valido = Sivalido;
            this.token = Sitoken;
            this.refreshToken = SirefreshToken;
        }

        public boolean EsValido() {
            return valido;
        }

        public boolean getValido() {
            return valido;
        }

        public String getToken() {
            return token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
