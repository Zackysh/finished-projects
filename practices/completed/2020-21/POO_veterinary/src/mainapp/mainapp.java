package mainapp;

import java.time.LocalDate;

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
import utils.ConsoleHelper;
import utils.DateHelper;

public class mainapp {
	
	public static void main(String[] argd) {
		
		ClinicaVeterinaria cv = new ClinicaVeterinaria();
		ConsoleHelper ch = new ConsoleHelper();
		
		LocalDate fechaNacimiento = DateHelper.generarFecha();
		Animal perro = new Perro("Pepper", fechaNacimiento, 7, RazaPerro.DALMATA, "3232132312412321");
		perro.setComentarios("Con puntitos negros.");
		Animal gato = new Gato("Garfield", fechaNacimiento, 23, RazaGato.SCOTTISH, "111432321444122");
		gato.setComentarios("Odia los lunes");
		Animal pajaro = new Pajaro("Snoopy", fechaNacimiento, 1.3, EspeciePajaro.CANARIO, true);
		pajaro.setComentarios("Es amarillo.");
		Animal reptil = new Reptil("Donatello", fechaNacimiento, 12, EspecieReptil.TORTUGA, false);
		reptil.setComentarios("Este bicho muerde.");
		
		cv.insertaAnimal(perro);
		cv.insertaAnimal(gato);
		cv.insertaAnimal(pajaro);
		cv.insertaAnimal(reptil);
		System.out.println(cv.lista);
//		ch.mainMenu(cv);
		
	}
}
