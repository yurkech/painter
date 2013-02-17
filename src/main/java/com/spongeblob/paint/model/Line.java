package com.spongeblob.paint.model;

import java.awt.Color;
import java.util.LinkedList;


public class Line extends AbstractShape{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109490022085804859L;
	public Line(){}
	
	public Line(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
}
