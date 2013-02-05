package com.spongeblob.paint.model;

import java.awt.Graphics;

import com.spongeblob.paint.settings.ShapeColorSettings;
import com.spongeblob.paint.settings.ShapeSolidSettings;
import com.spongeblob.paint.utils.PointUtil;


public abstract class SolidAbstractShape extends AbstractShape{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982632616667148896L;
	
	public SolidAbstractShape(){
		addSettings(new ShapeSolidSettings());
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(((ShapeColorSettings)getSettingsByClass(ShapeColorSettings.class)).getColor());
		if (((ShapeSolidSettings)getSettingsByClass(ShapeSolidSettings.class)).isSolid()){
			if(((ShapeSolidSettings)getSettingsByClass(ShapeSolidSettings.class)).isFilled())
	  	 	{
				g.fillPolygon(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
	  	 	}
	     	else
	     	{
	     		g.drawPolygon(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
	     	}
		} else{
			g.drawPolyline(PointUtil.getXs(points), PointUtil.getYs(points), points.size());
		}
	}
}
