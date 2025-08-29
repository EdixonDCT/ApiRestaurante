package MODELO;

//Archivo Java dedicado para los getters y setters de los valores de la tabla,sirve como objeto
public class Roles {
    //valores de la tabla
    private String id;
    private String nombre;
    private String salario;

    public String getId() {//obtener el Codigo
        return id;
    }

    public void setId(String id) {//asignar el Codigo
        this.id = id;
    }

    public String getNombre(){//obtener el Tipo
        return nombre;
    }

    public void setNombre(String nombre) {//asignar el tipo
        this.nombre = nombre;
    }

    public String getSalario() {//obtener el salario
        return salario;
    }

    public void setSalario(String salario) {//asignar el salario
        this.salario = salario;
    }
}
