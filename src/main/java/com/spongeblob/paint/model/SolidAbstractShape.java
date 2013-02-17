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
	@Override
	public List<Settings> getSettings() {
		List<Settings> list = super.getSettings();
		list.add(solidSettings);
		return list;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(colorSettings.getColor());
		if (solidSettings.isSolid()){
			if(solidSettings.isFilled())
	  	 	{
				g.fillPolygon(PointUtil.getXs(controlPoints), PointUtil.getYs(controlPoints), controlPoints.size());
	  	 	}
	     	else
	     	{
	     		g.drawPolygon(PointUtil.getXs(controlPoints), PointUtil.getYs(controlPoints), controlPoints.size());
	     	}
		} else{
			g.drawPolyline(PointUtil.getXs(controlPoints), PointUtil.getYs(controlPoints), controlPoints.size());
		}
	}
}
