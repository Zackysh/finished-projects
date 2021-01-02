package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.DAO_SignUp;
import utils.MediaFormer;
import utils.TextPrompt;

public class SignupView extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginView parent;

	private JLabel background;
	private JLabel lbl_PokeRegister;
	private JLabel lbl_PokeLogin;

	private JTextField tF_Username;
	private TextPrompt tP_Username;
	private JPasswordField pF_Password;
	private TextPrompt tP_Password;
	private JPasswordField pF_PasswordConfirm;
	private TextPrompt tP_PasswordConfirm;

	private JButton jB_Register;
	private JButton jB_Clear;
	private JButton jB_Back;
	private JLabel lbl_PokeBack;

	private DAO_SignUp sd;
	private Timer timer;

	/**
	 * Constructor of this view, it just call initialize().
	 */
	public SignupView(LoginView lv) {

		this.parent = lv;
		sd = new DAO_SignUp();
		if (sd.checkConnection()) {
			initialize();
			setVisible(true);
		} else
			JOptionPane.showMessageDialog(null, "Cannot connect to the DB", "Connection alert",
					JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Method that "launch" the frame and its components.
	 */
	private void initialize() {

		timer = new Timer(500, this);

		// Pane initialization
		contentPane = new JPanel();
		contentPane.setLayout(null);

		// Frame initialization
		setTitle("Pokedex SignUp");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\icon.png"));
		setBounds(0, 0, 440, 540);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		initializeUIComponentes();
	}

	/**
	 * Method that initialize UI components. - Fields. - Prompts. - Buttons. -
	 * Labels.
	 */
	public void initializeUIComponentes() {

		// FIELDS ---------------------------------------------------------------------
		// Username textField
		tF_Username = new JTextField();
		tF_Username.setBounds(206, 148, 150, 34);
		tF_Username.setFont(new Font("Flexo-Regular", WIDTH, 18));
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

		// PASSWORD FIELDS -----------------------------------------------------------------
		// Password
		pF_Password = new JPasswordField();
		pF_Password.setBounds(206, 188, 150, 34);
		pF_Password.setFont(new Font("Flexo-Regular", WIDTH, 18));
		pF_Password.setHorizontalAlignment(SwingConstants.LEFT);
		pF_Password.setForeground(new Color(150, 0, 0, 200));
		pF_Password.setColumns(10);
		pF_Password
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 10, 5, 5)));
		contentPane.add(pF_Password);
		// Password prompt
		tP_Password = new TextPrompt("Password", pF_Password);
		tP_Password.setFont(new Font("Flexo-Regular", WIDTH, 18));
		tP_Password.setHorizontalAlignment(SwingConstants.LEADING);
		tP_Password.changeAlpha(0.75f);
		tP_Password.setForeground(new Color(150, 0, 0, 180));
		tP_Password.changeStyle(Font.ITALIC);
		// Password Confirmation
		pF_PasswordConfirm = new JPasswordField();
		pF_PasswordConfirm.setBounds(206, 228, 150, 34);
		pF_PasswordConfirm.setFont(new Font("Flexo-Regular", WIDTH, 18));
		pF_PasswordConfirm.setHorizontalAlignment(SwingConstants.LEFT);
		pF_PasswordConfirm.setForeground(new Color(150, 0, 0, 200));
		pF_PasswordConfirm.setColumns(10);
		pF_PasswordConfirm
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 10, 5, 5)));
		contentPane.add(pF_PasswordConfirm);
		// Password Confirm prompt
		tP_PasswordConfirm = new TextPrompt("Repeat Password", pF_PasswordConfirm);
		tP_PasswordConfirm.setFont(new Font("Flexo-Regular", WIDTH, 15));
		tP_PasswordConfirm.setHorizontalAlignment(SwingConstants.LEADING);
		tP_PasswordConfirm.changeAlpha(0.75f);
		tP_PasswordConfirm.setForeground(new Color(150, 0, 0, 180));
		tP_PasswordConfirm.changeStyle(Font.ITALIC);

		// BUTTONS -----------------------------------------------------------------------
		// Register
		jB_Register = new JButton("SIGN UP");
		jB_Register.setBounds(302, 310, 100, 35);
		jB_Register.setBorder(null);
		jB_Register.setFont(new Font("Flexo-Regular", Font.BOLD, 18));
		jB_Register.setForeground(new Color(255, 255, 255));
		jB_Register.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		jB_Register.setContentAreaFilled(false);
		jB_Register.addActionListener(this); // ActionListener
		jB_Register.addMouseListener(this); // MouesListener
		contentPane.add(jB_Register);
		// Clear fields
		jB_Clear = new JButton("CLEAR FIELDS");
		jB_Clear.setBounds(157, 310, 100, 35);
		jB_Clear.setBorder(null);
		jB_Clear.setForeground(Color.WHITE);
		jB_Clear.setFont(new Font("Flexo-Regular", Font.BOLD, 13));
		jB_Clear.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		jB_Clear.setContentAreaFilled(false);
		jB_Clear.addActionListener(this); // ActionListener
		jB_Clear.addMouseListener(this); // MouseListener
		contentPane.add(jB_Clear);
		// Back
		jB_Back = new JButton("BACK");
		jB_Back.setBounds(228, 377, 100, 35);
		jB_Back.setBorder(null);
		jB_Back.setForeground(Color.WHITE);
		jB_Back.setFont(new Font("Flexo-Regular", Font.BOLD, 13));
		jB_Back.setBackground(new Color(0.76f, 0.42f, 0.42f, 0.01f));
		jB_Back.setContentAreaFilled(false);
		jB_Back.addActionListener(this); // ActionListener
		jB_Back.addMouseListener(this); // MouseListener
		contentPane.add(jB_Back);

		// LABELS ------------------------------------------------------------------------
		// lbl_PokeRegister
		lbl_PokeLogin = new JLabel();
		lbl_PokeLogin.setBounds(291, 290, 117, 107);
		lbl_PokeLogin.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeLogin, "images\\pokeButton_1.png"));
		contentPane.add(lbl_PokeLogin);
		// lbl_PokeClear
		lbl_PokeRegister = new JLabel();
		lbl_PokeRegister.setBounds(148, 290, 117, 107);
		lbl_PokeRegister.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_1.png"));
		contentPane.add(lbl_PokeRegister);
		// lbl_PokeBack
		lbl_PokeBack = new JLabel();
		lbl_PokeBack.setBounds(238, 370, 80, 80);
		lbl_PokeBack.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeBack, "images\\pokeButton_1.png"));
		contentPane.add(lbl_PokeBack);

		// Set JLabel as background to the contentPane
		// It must be done the latter, otherwise it would be proposed and cover other
		// elements
		background = new JLabel(new ImageIcon("images\\signupback.png"));
		background.setBounds(-5, -13, 440, 540);
		contentPane.add(background);
	}

	// EVENT HANDLER METHODS -----------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) {

		// Specifies font for local JOptionPanes
		UIManager.put("OptionPane.messageFont", new Font("Flexo-Regular", Font.BOLD, 14));

		// This button starts a timer, this timer will handle a delayed clear field
		// function in its own event clause
		if (e.getSource() == jB_Clear) {

			timer.setDelay(200); // Delay set to 0,2s
			timer.start();

		} else if (e.getSource() == jB_Register) {
			if (tF_Username.getText().isBlank() || String.valueOf(pF_Password.getPassword()).isBlank())
				JOptionPane.showMessageDialog(null, "Please, dont leave empty fields.", getTitle(),
						JOptionPane.INFORMATION_MESSAGE);
			else if (String.valueOf(pF_Password.getPassword()) != String.valueOf(pF_PasswordConfirm.getPassword()))
				JOptionPane.showMessageDialog(null, "Passwords must match.", getTitle(),
						JOptionPane.INFORMATION_MESSAGE);
			else if (sd.registerNewUser(tF_Username.getText(), String.valueOf(pF_Password.getPassword())) != 0) {
				JOptionPane.showMessageDialog(null, "Succesful sign-up, try to log-in!.", getTitle(),
						JOptionPane.INFORMATION_MESSAGE);
				parent.setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "That username is taken. Try another.", getTitle(),
						JOptionPane.WARNING_MESSAGE);
				parent.setVisible(true);
				dispose();
			}

		} else if (e.getSource() == timer) { // Clear fields with 0,2 delay

			String aux = "";

			if (!tF_Username.getText().isBlank()) { // Clear user name field
				aux = tF_Username.getText().substring(0, tF_Username.getText().length() - 1);
				tF_Username.setText(aux);
			} else if (!String.valueOf(pF_Password.getPassword()).isBlank()) { // Clear password field
				aux = String.valueOf(pF_Password.getPassword()).substring(0,
					  String.valueOf(pF_Password.getPassword()).length() - 1);
				pF_Password.setText(aux);
			} else if (!String.valueOf(pF_PasswordConfirm.getPassword()).isBlank()) { // Clear password confirm field
				aux = String.valueOf(pF_PasswordConfirm.getPassword()).substring(0,
					  String.valueOf(pF_PasswordConfirm.getPassword()).length() - 1);
				pF_PasswordConfirm.setText(aux);
			} else
				timer.stop();

		} else if (e.getSource() == jB_Back) {
			parent.setVisible(true);
			dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == jB_Register)
			lbl_PokeLogin.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_2.png"));
		if (e.getSource() == jB_Clear)
			lbl_PokeRegister.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_2.png"));
		if (e.getSource() == jB_Back)
			lbl_PokeBack.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeBack, "images\\pokeButton_2.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == jB_Register)
			lbl_PokeLogin.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_1.png"));
		if (e.getSource() == jB_Clear)
			lbl_PokeRegister.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeRegister, "images\\pokeButton_1.png"));
		if (e.getSource() == jB_Back)
			lbl_PokeBack.setIcon(MediaFormer.getImageIconFitLabel(lbl_PokeBack, "images\\pokeButton_1.png"));
	}

	// Unused Overridden methods
	public void mouseClicked(MouseEvent e) {
	} // Unused

	public void mousePressed(MouseEvent e) {
	} // Unused

	public void mouseReleased(MouseEvent e) {
	} // Unused

}