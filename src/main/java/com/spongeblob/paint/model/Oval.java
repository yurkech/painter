package com.spongeblob.paint.model;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends SimpleShape implements Shape{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706959802243028105L;
	
	public Oval(int x1, int y1, int x2, int y2, Color c){
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		color = c;
	}
	
	public Oval(int x1, int y1, int x2, int y2, Color c, Boolean solid){
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
		color = c;
		isSolid = solid; 
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		if(isSolid)
  	 	{
     		if(p1.x > p2.x || p1.y > p2.y)
     			g.fillOval(p2.x, p2.y, p1.x - p2.x, p1.y - p2.y);
     		else	
     			g.fillOval(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
  	 	}
     	else
     	{
     		if(p1.x > p2.x || p1.y > p2.y)
     			g.drawOval (p2.x, p2.y, p1.x - p2.x, p1.y - p2.y);
     		else
     			g.drawOval (p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
     	}
	}
}
