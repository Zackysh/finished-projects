package mainapp;

import java.util.ArrayList;
import java.util.Scanner;

import model.CuentaCorriente;
import model.Persona;
import utils.ConsoleHelper;



public class MainApp {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		ConsoleHelper ch = new ConsoleHelper();
		ArrayList<Persona> lista = new ArrayList<Persona>();
		lista.add(new Persona("Pipo", "Fernandez", "12345", 12000, new CuentaCorriente(1, 0)));
		lista.add(new Persona("Maria", "Fernández", "2", 13000, new CuentaCorriente(2, 0)));
		lista.add(new Persona("Carlos", "Piupiu", "32512", 12000, new CuentaCorriente(2,0)));
		ch.mainMenu(lista);
	}
	
}
