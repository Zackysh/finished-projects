package views;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.DAO_Login;
import utils.MediaFormer;
import utils.StringUtils;
import utils.TextPrompt;

public class LoginView extends JFrame implements ActionListener, MouseListener {
		
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JLabel background;
	private JLabel lbl_PokeRegister;
	private JLabel lbl_PokeLogin;

	private JTextField tF_Username;
	private TextPrompt tP_Username;
	private JPasswordField pF_Password;
	private TextPrompt tP_Password;

	private JButton jB_Register;
	private JButton jB_Login;
	
	private DAO_Login ld;
	

	/**
	 * Constructor of this view, it just call initialize().
	 */
	public LoginView() {
		ld = new DAO_Login();
		if(ld.checkConnection()) {
			initialize();
			setVisible(true);
		} else
			JOptionPane.showMessageDialog(null, "Cannot connect to the DB", "Connection alert", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Method that "launch" the frame and its components.
	 */
	private void initialize() {
		
		importFonts();

		// Pane initialization
		contentPane = new JPanel();
		contentPane.setLayout(null);
		
		// Frame initialization
		setTitle("Pokedex Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\icon.png"));
		setBounds(0, 0, 440, 540);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		initializeUIComponentes();
		

	}

	/**
	 * Import fonts from .ttf files.
	 * First a new Font is initialized, it receives an input stream that brings desired font.
	 * Later its registered into the current Graphic Environment.
	 */
	public void importFonts() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Flexo-Light.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Flexo-Regular.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Flexo-Medium.ttf")));
		} catch(FontFormatException | IOException e2) {
			e2.fillInStackTrace();
		}
	}
	
	/**
	 * Method that initialize UI components.
	 *  - Fields.
	 *  - Prompts.
	 *  - Buttons.
	 *  - Labels.	
	 */
	public void initializeUIComponentes() {

		// FIELDS ---------------------------------------------------------------------
		// Username textField
		tF_Username = new JTextField();
		tF_Username.setBounds(145, 190, 150, 34);
		tF_Username.setFont(new Font("Flexo-Regular", Font.PLAIN, 18));
		tF_Username.setHorizontalAlignment(SwingConstants.LEFT);
		tF_Username.setColumns(10);
		tF_Username.setForeground(new Color(150, 0, 0, 200));
		tF_Username
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 10, 5, 5)));
		getContentPane().add(tF_Username);
		// Username Prompt
		tP_Username = new TextPrompt("Username", tF_Username);
		tP_Username.setFont(new Font("Flexo-Regular", WIDTH, 18));
		tP_Username.setHorizontalAlignment(SwingConstants.LEADING);
		tP_Username.changeAlpha(0.75f);
		tP_Username.setForeground(new Color(150, 0, 0, 180));
		tP_Username.changeStyle(Font.ITALIC);
		
		// PasswordFields -----------------------------------------------------------------
		// Password
		pF_Password = new JPasswordField();
		pF_Password.setFont(new Font("Flexo-Regular", WIDTH, 18));
		pF_Password.setHorizontalAlignment(SwingConstants.LEFT);
		pF_Password.setForeground(new Color(150, 0, 0, 200));
		pF_Password.setColumns(10);
		pF_Password
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 10, 5, 5)));
		pF_Password.setBounds(145, 250, 150, 34);
		contentPane.add(pF_Password);
		// Password prompt
		tP_Password = new TextPrompt("Password", pF_Password);
		tP_Password.setFont(new Font("Flexo-Regular", WIDTH, 18));
		tP_Password.setHorizontalAlignment(SwingConstants.LEADING);
		tP_Password.changeAlpha(0.75f);
		tP_Password.setForeground(new Color(150, 0, 0, 180));
		tP_Password.changeStyle(Font.ITALIC);
		
		// BUTTONS -----------------------------------------------------------------------
		// Login
		jB_Register = new JButton("SIGN UP");
		jB_Register.setBounds(230, 340, 100, 35);
		jB_Register.setBorder(null);
		jB_Register.setFont(new Font("Flexo-Regular", Font.BOLD, 18));
		jB_Register.setForeground(new Color(255, 255, 255));
		jB_Register.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		jB_Register.setContentAreaFilled(false);
		jB_Register.addActionListener(this); // ActionListener
		jB_Register.addMouseListener(this); // MouesListener
		contentPane.add(jB_Register);
		// Register
		jB_Login = new JButton("LOG IN");
		jB_Login.setBounds(90, 340, 100, 35);
		jB_Login.setBorder(null);
		jB_Login.setForeground(Color.WHITE);
		jB_Login.setFont(new Font("Flexo-Regular", Font.BOLD, 18));
		jB_Login.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		jB_Login.setContentAreaFilled(false);
		jB_Login.addActionListener(this); // ActionListener
		jB_Login.addMouseListener(this); // MouseListener
		contentPane.add(jB_Login);
		
		// LABELS ------------------------------------------------------------------------
		// lbl_PokeLogin
		lbl_PokeLogin = new JLabel();
		lbl_PokeLogin.setBounds(219, 320, 117, 107);
		lbl_PokeLogin.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeLogin, "images\\pokeButton_1.png"));
		contentPane.add(lbl_PokeLogin);
		// lbl_PokeRegister
		lbl_PokeRegister = new JLabel();
		lbl_PokeRegister.setBounds(81, 320, 117, 107);
		lbl_PokeRegister.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_1.png"));
		contentPane.add(lbl_PokeRegister);
		
		// Set JLabel as background to the contentPane
		// It must be done the latter, otherwise it would be proposed and cover other elements
		background = new JLabel(new ImageIcon("images\\loginback.png"));
		background.setBounds(-10, -13, 440, 540);
		contentPane.add(background);
	}



	// Event Handler methods
	@Override
	public void actionPerformed(ActionEvent e) {
		
		UIManager.put("OptionPane.messageFont", new Font("Flexo-Regular", Font.BOLD, 14));
		
		if(e.getSource() == jB_Login) {
			
			if(tF_Username.getText().isBlank() || String.valueOf(pF_Password.getPassword()).isBlank())				
				JOptionPane.showMessageDialog(null, "Please, dont leave empty fields.", getTitle(), JOptionPane.INFORMATION_MESSAGE);			
			else if(ld.login(tF_Username.getText(), String.valueOf(pF_Password.getPassword()))) { // If successful login				
				JOptionPane.showMessageDialog(null, "Succesful login.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
				new PokedexView(StringUtils.normalizarString(this.tF_Username.getText()));
			} else {
				if(!ld.checkUsername(tF_Username.getText()))
					JOptionPane.showMessageDialog(null, "Couldn't find your Pokedex Account :(", getTitle(), JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Wrong password.", getTitle(), JOptionPane.WARNING_MESSAGE);
			}
			
		} else if(e.getSource() == jB_Register) {
			
			this.setVisible(false);
			new SignupView(this);
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == jB_Register)
			lbl_PokeLogin.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_2.png"));
		if (e.getSource() == jB_Login)
			lbl_PokeRegister.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_2.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == jB_Register) {
			lbl_PokeLogin.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_1.png"));
			
		} else if (e.getSource() == jB_Login)
			lbl_PokeRegister.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_1.png"));
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
		// TODO Auto-generated method stub

	}
	
	public void main(String[] args) {
		
	}
	
	
}