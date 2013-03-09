package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spongeblob.paint.utils.PointUtil;

public class ComplexPolygon extends SolidAbstractShape<MarkedPoint> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9039556669612238161L;

	public ComplexPolygon() {
	};

	public ComplexPolygon(int x, int y, Color c) {
		getControlPoints().add(new MarkedPoint(x, y));
		getColorSettings().setColor(c);
	}

	public void addPoint(int x, int y) {
		getControlPoints().add(new MarkedPoint(x, y));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColorSettings().getColor());
		List<Point> curvePoints = getCurvePoints();

		if (getSolidSettings().isSolid()) {
			if (getSolidSettings().isFilled()) {
				g.fillPolygon(PointUtil.getXs(curvePoints),
						PointUtil.getYs(curvePoints), curvePoints.size());
			} else {
				g.drawPolygon(PointUtil.getXs(curvePoints),
						PointUtil.getYs(curvePoints), curvePoints.size());
			}
		} else {
			g.drawPolyline(PointUtil.getXs(curvePoints),
					PointUtil.getYs(curvePoints), curvePoints.size());
		}

		List<Point> parralelCurvePoint = getParallelCurvePoints(curvePoints, 50);
		g.drawPolyline(PointUtil.getXs(parralelCurvePoint),
				PointUtil.getYs(parralelCurvePoint), parralelCurvePoint.size());
	}

	@Override
	public void drawControlPoints(Graphics g) {
		g.setColor(getColorSettings().getPathPointsColor());
		for (int i = 0; i < getControlPoints().size() - 1;) {
			if (((MarkedPoint) getControlPoints().get(i)).marker
					.equals(Marker.L_POINT)) {
				i++;
				continue;
			}
			if (((MarkedPoint) getControlPoints().get(i)).marker
					.equals(Marker.CL3_POINT)) {
				try {
					g.drawLine(getControlPoints().get(i).x, getControlPoints()
							.get(i).y, getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y);
					g.drawLine(getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y, getControlPoints()
									.get(i + 2).x, getControlPoints()
									.get(i + 2).y);
					i = i + 2;
				} catch (IndexOutOfBoundsException e) {
					// not enough points
					g.drawLine(getControlPoints().get(i).x, getControlPoints()
							.get(i).y, getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y);
					i++;
				}
				continue;
			}
			if (((MarkedPoint) getControlPoints().get(i)).marker
					.equals(Marker.CL4_POINT)) {
				try {
					g.drawLine(getControlPoints().get(i).x, getControlPoints()
							.get(i).y, getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y);
					g.drawLine(getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y, getControlPoints()
									.get(i + 2).x, getControlPoints()
									.get(i + 2).y);
					g.drawLine(getControlPoints().get(i + 2).x,
							getControlPoints().get(i + 2).y, getControlPoints()
									.get(i + 3).x, getControlPoints()
									.get(i + 3).y);
					i = i + 3;
				} catch (IndexOutOfBoundsException e) {
					// not enough points
					g.drawLine(getControlPoints().get(i).x, getControlPoints()
							.get(i).y, getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y);
					i++;
				}
				continue;
			}
		}
		for (Point point : getControlPoints()) {
			PointUtil.paintCircleAroundPoint(g, point);
		}
	}

	@JsonIgnore
	public List<Point> getCurvePoints() {
		List<Point> pathPoints = new LinkedList<Point>();
		for (int i = 0; i <= getControlPoints().size() - 1;) {
			if (((MarkedPoint) getControlPoints().get(i)).marker
					.equals(Marker.L_POINT)) {
				pathPoints.add(getControlPoints().get(i));
				i++;
				continue;
			}
			if (((MarkedPoint) getControlPoints().get(i)).marker
					.equals(Marker.CL3_POINT)) {
				try {
					pathPoints.addAll(PointUtil.calculateCurveLine3Points(
							getControlPoints().get(i).x, getControlPoints()
									.get(i).y, getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y, getControlPoints()
									.get(i + 2).x, getControlPoints()
									.get(i + 2).y));
					i = i + 2;
				} catch (IndexOutOfBoundsException e) {
					// not enough points
					pathPoints.add(getControlPoints().get(i));
					i++;
				}
				continue;
			}
			if (((MarkedPoint) getControlPoints().get(i)).marker
					.equals(Marker.CL4_POINT)) {
				try {
					pathPoints.addAll(PointUtil.calculateCurveLine4Points(
							getControlPoints().get(i).x, getControlPoints()
									.get(i).y, getControlPoints().get(i + 1).x,
							getControlPoints().get(i + 1).y, getControlPoints()
									.get(i + 2).x, getControlPoints()
									.get(i + 2).y, getControlPoints()
									.get(i + 3).x, getControlPoints()
									.get(i + 3).y));
					i = i + 3;
				} catch (IndexOutOfBoundsException e) {
					// not enough points
					pathPoints.add(getControlPoints().get(i));
					i++;
				}
				continue;
			}
		}
		return pathPoints;
	}

	@JsonIgnore
	public List<Point> getParallelCurvePoints(List<Point> curvePoints, int width) {
		List<Point> parallelCurvePoints = new LinkedList<Point>();
		for (int i = 0; i < curvePoints.size() - 1; i++) {
			parallelCurvePoints.addAll(PointUtil.getRect(curvePoints.get(i),
					curvePoints.get(i + 1), width));
		}
		removeIntersections(parallelCurvePoints);
		return parallelCurvePoints;
	}

	private void removeIntersections(List<Point> parallelCurvePoints) {
		for (int i = 0; i < parallelCurvePoints.size() - 3; i++) {
			Point p = PointUtil.getIntersection(parallelCurvePoints.get(i),
					parallelCurvePoints.get(i + 1), parallelCurvePoints.get(i + 2),
					parallelCurvePoints.get(i + 3));
			if (p != null){
				parallelCurvePoints.get(i + 1).x = p.x;
				parallelCurvePoints.get(i + 2).x = p.x;
				parallelCurvePoints.get(i + 1).y = p.y;
				parallelCurvePoints.get(i + 2).y = p.y;
			}
		}
	}

}
