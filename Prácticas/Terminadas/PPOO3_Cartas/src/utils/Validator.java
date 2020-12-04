package utils;

public class Validator {
	public static boolean validateStrInt(String strInt) {
		boolean isInt = false;		
		try {
			Integer.parseInt(strInt);
			isInt = true;
		} catch(Exception e) {
			isInt = false;
		}		
		return isInt;
	}
}
