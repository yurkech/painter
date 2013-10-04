package com.spongeblob.paint.model;

public enum PhysicObjectType {
	BORDERTRACK, CHECKPOINT; 
	
	@Override 
	public String toString() {
	   String s = super.toString();
	   return s.toLowerCase();
	 }
}
	
