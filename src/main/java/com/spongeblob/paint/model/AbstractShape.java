package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
	

import com.spongeblob.paint.settings.HideControlsSettings;
import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape<T extends Point> implements Shape<T>{
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
	protected HideControlsSettings hideSettings = new HideControlsSettings("Hide Control Points");

	public void drawControlPoints(Graphics g) {
		if (!hideSettings.isHide()){
			g.setColor(colorSettings.getPathPointsColor());
			for (Point point : getControlPoints()) {
				PointUtil.paintCircleAroundPoint(g, point);
			}
		}	
	}

	public void scale(float dX, float dY){
		for (Point point : getControlPoints()) {
			point.scale(dX, dY);
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		g.drawPolyline(PointUtil.getXs(getControlPoints()), PointUtil.getYs(getControlPoints()), getControlPoints().size());
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
	public int getClosestControlLineInRadius(Point p, int radius){
		if (getControlPoints().size() > 1){
			for (int i = 0; i < getControlPoints().size() - 1; i++) {
				if (PointUtil.isPointIntersectLineInRadius(p, getControlPoints().get(i), getControlPoints().get(i + 1), radius))
					return i;
			}
		}
		return -1;
	}
	
	public void move(int deltaX, int deltaY){
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
}
