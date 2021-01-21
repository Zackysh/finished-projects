package utils;

import java.awt.Color;
import java.awt.Dimension;
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
 * The objective of this class is to generate content sub-panels with different
 * purposes. For now only one. The generation of these panels will be static and
 * will be carried out within the panel that is passed as a parameter in the
 * methods.
 * 
 * @author AdriGB
 *
 */
public class ScrollPaneGenerator {
	
	/**
	 * This method makes use of createTypesPanel. It just add returned JPanel
	 * to an JScrollPane to complete its behavior correctly.
	 * 
	 * With this JScrollPane is possible to display a ton of labels horizontally
	 * in a smart way.
	 * 
	 * @param list list to display
	 * @param pane pane in which JScrollPane will be embedded
	 */
	public static void scrollableTypesPanel(ArrayList<PokeType> list, JPanel pane) {
		
		JPanel typeContainer = createTypesPanel(list);
		JScrollPane scroll = new JScrollPane();
		
		scroll.setPreferredSize(new Dimension(pane.getWidth(), pane.getHeight()));
		scroll.getViewport().add(typeContainer);
		pane.removeAll();
		pane.add(scroll);
	}
	
	/**
	 * This method fills and return a JPanel with given ArrayList<PokeType>.
	 * A GridLayout will be used. JButtons will be inserted on the JPanel one by one
	 * (which will act as JLabel). These will contain the name and color of the type
	 * that corresponds to it.
	 * GridLayout offers an Dynamic adaptation of its content.
	 * If there're more than 7 JButtons inside, there will be neccesary a JScrollPane
	 * that's why this pane is added to a JScrollPane at scrollableTypesPanel().
	 * 
	 * @param typeArr types to display as labels
	 * @return panel with desired labels
	 */
	private static JPanel createTypesPanel(ArrayList<PokeType> typeArr) {
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