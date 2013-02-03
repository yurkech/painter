package com.spongeblob.paint.settings;

import java.io.Serializable;

import javax.swing.JPanel;

public interface Settings extends Serializable{
	public JPanel getSettingsPanel();
}
