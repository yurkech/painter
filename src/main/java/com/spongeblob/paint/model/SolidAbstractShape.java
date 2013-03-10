package com.spongeblob.paint.model;

import java.awt.Graphics;


import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.settings.ShapeSolidSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class SolidAbstractShape<T extends Point> extends AbstractShape<T> {
	public static String SOLID_SETTINGS = "SOLID_SETTINGS";
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982632616667148896L;

	public SolidAbstractShape() {
		this.shapeSettings.put(SOLID_SETTINGS, new ShapeSolidSettings());
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(((ShapeColorSettings)getShapeSettings().get(COLOR_SETTINGS)).getColor());
		if (((ShapeSolidSettings)getShapeSettings().get(SOLID_SETTINGS)).isSolid()) {
			if (((ShapeSolidSettings)getShapeSettings().get(SOLID_SETTINGS)).isFilled()) {
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
	}
}
