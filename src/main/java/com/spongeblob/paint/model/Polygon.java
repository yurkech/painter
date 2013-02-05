package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.utils.PointUtil;

public class Polygon extends SolidAbstractShape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2411393086421989720L;
    private boolean isClosed;
    
    public Polygon(){}
	
	public Polygon(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).setColor(c);
	}
	
	public Polygon(int x, int y, Color c, Boolean solid){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).setColor(c);
		isSolid = solid;
	}
	
	public void draw(Graphics g) {
		g.setColor(((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).getColor());
		if (isClosed){
			if(isSolid)
	  	 	{
				g.fillPolygon(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
	  	 	}
	     	else
	     	{
	     		g.drawPolygon(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
	     	}
		}
		else{
			g.drawPolyline(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
		}
	}

	
	@JsonProperty(value="isClosed")
	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

}
