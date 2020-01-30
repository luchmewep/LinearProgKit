package FinalProject;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagConstraints;
import javax.swing.border.LineBorder;

import FinalProject2019.FrameMain;
import FinalProject2019.User;
import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class PanelLogin extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public PanelLogin() {
		setPreferredSize(new Dimension(800, 500));
		setMinimumSize(new Dimension(800, 500));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setPreferredSize(new Dimension(400, 250));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		
		JLabel panelLabel = new JLabel("Resource Allocator");
		panelLabel.setFont(new Font("Arial", Font.BOLD, 20));
		panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		panelLabel.setMinimumSize(new Dimension(46, 20));
		panelLabel.setPreferredSize(new Dimension(46, 20));
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(6, 30));
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setPreferredSize(new Dimension(6, 30));
		textField_1.setColumns(10);
		
		JButtonPlusIcon btnLogin = new JButtonPlusIcon("Login", FontAwesome.SIGN_IN, 0, null, true);
		JLinkLabel lblRegister = new JLinkLabel("Create Account", FontAwesome.USER_PLUS, 0, null, false);
		JLinkLabel lblGuestLogin = new JLinkLabel("Login as Guest", FontAwesome.USER_SECRET, 0, null, false);
		
		JLabelPlusIcon labelPlusIcon = new JLabelPlusIcon(null, FontAwesome.USER, 20, null, false);
		labelPlusIcon.setText("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(78)
					.addComponent(panelLabel, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addGap(72))
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(154)
							.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
							.addGap(77))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(31)
							.addComponent(labelPlusIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))))
					.addGap(70))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(187, Short.MAX_VALUE)
					.addComponent(lblRegister, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblGuestLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(textField, 0, 0, Short.MAX_VALUE)
						.addComponent(labelPlusIcon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGuestLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRegister, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);

	}
}
