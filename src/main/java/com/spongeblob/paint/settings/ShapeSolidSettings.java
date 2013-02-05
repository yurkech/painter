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

public class ShapeSolidSettings implements Settings{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7635818754896749119L;
	private boolean isSolid;
	private boolean isFilled;
	
	@JsonIgnore
	private JCheckBox solidChk, fillChk;
	
	
	@JsonIgnore
	public JPanel getSettingsPanel() {
		solidChk = new JCheckBox("Solid");
		solidChk.setSelected(isSolid);
		solidChk.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent event)
				{
					isSolid = solidChk.isSelected();
				}	
			}
		);	
		fillChk = new JCheckBox("Fill");
		fillChk.setSelected(isSolid);
		fillChk.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent event)
				{
					isFilled = fillChk.isSelected();
				}	
			}
		);	
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setBorder(BorderFactory.createTitledBorder("Solid"));
		panel.add(solidChk);
		panel.add(fillChk);
		return panel;
	}

	@JsonProperty(value="isSolid")
	public boolean isSolid() {
		return isSolid;
	}


	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}


	@JsonProperty(value="isFilled")
	public boolean isFilled() {
		return isFilled;
	}


	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
}
