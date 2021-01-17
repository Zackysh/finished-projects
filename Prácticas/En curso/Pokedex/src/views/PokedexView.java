package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
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
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel background;

	// MENU_BAR, MENUS, MENU_ITEMS -----------
	// This list is used to give certain behavior to all at the
	// same time
	private List<JMenuItem> menuIList;
	private JMenuBar menuBar;
	private JMenu menu_new;
	private JMenuItem mNew_new, mNew_save, mNew_discard;
	private JMenu menu_edit;
	private JMenuItem mEdit_on, mEdit_save, mEdit_discard;
	private Color menuDefaultColor = new Color(51, 51, 51);
	private Color menuHoverColor = new Color(159, 162, 162);
	private Color foreground;

	// MENU_CONTROLLERS ----------------------
	private boolean isEditOn; // limit user interaction when its on

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

	// TOP-LABELS ---------------------------
	private JLabel editMode;
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
	private JLabel speaker;
	private JLabel restore;

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

	// GRAY-BOX ----------------------------
	// This list is used on keyReleased to control only attribute input TextFields,
	// its neccesary to
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
	private ArrayList<Pokemon> pokeList = new ArrayList<Pokemon>();
	private ArrayList<PokeType> typeList = new ArrayList<PokeType>();
	private int listController = 0;
	// Add/Edit control
	private List<JTextField> readyFields;
	private List<JTextField> strFields;
	private JLabel descRules;
	private boolean isDescriptionReady;
	// Sound Control
	private boolean isSoundEnable = true;
	Timer soundTimer;

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
		new SelectTypes(typeList);
