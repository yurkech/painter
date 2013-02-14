package com.spongeblob.paint.model;

import java.io.Serializable;


public class Point implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1168719222708549603L;
	public int x = 0;
	public int y = 0;
	
	public Point() {}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
    }
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	void moveWithDelta(int deltaX, int deltaY){
		this.x = this.x + deltaX;
		this.y = this.y + deltaY;
	}

}
