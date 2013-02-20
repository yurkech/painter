package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SolidAbstractShape<Point> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6085392574960675124L;

	public Rectangle() {
	};

	public Rectangle(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		getColorSettings().setColor(c);
	}

	public Rectangle(int x, int y, Color c, Boolean solid) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		getColorSettings().setColor(c);
	}

	public void draw(Graphics g) {
		g.setColor(getColorSettings().getColor());
		if (getSolidSettings().isSolid()) {
			if (getControlPoints().get(0).x > getControlPoints().get(1).x
					|| getControlPoints().get(0).y > getControlPoints().get(1).y)
				g.fillRect(getControlPoints().get(1).x,
						getControlPoints().get(1).y,
						getControlPoints().get(0).x
								- getControlPoints().get(1).x,
						getControlPoints().get(0).y
								- getControlPoints().get(1).y);
			else
				g.fillRect(getControlPoints().get(0).x,
						getControlPoints().get(0).y,
						getControlPoints().get(1).x
								- getControlPoints().get(0).x,
						getControlPoints().get(1).y
								- getControlPoints().get(0).y);
		} else {
			if (getControlPoints().get(0).x > getControlPoints().get(1).x
					|| getControlPoints().get(0).y > getControlPoints().get(1).y)
				g.drawRect(getControlPoints().get(1).x,
						getControlPoints().get(1).y,
						getControlPoints().get(0).x
								- getControlPoints().get(1).x,
						getControlPoints().get(0).y
								- getControlPoints().get(1).y);
			else
				g.drawRect(getControlPoints().get(0).x,
						getControlPoints().get(0).y,
						getControlPoints().get(1).x
								- getControlPoints().get(0).x,
						getControlPoints().get(1).y
								- getControlPoints().get(0).y);
		}

	}
}
