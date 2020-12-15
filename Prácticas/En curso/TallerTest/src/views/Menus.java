package views;

import java.util.Scanner;

import models.Ciclomotor;
import models.Coche;
import models.Motocicleta;
import models.Taller;
import utils.RandomGenerator;
import utils.VarUtils;

/**
 * Clase que contiene todos los menús del proyecto.
 * 
 * Cabe destacar que el método menuPrincipal() es la raíz en la que todos los
 * demás subMenús son invocados.
 * 
 * @author AdriGB
 * @author Jonhy
 *
 */
public class Menus {
	
	private static Scanner sc = new Scanner(System.in);
	private static RandomGenerator rg = new RandomGenerator();
	private Taller taller;
	
	public Menus(Taller taller) {
		this.taller = taller;
	}
	
	/**
	 * Método que agiliza le insercción de saltos de línea y facilita la lectura del
	 * código.
	 */
	public void br() {
		System.out.println();
	}	
	
	/**
	 * Método que muestra el menú principal. Es el hub principal en el que todas las
	 * funcionalidades de este proyecte convergen.
	 * 
	 * Para poder testear todo durante la ejecución se pueden seguir estos pasos:
	 *  - Tratar de acceder a las opciones 2, 3, 4 o 5 sin vehículos.
	 *  - Crear un vehículo de cada tipo, excepto motocicleta.
	 *  - Tratar de acceder a la opción 3.
	 *  - Crear dos motocicletas.
	 *  - Cambiar el escape de una motocicleta.
	 *  - Usar todas las opciones del submenú 4, listar.
	 *  - Reaparar (usando todas las opciones) el resto de vehículos.
	 *  - Entregar todos los vehículos.
	 */
	public void menuPrincipal() {
		
		String strOpcion = "";
		do {
			System.out.println(
					"------------- Inicio -------------\n"
			+ "1. Añadir vehículo averiado.\n" 
		    + "2. Reparar vehículo.\n"
		    + "3. Cambiar escape de motocicleta.\n"
	    	+ "4. Listar vehículos.\n" 
		    + "5. Entregar vehículo.\n" 
	    	+ "6. Terminar la jornada.");
			System.out.print("Opción: ");
			strOpcion = sc.nextLine();
			br();
			
			switch (strOpcion) {
			case "1":
				br();
				subMenuInsertarVehiculo();
				br();
				break;
			case "2":
				br();
				subMenuRepararVehiculo();
				br();
				break;
			case "3":
				br();
				subMenuCambiarEscape();
				br();
				break;
			case "4":
				br();
				subMenuListarVehiculos();
				br();
				break;
			case "5":
				br();
				subMenuEntregarVehiculo();
				br();
				break;
			case "6":
				System.out.print("\n"
					+ "================================\n"
					+ "|     ¡Hasta la próxima!       |\n"
					+ "================================\n");
				break;
		    default:
				System.out.println("Opción no valida.");
			}
		} while (!strOpcion.equals("6"));
	}

