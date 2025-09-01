package MODELO; // Define el paquete MODELO donde se encuentra la clase Pedido

public class Pedido { // Clase que representa un pedido realizado en el restaurante

    private String id; // Identificador único del pedido
    private String numeroMesa; // Número de la mesa asociada al pedido
    private String fecha; // Fecha en que se realizó el pedido
    private String hora; // Hora en que se realizó el pedido
    private String valorTotal; // Valor total del pedido
    private String idCaja; // Identificador de la caja donde se registró el pedido
    private String numeroClientes; // Número de clientes atendidos en el pedido
    private String idReserva; // Identificador de la reserva asociada (si aplica)
    private String correoCliente; // Correo electrónico del cliente (si aplica)
    private String metodoPago; // Método de pago utilizado (ej. efectivo, tarjeta)
    private String facturado; // Estado de facturación del pedido (ej. "sí", "no")
    private String eliminado; // Estado de eliminación lógica del pedido (ej. "sí", "no")

    public String getId() { // Obtener el id del pedido
        return id;
    }

    public void setId(String id) { // Asignar el id del pedido
        this.id = id;
    }

    public String getNumeroMesa() { // Obtener el número de mesa
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) { // Asignar el número de mesa
        this.numeroMesa = numeroMesa;
    }

    public String getFecha() { // Obtener la fecha del pedido
        return fecha;
    }

    public void setFecha(String fecha) { // Asignar la fecha del pedido
        this.fecha = fecha;
    }

    public String getHora() { // Obtener la hora del pedido
        return hora;
    }

    public void setHora(String hora) { // Asignar la hora del pedido
        this.hora = hora;
    }

    public String getValorTotal() { // Obtener el valor total del pedido
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) { // Asignar el valor total del pedido
        this.valorTotal = valorTotal;
    }

    public String getIdCaja() { // Obtener el id de la caja
        return idCaja;
    }

    public void setIdCaja(String idCaja) { // Asignar el id de la caja
        this.idCaja = idCaja;
    }

    public String getNumeroClientes() { // Obtener el número de clientes
        return numeroClientes;
    }

    public void setNumeroClientes(String numeroClientes) { // Asignar el número de clientes
        this.numeroClientes = numeroClientes;
    }

    public String getIdReserva() { // Obtener el id de la reserva
        return idReserva;
    }

    public void setIdReserva(String idReserva) { // Asignar el id de la reserva
        this.idReserva = idReserva;
    }

    public String getCorreoCliente() { // Obtener el correo del cliente
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) { // Asignar el correo del cliente
        this.correoCliente = correoCliente;
    }

    public String getMetodoPago() { // Obtener el método de pago
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) { // Asignar el método de pago
        this.metodoPago = metodoPago;
    }

    public String getFacturado() { // Obtener el estado de facturación
        return facturado;
    }

    public void setFacturado(String facturado) { // Asignar el estado de facturación
        this.facturado = facturado;
    }

    public String getEliminado() { // Obtener el estado de eliminación
        return eliminado;
    }

    public void setEliminado(String eliminado) { // Asignar el estado de eliminación
        this.eliminado = eliminado;
    }
}
