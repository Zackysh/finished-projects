package utils;

import java.time.LocalDate;
import java.util.Scanner;

import Enums.Enums.EspeciePajaro;
import Enums.Enums.EspecieReptil;
import Enums.Enums.RazaGato;
import Enums.Enums.RazaPerro;
import models.Animal;
import models.ClinicaVeterinaria;
import models.Gato;
import models.Pajaro;
import models.Perro;
import models.Reptil;

public class ConsoleHelper {

	private static Scanner sc = new Scanner(System.in);

	public void mainMenu(ClinicaVeterinaria cv) {
		System.out.println("================================\n" + "            WELCOME!            \n"
				+ "================================\n\n");

		boolean exit = false;
		do {
			System.out.println("a. Añadir animal.\n" + "b. Cambiar comentarios de animal.\n"
					+ "c. Mostrar información de animal.\n" + "d. Salir.");
			exit = mainMenuNext(cv);
		} while (exit == false);
	}

	public boolean mainMenuNext(ClinicaVeterinaria cv) {
		boolean exit = false;
		char optionC;
		boolean isValid = false;

		do {
			System.out.print("Opción (minúsculas): ");
			String option = sc.nextLine();
			System.out.println();
			if (option.length() == 1) {
				isValid = true;
				optionC = option.charAt(0);

				if (optionC == 'a')
					menuAddAnimal(cv);
				else if (optionC == 'b')
					menuSetComentarios(cv);
				else if (optionC == 'c')
					menuShowInfo(cv);
				else if (optionC == 'd')
					exit = true;
				else {
					System.out.println("No has introducido una opción válida.");
					isValid = false;
				}

			} else {
				if (option.isBlank())
					System.out.println("No has escrito nada!");
				else
					System.out.println("Debes introducir solo una letra.");
			}

		} while (isValid == false);
		System.out.println();
		return exit;
	}

