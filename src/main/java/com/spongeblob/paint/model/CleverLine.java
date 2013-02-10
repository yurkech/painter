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
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		points.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "LINE";
	}
	
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
     	g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
     	g.drawOval(points.get(1).x - RADIOUS, points.get(1).y - RADIOUS, 2 *RADIOUS, 2 * RADIOUS);
     	
     	if (!points.get(0).equals(points.get(1))){
     	//	if (points.get(0).y != points.get(1).y){
     			Point [] point = GetKas2(points.get(0), points.get(1), RADIOUS);
	     			g.drawLine(points.get(0).x, points.get(0).y, point[0].x, point[0].y);
     			System.out.println("x0: " + point[0].x + " y0:" + point[0].y);
     			g.drawLine(points.get(0).x, points.get(0).y, point[1].x, point[1].y);
     			System.out.println("x1: " + point[1].x + " y1:" + point[1].y);
     	/*	} else{
     			g.drawLine(points.get(0).x, points.get(0).y, 
     					calculatePoint(points.get(0), points.get(1), RADIOUS), points.get(1).y);
     		}*/
     	}
	}
	
	private double calculateA(final Point p0, final Point p1){
		double A = (p0.x - p1.x) / (p0.y - p1.y);
		System.out.println("A:" + A);
		return A; 
	}
	
	
	private double calculateB(final Point p0, final Point p1, final int r){
		double B = (Math.pow(r, 2) - Math.pow(p1.x, 2) - Math.pow(p1.y, 2) + p1.y*p0.y + p1.x*p0.x) / (p0.y - p1.y);
		System.out.println("B:" + B);
		return B;
	}

	private double calculateD(final Point p0, final Point p1, final int r){
		double A = calculateA(p0, p1);
		double B = calculateB(p0, p1, r);
		double D = Math.pow((A*p0.y + A*p1.y - 2*A*B - p0.x - p1.x ), 2) -
				4 * (A*A + 1) * (B*B - B*p0.y - B*p1.y + p1.y*p0.y + p1.x*p0.x);
		System.out.println("D:" + D);
		return D;
	}
	
	private Point[] calculatePoints(Point p0, Point p1, int r){
		double [] x = new double[2];
		double [] y = new double[2];
		Point [] point = new Point[2];
		double D = calculateD(p0, p1, r);
		double A = calculateA(p0, p1);
		double B = calculateB(p0, p1, r);
		x[0] = (-(A*p0.y + A*p1.y - 2*A*B - p0.x - p1.x ) + Math.sqrt(D)) 
				/ (2 * (A*A + 1)); 
		x[1] = (-(A*p0.y + A*p1.y - 2*A*B - p0.x - p1.x ) - Math.sqrt(D)) 
				/ (2 * (A*A + 1));
		y[0] = -A*x[0] + B;
		y[1] = -A*x[1] + B;
		System.out.println("x0y0:" + x[0] +  "," + y[0]);
		System.out.println("x1y1:" + x[1] +  "," + y[1]);
		point[0] = new Point((int)x[0], (int)y[0]);
		point[1] = new Point((int)x[1], (int)y[1]);
		return point;
	}
	
	private int calculatePoint(Point p, Point c, int r){
		return (r*r - c.x*c.x - c.y*c.y + c.y*p.y + c.x*p.x) / (p.x - c.x);
	}
	
	
	private Point[] GetKas(Point O1, Point O2, double R1, double R2)
	{
	 double D=Math.sqrt((O1.x-O2.x)*(O1.x-O2.x)+(O1.y-O2.y)*(O1.y-O2.y));
	 System.out.println("D:" + D);
	 double a=(R1*R1-R2*R2+D*D)/(2*D);
	 System.out.println("a:" + a);
	 double h=Math.sqrt(R1*R1-a*a);
	 System.out.println("h:" + h);

	 double X=O1.x+a*(O2.x-O1.x)/D;   
	 double Y=O1.y+a*(O2.y-O1.y)/D; 
	 Point [] point = new Point[2];
	 point[0] = new Point((int)(X+h*(O2.y-O1.y)/D), (int)(Y-h*(O2.x-O1.x)/D));
	 point[1] = new Point((int)(X-h*(O2.y-O1.y)/D), (int)(Y+h*(O2.x-O1.x)/D));
	 return point;
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
