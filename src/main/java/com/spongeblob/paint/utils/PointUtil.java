package com.spongeblob.paint.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.spongeblob.paint.model.MarkedPoint;
import com.spongeblob.paint.model.Marker;
import com.spongeblob.paint.model.Point;
import com.spongeblob.paint.model.Vector;


public class PointUtil {
	protected final static int MARKER_RADIUS = 10;
	private static float STEP = 0.1f;
	
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
		if (p instanceof MarkedPoint){
			if (((MarkedPoint)p).marker.equals(Marker.CL3_POINT)){
				g.setColor(Color.GREEN);
			}
			if (((MarkedPoint)p).marker.equals(Marker.CL4_POINT)){
				g.setColor(Color.MAGENTA);
			}
			if (((MarkedPoint)p).marker.equals(Marker.L_POINT)){
				g.setColor(Color.RED);
			}
		} else{
			g.setColor(Color.RED);
		}
		g.drawOval((int)p.getX() - radius/2, (int)p.getY() - radius/2, radius, radius);
	}
	
	
	public static void paintCircleAroundPoint(Graphics g, Point p){
		paintCircleAroundPoint(g, p, MARKER_RADIUS);
	}
	
	public static int calcInnerProduct(Vector v1, Vector v2){
		return v1.getA1()*v2.getA1() + v1.getA2()*v2.getA2();
	}
	
	/* Formula:
	 * ((1-t)^2 * P1) + (2*(t)*(1-t) * P2) + ((tt) * P3) */
	public static List<Point> calculateCurveLine3Points(float x1, float y1, float x2, float y2, float x3, float y3) {

		List<Point> mPoints = new LinkedList<Point>();
		
		for(float t=0; t <= 1; t += STEP){
			final float u = 1 - t;
			final float tt = t*t;
			final float uu = u*u;
	
			final float ut2 = 2 * u * t;

			final float x = (uu * x1) + (ut2 * x2) + (tt * x3);
			final float y = (uu * y1) + (ut2 * y2) + (tt * y3);
			
			mPoints.add(new Point((int)x, (int)y));
		}
		return mPoints;
	}
	
	/* Formula:
	 * ((1-t)^2 * P1) + (2*(t)*(1-t) * P2) + ((tt) * P3) */
	public static List<Point> calculateCurveLine4Points(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {

		List<Point> mPoints = new LinkedList<Point>();
		
		for(float t=0; t <= 1; t += STEP){

			final float u = 1 - t;
			final float tt = t * t;
			final float uu = u * u;
			final float uuu = uu * u;
			final float ttt = tt * t;
	
			final float ut3 = 3 * uu * t;
			final float utt3 = 3 * u * tt;
	
			final float x = (uuu * x1) + (ut3 * x2) + (utt3 * x3) + (ttt * x4);
			final float y = (uuu * y1) + (ut3 * y2) + (utt3 * y3) + (ttt * y4);
			
			mPoints.add(new Point((int)x, (int)y));
		}
		return mPoints;
	}
}
