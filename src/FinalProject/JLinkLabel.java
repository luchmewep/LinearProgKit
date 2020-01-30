package FinalProject;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;

public class JLinkLabel extends JLabelPlusIcon {
	public JLinkLabel() {
		this("Text", FontAwesome.AT, 0, null, false);
	}
	public JLinkLabel(String text, IconCode iconCode, float size, Color color, boolean isRightToLeft) {
		super(text, iconCode, size, color, isRightToLeft);
		addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseEntered(MouseEvent e) {
		    	setText("<html><a href=''>"+text+"</a></html>");
		    }
		 
		    @Override
		    public void mouseExited(MouseEvent e) {
		    	setText(text);
		    }
		});
	}
}
