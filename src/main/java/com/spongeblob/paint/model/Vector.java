package com.spongeblob.paint.model;

import java.io.Serializable;

public class Vector implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5771586631233516036L;
	private Point p1;
	private Point p2;
	
	public Vector(Point p1, Point p2){
		this.setP1(p1);
		this.setP2(p2);
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
	
	public int getA1(){
		return p2.getX() - p1.getX();
	}
	
	public int getA2(){
		return p2.getY() - p1.getY();
	}

	public double getLength(){
		return Math.sqrt(getA1()*getA1() + getA2()*getA2());
		
	}
}
