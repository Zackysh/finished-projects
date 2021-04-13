package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainApp {
	
	public static void main(String[] args) {
		try {
			InputStream in = new URL("https://pbs.twimg.com/profile_images/456197684177543168/s3Nm6qzc_400x400.jpeg").openStream();
			FileOutputStream out = new FileOutputStream("pipo.jpeg");
			
			byte[] buffer = new byte[512];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
			
		} catch (FileNotFoundException e) {
			e.fillInStackTrace();
		} catch (IOException o) {
			o.fillInStackTrace();
		}
	}
}
