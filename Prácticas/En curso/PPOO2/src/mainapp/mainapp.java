package mainapp;

import java.util.Date;

import models.Animal;
import models.ClinicaVeterinaria;
import models.Gato;
import models.Gato.RazaGato;
import models.Pajaro;
import models.Pajaro.EspeciEPajaro;
import models.Perro;
import models.Perro.RazaPerro;
import models.Reptil.EspecieReptil;
import models.Reptil;
import utils.DateCreator;

public class mainapp {
	
	public static void main(String[] argd) {
		
		DateCreator dc = new DateCreator();
		ClinicaVeterinaria cv = new ClinicaVeterinaria();
		
		Date fechaNacimiento = dc.createDate("2000-12-06");
		Animal perro = new Perro("Pepper", fechaNacimiento, 7, RazaPerro.DALMATA, "3232132312412321");
		perro.setComentarios("Con puntitos negros.");
		Animal gato = new Gato("Garfield", fechaNacimiento, 23, RazaGato.SCOTTISH, "111432321444122");
		gato.setComentarios("Odia los lunes");
		Animal pajaro = new Pajaro("Snoopy", fechaNacimiento, 1.3, EspeciEPajaro.CANARIO, true);
		pajaro.setComentarios("Es amarillo.");
		Animal reptil = new Reptil("Donatello", fechaNacimiento, 12, EspecieReptil.TORTUGA, false);
		reptil.setComentarios("Este bicho muerde.");
		
		cv.insertaAnimal(perro);
		cv.insertaAnimal(gato);
		cv.insertaAnimal(pajaro);
		cv.insertaAnimal(reptil);
		
		System.out.println(cv);
	}

}
