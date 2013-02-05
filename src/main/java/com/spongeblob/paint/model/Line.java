package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.spongeblob.paint.settings.ShapeColorSettings;

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
		((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).setColor(c);
	}
	
	public void draw(Graphics g) {
		g.setColor(((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).getColor());
     	g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
	}

}
