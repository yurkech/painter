package com.spongeblob.paint.settings;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.SettingsPanel;

public abstract class AbstractSettings implements Settings {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1258670672266793706L;
	private SettingsPanel settingsPanel;
	private String title;

	public abstract void activate();

	@JsonIgnore
	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}

	public void setSettingsPanel(SettingsPanel sPanel) {
		this.settingsPanel = sPanel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSettings other = (AbstractSettings) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
