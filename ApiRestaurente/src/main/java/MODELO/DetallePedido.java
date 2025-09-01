package MODELO; // Define el paquete MODELO donde se encuentra la clase DetallePedido

public class DetallePedido { // Clase que representa el detalle completo de un pedido en el sistema

    private String id; // Identificador único del detalle de pedido
    private String id_pedido; // Identificador del pedido principal

    // Pedido
    private String fecha; // Fecha en que se realizó el pedido
    private String hora; // Hora en que se realizó el pedido
    private String numero_mesa; // Número de mesa asociada al pedido
    private String metodo_pago; // Método de pago utilizado (ej. efectivo, tarjeta)
    private String id_reserva; // Identificador de la reserva asociada (si aplica)

    // Comidas
    private String id_comida; // Identificador de la comida solicitada
    private String comida; // Nombre de la comida
    private String precio_comida; // Precio unitario de la comida
    private String cantidad_comida; // Cantidad de unidades de comida solicitadas
    private String nota_comida; // Nota especial sobre la comida (ej. sin sal)
    private String total_comida; // Total calculado por comida (precio × cantidad)

    // Bebidas
    private String id_bebida; // Identificador de la bebida solicitada
    private String bebida; // Nombre de la bebida
    private String precio_bebida; // Precio unitario de la bebida
    private String cantidad_bebida; // Cantidad de bebidas solicitadas
    private String nota_bebida; // Nota especial sobre la bebida (ej. sin hielo)
    private String total_bebida; // Total calculado por bebida (precio × cantidad)

    // Cócteles
    private String id_coctel; // Identificador del cóctel solicitado
    private String coctel; // Nombre del cóctel
    private String precio_coctel; // Precio unitario del cóctel
    private String cantidad_coctel; // Cantidad de cócteles solicitados
    private String nota_coctel; // Nota especial sobre el cóctel (ej. sin alcohol)
    private String total_coctel; // Total calculado por cóctel (precio × cantidad)

    // Reserva
    private String precio_reserva; // Precio asociado a la reserva (si aplica)

    // Cajero
    private String nombre_cajero; // Nombre del cajero que atendió el pedido

    // Métodos getter y setter para acceder y modificar cada atributo

    public String getId() { return id; } // Obtener el id del detalle
    public void setId(String id) { this.id = id; } // Asignar el id del detalle

    public String getId_pedido() { return id_pedido; } // Obtener el id del pedido
    public void setId_pedido(String id_pedido) { this.id_pedido = id_pedido; } // Asignar el id del pedido

    public String getFecha() { return fecha; } // Obtener la fecha del pedido
    public void setFecha(String fecha) { this.fecha = fecha; } // Asignar la fecha del pedido

    public String getHora() { return hora; } // Obtener la hora del pedido
    public void setHora(String hora) { this.hora = hora; } // Asignar la hora del pedido

    public String getNumero_mesa() { return numero_mesa; } // Obtener el número de mesa
    public void setNumero_mesa(String numero_mesa) { this.numero_mesa = numero_mesa; } // Asignar el número de mesa

    public String getMetodo_pago() { return metodo_pago; } // Obtener el método de pago
    public void setMetodo_pago(String metodo_pago) { this.metodo_pago = metodo_pago; } // Asignar el método de pago

    public String getId_reserva() { return id_reserva; } // Obtener el id de la reserva
    public void setId_reserva(String id_reserva) { this.id_reserva = id_reserva; } // Asignar el id de la reserva

    public String getId_comida() { return id_comida; } // Obtener el id de la comida
    public void setId_comida(String id_comida) { this.id_comida = id_comida; } // Asignar el id de la comida

    public String getComida() { return comida; } // Obtener el nombre de la comida
    public void setComida(String comida) { this.comida = comida; } // Asignar el nombre de la comida

    public String getPrecio_comida() { return precio_comida; } // Obtener el precio de la comida
    public void setPrecio_comida(String precio_comida) { this.precio_comida = precio_comida; } // Asignar el precio de la comida

    public String getCantidad_comida() { return cantidad_comida; } // Obtener la cantidad de comida
    public void setCantidad_comida(String cantidad_comida) { this.cantidad_comida = cantidad_comida; } // Asignar la cantidad de comida

    public String getNota_comida() { return nota_comida; } // Obtener la nota de comida
    public void setNota_comida(String nota_comida) { this.nota_comida = nota_comida; } // Asignar la nota de comida

    public String getTotal_comida() { return total_comida; } // Obtener el total de comida
    public void setTotal_comida(String total_comida) { this.total_comida = total_comida; } // Asignar el total de comida

    public String getId_bebida() { return id_bebida; } // Obtener el id de la bebida
    public void setId_bebida(String id_bebida) { this.id_bebida = id_bebida; } // Asignar el id de la bebida

    public String getBebida() { return bebida; } // Obtener el nombre de la bebida
    public void setBebida(String bebida) { this.bebida = bebida; } // Asignar el nombre de la bebida

    public String getPrecio_bebida() { return precio_bebida; } // Obtener el precio de la bebida
    public void setPrecio_bebida(String precio_bebida) { this.precio_bebida = precio_bebida; } // Asignar el precio de la bebida

    public String getCantidad_bebida() { return cantidad_bebida; } // Obtener la cantidad de bebida
    public void setCantidad_bebida(String cantidad_bebida) { this.cantidad_bebida = cantidad_bebida; } // Asignar la cantidad de bebida

    public String getNota_bebida() { return nota_bebida; } // Obtener la nota de bebida
    public void setNota_bebida(String nota_bebida) { this.nota_bebida = nota_bebida; } // Asignar la nota de bebida

    public String getTotal_bebida() { return total_bebida; } // Obtener el total de bebida
    public void setTotal_bebida(String total_bebida) { this.total_bebida = total_bebida; } // Asignar el total de bebida

    public String getId_coctel() { return id_coctel; } // Obtener el id del cóctel
    public void setId_coctel(String id_coctel) { this.id_coctel = id_coctel; } // Asignar el id del cóctel

    public String getCoctel() { return coctel; } // Obtener el nombre del cóctel
    public void setCoctel(String coctel) { this.coctel = coctel; } // Asignar el nombre del cóctel

    public String getPrecio_coctel() { return precio_coctel; } // Obtener el precio del cóctel
    public void setPrecio_coctel(String precio_coctel) { this.precio_coctel = precio_coctel; } // Asignar el precio del cóctel

    public String getCantidad_coctel() { return cantidad_coctel; } // Obtener la cantidad de cócteles
    public void setCantidad_coctel(String cantidad_coctel) { this.cantidad_coctel = cantidad_coctel; } // Asignar la cantidad de cócteles

    public String getNota_coctel() { return nota_coctel; } // Obtener la nota del cóctel
    public void setNota_coctel(String nota_coctel) { this.nota_coctel = nota_coctel; } // Asignar la nota del cóctel

    public String getTotal_coctel() { return total_coctel; } // Obtener el total del cóctel
    public void setTotal_coctel(String total_coctel) { this.total_coctel = total_coctel; } // Asignar el total del cóctel

    public String getPrecio_reserva() { return precio_reserva; } // Obtener el precio de la reserva
    public void setPrecio_reserva(String precio_reserva) { this.precio_reserva = precio_reserva; } // Asignar el precio de la reserva

    public String getNombre_cajero() { return nombre_cajero; } // Obtener el nombre del cajero
    public void setNombre_cajero(String nombre_cajero) { this.nombre_cajero = nombre_cajero; } //Asignar el nombre del cajero
}
