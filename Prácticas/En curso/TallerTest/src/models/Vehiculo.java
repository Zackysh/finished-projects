package models;

import Interfaces.Arrancable;
import Interfaces.Movible;

public abstract class Vehiculo implements Movible, Arrancable {
	// Son los atributos que debe tener un Vehiculo.
	protected String color;
	protected String matricula;
	protected String marca;
	protected String modelo;
	protected double velocidadActual;
	protected double velocidadMaxima;
	protected String telefonoOwner;
	
	/**
	 * Es el constructor de los atributos de Vehiculo. La velocidad actual se define
	 * en el constructor de Vehiculo ya que al principio siempre va a ser la misma
	 * qyue sera 0, el resto de atributos se incluyen en el constructor que hereda
	 * del padre y no se definen, para que cuando herede a sus hijos estos la
	 * definan.
	 * 
	 * @param color
	 * @param matricula
	 * @param marca
	 * @param modelo
	 * @param velocidadMaxima
	 * @param telefonoOwner
	 */
	public Vehiculo(String color, String matricula, String marca, String modelo, double velocidadMaxima,
			String telefonoOwner) {
		super();
		this.color = color;
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.velocidadActual = 0;
		this.velocidadMaxima = velocidadMaxima;
		this.telefonoOwner = telefonoOwner;
	}

	/**
     * Método que agiliza le insercción de saltos de línea y facilita la lectura del
     * código.
     */
	public void br() {
		System.out.println();
	}
	
	
	// Estos son los setter y getter de los atributos de Vehiculo.
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getVelocidadActual() {
		return velocidadActual;
	}

	public void setVelocidadActual(double velocidadActual) {
		this.velocidadActual = velocidadActual;
	}

	public double getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public void setVelocidadMaxima(double velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	public String getTelefonoDueño() {
		return telefonoOwner;
	}

	public void setTelefonoDueño(String telefonoDueño) {
		this.telefonoOwner = telefonoDueño;
	}

	// Estos de aquí son los metodos que implementamos de las interfaces
	/**
	 * Esta interfaz que Vehículo implementa y heredara a sus hijos trata de mostrar
	 * un mensaje diciende que el vehiculo esta arrancado
	 */
	@Override
	public void arrancar() {
		System.out.println("El Vehículo esta arrancado");
	}

	/**
	 * Esta interfaz que Vehículo implementa y heredara a sust hijos rata de mostrar
	 * un mensaje diciende que el vehiculo esta parado.
	 */
	@Override
	public void parar() {
		System.out.println("El Vehículo esta parado");
	}

	/**
	 * Esta interfaz que Vehículo implementa y heredara a sust hijos lo que hace
	 * mostrar la velocidadActual del vehículo luego recibe un numero por teclado
	 * para que aumente su velocidad(acelerar) y este se le sumara a la
	 * velocidadActual mostrando la velocidad que posee el vehículo ahora ,lo
	 * velocidadActual de todo vehículo al principio es 0.
	 */
	@Override
	public void acelerar(double v) {
		System.out.println("La velocidad actual es de " + velocidadActual);
		this.velocidadActual += v;
		br();
		System.out.println("Su nueva velocidad actual es de " + velocidadActual);
		br();
		if (velocidadActual < 0)
			System.out.println("Está yendo marcha atrás :)");
		br();
	}

	/**
	 * Esta interfaz que Vehículo implementa y heredara a sust hijos lo que hace
	 * mostrar la velocidadActual del vehículo luego recibe un numero por teclado
	 * para que disminuya su velocidad(frenar) y este se le restara a la
	 * velocidadActual mostrando la velocidad que posee el vehículo ahora ,lo
	 * velocidadActual de todo vehículo al principio es 0 ,tambien indicara un
	 * mensaje diciendo vas hacia atras en el caso de que la velocidadActual sea
	 * menor a 0 .
	 */
	@Override
	public void frenar(double v) {
		System.out.println("La velocidad actual es de " + velocidadActual);
		this.velocidadActual -= v;
		br();
		System.out.println("Su nueva velocidad actual es de " + velocidadActual);

		if (velocidadActual < 0)
			System.out.println("Está yendo marcha atrás :)");
		br();
	}

	/**
	 * Esta interfaz que Vehículo implementa y heredara a sust hijos lo que permite
	 * es cambiar la velocidadActual a 0 da igual que velocidad tenga con esta
	 * funcion pasa a 0 y mostrara un mensaje de que el vehículo a frenado hasta
	 * pararse.
	 */
	@Override
	public void frenarHastaParar() {
		this.velocidadActual = 0;
		System.out.println("El vehículo a frenado hasta pararse ");
		br();
	}

}
