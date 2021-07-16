package entidades;

/**
 *
 * @author Christian
 */
public class Cliente extends Usuario{

    private String cedula;
    private String direccion;

    public Cliente(String nombre, String apellido, String correo, String clave, String cedula, String direccion) {
        super(nombre, apellido, correo, clave);
        this.cedula = cedula;
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}