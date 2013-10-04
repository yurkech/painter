package com.spongeblob.paint.model.test;

import java.awt.Color;
import java.awt.Graphics;

import com.spongeblob.paint.model.point.Point;
import com.spongeblob.paint.model.shape.Line;
import com.spongeblob.paint.settings.ShapeColorSettings;

public class TestKasLine extends Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8837080939140009516L;
	private static final int RADIOUS = 50;

	public TestKasLine(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		((ShapeColorSettings)getShapeSettings().get(COLOR_SETTINGS)).setColor(c);
	}

	public void draw(Graphics g) {
		g.setColor(((ShapeColorSettings)getShapeSettings().get(COLOR_SETTINGS)).getColor());
		g.drawLine(getControlPoints().get(0).x, getControlPoints().get(0).y,
				getControlPoints().get(1).x, getControlPoints().get(1).y);
		g.drawOval(getControlPoints().get(1).x - RADIOUS, getControlPoints()
				.get(1).y - RADIOUS, 2 * RADIOUS, 2 * RADIOUS);

		if (!getControlPoints().get(0).equals(getControlPoints().get(1))) {
			// if (points.get(0).y != points.get(1).y){
			Point[] point = GetKas2(getControlPoints().get(0),
					getControlPoints().get(1), RADIOUS);
			g.drawLine(getControlPoints().get(0).x,
					getControlPoints().get(0).y, point[0].x, point[0].y);
			System.out.println("x0: " + point[0].x + " y0:" + point[0].y);
			g.drawLine(getControlPoints().get(0).x,
					getControlPoints().get(0).y, point[1].x, point[1].y);
			System.out.println("x1: " + point[1].x + " y1:" + point[1].y);
			/*
			 * } else{ g.drawLine(points.get(0).x, points.get(0).y,
			 * calculatePoint(points.get(0), points.get(1), RADIOUS),
			 * points.get(1).y); }
			 */
		}
	}

	private Point[] GetKas2(Point A, Point O, int R) {

		double L = Math.sqrt(Math.pow(O.x - A.x, 2) + Math.pow(O.y - A.y, 2));
		if (L <= R + 1)
			return null;

		double L1 = Math.sqrt(Math.pow(L, 2) - Math.pow(R, 2));
		double a1 = Math.asin((O.x - A.x) / L);
		double b1 = Math.asin(R / L);

		double k;
		if (O.y < A.y)
			k = -1;
		else
			k = 1;

		Point P1 = new Point();
		P1.x = (int) (A.x + L1 * Math.sin(a1 - b1));
		P1.y = (int) (A.y + k * L1 * Math.cos(a1 - b1));
		Point P2 = new Point();
		P2.x = (int) (A.x + L1 * Math.sin(a1 + b1));
		P2.y = (int) (A.y + k * L1 * Math.cos(a1 + b1));
		Point[] point = new Point[2];
		point[0] = P1;
		point[1] = P2;
		return point;
	}
}
