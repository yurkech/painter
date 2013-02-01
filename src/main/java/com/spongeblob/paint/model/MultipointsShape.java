package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.utils.PointUtil;

public abstract class MultipointsShape extends AbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6733928759363462311L;
	protected LinkedList<Point> points;
	
	public void addPoint(int x, int y){
		points.add(new Point(x, y));
	}
	
	public void drawPathPoints(Graphics g) {
		g.setColor(getColor());
		for (Point point : points) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	@JsonProperty(value="points")
	public List<Point> getPathPoints() {
		return points;
	}

	public Point contains(Point p, int radius) {
		for (Point point : points) {
			if (PointUtil.isPointInRadius(point, p, radius))
				return point;
		}
		return null;	
	}

}
