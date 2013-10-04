package com.spongeblob.paint.settings;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CanvasSettings extends AbstractSettings{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8542411917537252382L;
	private int height;
	private int width;
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void activate() {
		JPanel panel = new JPanel(new GridLayout(2,2));
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBorder(BorderFactory.createTitledBorder("Canvas"));
		panel.add(new JLabel("Width:",SwingConstants.LEFT));
		panel.add(new JLabel(String.valueOf(width),SwingConstants.LEFT));
		panel.add(new JLabel("Hight:",SwingConstants.LEFT));
		panel.add(new JLabel(String.valueOf(height),SwingConstants.LEFT));
		
		getSettingsPanel().add(panel);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CanvasSettings other = (CanvasSettings) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	
}
