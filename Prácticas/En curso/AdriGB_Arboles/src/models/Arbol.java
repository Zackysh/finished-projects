package models;

public class Arbol {

	private Nodo raiz;

	/**
	 * Constructor, establece la raíz del árbol. ç
	 * 
	 * @param raiz
	 */
	public Arbol(Nodo raiz) {
		this.raiz = raiz;
	}

	/**
	 * Método que inserta un nuevo nodo en el nodo pasado como parámetro, si existe en el árbol.
	 * 
	 * @param nodo Nodo a insertar.
	 * @param valor Valor del nuevo nodo
	 * @returns Nodo Si el nodo padre existe en el árbol
	 * @returns null Si el nodo padre no existe en el árbol
	 */
	public Nodo insertarNodo(Nodo nodo, String valor) {
		nodo = this.buscarNodo(nodo.getValor());
		if (nodo != null) {
			Nodo hijo = new Nodo(valor, nodo);
			nodo.insertarHijo(hijo);
			return hijo;
		}
		System.out.println("Ningún nodo tiene como hijo el nodo padre especificado, es decir \nno se encuentra en el árbol.\n");
		return null;
	}

	/**
	 * Método que hará lo siguiente: - Comrprobar si existe un nodo con el valor
	 * dado.
	 * - Si existe, devolverá un String con la ruta.
	 * 
	 * @param valor
	 * @returns path Si existe el nodo, String con el camino realizado
	 * @returns null Si on existe el nodo
	 */
	public String path(String valor) {
		Nodo target = this.buscarNodo(valor);
		if (target != null)
			return target.path();
		return null;
	}

	/**
	 * Método que desencadena en la raíz del árbol una recursión que mostrará los
	 * paths de cada nodo.
	 */
	public void mostrarArbol() {
		raiz.mostrarArbol();
	}

	/**
	 * Método que, dado un valor, devuelve:
	 * 
	 * @param valor
	 * @returns Nodo Sí el valor coincide con algún nodo del árbol
	 * @returns null Si el valor no coincide con ningún nodo del árbol
	 */
	public Nodo buscarNodo(String valor) {
		return this.raiz.buscarNodo(valor);
	}

	/**
	 * Desencadena en la raíz un recorrido preOrden.
	 */
	public void preOrden() {
		this.raiz.preOrden();
	}

	/**
	 * Desencadena en la raíz un recorrido postOrden.
	 */
	public void postOrden() {
		this.raiz.postOrden();
	}

	// Getters & Setters
	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

	public Nodo getRaiz() {
		return raiz;
	}
}
