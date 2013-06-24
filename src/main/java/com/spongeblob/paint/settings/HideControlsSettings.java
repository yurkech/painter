package com.spongeblob.paint.settings;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.codehaus.jackson.annotate.JsonIgnore;

public class HideControlsSettings extends AbstractSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7069408436981382146L;
	private boolean hide = false;
	
	@JsonIgnore
	private JCheckBox hideChk;
	
	public HideControlsSettings() {
	}

	public HideControlsSettings(String title) {
		setTitle(title);
	}

	@Override
	public void activate() {
		hideChk = new JCheckBox("Enabled");
		hideChk.setSelected(hide);
		hideChk.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				hide = hideChk.isSelected();
				getSettingsPanel().updateDrawing();
			}
		});
		
		JPanel panel = new JPanel(new GridLayout(1, 1));
		panel.setPreferredSize(new Dimension(200, 50));
		panel.setBorder(BorderFactory.createTitledBorder(getTitle()));
		panel.add(hideChk);

		getSettingsPanel().add(panel);

	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

}
