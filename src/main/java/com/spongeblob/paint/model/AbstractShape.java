package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
	

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeDrawingSettings;
import com.spongeblob.paint.settings.ShapePhysicsSettings;
import com.spongeblob.paint.utils.PointUtil;

public abstract class AbstractShape implements Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7586022119974312143L;
	protected List<Point> points;
	protected HashMap<Class<?>, Settings> settings;
	
	public AbstractShape(){
		settings = new HashMap<Class<?>, Settings>();
		settings.put(ShapePhysicsSettings.class, new ShapePhysicsSettings());
		settings.put(ShapeDrawingSettings.class, new ShapeDrawingSettings());
	}
	
	public List<Settings> getAllSettings() {
		LinkedList<Settings> list = new LinkedList<Settings>();
		for (Entry<Class<?>, Settings> item : settings.entrySet()) {
			list.add(item.getValue());
		}
		return list;
	}
	
	public Settings getSettingsByClass(Class<?> clazz) {
		return settings.get(clazz);
	}
	
	public void addSettings(Settings settings) {
		this.settings.put(settings.getClass(), settings);
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
		g.setColor(((ShapeDrawingSettings)getSettingsByClass(ShapeDrawingSettings.class)).getPathPointsColor());
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
	
	public void move(int deltaX, int deltaY){
		for (Point point : points) {
			point.moveWithDelta(deltaX, deltaY);
		}
	}
	
	public void setFocus(Boolean flag) {
		// TODO Auto-generated method stub
		
	}
}
