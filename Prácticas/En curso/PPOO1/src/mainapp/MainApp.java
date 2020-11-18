package mainapp;

import java.util.ArrayList;

import model.CuentaCorriente;
import model.Persona;
import java.util.Scanner;

public class MainApp {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		ArrayList<Persona> lista = new ArrayList<Persona>();
		
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
			exit = mainMenuNext(lista);
		} while (exit == false);
	}
	
	/**
	 * 
	 * @param lista
	 * @returns true If user wants to exit
	 * @returns false If user wants to go again
	 */
	private static boolean mainMenuNext(ArrayList<Persona> lista) {
		boolean exit = false;
		char optionC;
		boolean isValid = false;
		
		do {
			System.out.print("Option (lowercase): ");
			String option = sc.nextLine();
			if(option.length() == 1) {
				isValid = true;
				optionC = option.charAt(0);		
				
				if(optionC == 'a') menuAddPerson(lista);
				else if(optionC == 'b') menuChossePerson();
				else if(optionC == 'c') menuDeletePerson();
				else if(optionC == 'd') exit = true;
				else {
					System.out.println("This is not a valid option!");
					isValid = false;
				}
				
			} else {
				if(option.isBlank()) System.out.println("You didn't put anything!");
				else System.out.println("You must only introduce a single char.");
			}
			
		} while(isValid == false);
		System.out.println();
		return exit;
	}

	private static void menuAddPerson(ArrayList<Persona> lista) {
		System.out.print("Name: ");
		String nombre = sc.nextLine();
		System.out.print("Surname: ");
		String apellidos = sc.nextLine();
		System.out.print("ID: ");
		String dni = sc.nextLine();
		double sueldo = 0;
		boolean isValid = false;		
		do {	
			System.out.print("Salary: ");
			try {
				sueldo = Double.parseDouble(sc.nextLine());
				isValid = true;
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Not valid double.");
			}			
		} while (isValid != true);
		
		CuentaCorriente cuenta = null;
		Persona persona = new Persona(nombre, apellidos, dni, sueldo, cuenta);
		cuenta = new CuentaCorriente(Integer.toString(lista.size() + 1), 0, persona);
		persona.setCuentaCorriente(cuenta);
		lista.add(persona);
		System.out.println("Person added: " + lista.get(lista.size()-1));
	}
	
	private static void showPersonList(ArrayList<Persona> lista) {
		for (Persona persona : lista) {
			System.out.println(lista.indexOf(persona)+1 + ". " + persona.getDni() + ", " + persona.getNombre());			
		}
		int option = -1;
		do {
			System.out.print("Person (select numer)");
			try {
				option = Integer.parseInt(sc.nextLine());
				option--;
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("This isn't a valid index.");
			}
		} while(option < 0 && option > lista.size());
		
		doOverPerson(lista.get(option));
	}
	
	private static void doOverPerson(Persona persona) {
		boolean exit = false;
		do {		
			System.out.println(
					  "a. Charge salary.\n"
					+ "b. Take money.\n"
					+ "c. Set new salary.\n"
					+ "d. Show detaills.");
			exit = personMenuNext(persona);
		} while (exit == false);
		
	}

	private static boolean personMenuNext(Persona persona) {
		
		return false;
	}

	private static boolean menuDeletePerson() {
		boolean isValid = false;
		boolean exit = false;
		do {
			System.out.print("Option (number): ");
			try {
				int option = Integer.parseInt(sc.nextLine());	
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("You must input a number!");
			}
			
			
			
			
		} while(isValid == false);
		System.out.println();
		
		return exit;		
	}

	private static void menuChossePerson() {
		// TODO Auto-generated method stub
		
	}

}
