package com.spongeblob.paint.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.utils.PointUtil;

public class CurveLine4Points extends CurveLine3Points{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6959381887395467014L;

	public CurveLine4Points(){}
	
	public CurveLine4Points(int x, int y, Color c){
		controlPoints = new LinkedList<Point>();
		controlPoints.add(new Point(x, y));
		colorSettings.setColor(c);
	}
	
	@JsonIgnore
	@Override
	public List<Point> getCurvePoints() {
		List<Point> pathPoints = new LinkedList<Point>();
		if (controlPoints.size() > 3){
			for (int i = 0; i < controlPoints.size() - 3; i= i + 3) {
				pathPoints.addAll(PointUtil.calculateCurveLine4Points(controlPoints.get(i).x, controlPoints.get(i).y, 
						controlPoints.get(i + 1).x, controlPoints.get(i + 1).y, 
						controlPoints.get(i + 2).x, controlPoints.get(i + 2).y,
						controlPoints.get(i + 3).x, controlPoints.get(i + 3).y));			
			}
		}
		return pathPoints;
	}
	

	

}
