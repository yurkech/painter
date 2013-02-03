package com.spongeblob.paint;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import com.spongeblob.paint.settings.Settings;

public class SettingsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7136804231521726408L;
	private Container parent;
	
	public SettingsPanel(Container container){
		this.parent = container;
	}

	public void setSettings(List<Settings> settings) {
		if (settings != null){
			removeAll();
			setLayout(new FlowLayout());
			for (Settings item : settings) {
				add(item.getSettingsPanel());
			}
			
		}
		parent.repaint();
	}
	
	public void setSettings(Settings settings) {
		if (settings != null){
			removeAll();
			setLayout(new FlowLayout());
			add(settings.getSettingsPanel());
			
		}
		parent.repaint();
	}

}
