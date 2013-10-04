package com.spongeblob.paint.model.point;

import java.io.Serializable;

public class Point implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1168719222708549603L;
	public int x = 0;
	public int y = 0;

	public Point() {
	}

	public Point(int x, int y) {
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

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void scale(double dX, double dY) {
		this.x = (int) (x * dX);
		this.y = (int) (y * dY);
	}
	
	public void moveWithDelta(int deltaX, int deltaY) {
		this.x = this.x + deltaX;
		this.y = this.y + deltaY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
