package com.spongeblob.paint.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ShapeDrawingSettings implements Settings{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5130774767133682455L;
	
	private Color pathPointsColor;
	private Color color;
	
	public ShapeDrawingSettings(){
		this.pathPointsColor = Color.RED;
		this.color = Color.BLACK;
	}
	
	public ShapeDrawingSettings(Color color){
		this.pathPointsColor = Color.RED;
		this.color = color;
	}
	
	private JButton pathPointsColorBtn, colorBtn;

	public Color getPathPointsColor() {
		return pathPointsColor;
	}

	public void setPathPointsColor(Color pathPointsColor) {
		this.pathPointsColor = pathPointsColor;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public JPanel getSettingsPanel() {
		pathPointsColorBtn = new JButton();
		pathPointsColorBtn.setOpaque(true);
		pathPointsColorBtn.setBackground(pathPointsColor);
		pathPointsColorBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pathPointsColorBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					Color tColor = JColorChooser.showDialog(null,"Path Points Color", pathPointsColor);
					if(tColor!=null)
					{
						pathPointsColor = tColor;
						pathPointsColorBtn.setBackground(pathPointsColor);
					}
				}
			}
		);
		
		colorBtn = new JButton();
		colorBtn.setOpaque(true);
		colorBtn.setBackground(color);
		colorBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		colorBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					Color tColor = JColorChooser.showDialog(null,"Color", color);
					if(tColor!=null)
					{
						color = tColor;
						colorBtn.setBackground(color);
					}
				}
			}
		);
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setBorder(BorderFactory.createTitledBorder("Color"));
		panel.add(pathPointsColorBtn);
		panel.add(colorBtn);
		return panel;
	}

}
