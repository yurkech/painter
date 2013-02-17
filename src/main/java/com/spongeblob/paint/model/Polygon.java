package com.spongeblob.paint.model;

import java.awt.Color;
import java.util.LinkedList;


public class Polygon extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2411393086421989720L;
    
    public Polygon(){}
	
	public Polygon(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
	
	public Polygon(int x, int y, Color c, Boolean solid){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
}
