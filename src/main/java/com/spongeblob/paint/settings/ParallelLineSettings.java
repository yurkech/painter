package com.spongeblob.paint.settings;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ParallelLineSettings extends AbstractSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1746012019886679598L;
	private boolean isEnabled = false;
	private int width = 0;

	public ParallelLineSettings() {
	};

	public ParallelLineSettings(String title) {
		setTitle(title);
	}

	@JsonIgnore
	private JCheckBox enableChk;

	@JsonIgnore
	private JTextField widthText;

	@Override
	public void activate() {
		enableChk = new JCheckBox("Enabled");
		enableChk.setSelected(isEnabled);
		enableChk.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				isEnabled = enableChk.isSelected();
				widthText.setEnabled(isEnabled);
				getSettingsPanel().updateDrawing();
			}
		});

		widthText = new JTextField(String.valueOf(width), SwingConstants.LEFT);
		widthText.getDocument().addDocumentListener(new DocumentListener() {
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
					width = Integer.parseInt(widthText.getText());
					getSettingsPanel().updateDrawing();
				} catch (Exception e) {
				}
			}
		});

		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setBorder(BorderFactory.createTitledBorder(getTitle()));
		panel.add(enableChk);
		panel.add(new JPanel());
		panel.add(new JLabel("Width:", SwingConstants.LEFT));
		panel.add(widthText);

		getSettingsPanel().add(panel);

	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
