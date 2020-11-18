 package model;

public class Persona {

	private String nombre, apellidos, dni;
	private double sueldo;
	CuentaCorriente cuenta;
	
	// Construir persona y asignarle una cuenta
	public Persona(String nombre, String apellidos, String dni, double sueldo, CuentaCorriente cuenta) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.sueldo = sueldo;
		this.cuenta = cuenta;
	}
	
	public void cobrarSueldo() {
		this.cuenta.sumarCantidad(this.sueldo);
	}
	
	public void sacarPasta(double cantidad) {
		try {
			this.cuenta.restarCantidad(cantidad);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public CuentaCorriente getCuentaCorriente() {
		return cuenta;
	}	
	
	public void cambiarSueldo(double nuevoSueldo) {
		this.sueldo = nuevoSueldo;
	}
	
	public void setCuentaCorriente(CuentaCorriente cuenta) {
		this.cuenta = cuenta;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public double getSueldo() {
		return sueldo;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", sueldo=" + sueldo + ", saldo=" + String.format("%.0f", this.cuenta.getSaldo()) + "]";
	}
	
}
