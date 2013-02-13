package com.spongeblob.paint;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBarPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5281740009324771898L;
	private JLabel point, zoom;

	public StatusBarPanel()
	{
		this.setLayout(new FlowLayout());
		add(point = new JLabel());
		add(zoom = new JLabel());
	}

	public void showPoint(String status)
	{
		point.setText(status);
	}
	
	public void showZoom(String status)
	{
		zoom.setText(status);
	}
}