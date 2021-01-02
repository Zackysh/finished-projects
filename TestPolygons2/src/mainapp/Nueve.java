package mainapp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;

public class Nueve extends JFrame {

	private JPanel contentPane;
	JButton but;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Nueve frame = new Nueve();
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
	public Nueve() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		but = new JButton("Planta");
		but.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		but.setBounds(79, 70, 145, 40);
		but.setBackground(Color.green);
		but.setEnabled(false);
		but.setUI(new MetalButtonUI() {
		    protected Color getDisabledTextColor() {
		        return Color.BLACK;
		    }
		});
		but.setBorder(null);
		contentPane.add(but);
	}
}
