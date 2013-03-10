package com.spongeblob.paint.model;

import java.awt.Color;

import com.spongeblob.paint.settings.ShapeColorSettings;

public class HandLine extends AbstractShape<Point> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765897363067718369L;

	public HandLine() {
	}

	public HandLine(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		((ShapeColorSettings)getShapeSettings().get(COLOR_SETTINGS)).setColor(c);
	}
}
