package com.spongeblob.paint.settings;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ShapeSolidSettings extends AbstractSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7635818754896749119L;
	private boolean isSolid = false;
	private boolean isFilled = false;

	@JsonIgnore
	private JCheckBox solidChk, fillChk;

	public ShapeSolidSettings() {
	};

	public ShapeSolidSettings(String title) {
		setTitle(title);
	}

	@JsonProperty(value = "isSolid")
	public boolean isSolid() {
		return isSolid;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	@JsonProperty(value = "isFilled")
	public boolean isFilled() {
		return isFilled;
	}

	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	@Override
	public void activate() {
		solidChk = new JCheckBox("Solid");
		solidChk.setSelected(isSolid);
		solidChk.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				isSolid = solidChk.isSelected();
				fillChk.setEnabled(isSolid);
				getSettingsPanel().updateDrawing();
			}
		});
		fillChk = new JCheckBox("Fill");
		fillChk.setSelected(isFilled);
		fillChk.setEnabled(isSolid);
		fillChk.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				isFilled = fillChk.isSelected();
				getSettingsPanel().updateDrawing();
			}
		});
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setBorder(BorderFactory.createTitledBorder("Solid"));
		panel.add(solidChk);
		panel.add(fillChk);

		getSettingsPanel().add(panel);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (isFilled ? 1231 : 1237);
		result = prime * result + (isSolid ? 1231 : 1237);
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
		ShapeSolidSettings other = (ShapeSolidSettings) obj;
		if (isFilled != other.isFilled)
			return false;
		if (isSolid != other.isSolid)
			return false;
		return true;
	}

}
