package FinalProject2019;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

public class DialogRegister extends JDialog {

	private JPanel contentPanel;
	private JLabel txtTitle;
	private JTextField txtID, txtUsername, txtFName, txtLName;
	private JPasswordField txtPassword, txtPassword2;
	private JButton btnOK, btnCancel;
	private User u = null;
	private JPanel panel;
	private int width, height;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DialogRegister dialog = new DialogRegister(new JFrame());
		dialog.setVisible(true);
	}

	public DialogRegister(JFrame frame, User u) {
		this(frame);
		this.u = u;
		setForm();
	}

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public DialogRegister(JFrame frame) {
		super(frame, true);
		setSize(300, 447);
		try {
			IconFontSwing.register(FontAwesome.getIconFont());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		setResizable(false);
		setTitle("Register");
		setLocationRelativeTo(null);
		setIconImage(IconFontSwing.buildImage(FontAwesome.PLUS_SQUARE_O, 20));
		getContentPane().setLayout(new BorderLayout());

		panel = new JPanel();
		panel.setBackground(new Color(51, 204, 153));
		getContentPane().add(panel, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(294, 273));
		contentPanel.setBackground(new Color(204, 255, 204));
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		txtTitle = new JLabel("Register New Account");
		panel.add(txtTitle);
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setIcon(IconFontSwing.buildIcon(FontAwesome.USER_PLUS, 40));
		txtTitle.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));

		//Get Width and Height from ContentPanel
		width = contentPanel.getPreferredSize().width-40;

		txtID = new JTextField();
		txtID.setPreferredSize(new Dimension((width-10)/3, 50));
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtID.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "ID", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		txtID.setEnabled(false);
		
		txtUsername = new JTextField();
		txtUsername.setPreferredSize(new Dimension((width-10)*2/3, 50));
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtUsername.setName("username");
		txtUsername.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Enter Username", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		
		txtPassword = new JPasswordField();
		txtPassword.setPreferredSize(new Dimension(width, 50));
		txtPassword.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setName("password");
		txtPassword.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Enter Password", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		
		txtPassword2 = new JPasswordField();
		txtPassword2.setPreferredSize(new Dimension(width, 50));
		txtPassword2.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtPassword2.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Verify Password", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		
		txtFName = new JTextField();
		txtFName.setPreferredSize(new Dimension(width, 50));
		txtFName.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtFName.setHorizontalAlignment(SwingConstants.CENTER);
		txtFName.setName("fname");
		txtFName.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Enter First Name", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		
		txtLName = new JTextField();
		txtLName.setPreferredSize(new Dimension(width, 50));
		txtLName.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtLName.setHorizontalAlignment(SwingConstants.CENTER);
		txtLName.setName("lname");
		txtLName.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Enter Last Name", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		contentPanel.add(txtID);
		contentPanel.add(txtUsername);
		contentPanel.add(txtPassword);
		contentPanel.add(txtPassword2);
		contentPanel.add(txtFName);
		contentPanel.add(txtLName);
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(51, 204, 153));
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		btnOK = new JButton("Create", IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 16, Color.BLUE));
		btnOK.setHorizontalTextPosition(AbstractButton.LEADING);
		getRootPane().setDefaultButton(btnOK);
		btnOK.addActionListener(e->{
			if(u == null) {
				if(txtPassword.getText().equals(txtPassword2.getText())) {
					new User(txtUsername.getText(), txtPassword.getText(), txtFName.getText(), txtLName.getText());
				}else {
					JOptionPane.showMessageDialog(null, "CAUSE: Password verification failed. Please try again.", "Adding Failed", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				u.setUsername(txtUsername.getText());
				u.setFname(txtFName.getText());
				u.setLname(txtLName.getText());
			}
			dispose();
		});
		buttonPane.add(btnOK);
		btnCancel = new JButton("Cancel", IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 16, Color.RED));
		btnCancel.setHorizontalTextPosition(AbstractButton.LEADING);
		btnCancel.addActionListener(e->{
			//			dispose();
			System.out.println("Size: "+contentPanel.getSize());
		});
		buttonPane.add(btnCancel);

		//Listeners

	}

	private void setForm(){
		setIconImage(IconFontSwing.buildImage(FontAwesome.PENCIL_SQUARE_O, 20));
		setTitle("Update User");
		txtTitle.setText("Update Account");
		txtTitle.setIcon(IconFontSwing.buildIcon(FontAwesome.PENCIL_SQUARE, 40));
		btnOK.setVisible(false);
		txtID.setText(u.getId()+"");
		txtUsername.setText(u.getUsername());
		txtPassword.setText(u.getPassword());
		txtPassword2.setText(u.getPassword());
		txtPassword2.setEnabled(false);
		txtFName.setText(u.getFname());
		txtLName.setText(u.getLname());
		JComponent[] obj = new JComponent[] {txtUsername, txtPassword, txtFName, txtLName};
		for (JComponent component : obj) {
			component.setEnabled(false);
			component.setToolTipText("Click to edit.");
			component.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {

				}
			});
		}
	}
}
