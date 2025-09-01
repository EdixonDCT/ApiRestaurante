package MODELO; // Define el paquete MODELO donde se encuentra la clase Reserva

public class Reserva { // Clase que representa una reserva realizada por un cliente

    private String id; // Identificador único de la reserva
    private String cantidadTentativa; // Cantidad estimada de personas para la reserva
    private String precio; // Precio asociado a la reserva
    private String fecha; // Fecha en que se registró la reserva
    private String fechaTentativa; // Fecha tentativa solicitada por el cliente
    private String horaTentativa; // Hora tentativa solicitada por el cliente

    // Métodos getter y setter para acceder y modificar cada atributo

    public String getId() { // Obtener el id de la reserva
        return id;
    }

    public void setId(String id) { // Asignar el id de la reserva
        this.id = id;
    }

    public String getCantidadTentativa() { // Obtener la cantidad tentativa de personas
        return cantidadTentativa;
    }

    public void setCantidadTentativa(String cantidadTentativa) { // Asignar la cantidad tentativa de personas
        this.cantidadTentativa = cantidadTentativa;
    }

    public String getPrecio() { // Obtener el precio de la reserva
        return precio;
    }

    public void setPrecio(String precio) { // Asignar el precio de la reserva
        this.precio = precio;
    }

    public String getFecha() { // Obtener la fecha de registro de la reserva
        return fecha;
    }

    public void setFecha(String fecha) { // Asignar la fecha de registro de la reserva
        this.fecha = fecha;
    }

    public String getFechaTentativa() { // Obtener la fecha tentativa solicitada
        return fechaTentativa;
    }

    public void setFechaTentativa(String fechaTentativa) { // Asignar la fecha tentativa solicitada
        this.fechaTentativa = fechaTentativa;
    }

    public String getHoraTentativa() { // Obtener la hora tentativa solicitada
        return horaTentativa;
    }

    public void setHoraTentativa(String horaTentativa) { // Asignar la hora tentativa solicitada
        this.horaTentativa = horaTentativa;
    }
}
