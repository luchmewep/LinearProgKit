package FinalProject2019;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class FormattedTextField extends JFormattedTextField {
	public FormattedTextField() {
		NumberFormatter numFormatter  = new NumberFormatter();
		numFormatter.setAllowsInvalid(false);
		numFormatter.install(this);
	}
	
	public static void main(String[] args) {
		FormattedTextField x = new FormattedTextField();
		JOptionPane.showMessageDialog(null, x);
		System.out.println(x.getValue());
	}
	
	

}
