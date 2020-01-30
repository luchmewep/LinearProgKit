package FinalProject;

import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JButton;

import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class JButtonPlusIcon extends JButton {
	public JButtonPlusIcon() {
		this("Text", FontAwesome.AT, 0, null, true);
	}
	public JButtonPlusIcon(String text, IconCode iconCode, float size, Color color, boolean isRightToLeft) {
		super(text == null ? "" : text);
		try { IconFontSwing.register(FontAwesome.getIconFont()); } catch (Exception e) {}
		if(iconCode != null) setIcon(IconFontSwing.buildIcon(iconCode, size == 0 ? 16 : size, color == null ? Color.BLACK : color));
		if(isRightToLeft){ setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); }
	}

}
