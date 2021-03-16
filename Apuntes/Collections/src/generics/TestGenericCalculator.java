package generics;

public class TestGenericCalculator {
	
	public static void main(String[] args) {
		short myShort = (short) 3.332;
		int myInt = 3;
		long  myLong = 999999999;
		float myFloat = 3.7f;
		double myDouble = 6.4d;
		GenericCalculator<Short> gcShort = new GenericCalculator<Short>(myShort);
		GenericCalculator<Integer> gcInt = new GenericCalculator<Integer>(myInt);
		GenericCalculator<Long> gcLong = new GenericCalculator<Long>(myLong);
		GenericCalculator<Float> gcFloat = new GenericCalculator<Float>(myFloat);
		GenericCalculator<Double> gcDouble = new GenericCalculator<Double>(myDouble);
		
		System.out.println("Short: " + gcShort.divideHalf());
		System.out.println("Integer: " + gcInt.divideHalf());
		System.out.println("Long: " + gcLong.divideHalf());
		System.out.println("Float: " + gcFloat.divideHalf());
		System.out.println("Double: " + gcDouble.divideHalf());
	}
	
}
