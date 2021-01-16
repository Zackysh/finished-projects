package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MediaFormer {

	/**
	 * Method that given a JLabel and a String returns an ImageIcon. This ImageIcon
	 * will snap to the given JLabel and search for the source image.
	 * 
	 * With this you can resize a JLabel and its image will always be well formed.
	 * 
	 * @param label  JLabel that returned Icon will fit.
	 * @param source Source of image for IconImage.
	 * @return imageIcon adapted to given label.
	 */
	public static ImageIcon getImageIconFitLabel(JLabel label, String source) {

		Image img = new ImageIcon(source).getImage();

		Image redimensionedImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(redimensionedImg);
		return imageIcon;
	}

	public static ImageIcon getImageIconFitLabelURL(JLabel label, String url) {

		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(url));
		} catch (MalformedURLException e) {
			System.err
					.println("URL error with image: line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		} catch (IOException e) {
			System.err
					.println("URL error with image: line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
		}

		Image redimensionedImg = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(redimensionedImg);
		return imageIcon;
	}

	public static ImageIcon getImageIconFitLabelImage(JLabel label, Image image) {
		Image redimensionedImg = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(redimensionedImg);
		return imageIcon;
	}

	public static void downloadToFile(String url, File dest) {
		URL urlU = null;
		try {
			urlU = new URL(url);
			BufferedImage img = ImageIO.read(urlU);
			ImageIO.write(img, "png", dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Boolean testImage(String url) {
		try {
			BufferedImage image = ImageIO.read(new URL(url));
			if (image != null)
				return true;
			else
				return false;
		} catch (MalformedURLException e) {
			System.err
					.println("URL error with image: line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			return false;
		} catch (IOException e) {
			System.err.println("IO error with image");
			return false;
		}
	}

	/**
	 * Method that allow user to chose a file. The file is read. And it tries to
	 * create an image from it. Finally, this image is resized and placed into given
	 * JLabel as its Icon.
	 * 
	 * @param extensions The accepted file name extensions
	 * @param label      JLabel which will receive final image
	 * @param dest       New file name (directory + name.extension)
	 */
	public static File choseFileFromSystem(String... extensions) {
		if (extensions == null || extensions.length == 0) { // If extensions is empty
			throw new IllegalArgumentException("Extensions must be non-null and not empty");
		} else {
			final JFileChooser fc = new JFileChooser(); // Chose file
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", extensions);
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File origin = fc.getSelectedFile(); // Selected origin file
				return origin;
			}
		}
		return null;
	}

	public static void copyFile(File origin, File dest) throws FileNotFoundException, IOException {
		try (InputStream in = new BufferedInputStream(new FileInputStream(origin));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {

			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}

	}

	/**
	 * Overload to work with JButtons.
	 * 
	 * @param button Source button.
	 * @param source Source of desired image.
	 * @return imageIcon adapted to given button.
	 */
	public static ImageIcon getImageIconFitLabel(JButton button, String source) {

		Image img = new ImageIcon(source).getImage();

		Image redimensionedImg = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(redimensionedImg);
		return imageIcon;
	}
}
