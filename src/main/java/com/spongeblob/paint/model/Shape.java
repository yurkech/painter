package com.spongeblob.paint.model;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;

public interface Shape extends Serializable{
	public void draw(Graphics g);
	public void drawPathPoints(Graphics g);
	
	public List<Point> getPathPoints();
	public Point contains(Point p, int radius); 
	
}
