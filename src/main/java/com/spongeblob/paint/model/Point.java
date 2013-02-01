package com.spongeblob.paint.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Point extends java.awt.Point{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6923490625845115006L;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@JsonIgnore
	public Point getLocation() {
		return new Point(x, y);
	}
}
