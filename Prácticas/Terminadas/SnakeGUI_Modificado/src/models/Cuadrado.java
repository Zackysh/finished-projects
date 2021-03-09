package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.time.Year;

public class Cuadrado {
	// Constantes. Son las direcciones en las que nuestro cuadrado puede moverse
	public static final int ARRIBA = 1;
	public static final int ABAJO = 2;
	public static final int IZQ = 3;
	public static final int DER = 4;
	public int esManzana = 0;

	// Atributos -- Estado

	// Necesitamos la posiciÃ³n del cuadrado marcado por su esquina superior izq.
	private int posX;
	private int posY;

	// AdriGB: necesitamos el tamaño del tablero, con él, si está en el borde,
	// aparecerá en lado contrario
	private int heightTablero;
	private int widthTablero;

	// TambiÃ©n necesitamos el lado del cuadrado
	private int lado;

	// Ahora el color
	private int colorCuadrado;

	// comportamiento

	// creaciÃ³n
	public Cuadrado(int pX, int pY, int l, int cc, int widthTablero, int heightTablero) {
		this.widthTablero = widthTablero;
		this.heightTablero = heightTablero;
		posX = pX;
		posY = pY;
		lado = l;
		colorCuadrado = cc;
	}

	public void setAsManzana() {
		esManzana = 1;
	}

	// un cuadrado se mueve arriba, abajo, derecha e izquierda
	public void moverse(int iDireccion) {
		switch (iDireccion) {
		case Cuadrado.ARRIBA:
			if (posY > 0)
				posY -= lado; // 1 es arriba
			else
				posY = heightTablero - 90;
			break;
		case Cuadrado.ABAJO:
			if (posY < heightTablero - 90)
				posY += lado; // 2 es abajo
			else
				posY = 0;
			break;
		case Cuadrado.IZQ:
			if (posX > 0)
				posX -= lado; // 3 es izquierda
			else
				posX = widthTablero - 20;
			break;
		case Cuadrado.DER:
			if (posX < widthTablero - 20)
				posX += lado; // 4 es derecha
			else
				posX = 0;
		}
	}

	// un cuadrado puede estar colisionando con otro
	public boolean estaEncimaDe(Cuadrado otroC) {
		// en nuestro caso, sÃ³lo comprobamos la esquina superior izq
		// almacenada en las posiciones X e Y. No hay otra posibilidad.
		return (otroC.getX() == posX && otroC.getY() == posY);
	}

	// MÃ©todos de obtenciÃ³n de datos
	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getLado() {
		return lado;
	}

	public int getColor() {
		return colorCuadrado;
	}

	// Un cuadrado tiene que saber pintarse
	public void pintarse(Graphics2D g) {

		g.setColor(new Color(colorCuadrado));
		// g.drawRect(posX, posY, lado, lado);
		g.fillRect(posX, posY, lado, lado);
		// g.fillOval(posX, posY, lado, lado);

	}
}
