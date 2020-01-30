package FinalProject2019;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

public class random {
	public static void main(String[] args) {
		Object[] panes = new Object[3];
		for (int i = 0; i < panes.length; i++) {
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			JTable t = new JTable(new String[2][3], new String[] {"x", "y", "z"});
			p.add(t.getTableHeader(), BorderLayout.NORTH);
			p.add(t, BorderLayout.CENTER);
			panes[i] = p;
		}
		JOptionPane.showMessageDialog(null, panes);
	}
}
