package com.spongeblob.paint.model;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.spongeblob.paint.settings.Settings;

/**
 * @author yurkech
 *
 */
public interface Shape<T extends Point> extends Serializable, Cloneable{
	public void draw(Graphics g);
	public void drawControlPoints(Graphics g);
	public void scale(float dX, float dY);
	
	public List<T> getControlPoints();
	public Point getClosestControlPointInRadius(Point p, int radius); 
	public int getClosestControlLineInRadius(Point p, int radius);
	
	public void move(int deltaX, int deltaY);
	
	public Map<String, Settings> getShapeSettings();
}
