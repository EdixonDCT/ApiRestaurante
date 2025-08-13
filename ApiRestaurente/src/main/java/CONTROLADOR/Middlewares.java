package CONTROLADOR;

public class Middlewares {

    // Método auxiliar para verificar si una cadena es nula o vacía
    public static Boolean Vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // Valida que el campo de la contraseña no esté vacío
    public static String ContraseñaVacia(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio"; // Retorna error si está vacío
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena de texto contenga solo letras y espacios
    public static String validarString(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio"; // Retorna error si está vacío
        }
        // Expresión regular para permitir letras (mayúsculas/minúsculas), espacios y tildes
        if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "Error: " + campo + " debe contener solo letras"; // Retorna error si no cumple el formato
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena sea un número decimal (double) mayor que cero
    public static String validarDouble(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio "; // Retorna error si está vacío
        }
        // Expresión regular para números decimales, incluyendo negativos
        if (!valor.matches("^-?\\d+(\\.\\d+)?$")) {
            return "Error: " + campo + " debe ser un número y formato correcto (ej: 1  1.0  0.5)";
        }
        try {
            double numero = Double.parseDouble(valor); // Intenta convertir a double
            if (numero <= 0) {
                return "Error: " + campo + " debe ser mayor a 0"; // Retorna error si es menor o igual a cero
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un numero valido"; // Retorna error si no es un número
        }
        return "ok"; // Retorna "ok" si es válido
    }
    
    // Valida que una cadena sea un número decimal (double) para un valor de caja, con un límite superior
    public static String validarDoubleCaja(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio "; // Retorna error si está vacío
        }
        if (!valor.matches("^-?\\d+(\\.\\d+)?$")) {
            return "Error: " + campo + " debe ser un número y formato correcto (ej: 1  1.0  0.5)";
        }
        try {
            double numero = Double.parseDouble(valor); // Intenta convertir a double
            if (numero <= 0) {
                return "Error: " + campo + " debe ser mayor a 0"; // Retorna error si es menor o igual a cero
            }
            if (numero > 10000000.00)  {
                return "Error: " + campo + " no puede tener mas de 10.000.000,00$ en efectivo"; // Retorna error si excede el límite
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un numero valido"; // Retorna error si no es un número
        }
        return "ok"; // Retorna "ok" si es válido
    }
    
    // Valida que una cadena sea un número entero mayor que cero
    public static String validarEntero(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio "; // Retorna error si está vacío
        }
        // Expresión regular para números enteros (sin decimales)
        if (!valor.matches("^-?\\d+$")) {
            return "Error: " + campo + " debe ser un numero entero valido";
        }
        try {
            long numero = Long.parseLong(valor);
            if (numero <= 0) {
                return "Error: " + campo + " debe ser mayor a 0"; // Retorna error si es menor o igual a cero
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un número entero válido"; // Retorna error si no es un entero
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena sea un número entero mayor que cero, permitiendo que el campo sea nulo/vacío
    public static String validarEnteroNulo(String valor, String campo) {
        if (Vacio(valor)) {
            return "ok"; // Retorna "ok" si el campo está vacío, ya que se permite
        }
        // Expresión regular para números enteros
        if (!valor.matches("^-?\\d+$")) {
            return "Error: " + campo + " debe ser un numero entero valido";
        }
        try {
            int numero = Integer.parseInt(valor);
            if (numero <= 0) {
                return "Error: " + campo + " debe ser mayor a 0"; // Retorna error si es menor o igual a cero
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un número entero válido";
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena tenga el formato de fecha YYYY-MM-DD
    public static String validarFecha(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio"; // Retorna error si está vacío
        }
        // Expresión regular para formato de fecha YYYY-MM-DD
        if (!valor.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return "Error: " + campo + " debe tener el formato YYYY-MM-DD";
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena tenga el formato de hora HH:mm:ss
    public static String validarHora(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacío";
        }
        // Expresión regular para hora militar con segundos
        if (!valor.matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            return "Error: " + campo + " debe tener el formato HH:mm:ss en hora militar (00:00:00 a 23:59:59)";
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena sea '0' o '1' para representar un booleano
    public static String validarBooleano(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio"; // Retorna error si está vacío
        }
        String val = valor.trim();
        if (!val.equals("0") && !val.equals("1")) {
            return "Error: " + campo + " debe ser '0' o '1'";
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena sea un entero de 9 o 10 dígitos, permitiendo que el campo sea nulo/vacío
    public static String validarCantidad9o10(String valor, String campo) {
        if (Vacio(valor)) {
            return "ok"; // Retorna "ok" si está vacío, ya que se permite
        }
        if (!valor.matches("^-?\\d+$")) {
            return "Error: " + campo + " debe ser un numero entero valido";
        }
        long numero = Long.parseLong(valor);
        if (numero <= 0) {
            return "Error: " + campo + " debe ser mayor a 0";
        }
        try {
            int longitud = valor.length();
            if (longitud != 9 && longitud != 10) {
                return "Error: " + campo + " debe tener 9 o 10 caracteres";
            }
        } catch (NumberFormatException e) {
            return "Error: " + campo + " debe ser un número entero válido";
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena sea una dirección de correo electrónico válida
    public static String validarCorreo(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio";
        }
        // Expresión regular para validar correos electrónicos
        if (!valor.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Error: " + campo + " debe ser un correo electrónico válido";
        }
        return "ok"; // Retorna "ok" si es válido
    }

    // Valida que una cadena sea una de las unidades de bebida permitidas
    public static String validarUnidadBebida(String valor, String campo) {
        if (Vacio(valor)) {
            return "Error: " + campo + " no puede estar vacio";
        }
        String unidad = valor.toLowerCase();
        if (!(unidad.equals("vaso") || unidad.equals("botella") || unidad.equals("taza"))) {
            return "Error: " + campo + " debe ser 'vaso', 'botella', 'lata' o 'taza'";
        }
        return "ok"; // Retorna "ok" si es válido
    }
}
