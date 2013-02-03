package com.spongeblob.paint;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBarPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5281740009324771898L;
	private JLabel info;

	public StatusBarPanel()
	{
		this.setLayout(new FlowLayout());
		add(info = new JLabel());
	}

	public void showStatus(String status)
	{
		info.setText(status);
	}
}