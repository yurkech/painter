package com.spongeblob.paint.model.shape;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.model.point.Point;
import com.spongeblob.paint.utils.PointUtil;

public class CurveLine4Points extends CurveLine3Points {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6959381887395467014L;

	public CurveLine4Points() {
	}

	public CurveLine4Points(int x, int y, Color c) {
		getControlPoints().add(new Point(x, y));
		colorSettings.setColor(c);
	}

	@JsonIgnore
	@Override
	public List<Point> getCurvePoints() {
		List<Point> pathPoints = new LinkedList<Point>();
		if (getControlPoints().size() > 3) {
			for (int i = 0; i < getControlPoints().size() - 3; i = i + 3) {
				pathPoints
						.addAll(PointUtil.calculateCurveLine4Points(
								getControlPoints().get(i).x, getControlPoints()
										.get(i).y, getControlPoints()
										.get(i + 1).x,
								getControlPoints().get(i + 1).y,
								getControlPoints().get(i + 2).x,
								getControlPoints().get(i + 2).y,
								getControlPoints().get(i + 3).x,
								getControlPoints().get(i + 3).y));
			}
		}
		return pathPoints;
	}

}
