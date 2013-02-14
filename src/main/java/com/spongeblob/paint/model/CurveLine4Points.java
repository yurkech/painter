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
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		colorSettings.setColor(c);
		model = "CURVELINE_4POINTS";
	}
	
	@JsonIgnore
	@Override
	public List<Point> getCurvePoints() {
		List<Point> pathPoints = new LinkedList<Point>();
		if (points.size() > 3){
			for (int i = 0; i < points.size() - 3; i= i + 3) {
				pathPoints.addAll(PointUtil.calculateCurveLine4Points(points.get(i).x, points.get(i).y, 
						points.get(i + 1).x, points.get(i + 1).y, 
						points.get(i + 2).x, points.get(i + 2).y,
						points.get(i + 3).x, points.get(i + 3).y));			
			}
		}
		return pathPoints;
	}
	

	

}
