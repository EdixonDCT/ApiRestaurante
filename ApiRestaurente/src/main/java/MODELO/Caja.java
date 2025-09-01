package MODELO; // Define el paquete MODELO donde se encuentra la clase Caja

public class Caja { // Define la clase Caja que representa una caja registradora

    private String id; // Identificador único de la caja
    private String fechaApertura; // Fecha en que se abrió la caja
    private String horaApertura; // Hora en que se abrió la caja
    private String montoApertura; // Monto inicial con el que se abrió la caja
    private String fechaCierre; // Fecha en que se cerró la caja
    private String horaCierre; // Hora en que se cerró la caja
    private String montoCierre; // Monto final al cerrar la caja
    private String cedulaTrabajador; // Cédula del trabajador que operó la caja
    private String nombreCajero; // Nombre del cajero que operó la caja

    public String getId() { // Método para obtener el id de la caja
        return id;
    }

    public void setId(String id) { // Método para establecer el id de la caja
        this.id = id;
    }

    public String getFechaApertura() { // Método para obtener la fecha de apertura
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) { // Método para establecer la fecha de apertura
        this.fechaApertura = fechaApertura;
    }

    public String getHoraApertura() { // Método para obtener la hora de apertura
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) { // Método para establecer la hora de apertura
        this.horaApertura = horaApertura;
    }

    public String getMontoApertura() { // Método para obtener el monto de apertura
        return montoApertura;
    }

    public void setMontoApertura(String montoApertura) { // Método para establecer el monto de apertura
        this.montoApertura = montoApertura;
    }

    public String getFechaCierre() { // Método para obtener la fecha de cierre
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) { // Método para establecer la fecha de cierre
        this.fechaCierre = fechaCierre;
    }

    public String getHoraCierre() { // Método para obtener la hora de cierre
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) { // Método para establecer la hora de cierre
        this.horaCierre = horaCierre;
    }

    public String getMontoCierre() { // Método para obtener el monto de cierre
        return montoCierre;
    }

    public void setMontoCierre(String montoCierre) { // Método para establecer el monto de cierre
        this.montoCierre = montoCierre;
    }

    public String getCedulaTrabajador() { // Método para obtener la cédula del trabajador
        return cedulaTrabajador;
    }

    public void setCedulaTrabajador(String cedulaTrabajador) { // Método para establecer la cédula del trabajador
        this.cedulaTrabajador = cedulaTrabajador;
    }

    public String getNombreCajero() { // Método para obtener el nombre del cajero
        return nombreCajero;
    }

    public void setNombreCajero(String nombreCajero) { // Método para establecer el nombre del cajero
        this.nombreCajero = nombreCajero;
    }
}
