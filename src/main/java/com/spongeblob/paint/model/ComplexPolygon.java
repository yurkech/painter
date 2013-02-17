package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.utils.PointUtil;

public class ComplexPolygon extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9039556669612238161L;
	
	public ComplexPolygon(){};
	
	public ComplexPolygon(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new MarkedPoint(x, y));
		colorSettings.setColor(c);
		model = "COMPLEX_POLYGON";
	}
	
	public void addPoint(int x, int y){
		controlPoints.add(new MarkedPoint(x, y));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		List<Point> curvePoints = getCurvePoints();
		
		if (solidSettings.isSolid()){
			if(solidSettings.isFilled())
	  	 	{
				g.fillPolygon(PointUtil.getXs(curvePoints), PointUtil.getYs(curvePoints), curvePoints.size());
	  	 	}
	     	else
	     	{
	     		g.drawPolygon(PointUtil.getXs(curvePoints), PointUtil.getYs(curvePoints), curvePoints.size());
	     	}
		} else{
			g.drawPolyline(PointUtil.getXs(curvePoints), PointUtil.getYs(curvePoints), curvePoints.size());
		}
	}
	
	@Override
	public void drawControlPoints(Graphics g) {
		g.setColor(colorSettings.getPathPointsColor());
		for (int i = 0; i < controlPoints.size() - 1;) {
			if (((MarkedPoint)controlPoints.get(i)).marker.equals(Marker.L_POINT)){
				i++;
				continue;
			}
			if (((MarkedPoint)controlPoints.get(i)).marker.equals(Marker.CL3_POINT)){
				try{
					g.drawLine(controlPoints.get(i).x, controlPoints.get(i).y, controlPoints.get(i+1).x, controlPoints.get(i+1).y);
					g.drawLine(controlPoints.get(i+1).x, controlPoints.get(i+1).y, controlPoints.get(i+2).x, controlPoints.get(i+2).y);
					i = i + 2;
				} catch (IndexOutOfBoundsException e){
					//not enough points
					g.drawLine(controlPoints.get(i).x, controlPoints.get(i).y, controlPoints.get(i+1).x, controlPoints.get(i+1).y);
					i++;
				}
				continue;
			}
			if (((MarkedPoint)controlPoints.get(i)).marker.equals(Marker.CL4_POINT)){
				try{
					g.drawLine(controlPoints.get(i).x, controlPoints.get(i).y, controlPoints.get(i+1).x, controlPoints.get(i+1).y);
					g.drawLine(controlPoints.get(i+1).x, controlPoints.get(i+1).y, controlPoints.get(i+2).x, controlPoints.get(i+2).y);
					g.drawLine(controlPoints.get(i+2).x, controlPoints.get(i+2).y, controlPoints.get(i+3).x, controlPoints.get(i+3).y);
					i = i + 3;
				} catch (IndexOutOfBoundsException e){
					//not enough points
					g.drawLine(controlPoints.get(i).x, controlPoints.get(i).y, controlPoints.get(i+1).x, controlPoints.get(i+1).y);
					i++;
				}
				continue;
			}
		}
		for (Point point : controlPoints) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}
	
	@JsonIgnore
	public List<Point> getCurvePoints() {
		List<Point> pathPoints = new LinkedList<Point>();
		for (int i = 0; i <= controlPoints.size() - 1;) {
			if (((MarkedPoint)controlPoints.get(i)).marker.equals(Marker.L_POINT)){
				pathPoints.add(controlPoints.get(i));
				i++;
				continue;
			}
			if (((MarkedPoint)controlPoints.get(i)).marker.equals(Marker.CL3_POINT)){
				try{
					pathPoints.addAll(PointUtil.calculateCurveLine3Points(controlPoints.get(i).x, controlPoints.get(i).y, 
							controlPoints.get(i + 1).x, controlPoints.get(i + 1).y, 
							controlPoints.get(i + 2).x, controlPoints.get(i + 2).y));	
					i = i + 2;
				} catch (IndexOutOfBoundsException e){
					//not enough points
					pathPoints.add(controlPoints.get(i));
					i++;
				}
				continue;
			}
			if (((MarkedPoint)controlPoints.get(i)).marker.equals(Marker.CL4_POINT)){
				try{
					pathPoints.addAll(PointUtil.calculateCurveLine4Points(controlPoints.get(i).x, controlPoints.get(i).y, 
							controlPoints.get(i + 1).x, controlPoints.get(i + 1).y, 
							controlPoints.get(i + 2).x, controlPoints.get(i + 2).y,
							controlPoints.get(i + 3).x, controlPoints.get(i + 3).y));		
					i = i + 3;
				} catch (IndexOutOfBoundsException e){
					//not enough points
					pathPoints.add(controlPoints.get(i));
					i++;
				}
				continue;
			}
		}
		return pathPoints;
	}
}
