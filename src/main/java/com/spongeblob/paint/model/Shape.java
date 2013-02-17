package com.spongeblob.paint.model;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;

import com.spongeblob.paint.settings.Settings;

/**
 * @author yurkech
 *
 */
public interface Shape extends Serializable{
	public void draw(Graphics g);
	public void drawControlPoints(Graphics g);
	
	
	public List<Point> getControlPoints();
	public Point getClosestControlPointInRadius(Point p, int radius); 
	public int getClosestControlLineInRadius(Point p, int radius);
	
	public void move(int deltaX, int deltaY);
	
	public List<Settings> getSettings();
}
