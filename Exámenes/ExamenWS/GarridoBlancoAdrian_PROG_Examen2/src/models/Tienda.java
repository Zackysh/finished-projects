package models;

import java.util.ArrayList;
import java.util.Scanner;

import utils.Validator;

/**
 * Clase que simula una tienda.
 * 	- Controla una caja (dinero):
 *  - Puede almacenar InstrumentosMusicales.
 *  - Puede insertar nuevos InstrumentosMusicales.
 *  - Puede vender InstrumentosMusicales (suma dineroa la caja).
 * @author alumnado
 *
 */
public class Tienda {
	
	private Scanner sc = new Scanner(System.in);
	private ArrayList<InstrumentoMusical> instrumentosStock;
	private double caja;
	
	public static void br() {
		System.out.println();
	}
	
	public Tienda() {
		this.instrumentosStock = new ArrayList<InstrumentoMusical>();
		this.caja = 0;
	}
	
	public void insertarInstrumento() {
		
		String marca = "";
		String modelo = "";
		double precio = 0;
		String aux = "";
		
		System.out.println("A continuación seleccione una opción:\n"
				+ "1. Añadir batería al stock.\n"
				+ "2. Añadir guitarra al stock.");
		
		String strOpcion;
		boolean esValida = false;
		do {
			System.out.print("Opción: ");
			strOpcion = sc.nextLine().trim();
			
			if (strOpcion.equals("1") || strOpcion.equals("2"))
				esValida = true;
			else
				System.out.println("Opción no válida, vuelve a intentarlo.");
			br();
		} while (!esValida);
		
		System.out.println("A continuación detalle el producto:");
		// Marca
		do {
			System.out.print("Marca: ");
			aux = sc.nextLine().trim();
			if (aux.isBlank())
				System.out.println("Opción no válida, vuelve a intentarlo.");		
			else
				marca = aux;
		} while (aux.isBlank());
		
		// Modelo
		do {
			System.out.print("Modelo: ");
			aux = sc.nextLine().trim();
			if (aux.isBlank())
				System.out.println("Opción no válida, vuelve a intentarlo.");
			else
				modelo = aux;
		} while (aux.isBlank());
		
		// Precio
		do {
			System.out.print("Precio: ");
			aux = sc.nextLine().trim();
			if (Validator.isDouble(aux))
				if(Double.parseDouble(aux) >= 0)
					precio = Double.parseDouble(aux);
				else
					System.out.println("No te podemos regalar dinero.");
			else
				System.out.println("Opción no válida, vuelve a intentarlo.");
		} while (Validator.isDouble(aux) == false);	
		
		switch(strOpcion) {
		case "1":			
			instrumentosStock.add(new Bateria(precio, marca, modelo));
			break;
		case "2":
			instrumentosStock.add(new Guitarra(precio, marca, modelo));			
		}		
	}
	
	public void listarInstumentos() {
		if(hayStock()) {
			System.out.println("Listado de instrumentos");
			int cont = 1;
			for (InstrumentoMusical instrumentoMusical : instrumentosStock) {
				System.out.println(cont++ + ". "+ instrumentoMusical.toString());
			}			
		} else
			System.out.println("¡No hay stock!");
	}
	
	public void venderInstrumento() {
		if(hayStock()) {
			System.out.println("Seleccione un instrumendo:");
			listarInstumentos();
			br();
			String strAux;
			int aux = 0;
			do {
				System.out.print("¿Qué instrumento deseas?: ");
				strAux = sc.nextLine().trim();
				if (Validator.isInt(strAux))
					aux = Integer.parseInt(strAux);
				if(aux < 1 || aux > instrumentosStock.size())
					System.out.println("Opción no válida, vuelve a intentarlo.");
				else
					System.out.println("Opción no válida, vuelve a intentarlo.");
			} while (Validator.isInt(strAux) == false || aux < 1 || aux > instrumentosStock.size());	
			br();
			
			System.out.println("Te acabas de comprar una " + instrumentosStock.get(aux-1) + ",\n"
					+ "suena así: " + instrumentosStock.get(aux-1).tocarInstrumento() + ". ¡Disfrútala!");
			caja += instrumentosStock.get(aux-1).precio;
			instrumentosStock.remove(aux-1);
			
			System.out.println("Gracias por su compra :)");			
		} else {
			System.out.println("¡No hay stock!");
		}
	}
	
	public void mostrarCaja() {
		System.out.println("En la caja hay de momento: " + caja + "€.");
	}
	
	public boolean hayStock() {
		if (instrumentosStock.size() == 0)
			return false;
		else
			return true;
	}
}
