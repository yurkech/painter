package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class CleverLine extends Line{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8837080939140009516L;
	private static final int RADIOUS = 50;
	
	public CleverLine(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
	
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
     	g.drawLine(controlPoints.get(0).x, controlPoints.get(0).y, controlPoints.get(1).x, controlPoints.get(1).y);
     	g.drawOval(controlPoints.get(1).x - RADIOUS, controlPoints.get(1).y - RADIOUS, 2 *RADIOUS, 2 * RADIOUS);
     	
     	if (!controlPoints.get(0).equals(controlPoints.get(1))){
     	//	if (points.get(0).y != points.get(1).y){
     			Point [] point = GetKas2(controlPoints.get(0), controlPoints.get(1), RADIOUS);
	     			g.drawLine(controlPoints.get(0).x, controlPoints.get(0).y, point[0].x, point[0].y);
     			System.out.println("x0: " + point[0].x + " y0:" + point[0].y);
     			g.drawLine(controlPoints.get(0).x, controlPoints.get(0).y, point[1].x, point[1].y);
     			System.out.println("x1: " + point[1].x + " y1:" + point[1].y);
     	/*	} else{
     			g.drawLine(points.get(0).x, points.get(0).y, 
     					calculatePoint(points.get(0), points.get(1), RADIOUS), points.get(1).y);
     		}*/
     	}
	}
	
	private Point[] GetKas2(Point A, Point O, int R){

		   double L = Math.sqrt(Math.pow(O.x - A.x, 2) + Math.pow(O.y - A.y, 2));
		   if (L <= R + 1)
			   return null;

		   double L1 = Math.sqrt(Math.pow(L, 2)-Math.pow(R, 2));
		   double a1 = Math.asin((O.x - A.x) / L);
		   double b1 = Math.asin(R / L);

		   double k;
		   if (O.y < A.y)
			   k = -1; 
		   else 
			   k = 1;

		   Point P1 = new Point();
		   P1.x = (int)(A.x +     L1 * Math.sin(a1 - b1));
		   P1.y = (int)(A.y + k * L1 * Math.cos(a1 - b1));
		   Point P2 = new Point();
		   P2.x = (int)(A.x +     L1 * Math.sin(a1 + b1));
		   P2.y = (int)(A.y + k * L1 * Math.cos(a1 + b1));
		   Point [] point = new Point[2];
		   point[0] = P1;
		   point[1] = P2;
		   return point;
	}
 }
