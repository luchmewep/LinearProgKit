package FinalProject2019;

import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JButton;

import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class ButtonPlusIcon extends JButton {
	public ButtonPlusIcon(String text, IconCode iconCode, float size, Color color, ComponentOrientation componentOrientation) {
		super(text);
		try {
			IconFontSwing.register(FontAwesome.getIconFont());
		} catch (Exception e) {
		}
		if(size == 0 && color == null){
			System.out.println("2 parameters are null");
			setIcon(IconFontSwing.buildIcon(iconCode, 16));
			System.out.println(getIcon());
		}else if(color == null){
			setIcon(IconFontSwing.buildIcon(iconCode, size));
		}else if(size == 0){
			setIcon(IconFontSwing.buildIcon(iconCode, 16, color));
		}
		if(componentOrientation != null){
			setComponentOrientation(componentOrientation);
		}
	}

}
