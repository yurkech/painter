package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.spongeblob.paint.utils.PointUtil;

public class Polygon extends MultipointsShape implements Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2411393086421989720L;
    
    private boolean isClosed;
	
	
	public Polygon(int x, int y, Color c){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		color = c;
	}
	
	public Polygon(int x, int y, Color c, Boolean solid){
		points = new LinkedList<Point>();
		points.add(new Point(x, y));
		color = c;
		isSolid = solid;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
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

	

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

}
