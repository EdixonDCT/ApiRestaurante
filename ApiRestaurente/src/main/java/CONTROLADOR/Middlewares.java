package CONTROLADOR;

public class Middlewares {
    
    public static Boolean Vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public static String validarString(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio"; // Si está vacío, retorna mensaje de error
        }
        // Expresión regular para permitir letras (mayúsculas/minúsculas), espacios y caracteres con tilde
        if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "Error: " + campo + " debe contener solo letras"; // Si no cumple, mensaje de error
        }
        return "ok"; // Si pasa todas las validaciones, retorna "ok"
    }

    public static String validarDouble(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio "; // Si está vacío, retorna mensaje de error
        }
        if (!valor.matches("^-?\\d+(\\.\\d+)?$")) {
            return "Error: " + campo + " debe ser un número y formato correcto (ej: 1  1.0  0.5)";
        }
        try {
            double numero = Double.parseDouble(valor); // Intenta convertir a número
            if (numero <= 0) {
                return "Error: " + campo + " debe ser mayor a 0"; // Si es 0 o negativo, error
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un numero valido"; // Si no es un número, error
        }
        return "ok"; // Si pasa todas las validaciones, retorna "ok"
    }

    public static String validarEntero(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio "; // Si está vacío, retorna mensaje de error
        }
        // Validar solo enteros positivos (sin punto)
        if (!valor.matches("^-?\\d+$")) {
            return "Error: " + campo + " debe ser un numero entero valido";
        }
        try {
            int numero = Integer.parseInt(valor);
            if (numero <= 0) {
                return "Error: " + campo + " debe ser mayor a 0";
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un número entero válido"+valor;
        }
        return "ok";
    }
    
    public static String validarFecha(String valor, String campo) {
    if (valor == null || valor.trim().isEmpty()) {
        return campo + " no puede estar vacío";
    }

    // Expresión regular para formato de fecha: YYYY-MM-DD (con validación básica de formato)
    if (!valor.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
        return campo + " debe tener el formato YYYY-MM-DD";
    }

    return "ok";
}
}
