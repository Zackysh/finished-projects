package arrays;

public class Practica_Repaso {
	public static void main (String []args) {
		
		String[][] clase = new String[6][5];
//		String[] alumnos = {"Eduard Melgar", "Maria-Paz Piqueras", "Saul Guisado", "Maria-Josefa Espinosa",
//							"Alejandra Brito", "Desiree Sanjuan", "Olivia Madrid", "German Perdomo",
//							"Ion Canto", "Gabriel Rivero", "Rachida Mesa", "L�dia Ferreiro",
//							"Inmaculada Leal", "Regina de La Rosa", "Humberto Barrios"		, "Yesica de Miguel",
//							"Bryan Nogales", "Alejandra Ibarra", "Francisca Belmonte", "Rafael Valera",
//							"Teodora Sole", "Al�cia Davila", "Isidoro Prat", "Dina Neira",
//							"Alan Bello", "Emiliana Mejias", "Aaron Vilchez", "Valentina Peral",
//							"Juan-Carlos S�nchez", "Olga Gil"};
		
		String[] alumnos = {"Eduard MelgarI", "Maria-Paz PiquerasP", "Saul GuisadoI", "Maria-Josefa EspinosaP",
				"Alejandra BritoP", "Desiree SanjuanI", "Olivia MadridP", "German PerdomoI",
				"Ion CantoP", "Gabriel RiveroI", "Rachida MesaP", "L�dia FerreiroI",
				"Inmaculada LealP", "Regina de La RosaI", "Humberto BarriosP", "Yesica de MiguelI",
				"Bryan NogalesP", "Alejandra IbarraI", "Francisca BelmonteP", "Rafael ValeraI",
				"Teodora SoleP", "Al�cia DavilaI", "Isidoro PratP", "Dina NeiraI",
				"Alan BelloP", "Emiliana MejiasI", "Aaron VilchezP", "Valentina PeralI",
				"Juan-Carlos S�nchezP", "Olga GilI"};

		String[] grupoUno = new String[15];
		String[] grupoDos = new String[15];
		int j = 0;
		
		for (int i = 0; i < alumnos.length; i++) {
			
			if(i%2 == 0 && i < 15) grupoUno[i] = alumnos[i];
			
//			else {
//				
//				grupoDos[j] = alumnos[j];
//				j++;
//				
//			}
		}
		
//		for (int i = 0; i < grupoUno.length; i++) {
//			System.out.println(grupoUno[i]);
//		}
//		
//		for (int i = 0; i < grupoDos.length; i++) {
//			System.out.println(grupoDos[i]);
//		}

	}
}
