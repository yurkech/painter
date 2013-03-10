package com.spongeblob.paint.settings;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.SettingsPanel;

public abstract class AbstractSettings implements Settings{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1258670672266793706L;
	private SettingsPanel settingsPanel;

	public abstract void activate();

	@JsonIgnore
	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}

	public void setSettingsPanel(SettingsPanel sPanel) {
		this.settingsPanel = sPanel;
	}

}
