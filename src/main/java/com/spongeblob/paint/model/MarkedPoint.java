package com.spongeblob.paint.model;

public class MarkedPoint extends Point{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2582804592455198673L;
	
	public Marker marker = Marker.L_POINT;

	public MarkedPoint() {}
	
	public MarkedPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public MarkedPoint(int x, int y, Marker marker) {
		this.x = x;
		this.y = y;
		this.marker = marker;
	}
	
	public MarkedPoint(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public MarkedPoint(Point p, Marker marker) {
		this.x = p.x;
		this.y = p.y;
		this.marker = marker;
	}
	
	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}
}
