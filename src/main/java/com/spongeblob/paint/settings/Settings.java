package com.spongeblob.paint.settings;

import java.io.Serializable;

import com.spongeblob.paint.SettingsPanel;


public interface Settings extends Serializable{
	
	public void activate();
	public void setSettingsPanel(SettingsPanel sPanel);
}
