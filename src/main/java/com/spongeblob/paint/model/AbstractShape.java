package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
	

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	
		
	private List<? extends Point> controlPoints ;
	
	@JsonProperty("color")
	protected ShapeColorSettings colorSettings;
	
	public AbstractShape(){
		controlPoints = new LinkedList<Point>();
		colorSettings = new ShapeColorSettings();
	}
	
	@JsonIgnore
	public List<Settings> getSettings() {
		LinkedList<Settings> list = new LinkedList<Settings>();
		list.add(colorSettings);
		return list;
	}
	
	

	public void setPoints(List<? extends Point> points) {
		this.controlPoints = points;
	}

	
	public void addPoint(int x, int y){
		getControlPoints().add(new Point(x, y));
	}
	
	public void drawControlPoints(Graphics g) {
		g.setColor(colorSettings.getPathPointsColor());
		for (Point point : getControlPoints()) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		g.drawPolyline(PointUtil.getXs(getControlPoints()), PointUtil.getYs(getControlPoints()), getControlPoints().size());
	}
	
	public Point getClosestControlPointInRadius(Point p, int radius) {
		for (Point point : getControlPoints()) {
			if (PointUtil.isPointInRadius(point, p, radius))
				return point;
		}
		return null;	
	}
	
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

	@SuppressWarnings("unchecked")
	public <T extends Point> List<T> getControlPoints() {
		return (List<T>) controlPoints;
	}
	
	
}
