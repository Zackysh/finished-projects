package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import models.PokeType;

/**
 * TODO
 * @author adrig
 *
 */
public class ScrollPaneGenerator {
	
	public static void scrollablePanelForTypes(ArrayList<PokeType> list, JPanel pane) {
		
		JPanel typeContainer = createPanel(list);
		JScrollPane scroll = new JScrollPane();
		
		scroll.setPreferredSize(new Dimension(400, 56));
		scroll.getViewport().add(typeContainer);
		scroll.setLocation(102, 200);
		pane.removeAll();
		pane.add(scroll);
	}

	public static JPanel createPanel(ArrayList<PokeType> typeArr) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		for (PokeType type : typeArr) {
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
		}

		return panel;
	}
}