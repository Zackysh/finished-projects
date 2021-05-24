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
	private static String[] formatLineToMock(String[] fields) {

		String result[] = new String[fields.length];
		for (int i = 0; i < result.length; i++) {
			if (fields[i] != null)
				result[i] = fields[i].replaceAll("/n", "").trim();
		}
		return result;
	}

	/**
	 * Method that read CSV fields and mock it to an Show ArrayList.
	 * 
	 * @param src Path of CSV
	 * @param headers Number of CSV headers
	 * @param regex Regular expression to extract info from CSV
	 */
	public void mockShowsCSV(String src, int headers, String regex) {
		ArrayList<Show> showsFromCSV = new ArrayList<Show>(); // our Show List
		try {
			br = new BufferedReader(new FileReader("src"));
			// read header
			for (int i = 0; i < headers; i++) {
				br.readLine();
			}
			// read first record
			String line = br.readLine();
			// for every record ...
			while (line != null) {
				String[] mock = new String[12]; // use to fit Show() constructor
				String[] fields = line.split(regex, -1); // ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1
				for (int i = 0; i < fields.length; i++)
					mock[i] = fields[i];
				mock = formatLineToPrint(mock);
//				System.out.println(mock);
				mock = formatLineToMock(mock);				
				// Add to ArrayList
				showsFromCSV.add(new Show(mock[0] != null ? mock[0] : null, mock[1] != null ? mock[1] : null,
						mock[2] != null ? mock[2] : null, mock[3] != null ? mock[3] : null,
						mock[4] != null ? mock[4] : null, mock[5] != null ? mock[5] : null,
						mock[6] != null ? mock[6] : null, mock[7] != null ? mock[7] : null,
						mock[8] != null ? mock[8] : null, mock[9] != null ? mock[9] : null,
						mock[10] != null ? mock[10] : null, mock[11] != null ? mock[11] : null));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // close flow
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
