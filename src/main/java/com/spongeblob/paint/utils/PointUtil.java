package com.spongeblob.paint.utils;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class PointUtil {
	protected final static int MARKER_RADIUS = 10;
	
	public static boolean isPointInRadius(Point p1, Point p2, int radius){
		if (((p2.x - radius) <= p1.x) &&
			((p2.x + radius) >= p1.x) &&
			((p2.y - radius) <= p1.y) &&
			((p2.y + radius) >= p1.y))
			{
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public static int[] getXs(List<Point> points){
		int []  xs = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			xs[i] = points.get(i).x;
		}
		return xs;
	}
	
	public static int[] getYs(List<Point> points){
		int []  ys = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			ys[i] = points.get(i).y;
		}
		return ys;
	}
	
	public static void paintCircleAroundPoint(Graphics g, Point p, int radius){
		g.drawOval((int)p.getX() - radius/2, (int)p.getY() - radius/2, radius, radius);
	}
	
	
	public static void paintCircleAroundPoint(Graphics g, Point p){
		paintCircleAroundPoint(g, p, MARKER_RADIUS);
	}
}
