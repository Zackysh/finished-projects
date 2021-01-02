package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import utils.TextPrompt;


public class V_Login extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					V_Login frame = new V_Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	
//	private Color colorBotones...
	
	private JTextField tf_Username;
	private TextPrompt tp_Username;
	private JPasswordField pf_Password;
	private TextPrompt tp_Password;
	
	private JLabel lbl_Welcome;
	private JLabel lbl_Login;
	private JLabel background;
	
	private JButton login;

	
	/**
	 * Create the frame.
	 */
	public V_Login() {
		// Pane definition
		setIconImage(Toolkit.getDefaultToolkit().getImage("imges\\iconDB.png"));
		contentPane = new JPanel();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 425, 480);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// LABELS
			// Welcome
		lbl_Welcome = new JLabel("Welcome!");
		lbl_Welcome.setBounds(150, 21, 130, 42);
		lbl_Welcome.setFont(new Font("Corbel", Font.BOLD, 24));
		lbl_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_Welcome);
			// Login
		lbl_Login = new JLabel("Login");
		lbl_Login.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Login.setFont(new Font("Corbel", Font.PLAIN, 20));
		lbl_Login.setBounds(131, 83, 175, 42);
		contentPane.add(lbl_Login);
		
		// FIELDS
			// Username
		tf_Username = new JTextField();
		tf_Username.setBounds(150, 183, 130, 25);
		tf_Username.setHorizontalAlignment(SwingConstants.LEFT);
		tf_Username.setColumns(10);
		tf_Username.setForeground(new Color(150, 0, 0));
		tf_Username.setDisabledTextColor(new Color(150, 0, 0));
		tf_Username
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 10, 5, 5)));
		getContentPane().add(tf_Username);
			// Password
		pf_Password = new JPasswordField();
		pf_Password.setBounds(150, 233, 130, 25);
		pf_Password.setHorizontalAlignment(SwingConstants.LEFT);
		pf_Password.setColumns(10);
		pf_Password.setForeground(new Color(150, 0, 0));
		pf_Password.setDisabledTextColor(new Color(150, 0, 0));
		pf_Password
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 10, 5, 5)));
		getContentPane().add(pf_Password);
		
		// PLACEHOLDERS - Podéis ignorar esto
			// Username
		tp_Username = new TextPrompt("Username", tf_Username);
		tp_Username.setHorizontalAlignment(SwingConstants.LEADING);
		tp_Username.changeAlpha(0.75f);
		tp_Username.setForeground(new Color(150, 0, 0, 180));
		tp_Username.changeStyle(Font.ITALIC);
			// Password
		// TODO Place holder para JPasswordField
//		tp_Password = new TextPrompt("Password", tp_Password);
//		tp_Password.setHorizontalAlignment(SwingConstants.LEADING);
//		tp_Password.changeAlpha(0.75f);
//		tp_Password.setForeground(new Color(150, 0, 0, 180));
//		tp_Password.changeStyle(Font.ITALIC);

		// BUTTONS
			// Login
		login = new JButton("login");
		login.addActionListener(this);
		login.setFont(new Font("Arial", Font.PLAIN, 14));
		login.setBounds(169, 335, 80, 23);
		login.setForeground(new Color(255, 255, 255));
		login.setBackground(new Color(122, 214, 125));
		login
				.setBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(5, 0, 5, 0)));
		contentPane.add(login);
		
		// Pane Background
		background = new JLabel(new ImageIcon("imges\\background.png"));
		background.setBounds(0, 0, 419, 451);
		contentPane.add(background);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

}