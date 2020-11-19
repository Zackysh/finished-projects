package mainapp;

import java.util.ArrayList;

import model.CuentaCorriente;
import model.Persona;
import utils.ConsoleHelper;

import java.util.Scanner;

public class MainApp {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		ArrayList<Persona> lista = new ArrayList<Persona>();
		ConsoleHelper ch = new ConsoleHelper();
		
		System.out.println(
				   "================================\n"
				 + "            WELCOME!            \n"
				 + "================================\n\n");
		
		boolean exit = false;		
		do {		
			System.out.println(
					  "a. Add person.\n"
					+ "b. Chosse person.\n"
					+ "c. Delete person.\n"
					+ "d. Exit.");
			exit = ch.mainMenuNext(lista);
		} while (exit == false);
	}
}
