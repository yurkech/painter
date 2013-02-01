package com.spongeblob.paint.model;

import org.codehaus.jackson.annotate.JsonProperty;


public abstract class SolidAbstractShape extends AbstractShape{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982632616667148896L;
	protected boolean isSolid;
	
	@JsonProperty(value="isSolid")
	public boolean isSolid() {
		return isSolid;
	}
	
	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}
}
