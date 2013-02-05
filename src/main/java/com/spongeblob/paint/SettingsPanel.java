package com.spongeblob.paint;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


import com.spongeblob.paint.settings.Settings;

public class SettingsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7136804231521726408L;
	private List<Settings> settings;
	
	public List<Settings> getSettings() {
		return settings;
	}

	public void setSettings(List<Settings> settings) {
		this.settings = settings;
		update();
	}
	
	public void setSettings(Settings settings) {
		this.settings = new LinkedList<Settings>();
		this.settings.add(settings);
		update();
	}
	
	public void update() {
		if (settings != null) {
			removeAll();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			for (Settings item : settings) {
				add(item.getSettingsPanel());
			}
		}	
		updateUI();
	}

}
