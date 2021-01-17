package snippet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import models.PokeType;

public class JScrollablePane extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public JScrollablePane(ArrayList<PokeType> list) {
		
		setTitle("JScrollablePanel Test");
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		JPanel panel = createPanel(list);
		panel.setBounds(40, 40, 200, 100);
		panel.add(BorderLayout.CENTER, new JScrollPane());
		
		
		setContentPane(contentPane);
		setVisible(true);
		
		
		
	}

	public static JPanel createPanel(ArrayList<PokeType> list) {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 4, 10, 10));

		int cont = 0;
		int posX = 10;
		for (PokeType type : list) {

			JButton typeLabel = new JButton();
			typeLabel.setEnabled(false);
			typeLabel.setUI(new MetalButtonUI() {
				protected Color getDisabledTextColor() {
					return Color.WHITE;
				}
			});
			typeLabel.setFont(new Font("Flexo-Light", Font.BOLD, 16));
			typeLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			typeLabel.setText(type.getName());
			typeLabel.setBackground(type.getColor());

			panel.add(typeLabel);

			cont++;
		}
		return panel;
	}

	public static void main(String[] args) {
		ArrayList<PokeType> list = new ArrayList<>();
		list.add(new PokeType(1, "Fuego", Color.red));
		list.add(new PokeType(1, "Fuego", Color.red));
		list.add(new PokeType(1, "Fuego", Color.red));
		list.add(new PokeType(1, "Fuego", Color.red));
		list.add(new PokeType(1, "Fuego", Color.red));
		new JScrollablePane(list);
	}
}