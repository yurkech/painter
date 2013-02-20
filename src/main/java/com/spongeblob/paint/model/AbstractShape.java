package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
	

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape<T extends Point> implements Shape<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	
	@JsonProperty(value = "points")
	protected List<T> controlPoints;
	
	@JsonProperty("color")
	private ShapeColorSettings colorSettings;
	
	
	public ShapeColorSettings getColorSettings() {
		return this.colorSettings;
	}

	public void setColorSettings(ShapeColorSettings colorSettings) {
		this.colorSettings = colorSettings;
	}

	public AbstractShape(){
		this.controlPoints = new LinkedList<T>();
		this.colorSettings = new ShapeColorSettings();
	}
	
	@JsonIgnore
	public List<Settings> getSettings() {
		LinkedList<Settings> list = new LinkedList<Settings>();
		list.add(getColorSettings());
		return list;
	}
	
	public void drawControlPoints(Graphics g) {
		g.setColor(getColorSettings().getPathPointsColor());
		for (Point point : getControlPoints()) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	public void draw(Graphics g) {
		g.setColor(getColorSettings().getColor());
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

	public void setControlPoints(List<T> controlPoints) {
		this.controlPoints = controlPoints;
	}
	
}
