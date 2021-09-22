package mainapp;

import models.Arbol;
import models.Nodo;

public class Mainapp {

	public static void main(String[] args) {
		
		// Creamos la raiz y el árbol
		Nodo raiz = new Nodo("raiz", null);
		Arbol arbolito = new Arbol(raiz);
		
		// Creamos nodos y sus papas
		Nodo pollo = new Nodo("pollo", raiz);
		Nodo cerdo = new Nodo("cerdo", raiz);		
		Nodo ternera = new Nodo("ternera", raiz);		
		
		Nodo pollo1 = new Nodo("uno", pollo);
		Nodo pollo2 = new Nodo("dos", pollo);
		
		Nodo pollo25 = new Nodo("cinco", pollo2);
		Nodo pollo256 = new Nodo("seis", pollo25);
		
		Nodo cerdo3 = new Nodo("tres", cerdo);
		Nodo ternera4 = new Nodo("cuatro", ternera);
		
		//Establecemos los hijos de cada nodo
		raiz.insertarHijo(pollo);
		raiz.insertarHijo(ternera);
		raiz.insertarHijo(cerdo);
		pollo.insertarHijo(pollo1);
		pollo.insertarHijo(pollo2);
		pollo2.insertarHijo(pollo25);
		pollo25.insertarHijo(pollo256);
		cerdo.insertarHijo(cerdo3);
		ternera.insertarHijo(ternera4);
		
		// Tests

		System.out.println("\n=============================");
		System.out.println("Test mostrarArbol()");
		arbolito.mostrarArbol();
		System.out.println("\n=============================");
		System.out.println("Test buscarNodo(\"pollo\")");
		System.out.println(arbolito.buscarNodo("pollo").getValor());
		System.out.println("\n=============================");
		System.out.println("Test buscarNodo(\"noExisto\")");
		System.out.println(arbolito.buscarNodo("noExisto") + ", no se ha encontrado el nodo en el árbol.");
		System.out.println("\n=============================");
		System.out.println("Test preOrden()");
		arbolito.preOrden();
		System.out.println("\n=============================");
		System.out.println("Test postOrden()");
		arbolito.postOrden();
		System.out.println("\n=============================");
		System.out.println("Test path(\"cinco\")");
		System.out.println(arbolito.path("cinco"));
		System.out.println("\n=============================");
		System.out.println("Test path(\"noExisto\")");
		System.out.println(arbolito.path("noExisto") + ", no se ha encontrado el nodo en el árbol.");
		System.out.println("Test insertarNodo (pollo)");
		arbolito.insertarNodo(pollo, "cien");
		System.out.println(pollo.getHijos());
		System.out.println("\n=============================");
		System.out.println("Test insertarNodo() (noExisto)");
		Nodo noExisto = new Nodo("noExisto", pollo);
		arbolito.insertarNodo(noExisto, "cien");
	}

}
