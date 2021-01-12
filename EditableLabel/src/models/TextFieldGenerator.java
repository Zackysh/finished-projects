package models;

import java.awt.Color;

import javax.swing.JTextField;

public class TextFieldGenerator {
	
	
	public static JTextField createLabelLikeTF(String text) {
		JTextField textField = new JTextField(text);
		textField.setBackground(new Color(0, 0, 0, 0));
		return textField;		
	}

}
