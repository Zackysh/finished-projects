package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import models.PokeType;
import models.Pokemon;
import utils.MediaFormer;
import utils.ScrollPaneGenerator;
import utils.TextPrompt;

/**
 * The goal of this class is to provide a filter-based search system for this project.
 * 
 * Main Features:
 *  - Filter by name.
 *  - Filter by types.
 *  - Filter by id (not number of Pokedex).
 *  - Stackable filters.
 *  
 * @author AdriGB
 *
 */
public class SearchView extends JFrame implements MouseListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
	private PokedexView parent;
	private JPanel contentPane;
	private ArrayList<Pokemon> pokedexPokeList;
	private ArrayList<PokeType> pokedexTypeList;
	private ArrayList<PokeType> selectedTypes;
	private ArrayList<Pokemon> resultPokeList = new ArrayList<>();
	private JLabel lbl_Back;
	private JButton btn_Back;
	private JLabel lbl_Search;
	private JButton btn_Search;
	private JLabel lbl_Title;
	private JTextField tf_ByName;
	private TextPrompt tp_ByName;
	private JComboBox<String> cb_ById;
	private JPanel typeContainer;
	private JLabel changeTypes;
	private JLabel background;

	/**
	 * Constructor which receives pokeList, typeList and his PokedexView parent.
	 * 
	 * @param parent used to return search result and return focus
	 * @param pokeList pokemons to be search
	 * @param typeList existing types
	 */
	public SearchView(PokedexView parent, ArrayList<Pokemon> pokeList, ArrayList<PokeType> typeList) {
		UIManager.put("TextField.inactiveBackground", new ColorUIResource(new Color(255, 255, 255)));
		this.parent = parent;
		this.pokedexPokeList = pokeList;
		this.pokedexTypeList = typeList;
		parent.setVisible(false);
		initialize();
		setVisible(true);
	}

	/**
	 * Controls initialization and initialize main components.
	 */
	private void initialize() {
		// Initialize panel
		contentPane = new JPanel();
		contentPane.setLayout(null);
		// Initialize frame
		setBounds(0, 0, 720, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(contentPane);
		// Lists
		selectedTypes = new ArrayList<>();
		// Fill panel with UI components
		initializeUI();
	}

	/**
	 * This method initialize UI components.
	 */
	private void initializeUI() {
		// HEADDER
		lbl_Title = new JLabel("SEARCH");
		lbl_Title.setBounds(0, 0, 210, 40);
		lbl_Title.setLocation(this.getBounds().width / 2 - 85, 30);
		lbl_Title.setFont(new Font("Flexo-Medium", Font.BOLD, 36));
		lbl_Title.setForeground(new Color(103, 97, 97));
		contentPane.add(lbl_Title);
		
		btn_Back = new JButton("Back");
		btn_Back.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		btn_Back.setBounds(120, 74, 80, 70);
		btn_Back.setBorder(null);
		btn_Back.setForeground(Color.black);
		btn_Back.setFont(new Font("Flexo-Regular", Font.BOLD, 16));
		btn_Back.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		btn_Back.setContentAreaFilled(false);
		btn_Back.addActionListener(this);
		btn_Back.addMouseListener(this);
		contentPane.add(btn_Back);

		lbl_Back = new JLabel();
		lbl_Back.setBounds(120, 22, 80, 117);
		lbl_Back.setIcon(MediaFormer.getImageIconFitLabel(lbl_Back, "images\\Buttons\\jigliButton.png"));
		contentPane.add(lbl_Back);

		btn_Search = new JButton("Search");
		btn_Search.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		btn_Search.setBounds(311, 178, 80, 70);
		btn_Search.setBorder(null);
		btn_Search.setForeground(Color.black);
		btn_Search.setFont(new Font("Flexo-Regular", Font.BOLD, 16));
		btn_Search.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		btn_Search.setContentAreaFilled(false);
		btn_Search.addActionListener(this);
		btn_Search.addMouseListener(this);
		contentPane.add(btn_Search);

		lbl_Search = new JLabel();
		lbl_Search.setBounds(296, 100, 96, 150);
		lbl_Search.setIcon(MediaFormer.getImageIconFitLabel(lbl_Search, "images\\Buttons\\sandButton.png"));
		contentPane.add(lbl_Search);
		// BODY
		tf_ByName = new JTextField();
		tf_ByName.setHorizontalAlignment(SwingConstants.CENTER);
		tf_ByName.setBounds(115, 170, 160, 50);
		tf_ByName.setFont(new Font("Flexo-Medium", Font.BOLD, 20));
		tf_ByName.setBorder(
				BorderFactory.createSoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, Color.white, Color.gray));
		tf_ByName.setForeground(new Color(220, 220, 220));
		tf_ByName.setBackground(new Color(45, 45, 45));
		tf_ByName.setCaretColor(Color.white);
		contentPane.add(tf_ByName);
		tp_ByName = new TextPrompt("Name", tf_ByName);
		tp_ByName.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		tp_ByName.changeAlpha(0.75f);
		tp_ByName.setForeground(new Color(220, 220, 220, 160));
		tp_ByName.changeStyle(Font.ITALIC);

		DefaultListCellRenderer listRenderer;
		cb_ById = new JComboBox<String>();
		cb_ById.setBounds(435, 170, 160, 50);
		cb_ById.setFont(new Font("Flexo-Medium", Font.BOLD, 16));
		cb_ById.setForeground(Color.white);
		cb_ById.setBackground(new Color(45, 45, 45));
		listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
		cb_ById.setRenderer(listRenderer);
		cb_ById.setBorder(
				BorderFactory.createSoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, Color.white, Color.gray));
		cb_ById.addItem("None (id)");
		for (Pokemon pokemon : pokedexPokeList) {
			cb_ById.addItem(Integer.toString(pokemon.getIdP()));
		}
		contentPane.add(cb_ById);

		typeContainer = new JPanel();
		typeContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		typeContainer.setBackground(new Color(127, 127, 127));
		typeContainer.setBounds(125, 315, 450, 85);
		contentPane.add(typeContainer);
		ArrayList<PokeType> noType = new ArrayList<>();
		noType.add(new PokeType(1, "No types selected", new Color(45, 45, 45)));
		ScrollPaneGenerator.scrollableTypesPanel(noType, typeContainer);

		changeTypes = new JLabel();
		changeTypes.setBounds(320, 255, 60, 60);
		changeTypes.setBackground(new Color(0, 0, 0, 0));
		changeTypes.setBorder(null);
		changeTypes.setFont(new Font("Flexo-Medium", Font.BOLD, 26));
		changeTypes.setForeground(Color.WHITE);
		changeTypes.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon.png"));
		changeTypes.setVerticalTextPosition(SwingConstants.CENTER);
		changeTypes.setHorizontalTextPosition(SwingConstants.CENTER);
		changeTypes.addMouseListener(this);
		contentPane.add(changeTypes);
		
		background = new JLabel();
		background.setBounds(-20, 0, 725, 445);
		background.setIcon(MediaFormer.getImageIconFitLabel(background, "images\\Other\\searchBack.png"));
		contentPane.add(background);
	}
	
	/**
	 * Method that show selectedTypes content with an crollableTypesPanel.
	 * (In this view)
	 */
	public void showTypes() {
		ScrollPaneGenerator.scrollableTypesPanel(selectedTypes, typeContainer);
		typeContainer.repaint();
		typeContainer.setVisible(false);
		typeContainer.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == changeTypes) // change selectedTypes
			new SelectTypes(pokedexTypeList, selectedTypes, this);
	}

	/**
	 * Mouse entered and mouse exited are used to get a roll-over effect.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == changeTypes)
			changeTypes
					.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon2.png"));
		else if (e.getSource() == btn_Back)
			lbl_Back.setIcon(MediaFormer.getImageIconFitLabel(lbl_Back, "images\\Buttons\\jigliButton2.png"));
		else if (e.getSource() == btn_Search)
			lbl_Search.setIcon(MediaFormer.getImageIconFitLabel(lbl_Search, "images\\Buttons\\sandButton2.png"));
		else if (e.getSource() == changeTypes)
			changeTypes
					.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon2.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == changeTypes)
			changeTypes.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon.png"));
		else if (e.getSource() == btn_Back)
			lbl_Back.setIcon(MediaFormer.getImageIconFitLabel(lbl_Back, "images\\Buttons\\jigliButton.png"));
		else if (e.getSource() == btn_Search)
			lbl_Search.setIcon(MediaFormer.getImageIconFitLabel(lbl_Search, "images\\Buttons\\sandButton.png"));
		else if (e.getSource() == changeTypes)
			changeTypes.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon.png"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_Back) { // return focus to parent
			parent.setVisible(true);
			this.dispose();
		} else if (e.getSource() == btn_Search) { // return result and focus to parent
			/**
			 * Whole search logic run in this block. to decide which filters will be applied
			 * to the search and which ones will not, and if there is at least one in use.
			 * Each filter is also applied here when it has already been decided.
			 */
			boolean existMatches = false;
			boolean allEmpty = true;
			for (Pokemon pokemon : pokedexPokeList) {
				boolean byName = false;
				boolean byType = false;
				boolean byId = false;
				boolean nameOk = false;
				boolean typeOk = false;
				boolean idOk = false;

				// Check which constraints user want to use
				if (!tf_ByName.getText().isBlank())
					byName = true; // if user fill name field
				else
					nameOk = true;
				if (!selectedTypes.isEmpty())
					byType = true; // if user select any type
				else
					typeOk = true;
				if (!cb_ById.getSelectedItem().equals("None (id)"))
					byId = true; // if user select an id
				else
					idOk = true;
				// Applying constraints to find desired pokemon
				if (byName)
					if (pokemon.getName().toLowerCase().contains(tf_ByName.getText().trim().toLowerCase()))
						nameOk = true;
					else
						nameOk = false;
				if (byType) {
					int coincidences = 0;
					for (PokeType lookForType : selectedTypes) {
						for (PokeType type : pokemon.getTypes()) 
							if (lookForType.getName().equals(type.getName()))
								coincidences++;
						if (coincidences == selectedTypes.size())
							typeOk = true;
						else
							typeOk = false;
					}
				}
				if (byId)
					if (pokemon.getIdP() == Integer.parseInt((String) cb_ById.getSelectedItem()))
						idOk = true;
					else
						idOk = false;
				if (byName || byType || byId) {
					allEmpty = false;
				}
				if (nameOk && typeOk && idOk && !allEmpty) {
					resultPokeList.add(pokemon);
					existMatches = true;
				}
			}
			if (existMatches && !allEmpty) {
				parent.showSearchResult(resultPokeList);
				parent.setVisible(true);
				this.dispose();
			} else if (allEmpty)
				JOptionPane.showMessageDialog(null, "You must fill at least one field.", "Nothing filled",
						JOptionPane.WARNING_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "There are not any pokemon with this attributes.", "No matches",
						JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	// Unused overridden methods
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
