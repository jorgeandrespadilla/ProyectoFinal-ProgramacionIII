# Proyecto Final Programación III
## CORe: Cliente - Orden - Restaurante

**Integrantes:** 
- Miguel Brito
- José I. Escudero 
- Jorge Padilla
- Alain Ruales
- Christian Samaniego

<img src="src/assets/icons/icon.png" alt="drawing" width="200"/>

⚠️ Este proyecto fue desarrollado en el IDE NetBeans 8.2.


### Estructuras de datos utilizadas
1. Listas:
    - Lista de Platillos: Almacenar los platillos existentes en el restaurante.  Lista de Clientes: En esta lista se encontrará los clientes registrados del restaurante.
    - Lista de Cocineros: En esta lista se encontrará los cocineros del restaurante.
    - Lista de pedidos (historial de pedidos): Tendrá todos los pedidos que se han hecho en la empresa.
    - Lista de pedidos del cocinero (cola de pedidos): Almacenar las órdenes asignadas al cocinero en orden de llegada.
### Algoritmos utilizados

1. Algoritmo de búsqueda: 
    - Menú del restaurante: Utilizado para buscar un platillo del menú por su nombre.
    - Historial de consumo del cliente: Utilizado para buscar todas las órdenes realizadas por un cliente, por su número de cédula.
2. Algoritmo de ordenamiento: 
    - Menú del restaurante: Utilizado para ordenar los platos alfabéticamente.
3. Algoritmos especializados:
    - Algoritmo para gestionar la navegación entre interfaces (*StackManager*).
    - Algoritmo de asignación de órdenes a cocineros: Determina a que cocinero se le asigna una determinada orden en base al tiempo de procesamiento de la misma. Este algoritmo buscará delegar las órdenes a los cocineros en función del tiempo total de procesamiento de las órdenes: los cocineros con menor tiempo de procesamiento serán los encargados de procesar los nuevos pedidos.
