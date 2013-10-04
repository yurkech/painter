package com.spongeblob.paint.model.shape;

import java.awt.Color;

import com.spongeblob.paint.model.point.Point;


public class Polygon extends SolidAbstractShape<Point> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2411393086421989720L;

	public Polygon() {
	}

	public Polygon(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}

	public Polygon(int x, int y, Color c, Boolean solid) {
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}
}