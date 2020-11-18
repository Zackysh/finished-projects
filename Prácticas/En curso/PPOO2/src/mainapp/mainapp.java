package mainapp;
import models.Perro;
import models.Perro.RazaPerro;

public class mainapp {
	public static void main(String[] argd) {
		
		String pepito = "Pepito";

		RazaPerro rz = Enum.valueOf(RazaPerro.class, "E1");
		
		Perro adri = new Perro(pepito, rz);
		
		System.out.println(adri);
	}
}
