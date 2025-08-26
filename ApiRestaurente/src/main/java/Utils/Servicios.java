package Utils;

public class Servicios {

    public static String CalcularReserva(String cantidadTentativa) {
        double reservaUnitaria = 5500.0;
        int cantidadParseada = Integer.parseInt(cantidadTentativa);
        double precio = reservaUnitaria * cantidadParseada;
        String precioReserva = String.valueOf(precio);
        return precioReserva;
    }
}
