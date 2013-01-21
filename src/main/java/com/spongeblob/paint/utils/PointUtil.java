package com.spongeblob.paint.utils;

import java.awt.Graphics;
import java.awt.Point;

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
	
	public static void paintCircleAroundPoint(Graphics g, Point p, int radius){
		g.drawOval((int)p.getX() - radius/2, (int)p.getY() - radius/2, radius, radius);
	}
	
	
	public static void paintCircleAroundPoint(Graphics g, Point p){
		paintCircleAroundPoint(g, p, MARKER_RADIUS);
	}
}
