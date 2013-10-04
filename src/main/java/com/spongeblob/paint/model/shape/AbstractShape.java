package com.spongeblob.paint.model.shape;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.model.point.Point;
import com.spongeblob.paint.settings.HideControlsSettings;
import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape<T extends Point> implements Shape<T> {
	public static String COLOR_SETTINGS = "COLOR_SETTINGS";
	public static String HIDE_SETTINGS = "HIDE_SETTINGS";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;

	@JsonProperty(value = "points")
	protected List<T> controlPoints = new LinkedList<T>();
	protected AffineTransform scaleMatrix;

	@JsonProperty("color")
	protected ShapeColorSettings colorSettings = new ShapeColorSettings("Color");
	@JsonProperty("hide")
	protected HideControlsSettings hideSettings = new HideControlsSettings(
			"Hide Control Points");

	public void drawControlPoints(Graphics g) {
		if (!hideSettings.isHide()) {
			g.setColor(colorSettings.getPathPointsColor());
			for (Point point : getControlPoints()) {
				PointUtil.paintCircleAroundPoint(g, point);
			}
		}
	}

	public void scale(double dX, double dY) {
		for (Point point : getControlPoints()) {
			point.scale(dX, dY);
		}
	}

	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		g.drawPolyline(PointUtil.getXs(getControlPoints()),
				PointUtil.getYs(getControlPoints()), getControlPoints().size());
	}

	@JsonIgnore
	public Point getClosestControlPointInRadius(Point p, int radius) {
		for (Point point : getControlPoints()) {
			if (PointUtil.isPointInRadius(point, p, radius))
				return point;
		}
		return null;
	}

	@JsonIgnore
	public int getClosestControlLineInRadius(Point p, int radius) {
		if (getControlPoints().size() > 1) {
			for (int i = 0; i < getControlPoints().size() - 1; i++) {
				if (PointUtil.isPointIntersectLineInRadius(p,
						getControlPoints().get(i), getControlPoints()
								.get(i + 1), radius))
					return i;
			}
		}
		return -1;
	}

	public void move(int deltaX, int deltaY) {
		for (Point point : getControlPoints()) {
			point.moveWithDelta(deltaX, deltaY);
		}
	}

	public List<T> getControlPoints() {
		return controlPoints;
	}

	@JsonIgnore
	public Map<String, Settings> getShapeSettings() {
		Map<String, Settings> shapeSettings = new HashMap<String, Settings>();
		shapeSettings.put(COLOR_SETTINGS, colorSettings);
		shapeSettings.put(HIDE_SETTINGS, hideSettings);
		return shapeSettings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((colorSettings == null) ? 0 : colorSettings.hashCode());
		result = prime * result
				+ ((controlPoints == null) ? 0 : controlPoints.hashCode());
		result = prime * result
				+ ((hideSettings == null) ? 0 : hideSettings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractShape<?> other = (AbstractShape<?>) obj;
		if (colorSettings == null) {
			if (other.colorSettings != null)
				return false;
		} else if (!colorSettings.equals(other.colorSettings))
			return false;
		if (controlPoints == null) {
			if (other.controlPoints != null)
				return false;
		} else if (!controlPoints.equals(other.controlPoints))
			return false;
		if (hideSettings == null) {
			if (other.hideSettings != null)
				return false;
		} else if (!hideSettings.equals(other.hideSettings))
			return false;
		return true;
	}

}
