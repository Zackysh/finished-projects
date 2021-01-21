package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalButtonUI;

import dao.DAO_Pokemon;
import dao.DAO_Type;
import models.PokeType;
import models.Pokemon;
import utils.FancyProgressBar;
import utils.MediaFormer;
import utils.ScrollPaneGenerator;
import utils.StringUtils;

/**
 * Class that extends JFrame, which simulate the functionality of a Pokedex from
 * "Pokemon". It shows some Pokemon information stored into a DB called pokedb.
 * 
 * Its compound by standard swing component, but they're customized and its
 * behavior has been modified to reach a style according to today's
 * applications. Firstly it initialize necessary JComponents to show the
 * information. Later, PokeTypes are introduced into typeList from pokedb. Then
 * Pokemons can be injected because types are ready, pokeList is filled. When
 * all is ready, the user is received with a cool animation.
 * 
 * I must say javax.swing doesn't really work like I may desire xD * But have
 * achieved this result has been a really comfortable challenge.
 * 
 * This class is supposed to work with other files like DAOs or login/register
 * views.
 * 
 * @author AdriGB
 *
 */
public class PokedexView extends JFrame implements ActionListener, MouseListener, KeyListener {

	/**
	 * Its not used, but I prefer this solution than ignore the warning.
	 */
	private static final long serialVersionUID = -2255574258469725946L;
	// Main components
	private JPanel contentPane;
	private JLabel background;
	// MENU Components
	// This list is used to give certain behavior to all at the same time
	private List<JMenuItem> menuIList;
	private JMenuBar menuBar;
	private JMenu menu_new;
	private JMenuItem mNew_new, mNew_save, mNew_discard;
	private JMenu menu_edit;
	private JMenuItem mEdit_on, mEdit_save, mEdit_Remove;
	private JButton search;
	// WELCOME-ANIMATION components ----------
	private JButton welcomeMessage;
	private Timer welcomeTimer;
	private Timer welcomeTimerTwo;
	private String userName;
	// LABEL_BUTTONS -------------------------
	private JLabel lbl_buttonRight;
	private JLabel rightButtonFormer1;
	private JLabel rightButtonFormer2;
	private JLabel lbl_buttonLeft;
	private JLabel leftButtonFormer1;
	private JLabel leftButtonFormer2;
	// TOP ---------------------------
	private JLabel searchIcon;
	private JLabel searchLbl;
	private JLabel modeLbl;
	private JLabel previousName;
	private JLabel previousNumber;
	private JLabel nextName;
	private JLabel nextNumber;
	private JLabel numberSign;
	private JTextField currentName;
	private JTextField currentNumber;
	private JTextArea description;
	private JLabel pokeImage;
	private String pokeImageSource;
	private String pokeImageDefaultSource;
	private JLabel speakerIcon;
	private JLabel restoreIcon;
	// BLUE-BOX (T: Title, V: Value) --------
	private JLabel prop_skillT;
	private JTextField prop_skillV;
	private JLabel prop_categoryT;
	private JTextField prop_categoryV;
	private JLabel prop_heightT;
	private JTextField prop_heightV;
	private JLabel prop_weightT;
	private JTextField prop_weightV;
	private JLabel prop_sexT;
	private JTextField prop_sexV;
	// UNDER-BLUE-BOX -----------------------
	private JPanel typeContainer;
	private JLabel changeTypes;
	// GRAY-BOX ----------------------------
	private List<JTextField> attIntputList;
	private JLabel lbl_ps;
	private JProgressBar ps;
	private JTextField psI;
	private JLabel lbl_att;
	private JProgressBar att;
	private JTextField attI;
	private JLabel lbl_def;
	private JProgressBar def;
	private JTextField defI;
	private JLabel lbl_satt;
	private JProgressBar satt;
	private JTextField sattI;
	private JLabel lbl_sdef;
	private JProgressBar sdef;
	private JTextField sdefI;
	private JLabel lbl_speed;
	private JProgressBar speed;
	private JTextField speedI;
	// FOOTER ------------------------------
	private JButton exit;
	// LOGIC HANDLERS ---------------------
	// DAOs
	private DAO_Pokemon pokeDAO;
	private DAO_Type typeDAO;
	// Lists
	private ArrayList<Pokemon> pokeList = new ArrayList<>();
	private ArrayList<Pokemon> backupPokeList = new ArrayList<>();
	private ArrayList<PokeType> typeList = new ArrayList<>();
	private ArrayList<PokeType> backupTypeList = new ArrayList<>();
	private int listController = 0;
	// Add/Edit/Search control
	private Pokemon newPokemon;
	private List<JTextField> readyFields;
	private List<JTextField> strFields;
	private List<JTextField> doubleFields;
	private JLabel descriptionRules;
	private boolean isDescriptionReady;
	private boolean isPokeImageEdited;
	private boolean isSearchOn;
	private boolean isEditOn;
	// Sound Control
	private boolean isSoundEnable = true;
	private Timer soundTimer;

	public static void main(String[] args) {
		new PokedexView("Pipo");
	}

	/**
	 * Constructor of the class. Call main methods that will control whole frame and
	 * pane.
	 * 
	 * Then set visible this frame.
	 */
	public PokedexView(String userName) {
		this.userName = userName;
		initialize();
		insertTypes();
		insertPokemons();
		initializeUIComponentes();
		setReadyToShow();
		setVisible(true);
		welcome();
	}

	/**
	 * Initialize the frame (this class) and its contentPane.
	 * 
	 * This method import fonts for this Graphic Environment (not the same as
	 * Login-Register environment).
	 */
	private void initialize() {
		importFonts();
		soundTimer = new Timer(2050, this);
		welcomeTimer = new Timer(4000, this);
		welcomeTimerTwo = new Timer(4000, this);
		// Initialize DAO objects
		pokeDAO = new DAO_Pokemon();
		typeDAO = new DAO_Type();
		// Pane initialization
		contentPane = new JPanel();
		contentPane.setLayout(null);
		// Lists
		readyFields = new ArrayList<JTextField>();
		strFields = new ArrayList<JTextField>();
		attIntputList = new ArrayList<JTextField>();
		doubleFields = new ArrayList<JTextField>();
		// Frame initialization
		setTitle("Pokedex");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\Other\\icon.png"));
		setBounds(0, 0, 953, 659);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
	}

