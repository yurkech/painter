package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.spongeblob.paint.settings.ShapeDrawingSettings;
import com.spongeblob.paint.utils.PointUtil;


public class HandLine extends AbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;
	public HandLine(){}
	
	public HandLine(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		((ShapeDrawingSettings)getSettingsByClass(ShapeDrawingSettings.class)).setColor(c);
	}
	
	public void draw(Graphics g) {
		g.setColor(((ShapeDrawingSettings)getSettingsByClass(ShapeDrawingSettings.class)).getColor());
		g.drawPolyline(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
     	
	}
}