	/**
	 * Método que muestra un menú para seleccionar el tipo de vehículo a insertar.
	 * Posteriormente obtiene sus atributos.
	 * 
	 * Estos atributos se usarán para instanciar un nuevo vehículo del tipo
	 * especificado, será pasado al taller para que este lo inserte en su lista de
	 * vehículos averiados.
	 */
	public void subMenuInsertarVehiculo() {
		
		System.out.println("¿Que tipo de vehículo es?\n"
				+ "1. Coche.\n"
				+ "2. Motocicleta.\n"
				+ "3. Ciclomotor.");
		
		String op = "";
		do { // Determinamos el tipo de vehículo
			
			System.out.print("Opcion: ");
			op = sc.nextLine();
			br();			
			if(!op.equals("1") && !op.equals("2") && !op.equals("3"))
					System.out.println("Opción no válida.");
			
		} while (!op.equals("1") && !op.equals("2") && !op.equals("3"));
		
		System.out.println("Introduzca los datos del su vehículo: (Escribir \"auto\" para valores aleatorios)");
		
		// Atributo matrícula, necesaria para todos los vehículos
		String matricula;
		boolean esUnica = false;
		do { // Nos aseguramos de que la matrícula sea única entre los coches registrados
			
			matricula = VarUtils.pedirString("Matrícula");
			esUnica = taller.mtrIsUnica(matricula);
			
			if(!esUnica)
				System.out.println("La matrícula introducida ya existe, introduza otra, por favor.");
			
		} while(!esUnica);
		
		if(matricula.contentEquals("auto")) { // Matricula aleatoria, también será única
			do {
				matricula = rg.strR_Matricula();
				esUnica = taller.mtrIsUnica(matricula);
			} while(!esUnica);
		}
		
		// Atributos que pueden variar (durante la generación aleatoria)
		String marca = VarUtils.pedirString("Marca");
		String modelo = VarUtils.pedirString("Modelo");
		
		// Atributos que no varian (durante la generación aleatoria)
		String color = VarUtils.pedirString("Color");		
		if(color.contentEquals("auto")) // Color aleatorio (si se escribe "auto")
			color = rg.strR_Color();
		
		double velocidadMaxima = VarUtils.pedirDouble("Velocidad Máxima (0 para valor aleatorio)");	
		if(velocidadMaxima == 0)
			velocidadMaxima = rg.doubleR_VMax(); // Vekicudad Máxima aleatoria (si se escribe "0")
		
		String telefonoOwner = Integer.toString(VarUtils.pedirInt("Telefono del Dueño (0 para valor aleatorio)"));		
		if(telefonoOwner.contentEquals("0"))
			telefonoOwner = rg.strR_Tlfmovil(); // Tlf aleatorio (si se escribe "0")
		
		if (op.equals("1")) { // Si es un coche ------------------------------------------------
			
			if(marca.contentEquals("auto")) // Marca aleatoria de coche (si se escribe "auto")
				marca = rg.strR_MarcaCoche();
			if(modelo.contentEquals("auto")) // Modelo aleatorio de coche (si se escribe "auto")
				modelo = rg.strR_CocheModel();
			
			taller.insertarNuevoVehiculoAveriado(new Coche(color, matricula, marca, modelo, velocidadMaxima, telefonoOwner));
			System.out.println("Vehículo añadido: " + taller.getUltimoVehiculoAveriadoInsertado());
			
		} else if (op.equals("2")) { // Si es una motocicleta ----------------------------------
			
			if(marca.contentEquals("auto")) // Marca aleatoria de motocicleta (si se escribe "auto")
				marca = rg.strR_MarcaMotocicleta();
			if(modelo.contentEquals("auto")) // Modelo aleatorio de motocicleta (si se escribe "auto")
				modelo = rg.strR_MotocicletaModel();
			
			String marcaEscape = VarUtils.pedirString("Marca de Escape");
			if(marcaEscape.equals("auto")) // Marca de Escpae aleatoria (si se escribe "auto")
				marcaEscape = rg.strR_MarcaEscape();
			taller.insertarNuevoVehiculoAveriado(
					new Motocicleta(color, matricula, marca, modelo, velocidadMaxima, telefonoOwner, marcaEscape));
			System.out.println("Vehículo añadido: " + taller.getUltimoVehiculoAveriadoInsertado());
			
		} else { // Si es un ciclomotor --------------------------------------------------------
			if(marca.contentEquals("auto")) // Marca aleatoria de ciclomotor
				marca = rg.strR_MarcaCiclomotor();
			if(modelo.contentEquals("auto")) // Modelo aleatorio de ciclomotor
				modelo = rg.strR_CiclomotorModel();
			
			// Se pasa el nuevo vehículo al taller
			taller.insertarNuevoVehiculoAveriado(new Ciclomotor(color, matricula, marca, modelo, velocidadMaxima, telefonoOwner));
			System.out.println("Vehículo añadido: " + taller.getUltimoVehiculoAveriadoInsertado());
		}
		br();
	}
	
