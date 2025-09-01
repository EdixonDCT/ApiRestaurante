package MODELO; // Define el paquete MODELO donde se encuentra la clase Coctel

public class Coctel { // Define la clase Coctel que representa una bebida tipo cóctel

    private String id; // Identificador único del cóctel
    private String nombre; // Nombre del cóctel
    private String precio; // Precio del cóctel
    private String imagen; // Ruta o nombre del archivo de imagen del cóctel
    private String disponible; // Estado de disponibilidad del cóctel (ej. "sí", "no")

    public String getId() { // Método para obtener el id del cóctel
        return id;
    }

    public void setId(String id) { // Método para establecer el id del cóctel
        this.id = id;
    }

    public String getNombre() { // Método para obtener el nombre del cóctel
        return nombre;
    }

    public void setNombre(String nombre) { // Método para establecer el nombre del cóctel
        this.nombre = nombre;
    }

    public String getPrecio() { // Método para obtener el precio del cóctel
        return precio;
    }

    public void setPrecio(String precio) { // Método para establecer el precio del cóctel
        this.precio = precio;
    }

    public String getImagen() { // Método para obtener la imagen del cóctel
        return imagen;
    }

    public void setImagen(String imagen) { // Método para establecer la imagen del cóctel
        this.imagen = imagen;
    }

    public String getDisponible() { // Método para obtener la disponibilidad del cóctel
        return disponible;
    }

    public void setDisponible(String disponible) { // Método para establecer la disponibilidad del cóctel
        this.disponible = disponible;
    }
}
