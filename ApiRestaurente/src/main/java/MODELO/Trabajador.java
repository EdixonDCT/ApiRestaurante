package MODELO; // Define el paquete MODELO donde se encuentra la clase Trabajador

public class Trabajador { // Clase que representa a un trabajador del sistema

    private String cedula; // Cédula del trabajador (se usa String por simplicidad en validaciones)
    private String nombre; // Nombre del trabajador
    private String apellido; // Apellido del trabajador
    private String nacimiento; // Fecha de nacimiento como String para facilitar validaciones con expresiones regulares
    private String foto; // URL o ruta de la foto del trabajador
    private String contrasena; // Contraseña del trabajador (puede contener letras, números y símbolos)
    private String idOficio; // Identificador del oficio que desempeña el trabajador
    private String nombreOficio; // Nombre del oficio (usado en consultas con INNER JOIN)
    private String activo; // Estado de actividad del trabajador (ej. "sí", "no")
    private String adminTemporalInicio; // Fecha de inicio como administrador temporal
    private String adminTemporalFin; // Fecha de fin como administrador temporal

    public String getCedula() { // Obtener la cédula del trabajador
        return cedula;
    }

    public void setCedula(String cedula) { // Asignar la cédula del trabajador
        this.cedula = cedula;
    }

    public String getNombre() { // Obtener el nombre del trabajador
        return nombre;
    }

    public void setNombre(String nombre) { // Asignar el nombre del trabajador
        this.nombre = nombre;
    }

    public String getApellido() { // Obtener el apellido del trabajador
        return apellido;
    }

    public void setApellido(String apellido) { // Asignar el apellido del trabajador
        this.apellido = apellido;
    }

    public String getNacimiento() { // Obtener la fecha de nacimiento
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) { // Asignar la fecha de nacimiento
        this.nacimiento = nacimiento;
    }

    public String getFoto() { // Obtener la foto del trabajador
        return foto;
    }

    public void setFoto(String foto) { // Asignar la foto del trabajador
        this.foto = foto;
    }

    public String getContrasena() { // Obtener la contraseña del trabajador
        return contrasena;
    }

    public void setContrasena(String contrasena) { // Asignar la contraseña del trabajador
        this.contrasena = contrasena;
    }

    public String getIdOficio() { // Obtener el ID del oficio
        return idOficio;
    }

    public void setIdOficio(String idOficio) { // Asignar el ID del oficio
        this.idOficio = idOficio;
    }

    public String getNombreOficio() { // Obtener el nombre del oficio
        return nombreOficio;
    }

    public void setNombreOficio(String nombreOficio) { // Asignar el nombre del oficio
        this.nombreOficio = nombreOficio;
    }

    public String getActivo() { // Obtener el estado de actividad del trabajador
        return activo;
    }

    public void setActivo(String activo) { // Asignar el estado de actividad del trabajador
        this.activo = activo;
    }

    public String getAdminTemporalInicio() { // Obtener la fecha de inicio como administrador temporal
        return adminTemporalInicio;
    }

    public void setAdminTemporalInicio(String adminTemporalInicio) { // Asignar la fecha de inicio como administrador temporal
        this.adminTemporalInicio = adminTemporalInicio;
    }

    public String getAdminTemporalFin() { // Obtener la fecha de fin como administrador temporal
        return adminTemporalFin;
    }

    public void setAdminTemporalFin(String adminTemporalFin) { // Asignar la fecha de fin como administrador temporal
        this.adminTemporalFin = adminTemporalFin;
    }
}
