package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;

import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.utils.PointUtil;

public class Ruler extends Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1011553512173468765L;

	public Ruler() {
	}

	public Ruler(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		((ShapeColorSettings)getShapeSettings().get(COLOR_SETTINGS)).setColor(c);
	}

	public void drawControlPoints(Graphics g) {
		g.setColor(((ShapeColorSettings)getShapeSettings().get(COLOR_SETTINGS)).getPathPointsColor());
		for (Point point : getControlPoints()) {
			PointUtil.paintCircleAroundPoint(g, point);
		}

		Vector v = new Vector(getControlPoints().get(0), getControlPoints()
				.get(1));

		String length = String.valueOf(Math.round(v.getLength()));
		g.drawChars(length.toCharArray(), 0, length.length(),
				getControlPoints().get(1).x + 5, getControlPoints().get(1).y + 5);
	}
}
