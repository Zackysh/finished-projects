package main;

import java.util.Scanner;

public class Mainapp {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int palo = 0;
		int numero = 0;
		int cont = 0;
		for (int i = 0; i < 46; i++) {
			int id = cont++;

			if (id % 10 == 0) {
				palo = id / 10;
				numero = 10;
			} else {
				palo = id / 10 + 1;
				numero = id % 10;
			}

			System.out.println("Palo: " + palo + "  Número: " + numero);
		}
		
	}
}
