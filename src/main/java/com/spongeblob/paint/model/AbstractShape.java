package com.spongeblob.paint.model;

import java.awt.Color;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	protected Color color;
	protected boolean isSolid;
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isSolid() {
		return isSolid;
	}
	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}
	
}
