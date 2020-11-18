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
	
	public void restarCantidad(double cantidad) throws Exception {
		if(cantidad > this.saldo) throw new Exception("No puedes sacar más saldo del disponible.");
		else this.saldo -= cantidad;
	}
	
	public int getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	@Override
	public String toString() {
		return "CuentaCorriente [numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + "]";
	}
	
}
