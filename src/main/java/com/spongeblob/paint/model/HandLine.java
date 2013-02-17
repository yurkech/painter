package com.spongeblob.paint.model;

import java.awt.Color;
import java.util.LinkedList;


public class HandLine extends AbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;
	public HandLine(){}
	
	public HandLine(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "POLYGON";
	}
}
