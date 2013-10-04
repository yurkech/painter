package com.spongeblob.paint.model.shape;

import java.awt.Color;
import java.awt.Graphics;

import com.spongeblob.paint.model.point.Point;


public class Oval extends SolidAbstractShape<Point> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706959802243028105L;

	public Oval() {
	}

	public Oval(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}

	public Oval(int x, int y, Color c, Boolean solid) {
		getControlPoints().add(new Point(x, y));
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}

	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		if (solidSettings.isSolid()) {
			if (getControlPoints().get(0).x > getControlPoints().get(1).x
					|| getControlPoints().get(0).y > getControlPoints().get(1).y)
				g.fillOval(getControlPoints().get(1).x,
						getControlPoints().get(1).y,
						getControlPoints().get(0).x
								- getControlPoints().get(1).x,
						getControlPoints().get(0).y
								- getControlPoints().get(1).y);
			else
				g.fillOval(getControlPoints().get(0).x,
						getControlPoints().get(0).y,
						getControlPoints().get(1).x
								- getControlPoints().get(0).x,
						getControlPoints().get(1).y
								- getControlPoints().get(0).y);
		} else {
			if (getControlPoints().get(0).x > getControlPoints().get(1).x
					|| getControlPoints().get(0).y > getControlPoints().get(1).y)
				g.drawOval(getControlPoints().get(1).x,
						getControlPoints().get(1).y,
						getControlPoints().get(0).x
								- getControlPoints().get(1).x,
						getControlPoints().get(0).y
								- getControlPoints().get(1).y);
			else
				g.drawOval(getControlPoints().get(0).x,
						getControlPoints().get(0).y,
						getControlPoints().get(1).x
								- getControlPoints().get(0).x,
						getControlPoints().get(1).y
								- getControlPoints().get(0).y);
		}
	}
}
