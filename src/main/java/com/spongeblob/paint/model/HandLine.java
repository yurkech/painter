package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.spongeblob.paint.utils.PointUtil;


public class HandLine extends MultipointsShape implements Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;
	
	
	public HandLine(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		color = c;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawPolyline(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
     	
	}
}
