package main;

import utils.StringUtils;

public class TestingUtils {
	public static void main(String[] args) {
		for (int i = 0; i < 200; i++) {
			String nombre = StringUtils.nombreAleatorio();
			System.out.println(nombre);
		}
	}
}
