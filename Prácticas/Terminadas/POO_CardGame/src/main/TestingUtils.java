package main;

import utils.StringUtils;
import utils.Validator;

public class TestingUtils {
	public static void main(String[] args) {

		// nombreAleatorio()
		for (int i = 0; i < 10; i++) {
			String nombre = StringUtils.nombreAleatorio();
			System.out.println(nombre);
		}
		
		StringUtils.br(); // br()
		
		// normalizarString()
		String nonNormalized = "hOLa CaRaCoLA";
		String normalized = StringUtils.normalizarString(nonNormalized);
		System.out.println(normalized);
		
		// Validator - ValidateStrInt
		String valid = "1";
		String nonValid = "a";
		
		StringUtils.br();
		
		boolean validValidation = Validator.validateStrInt(valid);
		System.out.println(valid + " | " + validValidation);

		StringUtils.br();
		
		boolean nonValidValidation = Validator.validateStrInt(nonValid);
		System.out.println(nonValid + " | " + nonValidValidation);
	}
}
