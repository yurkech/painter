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
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "polygon";
	}
	
	public Polygon(int x, int y, Color c, Boolean solid){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		colorSettings.setColor(c);
	}
}