	/**
	 * Método que muestra la lista de vehículos averiados y, posteriormente, pide
	 * una matrícula.
	 * 
	 * Esta matricula se la pasará al taller para que empiece el proceso de
	 * reparación del vehículo.
	 */
	public void subMenuRepararVehiculo() {
		
		String matricula="";

		if(taller.checkVehiculosAveriados()) { // Solo si hay vehículos averiados procede
			
			taller.listarVAveriados();
			br();
			
			System.out.print("Por favor escriba su matrícula: ");
			
			do {
				matricula = sc.nextLine(); // Obtiene la matrícula
				if (matricula.isBlank())
					System.out.println("No has introducido nada, vuelve a intentarlo.");
			} while (matricula.isBlank());
			
			taller.reparaVehiculo(matricula); // Se pasa la matrícula la taller
		} else
			System.out.println("No hay vehiculos en el taller.");
	}

	/**
	 * Método que permite mostrar los vehículos del taller utilizando
	 * tres métodos diferentes.
	 *  - Mostrar todos los vehículos.
	 *  - Mostrar solo los reparados.
	 *  - Mostrar solo los averiados.
	 */
	public void subMenuListarVehiculos() {
		
		String strOpcion = "";
		if(taller.checkVehiculos()) { // Solo si hay vehículos procede
			do {
				System.out.println("Las opciones que tenemos son:\n"
						+ "1. Lista de Vehículos.\n"
						+ "2. Lista de Vehículos averiados.\n"
						+ "3. Lista de Vehículos reparados.\n"
						+ "4. Volver al Menu.");
				br();
				
				System.out.print("Opción elegida: ");
				strOpcion = sc.nextLine();
				
				switch (strOpcion) {
				case "1":
					br();
					taller.listarVehiculos();
					br();
					break;
				case "2":
					br();
					taller.listarVAveriados();
					br();
					break;
				case "3":
					br();
					taller.listarVReparados();
					br();
					break;
				case "4": // Case necesario para que simplemente continúe
					break;
				default:
					br();
					System.out.println("Opción no válida.");
					br();
					break;
				}
			} while (!strOpcion.equals("4"));
		} else
			System.out.println("No hay vehículos en el taller.");
		
	}

	/**
	 * Método que muestra todos los vehículos del taller y, posteriormente, pide una
	 * matrícula.
	 * 
	 * Esta matricula se la pasará al taller para que empiece el proceso de entrega
	 * del vehículo.
	 */
	public void subMenuEntregarVehiculo() {
		
		String matricula = "";
		
		// No restrinjo el método a vehículos reparados para poder
		// mostrar el mensaje "Su vehículo no está reparado, vuelva mañana por favor"
		if(taller.checkVehiculos()) { // Solo si hay vehiculos
			
			taller.listarVehiculos();
			br();
			
			System.out.print("Por favor escriba su matrícula: ");
			
			do {
			 matricula = sc.nextLine();
			 if(matricula.isBlank()) 
			   System.out.println("No has introducido nada, vuelve a intentarlo.");
			}while(matricula.isBlank());
			
			taller.entregarVehiculo(matricula);
			
		} else
			System.out.println("No hay vehículos en el taller.");
	}
	
	/**
	 * Método que muestra todas las motocicletas del taller y, posteriormente, pide
	 * una matrícula y la nueva marca del escape.
	 * 
	 * Esta matricula se la pasará al taller para que empiece el proceso de cambio
	 * de escape de la motocicleta.
	 */
	public void subMenuCambiarEscape() {

		String mtrMoto;
		String marcaEscape;

		if(taller.checkMotocicletas()) { // Solo si hay motocicletas procede
			
			taller.listarMotocicletas();
			br();
			
			do { // Pide la matrícula
				System.out.print("Matrícula: ");
				mtrMoto = sc.nextLine();
				if (mtrMoto.isBlank())
					if (mtrMoto.isBlank())
						System.out.println("Introduzca una matrícula, por favor.");
			} while (mtrMoto.isBlank());
			
			do { // Pide la nueva marca de escape
				System.out.print("Marca del escape: ");
				marcaEscape = sc.nextLine();
				if (marcaEscape.isBlank())
					System.out.println("Introduzca una marca de escape, por favor.");
			} while (marcaEscape.isBlank());
			br();
			taller.cambiarExcape(mtrMoto, marcaEscape); // Pasa estos datos al taller
			
		} else // Si no hay motocicletas lo notifica
			System.out.println("No hay motocicletas en el taller.");
		
	}
}
