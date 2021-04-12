package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import models.Show;

public class MainApp {
	
	private final String SOURCE_CSV_LOC = "Assets/netflix_titles.csv";
	private final String DUMP_LOC = "Assets/userFavs.txt";
	
	private BufferedReader br;
	private FileWriter fw;
	
	private String dumpLocation;
	private ArrayList<Show> favs;
	private ArrayList<Show> dumped;
	
	public MainApp() {
		favs = new ArrayList<Show>();
		dumped = new ArrayList<Show>();
	}

	/**
	 * Remove dangerous characters from given String[]. Format (add line braks for
	 * example) each String to print a fancy list.
	 * 
	 * @param fields
	 * @return String[] with fancy format
	 */
	private static String[] formatLineToPrint(String[] fields) {

		String result[] = new String[fields.length];

		for (int i = 0; i < result.length; i++) {
			if (fields[i] != null) {
				result[i] = fields[i].replaceAll("/", "").trim();
				result[i] = "\n   " + result[i];
				if (i == fields.length - 1)
					result[i] = result[i].toString().substring(0, result[i].length() - 1) + "\n";
			}
		}
		return result;
	}

	/**
	 * Removes line braks from each String on given String[].
	 * 
	 * @param fields
	 * @return line breaks removed from String[]
	 */
	private static String[] formatLineToMooc(String[] fields) {

		String result[] = new String[fields.length];
		for (int i = 0; i < result.length; i++) {
			if (fields[i] != null)
				result[i] = fields[i].replaceAll("/n", "").trim();
		}
		return result;
	}

	public void moocCSV(String[] args) {
		br = null; // declare our reader
		FileWriter fw = null; // declare our writer
		ArrayList<Show> showsFromCSV = new ArrayList<Show>(); // our Show List
		try {
			fw = new FileWriter("dumped.txt"); // define our dump file
			// read headder
			br = new BufferedReader(new FileReader("netflix_titles.csv"));
			// read headder
			br.readLine();
			br.readLine();
			// read first record
			String line = br.readLine();
			// for every record ...
			while (line != null) {
				String[] mooc = new String[12]; // use to fit Show() constructor
				String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				for (int i = 0; i < fields.length; i++)
					mooc[i] = fields[i];
				mooc = formatLineToPrint(mooc); // with \n, etc
				// Print to console
				System.out.println(Arrays.toString(mooc));
				System.out.println("=====================================");
				mooc = formatLineToMooc(mooc); // withoud \n
				// Write into file
				fw.write(Arrays.toString(mooc) + '\n');
				// Add to ArrayList
				showsFromCSV.add(new Show(mooc[0] != null ? mooc[0] : null, mooc[1] != null ? mooc[1] : null,
						mooc[2] != null ? mooc[2] : null, mooc[3] != null ? mooc[3] : null,
						mooc[4] != null ? mooc[4] : null, mooc[5] != null ? mooc[5] : null,
						mooc[6] != null ? mooc[6] : null, mooc[7] != null ? mooc[7] : null,
						mooc[8] != null ? mooc[8] : null, mooc[9] != null ? mooc[9] : null,
						mooc[10] != null ? mooc[10] : null, mooc[11] != null ? mooc[11] : null));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // close flow
			if (br != null && fw != null) {
				try {
					fw.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
