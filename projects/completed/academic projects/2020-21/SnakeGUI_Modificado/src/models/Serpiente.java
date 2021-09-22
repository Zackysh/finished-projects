package models;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Serpiente {
	// Constantes. No hay

	// ****** Atributos. Estado

	// Cuerpo de la serpiente, es una lista de cuadrados
	private ArrayList<Cuadrado> listaCuadrados;

	// Dirección inicial del movimiento
	private int iDireccion;
	
	// AdriGB: Necesitamos el tama�o del tablero para los cuadrados
	private int widthTablero;
	private int heightTablero;

	// ****** Métodos -- Comportamientos

	// Creación
	public Serpiente(int widthTablero, int heightTablero) {
		this.widthTablero = widthTablero;
		this.heightTablero = heightTablero;
		// creamos la lista de cuadrados.
		listaCuadrados = new ArrayList<Cuadrado>();

		// añadimos el primero de los cuadrados...
		listaCuadrados.add(new Cuadrado(60, 60, 20, (int) (Math.random() * 16000000), widthTablero, heightTablero));

		// siempre hacia abajo al principio
		iDireccion = Cuadrado.ABAJO;

	}

	// Moverse. Una serpiente sabe moverse
	public void moverse() {

		Cuadrado nuevaCabeza;
		Cuadrado antiguaCabeza;

		// Primero cogemos la cabeza y la duplicamos
		antiguaCabeza = listaCuadrados.get(0);
		nuevaCabeza = new Cuadrado(antiguaCabeza.getX(), antiguaCabeza.getY(), antiguaCabeza.getLado(),
				antiguaCabeza.getColor(), widthTablero, heightTablero);

		// movemos la cabeza a su nueva posición
		nuevaCabeza.moverse(iDireccion);

		// la añadimos a la lista
		listaCuadrados.add(0, nuevaCabeza);

		// borramos el último cuadrado por la cola (pop del basic)
		listaCuadrados.remove(listaCuadrados.size() - 1);
	}
	
	public Cuadrado getCabeza() {
		return listaCuadrados.get(0);
	}

	public void crecer() {
		Cuadrado nuevaCabeza;
		Cuadrado antiguaCabeza;

		// Primero cogemos la cabeza y la duplicamos
		antiguaCabeza = listaCuadrados.get(0);
		nuevaCabeza = new Cuadrado(antiguaCabeza.getX(), antiguaCabeza.getY(), antiguaCabeza.getLado(),
				antiguaCabeza.getColor(), widthTablero, heightTablero);
		// movemos la cabeza a su nueva posición
		nuevaCabeza.moverse(iDireccion);

		// la añadimos a la lista
		listaCuadrados.add(0, nuevaCabeza);

		// ahora no borramos la última y hemos crecido...
	}

	// la serpiente se muere porque se toca a si misma o porque se ha salido del
	// tablero
	public boolean estaMuerta(int iAlto, int iAncho) {
		boolean resultado;

		resultado = (seEstaTocandoEllaMisma());

		return resultado;
	}

	// la cabeza, está tocando alguna parte de su cuerpo??
	private boolean seEstaTocandoEllaMisma() {
		int iCont;
		Cuadrado cabeza;

		cabeza = listaCuadrados.get(0);

		// la cabeza podrá tocar como mucho, el quinto cuadrado en adelante de su
		// cuerpo...
		// por eso el cuadrado 1, 2 y 3 no lo comprobamos
		for (iCont = 4; iCont < listaCuadrados.size(); iCont++) {
			if (listaCuadrados.get(iCont).estaEncimaDe(cabeza)) // oh oh, hemos chocado...
				return true;
		}

		return false;
	}
	
	// la serpiente también sabe pintarse
	public void pintarse(Graphics2D g) {
		int iCont;

		// pintamos desde el cuadrado 0 hasta el último. Cuidado, aquí con el "<"
		// evitamos
		// tener que poner el "-1" que poniamos en el for del BASIC
		for (iCont = 0; iCont < listaCuadrados.size(); iCont++) {
			listaCuadrados.get(iCont).pintarse(g);
		}
	}

	// controlamos el cambio de dirección
	public void cambiaDireccion(int key) {
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			iDireccion = Cuadrado.IZQ;
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			iDireccion = Cuadrado.ABAJO;
		} else if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			iDireccion = Cuadrado.ARRIBA;
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			iDireccion = Cuadrado.DER;
		}
	}

	// los puntos se corresponden con el tamaño de nuestra serpiente
	public int getPuntos() {
		return listaCuadrados.size();
	}

}