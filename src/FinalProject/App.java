package FinalProject;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;

public class App extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 500));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		PanelLogin panelLogin = new PanelLogin();
		contentPane.add(panelLogin, "name_868492983047");
	}
	
	public static void addIcon(JComponent component, IconCode iconCode, float size, Color color, ComponentOrientation componentOrientation){
		try {
			IconFontSwing.register(FontAwesome.getIconFont());
		}catch (Exception e) {}
		if(component instanceof JButton){
			((JButton) component).setIcon(IconFontSwing.buildIcon(iconCode, size == 0 ? 16 : size, color == null ? Color.BLACK : color));
			if(componentOrientation != null){ ((JButton) component).setComponentOrientation(componentOrientation); }
		}
	}
}
