package MODELO;

import java.time.LocalDate;

public class Usuarios {

    private String id;
    private String cedula;//es int porque es un numero entero
    private String nombre;
    private String apellido;
    private String nacimiento;//Es String para que sea mas sencillo subirlo ala base de datos y hacer validaciones con regex
    private String foto;//la foto es string porque almacena su URL
    private String contrasena;//es string porque puede tener texto,numero y signos
    private String activo;
    private String eliminado;
    private String nombreRol;

    public String getId() {//obtener la Cedula
        return id;
    }

    public void setId(String id) {//asginar la Cedula
        this.id = id;
    }

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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
