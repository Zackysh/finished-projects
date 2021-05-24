package ejercicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Ejercicio4 {

  public static void main(String[] args) {
    printFile("cadenitas4.txt");
  }

  public static void printFile(String fileName) {
    List<String> list = new ArrayList<String>();
    Scanner sc;

    try {
      sc = new Scanner(new File(fileName));
      while (sc.hasNextLine())
        list.addAll(Arrays.asList(sc.nextLine().split("\\*")));
      list.forEach(str -> System.out.println(str));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
