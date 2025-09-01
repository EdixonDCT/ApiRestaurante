package MODELO; // Define el paquete MODELO donde se encuentra la clase IngredientesComida

public class IngredientesComida { // Clase que representa la relación entre ingredientes y comidas

    private String id; // Identificador único de la relación ingrediente-comida
    private String idIngrediente; // Identificador del ingrediente
    private String nombreIngrediente; // Nombre del ingrediente
    private String idComida; // Identificador de la comida
    private String nombreComida; // Nombre de la comida

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

    public String getIdComida() { // Método para obtener el id de la comida
        return idComida;
    }

    public void setIdComida(String idComida) { // Método para establecer el id de la comida
        this.idComida = idComida;
    }

    public String getNombreComida() { // Método para obtener el nombre de la comida
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) { // Método para establecer el nombre de la comida
        this.nombreComida = nombreComida;
    }
}
