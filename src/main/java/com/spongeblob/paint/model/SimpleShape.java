package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.utils.PointUtil;

public abstract class SimpleShape extends AbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8362648333737765076L;
	protected Point p1;
	protected Point p2;
	
	public Point contains(Point p, int radius) {
		if (PointUtil.isPointInRadius(p1, p, radius))
  			return p1;
  		if (PointUtil.isPointInRadius(p2, p, radius))
  			return p2;
		return null;
	}

	@JsonProperty(value="points")
	public List<Point> getPathPoints() {
		List<Point> l = new ArrayList<Point>(2);
		l.add(p1);
		l.add(p2);
		return l;
	}

	public void drawPathPoints(Graphics g) {
		g.setColor(getColor());
    	PointUtil.paintCircleAroundPoint(g, p1);
    	PointUtil.paintCircleAroundPoint(g, p2);
	}

}
