package model;

public class CuentaCorriente {

	private String numeroCuenta;
	private double saldo;
	private Persona titular;
	
	public CuentaCorriente(String numeroCuenta, double saldo, Persona titular) {
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.titular = titular;
	}
	
	public void sumarCantidad(double cantidad) {
		if(cantidad > 0)
		this.saldo += cantidad;
		else System.out.println("Solo valores mayores a cero.");
	}
	
	public void restarCantidad(double cantidad) {
		if(cantidad > 0)
		this.saldo -= cantidad;
		else System.out.println("Solo valores mayores a cero.");
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public Persona getTitular() {
		return titular;
	}
	public void setTitular(Persona titular) {
		this.titular = titular;
	}
	
	@Override
	public String toString() {
		return "CuentaCorriente [numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", titular=" + titular + "]";
	}
	
}
