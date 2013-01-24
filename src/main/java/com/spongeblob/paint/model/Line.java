package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends SimpleShape implements Shape{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109490022085804859L;

	public Line(int x1, int y1, int x2, int y2, Color c){
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		color = c;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
     	g.drawLine(p1.x, p1.y, p2.x, p2.y);
		
	}

}
