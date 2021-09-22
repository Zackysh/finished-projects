package Enums;

public class Enums {
	
	public enum RazaGato {COMUN("Com�n"), SIAMES("Siam�s"), PERSA("Persa"), ANGORA("Angora"), SCOTTISH("Scottish Fold");
		public String raza;
	
		private RazaGato(String raza) {
			this.raza = raza;
		}
		
		public String dameRaza() {
			return raza;
		}
	}
	
	public enum EspecieReptil {TORTUGA("Tortuga"), IGUANA("Iguana"), DRAGONK("Drag�n de Komodo");
		public String especie;
	
		private EspecieReptil(String especie) {
			this.especie = especie;
		}
		
		public String dameEspecie() {
			return especie;
		}
	}
	
	public enum EspeciePajaro {CANARIO("Canario"), PERIQUITO("Periquito"), AGAP("Agapornis");
		public String especie;
	
		private EspeciePajaro(String especie) {
			this.especie = especie;
		}
		
		public String dameEspecie() {
			return especie;
		}
	}
	
	public enum RazaPerro {PASTOR_ALEMAN("Pastor alem�n"), HUSKY("Husky"), FOXT("Fox Terrier"), DALMATA("Dalmata"), SANB("San Bernardo");
		public String raza;
	
		RazaPerro(String raza) {
			this.raza = raza;
		}
		
		public String dameRaza() {
			return raza;
		}
	}
	
	
	
}
