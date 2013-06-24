package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;


import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeSolidSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class SolidAbstractShape<T extends Point> extends AbstractShape<T> {
	public static String SOLID_SETTINGS = "SOLID_SETTINGS";
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982632616667148896L;

	@JsonProperty("solid")
	protected ShapeSolidSettings solidSettings = new ShapeSolidSettings("Solid");
	
	@Override
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		if (solidSettings.isSolid()) {
			if (solidSettings.isFilled()) {
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
	
	@Override
	@JsonIgnore
	public Map<String, Settings> getShapeSettings() {
		Map<String, Settings> shapeSettings = super.getShapeSettings();
		shapeSettings.put(SOLID_SETTINGS, solidSettings);
		return shapeSettings;
	}
}
