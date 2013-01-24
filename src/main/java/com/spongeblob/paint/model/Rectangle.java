package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rectangle extends SimpleShape implements Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6085392574960675124L;

	public Rectangle(int x1, int y1, int x2, int y2, Color c){
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		color = c;
	}
	
	public Rectangle(int x1, int y1, int x2, int y2, Color c, Boolean solid){
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		color = c;
		isSolid = solid; 
	}
	
	public void draw(Graphics g) {
		if(isSolid)
  	 	{
  	 		if(p1.x > p2.x || p1.y > p2.y)
  	 			g.fillRect (p2.x, p2.y, p1.x - p2.x, p1.y - p2.y);
  	 		else
  	 			g.fillRect (p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
  	 	}
     	else
     	{
     		if(p1.x > p2.x || p1.y > p2.y)
     			g.drawRect (p2.x, p2.y, p1.x - p2.x, p1.y - p2.y);
     		else
     			g.drawRect (p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
     	}
		
	}
}
