package com.spongeblob.paint.settings;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class ShapePhysicsSettings implements Settings{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 22598357548211343L;
	private double density = 1.0;
	private int restriction  = 0;
	private int friction = 0;
	
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public int getRestriction() {
		return restriction;
	}
	public void setRestriction(int restriction) {
		this.restriction = restriction;
	}
	public int getFriction() {
		return friction;
	}
	public void setFriction(int friction) {
		this.friction = friction;
	}
	public JPanel getSettingsPanel() {
		JPanel panel = new JPanel(new GridLayout(3,2));
		panel.setBorder(BorderFactory.createTitledBorder("Physics"));
		panel.add(new JLabel("Density:",SwingConstants.LEFT));
		panel.add(new JLabel(String.valueOf(density),SwingConstants.LEFT));
		panel.add(new JLabel("Restriction:",SwingConstants.LEFT));
		panel.add(new JLabel(String.valueOf(restriction),SwingConstants.LEFT));
		panel.add(new JLabel("Friction:",SwingConstants.LEFT));
		panel.add(new JLabel(String.valueOf(friction),SwingConstants.LEFT));
		return panel;
	}
	
	
}
