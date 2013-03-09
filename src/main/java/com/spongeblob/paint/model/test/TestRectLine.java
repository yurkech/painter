package com.spongeblob.paint.model.test;

import java.awt.Color;
import java.awt.Graphics;

import com.spongeblob.paint.model.Line;
import com.spongeblob.paint.model.Point;

public class TestRectLine extends Line{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8837080939140009516L;
	private static final int LENGTH = 50;

	public TestRectLine(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		getColorSettings().setColor(c);
	}
	
	public void draw(Graphics g) {
		g.setColor(getColorSettings().getColor());
		g.drawLine(getControlPoints().get(0).x, getControlPoints().get(0).y,
				getControlPoints().get(1).x, getControlPoints().get(1).y);
		

		if (!getControlPoints().get(0).equals(getControlPoints().get(1))) {
			Point[] point = getRect(getControlPoints().get(0),
					getControlPoints().get(1), LENGTH);
			g.drawLine(getControlPoints().get(0).x,
					getControlPoints().get(0).y, point[0].x, point[0].y);
			System.out.println("x0: " + point[0].x + " y0:" + point[0].y);
			g.drawLine(getControlPoints().get(1).x,
					getControlPoints().get(1).y, point[1].x, point[1].y);
			System.out.println("x0: " + point[0].x + " y0:" + point[0].y);
		}
	}
	private Point[] getRect(Point A, Point B, int length) {
		
		Point[] point = new Point[2];
		point[0] = getOrtPoint(A, B, length, true);
		point[1] = getOrtPoint(B, A, length, false);
		return point;
	}
	
	private Point getOrtPoint(Point A, Point B, int length, boolean isUp){
		int k;
		if (isUp)
			k = 1;
		else
			k = -1;
		
		double L = Math.sqrt(Math.pow(B.x - A.x, 2) + Math.pow(B.y - A.y, 2));
		Point P1 = new Point();
		double a;
		if ((B.x >= A.x) && (B.y >= A.y)){ 
			a = Math.asin((B.x - A.x) / L);
			P1.x = (int) (A.x - k *length * Math.sin(Math.PI / 2 - a));
			P1.y = (int) (A.y + k * length * Math.cos(Math.PI / 2 - a));
		} else if ((B.x >= A.x) && (B.y < A.y)){
			a = Math.asin((B.x - A.x) / L);
			P1.x = (int) (A.x + k * length * Math.sin(Math.PI / 2 - a));
			P1.y = (int) (A.y + k * length * Math.cos(Math.PI / 2 - a));
		} else if ((B.x < A.x) && (B.y >= A.y)){
			a = Math.asin((A.x - B.x) / L);
			P1.x = (int) (A.x - k * length * Math.sin(Math.PI / 2 - a));
			P1.y = (int) (A.y - k * length * Math.cos(Math.PI / 2 - a));
		} else if ((B.x < A.x) && (B.y < A.y)){
			a = Math.asin((A.x - B.x) / L);
			P1.x = (int) (A.x + k * length * Math.sin(Math.PI / 2 - a));
			P1.y = (int) (A.y - k * length * Math.cos(Math.PI / 2 - a));
		}
		return P1;
	}
}
