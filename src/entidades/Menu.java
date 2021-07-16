package entidades;

import estructurasdatos.List;

/**
 *
 * @author Miguel Brito
 */
public class Menu {

    private final List<Platillo> listaPlatillos;

    public Menu() {
        this.listaPlatillos = new List<>();
    }

    public List<Platillo> getListaPlatillos() {
        return this.listaPlatillos;
    }

    public List<Platillo> buscarPlatillo(String nombre) {
        return null;
    }

    public void agregar(Platillo platillo) {
        listaPlatillos.addEnd(platillo);
    }

    public void quitar(Platillo platillo) throws Exception {
        int aux = listaPlatillos.indexOf(platillo);
        listaPlatillos.remove(aux);
    }
    
    public void quitar(String nombre) throws Exception {
        List<Platillo> platillos=getListaPlatillos();
        int cont = 0;
        for (Platillo platillo : platillos) {
            if (platillo.getNombre().trim().compareTo(nombre) == 0) {
                platillos.remove(cont);
            }
            cont++;
        }
        
    }
}
