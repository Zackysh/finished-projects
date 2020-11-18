package utils;

import java.util.ArrayList;
import java.util.Scanner;

import model.CuentaCorriente;
import model.Persona;

public class ConsoleHelper {
	
	private static Scanner sc = new Scanner(System.in);
	
	public void mainMenu(ArrayList<Persona> lista) {
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
	
	public boolean mainMenuNext(ArrayList<Persona> lista) {
		boolean exit = false;
		char optionC;
		boolean isValid = false;
		
		do {
			System.out.print("Option (lowercase): ");
			String option = sc.nextLine();
			System.out.println();
			if(option.length() == 1) {
				isValid = true;
				optionC = option.charAt(0);		
				
				if(optionC == 'a') menuAddPerson(lista);
				else if(optionC == 'b') menuChossePerson(lista);
				else if(optionC == 'c') menuDeletePerson(lista);
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
	
	public void doOverPerson(Persona persona) {
		boolean exit = false;
		do {
			System.out.println(
					  "a. Charge salary.\n"
					+ "b. Take money.\n"
					+ "c. Set new salary.\n"
					+ "d. Show detaills.\n"
					+ "e. Back.");
			exit = personMenuNext(persona);
		} while (exit == false);
		
	}

	private boolean personMenuNext(Persona persona) {
		
		boolean exit = false;
		char optionC;
		boolean isValid = false;
		
		do {
			System.out.print("Option (lowercase): ");
			String option = sc.nextLine();
			System.out.println();
			
			if(option.length() == 1) {
				isValid = true;
				optionC = option.charAt(0);		
				
				if(optionC == 'a') persona.cobrarSueldo();
				else if(optionC == 'b') {
					int pasta = 0;
					System.out.print("Amount: ");
					try {
						pasta = Integer.parseInt(sc.nextLine());
						persona.sacarPasta(pasta);
					} catch (Exception e) {
						System.out.println("No has introducido un número.");
						e.fillInStackTrace();
					}
					System.out.println();
				} else if(optionC == 'c') {
					int newSalary = 0;
					System.out.print("New salary: ");
					try {
						newSalary = Integer.parseInt(sc.nextLine());
						persona.cambiarSueldo(newSalary);
					} catch (Exception e) {
						System.out.println("No has introducido un número.");
						e.fillInStackTrace();
					}			
				} else if(optionC == 'd') {
					System.out.println(persona);
				} else if(optionC == 'e') {
					exit = true;
				}
				else {
					System.out.println("This is not a valid option!");
					isValid = false;
				}				
			} else {
				if(option.isBlank()) System.out.println("You didn't put anything!");
				else System.out.println("You must only introduce a single char.");
			}
			
		} while(isValid == false || exit == false);
		System.out.println();
		return exit;
	}
	
	public void menuAddPerson(ArrayList<Persona> lista) {
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
		
		CuentaCorriente cuenta = new CuentaCorriente(lista.size() + 1, 0);
		Persona persona = new Persona(nombre, apellidos, dni, sueldo, cuenta);
		lista.add(persona);
		System.out.println("Person added: " + lista.get(lista.size()-1));
	}
	
	public void menuChossePerson(ArrayList<Persona> lista) {
		if(!lista.isEmpty()) {
			showPersonList(lista);
			int option = -1;
			do {
				System.out.print("Person (numer): ");
				try {
					option = Integer.parseInt(sc.nextLine());
					if(option < 1 || option > lista.size())
						System.out.println("This is not a valid index.");
				} catch (Exception e) {
					e.fillInStackTrace();
					System.out.println("This is not a number BRO.");
				}
			} while(option < 1 || option > lista.size());
			option--;
			doOverPerson(lista.get(option));
		} else System.out.println("\nThere's no people registered.");
	}
	
	public void showPersonList(ArrayList<Persona> lista) {
		if(!lista.isEmpty()) {
			for (Persona persona : lista) {
				System.out.println(lista.indexOf(persona)+1 + ". " + persona.getNombre());	
			}
		}
	}

	public boolean menuDeletePerson(ArrayList<Persona> lista) {
		boolean isValid = false;
		boolean exit = false;
		
		showPersonList(lista);
		
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
}
