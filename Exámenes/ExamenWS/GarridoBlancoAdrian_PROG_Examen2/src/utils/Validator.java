package utils;

public class Validator {
	public static boolean isInt(String strInt) {
		boolean isInt = false;		
		try {
			Integer.parseInt(strInt);
			isInt = true;
		} catch(Exception e) {
			isInt = false;
		}		
		return isInt;
	}
	
	public static boolean isDouble(String strDouble) {
		try {
			Double.parseDouble(strDouble);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
