package com.spongeblob.paint.model.shape;

import java.awt.Color;

import com.spongeblob.paint.model.point.Point;


public class Line extends AbstractShape<Point> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109490022085804859L;

	public Line() {
	}

	public Line(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}
}
