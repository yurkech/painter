package com.spongeblob.paint.model.point;

public enum Marker {
	L_POINT, CL3_POINT, CL4_POINT;  

	@Override 
	public String toString() {
	   String s = super.toString();
	   return s.toLowerCase();
	 }
}
