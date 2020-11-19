package model;

public class CuentaCorriente {

	private int numeroCuenta;
	private double saldo;
	
	public CuentaCorriente(int numeroCuenta, double saldo) {
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
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
	
	public int getNumeroCuenta() {
		return numeroCuenta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
}
