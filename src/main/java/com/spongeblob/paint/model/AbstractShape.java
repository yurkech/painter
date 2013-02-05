package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonIgnore;
	

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.settings.ShapePhysicsSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	protected List<Point> points;
	protected Map<String, Settings> settings = new HashMap<String, Settings>();
	
	public AbstractShape(){
		addSettings(new ShapePhysicsSettings());
		addSettings(new ShapeColorSettings());
	}
	
	@JsonIgnore
	public List<Settings> getAllSettings() {
		LinkedList<Settings> list = new LinkedList<Settings>();
		for (Entry<String, Settings> item : settings.entrySet()) {
			list.add(item.getValue());
		}
		return list;
	}
	
	@JsonIgnore
	public Settings getSettingsByClass(Class<?> clazz) {
		return settings.get(clazz.getName());
	}
	
	public void addSettings(Settings settings) {
		this.settings.put(settings.getClass().getName(), settings);
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
		g.setColor(((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).getPathPointsColor());
		for (Point point : points) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	public void draw(Graphics g) {
		g.setColor(((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).getColor());
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
			for (int i = 0; i < points.size() - 1; i= i + 1) {
				if (PointUtil.isPointIntersectLineInRadius(p, points.get(i), points.get(i + 1), radius))
					return true;
			}
		}
		return false;
	}
	
	public void move(int deltaX, int deltaY){
		for (Point point : points) {
			point.moveWithDelta(deltaX, deltaY);
		}
	}
	
	public void setFocus(Boolean flag) {
		// TODO Auto-generated method stub
		
	}
}
