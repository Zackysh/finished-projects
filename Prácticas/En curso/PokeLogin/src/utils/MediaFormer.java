package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
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
	
	public static ImageIcon getImageIconFitLabelURL(JLabel label, String url) {
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Image redimensionedImg = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(redimensionedImg);
		return imageIcon;
	}
	
	public static Boolean testImage(String url){  
        try {  
            BufferedImage image = ImageIO.read(new URL(url));  
            //BufferedImage image = ImageIO.read(new URL("http://someimage.jpg"));  
            if(image != null){  
                return true;
            } else{
                return false;
            }

        } catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            System.err.println("URL error with image");  
            e.printStackTrace();
            return false;
        } catch (IOException e) {  
            System.err.println("IO error with image");  
            // TODO Auto-generated catch block  
            e.printStackTrace();
            return false;  
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
