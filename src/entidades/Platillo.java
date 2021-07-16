package entidades;

public class Platillo {

    public enum Tipo {
        ENTRADA,
        BEBIDA,
        POSTRE,
        FUERTE,
        SOPA,
        ENSALADA
    }

    private String nombre;
    private Tipo tipo;
    private String descripcion;
    private double precio;
    private boolean disponible;
    private boolean prioridad;
    private int tiempoPreparacion;
    private String urlImagen;

    public Platillo() {
    }
    
    public Platillo(String nombre, Tipo tipo, String descripcion, double precio, boolean disponible, boolean prioridad, int tiempoPreparacion, String url) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
        this.prioridad = prioridad;
        this.tiempoPreparacion = tiempoPreparacion;
        this.urlImagen = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = Tipo.valueOf(tipo);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public boolean hasPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
    
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String formatPrecio() {
        return String.format("$%.2f", this.precio);
    }

    public String formatDisponibilidad() {
        if (disponible) {
            return "Disponible";
        } else {
            return "No Disponible";
        }
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nTipo: " + tipo + "\nPrecio: " + formatPrecio() + "\n";
    }

}
