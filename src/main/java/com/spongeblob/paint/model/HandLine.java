package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.spongeblob.paint.utils.PointUtil;

public class HandLine extends AbstractShape implements Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;
	private LinkedList<Point> points;
	
	
	public HandLine(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		color = c;
	}
	
	public void addPoint(int x, int y){
		points.add(new Point(x, y));
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		for (int i = 0; i < points.size() - 1; i++) {
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);			
		}
     	
	}

	public void drawPathPoints(Graphics g) {
		for (Point point : points) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
		
	}

	public List<Point> getPathPoints() {
		return points;
	}

	public Point contains(Point p, int radius) {
		for (Point point : points) {
			if (PointUtil.isPointInRadius(point, p, radius))
				return point;
		}
		return null;	
	}
}
