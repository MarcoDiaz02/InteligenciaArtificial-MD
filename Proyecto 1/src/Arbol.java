public class Arbol {
    Nodo inicial;
	public Arbol() {
		this.inicial = null;
	}
	
	public void insertar(int valor) {
		if(this.inicial == null) {
			this.inicial = new Nodo(valor);
		}else {
			this.inicial.insertar(valor);
		}
	}
	
	public void ImprimirArbol() {
		this.preorden(this.inicial);
	}
	
	public void preorden(Nodo nodo) {
		if(nodo == null) {
			return;
		}else {
			System.out.print(nodo.getValor() + " ");
			preorden(nodo.getNodoIzq());
			preorden(nodo.getNodoDer());
		}
	}
	
	public Nodo buscarNodo(int valor) {
        return buscarNodoRecursivo(this.inicial, valor);
    }

    private Nodo buscarNodoRecursivo(Nodo nodo, int valor) {
        if (nodo == null || nodo.getValor() == valor) {
            return nodo;
        }

        // Si el valor es menor que el valor del nodo actual, busca en el sub�rbol izquierdo
        if (valor < nodo.getValor()) {
            return buscarNodoRecursivo(nodo.getNodoIzq(), valor);
        }

        // Si el valor es mayor que el valor del nodo actual, busca en el sub�rbol derecho
        return buscarNodoRecursivo(nodo.getNodoDer(), valor);
    }
	
    public boolean vacio() {
    	boolean vacio;
    	if(this.inicial == null) {
    		vacio = true;
    	}else {
    		vacio = false;
    	}
    	return vacio;
    }
}
