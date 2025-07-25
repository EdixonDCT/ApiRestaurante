package MODELO;

public class DetallePedido {
    private String id;
    private String id_pedido;
    private String id_comida;
    private String cantidad_comida;
    private String id_bebida;
    private String cantidad_bebida;
    private String id_coctel;
    private String cantidad_coctel;

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_comida() {
        return id_comida;
    }

    public void setId_comida(String id_comida) {
        this.id_comida = id_comida;
    }

    public String getCantidad_comida() {
        return cantidad_comida;
    }

    public void setCantidad_comida(String cantidad_comida) {
        this.cantidad_comida = cantidad_comida;
    }

    public String getId_bebida() {
        return id_bebida;
    }

    public void setId_bebida(String id_bebida) {
        this.id_bebida = id_bebida;
    }

    public String getCantidad_bebida() {
        return cantidad_bebida;
    }

    public void setCantidad_bebida(String cantidad_bebida) {
        this.cantidad_bebida = cantidad_bebida;
    }

    public String getId_coctel() {
        return id_coctel;
    }

    public void setId_coctel(String id_coctel) {
        this.id_coctel = id_coctel;
    }

    public String getCantidad_coctel() {
        return cantidad_coctel;
    }

    public void setCantidad_coctel(String cantidad_coctel) {
        this.cantidad_coctel = cantidad_coctel;
    }
}
