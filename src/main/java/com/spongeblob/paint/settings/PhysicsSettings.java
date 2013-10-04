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

import com.spongeblob.paint.model.PhysicObject;

public class PhysicsSettings extends AbstractSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = 22598357548211343L;
	private static final String TITLE = "Physics";

	private PhysicObject object;

	@JsonIgnore
	private JTextField textDensity, textRestriction, textFriction, textId,
			textType;

	public PhysicsSettings(PhysicObject object) {
		setObject(object);
		setTitle(TITLE);
	};

	public PhysicsSettings(PhysicObject object, String title) {
		setObject(object);
		setTitle(title);
	}

	@Override
	public void activate() {
		JPanel panel = new JPanel(new GridLayout(5, 2));
		panel.setPreferredSize(new Dimension(200, 150));
		panel.setBorder(BorderFactory.createTitledBorder("Physics"));
		
		panel.add(new JLabel("ID:", SwingConstants.LEFT));
		textId = new JTextField(String.valueOf(getObject()
				.getId()), SwingConstants.LEFT);
		panel.add(textId);
		
		panel.add(new JLabel("Type:", SwingConstants.LEFT));
		textType = new JTextField(String.valueOf(getObject()
				.getType()), SwingConstants.LEFT);
		panel.add(textType);
		
		panel.add(new JLabel("Density:", SwingConstants.LEFT));
		textDensity = new JTextField(String.valueOf(getObject().getDensity()),
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
					getObject().setDensity(
							Double.parseDouble(textDensity.getText()));
				} catch (Exception e) {
				}
			}
		});
		panel.add(textDensity);
		panel.add(new JLabel("Restriction:", SwingConstants.LEFT));
		textRestriction = new JTextField(String.valueOf(getObject()
				.getRestriction()), SwingConstants.LEFT);
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
							getObject()
									.setRestriction(
											Integer.parseInt(textRestriction
													.getText()));
						} catch (Exception e) {
						}
					}
				});
		panel.add(textRestriction);
		panel.add(new JLabel("Friction:", SwingConstants.LEFT));
		textFriction = new JTextField(
				String.valueOf(getObject().getFriction()), SwingConstants.LEFT);
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
					getObject().setFriction(
							Integer.parseInt(textFriction.getText()));
				} catch (Exception e) {
				}
			}
		});
		panel.add(textFriction);

		getSettingsPanel().add(panel);
	}

	/**
	 * @return the object
	 */
	public PhysicObject getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(PhysicObject object) {
		this.object = object;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

}
