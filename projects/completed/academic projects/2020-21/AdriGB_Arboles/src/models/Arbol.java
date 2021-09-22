package models;

public class Arbol {

	private Nodo raiz;

	/**
	 * Constructor, establece la ra�z del �rbol. �
	 * 
	 * @param raiz
	 */
	public Arbol(Nodo raiz) {
		this.raiz = raiz;
	}

	/**
	 * M�todo que inserta un nuevo nodo en el nodo pasado como par�metro, si existe en el �rbol.
	 * 
	 * @param nodo Nodo a insertar.
	 * @param valor Valor del nuevo nodo
	 * @returns Nodo Si el nodo padre existe en el �rbol
	 * @returns null Si el nodo padre no existe en el �rbol
	 */
	public Nodo insertarNodo(Nodo nodo, String valor) {
		nodo = this.buscarNodo(nodo.getValor());
		if (nodo != null) {
			Nodo hijo = new Nodo(valor, nodo);
			nodo.insertarHijo(hijo);
			return hijo;
		}
		System.out.println("Ning�n nodo tiene como hijo el nodo padre especificado, es decir \nno se encuentra en el �rbol.\n");
		return null;
	}

	/**
	 * M�todo que har� lo siguiente: - Comrprobar si existe un nodo con el valor
	 * dado.
	 * - Si existe, devolver� un String con la ruta.
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
	 * M�todo que desencadena en la ra�z del �rbol una recursi�n que mostrar� los
	 * paths de cada nodo.
	 */
	public void mostrarArbol() {
		raiz.mostrarArbol();
	}

	/**
	 * M�todo que, dado un valor, devuelve:
	 * 
	 * @param valor
	 * @returns Nodo S� el valor coincide con alg�n nodo del �rbol
	 * @returns null Si el valor no coincide con ning�n nodo del �rbol
	 */
	public Nodo buscarNodo(String valor) {
		return this.raiz.buscarNodo(valor);
	}

	/**
	 * Desencadena en la ra�z un recorrido preOrden.
	 */
	public void preOrden() {
		this.raiz.preOrden();
	}

	/**
	 * Desencadena en la ra�z un recorrido postOrden.
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
