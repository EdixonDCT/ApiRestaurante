package CONTROLADOR; // Paquete al que pertenece la clase

// Clase que contiene validaciones comunes para los oficios
public class MiddlewareOficio {


    public static Boolean Vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public static String textoValido(String valor, String campo) {
        if (Vacio(valor)) {
            return campo + " no puede estar vacío"; // Si está vacío, retorna mensaje de error
        }

        // Expresión regular para permitir letras (mayúsculas/minúsculas), espacios y caracteres con tilde
        if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return campo + " debe contener solo letras"; // Si no cumple, mensaje de error
        }

        return "ok"; // Si pasa todas las validaciones, retorna "ok"
    }

    /**
     * Valida que el valor recibido sea un número mayor a cero.
     * @param valor Texto que representa el número.
     * @param campo Nombre del campo (para usar en los mensajes de error).
     * @return "ok" si es válido, mensaje de error si no lo es.
     */
    public static String numeroMayorA0(String valor, String campo) {
        try {
            double numero = Double.parseDouble(valor); // Intenta convertir a número

            if (numero <= 0) {
                return campo + " debe ser mayor a 0"; // Si es 0 o negativo, error
            }

        } catch (NumberFormatException e) {
            return campo + " debe ser un número válido"; // Si no es un número, error
        }

        return "ok"; // Si pasa todas las validaciones, retorna "ok"
    }
}
