package models;

import java.util.ArrayList;

public class Nodo {

	private String valor;
	private Nodo padre;
	private ArrayList<Nodo> hijos;

	/**
	 * Constructor simple, inicializa hijos y establece los valores valor y padre
	 * 
	 * @param valor
	 * @param padre
	 */
	public Nodo(String valor, Nodo padre) {
		hijos = new ArrayList<>();
		this.valor = valor;
		this.padre = padre;
	}

	/**
	 * Inserta un nuevo hijo a un nodo.
	 * 
	 * @param hijo Nodo a insertar
	 */
	public void insertarHijo(Nodo hijo) {
		hijos.add(hijo);
	}

	/**
	 * Método que comprueba si un nodo es una hoja.
	 * 
	 * @returns true Si es hoja
	 * @returns false Si no es hoja
	 */
	public boolean esHoja() {
		return hijos.isEmpty();
	}

	/**
	 * Método que busca un nodo en el árbol cuyo valor coincida con el dado en los
	 * parámetros.
	 * 
	 * @param valor deseado
	 * @return nodo con el valor deseado
	 */
	public Nodo buscarNodo(String valor) {

		if (this.valor.equalsIgnoreCase(valor))
			return this;
		if (!this.esHoja()) {

			for (Nodo nodo : hijos) {
				Nodo result = nodo.buscarNodo(valor);
				if (result == null) {
					continue;
				} else
					return result;
			}

			return null;
		} else
			return null;
	}

	/**
	 * Método que devolverá el camino más corto hasta el nodo.
	 * 
	 * @return path del nodo
	 */
	public String path() {
		if (padre != null)
			return padre.path() + "/" + this.valor;
		return this.valor;
	}

	/**
	 * Método que muestra, para cada nodo del árbol, su path.
	 */
	public void mostrarArbol() {
		System.out.println(this.path());
		if (!this.esHoja())
			for (Nodo nodo : hijos)
				nodo.mostrarArbol();
		else
			return;
	}

	/**
	 * Método de recorrido postOrden, imprime los valores de los nodos en el momento
	 * adecuado.
	 */
	public void postOrden() {
		if (!this.esHoja())
			for (Nodo nodo : hijos)
				nodo.postOrden();
		System.out.println(this.valor);

	}

	/**
	 * Método de recorrido preOrden, imprime los valores de los nodos en el momento
	 * adecuado.
	 */
	public void preOrden() {
		System.out.println(this.valor);
		if (!this.esHoja())
			for (Nodo nodo : hijos)
				nodo.preOrden();
		else
			return;
	}

	// Getters & Setters
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public String getHijos() {
		String str = this.valor + "/";
		for (Nodo nodo : hijos) {
			str += nodo.getValor();
			if (nodo != hijos.get(hijos.size() - 1))
				str += "/";
		}
		return str;
	}

	public void setHijos(ArrayList<Nodo> hijos) {
		this.hijos = hijos;
	}

}
