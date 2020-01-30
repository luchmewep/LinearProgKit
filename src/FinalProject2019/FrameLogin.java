package FinalProject2019;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import luchavez.SQLiteKit;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class FrameLogin extends JFrame {

	public static SQLiteKit db = new SQLiteKit("problems.db");
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private ArrayList<Object> user;
	private User u;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		firstTimeSetup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FrameLogin();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public FrameLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		try {
			IconFontSwing.register(FontAwesome.getIconFont());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		setIconImage(IconFontSwing.buildImage(FontAwesome.LOCK, 40, Color.BLACK));
		setSize(500, 330);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 255, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Resource Allocator");
		lblTitle.setBackground(new Color(0, 0, 0));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTitle.setBounds(75, 11, 350, 28);
		lblTitle.setIcon(IconFontSwing.buildIcon(FontAwesome.PIE_CHART, (float) (lblTitle.getHeight()*0.75)));
		contentPane.add(lblTitle);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(75, 50, 350, 200);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(75, 26, 200, 40);
		txtUsername.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Username", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtPassword.setColumns(10);
		txtPassword.setBounds(75, 80, 200, 40);
		panel.add(txtPassword);
		
		ButtonPlusIcon btnLogin = new ButtonPlusIcon("Login", FontAwesome.SIGN_IN, 0, null, ComponentOrientation.RIGHT_TO_LEFT);
//		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(123, 145, 90, 28);
		getRootPane().setDefaultButton(btnLogin);
//		btnLogin.setIcon(IconFontSwing.buildIcon(FontAwesome.SIGN_IN, (float) (btnLogin.getHeight()*0.75)));
//		btnLogin.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnLogin.addActionListener(e->{
			user = db.getOneRow("select * from tbl_users where username = ? and password = ?", new Object[] {txtUsername.getText(), txtPassword.getText()});
			if(user == null) {
				JOptionPane.showMessageDialog(this, "CAUSE: Incorrect username and password combination.\nPlease try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
			}else {
				u = new User(user);
				System.out.println(Arrays.toString(u.getDetails()));
				dispose();
				new FrameMain(u);
			}
		});
		panel.add(btnLogin);
		
		JLabel lblLoginAsGuest = new JLabel("Login as Guest");
		lblLoginAsGuest.setBounds(362, 266, 110, 25);
		lblLoginAsGuest.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
		       new FrameMain(new User(db.getOneRow("select * from tbl_users where user_id = 1")));
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	lblLoginAsGuest.setText("<html><a href=''>Login as Guest</a></html>");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	lblLoginAsGuest.setText("Login as Guest");
		    }
		});
		lblLoginAsGuest.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_SECRET, (float) (lblLoginAsGuest.getHeight()*0.75)));
		contentPane.add(lblLoginAsGuest);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(272, 266, 80, 25);
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
				registerAccount();
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	lblRegister.setText("<html><a href=''>Register</a></html>");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	lblRegister.setText("Register");
		    }
		});
		lblRegister.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_PLUS, (float) (lblRegister.getHeight()*0.75)));
		contentPane.add(lblRegister);
		changeFont(this, new Font("Arial", Font.PLAIN, 12));
		setTitle("Login");
		setVisible(true);
		setLocationRelativeTo(null);
	}
	@SuppressWarnings("unchecked")
	static void firstTimeSetup() {
		ArrayList<Object> tables = db.getOneColumn("SELECT name FROM sqlite_master WHERE type='table' and name = 'tbl_problems'");
		if(tables.size() == 0) {
			db.runQuery("CREATE TABLE 'tbl_users' (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, fname TEXT NOT NULL, lname TEXT NOT NULL);");
			db.runQuery("INSERT into tbl_users values (null, 'guest', 'guest', 'Guest', 'Guest')");
			db.runQuery("CREATE TABLE 'tbl_problems' (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, title TEXT DEFAULT 'No Title', description TEXT DEFAULT 'No Description', isMaximize INTEGER NOT NULL, equation TEXT NOT NULL, constraints TEXT NOT NULL, FOREIGN KEY('user_id') REFERENCES 'tbl_users'('user_id'));");
			db.runQuery("INSERT INTO tbl_problems VALUES (null, 1, 'Gowns', 'Maximize profit for producing gown A and B', '1', '3 x,4 y,2 z', '4,2,2,<=,20/1,1,2,<=,12/1,3,2,<=,18')");
		}
	}
	
	public static void changeFont(Component component, Font font){
		component.setFont(font);
		if(component instanceof JLabel){
			((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
		}else if(component instanceof JPasswordField){
			((JPasswordField) component).setHorizontalAlignment(SwingConstants.CENTER);
		}else if(component instanceof JTextField){
			((JTextField) component).setHorizontalAlignment(SwingConstants.CENTER);
		}else if(component instanceof JSpinner){
			((JSpinner) component).getEditor().setAlignmentX(CENTER_ALIGNMENT);
		}else if(component instanceof JTable){
			DefaultTableCellRenderer cellRenderer = (DefaultTableCellRenderer) ((JTable) component).getDefaultRenderer(Object.class);
			cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			((JTable) component).setDefaultRenderer(Object.class, cellRenderer);
			cellRenderer = (DefaultTableCellRenderer) ((JTable) component).getTableHeader().getDefaultRenderer();
			cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			((JTable) component).getTableHeader().setDefaultRenderer(cellRenderer);
		}
		if(component instanceof Container){
			for (Component child : ((Container) component).getComponents()) {
				changeFont(child, font);
			}
		}
	}
	
	private void registerAccount(){
		DialogRegister register = new DialogRegister(this);
		register.setVisible(true);
	}
}