//		welcome();
		// Rest of the logic continue on showPokemon() method
		// and event listener (Action/Mouse listeners)
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
					for (PokeType pokeType : typeList) {

						if (pokeType.getName().equals(rsTypes.getString("name"))) {

							types.add(pokeType);

						}

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
	 * Method that animates Welcome message at the beginning.
	 */
	public void welcome() {
		animOne();
		welcomeTimer.start();
	}

	/*
	 * First loop of the animation.
	 */
	private void animTwo() {
		welcomeTimerTwo.start();
		Point punto = new Point(940, (int) welcomeMessage.getBounds().getY());
		animate(welcomeMessage, punto, 150, 2);
	}

	/**
	 * Second loop of the animation.
	 */
	private void animOne() {
		Point punto = new Point((int) this.getBounds().getWidth() / 2 - 150, (int) welcomeMessage.getBounds().getY());
		animate(welcomeMessage, punto, 150, 2);
	}

	/**
	 * Method that animate a JComponent updating the position if it over time.
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
				else {
					((Timer) e.getSource()).stop();
				}
			}
		}).start();
	}

	/**
	 * Method that control whole filling process to show all Pokemon information on
	 * the panel. It shows listController associated pokemon. Instead of receiving
	 * Pokemon id as int parameter, it access to listController.
	 * 
	 * His task is subdivided on methods specialized on complete a specific
	 * sub-task.
	 */
	public void showPokemon() {
		Pokemon current = pokeList.get(listController);
//		pokeDAO.updatePokemon(current.getIdP(), current.getName(), current.getNumber(), current.getDescription(),
//				current.getSkill(), current.getCategory(), current.getHeight(), current.getWeight(), current.getSex(),
//				current.getTypes(), current.getBaseAtt());
		
		showHeadder(current);
		description.setText(pokeList.get(listController).getDescription());
		showBlueBox(current);
		showGrayBox(current);
		showTypes(current);
		pokeImageSource = "images\\Pokemons\\" + pokeList.get(listController).getName() + ".png";
		pokeImageDefaultSource = "images\\Pokemons_restore_default\\" + pokeList.get(listController).getName() + ".png";
		showPokeImage();
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
	private void showTypes(Pokemon current) {
		ScrollPaneGenerator.scrollablePanelForTypes(current.getTypes(), typeContainer);
		typeContainer.repaint();
		for (PokeType type : current.getTypes()) {
			System.out.println(type);
		}
		System.out.println();
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

		// CURRENT INFO
		// Always initialized int the same way
		String currName = current.getName();
		String currNumber = Integer.toString(current.getNumber());

		// PREVIOUS INFO
		String prevName;
		String prevNumber;

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

		currentName.setText(currName);
		currentNumber.setText(currNumber);

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
	}

	/**
	 * Configure components to work as exhibitor view instead of edit view.
	 */
	private void setReadyToEdit() {
		// Setting JMenuBar
		mNew_new.setEnabled(false);
		mNew_discard.setEnabled(false);
		mNew_save.setEnabled(false);
		mEdit_on.setText("Disable edit mode");		
		mEdit_save.setEnabled(true);
		mEdit_discard.setEnabled(true);
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
		restore.setVisible(true);
		// Setting BLUE-BOX
		prop_categoryV.setEditable(true);
		prop_heightV.setEditable(true);
		prop_sexV.setEditable(true);
		prop_skillV.setEditable(true);
		prop_weightV.setEditable(true);
		currentName.setEditable(true);
		currentNumber.setEditable(true);
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
		
		editMode.setVisible(true);
	}

	/**
	 * Configure components to work as edit view instead of simple exhibitor view.
	 */
	private void setReadyToShow() {
		// Setting JMenuBar
		mEdit_save.setEnabled(false);
		mEdit_discard.setEnabled(false);
		mNew_save.setEnabled(false);
		mNew_discard.setEnabled(false);
		mNew_new.setEnabled(true);
		editMode.setVisible(false);
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
		restore.setVisible(false);
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
		showPokemon(); // reset fields with current pokemon info
		isEditOn = false; // used to limit user interaction
		
		for (JTextField jTextField : strFields)
			verifyStrFields(jTextField);
		for (JTextField jTextField : attIntputList)
			jTextField.setBackground(new Color(139, 139, 139));
		verifyDoubleFields(prop_weightV);
		verifyDoubleFields(prop_heightV);
		verifyCurrentName();
		verifyCurrentNumber();
		verifyDescription();
	}

	private void emptyFields() {
		prop_categoryV.setText("...");
		prop_heightV.setText("...");
		prop_sexV.setText("...");
		prop_skillV.setText("...");
		prop_weightV.setText("...");
		description.setText("...");
		currentName.setText("...");
		currentNumber.setText("...");
		ps.setValue(0);
		att.setValue(0);
		satt.setValue(0);
		def.setValue(0);
		sdef.setValue(0);
		speed.setValue(0);
		pokeImage.setIcon(MediaFormer.getImageIconFitLabel(pokeImage, "images\\Other\\clip.png"));
	}

	/**
	 * Current --> Default
	 */
	private void setCurrentToDefault() {
		File dest = new File(pokeImageDefaultSource);
		dest.delete();
		dest = new File(pokeImageDefaultSource);
		try {
			MediaFormer.copyFile(getCurrentImage(), dest);
		} catch (IOException e1) {
		}
	}

	/**
	 * Default --> Current
	 */
	private void setDefaultToCurrent() {
		File dest = new File(pokeImageSource);
		dest.delete();
		dest = new File(pokeImageSource);
		try {
			MediaFormer.copyFile(getDefaultImage(), dest);
		} catch (IOException e1) {
		}
	}

	private File getCurrentImage() {
		return new File(pokeImageSource);
	}

	private File getDefaultImage() {
		return new File(pokeImageDefaultSource);
	}

	private void setDefaultImage(File origin) {
		File dest = new File(pokeImageDefaultSource);
		dest.delete();
		dest = new File(pokeImageDefaultSource);
		try {
			MediaFormer.copyFile(origin, dest);
		} catch (IOException e1) {
		}
	}

	private void setCurrentImage(File origin) {
		File dest = new File(pokeImageSource);
		dest.delete();
		dest = new File(pokeImageSource);
		try {
			MediaFormer.copyFile(origin, dest);
		} catch (IOException e1) {
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
	 */
	private File choseFileFrom() {
		File newFile = new File("images\\" + pokeList.get(listController).getName() + ".png");
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
			if (fromFile.isSelected()) {// VIA FILE -------
				File origin = MediaFormer.choseFileFromSystem("jpg", "png");
				try {
					MediaFormer.copyFile(origin, newFile);
				} catch (IOException e) {
				}
			} else if (fromURL.isSelected()) { // VIA URL ---------------------------
				String url = getUrlFromUser();
				MediaFormer.downloadToFile(url, newFile);
			} else {
				JOptionPane.showMessageDialog(null, "Operation cancelled", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			return newFile;
		}
		return null;
	}

	private String getUrlFromUser() {
		String url = "";
		boolean isValid;
		do { // Check if URL is valid
			url = JOptionPane.showInputDialog("Insert Image Url (blank to cancel)");
			if (url != null) {
				isValid = MediaFormer.testImage(url);
				if (url.isBlank())
					isValid = true;
				if (!isValid) {
					JOptionPane.showMessageDialog(null, "This is not a valid url.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else
				isValid = true;
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
			System.out.println("Error al reproducir el sonido.");
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
	 * It makes soundTimer stops at second loop: - First event: after soundTimer
	 * first loop it sends first event. As isSoundEnable = true at the beginning,
	 * first event will turn isSoundEnable into false. Look if conditional. - Second
	 * event: As isSoundEnable = false at this time (only manipulated by
	 * soundTimer), second event will turn isSoundEnable into true and
	 * soundTimer.stop() will be called. So there's never a third event.
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
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2) {
			// leftButton
			if (listController != 0) {
				listController--;
				showPokemon();
			} else {
				listController = pokeList.size() - 1;
				showPokemon();
			}
		} else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2) {
			// rightButton
			if (listController < pokeList.size() - 1) {
				listController++;
				showPokemon();
			} else {
				listController = 0;
				showPokemon();
			}
		} else if (e.getSource() == speaker) {
			// playSoundButton
			if (isSoundEnable)
				playSound("Sounds\\" + pokeList.get(listController).getName().toLowerCase() + ".wav");
		} else if (e.getSource() == exit) {
			this.dispose(); // exitButton
		} else if (e.getSource() == pokeImage && isEditOn) {
			String msg = "You are about to change " + pokeList.get(listController).getName() + "s image.";
			int n = JOptionPane.showConfirmDialog(null, msg, "Pokemon image selector", JOptionPane.OK_CANCEL_OPTION);
			if (n == 0) {
				System.out.println("Bien 1");
				File newFile = choseFileFrom();
				if(newFile != null) {
					setCurrentImage(newFile);
					showPokeImage();					
				}
			}
		} else if (e.getSource() == restore) { // Restore defaultImage
			JCheckBox muteDefault = new JCheckBox(
					"Change " + pokeList.get(listController).getName() + "s default image");
			JLabel msg = new JLabel("<html><center>\"You are about to restore " + pokeList.get(listController).getName()
					+ "s default image,<br> do not check the checkbox if you want to keep the default image.verifique");
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
							File newFile = choseFileFrom();
							setDefaultImage(newFile);
							setDefaultToCurrent();
							showPokeImage();
						}
					}
				} else {
					setDefaultToCurrent();
					showPokeImage();
				}

				// String msg = "This pokemon doesn't have default image. Would you like to add
				// one?";
//				int n = JOptionPane.showConfirmDialog(null, msg, "Pokemon default image missing",
//						JOptionPane.OK_CANCEL_OPTION);
//				if (n == 0) {
//					setDefaultImage();
//				}
			}
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
		else if (e.getSource() == speaker)
			speaker.setIcon(MediaFormer.getImageIconFitLabel(speaker, "images\\Other\\speakerIconHover.png"));
		else if (e.getSource() == restore)
			restore.setIcon(MediaFormer.getImageIconFitLabel(restore, "images\\Other\\restoreIconHover.png"));
		else if (menuIList.contains(e.getSource())) {
			// -------------------- JMenuItems ---------------------------
			JMenuItem source = (JMenuItem) e.getSource();
			source.setBackground(menuHoverColor);
		}
	}

	/**
	 * mouesEntered and mouseExited are used to gain a roll over effect to
	 * top_buttons and JMenuItems.
	 */
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2)
			lbl_buttonLeft.setIcon(new ImageIcon("images\\Buttons\\buttonLeft.png"));
		else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2)
			lbl_buttonRight.setIcon(new ImageIcon("images\\Buttons\\buttonRight.png"));
		else if (e.getSource() == exit)
			exit.setBounds(exit.getBounds().x + 30, exit.getBounds().y + 30, exit.getWidth(), exit.getHeight());
		else if (e.getSource() == restore)
			restore.setIcon(MediaFormer.getImageIconFitLabel(restore, "images\\Other\\restoreIcon.png"));
		else if (e.getSource() == speaker)
			speaker.setIcon(MediaFormer.getImageIconFitLabel(speaker, "images\\Other\\speakerIcon.png"));
		// -------------------- JMenuItems ---------------------------
		else if (menuIList.contains(e.getSource())) {
			JMenuItem source = (JMenuItem) e.getSource();
			source.setBackground(menuDefaultColor);
		}
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if (menuIList.contains(e.getSource())) {
			// set default color to JMenuItem when its pressed
			JMenuItem source = (JMenuItem) e.getSource();
			source.setBackground(menuDefaultColor);
		}
		if (e.getSource() == soundTimer) {
			runSoundTimer(); // soundTimer event
		} else if (e.getSource() == welcomeTimer) { // welcomeTimers controls welcome animation
			runWelcomeTimer();
		} else if (e.getSource() == welcomeTimerTwo) { // when this timer is called, end the animation
			welcomeMessage.setVisible(false);
			welcomeTimerTwo.stop();
			// JMenuItems unique behavior ------------------------------------
		} else if (e.getSource() == mNew_new) { // New -> new
			emptyFields();
			setReadyToEdit();
			mEdit_on.setEnabled(false);
			mEdit_on.setText("Enable edit mode.");
			mEdit_save.setEnabled(false);
			mEdit_discard.setEnabled(false);
			mNew_save.setEnabled(true);
			mNew_discard.setEnabled(true);
		} else if (e.getSource() == mNew_save) { // New -> save
			
		} else if (e.getSource() == mNew_discard) { // New -> discard
			setReadyToShow();
		} else if (e.getSource() == mEdit_on) { // Edit -> on
			if (!isEditOn)
				setReadyToEdit();
			else
				setReadyToShow();
		} else if (e.getSource() == mEdit_save) { // Edit -> save
			System.out.println(verifyAllFields());
		} else if (e.getSource() == mEdit_discard) { // Edit -> discard

		}
	}

	// TODO KeyEvents
	/**
	 * KeyRealeased controls field verification.
	 */
	public void keyReleased(KeyEvent e) {
		if (attIntputList.contains(e.getSource())) {
			verifyAttFields((JTextField) e.getSource());
		} else if (e.getSource() == prop_heightV || e.getSource() == prop_weightV) {
			verifyDoubleFields((JTextField) e.getSource());
		} else if (e.getSource() == currentNumber) {
			verifyCurrentNumber();
		} else if (e.getSource() == description) {
			verifyDescription();
		} else if (strFields.contains(e.getSource())) {
			verifyStrFields((JTextField) e.getSource());
		} else if (e.getSource() == currentName) {
			verifyCurrentName();
		}
	}
	
	/**
	 * This method check if all input fields are stored in redyFields (this mean all
	 * fields are ready to process).
	 * 
	 * @returns true If all fields are stored in readtFields
	 * @returns false If any field is not stored in readyFields
	 */
	public boolean verifyAllFields() {
		if (!readyFields.contains(currentName))
			return false;
		if (!readyFields.contains(currentNumber))
			return false;
		if (!readyFields.containsAll(attIntputList))
			return false;
		if (!readyFields.containsAll(strFields))
			return false;
		if (!readyFields.contains(prop_heightV))
			return false;
		if (!readyFields.contains(prop_weightV))
			return false;
		return true;
	}
	
	/**
	 * This method verifies if <strong>attIntputList</strong> fields contain only Integer characters.
	 * It warn user by changing its background.
	 * 
	 * @param e
	 */
	private void verifyAttFields(JTextField source) {
		boolean isValid = StringUtils.checkInt(source.getText());
		if (!isValid) {
			source.setBackground(new Color(202, 129, 129));
			readyFields.remove(source);
		} else {
			int newInt = Integer.parseInt(source.getText());
			if (newInt < 1) {
				newInt = 1;
				source.setText(Integer.toString(newInt));
			} else if (newInt > 15) {
				newInt = 15;
				source.setText(Integer.toString(newInt));
			}
			source.setBackground(new Color(139, 139, 139));
			if (!readyFields.contains(source))
				readyFields.add(source);
		}
	}

	/**
	 * This method verifies height and weight to be Double. It warns user by
	 * changing its background if it's blank or its foreground if it's not Double.
	 * 
	 * @param e Height or Weight.
	 */
	private void verifyDoubleFields(JTextField source) {
		if (!source.getText().trim().isBlank()) {
			source.setBackground(new Color(48, 167, 215));
			boolean isValid = StringUtils.checkDouble(source.getText());
			if (!isValid) {
				source.setForeground(Color.red);
				readyFields.remove(source);
			} else {
				source.setForeground(foreground);
				if (!readyFields.contains(source))
					readyFields.add(source);
			}
		} else {
			readyFields.remove(source);
			source.setBackground(new Color(202, 129, 129));
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
		}
	}

	/**
	 * This method verifies description. Description must be at least 30 character
	 * length.
	 * 
	 * It warns the user to fulfill rules.
	 */
	private void verifyDescription() {
		int length = description.getText().trim().length();
		System.out.println(length);
		if (length < 30) {
			if (length <= 1) {
				description.setBackground(new Color(202, 129, 129));
			} else {
				description.setBackground(Color.white);
			}
			description.setForeground(Color.red);
			isDescriptionReady = false;
			System.out.println(isDescriptionReady);
			descRules.setVisible(true);
		} else {
			description.setBackground(Color.white);
			description.setForeground(Color.black);
			isDescriptionReady = true;
			descRules.setVisible(false);
		}
	}

	/**
	 * This method verifies strFields are not blank. It warns the user to fill this
	 * fields.
	 * 
	 * @param e any of "strFields" list textField.
	 */
	private void verifyStrFields(JTextField source) {

		if (source.getText().trim().isBlank()) {
			source.setBackground(new Color(202, 129, 129));
			readyFields.remove(source);
		} else {
			if (!readyFields.contains(source)) {
				readyFields.add(source);
			}
			source.setBackground(new Color(48, 167, 215));
		}
	}

	/**
	 * This method verifies currentName is not blank. It warns the user to fill this
	 * field.
	 */
	private void verifyCurrentName() {
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
	 * sectors: - Top_Labels (header). - Blue_Box Labels (first info sector, it
	 * shows main characteristics). - Under Blue_Box Buttons (second info sector, it
	 * shows the types). - Gray_Box Labels (third info sector, it shows base
	 * attributes).
	 */
	public void initializeUIComponentes() {
		// Add typeLabels and its container
		typeContainer = new JPanel();
		typeContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		typeContainer.setBackground(Color.white);
		typeContainer.setBounds(417, 460, 410, 65);
		
		// MenuBar
		menuBar = new JMenuBar();
		menuBar.setBackground(menuDefaultColor);
		// Menu new
		menu_new = new JMenu("    New    "); // Menu 1
		menu_new.setForeground(Color.white);
		// Menu edit
		menu_edit = new JMenu("   Edit   ");
		menu_edit.setForeground(Color.white);
		menuIList = new ArrayList<JMenuItem>();
		// "NEW" MENU --------------------------------------------------
		// mNew New
		mNew_new = new JMenuItem("Add new Pokemon.") { // 1
			private static final long serialVersionUID = 1L;

			public void setArmed(boolean b) { // Default hover effect is disabled now
			}
		};
		mNew_new.setBackground(menuDefaultColor);
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
		mNew_save.setBackground(menuDefaultColor);
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
		mNew_discard.setBackground(menuDefaultColor);
		mNew_discard.setForeground(Color.white);
		mNew_discard.addMouseListener(this);
		mNew_discard.addActionListener(this);
		menuIList.add(mNew_discard);
		// "EDIT" MENU ---------------------------------------------------
		// mEdit On
		mEdit_on = new JMenuItem("Enable edit mode.") { // Item 1
			private static final long serialVersionUID = 2L;

			public void setArmed(boolean b) {
			}
		};
		mEdit_on.setBackground(menuDefaultColor);
		mEdit_on.setForeground(Color.white);
		mEdit_on.addMouseListener(this);
		mEdit_on.addActionListener(this);
		menuIList.add(mEdit_on);
		// mEdit Off
		// mEdit Save
		mEdit_save = new JMenuItem("Save changes and disable edit mode.") { // 1
			private static final long serialVersionUID = 3L;

			public void setArmed(boolean b) {
			}
		};
		mEdit_save.setBackground(menuDefaultColor);
		mEdit_save.setForeground(Color.white);
		mEdit_save.addMouseListener(this);
		mEdit_save.addActionListener(this);
		menuIList.add(mEdit_save);
		// mEdit Discard
		mEdit_discard = new JMenuItem("Discard changes and disable edit mode.") { // 2
			private static final long serialVersionUID = 4L;

			public void setArmed(boolean b) {
			}
		};
		mEdit_discard.setBackground(menuDefaultColor);
		mEdit_discard.setForeground(Color.white);
		mEdit_discard.addMouseListener(this);
		mEdit_discard.addActionListener(this);
		menuIList.add(mEdit_discard);

		// MENU CONNECTIONS ---------------------------------------------
		menuBar.add(menu_new);
		menuBar.add(menu_edit);

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator separator4 = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBackground(Color.white);
		separator2.setBackground(Color.white);
		separator3.setBackground(Color.white);
		separator4.setBackground(Color.white);
		menu_new.add(mNew_new);
		menu_new.add(separator);
		menu_new.add(mNew_save);
		menu_new.add(separator2);
		menu_new.add(mNew_discard);
		menu_edit.add(mEdit_on);
		menu_edit.add(separator3);
		menu_edit.add(mEdit_save);
		menu_edit.add(separator4);
		menu_edit.add(mEdit_discard);

		setJMenuBar(menuBar);

		// FOOTER
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
		contentPane.add(exit);
		contentPane.add(typeContainer);

		// WELCOME
		// welcomeMessage - Invoked when Pokedex (this) is instantiated
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
		contentPane.add(welcomeMessage);

		// TOP_LABELS ---------------------------------------------------------
		// EditMode
		editMode = new JLabel("E D I T  M O D E  O N");
		editMode.setBounds(getBounds().width / 2 - 155, 15, 330, 50);
		editMode.setFont(new Font("Flexo-Regular", NORMAL, 36));
		editMode.setVisible(false);
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
		currentName.setBounds(340, 55, 130, 50);
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
		descRules = new JLabel("Description must be at least 30 characters lenght :)");
		descRules.setBounds(430, 115, 387, 20);
		descRules.setFont(new Font("Flexo-Regular", NORMAL, 16));
		descRules.setForeground(Color.DARK_GRAY);
		descRules.setVisible(false);
		// PokeImage
		pokeImage = new JLabel();
		pokeImage.setBounds(120, 90, 280, 280);
		pokeImage.addMouseListener(this);
		// SpeakerImage
		speaker = new JLabel();
		speaker.setBounds(360, 335, 40, 40);
		speaker.setIcon(MediaFormer.getImageIconFitLabel(speaker, "images\\Other\\speakerIcon.png"));
		speaker.addMouseListener(this);
		// RestoreImage
		restore = new JLabel();
		restore.setBounds(120, 333, 40, 40);
		restore.setIcon(MediaFormer.getImageIconFitLabel(speaker, "images\\Other\\restoreIcon.png"));
		restore.setToolTipText("Restore default pokeImage");
		restore.addMouseListener(this);
		// Add previous components
		contentPane.add(numberSign);
		contentPane.add(editMode);
		contentPane.add(previousName);
		contentPane.add(previousNumber);
		contentPane.add(nextName);
		contentPane.add(nextNumber);
		contentPane.add(currentName);
		contentPane.add(currentNumber);
		contentPane.add(description);
		contentPane.add(descRules);
		contentPane.add(speaker);
		contentPane.add(restore);
		contentPane.add(pokeImage);
		// lbl_buttonLeft
		lbl_buttonLeft = new JLabel(new ImageIcon("images\\Buttons\\buttonLeft.png"));
		lbl_buttonLeft.setBounds(107, 0, 372, 79);
		// lbl_buttonLeft formers (for custom listen-event shape, compund by two
		// rectangles)
		leftButtonFormer1 = new JLabel(); // Rectangle 1
		leftButtonFormer1.setBounds(107, 0, 372, 50);
		leftButtonFormer1.addMouseListener(this);
		leftButtonFormer2 = new JLabel(); // Rectangle 2
		leftButtonFormer2.setBounds(107, 50, 125, 30);
		leftButtonFormer2.addMouseListener(this);

		// lbl_buttonRight
		lbl_buttonRight = new JLabel(new ImageIcon("images\\Buttons\\buttonRight.png"));
		lbl_buttonRight.setBounds(479, 0, 372, 79);
		// lbl_buttonRight formers (for custom listen-event shape, compound by two
		// rectangles)
		rightButtonFormer1 = new JLabel(); // Rectangle 1
		rightButtonFormer1.setBounds(479, 0, 372, 50);
		rightButtonFormer1.addMouseListener(this);
		rightButtonFormer2 = new JLabel(); // Rectangle 2
		rightButtonFormer2.setBounds(726, 50, 125, 30);
		rightButtonFormer2.addMouseListener(this);
		// Add previous components
		contentPane.add(lbl_buttonLeft);
		contentPane.add(leftButtonFormer1);
		contentPane.add(leftButtonFormer2);
		contentPane.add(lbl_buttonRight);
		contentPane.add(rightButtonFormer1);
		contentPane.add(rightButtonFormer2);

		// BLUE_BOX LABELS -------------------------------------------------------
		foreground = new Color(77, 77, 77);
		prop_heightT = new JLabel("Height (m)");
		prop_heightT.setBounds(438, 265, 120, 20);
		prop_heightT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_heightT.setForeground(Color.white);

		prop_heightV = new JTextField();
		prop_heightV.setBounds(438, 290, 150, 20);
		prop_heightV.setBorder(null);
		prop_heightV.setBackground(new Color(48, 167, 215));
		prop_heightV.setForeground(foreground);
		prop_heightV.setDisabledTextColor(foreground);
		prop_heightV.setFont(new Font("Flexo-Light", 1, 18));
		prop_heightV.addKeyListener(this);
		prop_heightV.setEditable(false);

		prop_weightT = new JLabel("Weight (kg)");
		prop_weightT.setBounds(438, 323, 120, 20);
		prop_weightT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_weightT.setForeground(Color.white);
		
		prop_weightV = new JTextField();
		prop_weightV.setBounds(438, 348, 150, 20);
		prop_weightV.setBorder(null);
		prop_weightV.setBackground(new Color(48, 167, 215));
		prop_weightV.setForeground(foreground);
		prop_weightV.setDisabledTextColor(foreground);
		prop_weightV.setFont(new Font("Flexo-Light", 1, 18));
		prop_weightV.addKeyListener(this);
		prop_weightV.setEditable(false);

		prop_sexT = new JLabel("Sex");
		prop_sexT.setBounds(438, 376, 120, 20);
		prop_sexT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_sexT.setForeground(Color.white);

		prop_sexV = new JTextField();
		prop_sexV.setBounds(438, 401, 150, 20);
		prop_sexV.setBorder(null);
		prop_sexV.setBackground(new Color(48, 167, 215));
		prop_sexV.setForeground(foreground);
		prop_sexV.setDisabledTextColor(foreground);
		prop_sexV.setFont(new Font("Flexo-Light", 1, 18));
		prop_sexV.setEditable(false);
		prop_sexV.addKeyListener(this);
		strFields.add(prop_sexV);

		prop_categoryT = new JLabel("Category");
		prop_categoryT.setBounds(638, 265, 120, 20);
		prop_categoryT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_categoryT.setForeground(Color.white);

		prop_categoryV = new JTextField();
		prop_categoryV.setBounds(638, 290, 150, 20);
		prop_categoryV.setBorder(null);
		prop_categoryV.setBackground(new Color(48, 167, 215));
		prop_categoryV.setForeground(foreground);
		prop_categoryV.setDisabledTextColor(foreground);
		prop_categoryV.setFont(new Font("Flexo-Light", 1, 18));
		prop_categoryV.setEditable(false);
		prop_categoryV.addKeyListener(this);
		strFields.add(prop_categoryV);

		prop_skillT = new JLabel("Skill");
		prop_skillT.setBounds(638, 323, 120, 20);
		prop_skillT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		prop_skillT.setForeground(Color.white);

		prop_skillV = new JTextField();
		prop_skillV.setBounds(638, 348, 150, 20);
		prop_skillV.setBackground(new Color(48, 167, 215));
		prop_skillV.setBorder(null);
		prop_skillV.setForeground(foreground);
		prop_skillV.setDisabledTextColor(foreground);
		prop_skillV.setFont(new Font("Flexo-Light", 1, 18));
		prop_skillV.setEditable(false);
		prop_skillV.addKeyListener(this);
		strFields.add(prop_skillV);
		// Add previous characteristics labels
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

		// UNDER-BLUE-BOX BUTTONS --------------------------------------
		// GRAY-BOX LABELS ---------------------------------------------------------
		attIntputList = new ArrayList<JTextField>();

		lbl_ps = new JLabel("PS");
		lbl_ps.setBounds(130, 385, 50, 20);
		lbl_ps.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_ps.setForeground(Color.white);
		lbl_ps.setHorizontalAlignment(JLabel.CENTER);

		ps = new JProgressBar(0, 0, 15);
		ps.setBounds(180, 385, 200, 20);
		ps.setBackground(new Color(164, 164, 164));
		ps.setUI(new FancyProgressBar());
		ps.setBorder(null);

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

		lbl_att = new JLabel("Attack");
		lbl_att.setBounds(130, 415, 50, 20);
		lbl_att.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_att.setForeground(Color.white);
		lbl_att.setHorizontalAlignment(JLabel.CENTER);

		att = new JProgressBar(0, 0, 15);
		att.setBounds(180, 415, 200, 20);
		att.setBackground(new Color(164, 164, 164));
		att.setUI(new FancyProgressBar());
		att.setBorder(null);

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

		lbl_def = new JLabel("Defense");
		lbl_def.setBounds(130, 445, 50, 20);
		lbl_def.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_def.setForeground(Color.white);
		lbl_def.setHorizontalAlignment(JLabel.CENTER);

		def = new JProgressBar(0, 0, 15);
		def.setBounds(180, 445, 200, 20);
		def.setBackground(new Color(164, 164, 164));
		def.setUI(new FancyProgressBar());
		def.setBorder(null);

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

		lbl_satt = new JLabel(
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>" + "<p>Special<br>Attack</p>" + "<html>");
		lbl_satt.setBounds(130, 473, 50, 30);
		lbl_satt.setFont(new Font("Flexo-Light", Font.BOLD, 10));
		lbl_satt.setForeground(Color.white);
		lbl_satt.setHorizontalAlignment(JLabel.CENTER);

		satt = new JProgressBar(0, 0, 15);
		satt.setBounds(180, 475, 200, 20);
		satt.setBackground(new Color(164, 164, 164));
		satt.setUI(new FancyProgressBar());
		satt.setBorder(null);

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

		lbl_sdef = new JLabel(
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>" + "<p>Special<br>Defense</p>" + "<html>");
		lbl_sdef.setBounds(130, 503, 50, 30);
		lbl_sdef.setFont(new Font("Flexo-Light", Font.BOLD, 10));
		lbl_sdef.setForeground(Color.white);
		lbl_sdef.setHorizontalAlignment(JLabel.CENTER);

		sdef = new JProgressBar(0, 0, 15);
		sdef.setBounds(180, 505, 200, 20);
		sdef.setBackground(new Color(164, 164, 164));
		sdef.setUI(new FancyProgressBar());
		sdef.setBorder(null);

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

		lbl_speed = new JLabel("Speed");
		lbl_speed.setBounds(130, 535, 50, 20);
		lbl_speed.setFont(new Font("Flexo-Light", Font.BOLD, 11));
		lbl_speed.setForeground(Color.white);
		lbl_speed.setHorizontalAlignment(JLabel.CENTER);

		speed = new JProgressBar(0, 0, 15);
		speed.setBounds(180, 535, 200, 20);
		speed.setBackground(new Color(164, 164, 164));
		speed.setUI(new FancyProgressBar());
		speed.setBorder(null);

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
		// Add previuos attribute labels and its progressBar (no value yet)
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

		// BACKGROUND -----------------------------------------------------------
		background = new JLabel(new ImageIcon("images\\Background\\pokedexBackground.png"));
		background.setBounds(-5, -13, 953, 659);
		contentPane.add(background);

		showPokemon(); // Show first Pokemon (avoid blank default state)
	}

	// Unused Overridden methods
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}
}