	// MENU AÑADIR ANIMAL
	private int menuTipoAnimal() {
		boolean isValid = false;
		int option = -1;
		System.out.print("Animales permitidos: \n" + "1. Perro.\n" + "2. Gato.\n" + "3. Pájaro.\n" + "4. Reptil.");
		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2 || option == 3 || option == 4)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2 || option == 3 || option == 4)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);

		return option;
	}

	// OBTENIENDO DATOS GENERALES DE ANIMAL
	public void menuAddAnimal(ClinicaVeterinaria cv) {
		String nombre;
		LocalDate fechaNacimiento;
		double peso = 0;

		int option = menuTipoAnimal();
		int option2 = -1;
		boolean isValid = false;

		System.out.print("Nombre: ");
		nombre = sc.nextLine();
		System.out.println("Fecha de nacimiento del animal: (YYYY-MM-DD)");
		fechaNacimiento = DateHelper.leerFecha();
		do {
			isValid = true;
			try {
				System.out.print("Peso: ");
				peso = Double.parseDouble(sc.nextLine());
			} catch (Exception e) {
				System.out.println("Peso no válido.");
				isValid = false;
			}
		} while (!isValid);
		Animal animal = null;
		if (option == 1)
			animal = menuTipoPerro(nombre, fechaNacimiento, peso);
		else if (option == 2)
			animal = menuTipoGato(nombre, fechaNacimiento, peso);
		else if (option == 3)
			animal = menuTipoPajaro(nombre, fechaNacimiento, peso);
		else if (option == 4)
			animal = menuTipoReptil(nombre, fechaNacimiento, peso);

		cv.insertaAnimal(animal);
		System.out.println("Animal añadido: " + animal);
	}

	// GENERANDO ANIMAL ESPECIFICO
	// PAJARO
	private Animal menuTipoPajaro(String nombre, LocalDate fechaNacimiento, double peso) {
		boolean isValid = false;
		boolean esCantor = false;
		int option = -1;
		int cont = 1;
		System.out.println("Razas permitidas:");
		for (EspeciePajaro especie : EspeciePajaro.values()) {
			System.out.println(cont++ + ". " + especie.especie + ".");
		}
		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2 || option == 3)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2 || option == 3)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);
		System.out.println("¿Desea que sea cantor?\n" + "1. Sí." + "2. No.");
		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);

		if (option == 1)
			esCantor = true;

		EspeciePajaro especie = EspeciePajaro.values()[option - 1];
		Animal pajaro = new Pajaro(nombre, fechaNacimiento, peso, especie, esCantor);
		return pajaro;
	}

	// REPTIL
	private Animal menuTipoReptil(String nombre, LocalDate fechaNacimiento, double peso) {
		boolean isValid = false;
		boolean esVenenoso = false;
		int option = -1;
		int cont = 1;
		System.out.println("Razas permitidas:");
		for (EspecieReptil especie : EspecieReptil.values()) {
			System.out.println(cont++ + ". " + especie.especie + ".");
		}
		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2 || option == 3)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2 || option == 3)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);
		System.out.println("¿Desea que sea venenoso?\n" + "1. Sí." + "2. No.");
		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);

		if (option == 1)
			esVenenoso = true;

		EspecieReptil especie = EspecieReptil.values()[option - 1];
		Animal reptil = new Reptil(nombre, fechaNacimiento, peso, especie, esVenenoso);
		return reptil;
	}

	// GATO
	private Animal menuTipoGato(String nombre, LocalDate fechaNacimiento, double peso) {
		boolean isValid = false;
		int option = -1;
		int cont = 1;
		System.out.println("Razas permitidas:");
		for (RazaGato raza : RazaGato.values()) {
			System.out.println(cont++ + ". " + raza.raza + ".");
		}

		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);

		RazaGato raza = RazaGato.values()[option - 1];
		Animal gato = new Gato(nombre, fechaNacimiento, peso, raza, ClinicaVeterinaria.genChip());
		return gato;
	}

	// PERRO
	private Animal menuTipoPerro(String nombre, LocalDate fechaNacimiento, double peso) {
		boolean isValid = false;
		int option = -1;
		int cont = 1;
		System.out.println("Razas permitidas:");
		for (RazaPerro raza : RazaPerro.values()) {
			System.out.println(cont++ + ". " + raza.raza + ".");
		}

		do {
			System.out.print("Opción (número): ");
			try {
				option = Integer.parseInt(sc.nextLine());
				if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5)
					isValid = true;
				else
					isValid = false;
				if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5)
					isValid = true;
				else {
					isValid = false;
					System.out.println("No has introducido una opción válida.");
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				System.out.println("Debes introducir un número entero..");
			}
		} while (isValid != true);

		RazaPerro raza = RazaPerro.values()[option - 1];
		Animal perro = new Perro(nombre, fechaNacimiento, peso, raza, ClinicaVeterinaria.genChip());
		return perro;
	}

	// MENU CAMBIAR COMENTARIOS
	public void menuSetComentarios(ClinicaVeterinaria cv) {
		String comentario;
		String nombre;
		boolean isValid = false;
		do {
			isValid = true;
			System.out.print("Nombre de la mascota: ");
			nombre = sc.nextLine();
			System.out.println("Comprobando...");
			Animal temp = cv.buscarAnimal(nombre);
			if (temp != null) {
				System.out.println("Ha ido bien.");
				System.out.println(
						"A continuación, introduzca los comentarios a añadir: (introducir \"\\n\" para saltos de línea)");
				comentario = sc.nextLine();
				try {
					cv.modificarComentarioAnimal(nombre, comentario);
				} catch (Exception e) {
					e.fillInStackTrace();
				}
			} else {
				isValid = false;
				System.out.println("No se encuentra ningún animal que coincida con ese nombre. Vuelva a intentarlo.");
			}
		} while (isValid != true);

	}
	
	// MENU MOSTRAR INFORMACIÓN
	private void menuShowInfo(ClinicaVeterinaria cv) {
		String nombre;
		boolean isValid = false;
		do {
			isValid = true;
			System.out.print("Nombre de la mascota: ");
			nombre = sc.nextLine();
			System.out.println("Comprobando...");
			Animal temp = cv.buscarAnimal(nombre);
			if (temp != null) {
				System.out.println("Ha ido bien.");
				cv.mostrarAnimal(nombre);
			} else {
				isValid = false;
				System.out.println("No se encuentra ningún animal que coincida con ese nombre. Vuelva a intentarlo.");
			}
		} while (isValid != true);
		
	}

}
