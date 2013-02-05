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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


public class ShapeColorSettings implements Settings{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5130774767133682455L;
	
	private Color pathPointsColor;
	private Color color;
	
	public ShapeColorSettings(){
		this.pathPointsColor = Color.RED;
		this.color = Color.BLACK;
	}
	
	public ShapeColorSettings(Color color){
		this.pathPointsColor = Color.RED;
		this.color = color;
	}
	
	@JsonIgnore
	private JButton pathPointsColorBtn, colorBtn;

	@JsonIgnore
	public Color getPathPointsColor() {
		return pathPointsColor;
	}
	
	@JsonProperty(value="pathPointsColor")
	public String getPathPointsColorAsString() {
		return String.valueOf(pathPointsColor.getRGB());
	}

	@JsonIgnore
	public void setPathPointsColor(Color pathPointsColor) {
		this.pathPointsColor = pathPointsColor;
	}
	
	@JsonProperty(value="pathPointsColor")
	public void setPathPointsColor(String pathPointsColor) {
		this.pathPointsColor = new Color(Integer.parseInt(pathPointsColor));
	}

	@JsonIgnore
	public Color getColor() {
		return color;
	}
	
	@JsonProperty(value = "color")
	public String getColorAsString() {
		return String.valueOf(color.getRGB());
	}

	@JsonIgnore
	public void setColor(Color color) {
		this.color = color;
	}
	
	@JsonProperty(value = "color")
	public void setColorFromStrin(String color) {
		this.color = new Color(Integer.parseInt(color));
	}

	@JsonIgnore
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
