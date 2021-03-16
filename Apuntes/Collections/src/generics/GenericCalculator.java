package generics;

public class GenericCalculator<T extends Number> {
	
	private T number;
	
	public GenericCalculator(T number) {
		this.number = number;
	}
	
	public Number divideHalf() {
		return number.doubleValue() / 2;
	}
	
}
