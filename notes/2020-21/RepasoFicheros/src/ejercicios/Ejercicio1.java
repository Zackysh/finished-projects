package ejercicios;

import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio1 {

  public static void main(String[] args) {

    try {
      FileWriter fileWriter;
      fileWriter = new FileWriter("numeritos.txt");
      for (int i = 0; i < 100; i++)
        fileWriter.write(Integer.toString(i));
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
