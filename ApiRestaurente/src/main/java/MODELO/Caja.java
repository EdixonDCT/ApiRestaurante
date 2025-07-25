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

    // Getters
    public String getId() {
        return id;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public String getMontoApertura() {
        return montoApertura;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public String getMontoCierre() {
        return montoCierre;
    }

    public String getCedulaTrabajador() {
        return cedulaTrabajador;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public void setMontoApertura(String montoApertura) {
        this.montoApertura = montoApertura;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public void setMontoCierre(String montoCierre) {
        this.montoCierre = montoCierre;
    }

    public void setCedulaTrabajador(String cedulaTrabajador) {
        this.cedulaTrabajador = cedulaTrabajador;
    }
}
