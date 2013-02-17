package com.spongeblob.paint.settings;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.model.PhysicObjectType;

public class PhysicsSettings implements Settings{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 22598357548211343L;
	private double density = 1.0;
	private int restriction  = 0;
	private int friction = 0;
	private PhysicObjectType type = PhysicObjectType.BORDERTRACK;
	
	@JsonIgnore
	private JTextField textDensity, textRestriction, textFriction; 
	
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
	
	@JsonIgnore
	public JPanel getSettingsPanel() {
		JPanel panel = new JPanel(new GridLayout(3,2));
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setBorder(BorderFactory.createTitledBorder("Physics"));
		panel.add(new JLabel("Density:",SwingConstants.LEFT));
		textDensity = new JTextField(String.valueOf(density), SwingConstants.LEFT);
		textDensity.getDocument().addDocumentListener(new DocumentListener() {
			  	  public void changedUpdate(DocumentEvent e) {
				    warn();
				  }
				  public void removeUpdate(DocumentEvent e) {
				    warn();
				  }
				  public void insertUpdate(DocumentEvent e) {
				    warn();
				  }

				  public void warn() {
					 try{ 
				    	 density = Double.parseDouble(textDensity.getText());
					 }
					 catch (Exception e) {
					}   
				  }
				});
		panel.add(textDensity);
		panel.add(new JLabel("Restriction:",SwingConstants.LEFT));
		textRestriction = new JTextField(String.valueOf(restriction), SwingConstants.LEFT);
		textRestriction.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				 try{ 
			    	 restriction = Integer.parseInt(textRestriction.getText());
				 }    
				 catch (Exception e) {
				}    
			  }
			});
		panel.add(textRestriction);
		panel.add(new JLabel("Friction:",SwingConstants.LEFT));
		textFriction = new JTextField(String.valueOf(friction), SwingConstants.LEFT);
		textFriction.getDocument().addDocumentListener(new DocumentListener() {
		  	  public void changedUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    warn();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				 try{ 
			    	 friction = Integer.parseInt(textFriction.getText());
				 }    
			     catch (Exception e) {
				}        
			  }
			});
		panel.add(textFriction);
		return panel;
	}
	public PhysicObjectType getType() {
		return type;
	}
	public void setType(PhysicObjectType type) {
		this.type = type;
	}
}
