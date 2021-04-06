package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MainApp {

	private static String[] formatLine(String[] fields) {

		String result[] = new String[fields.length];

		for (int i = 0; i < result.length; i++) {	
			result[i] = fields[i].replaceAll("/", "").trim();
			result[i] = "\n   " + result[i];
			if(i == fields.length - 1)
				result[i] = result[i].toString().substring(0, result[i].length() - 1) + "\n";
		}
		return result;
	}

	public static void main(String[] args) {

		BufferedReader br = null; // declare our reader

		try {
			// read headder
			br = new BufferedReader(new FileReader("netflix_titles.csv"));
			br.readLine(); br.readLine();
			// read first registry
			String line = br.readLine();
			
			while (line != null) {
				String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				fields = formatLine(fields);
				System.out.println(Arrays.toString(fields));
				System.out.println("=====================================");
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
