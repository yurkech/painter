package com.spongeblob.paint.utils;

import java.awt.Graphics;
import java.util.List;

import com.spongeblob.paint.model.Point;
import com.spongeblob.paint.model.Vector;


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
	
	public static boolean isPointIntersectLineInRadius(Point p, Point p1, Point p2, int radius){
		double distance;
		if (calcInnerProduct(new Vector(p1, p), new Vector(p1, p2)) < 0){
			distance = (new Vector(p1, p)).getLength();
		}
		else if (calcInnerProduct(new Vector(p2, p), new Vector(p2, p1)) < 0){
			distance = (new Vector(p2, p)).getLength();
		}
		else{
			Vector v1 = new Vector(p1, p2);
			Vector v2 = new Vector(p1, p);
			distance = Math.abs((v1.getA1()*v2.getA2() - v1.getA2()*v2.getA1()) / v1.getLength());
		}
		return distance < radius? true: false;
		
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
	
	public static int calcInnerProduct(Vector v1, Vector v2){
		return v1.getA1()*v2.getA1() + v1.getA2()*v2.getA2();
	}
}
