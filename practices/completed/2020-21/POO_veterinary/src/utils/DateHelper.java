package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateHelper {

	private static Scanner sc = new Scanner(System.in);

	public DateHelper() {
	}

	public static LocalDate leerFecha() {

		
		boolean isValid = false;
		LocalDate date = null;
		do {
			isValid = true;
			System.out.println("Fecha:");
			String strDate = sc.nextLine();
			try {
				date = LocalDate.parse(strDate);
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: hay un problema con el formato de la fecha, introduce "
						+ "la fecha así (YYYY-MM-DD). Ejemplo: 1990-10-22 :)");
				isValid = false;
			}
		} while (!isValid);

		return date;
	}

	public static LocalDate generarFecha() {
		LocalDate date = LocalDate.now();
		return date;
	}
}