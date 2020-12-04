package mainapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Clean extends JFrame {

	private JPanel contentPane;
	private JTextField tfA;
	private JTextField tfB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clean frame = new Clean();
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
	
	public Clean() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel() {
			
			   private static final int RECT_X = 70;
			   private static final int RECT_Y = 60;
			   private static final int RECT_WIDTH = 270;
			   private static final int RECT_HEIGHT = 100;
			   
			   private static final int RECT2_X = 70;
			   private static final int RECT2_Y = 20;
			   private static final int RECT2_WIDTH = 270;
			   private static final int RECT2_HEIGHT = 20;

			   @Override
			   protected void paintComponent(Graphics g) {
			      super.paintComponent(g);
			      // draw the rectangle here
			      g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
			      g.drawRect(RECT2_X, RECT2_Y, RECT2_WIDTH, RECT2_HEIGHT);
			   }

			   @Override
			   public Dimension getPreferredSize() {
			      // so that our GUI is big enough
			      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
			   }
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Salir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		btnNewButton_1.setBounds(239, 180, 81, 37);
		contentPane.add(btnNewButton_1);
		
		tfA = new JTextField();
		tfA.setBounds(166, 66, 86, 20);
		contentPane.add(tfA);
		tfA.setColumns(10);
		
		tfB = new JTextField();
		tfB.setColumns(10);
		tfB.setBounds(166, 98, 86, 20);
		contentPane.add(tfB);
		
		JLabel lblNewLabel = new JLabel("A");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(110, 68, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblB = new JLabel("B");
		lblB.setHorizontalAlignment(SwingConstants.CENTER);
		lblB.setFont(new Font("Arial", Font.BOLD, 14));
		lblB.setBounds(110, 100, 46, 14);
		contentPane.add(lblB);
		
		JLabel lblResultado = new JLabel("Resultado:");
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
		lblResultado.setBounds(75, 140, 81, 14);
		contentPane.add(lblResultado);
		
		JLabel lblR = new JLabel("");
		lblR.setBackground(Color.GRAY);
		lblR.setHorizontalAlignment(SwingConstants.CENTER);
		lblR.setFont(new Font("Arial", Font.BOLD, 14));
		lblR.setBounds(182, 141, 46, 14);
		lblR.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(lblR);
		
		JButton btnNewButton = new JButton("Sumar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(tfA != null && tfA.getText().trim() != "" && tfB != null && tfB.getText().trim() != "") {
						lblR.setBounds(182, 141, 46, 14);
						int A = Integer.parseInt(tfA.getText().trim());
						int B = Integer.parseInt(tfB.getText().trim());
						String C = Integer.toString(A+B);
						lblR.setText(C);
					}
					lblR.setBounds(182, 141, 46, 14);
				} catch (Exception ex) {
					ex.fillInStackTrace();
					lblR.setBounds(lblR.getBounds().x,lblR.getBounds().y,156,14);
					lblR.setText("Debes introducir números");
				}
				
			}
		});
		btnNewButton.setBounds(97, 180, 74, 37);
		contentPane.add(btnNewButton);
		
		JLabel lblSumador = new JLabel("Sumador");
		lblSumador.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumador.setFont(new Font("Arial", Font.BOLD, 17));
		lblSumador.setBounds(166, 22, 81, 14);
		contentPane.add(lblSumador);
		
		
		
	}
}
