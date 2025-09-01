package MODELO; // Define el paquete MODELO donde se encuentra la clase Cliente

public class Cliente { // Define la clase Cliente

    private String correo; // Correo electrónico del cliente
    private String cedula; // Cédula de identidad del cliente
    private String telefono; // Número de teléfono del cliente

    public String getCorreo() { // Método para obtener el correo del cliente
        return correo;
    }

    public void setCorreo(String correo) { // Método para asignar el correo del cliente
        this.correo = correo;
    }

    public String getCedula() { // Método para obtener la cédula del cliente
        return cedula;
    }

    public void setCedula(String cedula) { // Método para asignar la cédula del cliente
        this.cedula = cedula;
    }

    public String getTelefono() { // Método para obtener el teléfono del cliente
        return telefono;
    }

    public void setTelefono(String telefono) { // Método para asignar el teléfono del cliente
        this.telefono = telefono;
    }
}
