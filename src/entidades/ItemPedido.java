package entidades;

/**
 *
 * @author Jorge Padilla
 */
public class ItemPedido {
    private Platillo platillo;
    private int cantidad;
    private final int LIMITE = 10;

    public ItemPedido(Platillo platillo, int cantidad) {
        this.platillo = platillo;
        this.cantidad = cantidad;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public void agregarUnidad() {
        if (cantidad < LIMITE) {
            cantidad++;
        }
    }
    
    public void quitarUnidad() {
        if (cantidad > 0) {
            cantidad--;
        }
    }

}
