package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public class Oval extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706959802243028105L;
	public Oval(){}
	
	public Oval(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "OVAL";
	}
	
	public Oval(int x, int y, Color c, Boolean solid){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
	
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		if(solidSettings.isSolid())
  	 	{
     		if(controlPoints.get(0).x > controlPoints.get(1).x || controlPoints.get(0).y > controlPoints.get(1).y)
     			g.fillOval(controlPoints.get(1).x, controlPoints.get(1).y, controlPoints.get(0).x - controlPoints.get(1).x, controlPoints.get(0).y - controlPoints.get(1).y);
     		else	
     			g.fillOval(controlPoints.get(0).x, controlPoints.get(0).y, controlPoints.get(1).x - controlPoints.get(0).x, controlPoints.get(1).y - controlPoints.get(0).y);
  	 	}
     	else
     	{
     		if(controlPoints.get(0).x > controlPoints.get(1).x || controlPoints.get(0).y > controlPoints.get(1).y)
     			g.drawOval (controlPoints.get(1).x, controlPoints.get(1).y, controlPoints.get(0).x - controlPoints.get(1).x, controlPoints.get(0).y - controlPoints.get(1).y);
     		else
     			g.drawOval (controlPoints.get(0).x, controlPoints.get(0).y, controlPoints.get(1).x - controlPoints.get(0).x, controlPoints.get(1).y - controlPoints.get(0).y);
     	}
	}
}
