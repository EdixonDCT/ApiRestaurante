package MODELO;

//Archivo Java dedicado para los getters y setters de los valores de la tabla,sirve como objeto
public class Oficio {
    //valores de la tabla
    private int codigo;
    private String tipo;
    private double salario;

    public int getCodigo() {//obtener el Codigo
        return codigo;
    }

    public void setCodigo(int codigo) {//asignar el Codigo
        this.codigo = codigo;
    }

    public String getTipo(){//obtener el Tipo
        return tipo;
    }

    public void setTipo(String tipo) {//asignar el tipo
        this.tipo = tipo;
    }

    public double getSalario() {//obtener el salario
        return salario;
    }

    public void setSalario(double salario) {//asignar el salario
        this.salario = salario;
    }
}
