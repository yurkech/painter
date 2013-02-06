package com.spongeblob.paint.model;

import java.awt.Graphics;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.settings.Settings;
import com.spongeblob.paint.settings.ShapeSolidSettings;
import com.spongeblob.paint.utils.PointUtil;


public abstract class SolidAbstractShape extends AbstractShape{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982632616667148896L;
	@JsonProperty("solid")
	protected ShapeSolidSettings solidSettings; 
	
	public SolidAbstractShape(){
		solidSettings = new ShapeSolidSettings();
	}
	
	@JsonIgnore
	public List<Settings> getAllSettings() {
		List<Settings> list = super.getAllSettings();
		list.add(solidSettings);
		return list;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		if (solidSettings.isSolid()){
			if(solidSettings.isFilled())
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
