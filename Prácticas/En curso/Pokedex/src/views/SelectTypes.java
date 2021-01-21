package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.PokeType;
import utils.MediaFormer;

/**
 * The goal of this class is to fill in or select multiple types and enter them
 * in pokemonTypes. The ArrayList pokemonTypes will be populated with object
 * references from availableTypes. To do this, it will use UI components and it
 * will be the user who makes the selection.
 * 
 * Main Features:
 *  - Dynamic organization of the types contained in available Types into graphic components.
 *  - Each CheckBox is related to a type.
 *  - ArrayList pokemonTypes will be filled with selected types.
 * 
 * @author AdriGB
 *
 */
public class SelectTypes extends JFrame implements MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbl_headder;
	private JLabel lbl_selectAll;
	private JButton btn_selectAll;
	private JLabel lbl_Accept;
	private JButton btn_Accept;
	private JLabel lbl_Back;
	private JButton btn_Back;
	private JLabel background;
	private ArrayList<JCheckBox> chTypes;
	private ArrayList<PokeType> availableTypes;
	private ArrayList<PokeType> pokemonTypes;
	private PokedexView parentP;
	private SearchView parentS;

	/**
	 * Constructor of the class.
	 * 
	 * @param availableTypes
	 * @param pokemonTypes
	 */
	public SelectTypes(ArrayList<PokeType> availableTypes, ArrayList<PokeType> pokemonTypes, PokedexView parent) {
		this.availableTypes = availableTypes;
		this.pokemonTypes = pokemonTypes;
		this.parentP = parent;
		Collections.sort(availableTypes);
		initialize();
		importFonts();
		initializeUI();
		setVisible(true);
	}

	/**
	 * 
	 * @param availableTypes
	 * @param pokemonTypes
	 * @param parent
	 */
	public SelectTypes(ArrayList<PokeType> availableTypes, ArrayList<PokeType> pokemonTypes, SearchView parent) {
		this.availableTypes = availableTypes;
		this.pokemonTypes = pokemonTypes;
		this.parentS = parent;
		Collections.sort(availableTypes);
		initialize();
		importFonts();
		initializeUI();
		setVisible(true);
	}
	
	/**
	 * Import fonts from .ttf files. First a new Font is initialized, it receives an
	 * input stream that brings desired font. Later its registered into the current
	 * Graphic Environment.
	 */
	public void importFonts() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Flexo-Light.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Flexo-Regular.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Flexo-Medium.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Pokefont.ttf")));
		} catch (FontFormatException | IOException e2) {
			e2.fillInStackTrace();
		}
	}

	/**
	 * Initialize non-graphic components.
	 */
	private void initialize() {
		// Main JPanel initialization
		contentPane = new JPanel();
		contentPane.setLayout(null);
		// Frame initialization
		setBounds(0, 0, 600, 500);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		// Other
		chTypes = new ArrayList<>();
	}

	/**
	 * Check JCheckBoxes in chTypes which coincide with given pokemonTypes. Its used
	 * on initializeUI.
	 */
	public void setSelectedTypes() {
		for (PokeType pokeType : pokemonTypes) {
			if (availableTypes.contains(pokeType)) {
				for (JCheckBox ch : chTypes) {
					if (ch.getText() == pokeType.getName()) {
						ch.setSelected(true);
					}
				}
			}
		}
	}

	/**
	 * Initialize this frame and its componentes.
	 */
	private void initializeUI() {
		initializeCheckBoxes();
		setSelectedTypes();
		initializeStaticComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_selectAll) {
			checkAllBoxes(e);
		} else if (e.getSource() == btn_Back) {
			this.dispose();
		} else if (e.getSource() == btn_Accept) {
			if (!checkCheckBoxes())
				JOptionPane.showMessageDialog(null, "At least one type must be selected", "Warning",
						JOptionPane.WARNING_MESSAGE);
			else {
				updatePokemonTypes();
				if(parentP != null)
					parentP.showTypes();
				else
					parentS.showTypes();
				dispose();
			}
		}
	}

	/**
	 * Method that create a JCheckBox for each type in availableTypes and add it to
	 * contentPane.
	 */
	private void initializeCheckBoxes() {
		int x = 190;
		int y = 80;
		int width = 100;
		int height = 20;
		int cont = 1;
		for (PokeType type : availableTypes) {
			cont++;
			JCheckBox ch = new JCheckBox(type.getName());
			ch.setBounds(x, y, width, height);
			ch.setFont(new Font("Flexo-Medium", Font.BOLD, 14));
			contentPane.add(ch);
			chTypes.add(ch);
			y += 50;
			if (cont % 7 == 0) {
				x += 120;
				y = 80;
				cont = 1;
			}
		}
	}
	
	/**
	 * Method that verify that at least one CheckBox is selected.
	 * 
	 * @returns true If there are at least one
	 * @returns false If none is selected
	 */
	private boolean checkCheckBoxes() {
		for (JCheckBox ch : chTypes) {
			if (ch.isSelected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method which return an ArrayList of JCheckBox that will contain only selected
	 * JCheckBoxes.
	 * 
	 * @return desired ArrayList
	 */
	private ArrayList<JCheckBox> getSelectedBoxes() {
		ArrayList<JCheckBox> selectedBoxes = new ArrayList<>();
		for (JCheckBox jCheckBox : chTypes) {
			if (jCheckBox.isSelected())
				selectedBoxes.add(jCheckBox);
		}
		return selectedBoxes;
	}

	/**
	 * This method return an ArrayList of PokeTypes which will contain correspondent
	 * selected types in this view. Types are extracted from main type ArrayList
	 * instead of create new ones.
	 * 
	 * @return desired ArrayList
	 */
	private ArrayList<PokeType> getSelectedTypes() {
		ArrayList<JCheckBox> selectedBoxes = getSelectedBoxes();
		ArrayList<PokeType> selectedTypes = new ArrayList<>();

		for (PokeType type : availableTypes) {
			for (JCheckBox ch : selectedBoxes) {
				if (ch.getText().equals(type.getName())) {
					selectedTypes.add(type);
				}
			}
		}
		return selectedTypes;
	}

	/**
	 * This method updates original Pokemon types with selected types in this view.
	 */
	private void updatePokemonTypes() {
		pokemonTypes.clear();
		pokemonTypes.addAll(getSelectedTypes());
	}

	/**
	 * Method that check/un-check JCheckBoxes in chTypes.
	 * 
	 * @param e
	 */
	private void checkAllBoxes(ActionEvent e) {
		if (((JButton) e.getSource()).getText().equals("Select All")) {
			for (JCheckBox ch : chTypes) {
				ch.setSelected(true);
			}
			btn_selectAll.setText("Deselect");
		} else {
			for (JCheckBox ch : chTypes) {
				ch.setSelected(false);
			}
			btn_selectAll.setText("Select All");
		}
	}

	/**
	 * Controls rollover effect-
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == btn_selectAll) {
			lbl_selectAll
					.setIcon(MediaFormer.getImageIconFitLabel(lbl_selectAll, "images\\Buttons\\pikaButtonIcon2.png"));
			btn_selectAll.setForeground(Color.white);
		} else if (e.getSource() == btn_Accept) {
			lbl_Accept.setIcon(MediaFormer.getImageIconFitLabel(lbl_Accept, "images\\Buttons\\vulButton2.png"));
			btn_Accept.setForeground(Color.white);
		} else if (e.getSource() == btn_Back) {
			lbl_Back.setIcon(MediaFormer.getImageIconFitLabel(lbl_Back, "images\\Buttons\\eveeButtonIcon2.png"));
			btn_Back.setForeground(Color.white);
		}
	}

	/**
	 * Controls rollover effect-
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == btn_selectAll) {
			lbl_selectAll
					.setIcon(MediaFormer.getImageIconFitLabel(lbl_selectAll, "images\\Buttons\\pikaButtonIcon.png"));
			btn_selectAll.setForeground(Color.black);
		} else if (e.getSource() == btn_Accept) {
			lbl_Accept.setIcon(MediaFormer.getImageIconFitLabel(lbl_Accept, "images\\Buttons\\vulButton.png"));
			btn_Accept.setForeground(Color.black);
		} else if (e.getSource() == btn_Back) {
			lbl_Back.setIcon(MediaFormer.getImageIconFitLabel(lbl_Back, "images\\Buttons\\eveeButtonIcon.png"));
			btn_Back.setForeground(Color.black);
		}
	}
	
	/**
	 * Method that initialize static graphic components.
	 */
	private void initializeStaticComponents() {
		// Just a title
		lbl_headder = new JLabel("Type selector");
		lbl_headder.setBounds((getBounds().width / 2) - 78, 20, 160, 30);
		lbl_headder.setFont(new Font("Flexo-Medium", Font.BOLD, 24));
		lbl_headder.setForeground(new Color(179, 179, 179));
		contentPane.add(lbl_headder);

		btn_selectAll = new JButton("Select All");
		btn_selectAll.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		btn_selectAll.setBounds(40, 86, 80, 70);
		btn_selectAll.setBorder(null);
		btn_selectAll.setForeground(Color.black);
		btn_selectAll.setFont(new Font("Flexo-Regular", Font.BOLD, 16));
		btn_selectAll.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		btn_selectAll.setContentAreaFilled(false);
		btn_selectAll.addActionListener(this); // ActionListener
		btn_selectAll.addMouseListener(this); // MouseListener
		contentPane.add(btn_selectAll);

		lbl_selectAll = new JLabel();
		lbl_selectAll.setBounds(40, 30, 80, 120); // +120 +50 = +170
		lbl_selectAll.setIcon(MediaFormer.getImageIconFitLabel(lbl_selectAll, "images\\Buttons\\pikaButtonIcon.png"));
		contentPane.add(lbl_selectAll);

		btn_Accept = new JButton("Accept");
		btn_Accept.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		btn_Accept.setBounds(40, 208, 80, 70);
		btn_Accept.setBorder(null);
		btn_Accept.setForeground(Color.black);
		btn_Accept.setFont(new Font("Flexo-Regular", Font.BOLD, 16));
		btn_Accept.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		btn_Accept.setContentAreaFilled(false);
		btn_Accept.addActionListener(this); // ActionListener
		btn_Accept.addMouseListener(this); // MouseListener
		contentPane.add(btn_Accept);

		lbl_Accept = new JLabel();
		lbl_Accept.setBounds(40, 170, 95, 110); // +170 +110 = +280
		lbl_Accept.setIcon(MediaFormer.getImageIconFitLabel(lbl_Accept, "images\\Buttons\\vulButton.png"));
		contentPane.add(lbl_Accept);

		btn_Back = new JButton("Back");
		btn_Back.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		btn_Back.setBounds(40, 355, 80, 70);
		btn_Back.setBorder(null);
		btn_Back.setForeground(Color.black);
		btn_Back.setFont(new Font("Flexo-Regular", Font.BOLD, 16));
		btn_Back.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		btn_Back.setContentAreaFilled(false);
		btn_Back.addActionListener(this); // ActionListener
		btn_Back.addMouseListener(this); // MouseListener
		contentPane.add(btn_Back);

		lbl_Back = new JLabel();
		lbl_Back.setBounds(40, 280, 82, 147); // +120 +50 = +170
		lbl_Back.setIcon(MediaFormer.getImageIconFitLabel(lbl_Back, "images\\Buttons\\eveeButtonIcon.png"));
		contentPane.add(lbl_Back);

		background = new JLabel(new ImageIcon("images\\Other\\typeSbackground.png"));
		background.setBounds(-6, -15, 600, 500);
		contentPane.add(background);
	}

	// Unused overriden methods
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
