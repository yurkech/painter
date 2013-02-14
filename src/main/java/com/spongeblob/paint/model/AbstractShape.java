package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
	

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.settings.ShapePhysicsSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static int ID = 0;
	
	private static final long serialVersionUID = 7586022119974312143L;
	protected PhysicsObjectType type;
	protected String model;
	protected int id;
		
	protected List<Point> points;
	@JsonProperty("phys")	
	protected ShapePhysicsSettings physicsSettings;
	@JsonProperty("color")
	protected ShapeColorSettings colorSettings;
	
	public AbstractShape(){
		physicsSettings = new ShapePhysicsSettings();
		colorSettings = new ShapeColorSettings();
		setType(PhysicsObjectType.BORDERTRACK);
		id = ID++;
	}
	
	@JsonIgnore
	public List<Settings> getAllSettings() {
		LinkedList<Settings> list = new LinkedList<Settings>();
		list.add(physicsSettings);
		list.add(colorSettings);
		return list;
	}
	
	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	
	public void addPoint(int x, int y){
		points.add(new Point(x, y));
	}
	
	public void drawPathPoints(Graphics g) {
		g.setColor(colorSettings.getPathPointsColor());
		for (Point point : points) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		g.drawPolyline(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
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
			for (int i = 0; i < points.size() - 1; i++) {
				if (PointUtil.isPointIntersectLineInRadius(p, points.get(i), points.get(i + 1), radius))
					return true;
			}
		}
		return false;
	}
	
	public int intersectionPointIndex(Point p, int radius){
		if (points.size() > 1){
			for (int i = 0; i < points.size() - 1; i++) {
				if (PointUtil.isPointIntersectLineInRadius(p, points.get(i), points.get(i + 1), radius))
					return i;
			}
		}
		return -1;
	}
	
	public void move(int deltaX, int deltaY){
		for (Point point : points) {
			point.moveWithDelta(deltaX, deltaY);
		}
	}
	
	public void setFocus(Boolean flag) {
		// TODO Auto-generated method stub
		
	}

	public PhysicsObjectType getType() {
		return type;
	}

	public void setType(PhysicsObjectType type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
