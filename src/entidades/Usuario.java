package entidades;

import utilities.FilterTools;

public class Usuario {
    private String nombre;
    private String apellido;
    private String correo;
    private String clave;
    
    public Usuario(String nombre, String apellido, String correo, String clave) {
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        this.nombre = FilterTools.capitalize(nombre);
    }

    public String getApellido() {
        return apellido;
    }

    public final void setApellido(String apellido) {
        this.apellido = FilterTools.capitalize(apellido);
    }

    public String getCorreo() {
        return correo;
    }

    public final void setCorreo(String correo) {
        this.correo = FilterTools.normalizeEmail(correo);
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}