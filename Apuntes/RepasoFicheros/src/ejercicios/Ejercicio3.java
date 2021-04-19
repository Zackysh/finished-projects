package ejercicios;

import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio3 {

  public static void main(String[] args) {
    String[] arr = { "pipo", "es", "el", "mejor" };
    arratToFile(arr, "array3.txt");
  }

  public static void arratToFile(String[] arr, String fileName) {
    try {
      FileWriter fw = new FileWriter(fileName);
      for (int i = 0; i < arr.length; i++) {
        fw.write(arr[i] + "*");
      }
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
