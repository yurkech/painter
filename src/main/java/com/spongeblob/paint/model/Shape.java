package com.spongeblob.paint.model;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;

import com.spongeblob.paint.settings.Settings;


public interface Shape extends Serializable{
	public void draw(Graphics g);
	public void drawPathPoints(Graphics g);
	
	
	public List<Point> getPoints();
	public Point contains(Point p, int radius); 
	public Boolean intersects(Point p, int radius);
	
	public void setFocus(Boolean flag);
	public void move(int deltaX, int deltaY);
	
	public List<Settings> getSettings();
	
}
