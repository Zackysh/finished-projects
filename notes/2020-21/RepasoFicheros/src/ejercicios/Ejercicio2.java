package ejercicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ejercicio2 {

  public static void main(String[] args) {

    sumarEnterosDeArchivo("numeritos2.txt");

  }

  public static void sumarEnterosDeArchivo(String nombre) {
    Scanner sc;
    String lines = "";

    try {
      sc = new Scanner(new File(nombre));
      while (sc.hasNextLine())
        lines += sc.nextLine();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    int sum = 0;
    for (int i = 0; i < lines.length(); i++) {
      if (Character.isDigit(lines.charAt(i)))
        sum += Character.getNumericValue(lines.charAt(i));
    }
    
    System.out.println(sum);

  }
}
