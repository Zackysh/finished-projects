package mainapp;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame implements MouseListener {

	private JPanel panel;
	private JLabel buttonLeft;

	private JLabel labelFormer1;
	private JLabel labelFormer2;


	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
		initializeUI();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(0, 0, 750, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = (JPanel) getContentPane();
		panel.setLayout(null);
	}

	private void initializeUI() {


		labelFormer1 = new JLabel();
		labelFormer1.setBounds(0, 0, 372, 50);
		labelFormer1.addMouseListener(this);
		panel.add(labelFormer1);
		
		labelFormer2 = new JLabel();
		labelFormer2.setBounds(0, 50, 125, 30);
		labelFormer2.addMouseListener(this);
		panel.add(labelFormer2);
		
		
		buttonLeft = new JLabel(new ImageIcon("images\\buttonLeft.png"));
		buttonLeft.setBounds(0, 0, 372, 80);
		buttonLeft.setForeground(Color.black);
		buttonLeft.addMouseListener(this);
		panel.add(buttonLeft);
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

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == labelFormer1 || e.getSource() == labelFormer2)
			buttonLeft.setIcon(new ImageIcon("images\\buttonLeft2.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == labelFormer1 || e.getSource() == labelFormer2)
			buttonLeft.setIcon(new ImageIcon("images\\buttonLeft.png"));
	}
}
