package com.spongeblob.paint.model;

import java.awt.Color;

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
		getColorSettings().setColor(c);
	}
}
