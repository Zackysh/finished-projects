package views;

import java.awt.EventQueue;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PokedexView extends JFrame implements ActionListener, MouseListener {

	private JPanel contentPane;
	
	private JLabel lbl_buttonRight_Off;
	private JLabel background;
	private JLabel lbl_buttonLeft_Off;
	
	private List<Polygon> shapes;
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Testing

		// Pane initialization
		contentPane = new JPanel();
		contentPane.setLayout(null);
		
		// Frame initialization
		setTitle("Pokedex");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imges\\icon.png"));
		setBounds(0, 0, 953, 659);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		initializeUIComponentes();
		setVisible(true);
	}
	
	
	public void initializeUIComponentes() {
		
		// SHAPES
		shapes = new ArrayList<>();
		
		Shape shape;
		Polygon poly = new Polygon();
		poly.addPoint(0, 0);
		poly.addPoint(0, 2);
		poly.addPoint(2, 0);
		poly.addPoint(2, 2);
		shape = poly;
		contentPane.add(poly)
		
		// lbl_buttonLeft Off
		lbl_buttonLeft_Off = new JLabel(new ImageIcon("imges\\buttonLeft.png"));
		lbl_buttonLeft_Off.setBounds(107, 0, 372, 79);
		lbl_buttonLeft_Off.addMouseListener(this);
		contentPane.add(lbl_buttonLeft_Off);
		// lbl_buttonLeft Off
		lbl_buttonRight_Off = new JLabel(new ImageIcon("imges\\buttonRight.png"));
		lbl_buttonRight_Off.setBounds(479, 0, 372, 79);
		lbl_buttonRight_Off.addMouseListener(this);
		contentPane.add(lbl_buttonRight_Off);
		
		// Set JLabel as background to the contentPane
		// It must be done the latter, otherwise it would be proposed and cover other
		// elements
		background = new JLabel(new ImageIcon("imges\\pokedexBack.png"));
		background.setBounds(-5, -13, 953, 659);
		contentPane.add(background);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == lbl_buttonLeft_Off)
			lbl_buttonLeft_Off.setIcon(new ImageIcon("imges\\\\buttonLeft2.png"));		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == lbl_buttonLeft_Off)
			lbl_buttonLeft_Off.setIcon(new ImageIcon("imges\\\\buttonLeft.png"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Create the application.
	 */
	public PokedexView() {
		initialize();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PokedexView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
