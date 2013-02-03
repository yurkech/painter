package com.spongeblob.paint;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import com.spongeblob.paint.settings.Settings;

public class SettingsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7136804231521726408L;
	
	public void setSettings(List<Settings> settings) {
		if (settings != null){
			removeAll();
			setLayout(new FlowLayout());
			for (Settings item : settings) {
				add(item.getSettingsPanel());
			}
			
		}
		repaint();
	}
	
	public void setSettings(Settings settings) {
		if (settings != null){
			removeAll();
			setLayout(new FlowLayout());
			add(settings.getSettingsPanel());
			
		}
		repaint();
	}

}
