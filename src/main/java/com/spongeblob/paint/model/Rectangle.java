package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Rectangle extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6085392574960675124L;
	public Rectangle(){};

	public Rectangle(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "RECTANGLE";
	}
	
	public Rectangle(int x, int y, Color c, Boolean solid){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x, y));
		colorSettings.setColor(c);
	}
	
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		if(solidSettings.isSolid())
  	 	{
  	 		if(points.get(0).x > points.get(1).x || points.get(0).y > points.get(1).y)
  	 			g.fillRect (points.get(1).x, points.get(1).y, points.get(0).x - points.get(1).x, points.get(0).y - points.get(1).y);
  	 		else
  	 			g.fillRect (points.get(0).x, points.get(0).y, points.get(1).x - points.get(0).x, points.get(1).y - points.get(0).y);
  	 	}
     	else
     	{
     		if(points.get(0).x > points.get(1).x || points.get(0).y > points.get(1).y)
     			g.drawRect (points.get(1).x, points.get(1).y, points.get(0).x - points.get(1).x, points.get(0).y - points.get(1).y);
     		else
     			g.drawRect (points.get(0).x, points.get(0).y, points.get(1).x - points.get(0).x, points.get(1).y - points.get(0).y);
     	}
		
	}
}
