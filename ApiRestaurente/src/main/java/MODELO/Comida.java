package MODELO; // Define el paquete MODELO donde se encuentra la clase Comida

public class Comida { // Define la clase Comida que representa un platillo o alimento

    private String id; // Identificador único de la comida
    private String nombre; // Nombre del platillo
    private String precio; // Precio del platillo
    private String tipo; // Tipo de comida (ej. entrada, plato fuerte, postre)
    private String imagen; // Ruta o nombre del archivo de imagen del platillo
    private String disponible; // Estado de disponibilidad (ej. "sí", "no")

    public String getId() { // Método para obtener el id de la comida
        return id;
    }

    public void setId(String id) { // Método para establecer el id de la comida
        this.id = id;
    }

    public String getNombre() { // Método para obtener el nombre del platillo
        return nombre;
    }

    public void setNombre(String nombre) { // Método para establecer el nombre del platillo
        this.nombre = nombre;
    }

    public String getPrecio() { // Método para obtener el precio del platillo
        return precio;
    }

    public void setPrecio(String precio) { // Método para establecer el precio del platillo
        this.precio = precio;
    }

    public String getTipo() { // Método para obtener el tipo de comida
        return tipo;
    }

    public void setTipo(String tipo) { // Método para establecer el tipo de comida
        this.tipo = tipo;
    }

    public String getImagen() { // Método para obtener la imagen del platillo
        return imagen;
    }

    public void setImagen(String imagen) { // Método para establecer la imagen del platillo
        this.imagen = imagen;
    }

    public String getDisponible() { // Método para obtener la disponibilidad del platillo
        return disponible;
    }

    public void setDisponible(String disponible) { // Método para establecer la disponibilidad del platillo
        this.disponible = disponible;
    }
}
