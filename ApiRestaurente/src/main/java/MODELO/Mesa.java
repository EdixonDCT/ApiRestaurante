package MODELO; // Define el paquete MODELO donde se encuentra la clase Mesa

public class Mesa { // Clase que representa una mesa en el restaurante

    private String numero; // Número identificador de la mesa
    private String capacidad; // Capacidad de la mesa (cantidad de personas que puede acomodar)
    private String disponible; // Estado de disponibilidad de la mesa (ej. "sí", "no")

    public String getNumero() { // Método para obtener el número de la mesa
        return numero;
    }

    public void setNumero(String numero) { // Método para establecer el número de la mesa
        this.numero = numero;
    }

    public String getCapacidad() { // Método para obtener la capacidad de la mesa
        return capacidad;
    }

    public void setCapacidad(String capacidad) { // Método para establecer la capacidad de la mesa
        this.capacidad = capacidad;
    }

    public String getDisponible() { // Método para obtener el estado de disponibilidad
        return disponible;
    }

    public void setDisponible(String disponible) { // Método para establecer el estado de disponibilidad
        this.disponible = disponible;
    }
}
