package main;

import java.util.ArrayList;

import enums.EnumCartas.Numeros;
import enums.EnumCartas.Palos;
import models.Carta;

public class Mainapp {
	public static void main(String[] args) {
		
		Carta nueva = new Carta(Palos.OROS, Numeros.REY);
		Carta vieja = null;
		ArrayList lista = null;
		
		int cont = 1;
		for (int i = 0; i < 40; i++) {
			vieja = new Carta(cont++);
			System.out.println(
					  "Carta: " + vieja.getNombre() + "\n"
					+ "Valor Tute: " + vieja.getValorTute() + "\n"
					+ "Valor Mus: " + vieja.getValorMus() + "\n"
			);
		}	
	}
}
