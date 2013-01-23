package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.spongeblob.paint.utils.PointUtil;

public class Polygon extends AbstractShape implements Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2411393086421989720L;
    private LinkedList<Point> points;
    private boolean isClosed;
	
	
	public Polygon(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		color = c;
	}
	
	public Polygon(int x, int y, Color c, Boolean solid){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		color = c;
		isSolid = solid;
	}
	
	public void addPoint(int x, int y){
		points.add(new Point(x, y));
	}
	
	public int[] getXs(){
		int []  xs = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			xs[i] = points.get(i).x;
		}
		return xs;
	}
	
	public int[] getYs(){
		int []  ys = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			ys[i] = points.get(i).y;
		}
		return ys;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		if (isClosed){
			if(isSolid)
	  	 	{
				g.fillPolygon(getXs(), getYs(), points.size());
	  	 	}
	     	else
	     	{
	     		g.drawPolygon(getXs(), getYs(), points.size());
	     	}
		}
		else{
			g.drawPolyline(getXs(), getYs(), points.size());
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

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

}
