package MODELO;

import java.time.LocalDate;

public class Trabajador {

    private String cedula;//es int porque es un numero entero
    private String nombre;
    private String apellido;
    private String nacimiento;//Es String para que sea mas sencillo subirlo ala base de datos y hacer validaciones con regex
    //es nacimiento y no Fecha de Nacimiento para ser mas simple
    private String foto;//la foto es string porque almacena su URL
    private String contrasena;//es string porque puede tener texto,numero y signos
    //es contraseña con n por el manejo de Ñ
    private String idOficio;//es int porque son numeros enteros
    private String nombreOficio;//es para el Inner Join cuando se consulta el nombre con el id de Oficio
    private String activo;
    private String adminTemporalInicio;
    private String adminTemporalFin;

    public String getCedula() {//obtener la Cedula
        return cedula;
    }

    public void setCedula(String cedula) {//asginar la Cedula
        this.cedula = cedula;
    }

    public String getNombre() {//obtener el Nombre
        return nombre;
    }

    public void setNombre(String nombre) {//asignar el Nombre
        this.nombre = nombre;
    }

    public String getApellido() {//obtener el Apellido
        return apellido;
    }

    public void setApellido(String apellido) {//asignar el Apellido
        this.apellido = apellido;
    }

    public String getNacimiento() {//obtener la Fecha de nacimiento
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {//asignar la Fecha de nacimiento
        this.nacimiento = nacimiento;
    }

    public String getFoto() {//obtener la Foto
        return foto;
    }

    public void setFoto(String foto) {//asginar la Foto
        this.foto = foto;
    }

    public String getContrasena() {//obtener la Contraseña
        return contrasena;
    }

    public void setContrasena(String contrasena) {//asignar la Contraseña
        this.contrasena = contrasena;
    }

    public String getIdOficio() {//obtener el ID del Codigo Oficio
        return idOficio;
    }

    public void setIdOficio(String idOficio) {//asginar el ID del Codigo Oficio
        this.idOficio = idOficio;
    }

    public String getNombreOficio() {
        return nombreOficio;
    }

    public void setNombreOficio(String nombreOficio) {
        this.nombreOficio = nombreOficio;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getAdminTemporalInicio() {
        return adminTemporalInicio;
    }

    public void setAdminTemporalInicio(String adminTemporalInicio) {
        this.adminTemporalInicio = adminTemporalInicio;
    }

    public String getAdminTemporalFin() {
        return adminTemporalFin;
    }

    public void setAdminTemporalFin(String adminTemporalFin) {
        this.adminTemporalFin = adminTemporalFin;
    }
}
