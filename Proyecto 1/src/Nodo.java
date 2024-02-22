public class Nodo {
    private int valor;
	private Nodo nodoDer;
	private Nodo nodoIzq;
	
	public Nodo(int valor) {
		this.valor=valor;
		this.nodoDer=null;
		this.nodoIzq=null;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Nodo getNodoDer() {
		return nodoDer;
	}

	public Nodo getNodoIzq() {
		return nodoIzq;
	}
	
	public void insertar(int valorN) {
		if(valorN < this.valor) {
			// Insertar en el lado izquierdo
			if(this.nodoIzq == null) {
				this.nodoIzq = new Nodo(valorN);
			}else {
				this.nodoIzq.insertar(valorN);
			}
		}else {
			// Insertar en el lado derecho
			if(this.nodoDer == null) {
				this.nodoDer = new Nodo(valorN);
			}else {
				this.nodoDer.insertar(valorN);
			}
		}
	}
}
