package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.TextFieldGenerator;


public class EditableLabel extends JFrame {

	private JPanel contentPane;
	private JTextField textF;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new EditableLabel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EditableLabel() {
		
		// Pane initialization
		contentPane = new JPanel();
		contentPane.setLayout(null);

		// Frame initialization
		setTitle("Prueba");
		setBounds(0, 0, 953, 659);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		
		// Components
		textF = TextFieldGenerator.createLabelLikeTF("Esto es una prueba");
		textF.setBounds(50, 50, 200, 200);
		contentPane.add(textF);
		
		this.setVisible(true);
	}

}