	/**
	 * Method that adds existing pokeTypes in the DB to this Pokedex typeList.
	 */
	private void insertTypes() {
		ResultSet rs = (ResultSet) typeDAO.getTypes();
		try {
			while (rs.next()) {
				int idtype = rs.getInt("idtype");
				String name = rs.getString("name");
				String strColor = rs.getString("RGBColor");
				int red = Integer.parseInt(strColor.substring(1, 4));
				int green = Integer.parseInt(strColor.substring(5, 8));
				int blue = Integer.parseInt(strColor.substring(9, 12));

				// New color extracted from pokeDB RGB String color
				Color color = new Color(red, green, blue);

				typeList.add(new PokeType(idtype, name, color));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that adds existing pokemons in the DB to this Pokedex pokeList.
	 */
	private void insertPokemons() {
		// ResultSet in which pokemons are stored
		ResultSet rsPokemon = (ResultSet) pokeDAO.getPokemons();
		try {
			while (rsPokemon.next()) {
				ArrayList<PokeType> types = new ArrayList<PokeType>();
				int[] baseAtt = new int[6];
				// ResultSet in which every single pokemon types are stored individually,
				// one for time (according with surrounding while loop)
				ResultSet rsTypes = (ResultSet) typeDAO.getTypesFromPoke(rsPokemon.getInt("idpoke"));
				while (rsTypes.next()) {
					// We obtain appropriate pokeType from typeList according
					// to types stored in ResultSet
					for (PokeType type : typeList) {
						if (type.getName().equals(rsTypes.getString("name")))
							types.add(type);
					}
					String aux = rsPokemon.getString("baseAttributes");
					int cont = 0;
					for (int i = 0; i < baseAtt.length; i++) {
						baseAtt[i] = Character.getNumericValue(aux.charAt(cont));
						cont += 2;
					}
				}
				// BACKGROUND ------------------------------------------------------------
				pokeList.add(new Pokemon(rsPokemon.getInt("idpoke"), rsPokemon.getString("name"),
						rsPokemon.getInt("number"), rsPokemon.getString("description"), rsPokemon.getString("skill"),
						rsPokemon.getString("category"), rsPokemon.getDouble("height"), rsPokemon.getDouble("weight"),
						rsPokemon.getString("sex"), types, baseAtt));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that animates Welcome message at the beginning. From left to center
	 * animOne(). From center to right animTwo().
	 */
	public void welcome() {
		animOne();
		welcomeTimer.start(); // timer will run animTwo()
	}

	/*
	 * First loop of the animation.
	 */
	private void animTwo() {
		welcomeTimerTwo.start();
		Point punto = new Point(1200, (int) welcomeMessage.getBounds().getY());
		animate(welcomeMessage, punto, 150, 2);
	}

	/**
	 * Second loop of the animation.
	 */
	private void animOne() {
		Point punto = new Point((int) this.getBounds().getWidth() / 2 - 150, (int) welcomeMessage.getBounds().getY());
		animate(welcomeMessage, punto, 120, 2);
	}

	/**
	 * Method that animate a JComponent updating the position of it over time.
	 * 
	 * @param component JComponent to be animated.
	 * @param newPoint  New location.
	 * @param frames    Frames in which transition will be performed.
	 * @param interval  Interval of frames component will "jump" every frame.
	 */
	private void animate(JComponent component, Point newPoint, int frames, int interval) {
		Rectangle compBounds = component.getBounds();
		Point oldPoint = new Point(compBounds.x, compBounds.y),
				animFrame = new Point((newPoint.x - oldPoint.x) / frames, (newPoint.y - oldPoint.y) / frames);
		new Timer(interval, new ActionListener() {
			int currentFrame = 0;

			public void actionPerformed(ActionEvent e) {
				component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
						oldPoint.y + (animFrame.y * currentFrame), compBounds.width, compBounds.height);
				if (currentFrame != frames)
					currentFrame++;
				else
					((Timer) e.getSource()).stop();
			}
		}).start();
	}

	/**
	 * Method that control whole filling process to show all Pokemon information on
	 * the panel. Note that pokeList is an ArrayList and its content is shown by
	 * iterating over it with listController.
	 * 
	 * This method is subdivided on sub-methods specialized on complete a specific
	 * sub-task.
	 */
	public void showPokemon() {
		if (!pokeList.isEmpty()) {
			Pokemon current = pokeList.get(listController);
			showHeadder(current);
			description.setText(pokeList.get(listController).getDescription());
			showBlueBox(current);
			showGrayBox(current);
			showTypes();
			pokeImageSource = "images\\Pokemons\\" + pokeList.get(listController).getName() + ".png";
			pokeImageDefaultSource = "images\\Pokemons_restore_default\\" + pokeList.get(listController).getName()
					+ ".png";
			showPokeImage();
		} else
			actionPerformed(new ActionEvent(mNew_new, 1, ""));
	}

	/**
	 * Method that receives search result and display its content in this view. A
	 * backup of main pokeList is done. This backup will be necessary to restore
	 * pokeList when user press "close search".
	 * 
	 * @param result Pokemons to be displayed.
	 */
	@SuppressWarnings("unchecked")
	public void showSearchResult(ArrayList<Pokemon> result) {
		isSearchOn = true;
		mNew_new.setEnabled(false);
		search.setText("Close search");
		backupPokeList = (ArrayList<Pokemon>) pokeList.clone();
		pokeList = result;
		listController = 0;
		searchLbl.setVisible(true);
		searchIcon.setVisible(true);
		showPokemon();
	}

	/**
	 * Method that fill type labels with current pokemon types.
	 * 
	 * Two is the max number of types that a pokemon can have, so there's two label
	 * (special label based on JButton) that contain possible types.
	 * 
	 * If there's no second type, second label 2ond label is set no non visible.
	 * 
	 * @param current Current Pokemon reference.
	 */
	public void showTypes() {
		ScrollPaneGenerator.scrollableTypesPanel(pokeList.get(listController).getTypes(), typeContainer);
		typeContainer.repaint();
		typeContainer.setVisible(false);
		typeContainer.setVisible(true);
	}

	/**
	 * Method that fills Gray-Box with current Pokemon baseAtt.
	 * 
	 * This Gray-Box is compound of six personalized JProgressBar. Those have
	 * attached a ToolTip which is updated every time its progress bar is updated
	 * too.
	 * 
	 * @param current Current Pokemon reference.
	 */
	private void showGrayBox(Pokemon current) {
		int[] baseAtt = current.getBaseAtt();
		ps.setValue(baseAtt[0]);
		att.setValue(baseAtt[1]);
		def.setValue(baseAtt[2]);
		satt.setValue(baseAtt[3]);
		sdef.setValue(baseAtt[4]);
		speed.setValue(baseAtt[5]);
		ps.setToolTipText(Integer.toString(ps.getValue()));
		att.setToolTipText(Integer.toString(att.getValue()));
		def.setToolTipText(Integer.toString(def.getValue()));
		satt.setToolTipText(Integer.toString(satt.getValue()));
		sdef.setToolTipText(Integer.toString(sdef.getValue()));
		speed.setToolTipText(Integer.toString(speed.getValue()));
	}

	/**
	 * Method that fills Blue-Box with current Pokemon baseAtt.
	 * 
	 * This Blue-Box contains six different attributes obtained from current. This
	 * String attributes are passed to its correspondent JLabel.
	 */
	private void showBlueBox(Pokemon current) {
		// Obtaining attributes
		String height = Double.toString(current.getHeight());
		String weight = Double.toString(current.getWeight());
		String sex = current.getSex();
		String category = current.getCategory();
		String skill = current.getSkill();
		// Showing attributes
		prop_heightV.setText(height);
		prop_weightV.setText(weight);
		prop_sexV.setText(sex);
		prop_categoryV.setText(category);
		prop_skillV.setText(skill);
	}

	/**
	 * Method that controls labels content on header section.
	 * 
	 * Header is compound by four labels for next and previous pokemon info, and two
	 * labels for current pokemon info.
	 * 
	 * @param current Current pokemon reference.
	 */
	private void showHeadder(Pokemon current) {
		// CURRENT INFO Always initialized int the same way
		String currName = current.getName();
		String currNumber = Integer.toString(current.getNumber());
		// PREVIOUS INFO
		String prevName;
		String prevNumber;
		if (pokeList.size() > 1) {
			lbl_buttonLeft.setVisible(true);
			lbl_buttonRight.setVisible(true);
			if (listController > 0) { // if current pokemon is the first on the list
				// previuous will be last on the list
				prevName = pokeList.get(listController - 1).getName();
				prevNumber = Integer.toString(pokeList.get(listController - 1).getNumber());

				previousName.setText(prevName);
				previousNumber.setText("N.º" + prevNumber);
			} else {
				prevName = pokeList.get(pokeList.size() - 1).getName();
				prevNumber = Integer.toString(pokeList.get(pokeList.size() - 1).getNumber());

				previousName.setText(prevName);
				previousNumber.setText("N.º" + prevNumber);
			}
			// NEXT INFO
			String nexName;
			String nexNumber;
			if (listController < pokeList.size() - 1) {
				nexName = pokeList.get(listController + 1).getName();
				nexNumber = Integer.toString(pokeList.get(listController + 1).getNumber());

				nextName.setText(nexName);
				nextNumber.setText("N.º" + nexNumber);
			} else {
				nexName = pokeList.get(0).getName();
				nexNumber = Integer.toString(pokeList.get(0).getNumber());

				nextName.setText(nexName);
				nextNumber.setText("N.º" + nexNumber);
			}
		} else {
			previousName.setText("");
			previousNumber.setText("");
			nextName.setText("");
			nextNumber.setText("");
			lbl_buttonLeft.setVisible(false);
			lbl_buttonRight.setVisible(false);
		}
		currentName.setText(currName);
		currentNumber.setText(currNumber);
	}

	/**
	 * Configure components to work as exhibitor view instead of edit view.
	 */
	private void setReadyToEdit() {
		// Setting JMenuBar
		search.setEnabled(false);
		mNew_new.setEnabled(false);
		mNew_discard.setEnabled(false);
		mNew_save.setEnabled(false);
		mEdit_on.setText("Discard changes");
		mEdit_save.setEnabled(true);
		mEdit_Remove.setEnabled(true);
		// Setting TOP-COMPONENTS
		rightButtonFormer1.setVisible(false);
		rightButtonFormer2.setVisible(false);
		lbl_buttonRight.setVisible(false);
		leftButtonFormer1.setVisible(false);
		leftButtonFormer2.setVisible(false);
		lbl_buttonLeft.setVisible(false);
		previousName.setVisible(false);
		previousNumber.setVisible(false);
		nextName.setVisible(false);
		nextNumber.setVisible(false);
		description.setEditable(true);
		restoreIcon.setVisible(true);
		// Setting BLUE-BOX
		prop_categoryV.setEditable(true);
		prop_heightV.setEditable(true);
		doubleFields.add(prop_heightV);
		prop_sexV.setEditable(true);
		prop_skillV.setEditable(true);
		prop_weightV.setEditable(true);
		doubleFields.add(prop_weightV);
		currentName.setEditable(true);
		currentNumber.setEditable(true);
		changeTypes.setVisible(true);
		// Setting GRAY-BOX
		psI.setVisible(true);
		psI.setText(Integer.toString(ps.getValue()));
		attI.setVisible(true);
		attI.setText(Integer.toString(att.getValue()));
		defI.setVisible(true);
		defI.setText(Integer.toString(def.getValue()));
		sattI.setVisible(true);
		sattI.setText(Integer.toString(satt.getValue()));
		sdefI.setVisible(true);
		sdefI.setText(Integer.toString(sdef.getValue()));
		speedI.setVisible(true);
		speedI.setText(Integer.toString(speed.getValue()));
		isEditOn = true;
		/*
		 * Set all components into ready state, as current fields will contain
		 * information about a Pokemon which is obtained directly from PokeDB (all
		 * fields are correct).
		 */
		readyFields.add(currentName);
		readyFields.add(currentNumber);
		readyFields.add(prop_heightV);
		readyFields.add(prop_weightV);
		readyFields.add(prop_sexV);
		readyFields.add(prop_categoryV);
		readyFields.add(prop_skillV);
		readyFields.add(psI);
		readyFields.add(attI);
		readyFields.add(defI);
		readyFields.add(sattI);
		readyFields.add(sdefI);
		readyFields.add(speedI);
		isDescriptionReady = true;

		modeLbl.setVisible(true);
	}

	/**
	 * Configure components to work as edit view instead of simple exhibitor view.
	 */
	private void setReadyToShow() {
		// Setting JMenuBar
		search.setEnabled(true);
		mEdit_on.setEnabled(true);
		mEdit_on.setText("Enable edit mode");
		mEdit_save.setEnabled(false);
		mEdit_Remove.setEnabled(false);
		mNew_save.setEnabled(false);
		mNew_discard.setEnabled(false);
		if (!isSearchOn)
			mNew_new.setEnabled(true);
		modeLbl.setVisible(false);
		// Setting TOP-COMPONENTS
		rightButtonFormer1.setVisible(true);
		rightButtonFormer2.setVisible(true);
		lbl_buttonRight.setVisible(true);
		leftButtonFormer1.setVisible(true);
		leftButtonFormer2.setVisible(true);
		lbl_buttonLeft.setVisible(true);
		previousName.setVisible(true);
		previousNumber.setVisible(true);
		nextName.setVisible(true);
		nextNumber.setVisible(true);
		description.setEditable(false);
		restoreIcon.setVisible(false);
		// Setting BLUE-BOX
		prop_categoryV.setEditable(false);
		prop_heightV.setEditable(false);
		prop_sexV.setEditable(false);
		prop_skillV.setEditable(false);
		prop_weightV.setEditable(false);
		currentName.setEditable(false);
		currentNumber.setEditable(false);
		// Setting GRAY-BOX
		psI.setVisible(false);
		attI.setVisible(false);
		defI.setVisible(false);
		sattI.setVisible(false);
		sdefI.setVisible(false);
		speedI.setVisible(false);
		// Set isEditOn false
		isPokeImageEdited = false;
		showPokemon(); // reset fields with current pokemon info

		isEditOn = false; // used to limit user interaction
		changeTypes.setVisible(false);
		verifyStrFields();
		for (JTextField jTextField : attIntputList)
			jTextField.setBackground(new Color(139, 139, 139));
		verifyDoubleFields();
		verifyCurrentName();
		verifyCurrentNumber();
		verifyDescription();
	}

	/**
	 * Empty all fields. Its used to prepare fields when adding a new Pokemon.
	 */
	private void emptyFields() {
		prop_categoryV.setText("Category");
		prop_heightV.setText("Height");
		prop_sexV.setText("Sex");
		prop_skillV.setText("Skill");
		prop_weightV.setText("Weight");
		description.setText("Description here, must be > 30 character length.");
		currentName.setText("Name");
		currentNumber.setText("Number");
		ps.setValue(3);
		att.setValue(3);
		satt.setValue(3);
		def.setValue(3);
		sdef.setValue(3);
		speed.setValue(3);
		pokeImage.setIcon(MediaFormer.getImageIconFitLabel(pokeImage, "images\\Other\\clip.png"));
	}

	/**
	 * Current file is copied to default file. Current --> Default.
	 */
	private void setCurrentToDefault() {
		File dest = getDefaultImage();
		dest.delete();
		dest = getDefaultImage();
		try {
			MediaFormer.copyFile(getCurrentImage(), dest);
		} catch (IOException e1) {
			e1.fillInStackTrace();
		}
	}

	/**
	 * Default --> Current
	 */
	private void setDefaultToCurrent() {
		File dest = getCurrentImage();
		dest.delete();
		dest = getCurrentImage();
		try {
			MediaFormer.copyFile(getDefaultImage(), dest);
		} catch (IOException e1) {
			e1.fillInStackTrace();
		}
	}

	/**
	 * Get current Pokemon image source file.
	 * 
	 * @return current image source file
	 */
	private File getCurrentImage() {
		return new File(pokeImageSource);
	}

	/**
	 * Get current Pokemon default image source file.
	 * 
	 * @return current image source file
	 */
	private File getDefaultImage() {
		return new File(pokeImageDefaultSource);
	}

	/**
	 * Change current Pokemon default image file with given file parameter.
	 * 
	 * @param origin new file which will replace original file
	 */
	private void setDefaultImage(File origin) {
		File dest = getDefaultImage();
		dest.delete();
		dest = getDefaultImage();
		try {
			MediaFormer.copyFile(origin, dest);
		} catch (IOException e1) {
		}
	}

	/**
	 * Change current Pokemon image file with given file parameter.
	 * 
	 * @param origin new file which will replace original file
	 */
	private void setCurrentImage(File origin) {
		File dest = getCurrentImage();
		dest.delete();
		dest = getCurrentImage();
		try {
			MediaFormer.copyFile(origin, dest);
		} catch (IOException e1) {
			e1.fillInStackTrace();
		}
		origin.delete();
	}

	/**
	 * Paint pokeImage with pokeImageSource. pokeImageSource is main reference to
	 * each Pokemon image, so this method will show always current Pokemon image.
	 */
	private void showPokeImage() {
		pokeImage.setIcon(MediaFormer.getImageIconFitLabel(pokeImage, pokeImageSource));
	}

	/**
	 * Method that allow user to change pokeImage trough UI components. It asks the
	 * user for insert new image via URL or via System explorer.
	 * It copies desired file on staging folder. Then return this file.
	 * 
	 * After this process, parent/caller must move desired file in staging folder to
	 * its destination.
	 */
	private File choseFileFrom() {
		File newFile = new File("images\\staging" + pokeList.get(listController).getName() + ".png");
		ButtonGroup group = new ButtonGroup();
		JRadioButton fromFile = new JRadioButton("From existing system file.", true);
		JRadioButton fromURL = new JRadioButton("From image URL.", false);
		group.add(fromFile);
		group.add(fromURL);
		String msg = "You are about to change current Pokemon image from an URL.";
		Object[] msgContent = { msg, fromFile, fromURL };
		// User chose upload new image via URL or via file
		int n = JOptionPane.showConfirmDialog(null, msgContent, "Method selection", JOptionPane.OK_CANCEL_OPTION);
		if (n == 0) {
			if (fromFile.isSelected()) { // VIA FILE -------
				File origin = MediaFormer.choseFileFromSystem("jpg", "png");
				if (origin != null)
					try {
						MediaFormer.copyFile(origin, newFile);
					} catch (IOException e) {
						e.fillInStackTrace();
					}
				else
					return null;
			} else if (fromURL.isSelected()) { // VIA URL ---------------------------
				String url = getUrlFromUser();
				if (!url.isBlank())
					MediaFormer.downloadToFile(url, newFile);
				else
					return null;
			}
			return newFile;
		} else
			return null;
	}

	/**
	 * Method that ask to user for an URL, check if it is an image URL and return it
	 * as a String. This method also warn the user if given URL is not valid.
	 * 
	 * @returns url desired valid URL
	 * @returns null if user cancel the operation
	 */
	private String getUrlFromUser() {
		String url = "";
		boolean isValid;
		do { // Check if URL is valid
			url = JOptionPane.showInputDialog("Insert Image Url (blank to cancel this operation)");
			if (url != null) {
				isValid = MediaFormer.testImage(url);
				if (!isValid)
					JOptionPane.showMessageDialog(null, "This is not a valid url.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				url = "";
				return url;
			}
		} while (!isValid);
		return url;
	}

	/**
	 * This method play a sound clip given its source. InputStream and Clips are
	 * used to achieve desired behavior.
	 * 
	 * @param source of sound clip
	 */
	public void playSound(String source) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(source).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			actionPerformed(new ActionEvent(soundTimer, 1, "")); // Send null action to turn false "isSoundEnable"
			soundTimer.start(); // in 5segs it will be possible to isSoundEnable sound again
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			changePokeSound();
		}
	}
	
	/**^
	 * This method is similar to choseFileFrom, but it doesn't make use of staging. 
	 */
	private void changePokeSound() {
		File newFile = new File("Sounds\\" + pokeList.get(listController).getName() + ".wav");
		String msg = "You are about to change current Pokemon sound(.wav)\nPLEASE dont't chose tranks longer than 4-6 seconds,\n "
				+ "this kind of tranks are not supported / this implementation only can handle short traks.";
		Object[] msgContent = { msg };
		// User chose upload new image via URL or via file
		int n = JOptionPane.showConfirmDialog(null, msgContent, "Method selection", JOptionPane.OK_CANCEL_OPTION);
		if (n == 0) {
			File origin = MediaFormer.choseFileFromSystem("wav"); // get file to staging
			if (origin != null) {
				try {
					MediaFormer.copyFile(origin, newFile); // move new file to desired folder
				} catch (IOException e) {
					e.fillInStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is called by welcomeTimer. It controls welcome Animation. Note
	 * that welcomeTimer is called by welcome(). - First call: it's committed 4000ms
	 * after welcomeTimer is started, at this point firstLoop of animation will be
	 * over, so secondLoop (animTwo()) is called. - Second call: It's committed
	 * 4000ms after secondLoop is over. This timer is no longer necessary, so it's
	 * stopped.
	 */
	private void runWelcomeTimer() {
		if (!welcomeMessage.isVisible())
			welcomeTimer.stop(); // Second call: stop timer
		else {
			animTwo(); // First call: starts second loop
		}
	}

	/**
	 * This method is called by soundTimer. It just turn isSoundEnable into false
	 * for 2050ms, enough to avoid sound slap / overlap.
	 * 
	 * It makes soundTimer stops at second loop:
	 * - First event: after soundTimer first loop it sends first event.
	 * As isSoundEnable = true at the beginning, first event will turn
	 * isSoundEnable into false. Look if conditional.
	 * - Second event: As isSoundEnable = false at this time (only manipulated by
	 * soundTimer), second event will turn isSoundEnable into true and soundTimer.stop()
	 * will be called. So there's never a third event.
	 */
	private void runSoundTimer() {
		if (isSoundEnable)
			isSoundEnable = false;
		else {
			isSoundEnable = true;
			soundTimer.stop();
		}
	}

	/**
	 * Logic of main buttons.
	 */
	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2) {
			if (listController != 0) { // leftButton
				listController--;
				showPokemon();
			} else {
				listController = pokeList.size() - 1;
				showPokemon();
			}
		} else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2) {
			if (listController < pokeList.size() - 1) {
				listController++; // rightButton
				showPokemon();
			} else {
				listController = 0;
				showPokemon();
			}
		} else if (e.getSource() == speakerIcon) {
			if(isEditOn) {
				changePokeSound();
			} else if (isSoundEnable)
				playSound("Sounds\\" + pokeList.get(listController).getName().toLowerCase() + ".wav");
		} else if (e.getSource() == exit)
			this.dispose(); // exitButton
		else if (e.getSource() == pokeImage && isEditOn) {
			System.out.println("Algo mal");
			String msg = "You are about to change " + pokeList.get(listController).getName() + "s image.";
			int n = JOptionPane.showConfirmDialog(null, msg, "Pokemon image selector", JOptionPane.OK_CANCEL_OPTION);
			if (n == 0) {
				File newFile = choseFileFrom(); // copy file into staging
				if (newFile != null) {
					setCurrentImage(newFile); // move from staging to current pokemon image file
					isPokeImageEdited = true;
					showPokeImage();
				} else
					JOptionPane.showMessageDialog(null, "Operation cancelled", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == restoreIcon) { // Restore defaultImage
			JCheckBox muteDefault = new JCheckBox(
					"Change " + pokeList.get(listController).getName() + "s default image");
			JLabel msg = new JLabel("<html><center>You are about to restore " + pokeList.get(listController).getName()
					+ " default image,<br> do not check the checkbox if you want to keep the default image.");
			Object[] list = { muteDefault, msg };
			int n = JOptionPane.showConfirmDialog(null, list, "Pokemon default image restoring",
					JOptionPane.OK_CANCEL_OPTION);
			if (n == 0) {
				if (muteDefault.isSelected()) {
					ButtonGroup group = new ButtonGroup();
					JRadioButton useCurrent = new JRadioButton(
							"Set " + pokeList.get(listController).getName() + "s current image as default.", true);
					JRadioButton useNew = new JRadioButton("Chose new file.", false);
					group.add(useCurrent);
					group.add(useNew);
					String msg2 = "You are about to change " + pokeList.get(listController).getName()
							+ "s default image. Indicate how you want to do it.";
					Object[] list2 = { useCurrent, useNew, msg2 };
					int m = JOptionPane.showConfirmDialog(null, list2, "Pokemon default image selection",
							JOptionPane.OK_CANCEL_OPTION);
					if (m == 0) {
						if (useCurrent.isSelected()) {
							setCurrentToDefault();
							JOptionPane.showMessageDialog(null, "Now " + pokeList.get(listController).getName()
									+ "s default image is his current image! :)");
						} else if (useNew.isSelected()) {
							File newFile = choseFileFrom(); // get file in staging
							setDefaultImage(newFile); // move file to pokemon default image
							setDefaultToCurrent();
							showPokeImage();
						}
					}
				} else {
					setDefaultToCurrent();
					showPokeImage();
				}
			}
		} else if (e.getSource() == changeTypes) {
			/**
			 * At this point Eclipse doesn't allow any way to avoid this warning. Its
			 * necessary to get desired arrayList and parse it. If you don't believe me, try
			 * the other two commented methods :)
			 * ArrayList<PokeType> example = pokeList.get(listController).getTypes();
			 * backupTypeList = example.clone();
			 * backupTypeList = pokeList.get(listController).getTypes().clone();
			 */
			backupTypeList = (ArrayList<PokeType>) pokeList.get(listController).getTypes().clone();
			new SelectTypes(typeList, pokeList.get(listController).getTypes(), this);
		}
	}

	/**
	 * mouesEntered and mouseExited are used to gain a roll over effect to
	 * top_buttons and JMenuItems.
	 */
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2)
			lbl_buttonLeft.setIcon(new ImageIcon("images\\Buttons\\buttonLeft2.png"));
		else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2)
			lbl_buttonRight.setIcon(new ImageIcon("images\\Buttons\\buttonRight2.png"));
		else if (e.getSource() == exit)
			exit.setBounds(exit.getBounds().x - 30, exit.getBounds().y - 30, exit.getWidth(), exit.getHeight());
		else if (e.getSource() == speakerIcon)
			speakerIcon.setIcon(MediaFormer.getImageIconFitLabel(speakerIcon, "images\\Other\\speakerIconHover.png"));
		else if (e.getSource() == restoreIcon)
			restoreIcon.setIcon(MediaFormer.getImageIconFitLabel(restoreIcon, "images\\Other\\restoreIconHover.png"));
		else if (menuIList.contains(e.getSource())) {
			// -------------------- JMenuItems ---------------------------
			JMenuItem source = (JMenuItem) e.getSource();
			source.setBackground(new Color(159, 162, 162));
		} else if (e.getSource() == changeTypes) {
			changeTypes
					.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon2.png"));
		}
	}

	/**
	 * mouesEntered and mouseExited are used to gain a roll-over effect to
	 * top_buttons and JMenuItems.
	 */
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2)
			lbl_buttonLeft.setIcon(new ImageIcon("images\\Buttons\\buttonLeft.png"));
		else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2)
			lbl_buttonRight.setIcon(new ImageIcon("images\\Buttons\\buttonRight.png"));
		else if (e.getSource() == exit)
			exit.setBounds(exit.getBounds().x + 30, exit.getBounds().y + 30, exit.getWidth(), exit.getHeight());
		else if (e.getSource() == restoreIcon)
			restoreIcon.setIcon(MediaFormer.getImageIconFitLabel(restoreIcon, "images\\Other\\restoreIcon.png"));
		else if (e.getSource() == speakerIcon)
			speakerIcon.setIcon(MediaFormer.getImageIconFitLabel(speakerIcon, "images\\Other\\speakerIcon.png"));
		// -------------------- JMenuItems ---------------------------
		else if (menuIList.contains(e.getSource())) {
			JMenuItem source = (JMenuItem) e.getSource();
			source.setBackground(new Color(51, 51, 51));
		} else if (e.getSource() == changeTypes)
			changeTypes.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon.png"));
	}

	/**
	 * All buttons behavior.
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (menuIList.contains(e.getSource())) {
			// set default color to JMenuItem when its pressed
			JMenuItem source = (JMenuItem) e.getSource();
			source.setBackground(new Color(51, 51, 51));
		}
		if (e.getSource() == soundTimer) // controls when is possible to play sounds
			runSoundTimer();
		else if (e.getSource() == welcomeTimer) // controls welcome animation
			runWelcomeTimer();
		else if (e.getSource() == welcomeTimerTwo) { // when this timer is called, dispose animation components
			welcomeMessage.setVisible(false);
			welcomeTimerTwo.stop();
		} else if (e.getSource() == mNew_new) { // New -> new --------- MENUS
			// prepare view
			emptyFields();
			modeLbl.setText("ADD MODE ON");
			setReadyToEdit();
			search.setEnabled(false);
			mEdit_on.setEnabled(false);
			mEdit_on.setText("Enable edit mode.");
			mEdit_save.setEnabled(false);
			mEdit_Remove.setEnabled(false);
			mNew_save.setEnabled(true);
			mNew_discard.setEnabled(true);
			restoreIcon.setVisible(false);
			// prepare newPokemon
			ArrayList<PokeType> newPokemonTypes = new ArrayList<PokeType>();
			for (PokeType type : typeList)
				if (type.getName().equals("Normal"))
					newPokemonTypes.add(type);
			newPokemon = new Pokemon(pokeList.get(pokeList.size() - 1).getIdP() + 1, "NewPokemon", 0, "", "", "", 0, 0,
					"", newPokemonTypes, new int[6]);
			pokeList.add(newPokemon); // add newPokemon
			listController = pokeList.size() - 1; // set listController to newPokemon index
			// prepare newPokemon media
			pokeImageSource = "images\\Pokemons\\" + pokeList.get(listController).getName() + ".png";
			pokeImageDefaultSource = "images\\Pokemons_restore_default\\" + pokeList.get(listController).getName()
					+ ".png";
			// show default type (normal)
			showTypes();
		} else if (e.getSource() == mNew_save) { // New -> save
			verifyAllFields();
			verifyDescription();
			verifyDoubleFields();
			if (!verifyAllFields()) // something wrong
				JOptionPane.showMessageDialog(null, "You must fill all fields correctly.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			else { // successful
				if(checkName() && checkId()) {
					updatePokemon();
					pokeDAO.insertPokemon(pokeList.get(listController));
					pokeImageSource = "images\\Pokemons\\" + pokeList.get(listController).getName() + ".png";
					pokeImageDefaultSource = "images\\Pokemons_restore_default\\" + pokeList.get(listController).getName()
							+ ".png";
					File origin;
					try {
						if (!isPokeImageEdited) {
							origin = new File("images\\Other\\unknown.png");
							MediaFormer.copyFile(origin, getCurrentImage());
						} else {
							origin = getCurrentImage();
						}
						MediaFormer.copyFile(origin, getDefaultImage());
						JOptionPane.showMessageDialog(null, "Successful creation!.", "Info message",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						e1.fillInStackTrace();
					}
					File toDelete = new File("images\\Pokemons\\NewPokemon.png");
					toDelete.delete();
					toDelete = new File("images\\Pokemons_restore_default\\NewPokemon.png");
					toDelete.delete();
					setReadyToShow();
				} else if (!checkName())
					JOptionPane.showMessageDialog(null, "This name is not available, try another.", "Info message",
							JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "This number is not available, try another.", "Info message",
							JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == mNew_discard) { // New -> discard
			listController--;
			pokeList.remove(newPokemon);
			setReadyToShow();
			File toDelete = new File("images\\Pokemons\\NewPokemon.png");
			toDelete.delete();
		} else if (e.getSource() == mEdit_on) { // Edit -> on
			if (!isEditOn) {
				setReadyToEdit();
				backupTypeList = (ArrayList<PokeType>) pokeList.get(listController).getTypes().clone();
				modeLbl.setText("EDIT MODE ON");
			} else {
				pokeList.get(listController).setTypes(backupTypeList);
				backupTypeList.clear();
				JOptionPane.showMessageDialog(null, "Changes discared");
				setDefaultToCurrent();
				setReadyToShow();
			}
		} else if (e.getSource() == mEdit_save) { // Edit -> save
			if (!verifyAllFields())
				JOptionPane.showMessageDialog(null, "You must fill all fields correctly.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			else {
				if(checkName() && checkId()) {
					updatePokemon();
					pokeDAO.updatePokemon(pokeList.get(listController));
					JOptionPane.showMessageDialog(null, "Successful edition!.", "Info message",
							JOptionPane.INFORMATION_MESSAGE);
					setReadyToShow();
				} else if (!checkName())
					JOptionPane.showMessageDialog(null, "This name is not available, try another.", "Info message",
							JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "This number is not available, try another.", "Info message",
							JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == mEdit_Remove) { // Edit -> remove
			String msg = "You are about to delete current Pokemon! Do you want to continue?.";
			Object[] msgContent = {msg};
			int n = JOptionPane.showConfirmDialog(null, msgContent, "Deletion", JOptionPane.OK_CANCEL_OPTION);
			if (n == 0) {
				pokeDAO.deletePokemon(pokeList.get(listController));
				File toDelete = getCurrentImage();
				toDelete.delete();
				toDelete = getDefaultImage();
				toDelete.delete();
				toDelete = new File("Sounds\\" + pokeList.get(listController).getName() + ".wav");
				toDelete.delete();
				pokeList.remove(pokeList.get(listController));
				listController = 0;
				setReadyToShow();
				JOptionPane.showMessageDialog(null, "Pokemon removed successfully");
			}
		} else if (e.getSource() == search) { // search Pokemon
			System.out.println(isSearchOn);
			if (!isSearchOn) // turn on search
				new SearchView(this, pokeList, typeList);
			else { // turn off search
				isSearchOn = false;
				mNew_new.setEnabled(true);
				search.setText("Search pokemon");
				searchLbl.setVisible(false);
				searchIcon.setVisible(false);
				pokeList = backupPokeList;
				listController = 0;
				showPokemon();
			}
		}
	}

	/**
	 * This method verify if current Pokemon name is available in pokeDB.
	 * 
	 * @returns false If name is not available
	 * @returns true If name is available
	 */
	private boolean checkName() {
		for (Pokemon pokemon : pokeList)
			if(pokemon != pokeList.get(listController))
				if(currentName.getText().equals(pokemon.getName()))
					return false;
		return true;
	}
	
	/**
	 * This method verify if current Pokemon id is available in pokeDB.
	 * 
	 * @returns false if id is not available
	 * @returns true if id is available
	 */
	private boolean checkId() {
		for (Pokemon pokemon : pokeList)
			if(pokemon != pokeList.get(listController))
				if(Integer.parseInt(currentNumber.getText()) == pokemon.getNumber())
					return false;
		return true;
	}

	/**
	 * When this method is called, current Pokemon (listController) is updated in
	 * pokeDB and as a java object. Field content is used to get new attributes.
	 */
	private void updatePokemon() {
		Pokemon current = pokeList.get(listController);
		if (!current.getName().equals(currentName.getText())) {
			updatePokemonMedia(current.getName(), StringUtils.normalizarString(currentName.getText()));
			current.setName(currentName.getText());
		}
		current.setNumber(Integer.parseInt(currentNumber.getText()));
		current.setDescription(description.getText());
		current.setSkill(prop_skillV.getText());
		current.setCategory(prop_categoryV.getText());
		current.setHeight(Double.parseDouble(prop_heightV.getText()));
		current.setWeight(Double.parseDouble(prop_weightV.getText()));
		current.setSex(prop_sexV.getText());
		int[] baseAtt = new int[6];
		for (int i = 0; i < baseAtt.length; i++)
			baseAtt[i] = Integer.parseInt(attIntputList.get(i).getText());
		current.setBaseAtt(baseAtt);
		backupTypeList = current.getTypes();
	}

	/**
	 * Used when updating a Pokemon. This method update correspondent Pokemon media.
	 * All Pokemon files contain its name and appropriate extension.
	 * 
	 * @param oldName of current Pokemon needed to update files
	 * @param newName of current Pokemon needed to update files
	 */
	private void updatePokemonMedia(String oldName, String newName) {
		File oldFile = new File("images\\Pokemons\\" + oldName + ".png");
		File newFile = new File("images\\Pokemons\\" + newName + ".png");
		oldFile.renameTo(newFile);
	}

	/**
	 * KeyRealeased controls field verification.
	 * 
	 * @param e Event which contains source.
	 */
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code != KeyEvent.VK_RIGHT && code != KeyEvent.VK_LEFT && code != KeyEvent.VK_UP && code != KeyEvent.VK_DOWN
				&& code != KeyEvent.VK_CAPS_LOCK && code != KeyEvent.SHIFT_DOWN_MASK && code != KeyEvent.VK_SHIFT)
			if (attIntputList.contains(e.getSource()))
				verifyAttFields();
			else if (e.getSource() == prop_heightV || e.getSource() == prop_weightV)
				verifyDoubleFields();
			else if (e.getSource() == currentNumber)
				verifyCurrentNumber();
			else if (e.getSource() == description)
				verifyDescription();
			else if (strFields.contains(e.getSource()))
				verifyStrFields();
			else if (e.getSource() == currentName)
				verifyCurrentName();
	}

	/**
	 * This method check if all input fields are stored in redyFields (this mean all
	 * fields are ready to process).
	 * 
	 * @returns true If all fields are stored in readtFields
	 * @returns false If any field is not stored in readyFields
	 */
	public boolean verifyAllFields() {
		verifyAttFields();
		verifyCurrentName();
		verifyCurrentNumber();
		verifyStrFields();
		if (!readyFields.contains(currentName))
			return false;
		if (!readyFields.contains(currentNumber))
			return false;
		if (!readyFields.containsAll(attIntputList))
			return false;
		if (!readyFields.containsAll(strFields))
			return false;
		if (!readyFields.containsAll(doubleFields))
			return false;
		if (!isDescriptionReady)
			return false;
		return true;
	}

	/**
	 * This method verifies if <strong>attIntputList</strong> fields contain only
	 * Integer characters. It warn user by changing its background.
	 * 
	 */
	private void verifyAttFields() {
		for (JTextField source : attIntputList) {
			boolean isValid = StringUtils.checkInt(source.getText());
			if (!isValid) {
				source.setBackground(new Color(202, 129, 129));
				readyFields.remove(source);
			} else {
				int newInt = Integer.parseInt(source.getText());
				if (newInt < 1) {
					newInt = 1;
					source.setText(Integer.toString(newInt));
				} else if (newInt > 9) {
					newInt = 9;
					source.setText(Integer.toString(newInt));
				}
				source.setBackground(new Color(139, 139, 139));
				if (!readyFields.contains(source))
					readyFields.add(source);
			}
		}
	}

	/**
	 * This method verifies height and weight to be Double. It warns user by
	 * changing its background or foreground.
	 * 
	 */
	private void verifyDoubleFields() {
		for (JTextField source : doubleFields) {
			if (!source.getText().trim().isBlank()) {
				source.setBackground(new Color(48, 167, 215));
				boolean isValid = StringUtils.checkDouble(source.getText());
				if (!isValid) {
					source.setForeground(Color.red);
					readyFields.remove(source);
				} else {
					source.setForeground(new Color(77, 77, 77));
					if (!readyFields.contains(source))
						readyFields.add(source);
				}
			} else {
				readyFields.remove(source);
				source.setBackground(new Color(202, 129, 129));
			}
		}
	}
	
	/**
	 * This method verifies currentNumber. It should not be blank or contain no
	 * Integer characters.
	 */
	private void verifyCurrentNumber() {
		if (!currentNumber.getText().trim().isBlank()) {
			currentNumber.setBackground(Color.white);
			boolean isValid = StringUtils.checkInt(currentNumber.getText());
			if (!isValid) {
				currentNumber.setForeground(Color.red);
				readyFields.remove(currentNumber);
			} else {
				currentNumber.setForeground(new Color(103, 107, 107));
				if (!readyFields.contains(currentNumber))
					readyFields.add(currentNumber);
			}
		} else {
			currentNumber.setBackground(new Color(202, 129, 129));
			readyFields.remove(currentNumber);
		}
	}

	/**
	 * This method verifies description. Description must be at least 30 character
	 * length.
	 * 
	 * It warns the user to fulfill rules.
	 */
	private void verifyDescription() {
		description.setText(containsQuotes(description.getText()));
		int length = description.getText().trim().length();
		if (length < 30) {
			if (description.getText().isBlank())
				description.setBackground(new Color(202, 129, 129));
			else
				description.setBackground(Color.white);
			description.setForeground(Color.red);
			isDescriptionReady = false;
			descriptionRules.setVisible(true);
		} else if (length > 200) {
			description.setBackground(new Color(202, 129, 129));
			description.setText(description.getText().substring(0, 200));
		} else {
			description.setBackground(Color.white);
			description.setForeground(Color.black);
			isDescriptionReady = true;
			descriptionRules.setVisible(false);
		}
	}
	
	/**
	 * Method that receives an String. If this string contains a character with a
	 * non-valid numeric value (check if conditional) is removed from it.
	 * 
	 * @param strToCheck string to clean
	 * @return clean string
	 */
	private String containsQuotes(String strToCheck) {
		boolean isValid = true;
		do {
			for (int i = 0; i < strToCheck.length(); i++) {
				isValid = true;
				int num = (int) strToCheck.toUpperCase().charAt(i);
				if (num != 32 && num != 44 && num != 46 && num != 165 && (num < 65 || num > 90)) {
					if (strToCheck.length() < 2)
						strToCheck = "";
					else {
						String left = strToCheck.substring(0, i);
						String right = strToCheck.substring(i + 1, strToCheck.length());
						strToCheck = left + right;
						isValid = false;
					}
					break;
				}
			}
		} while (!isValid);
		return strToCheck;
	}

	/**
	 * This method verifies strFields are not blank. It warns the user to fill this
	 * fields.
	 */
	private void verifyStrFields() {
		for (JTextField source : strFields) {
			source.setText(containsQuotes(source.getText()));
			if (source.getText().trim().isBlank()) {
				source.setBackground(new Color(202, 129, 129));
				readyFields.remove(source);
			} else {
				source.setBackground(new Color(48, 167, 215));
				if (!readyFields.contains(source))
					readyFields.add(source);
			}
		}
	}

	/**
	 * This method verifies currentName is not blank. It warns the user to fill this
	 * field.
	 */
	private void verifyCurrentName() {
		currentName.setText(containsQuotes(currentName.getText()));
		if (currentName.getText().trim().isBlank()) {
			currentName.setBackground(new Color(202, 129, 129));
			readyFields.remove(currentName);
		} else {
			currentName.setBackground(Color.white);
			if (!readyFields.contains(currentName)) {
				readyFields.add(currentName);
			}
			currentName.setBackground(Color.white);
		}
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
	 * component is classified in a specific sector of the frame. There's six main
	 * sectors:
	 * - Top_Labels (header).
	 * - Blue_Box Labels (first info sector, it shows main characteristics).
	 * - Under Blue_Box Buttons (second info sector, it shows the types).
	 * - Gray_Box Labels (third info sector, it shows base attributes).
	 */
	public void initializeUIComponentes() {
		// WELCOME Animation components -------------------------------------
		welcomeMessage = new JButton("WELCOME " + userName + "!");
		welcomeMessage.setBounds(-300, this.getBounds().height / 2 - 250, 400, 400);
		welcomeMessage.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		welcomeMessage.setBackground(new Color(0, 0, 0, 0));
		welcomeMessage.setBorder(null);
		welcomeMessage.setEnabled(false);
		welcomeMessage.setFont(new Font("Flexo-Medium", Font.BOLD, 36));
		welcomeMessage.setForeground(Color.WHITE);
		welcomeMessage
				.setDisabledIcon(MediaFormer.getImageIconFitLabel(welcomeMessage, "images\\Other\\welcomeIcon.png"));
		welcomeMessage.setIcon(MediaFormer.getImageIconFitLabel(welcomeMessage, "images\\Other\\welcomeIcon.png"));
		welcomeMessage.setVerticalTextPosition(SwingConstants.CENTER);
		welcomeMessage.setHorizontalTextPosition(SwingConstants.CENTER);
		welcomeMessage.repaint();
		// HEADDER ---------------------------------------------------------
		// MenuBar
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(51, 51, 51));
		// Menu new
		menu_new = new JMenu("    New    "); // Menu 1
		menu_new.setForeground(Color.white);
		// Menu edit
		menu_edit = new JMenu("   Edit   ");
		menu_edit.setForeground(Color.white);
		menuIList = new ArrayList<JMenuItem>();
		// SEARCH Menu_Button
		search = new JButton("Search pokemon");
		search.setBackground(new Color(51, 51, 51));
		search.setForeground(Color.white);
		search.setBorder(null);
		search.addActionListener(this);
		menuBar.add(search);
		// "NEW" Menu
		// mNew New
		mNew_new = new JMenuItem("Add new Pokemon.") { // 1
			private static final long serialVersionUID = 1L;

			public void setArmed(boolean b) { // Default hover effect is disabled now
			}
		};
		mNew_new.setBackground(new Color(51, 51, 51));
		mNew_new.setForeground(Color.white);
		mNew_new.addMouseListener(this);
		mNew_new.addActionListener(this);
		menuIList.add(mNew_new);
		// mNew Save
		mNew_save = new JMenuItem("Save current new Pokemon.") { // 1
			private static final long serialVersionUID = 1L;

			public void setArmed(boolean b) { // Default hover effect is disabled now
			}
		};
		mNew_save.setBackground(new Color(51, 51, 51));
		mNew_save.setForeground(Color.white);
		mNew_save.addMouseListener(this);
		mNew_save.addActionListener(this);
		menuIList.add(mNew_save);
		// mNew Discard
		mNew_discard = new JMenuItem("Discard current new Pokemon.") { // 1
			private static final long serialVersionUID = 1L;

			public void setArmed(boolean b) { // Default hover effect is disabled now
			}
		};
		mNew_discard.setBackground(new Color(51, 51, 51));
		mNew_discard.setForeground(Color.white);
		mNew_discard.addMouseListener(this);
		mNew_discard.addActionListener(this);
		menuIList.add(mNew_discard);
		// "EDIT" Menu
		// mEdit On
		mEdit_on = new JMenuItem("Enable edit mode.") { // Item 1
			private static final long serialVersionUID = 2L;

			public void setArmed(boolean b) {
			}
		};
		mEdit_on.setBackground(new Color(51, 51, 51));
		mEdit_on.setForeground(Color.white);
		mEdit_on.addMouseListener(this);
		mEdit_on.addActionListener(this);
		menuIList.add(mEdit_on);
		// mEdit Save
		mEdit_save = new JMenuItem("Save changes and disable edit mode.") { // 1
			private static final long serialVersionUID = 3L;

			public void setArmed(boolean b) {
			}
		};
		mEdit_save.setBackground(new Color(51, 51, 51));
		mEdit_save.setForeground(Color.white);
		mEdit_save.addMouseListener(this);
		mEdit_save.addActionListener(this);
		menuIList.add(mEdit_save);
		// mEdit Delete
		mEdit_Remove = new JMenuItem("Delete current Pokemon.") { // 2
			private static final long serialVersionUID = 4L;

			public void setArmed(boolean b) {
			}
		};
		mEdit_Remove.setBackground(new Color(51, 51, 51));
		mEdit_Remove.setForeground(Color.white);
		mEdit_Remove.addMouseListener(this);
		mEdit_Remove.addActionListener(this);
		menuIList.add(mEdit_Remove);
		// Menu connections
		menuBar.add(menu_new);
		menuBar.add(menu_edit);
		menu_new.add(mNew_new);
		menu_new.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu_new.add(mNew_save);
		menu_new.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu_new.add(mNew_discard);
		menu_edit.add(mEdit_on);
		menu_edit.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu_edit.add(mEdit_save);
		menu_edit.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu_edit.add(mEdit_Remove);
		setJMenuBar(menuBar);
		// TOP ---------------------------------------------------------
		searchIcon = new JLabel();
		searchIcon.setBounds(466, 5, 34, 34);
		searchIcon.setIcon(MediaFormer.getImageIconFitLabel(searchIcon, "images\\Other\\searchIcon.png"));
		searchIcon.setVisible(false);
		searchLbl = new JLabel("SEARCH MODE ON");
		searchLbl.setBounds(438, 100, 160, 30);
		searchLbl.setFont(new Font("Flexo-Regular", NORMAL, 16));
		searchLbl.setForeground(new Color(45, 45, 45));
		searchLbl.setVisible(false);
		// EditMode
		modeLbl = new JLabel();
		modeLbl.setBounds(getBounds().width / 2 - 115, 15, 330, 50);
		modeLbl.setFont(new Font("Flexo-Regular", NORMAL, 36));
		modeLbl.setVisible(false);
		// Previous pokemon name
		previousName = new JLabel();
		previousName.setBounds(317, 5, 120, 50);
		previousName.setFont(new Font("Flexo-Regular", NORMAL, 26));
		previousName.setHorizontalAlignment(SwingConstants.LEADING);
		previousName.setForeground(new Color(103, 97, 97));
		// Previous pokemon number
		previousNumber = new JLabel();
		previousNumber.setBounds(237, 5, 100, 50);
		previousNumber.setFont(new Font("Flexo-Regular", Font.BOLD, 24));
		previousNumber.setHorizontalAlignment(SwingConstants.LEADING);
		previousNumber.setForeground(Color.white);
		// Next pokemon name
		nextName = new JLabel();
		nextName.setBounds(545, 5, 120, 50);
		nextName.setFont(new Font("Flexo-Regular", NORMAL, 26));
		nextName.setHorizontalAlignment(SwingConstants.LEADING);
		nextName.setForeground(new Color(103, 97, 97));
		// Next pokemon number
		nextNumber = new JLabel();
		nextNumber.setBounds(646, 5, 100, 50);
		nextNumber.setFont(new Font("Flexo-Regular", Font.BOLD, 24));
		nextNumber.setHorizontalAlignment(SwingConstants.LEADING);
		nextNumber.setForeground(Color.white);
		// Current pokemon name
		UIManager.put("TextField.inactiveBackground", new ColorUIResource(new Color(255, 255, 255)));
		currentName = new JTextField();
		currentName.setBounds(340, 55, 130,45);
		currentName.setBorder(null);
		currentName.setFont(new Font("Flexo-Regular", WIDTH, 26));
		currentName.setEditable(false);
		currentName.addKeyListener(this);
		// NumberSign
		numberSign = new JLabel("Nº.");
		numberSign.setBounds(500, 55, 50, 50);
		numberSign.setFont(new Font("Flexo-Regular", WIDTH, 26));
		numberSign.setForeground(new Color(103, 107, 107));
		// Current pokemon number
		currentNumber = new JTextField();
		currentNumber.setBounds(545, 55, 100, 50);
		currentNumber.setBorder(null);
		currentNumber.setFont(new Font("Flexo-Regular", WIDTH, 26));
		currentNumber.setEditable(false);
		currentNumber.setForeground(new Color(103, 107, 107));
		currentNumber.addKeyListener(this);
		// Description
		description = new JTextArea();
		description.setBounds(430, 135, 390, 100);
		description.setFont(new Font("Flexo-Regular", NORMAL, 15));
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setEditable(false);
		description.addKeyListener(this);
		// Description rules
		descriptionRules = new JLabel("Description must be at least 30 characters length :)");
		descriptionRules.setBounds(430, 115, 387, 20);
		descriptionRules.setFont(new Font("Flexo-Regular", NORMAL, 16));
		descriptionRules.setForeground(Color.DARK_GRAY);
		descriptionRules.setVisible(false);
		// PokeImage
		pokeImage = new JLabel();
		pokeImage.setBounds(120, 90, 280, 280);
		pokeImage.addMouseListener(this);
		// SpeakerImage
		speakerIcon = new JLabel();
		speakerIcon.setBounds(360, 335, 40, 40);
		speakerIcon.setIcon(MediaFormer.getImageIconFitLabel(speakerIcon, "images\\Other\\speakerIcon.png"));
		speakerIcon.addMouseListener(this);
		// RestoreImage
		restoreIcon = new JLabel();
		restoreIcon.setBounds(120, 333, 40, 40);
		restoreIcon.setIcon(MediaFormer.getImageIconFitLabel(speakerIcon, "images\\Other\\restoreIcon.png"));
		restoreIcon.setToolTipText("Restore default pokeImage");
		restoreIcon.addMouseListener(this);
		// lbl_buttonLeft
		lbl_buttonLeft = new JLabel(new ImageIcon("images\\Buttons\\buttonLeft.png"));
		lbl_buttonLeft.setBounds(107, 0, 372, 79);
		// lbl_buttonLeft formers
		leftButtonFormer1 = new JLabel(); // Rectangle 1
		leftButtonFormer1.setBounds(107, 0, 372, 50);
		leftButtonFormer1.addMouseListener(this);
		leftButtonFormer2 = new JLabel(); // Rectangle 2
		leftButtonFormer2.setBounds(107, 50, 125, 30);
		leftButtonFormer2.addMouseListener(this);
		// lbl_buttonRight
		lbl_buttonRight = new JLabel(new ImageIcon("images\\Buttons\\buttonRight.png"));
		lbl_buttonRight.setBounds(479, 0, 372, 79);
		// lbl_buttonRight formers
		rightButtonFormer1 = new JLabel(); // former 1
		rightButtonFormer1.setBounds(479, 0, 372, 50);
		rightButtonFormer1.addMouseListener(this);
		rightButtonFormer2 = new JLabel(); // former 2
		rightButtonFormer2.setBounds(726, 50, 125, 30);
		rightButtonFormer2.addMouseListener(this);
		// BLUE_BOX -------------------------------------------------------
		// height Title
		prop_heightT = new JLabel("Height (m)");
		prop_heightT.setBounds(438, 265, 120, 20);
		prop_heightT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_heightT.setForeground(Color.white);
		// height Value
		prop_heightV = new JTextField();
		prop_heightV.setBounds(438, 290, 150, 20);
		prop_heightV.setBorder(null);
		prop_heightV.setBackground(new Color(48, 167, 215));
		prop_heightV.setForeground(new Color(77, 77, 77));
		prop_heightV.setDisabledTextColor(new Color(77, 77, 77));
		prop_heightV.setFont(new Font("Flexo-Light", 1, 18));
		prop_heightV.addKeyListener(this);
		prop_heightV.setEditable(false);
		// weight Title
		prop_weightT = new JLabel("Weight (kg)");
		prop_weightT.setBounds(438, 323, 120, 20);
		prop_weightT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_weightT.setForeground(Color.white);
		// weight Value
		prop_weightV = new JTextField();
		prop_weightV.setBounds(438, 348, 150, 20);
		prop_weightV.setBorder(null);
		prop_weightV.setBackground(new Color(48, 167, 215));
		prop_weightV.setForeground(new Color(77, 77, 77));
		prop_weightV.setDisabledTextColor(new Color(77, 77, 77));
		prop_weightV.setFont(new Font("Flexo-Light", 1, 18));
		prop_weightV.addKeyListener(this);
		prop_weightV.setEditable(false);
		// sex Title
		prop_sexT = new JLabel("Sex");
		prop_sexT.setBounds(438, 376, 120, 20);
		prop_sexT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_sexT.setForeground(Color.white);
		// sex Value
		prop_sexV = new JTextField();
		prop_sexV.setBounds(438, 401, 150, 20);
		prop_sexV.setBorder(null);
		prop_sexV.setBackground(new Color(48, 167, 215));
		prop_sexV.setForeground(new Color(77, 77, 77));
		prop_sexV.setDisabledTextColor(new Color(77, 77, 77));
		prop_sexV.setFont(new Font("Flexo-Light", 1, 18));
		prop_sexV.setEditable(false);
		prop_sexV.addKeyListener(this);
		strFields.add(prop_sexV);
		// category Title
		prop_categoryT = new JLabel("Category");
		prop_categoryT.setBounds(638, 265, 120, 20);
		prop_categoryT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_categoryT.setForeground(Color.white);
		// category Value
		prop_categoryV = new JTextField();
		prop_categoryV.setBounds(638, 290, 150, 20);
		prop_categoryV.setBorder(null);
		prop_categoryV.setBackground(new Color(48, 167, 215));
		prop_categoryV.setForeground(new Color(77, 77, 77));
		prop_categoryV.setDisabledTextColor(new Color(77, 77, 77));
		prop_categoryV.setFont(new Font("Flexo-Light", 1, 18));
		prop_categoryV.setEditable(false);
		prop_categoryV.addKeyListener(this);
		strFields.add(prop_categoryV);
		// skill Title
		prop_skillT = new JLabel("Skill");
		prop_skillT.setBounds(638, 323, 120, 20);
		prop_skillT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_skillT.setForeground(Color.white);
		// skill Value
		prop_skillV = new JTextField();
		prop_skillV.setBounds(638, 348, 150, 20);
		prop_skillV.setBackground(new Color(48, 167, 215));
		prop_skillV.setBorder(null);
		prop_skillV.setForeground(new Color(77, 77, 77));
		prop_skillV.setDisabledTextColor(new Color(77, 77, 77));
		prop_skillV.setFont(new Font("Flexo-Light", 1, 18));
		prop_skillV.setEditable(false);
		prop_skillV.addKeyListener(this);
		strFields.add(prop_skillV);
		// UNDER BLUE-BOX --------------------------------------------------
		// typeContainer - for more info CTRL+f on typeContaier
		typeContainer = new JPanel();
		typeContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		typeContainer.setBackground(Color.white);
		typeContainer.setBounds(417, 460, 410, 65);
		// changeTypes button
		changeTypes = new JLabel();
		changeTypes.setBounds(417, 527, 40, 40);
		changeTypes.setBackground(new Color(0, 0, 0, 0));
		changeTypes.setBorder(null);
		changeTypes.setFont(new Font("Flexo-Medium", Font.BOLD, 26));
		changeTypes.setForeground(Color.WHITE);
		changeTypes.setIcon(MediaFormer.getImageIconFitLabel(changeTypes, "images\\Buttons\\changeButtonIcon.png"));
		changeTypes.setVerticalTextPosition(SwingConstants.CENTER);
		changeTypes.setHorizontalTextPosition(SwingConstants.CENTER);
		changeTypes.addMouseListener(this);
		changeTypes.setVisible(false);
		// GRAY-BOX ---------------------------------------------------------
		// ps Title
		lbl_ps = new JLabel("PS");
		lbl_ps.setBounds(130, 385, 50, 20);
		lbl_ps.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_ps.setForeground(Color.white);
		lbl_ps.setHorizontalAlignment(JLabel.CENTER);
		// ps ProgressBar
		ps = new JProgressBar(0, 0, 15);
		ps.setBounds(180, 385, 200, 20);
		ps.setBackground(new Color(164, 164, 164));
		ps.setUI(new FancyProgressBar());
		ps.setBorder(null);
		// ps TextField
		psI = new JTextField();
		psI.setBounds(180, 385, 200, 20);
		psI.setBorder(null);
		psI.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		psI.setBackground(new Color(139, 139, 139));
		psI.setForeground(Color.black);
		psI.setHorizontalAlignment(JLabel.CENTER);
		psI.addKeyListener(this);
		psI.setVisible(false);
		attIntputList.add(psI);
		// att Title
		lbl_att = new JLabel("Attack");
		lbl_att.setBounds(130, 415, 50, 20);
		lbl_att.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_att.setForeground(Color.white);
		lbl_att.setHorizontalAlignment(JLabel.CENTER);
		// att ProgressBar
		att = new JProgressBar(0, 0, 15);
		att.setBounds(180, 415, 200, 20);
		att.setBackground(new Color(164, 164, 164));
		att.setUI(new FancyProgressBar());
		att.setBorder(null);
		// att TextField
		attI = new JTextField();
		attI.setBounds(180, 415, 200, 20);
		attI.setBorder(null);
		attI.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		attI.setBackground(new Color(139, 139, 139));
		attI.setForeground(Color.black);
		attI.setHorizontalAlignment(JLabel.CENTER);
		attI.addKeyListener(this);
		attI.setVisible(false);
		attIntputList.add(attI);
		// def Title
		lbl_def = new JLabel("Defense");
		lbl_def.setBounds(130, 445, 50, 20);
		lbl_def.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_def.setForeground(Color.white);
		lbl_def.setHorizontalAlignment(JLabel.CENTER);
		// def ProgressBar
		def = new JProgressBar(0, 0, 15);
		def.setBounds(180, 445, 200, 20);
		def.setBackground(new Color(164, 164, 164));
		def.setUI(new FancyProgressBar());
		def.setBorder(null);
		// def TextField
		defI = new JTextField();
		defI.setBounds(180, 445, 200, 20);
		defI.setBorder(null);
		defI.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		defI.setBackground(new Color(139, 139, 139));
		defI.setForeground(Color.black);
		defI.setHorizontalAlignment(JLabel.CENTER);
		defI.addKeyListener(this);
		defI.setVisible(false);
		attIntputList.add(defI);
		// satt Title
		lbl_satt = new JLabel(
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>" + "<p>Special<br>Attack</p>" + "<html>");
		lbl_satt.setBounds(130, 473, 50, 30);
		lbl_satt.setFont(new Font("Flexo-Light", Font.BOLD, 10));
		lbl_satt.setForeground(Color.white);
		lbl_satt.setHorizontalAlignment(JLabel.CENTER);
		// satt ProgressBar
		satt = new JProgressBar(0, 0, 15);
		satt.setBounds(180, 475, 200, 20);
		satt.setBackground(new Color(164, 164, 164));
		satt.setUI(new FancyProgressBar());
		satt.setBorder(null);
		// satt TextField
		sattI = new JTextField();
		sattI.setBounds(180, 475, 200, 20);
		sattI.setBorder(null);
		sattI.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		sattI.setBackground(new Color(139, 139, 139));
		sattI.setForeground(Color.black);
		sattI.setHorizontalAlignment(JLabel.CENTER);
		sattI.addKeyListener(this);
		sattI.setVisible(false);
		attIntputList.add(sattI);
		// sdef Title
		lbl_sdef = new JLabel(
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>" + "<p>Special<br>Defense</p>" + "<html>");
		lbl_sdef.setBounds(130, 503, 50, 30);
		lbl_sdef.setFont(new Font("Flexo-Light", Font.BOLD, 10));
		lbl_sdef.setForeground(Color.white);
		lbl_sdef.setHorizontalAlignment(JLabel.CENTER);
		// sdef ProgressBar
		sdef = new JProgressBar(0, 0, 15);
		sdef.setBounds(180, 505, 200, 20);
		sdef.setBackground(new Color(164, 164, 164));
		sdef.setUI(new FancyProgressBar());
		sdef.setBorder(null);
		// sdef TextField
		sdefI = new JTextField();
		sdefI.setBounds(180, 505, 200, 20);
		sdefI.setBorder(null);
		sdefI.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		sdefI.setBackground(new Color(139, 139, 139));
		sdefI.setForeground(Color.black);
		sdefI.setHorizontalAlignment(JLabel.CENTER);
		sdefI.addKeyListener(this);
		sdefI.setVisible(false);
		attIntputList.add(sdefI);
		// spped Title
		lbl_speed = new JLabel("Speed");
		lbl_speed.setBounds(130, 535, 50, 20);
		lbl_speed.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_speed.setForeground(Color.white);
		lbl_speed.setHorizontalAlignment(JLabel.CENTER);
		// speed ProgressBar
		speed = new JProgressBar(0, 0, 15);
		speed.setBounds(180, 535, 200, 20);
		speed.setBackground(new Color(164, 164, 164));
		speed.setUI(new FancyProgressBar());
		speed.setBorder(null);
		// speed TextField
		speedI = new JTextField();
		speedI.setBounds(180, 535, 200, 20);
		speedI.setBorder(null);
		speedI.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		speedI.setBackground(new Color(139, 139, 139));
		speedI.setForeground(Color.black);
		speedI.setHorizontalAlignment(JLabel.CENTER);
		speedI.addKeyListener(this);
		speedI.setVisible(false);
		attIntputList.add(speedI);
		// FOOTER ----------------------------------------------------------
		// Quit button - Exit app
		exit = new JButton("Quit");
		exit.setBounds(this.getBounds().width - 140, this.getBounds().height - 200, 240, 240);
		exit.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		exit.setBackground(new Color(0, 0, 0, 0));
		exit.setBorder(null);
		exit.setEnabled(false);
		exit.setFont(new Font("Flexo-Medium", Font.BOLD, 26));
		exit.setForeground(Color.WHITE);
		exit.setDisabledIcon(MediaFormer.getImageIconFitLabel(exit, "images\\Other\\welcomeIcon.png"));
		exit.setIcon(MediaFormer.getImageIconFitLabel(exit, "images\\Other\\welcomeIcon.png"));
		exit.setVerticalTextPosition(SwingConstants.CENTER);
		exit.setHorizontalTextPosition(SwingConstants.CENTER);
		exit.addMouseListener(this);
		// ADD COMPONENTS
		contentPane.add(searchLbl);
		contentPane.add(searchIcon);
		contentPane.add(changeTypes);
		contentPane.add(welcomeMessage);
		contentPane.add(numberSign);
		contentPane.add(modeLbl);
		contentPane.add(previousName);
		contentPane.add(previousNumber);
		contentPane.add(nextName);
		contentPane.add(nextNumber);
		contentPane.add(currentName);
		contentPane.add(currentNumber);
		contentPane.add(description);
		contentPane.add(descriptionRules);
		contentPane.add(speakerIcon);
		contentPane.add(restoreIcon);
		contentPane.add(pokeImage);
		contentPane.add(lbl_buttonLeft);
		contentPane.add(leftButtonFormer1);
		contentPane.add(leftButtonFormer2);
		contentPane.add(lbl_buttonRight);
		contentPane.add(rightButtonFormer1);
		contentPane.add(rightButtonFormer2);
		contentPane.add(prop_heightT);
		contentPane.add(prop_heightV);
		contentPane.add(prop_weightT);
		contentPane.add(prop_weightV);
		contentPane.add(prop_sexT);
		contentPane.add(prop_sexV);
		contentPane.add(prop_categoryT);
		contentPane.add(prop_categoryV);
		contentPane.add(prop_skillT);
		contentPane.add(prop_skillV);
		contentPane.add(lbl_ps);
		contentPane.add(psI);
		contentPane.add(ps);
		contentPane.add(lbl_att);
		contentPane.add(attI);
		contentPane.add(att);
		contentPane.add(lbl_def);
		contentPane.add(defI);
		contentPane.add(def);
		contentPane.add(lbl_satt);
		contentPane.add(sattI);
		contentPane.add(satt);
		contentPane.add(lbl_sdef);
		contentPane.add(sdefI);
		contentPane.add(sdef);
		contentPane.add(lbl_speed);
		contentPane.add(speedI);
		contentPane.add(speed);
		contentPane.add(exit);
		contentPane.add(typeContainer);
		// BACKGROUND -----------------------------------------------------------
		background = new JLabel(new ImageIcon("images\\Background\\pokedexBackground.png"));
		background.setBounds(-5, -13, 953, 659);
		contentPane.add(background);
	}

	// Unused Overridden methods
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
}
