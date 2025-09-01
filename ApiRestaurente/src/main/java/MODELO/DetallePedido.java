package MODELO;

public class DetallePedido {
    private String id;
    private String id_pedido;

    // Pedido
    private String fecha;
    private String hora;
    private String numero_mesa;
    private String metodo_pago;
    private String id_reserva;

    // Comidas
    private String id_comida;
    private String comida;
    private String precio_comida;
    private String cantidad_comida;
    private String nota_comida;
    private String total_comida;

    // Bebidas
    private String id_bebida;
    private String bebida;
    private String precio_bebida;
    private String cantidad_bebida;
    private String nota_bebida;
    private String total_bebida;

    // Cócteles
    private String id_coctel;
    private String coctel;
    private String precio_coctel;
    private String cantidad_coctel;
    private String nota_coctel;
    private String total_coctel;

    // Reserva
    private String precio_reserva;

    // Cajero
    private String nombre_cajero;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getId_pedido() { return id_pedido; }
    public void setId_pedido(String id_pedido) { this.id_pedido = id_pedido; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getNumero_mesa() { return numero_mesa; }
    public void setNumero_mesa(String numero_mesa) { this.numero_mesa = numero_mesa; }

    public String getMetodo_pago() { return metodo_pago; }
    public void setMetodo_pago(String metodo_pago) { this.metodo_pago = metodo_pago; }

    public String getId_reserva() { return id_reserva; }
    public void setId_reserva(String id_reserva) { this.id_reserva = id_reserva; }

    public String getId_comida() { return id_comida; }
    public void setId_comida(String id_comida) { this.id_comida = id_comida; }

    public String getComida() { return comida; }
    public void setComida(String comida) { this.comida = comida; }

    public String getPrecio_comida() { return precio_comida; }
    public void setPrecio_comida(String precio_comida) { this.precio_comida = precio_comida; }

    public String getCantidad_comida() { return cantidad_comida; }
    public void setCantidad_comida(String cantidad_comida) { this.cantidad_comida = cantidad_comida; }

    public String getNota_comida() { return nota_comida; }
    public void setNota_comida(String nota_comida) { this.nota_comida = nota_comida; }

    public String getTotal_comida() { return total_comida; }
    public void setTotal_comida(String total_comida) { this.total_comida = total_comida; }

    public String getId_bebida() { return id_bebida; }
    public void setId_bebida(String id_bebida) { this.id_bebida = id_bebida; }

    public String getBebida() { return bebida; }
    public void setBebida(String bebida) { this.bebida = bebida; }

    public String getPrecio_bebida() { return precio_bebida; }
    public void setPrecio_bebida(String precio_bebida) { this.precio_bebida = precio_bebida; }

    public String getCantidad_bebida() { return cantidad_bebida; }
    public void setCantidad_bebida(String cantidad_bebida) { this.cantidad_bebida = cantidad_bebida; }

    public String getNota_bebida() { return nota_bebida; }
    public void setNota_bebida(String nota_bebida) { this.nota_bebida = nota_bebida; }

    public String getTotal_bebida() { return total_bebida; }
    public void setTotal_bebida(String total_bebida) { this.total_bebida = total_bebida; }

    public String getId_coctel() { return id_coctel; }
    public void setId_coctel(String id_coctel) { this.id_coctel = id_coctel; }

    public String getCoctel() { return coctel; }
    public void setCoctel(String coctel) { this.coctel = coctel; }

    public String getPrecio_coctel() { return precio_coctel; }
    public void setPrecio_coctel(String precio_coctel) { this.precio_coctel = precio_coctel; }

    public String getCantidad_coctel() { return cantidad_coctel; }
    public void setCantidad_coctel(String cantidad_coctel) { this.cantidad_coctel = cantidad_coctel; }

    public String getNota_coctel() { return nota_coctel; }
    public void setNota_coctel(String nota_coctel) { this.nota_coctel = nota_coctel; }

    public String getTotal_coctel() { return total_coctel; }
    public void setTotal_coctel(String total_coctel) { this.total_coctel = total_coctel; }

    public String getPrecio_reserva() { return precio_reserva; }
    public void setPrecio_reserva(String precio_reserva) { this.precio_reserva = precio_reserva; }

    public String getNombre_cajero() { return nombre_cajero; }
    public void setNombre_cajero(String nombre_cajero) { this.nombre_cajero = nombre_cajero; }
}
