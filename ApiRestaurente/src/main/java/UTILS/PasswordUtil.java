package UTILS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {

    /**
     * Convierte una contraseña en un hash "raro".
     * 
     * @param password contraseña normal
     * @return contraseña codificada (hash en base64)
     */
    public static String codificarPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al codificar la contraseña", e);
        }
    }

    /**
     * Compara una contraseña en texto plano contra una codificada.
     * 
     * @param passwordNormal contraseña sin codificar
     * @param passwordCodificada contraseña previamente codificada
     * @return true si coinciden, false si no
     */
    public static boolean validarPassword(String passwordNormal, String passwordCodificada) {
        String codificada = codificarPassword(passwordNormal);
        return codificada.equals(passwordCodificada);
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        String contra = "MiClaveSuperSecreta";
        String contraHash = codificarPassword(contra);

        System.out.println("Contraseña original: " + contra);
        System.out.println("Contraseña codificada: " + contraHash);

        boolean valida = validarPassword("MiClaveSuperSecreta", contraHash);
        System.out.println("¿Coincide? " + valida);

        boolean invalida = validarPassword("OtraClave", contraHash);
        System.out.println("¿Coincide con otra clave? " + invalida);
    }
}
