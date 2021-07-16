package infraestructura;

import entidades.ItemPedido;
import entidades.Cliente;
import entidades.Cocinero;
import entidades.Pedido;
import entidades.Platillo;
import estructurasdatos.List;
import utilities.Hasher;

/**
 *
 * @author Alain
 */
public class Constantes {

    private Constantes() {
    }

    public static List<Platillo> crearPlatillos() {
        List<Platillo> platillos = new List<>();
        platillos.addEnd(new Platillo("Cazuela", Platillo.Tipo.FUERTE, "Cazuela gigante acompañado con una limonada.", 6.75, true, false, 7, "cazuela.jpg"));
        platillos.addEnd(new Platillo("Ceviche Camaron", Platillo.Tipo.FUERTE, "Ceviche con camarones acompañado con canguil y una limonada.", 7.75, true, false, 10, "ceviche_camaron.jpg"));
        platillos.addEnd(new Platillo("Ceviche triple", Platillo.Tipo.FUERTE, "Un ceviche triple de camaron, concha y pescado acompañado con canguil y una limonada.", 10.75, true, false, 10, "ceviche_triple.jpg"));
        platillos.addEnd(new Platillo("Churrasco camaron", Platillo.Tipo.FUERTE, "Camarones jugosos con arroz, ensalada, huevos fritos aguacate y patacones.", 8.0, true, false, 10, "churrasco_camaron.jpg"));
        platillos.addEnd(new Platillo("Encebollado Combo", Platillo.Tipo.FUERTE, "Encebollado gigante, acompañado de porción de arroz, canguil, chifles y limonada.", 5.75, true, false, 15, "combo_encebollado.jpg"));
        platillos.addEnd(new Platillo("Desayuno completo", Platillo.Tipo.POSTRE, "Café pasado/agua aromática/leche + jugo + sanduche + huevos a su gusto + seco de pollo o bistec de carne.", 4.75, true, false, 5, "desayuno.jpg"));
        platillos.addEnd(new Platillo("Encebollado gigante", Platillo.Tipo.FUERTE, "Encebollado acompañado de canguil y chifles.", 4.75, true, false, 10, "encebollado_gigante.jpg"));
        platillos.addEnd(new Platillo("Encebollado Mixto", Platillo.Tipo.FUERTE, "Encebollado con camarones y albacora acompañado de canguil y chifles.", 7.0, true, false, 15, "encebollado_mixto.jpg"));
        platillos.addEnd(new Platillo("Menestra", Platillo.Tipo.FUERTE, "Una menestra de frejol o lenteja acompañado con carne, arroz y ensalada.", 4.75, true, false, 10, "menestra.jpg"));
        platillos.addEnd(new Platillo("Pargo frito", Platillo.Tipo.FUERTE, "Pargo frito con ensalada y arroz.", 7.50, true, false, 10, "pargo_frito.jpg"));
        platillos.addEnd(new Platillo("Pargo con salsa marinera", Platillo.Tipo.FUERTE, "Un pargo con salsa marinera arroz y ensalada.", 10, true, false, 10, "pargo.jpg"));
        platillos.addEnd(new Platillo("Tigrillo", Platillo.Tipo.FUERTE, "Café pasado + jugo + majado de verde mixto (queso y chicharron) + seco de pollo o bistec de carne + huevo frito.", 4.5, true, false, 7, "tigrillo.jpg"));
        platillos.addEnd(new Platillo("Cafe", Platillo.Tipo.FUERTE, "Café de agua o leche con azucar o sin azucar.", 4.5, true, true, 7, "cafe.png"));
        platillos.addEnd(new Platillo("Jugo de naranja", Platillo.Tipo.FUERTE, "Jugo de naranja natural con o sin azucar.", 4.5, true, true, 7, "jugo.png"));
        return platillos;
    }

    public static List<Cocinero> crearCocineros() {
        List<Cocinero> cocineros = new List<>();
        try {
            cocineros.addEnd(new Cocinero("Juan", "Pérez", "juan.perez@hotmail.com", Hasher.convert("holamundo")));
            cocineros.addEnd(new Cocinero("Yadira", "Pérez", "yadira.perez@gmail.com", Hasher.convert("holamundo")));
            cocineros.addEnd(new Cocinero("Diana", "Pérez", "diana.perez@gmail.com", Hasher.convert("holamundo")));
            cocineros.addEnd(new Cocinero("Luis", "Pérez", "luis.perez@gmail.com", Hasher.convert("holamundo")));
            cocineros.addEnd(new Cocinero("Miriam", "Zambrano", "Miriam.Zambrano@gmail.com", Hasher.convert("holamundo")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cocineros;
    }

    public static List<Cliente> crearClientes() {
        List<Cliente> clientes = new List<>();
        try {
            clientes.addEnd(new Cliente("Ernesto", "González", "ernesto.gp87@yahoo.com", Hasher.convert("holamundo"), "2263083855", "San Carlos"));
            clientes.addEnd(new Cliente("Miguel", "Brito", "miguelcliente@gmail.com", Hasher.convert("holamundo"), "2326560915", "Pomasqui"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public static List<Pedido> crearPedidos() {
        List<Pedido> pedidos = new List<>();
        List<Platillo> menu = crearPlatillos();
        List<Cliente> clientes = crearClientes();
        List<ItemPedido> platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 1));
            platos.addEnd(new ItemPedido(menu.get(11), 1));
            platos.addEnd(new ItemPedido(menu.get(12), 2));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 2));
            platos.addEnd(new ItemPedido(menu.get(8), 4));
            platos.addEnd(new ItemPedido(menu.get(10), 5));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 3));
            platos.addEnd(new ItemPedido(menu.get(6), 9));
            platos.addEnd(new ItemPedido(menu.get(7), 7));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 3));
            platos.addEnd(new ItemPedido(menu.get(4), 5));
            platos.addEnd(new ItemPedido(menu.get(2), 1));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(2), 1));
            platos.addEnd(new ItemPedido(menu.get(3), 1));
            platos.addEnd(new ItemPedido(menu.get(4), 1));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 2));
            platos.addEnd(new ItemPedido(menu.get(8), 4));
            platos.addEnd(new ItemPedido(menu.get(10), 5));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 3));
            platos.addEnd(new ItemPedido(menu.get(6), 9));
            platos.addEnd(new ItemPedido(menu.get(7), 7));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(0), 3));
            platos.addEnd(new ItemPedido(menu.get(4), 5));
            platos.addEnd(new ItemPedido(menu.get(2), 1));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        platos = new List<>();
        try {
            platos.addEnd(new ItemPedido(menu.get(2), 1));
            platos.addEnd(new ItemPedido(menu.get(3), 1));
            platos.addEnd(new ItemPedido(menu.get(4), 1));
            pedidos.addEnd(new Pedido(clientes.get(1), platos, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }
}
