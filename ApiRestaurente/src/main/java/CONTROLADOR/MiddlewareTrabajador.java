package CONTROLADOR;

public class MiddlewareTrabajador {
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
    public static String fechaValida(String valor, String campo) {
    if (valor == null || valor.trim().isEmpty()) {
        return campo + " no puede estar vacío";
    }

    // Expresión regular para formato de fecha: YYYY-MM-DD (con validación básica de formato)
    if (!valor.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
        return campo + " debe tener el formato YYYY-MM-DD";
    }

    return "ok";
}

    public static String numeroMayorA0(String valor, String campo) {
        try {
            int numero = Integer.parseInt(valor);  // Intenta convertir a número

            if (numero <= 0) {
                return campo + " debe ser mayor a 0"; // Si es 0 o negativo, error
            }

        } catch (NumberFormatException e) {
            return campo + " debe ser un número válido"; // Si no es un número, error
        }

        return "ok"; // Si pasa todas las validaciones, retorna "ok"
    }
}
