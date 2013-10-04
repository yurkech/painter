package com.spongeblob.paint.startrack.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.model.ComplexPolygon;
import com.spongeblob.paint.model.MarkedPoint;
import com.spongeblob.paint.model.Point;
import com.spongeblob.paint.settings.ParallelLineSettings;
import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.utils.PointUtil;

public class Road extends ComplexPolygon {
	public static String UPLINE_BORDER_SETTINGS = "UPLINE_BORDER_SETTINGS";
	public static String DOWNLINE_BORDER_SETTINGS = "DOWNLINE_BORDER_SETTINGS";

	/**
	 * 
	 */
	private static final long serialVersionUID = -6471264294390639372L;

	@JsonProperty(value = "uplineBorder")
	protected ParallelLineSettings upLineBorder = new ParallelLineSettings(
			"Up Line Border");

	@JsonProperty(value = "downlineBorder")
	protected ParallelLineSettings downLineBorder = new ParallelLineSettings(
			"Down Line Border");

	public Road() {
	};

	public Road(int x, int y, Color c) {
		getControlPoints().add(new MarkedPoint(x, y));
		colorSettings.setColor(c);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		List<Point> curvePoints = getCurvePoints();

		if (solidSettings.isSolid()) {
			if (solidSettings.isFilled()) {
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

		// draw down line
		if (downLine.isEnabled()) {
			List<Point> downlineCurvePoint = getParallelCurvePoints(
					curvePoints, downLine.getWidth(), downLine.getSmooth(),
					true);
			if (solidSettings.isSolid())
				g.drawPolygon(PointUtil.getXs(downlineCurvePoint),
						PointUtil.getYs(downlineCurvePoint),
						downlineCurvePoint.size());
			else
				g.drawPolyline(PointUtil.getXs(downlineCurvePoint),
						PointUtil.getYs(downlineCurvePoint),
						downlineCurvePoint.size());

			// draw down line border
			if (downLineBorder.isEnabled()) {
				List<Point> downBorderCurvePoint = getParallelCurvePoints(
						downlineCurvePoint, downLineBorder.getWidth(),
						downLineBorder.getSmooth(), true);
				if (solidSettings.isSolid())
					g.drawPolygon(PointUtil.getXs(downBorderCurvePoint),
							PointUtil.getYs(downBorderCurvePoint),
							downBorderCurvePoint.size());
				else
					g.drawPolyline(PointUtil.getXs(downBorderCurvePoint),
							PointUtil.getYs(downBorderCurvePoint),
							downBorderCurvePoint.size());
			}
		}

		// draw up line
		if (upLine.isEnabled()) {
			List<Point> uplineCurvePoint = getParallelCurvePoints(curvePoints,
					upLine.getWidth(), upLine.getSmooth(), false);
			if (solidSettings.isSolid())
				g.drawPolygon(PointUtil.getXs(uplineCurvePoint),
						PointUtil.getYs(uplineCurvePoint),
						uplineCurvePoint.size());
			else
				g.drawPolyline(PointUtil.getXs(uplineCurvePoint),
						PointUtil.getYs(uplineCurvePoint),
						uplineCurvePoint.size());
			if (upLineBorder.isEnabled()) {
				List<Point> upBorderCurvePoint = getParallelCurvePoints(
						uplineCurvePoint, upLineBorder.getWidth(),
						upLineBorder.getSmooth(), false);
				if (solidSettings.isSolid())
					g.drawPolygon(PointUtil.getXs(upBorderCurvePoint),
							PointUtil.getYs(upBorderCurvePoint),
							upBorderCurvePoint.size());
				else
					g.drawPolyline(PointUtil.getXs(upBorderCurvePoint),
							PointUtil.getYs(upBorderCurvePoint),
							upBorderCurvePoint.size());
			}
		}
	}

	@Override
	@JsonIgnore
	public Map<String, Settings> getShapeSettings() {
		Map<String, Settings> shapeSettings = super.getShapeSettings();
		shapeSettings.put(UPLINE_BORDER_SETTINGS, upLineBorder);
		shapeSettings.put(DOWNLINE_BORDER_SETTINGS, downLineBorder);
		return shapeSettings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((downLineBorder == null) ? 0 : downLineBorder.hashCode());
		result = prime * result
				+ ((upLineBorder == null) ? 0 : upLineBorder.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Road other = (Road) obj;
		if (downLineBorder == null) {
			if (other.downLineBorder != null)
				return false;
		} else if (!downLineBorder.equals(other.downLineBorder))
			return false;
		if (upLineBorder == null) {
			if (other.upLineBorder != null)
				return false;
		} else if (!upLineBorder.equals(other.upLineBorder))
			return false;
		return true;
	}

}
