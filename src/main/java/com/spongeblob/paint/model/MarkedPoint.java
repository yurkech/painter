package com.spongeblob.paint.model;

public class MarkedPoint extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2582804592455198673L;

	public Marker marker = Marker.L_POINT;

	public MarkedPoint() {
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((marker == null) ? 0 : marker.hashCode());
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
		MarkedPoint other = (MarkedPoint) obj;
		if (marker != other.marker)
			return false;
		return true;
	}

}
