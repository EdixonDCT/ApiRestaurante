package MODELO; // Define el paquete MODELO donde se encuentra la clase Oficio

// Archivo Java dedicado para los getters y setters de los valores de la tabla, sirve como objeto
public class Oficio { // Clase que representa un oficio o puesto de trabajo

    // Valores de la tabla
    private String codigo; // Código identificador del oficio
    private String tipo; // Tipo de oficio (ej. administrativo, técnico, operativo)
    private String salario; // Salario asociado al oficio

    public String getCodigo() { // Método para obtener el código del oficio
        return codigo;
    }

    public void setCodigo(String codigo) { // Método para asignar el código del oficio
        this.codigo = codigo;
    }

    public String getTipo() { // Método para obtener el tipo de oficio
        return tipo;
    }

    public void setTipo(String tipo) { // Método para asignar el tipo de oficio
        this.tipo = tipo;
    }

    public String getSalario() { // Método para obtener el salario del oficio
        return salario;
    }

    public void setSalario(String salario) { // Método para asignar el salario del oficio
        this.salario = salario;
    }
}
