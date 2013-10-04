package com.spongeblob.paint.model.shape;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.spongeblob.paint.model.point.Point;
import com.spongeblob.paint.settings.Settings;

/**
 * @author yurkech
 * 
 */
public interface Shape<T extends Point> extends Serializable, Cloneable {
	/**
	 * @param g
	 */
	public void draw(Graphics g);

	/**
	 * @param g
	 */
	public void drawControlPoints(Graphics g);

	/**
	 * @return
	 */
	public List<T> getControlPoints();

	/**
	 * @param p
	 * @param radius
	 * @return
	 */
	public Point getClosestControlPointInRadius(Point p, int radius);

	/**
	 * @param p
	 * @param radius
	 * @return
	 */
	public int getClosestControlLineInRadius(Point p, int radius);

	/**
	 * @param deltaX
	 * @param deltaY
	 */
	public void move(int deltaX, int deltaY);

	/**
	 * @param dX
	 * @param dY
	 */
	public void scale(double dX, double dY);

	/**
	 * @return
	 */
	public Map<String, Settings> getShapeSettings();
}
