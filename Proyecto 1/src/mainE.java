public class mainE {
    public static void main(String[] args) {
		Arbol arbol = new Arbol();
		arbol.insertar(43);
		arbol.insertar(10);
		arbol.insertar(8);
		arbol.insertar(54);
		arbol.insertar(15);
		arbol.insertar(50);
		arbol.insertar(53);
		
		System.out.println("Nodos del arbol: ");
		arbol.ImprimirArbol();
		
		int valorABuscar = 16; // Como no se encuentra en el arbol, debe dar de resultado que no se encuentra en el arbol
        Nodo nodoEncontrado = arbol.buscarNodo(valorABuscar);
        if (nodoEncontrado != null) {
            System.out.println("\nEl nodo con valor " + valorABuscar + " fue encontrado en el arbol.");
        } else {
            System.out.println("\nEl nodo con valor " + valorABuscar + " no fue encontrado en el arbol.");
        }
        
        boolean vacio = arbol.vacio();
        if(vacio) {
        	System.out.println("El arbol esta vacio.");
        }else {
        	System.out.println("El arbol no esta vacio.");
        }
       
	}
}
