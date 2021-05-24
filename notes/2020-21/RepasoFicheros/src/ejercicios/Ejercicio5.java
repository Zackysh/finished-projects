package ejercicios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio5 {
  public static void main(String[] args) {
    sortFileLines("ordenarlines.txt");
  }

  public static void sortFileLines(String fileName) {
    List<String> lines = new ArrayList<String>();
    File file = new File(fileName);

    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        lines.add(sc.nextLine());
      }
      lines.sort((a, b) -> a.compareTo(b));
      System.out.println(lines);

      FileWriter fw = new FileWriter(file);
      for (String string : lines)
        fw.write(string + "\n");
      sc.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
