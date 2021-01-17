package snippet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Snippet extends JFrame {
	
	public Snippet() {
		
		
		
		int tableW = 300;
		int tableH = 300;
		
		// JPANEL WITH NULL LAYOUT
		JPanelForNullLayoutScroll container = new JPanelForNullLayoutScroll();
		container.setLayout(null);
		container.setVisible(true);
		container.setEnabled(true);
		container.setBounds(0, 0, tableW, tableH);
		setContentPane(container);
		
		// SCROLLER
		JScrollPane scrollPane = new JScrollPane(container);
		scrollPane.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, tableW, tableH);
		
		
		setVisible(true);
	}
	
	public static void main (String[] argd) {
		new Snippet();
	}
}

