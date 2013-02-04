package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.spongeblob.paint.settings.ShapeDrawingSettings;

public class Oval extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706959802243028105L;
	public Oval(){}
	
	public Oval(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x, y));
		((ShapeDrawingSettings)getSettingsByClass(ShapeDrawingSettings.class)).setColor(c);
	}
	
	public Oval(int x, int y, Color c, Boolean solid){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x, y));
		((ShapeDrawingSettings)getSettingsByClass(ShapeDrawingSettings.class)).setColor(c);
		isSolid = solid; 
	}
	
	public void draw(Graphics g) {
		g.setColor(((ShapeDrawingSettings)getSettingsByClass(ShapeDrawingSettings.class)).getColor());
		if(isSolid)
  	 	{
     		if(points.get(0).x > points.get(1).x || points.get(0).y > points.get(1).y)
     			g.fillOval(points.get(1).x, points.get(1).y, points.get(0).x - points.get(1).x, points.get(0).y - points.get(1).y);
     		else	
     			g.fillOval(points.get(0).x, points.get(0).y, points.get(1).x - points.get(0).x, points.get(1).y - points.get(0).y);
  	 	}
     	else
     	{
     		if(points.get(0).x > points.get(1).x || points.get(0).y > points.get(1).y)
     			g.drawOval (points.get(1).x, points.get(1).y, points.get(0).x - points.get(1).x, points.get(0).y - points.get(1).y);
     		else
     			g.drawOval (points.get(0).x, points.get(0).y, points.get(1).x - points.get(0).x, points.get(1).y - points.get(0).y);
     	}
	}
}
