package MODELO; // Define el paquete MODELO donde se encuentra la clase Ingrediente

public class Ingrediente { // Clase que representa un ingrediente utilizado en recetas o productos

    private String id; // Identificador único del ingrediente
    private String nombre; // Nombre del ingrediente

    public String getId() { // Método para obtener el id del ingrediente
        return id;
    }

    public void setId(String id) { // Método para establecer el id del ingrediente
        this.id = id;
    }

    public String getNombre() { // Método para obtener el nombre del ingrediente
        return nombre;
    }

    public void setNombre(String nombre) { // Método para establecer el nombre del ingrediente
        this.nombre = nombre;
    }
}
