package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeSolidSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class SolidAbstractShape extends AbstractShape {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982632616667148896L;
	@JsonProperty("solid")
	private ShapeSolidSettings solidSettings;

	
	public ShapeSolidSettings getSolidSettings() {
		return solidSettings;
	}

	public void setSolidSettings(ShapeSolidSettings solidSettings) {
		this.solidSettings = solidSettings;
	}

	public SolidAbstractShape() {
		solidSettings = new ShapeSolidSettings();
	}

	@JsonIgnore
	@Override
	public List<Settings> getSettings() {
		List<Settings> list = super.getSettings();
		list.add(getSolidSettings());
		return list;
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
	}
}
