package UTILS;

public class Middlewares {

    // Método auxiliar para verificar si una cadena es nula o vacía
    public static Boolean Vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // Valida que el campo de la contraseña no esté vacío
    public static String ContraseñaVacia(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        return "ok";
    }

    // Valida que una cadena de texto contenga solo letras y espacios
    public static String validarString(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "{\"Error\":\"" + campo + " debe contener solo letras\"}";
        }
        return "ok";
    }

    // Valida que una cadena sea un número decimal (double) mayor que cero
    public static String validarDouble(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        if (!valor.matches("^-?\\d+(\\.\\d+)?$")) {
            return "{\"Error\":\"" + campo + " debe ser un número y formato correcto (ej: 1  1.0  0.5)\"}";
        }
        try {
            double numero = Double.parseDouble(valor);
            if (numero <= 0) {
                return "{\"Error\":\"" + campo + " debe ser mayor a 0\"}";
            }
        } catch (NumberFormatException e) {
            return "{\"Error\":\"" + campo + " debe ser un numero valido\"}";
        }
        return "ok";
    }

    // Valida que una cadena sea un número decimal (double) con límite
    public static String validarDoubleCaja(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        if (!valor.matches("^-?\\d+(\\.\\d+)?$")) {
            return "{\"Error\":\"" + campo + " debe ser un número y formato correcto (ej: 1  1.0  0.5)\"}";
        }
        try {
            double numero = Double.parseDouble(valor);
            if (numero <= 0) {
                return "{\"Error\":\"" + campo + " debe ser mayor a 0\"}";
            }
            if (numero > 10000000.00) {
                return "{\"Error\":\"" + campo + " no puede tener mas de 10.000.000,00$ en efectivo\"}";
            }
        } catch (NumberFormatException e) {
            return "{\"Error\":\"" + campo + " debe ser un numero valido\"}";
        }
        return "ok";
    }

    // Valida entero mayor que cero
    public static String validarEntero(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        if (!valor.matches("^-?\\d+$")) {
            return "{\"Error\":\"" + campo + " debe ser un numero entero valido\"}";
        }
        try {
            long numero = Long.parseLong(valor);
            if (numero <= 0) {
                return "{\"Error\":\"" + campo + " debe ser mayor a 0\"}";
            }
        } catch (NumberFormatException e) {
            return "{\"Error\":\"" + campo + " debe ser un número entero válido\"}";
        }
        return "ok";
    }

    // Valida entero nulo o mayor a cero
    public static String validarEnteroNulo(String valor, String campo) {
        if (Vacio(valor)) {
            return "ok";
        }
        if (!valor.matches("^-?\\d+$")) {
            return "{\"Error\":\"" + campo + " debe ser un numero entero valido\"}";
        }
        try {
            int numero = Integer.parseInt(valor);
            if (numero <= 0) {
                return "{\"Error\":\"" + campo + " debe ser mayor a 0\"}";
            }
        } catch (NumberFormatException e) {
            return "{\"Error\":\"" + campo + " debe ser un número entero válido\"}";
        }
        return "ok";
    }

    // Fecha
    public static String validarFecha(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        if (!valor.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return "{\"Error\":\"" + campo + " debe tener el formato YYYY-MM-DD\"}";
        }
        return "ok";
    }

    // Hora
    public static String validarHora(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacío\"}";
        }
        if (!valor.matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            return "{\"Error\":\"" + campo + " debe tener el formato HH:mm:ss en hora militar (00:00:00 a 23:59:59)\"}";
        }
        return "ok";
    }

    // Booleano
    public static String validarBooleano(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        String val = valor.trim();
        if (!val.equals("0") && !val.equals("1")) {
            return "{\"Error\":\"" + campo + " debe ser '0' o '1'\"}";
        }
        return "ok";
    }

    // Cantidad 9 o 10 dígitos
    public static String validarCantidad9o10(String valor, String campo) {
        if (Vacio(valor)) {
            return "ok";
        }
        if (!valor.matches("^-?\\d+$")) {
            return "{\"Error\":\"" + campo + " debe ser un numero entero valido\"}";
        }
        long numero = Long.parseLong(valor);
        if (numero <= 0) {
            return "{\"Error\":\"" + campo + " debe ser mayor a 0\"}";
        }
        int longitud = valor.length();
        if (longitud != 9 && longitud != 10) {
            return "{\"Error\":\"" + campo + " debe tener 9 o 10 caracteres\"}";
        }
        return "ok";
    }

    // Correo
    public static String validarCorreo(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        if (!valor.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "{\"Error\":\"" + campo + " debe ser un correo electrónico válido\"}";
        }
        return "ok";
    }

    // Unidad bebida
    public static String validarUnidadBebida(String valor, String campo) {
        if (Vacio(valor)) {
            return "{\"Error\":\"" + campo + " no puede estar vacio\"}";
        }
        String unidad = valor.toLowerCase();
        if (!(unidad.equals("vaso") || unidad.equals("botella") || unidad.equals("taza"))) {
            return "{\"Error\":\"" + campo + " debe ser 'vaso', 'botella', 'lata' o 'taza'\"}";
        }
        return "ok";
    }
}
