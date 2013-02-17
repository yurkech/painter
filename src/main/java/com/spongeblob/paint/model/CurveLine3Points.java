package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.utils.PointUtil;

public class CurveLine3Points extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3456072526729463848L;
	
	public CurveLine3Points(){}
	
	public CurveLine3Points(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
	
	public void drawControlPoints(Graphics g) {
		g.setColor(colorSettings.getPathPointsColor());
		g.drawPolyline(PointUtil.getXs(controlPoints), PointUtil.getYs(controlPoints), controlPoints.size());
		for (Point point : controlPoints) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		List<Point> curvePoints = getCurvePoints();
		//List<Point> borderPoints = getBorderPoints(curvePoints, 30);
		
		if (solidSettings.isSolid()){
			if(solidSettings.isFilled())
	  	 	{
				g.fillPolygon(PointUtil.getXs(curvePoints), PointUtil.getYs(curvePoints), curvePoints.size());
				//g.drawPolygon(PointUtil.getXs(borderPoints), PointUtil.getYs(borderPoints), borderPoints.size());
	  	 	}
	     	else
	     	{
	     		g.drawPolygon(PointUtil.getXs(curvePoints), PointUtil.getYs(curvePoints), curvePoints.size());
	     		//g.drawPolygon(PointUtil.getXs(borderPoints), PointUtil.getYs(borderPoints), borderPoints.size());
	     	}
		} else{
			g.drawPolyline(PointUtil.getXs(curvePoints), PointUtil.getYs(curvePoints), curvePoints.size());
			//g.drawPolyline(PointUtil.getXs(borderPoints), PointUtil.getYs(borderPoints), borderPoints.size());
		}
	}

	@JsonIgnore
	public List<Point> getCurvePoints() {
		List<Point> pathPoints = new LinkedList<Point>();
		if (controlPoints.size() > 2){
			for (int i = 0; i < controlPoints.size() - 2; i= i + 2) {
				pathPoints.addAll(PointUtil.calculateCurveLine3Points(controlPoints.get(i).x, controlPoints.get(i).y, 
						controlPoints.get(i + 1).x, controlPoints.get(i + 1).y, 
						controlPoints.get(i + 2).x, controlPoints.get(i + 2).y));			
			}
		}
		return pathPoints;
	}
	
	@JsonIgnore
	public List<Point> getBorderPoints(List<Point> basicPoints, int radius) {
		List<Point> points = new LinkedList<Point>();
		Point curPoint = new Point(0,0);
		if (basicPoints.size() > 2){
			for (int i = 0; i < basicPoints.size(); i= i + 1) {
				Point[] tanPoints = calculateTangent(curPoint, basicPoints.get(i), radius);
				if (tanPoints != null){
					points.add(tanPoints[0]);
				}
			}
		}	
		return points;
	}
	
	
	private Point[] calculateTangent(Point point, Point center, int radius){

		   double L = Math.sqrt(Math.pow(center.x - point.x, 2) + Math.pow(center.y - point.y, 2));
		   if (L <= radius + 1)
			   return null;

		   double L1 = Math.sqrt(Math.pow(L, 2)-Math.pow(radius, 2));
		   double a1 = Math.asin((center.x - point.x) / L);
		   double b1 = Math.asin(radius / L);

		   double k;
		   if (center.y < point.y)
			   k = -1; 
		   else 
			   k = 1;

		   Point P1 = new Point();
		   P1.x = (int)(point.x +     L1 * Math.sin(a1 - b1));
		   P1.y = (int)(point.y + k * L1 * Math.cos(a1 - b1));
		   Point P2 = new Point();
		   P2.x = (int)(point.x +     L1 * Math.sin(a1 + b1));
		   P2.y = (int)(point.y + k * L1 * Math.cos(a1 + b1));
		   Point [] points = new Point[2];
		   points[0] = P1;
		   points[1] = P2;
		   return points;
	}
	
	
}
