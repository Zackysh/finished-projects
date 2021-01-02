package utils;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

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
