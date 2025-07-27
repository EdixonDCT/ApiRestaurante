package MODELO;

public class Caja {
    private String id;
    private String fechaApertura;
    private String horaApertura;
    private String montoApertura;
    private String fechaCierre;
    private String horaCierre;
    private String montoCierre;
    private String cedulaTrabajador;
    private String nombreCajero;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(String montoApertura) {
        this.montoApertura = montoApertura;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(String montoCierre) {
        this.montoCierre = montoCierre;
    }

    public String getCedulaTrabajador() {
        return cedulaTrabajador;
    }

    public void setCedulaTrabajador(String cedulaTrabajador) {
        this.cedulaTrabajador = cedulaTrabajador;
    }

    public String getNombreCajero() {
        return nombreCajero;
    }

    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }
}
