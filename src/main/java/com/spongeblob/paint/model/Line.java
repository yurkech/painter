package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Line extends AbstractShape{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109490022085804859L;
	public Line(){}
	
	public Line(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "LINE";
	}
	
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
     	g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
	}

}
