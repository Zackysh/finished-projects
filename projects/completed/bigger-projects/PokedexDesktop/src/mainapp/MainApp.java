package mainapp;

import javax.swing.JOptionPane;

import views.LoginView;

/**
 * This class launch whole application.
 * 
 * @author AdriGB
 *
 */
public class MainApp {

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null,
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>"
						+ "<p>If you can't access to PokeDB you might want to change User and Password <br>"
						+ "from AbstractDAO connection definition, you can try root with blank password or any other <br>"
						+ "user you have registered on your localhost.</p>" + "<html>",
				"Hello", JOptionPane.WARNING_MESSAGE);

		new LoginView();
	}
}
