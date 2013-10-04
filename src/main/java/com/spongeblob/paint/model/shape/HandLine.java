package com.spongeblob.paint.model.shape;

import java.awt.Color;

import com.spongeblob.paint.model.point.Point;


public class HandLine extends AbstractShape<Point> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;

	public HandLine() {
	}

	public HandLine(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}
}
