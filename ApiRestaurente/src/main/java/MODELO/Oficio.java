package MODELO;

//Archivo Java dedicado para los getters y setters de los valores de la tabla,sirve como objeto
public class Oficio {
    //valores de la tabla
    private String codigo;
    private String tipo;
    private String salario;

    public String getCodigo() {//obtener el Codigo
        return codigo;
    }

    public void setCodigo(String codigo) {//asignar el Codigo
        this.codigo = codigo;
    }

    public String getTipo(){//obtener el Tipo
        return tipo;
    }

    public void setTipo(String tipo) {//asignar el tipo
        this.tipo = tipo;
    }

    public String getSalario() {//obtener el salario
        return salario;
    }

    public void setSalario(String salario) {//asignar el salario
        this.salario = salario;
    }
}
