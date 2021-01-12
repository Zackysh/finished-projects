package ddd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class adas extends JFrame {

	private JPanel contentPane;
	private JPanel subPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adas frame = new adas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public adas() {
		setBounds(0, 0, 300, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		initialize();
	}
	
	public void initialize() {
		subPane = new JPanel();
		subPane.setPreferredSize(new Dimension(250, 250));
		subPane.setAutoscrolls(true);
		
		JScrollPane prueba = new JScrollPane(subPane);
	}

}
