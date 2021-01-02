package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.MetalButtonUI;

import dao.DAO_Pokemon;
import dao.DAO_Type;
import models.PokeType;
import models.Pokemon;
import utils.FancyProgressBar;
import utils.MediaFormer;

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
public class PokedexView extends JFrame implements ActionListener, MouseListener {

	/**
	 * Its not used, but I prefer this solution than ignore the warning.
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JLabel background;

	// WELCOME-ANIMATION components ----------
	private JButton box;
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
	private JLabel previousName;
	private JLabel previousNumber;
	private JLabel nextName;
	private JLabel nextNumber;
	private JLabel currentName;
	private JLabel currentNumber;
	private JLabel description;
	private JLabel pokeImage;
	private JLabel speaker;

	// BLUE-BOX (T: Title, V: Value) --------
	private JLabel skillT;
	private JLabel skillV;
	private JLabel categoryT;
	private JLabel categoryV;
	private JLabel heightT;
	private JLabel heightV;
	private JLabel weightT;
	private JLabel weightV;
	private JLabel sexT;
	private JLabel sexV;

	// UNDER-BLUE-BOX -----------------------
	private JButton typeOneLabel;
	private JButton typeTwoLabel;

	// GRAY-BOX ----------------------------
	private JLabel lbl_ps;
	private JProgressBar ps;
	private JLabel lbl_att;
	private JProgressBar att;
	private JLabel lbl_def;
	private JProgressBar def;
	private JLabel lbl_satt;
	private JProgressBar satt;
	private JLabel lbl_sdef;
	private JProgressBar sdef;
	private JLabel lbl_speed;
	private JProgressBar speed;

	// FOOTER ------------------------------
	private JButton exit;

	// DAOs
	private DAO_Pokemon pokeDAO;
	private DAO_Type typeDAO;

	// Lists
	private ArrayList<Pokemon> pokeList = new ArrayList<Pokemon>();
	private ArrayList<PokeType> typeList = new ArrayList<PokeType>();
	private int listController = 0;

	// Sound Control
	private boolean play = true;
	Timer soundTimer;

	/**
	 * Import fonts from .ttf files. First a new Font is initialized, it receives an
	 * input stream that brings desired font. Later its registered into the current
	 * Graphic Environment.
	 */
	public void importFonts() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Flexo-Light.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Flexo-Regular.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Flexo-Medium.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Pokefont.ttf")));
		} catch (FontFormatException | IOException e2) {
			e2.fillInStackTrace();
		}
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

		// Frame initialization
		setTitle("Pokedex");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\icon.png"));
		setBounds(0, 0, 953, 659);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
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
		Point punto = new Point(940, (int) box.getBounds().getY());
		animate(box, punto, 150, 2);
	}

	/**
	 * Second loop of the animation.
	 */
	private void animOne() {
		Point punto = new Point((int) this.getBounds().getWidth() / 2 - 150, (int) box.getBounds().getY());
		animate(box, punto, 150, 2);
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
	 * Initialize main UI components. There're a bunch, so every component is
	 * classified in a specific sector of the frame. There's six main sectors: -
	 * Top_Labels (header). - Blue_Box Labels (first info sector, it shows main
	 * characteristics). - Under Blue_Box Buttons (second info sector, it shows the
	 * types). - Gray_Box Labels (third info sector, it shows base attributes).
	 */
	public void initializeUIComponentes() {

		// FOOTER
		exit = new JButton("Quit");
		exit.setBounds(this.getBounds().width - 180, this.getBounds().height - 200, 240, 240);
		exit.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		exit.setBackground(new Color(0, 0, 0, 0));
		exit.setBorder(null);
		exit.setEnabled(false);
		exit.setFont(new Font("LMS Poke'mon Master Solid", Font.BOLD, 26));
		exit.setForeground(Color.WHITE);
		exit.setDisabledIcon(MediaFormer.getImageIconFitLabel(exit, "images\\welcome.png"));
		exit.setIcon(MediaFormer.getImageIconFitLabel(exit, "images\\welcome.png"));
		exit.setVerticalTextPosition(SwingConstants.CENTER);
		exit.setHorizontalTextPosition(SwingConstants.CENTER);
		exit.addMouseListener(this);
		contentPane.add(exit);

		// WELCOME
		String boxText = "WELCOME " + userName + "!";
		box = new JButton(boxText);
		box.setBounds(-300, this.getBounds().height / 2 - 250, 400, 400);
		box.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		box.setBackground(new Color(0, 0, 0, 0));
		box.setBorder(null);
		box.setEnabled(false);
		box.setFont(new Font("LMS Poke'mon Master Solid", Font.BOLD, 26));
		box.setForeground(Color.WHITE);
		box.setDisabledIcon(MediaFormer.getImageIconFitLabel(box, "images\\welcome.png"));
		box.setIcon(MediaFormer.getImageIconFitLabel(box, "images\\welcome.png"));
		box.setVerticalTextPosition(SwingConstants.CENTER);
		box.setHorizontalTextPosition(SwingConstants.CENTER);
		box.repaint();
		contentPane.add(box);

		// TOP_LABELS ---------------------------------------------------------
		// Previous info
		previousName = new JLabel();
		previousName.setBounds(317, 5, 120, 50);
		previousName.setFont(new Font("Flexo-Regular", NORMAL, 26));
		previousName.setHorizontalAlignment(SwingConstants.LEADING);
		previousName.setForeground(new Color(103, 97, 97));

		previousNumber = new JLabel();
		previousNumber.setBounds(237, 5, 100, 50);
		previousNumber.setFont(new Font("Flexo-Regular", Font.BOLD, 24));
		previousNumber.setHorizontalAlignment(SwingConstants.LEADING);
		previousNumber.setForeground(Color.white);

		// Next info
		nextName = new JLabel();
		nextName.setBounds(545, 5, 120, 50);
		nextName.setFont(new Font("Flexo-Regular", NORMAL, 26));
		nextName.setHorizontalAlignment(SwingConstants.LEADING);
		nextName.setForeground(new Color(103, 97, 97));

		nextNumber = new JLabel();
		nextNumber.setBounds(646, 5, 100, 50);
		nextNumber.setFont(new Font("Flexo-Regular", Font.BOLD, 24));
		nextNumber.setHorizontalAlignment(SwingConstants.LEADING);
		nextNumber.setForeground(Color.white);

		// Current info
		currentName = new JLabel();
		currentName.setBounds(375, 55, 130, 50);
		currentName.setFont(new Font("Flexo-Regular", NORMAL, 30));
		currentName.setHorizontalAlignment(SwingConstants.LEADING);

		currentNumber = new JLabel();
		currentNumber.setBounds(500, 55, 100, 50);
		currentNumber.setFont(new Font("Flexo-Regular", WIDTH, 26));
		currentNumber.setHorizontalAlignment(SwingConstants.LEADING);
		currentNumber.setForeground(new Color(103, 107, 107));

		// Description
		description = new JLabel("<html><p>" + "" + "</p><html>");
		description.setBounds(430, 20, 390, 300);
		description.setFont(new Font("Flexo-Regular", NORMAL, 15));
		description.setHorizontalAlignment(SwingConstants.LEADING);

		// PokeImage
		pokeImage = new JLabel();
		pokeImage.setBounds(120, 90, 280, 280);

		// SpeakerImage
		speaker = new JLabel();
		speaker.setBounds(350, 325, 50, 50);
		speaker.setIcon(MediaFormer.getImageIconFitLabel(speaker, "images\\speaker.png"));
		speaker.addMouseListener(this);

		contentPane.add(previousName);
		contentPane.add(previousNumber);
		contentPane.add(nextName);
		contentPane.add(nextNumber);
		contentPane.add(currentName);
		contentPane.add(currentNumber);
		contentPane.add(description);
		contentPane.add(speaker);
		contentPane.add(pokeImage);

		// lbl_buttonLeft
		lbl_buttonLeft = new JLabel(new ImageIcon("images\\buttonLeft.png"));
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
		lbl_buttonRight = new JLabel(new ImageIcon("images\\buttonRight.png"));
		lbl_buttonRight.setBounds(479, 0, 372, 79);

		// lbl_buttonRight formers (for custom listen-event shape, compound by two
		// rectangles)
		rightButtonFormer1 = new JLabel(); // Rectangle 1
		rightButtonFormer1.setBounds(479, 0, 372, 50);
		rightButtonFormer1.addMouseListener(this);

		rightButtonFormer2 = new JLabel(); // Rectangle 2
		rightButtonFormer2.setBounds(726, 50, 125, 30);
		rightButtonFormer2.addMouseListener(this);

		contentPane.add(lbl_buttonLeft);
		contentPane.add(leftButtonFormer1);
		contentPane.add(leftButtonFormer2);
		contentPane.add(lbl_buttonRight);
		contentPane.add(rightButtonFormer1);
		contentPane.add(rightButtonFormer2);

		// BLUE_BOX LABELS -------------------------------------------------------
		heightT = new JLabel("Height");
		heightT.setBounds(438, 265, 120, 20);
		heightT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		heightT.setForeground(Color.white);
		heightT.setHorizontalAlignment(SwingConstants.LEADING);

		heightV = new JLabel();
		heightV.setBounds(438, 290, 120, 20);
		heightV.setFont(new Font("Flexo-Light", 1, 18));
		heightV.setHorizontalAlignment(SwingConstants.LEADING);

		weightT = new JLabel("Weight");
		weightT.setBounds(438, 323, 120, 20);
		weightT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		weightT.setForeground(Color.white);
		weightT.setHorizontalAlignment(SwingConstants.LEADING);

		weightV = new JLabel();
		weightV.setBounds(438, 348, 120, 20);
		weightV.setFont(new Font("Flexo-Light", 1, 18));
		weightV.setHorizontalAlignment(SwingConstants.LEADING);

		sexT = new JLabel("Sex");
		sexT.setBounds(438, 376, 120, 20);
		sexT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		sexT.setForeground(Color.white);
		sexT.setHorizontalAlignment(SwingConstants.LEADING);

		sexV = new JLabel();
		sexV.setBounds(438, 401, 170, 20);
		sexV.setFont(new Font("Flexo-Light", 1, 18));
		sexV.setHorizontalAlignment(SwingConstants.LEADING);

		categoryT = new JLabel("Category");
		categoryT.setBounds(638, 265, 120, 20);
		categoryT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		categoryT.setForeground(Color.white);
		categoryT.setHorizontalAlignment(SwingConstants.LEADING);

		categoryV = new JLabel();
		categoryV.setBounds(638, 290, 150, 20);
		categoryV.setFont(new Font("Flexo-Light", 1, 18));
		categoryV.setHorizontalAlignment(SwingConstants.LEADING);

		skillT = new JLabel("Skill");
		skillT.setBounds(638, 323, 120, 20);
		skillT.setFont(new Font("Flexo-Light", Font.BOLD, 14));
		skillT.setForeground(Color.white);
		skillT.setHorizontalAlignment(SwingConstants.LEADING);

		skillV = new JLabel();
		skillV.setBounds(638, 348, 120, 20);
		skillV.setFont(new Font("Flexo-Light", 1, 18));
		skillV.setHorizontalAlignment(SwingConstants.LEADING);

		contentPane.add(heightT);
		contentPane.add(heightV);
		contentPane.add(weightT);
		contentPane.add(weightV);
		contentPane.add(sexT);
		contentPane.add(sexV);
		contentPane.add(categoryT);
		contentPane.add(categoryV);
		contentPane.add(skillT);
		contentPane.add(skillV);

		// UNDER-BLUE-BOX BUTTONS ------------------------------------------------------

		Border loweredbevel = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		typeOneLabel = new JButton();
		typeOneLabel.setBounds(448, 460, 150, 36);
		typeOneLabel.setEnabled(false);
		typeOneLabel.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		typeOneLabel.setFont(new Font("Flexo-Light", Font.BOLD, 16));
		typeOneLabel.setBorder(loweredbevel);
		contentPane.add(typeOneLabel);

		typeTwoLabel = new JButton();
		typeTwoLabel.setBounds(628, 460, 150, 36);
		typeTwoLabel.setEnabled(false);
		typeTwoLabel.setUI(new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.WHITE;
			}
		});
		typeTwoLabel.setFont(new Font("Flexo-Light", Font.BOLD, 16));
		typeTwoLabel.setBorder(loweredbevel);
		contentPane.add(typeTwoLabel);

		// GRAY-BOX ------------------------------------------------------------

		lbl_ps = new JLabel("PS");
		lbl_ps.setBounds(130, 385, 50, 20);
		lbl_ps.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		lbl_ps.setForeground(Color.black);
		lbl_ps.setHorizontalAlignment(JLabel.CENTER);

		ps = new JProgressBar(0, 0, 15);
		ps.setBounds(180, 385, 200, 20);
		ps.setBackground(new Color(164, 164, 164));
		ps.setUI(new FancyProgressBar());
		ps.setBorder(null);

		lbl_att = new JLabel("Attack");
		lbl_att.setBounds(130, 415, 50, 20);
		lbl_att.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		lbl_att.setForeground(Color.black);
		lbl_att.setHorizontalAlignment(JLabel.CENTER);

		att = new JProgressBar(0, 0, 15);
		att.setBounds(180, 415, 200, 20);
		att.setBackground(new Color(164, 164, 164));
		att.setUI(new FancyProgressBar());
		att.setBorder(null);

		lbl_def = new JLabel("Defense");
		lbl_def.setBounds(130, 445, 50, 20);
		lbl_def.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		lbl_def.setForeground(Color.black);
		lbl_def.setHorizontalAlignment(JLabel.CENTER);

		def = new JProgressBar(0, 0, 15);
		def.setBounds(180, 445, 200, 20);
		def.setBackground(new Color(164, 164, 164));
		def.setUI(new FancyProgressBar());
		def.setBorder(null);

		lbl_satt = new JLabel(
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>" + "<p>Special<br>Attack</p>" + "<html>");
		lbl_satt.setBounds(130, 473, 50, 30);
		lbl_satt.setFont(new Font("Flexo-Light", Font.PLAIN, 10));
		lbl_satt.setForeground(Color.black);
		lbl_satt.setHorizontalAlignment(JLabel.CENTER);

		satt = new JProgressBar(0, 0, 15);
		satt.setBounds(180, 475, 200, 20);
		satt.setBackground(new Color(164, 164, 164));
		satt.setUI(new FancyProgressBar());
		satt.setBorder(null);

		lbl_sdef = new JLabel(
				"<html>" + "<style>" + "p {text-align: center;}" + "</style>" + "<p>Special<br>Defense</p>" + "<html>");
		lbl_sdef.setBounds(130, 503, 50, 30);
		lbl_sdef.setFont(new Font("Flexo-Light", Font.PLAIN, 10));
		lbl_sdef.setForeground(Color.black);
		lbl_sdef.setHorizontalAlignment(JLabel.CENTER);

		sdef = new JProgressBar(0, 0, 15);
		sdef.setBounds(180, 505, 200, 20);
		sdef.setBackground(new Color(164, 164, 164));
		sdef.setUI(new FancyProgressBar());
		sdef.setBorder(null);

		lbl_speed = new JLabel("Speed");
		lbl_speed.setBounds(130, 535, 50, 20);
		lbl_speed.setFont(new Font("Flexo-Light", Font.PLAIN, 11));
		lbl_speed.setForeground(Color.black);
		lbl_speed.setHorizontalAlignment(JLabel.CENTER);

		speed = new JProgressBar(0, 0, 15);
		speed.setBounds(180, 535, 200, 20);
		speed.setBackground(new Color(164, 164, 164));
		speed.setUI(new FancyProgressBar());
		speed.setBorder(null);

		contentPane.add(lbl_ps);
		contentPane.add(ps);
		contentPane.add(lbl_att);
		contentPane.add(att);
		contentPane.add(lbl_def);
		contentPane.add(def);
		contentPane.add(lbl_satt);
		contentPane.add(satt);
		contentPane.add(lbl_sdef);
		contentPane.add(sdef);
		contentPane.add(lbl_speed);
		contentPane.add(speed);

		// BACKGROUND -----------------------------------------------------------
		background = new JLabel(new ImageIcon("images\\pokedexBack.png"));
		background.setBounds(-5, -13, 953, 659);
		contentPane.add(background);

		showPokemon();
	}

	/**
	 * Method that control whole filling process to show all pokemons information on
	 * the panel.
	 * 
	 * His task is subdivided on methods specialized on complete a specific
	 * sub-task.
	 */
	public void showPokemon() {

		Pokemon current = pokeList.get(listController);

		showHeadder(current);
		description.setText("<html><p>" + pokeList.get(listController).getDescription() + "</p><html>");
		showBlueBox(current);
		showGrayBox(current);
		showTypes(current);
		pokeImage.setIcon(MediaFormer.getImageIconFitLabel(pokeImage,
				"PokeImages\\" + pokeList.get(listController).getName() + ".png"));

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

		typeOneLabel.setText(current.getTypes().get(0).getName());
		typeOneLabel.setBackground(current.getTypes().get(0).getColor());

		try {
			typeTwoLabel.setVisible(true);
			typeTwoLabel.setText(current.getTypes().get(1).getName());
			typeTwoLabel.setBackground(current.getTypes().get(1).getColor());
		} catch (Exception e) {
			// Current Pokemon doesn't have 2ond type
			typeTwoLabel.setText("");
			typeTwoLabel.setVisible(false);
		}

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
		heightV.setText(height + " m");
		weightV.setText(weight + " kg");
		sexV.setText(sex);
		categoryV.setText(category);
		skillV.setText(skill);
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
			previousNumber.setText("N.�" + prevNumber);
		} else {
			prevName = pokeList.get(pokeList.size() - 1).getName();
			prevNumber = Integer.toString(pokeList.get(pokeList.size() - 1).getNumber());

			previousName.setText(prevName);
			previousNumber.setText("N.�" + prevNumber);
		}

		// NEXT INFO
		String nexName;
		String nexNumber;

		currentName.setText(currName);
		currentNumber.setText("N.�" + currNumber);

		if (listController < pokeList.size() - 1) {
			nexName = pokeList.get(listController + 1).getName();
			nexNumber = Integer.toString(pokeList.get(listController + 1).getNumber());

			nextName.setText(nexName);
			nextNumber.setText("N.�" + nexNumber);
		} else {
			nexName = pokeList.get(0).getName();
			nexNumber = Integer.toString(pokeList.get(0).getNumber());

			nextName.setText(nexName);
			nextNumber.setText("N.�" + nexNumber);
		}
	}

	public void playSound(String source) {

		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(source).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();

			clip.open(audioInputStream);
			this.actionPerformed(new ActionEvent(soundTimer, 1, "")); // Send null action to turn false "play"
			soundTimer.start(); // in 5segs it will be possible to play sound again
			clip.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
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
		setVisible(true);
		welcome();
	}

	/**
	 * When the mouse is clicked, we check the source, then...
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2) {
			/**
			 * If leftButton is the source previous or last pokemon is shown. Depends if
			 * current pokemon is the first.
			 */
			if (listController != 0) { // Is not the first

				listController--;
				showPokemon();

			} else { // Is the first

				listController = pokeList.size() - 1;
				showPokemon();

			}

		} else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2) {
			/**
			 * If rightButton is the source next or first pokemon is shown. Depends if
			 * current pokemon is the first.
			 */
			if (listController < pokeList.size() - 1) { // Is not the last

				listController++;
				showPokemon();

			} else { // Is the last

				listController = 0;
				showPokemon();

			}
		} else if (e.getSource() == speaker) {
			if (play)
				playSound("PokeCry\\" + pokeList.get(listController).getName().toLowerCase() + ".wav");
			else
				JOptionPane.showMessageDialog(null, "There's a 4 second delay to play a sound again.", getTitle(),
						JOptionPane.WARNING_MESSAGE);

		} else if (e.getSource() == exit) {
			this.dispose();
		}
	}

	/**
	 * mouesEntered and mouseExited are used to highlight buttons when the mouse
	 * enter or exit on their Formers (invisible labels that act like "hit-boxes".
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2)
			lbl_buttonLeft.setIcon(new ImageIcon("images\\buttonLeft2.png"));
		else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2)
			lbl_buttonRight.setIcon(new ImageIcon("images\\buttonRight2.png"));
		else if (e.getSource() == exit) {
			exit.setBounds(exit.getBounds().x - 30, exit.getBounds().y - 30, exit.getWidth(), exit.getHeight());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == leftButtonFormer1 || e.getSource() == leftButtonFormer2)
			lbl_buttonLeft.setIcon(new ImageIcon("images\\buttonLeft.png"));
		else if (e.getSource() == rightButtonFormer1 || e.getSource() == rightButtonFormer2)
			lbl_buttonRight.setIcon(new ImageIcon("images\\buttonRight.png"));
		else if (e.getSource() == exit) {
			exit.setBounds(exit.getBounds().x + 30, exit.getBounds().y + 30, exit.getWidth(), exit.getHeight());
		}
	}

	/**
	 * It might be possible to control whole sound and animation in a better way.
	 * But and don't think its necessary here, because it will not grow enough.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == soundTimer) { // Sound timer controls when its possible to play a sound
			if (play)
				play = false; // When a sound is played, its now possible to play a sound again
			else {
				play = true; // When the sound ends (timer send second action) timer stops and play = true
				soundTimer.stop();
			}
		} else if (e.getSource() == welcomeTimer) { // welcomeTimers controls welcome animation
			if (!box.isVisible())
				welcomeTimer.stop(); // Second call: stop timer
			else {
				animTwo(); // First call: starts second loop
			}
		} else if (e.getSource() == welcomeTimerTwo) { // when this timer is called, end the animation
			box.setVisible(false); 
			welcomeTimerTwo.stop();
		}

	}
	
	// Unused Overridden methods
	public void mousePressed(MouseEvent e) {} // Unused
	public void mouseReleased(MouseEvent e) {} // Unused
}
