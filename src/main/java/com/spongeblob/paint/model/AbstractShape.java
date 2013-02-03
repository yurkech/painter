package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
	
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapePhysicsSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	protected Color color;
	protected List<Point> points;
	protected List<Settings> settings;
	
	public AbstractShape(){
		settings = new LinkedList<Settings>();
		settings.add(new ShapePhysicsSettings());
	}
	
	public List<Settings> getSettings() {
		return settings;
	}

	public void setSettings(List<Settings> settings) {
		this.settings = settings;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	@JsonIgnore
	public Color getColor() {
		return color;
	}
	
	@JsonProperty(value="color")
	public String getColorAsString() {
		return color.toString();
	}
	
	@JsonProperty(value="color")
	public void setColorFromString(String color) {
		this.color = Color.getColor(color);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	public void addPoint(int x, int y){
		points.add(new Point(x, y));
	}
	
	public void drawPathPoints(Graphics g) {
		g.setColor(getColor());
		for (Point point : points) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	public Point contains(Point p, int radius) {
		for (Point point : points) {
			if (PointUtil.isPointInRadius(point, p, radius))
				return point;
		}
		return null;	
	}
	
	public Boolean intersects(Point p, int radius){
		if (points.size() > 1){
			for (int i = 0; i < points.size() - 1; i= i + 1) {
				if (PointUtil.isPointIntersectLineInRadius(p, points.get(i), points.get(i + 1), radius))
					return true;
			}
		}
		return false;
	}
	
	public void setFocus(Boolean flag){
		if (flag){
			color = Color.RED;
		} else{
			color = Color.BLACK;
		}
	}
	
	public void move(int deltaX, int deltaY){
		for (Point point : points) {
			point.moveWithDelta(deltaX, deltaY);
		}
	}
}
