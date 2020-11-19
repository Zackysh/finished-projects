package utils;

import java.util.ArrayList;
import java.util.Scanner;

import models.Animal;

public class ConsoleHelper {
	
	private static Scanner sc = new Scanner(System.in);
	
	public void mainMenu(ArrayList<Animal> lista) {
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
	
	public boolean mainMenuNext(ArrayList<Animal> lista) {
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
	
	public void doOverPerson(Animal animal) {
		boolean exit = false;
		do {
			System.out.println(
					  "a. Charge salary.\n"
					+ "b. Take money.\n"
					+ "c. Set new salary.\n"
					+ "d. Show detaills.\n"
					+ "e. Back.");
			exit = personMenuNext(animal);
		} while (exit == false);
		
	}

	private boolean personMenuNext(Animal animal) {
		
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
				
				if(optionC == 'a') animal.cobrarSueldo();
				else if(optionC == 'b') {
					int pasta = 0;
					System.out.print("Amount: ");
					try {
						pasta = Integer.parseInt(sc.nextLine());
						animal.sacarPasta(pasta);
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
						animal.cambiarSueldo(newSalary);
					} catch (Exception e) {
						System.out.println("No has introducido un número.");
						e.fillInStackTrace();
					}			
				} else if(optionC == 'd') {
					System.out.println(animal);
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
	
	public void menuAddPerson(ArrayList<Animal> lista) {
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
		
		Animal animal = new Animal();
		lista.add(animal);
		System.out.println("Person added: " + lista.get(lista.size()-1));
	}
	
	public void menuChossePerson(ArrayList<Animal> lista) {
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
			System.out.println();
			doOverPerson(lista.get(option));
		} else System.out.println("\nThere's no people registered.");
	}
	
	public void showPersonList(ArrayList<Animal> lista) {
		if(!lista.isEmpty()) {
			for (Animal animal : lista) {
				System.out.println(lista.indexOf(animal)+1 + ". " + animal.getNombre());	
			}
		}
	}

	public boolean menuDeletePerson(ArrayList<Animal> lista) {
		boolean exit = false;
		
		showPersonList(lista);
		int option = -1;
		do {
			System.out.print("Person (numer): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if(option < 1 || option > lista.size())
					System.out.println("This is not a valid index.");
					showPersonList(lista);
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("This is not a number BRO.");
				showPersonList(lista);
			}
		} while(option < 1 || option > lista.size());
		option--;
		System.out.println("Persona eliminada: " + lista.get(option));
		lista.remove(option);
		
		return exit;		
	}
}
