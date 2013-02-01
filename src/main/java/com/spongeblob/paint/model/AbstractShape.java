package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
	
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	protected Color color;
	protected LinkedList<Point> points;
	
	
	public LinkedList<Point> getPoints() {
		return points;
	}

	public void setPoints(LinkedList<Point> points) {
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
}
