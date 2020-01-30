package FinalProject2019;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class example {
	public static void main(String[] args) {
		JTable tbl = new JTable(10, 100);
		JScrollPane scroll = new JScrollPane(tbl);
		tbl.setPreferredScrollableViewportSize(new Dimension(tbl.getPreferredScrollableViewportSize().width, tbl.getRowCount()*tbl.getRowHeight()));
		JOptionPane.showMessageDialog(null, scroll);
	}
}
