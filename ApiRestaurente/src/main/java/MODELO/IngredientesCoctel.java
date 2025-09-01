package MODELO; // Define el paquete MODELO donde se encuentra la clase IngredientesCoctel

public class IngredientesCoctel { // Clase que representa la relación entre ingredientes y cócteles

    private String id; // Identificador único de la relación ingrediente-cóctel
    private String idIngrediente; // Identificador del ingrediente
    private String nombreIngrediente; // Nombre del ingrediente
    private String idCoctel; // Identificador del cóctel
    private String nombreCoctel; // Nombre del cóctel

    public String getId() { // Método para obtener el id de la relación
        return id;
    }

    public void setId(String id) { // Método para establecer el id de la relación
        this.id = id;
    }

    public String getIdIngrediente() { // Método para obtener el id del ingrediente
        return idIngrediente;
    }

    public void setIdIngrediente(String idIngrediente) { // Método para establecer el id del ingrediente
        this.idIngrediente = idIngrediente;
    }

    public String getNombreIngrediente() { // Método para obtener el nombre del ingrediente
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) { // Método para establecer el nombre del ingrediente
        this.nombreIngrediente = nombreIngrediente;
    }

    public String getIdCoctel() { // Método para obtener el id del cóctel
        return idCoctel;
    }

    public void setIdCoctel(String idCoctel) { // Método para establecer el id del cóctel
        this.idCoctel = idCoctel;
    }

    public String getNombreCoctel() { // Método para obtener el nombre del cóctel
        return nombreCoctel;
    }

    public void setNombreCoctel(String nombreCoctel) { // Método para establecer el nombre del cóctel
        this.nombreCoctel = nombreCoctel;
    }
}
