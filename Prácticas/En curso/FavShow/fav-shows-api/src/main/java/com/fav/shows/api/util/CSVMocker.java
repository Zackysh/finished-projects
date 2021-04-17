package com.fav.shows.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.core.io.ResourceLoader;

import com.fav.shows.api.entity.Show;

public class CSVMocker {

	private static BufferedReader br;
	static ResourceLoader resourceLoader;

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
	 * @param src     Path of CSV
	 * @param headers Number of CSV headers
	 * @param regex   Regular expression to extract info from CSV
	 */
	public ArrayList<Show> mockShowsCSV(String src, int headers, String regex) {

		File file = new File(src);
		System.out.println(file.getName());

		ArrayList<Show> showsFromCSV = new ArrayList<Show>(); // our Show List
		try {
			br = getBReaderResource(src);
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
			return showsFromCSV;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

	private BufferedReader getBReaderResource(String src) {
		return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(src)));
	}

}
