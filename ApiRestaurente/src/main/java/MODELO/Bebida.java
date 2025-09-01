package MODELO; // Define el paquete MODELO donde se encuentra esta clase

public class Bebida { // Define la clase Bebida

    private String id; // Identificador único de la bebida
    private String nombre; // Nombre de la bebida
    private String precio; // Precio de la bebida
    private String unidad; // Unidad de medida (ej. ml, L)
    private String tipo; // Tipo de bebida (ej. gaseosa, jugo)
    private String imagen; // Ruta o nombre del archivo de imagen de la bebida
    private String disponible; // Estado de disponibilidad (ej. "sí", "no")

    public String getId() { // Método para obtener el id
        return id;
    }

    public void setId(String id) { // Método para establecer el id
        this.id = id;
    }

    public String getNombre() { // Método para obtener el nombre
        return nombre;
    }

    public void setNombre(String nombre) { // Método para establecer el nombre
        this.nombre = nombre;
    }

    public String getPrecio() { // Método para obtener el precio
        return precio;
    }

    public void setPrecio(String precio) { // Método para establecer el precio
        this.precio = precio;
    }

    public String getUnidad() { // Método para obtener la unidad de medida
        return unidad;
    }

    public void setUnidad(String unidad) { // Método para establecer la unidad de medida
        this.unidad = unidad;
    }

    public String getTipo() { // Método para obtener el tipo de bebida
        return tipo;
    }

    public void setTipo(String tipo) { // Método para establecer el tipo de bebida
        this.tipo = tipo;
    }

    public String getImagen() { // Método para obtener la imagen
        return imagen;
    }

    public void setImagen(String imagen) { // Método para establecer la imagen
        this.imagen = imagen;
    }

    public String getDisponible() { // Método para obtener la disponibilidad
        return disponible;
    }

    public void setDisponible(String disponible) { // Método para establecer la disponibilidad
        this.disponible = disponible;
    }
}
