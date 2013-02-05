package com.spongeblob.paint.model;

import java.awt.Color;
import java.util.LinkedList;

import com.spongeblob.paint.settings.ShapeColorSettings;


public class HandLine extends AbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;
	public HandLine(){}
	
	public HandLine(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).setColor(c);
	}
}
