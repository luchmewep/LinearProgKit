package FinalProject2019;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

public class FrameMain extends JFrame {

	private JPanel contentPane;
	private JDialog dialog;
	private User u;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				new FrameMain(new User(FrameLogin.db.getOneRow("select * from tbl_users where user_id = 1")));
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameMain(User u) {
		this.u = u;
		setTitle("Resource Allocator");
		try {
			IconFontSwing.register(FontAwesome.getIconFont());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		addIcon(this, FontAwesome.PIE_CHART, Color.RED, false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(e->{
			dispose();
			new FrameLogin();
		});
		btnLogout.setBounds(774, 11, 90, 25);
		addIcon(btnLogout, FontAwesome.POWER_OFF, null, true);
		contentPane.add(btnLogout);
		JLabel lblWelcome = new JLabel("Welcome, "+u.getFname().toUpperCase()+"!");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblWelcome.setForeground(new Color(0, 0, 0));
		lblWelcome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWelcome.setBounds(305, 11, 459, 25);
		lblWelcome.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
		       openAccount();
		    }
		 
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	lblWelcome.setText("<html><a href=''>"+"Welcome, "+u.getFname().toUpperCase()+"!"+"</a></html>");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	lblWelcome.setText("Welcome, "+u.getFname().toUpperCase()+"!");
		    }
		});
		addIcon(lblWelcome, FontAwesome.USER, null, false);
		contentPane.add(lblWelcome);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(305, 59, 558, 273);
		contentPane.add(tabbedPane);
		
		JButton btnAddProblem = new JButton("New Problem");
		getRootPane().setDefaultButton(btnAddProblem);
		btnAddProblem.addActionListener(e->{
			DialogAddProblem add = new DialogAddProblem(this, u);
			add.setVisible(true);
		});
		btnAddProblem.setBounds(10, 12, 120, 25);
		addIcon(btnAddProblem, FontAwesome.PLUS_CIRCLE, null, true);
		contentPane.add(btnAddProblem);
		
		JButton btnViewProblems = new JButton("Open Problems");
		btnViewProblems.addActionListener(e->{
			dialog = new JDialog(this, "View Problems");
			addIcon(dialog, FontAwesome.FOLDER_OPEN, null, false);
			dialog.setLayout(null);
			dialog.setSize(400, 300);
			dialog.setLocationRelativeTo(this);
			dialog.setResizable(false);
			dialog.setVisible(true);
		});
		btnViewProblems.setBounds(140, 11, 120, 25);
		addIcon(btnViewProblems, FontAwesome.FOLDER_OPEN, null, true);
		contentPane.add(btnViewProblems);
		FrameLogin.changeFont(this, new Font("Arial", Font.PLAIN, 12));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void openAccount(){
		DialogRegister register = new DialogRegister(this, u);
		register.setVisible(true);
	}
	
	public void addIcon(Component component, FontAwesome fontawesome, Color color, boolean isIconRight) {
		IconFontSwing.register(FontAwesome.getIconFont());
		if(color == null) {
			color = component.getForeground();
		}
		if(component instanceof JButton) {
			((JButton) component).setIcon(IconFontSwing.buildIcon(fontawesome, (float) (component.getHeight()*0.75), color));
		}else if (component instanceof JLabel) {
			((JLabel) component).setIcon(IconFontSwing.buildIcon(fontawesome, (float) (component.getHeight()*0.75), color));
		}else if (component instanceof JFrame) {
			((JFrame) component).setIconImage(IconFontSwing.buildImage(fontawesome, 40, color));
		}else if (component instanceof JDialog) {
			((JDialog) component).setIconImage(IconFontSwing.buildImage(fontawesome, 40, color));
		}
		if(isIconRight) component.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}
}
