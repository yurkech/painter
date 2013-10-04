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

public class PhysicsSettings extends AbstractSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = 22598357548211343L;
	private double density = 1.0;
	private int restriction = 0;
	private int friction = 0;

	@JsonIgnore
	private JTextField textDensity, textRestriction, textFriction;

	public PhysicsSettings() {
	};

	public PhysicsSettings(String title) {
		setTitle(title);
	}

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

	@Override
	public void activate() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setBorder(BorderFactory.createTitledBorder("Physics"));
		panel.add(new JLabel("Density:", SwingConstants.LEFT));
		textDensity = new JTextField(String.valueOf(density),
				SwingConstants.LEFT);
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
				try {
					density = Double.parseDouble(textDensity.getText());
				} catch (Exception e) {
				}
			}
		});
		panel.add(textDensity);
		panel.add(new JLabel("Restriction:", SwingConstants.LEFT));
		textRestriction = new JTextField(String.valueOf(restriction),
				SwingConstants.LEFT);
		textRestriction.getDocument().addDocumentListener(
				new DocumentListener() {
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
						try {
							restriction = Integer.parseInt(textRestriction
									.getText());
						} catch (Exception e) {
						}
					}
				});
		panel.add(textRestriction);
		panel.add(new JLabel("Friction:", SwingConstants.LEFT));
		textFriction = new JTextField(String.valueOf(friction),
				SwingConstants.LEFT);
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
				try {
					friction = Integer.parseInt(textFriction.getText());
				} catch (Exception e) {
				}
			}
		});
		panel.add(textFriction);

		getSettingsPanel().add(panel);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(density);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + friction;
		result = prime * result + restriction;
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
		PhysicsSettings other = (PhysicsSettings) obj;
		if (Double.doubleToLongBits(density) != Double
				.doubleToLongBits(other.density))
			return false;
		if (friction != other.friction)
			return false;
		if (restriction != other.restriction)
			return false;
		return true;
	}

}
