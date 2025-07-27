package MODELO;

public class Reserva {
    private String id;
    private String cantidadTentativa;
    private String precio;
    private String fecha;
    private String fechaTentativa;
    private String horaTentativa;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCantidadTentativa() {
        return cantidadTentativa;
    }

    public void setCantidadTentativa(String cantidadTentativa) {
        this.cantidadTentativa = cantidadTentativa;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaTentativa() {
        return fechaTentativa;
    }

    public void setFechaTentativa(String fechaTentativa) {
        this.fechaTentativa = fechaTentativa;
    }

    public String getHoraTentativa() {
        return horaTentativa;
    }

    public void setHoraTentativa(String horaTentativa) {
        this.horaTentativa = horaTentativa;
    }
}
