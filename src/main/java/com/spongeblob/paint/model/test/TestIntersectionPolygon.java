package com.spongeblob.paint.model.test;

import java.awt.Color;
import java.awt.Graphics;

import com.spongeblob.paint.model.Point;
import com.spongeblob.paint.model.Polygon;
import com.spongeblob.paint.utils.PointUtil;

public class TestIntersectionPolygon extends Polygon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4339603663648536331L;

	public TestIntersectionPolygon() {
	}

	public TestIntersectionPolygon(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		getColorSettings().setColor(c);
	}

	public TestIntersectionPolygon(int x, int y, Color c, Boolean solid) {
		getControlPoints().add(new Point(x, y));
		getColorSettings().setColor(c);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColorSettings().getColor());
		if (getSolidSettings().isSolid()) {
			if (getSolidSettings().isFilled()) {
				g.fillPolygon(PointUtil.getXs(getControlPoints()), PointUtil
						.getYs(getControlPoints()), getControlPoints().size());
			} else {
				g.drawPolygon(PointUtil.getXs(getControlPoints()), PointUtil
						.getYs(getControlPoints()), getControlPoints().size());
			}
		} else {
			g.drawPolyline(PointUtil.getXs(getControlPoints()), PointUtil
					.getYs(getControlPoints()), getControlPoints().size());
		}
		
		for (int i = 0; i < getControlPoints().size() - 1; i++) {
			for (int k = i; k < getControlPoints().size() - 1; k++) {
			Point p = PointUtil.getIntersection(getControlPoints().get(i), getControlPoints().get(i + 1),
					getControlPoints().get(k), getControlPoints().get(k + 1));
			if (p != null){
				System.out.print(p);
				PointUtil.paintCircleAroundPoint(g, p);
			}
			}
		}
		
		g.drawLine(0, 0, 100, 100);
		g.drawLine(20, 100, 100, 20);
		Point p = PointUtil.getIntersection(new Point(0,0), new Point(100,100),
				new Point(20,100), new Point(100, 20));
		if (p != null)
			PointUtil.paintCircleAroundPoint(g, p);
	}
	
	public void addPoint(int x, int y) {
		getControlPoints().add(new Point(x, y));
	}
}
