package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.imageio.*;

import com.spongeblob.paint.model.Line;
import com.spongeblob.paint.model.Oval;
import com.spongeblob.paint.model.Rectangle;
import com.spongeblob.paint.model.Shape;

public class CanvasPanel extends JPanel implements MouseListener,MouseMotionListener, Serializable
{
	/**
	 * 
	 */
	protected final static int RADIUS = 10;
	
	private static final long serialVersionUID = -3371112021797757444L;
	protected final static int LINE=1,SQUARE=2,OVAL=3,POLYGON=4,ROUND_RECT=5,FREE_HAND=6,
							 SOLID_POLYGON=44,
								SOLID_ROUND_RECT=55,
								DRAG=7;
	protected static Vector<Serializable> vPolygon,vRoundRect,
						 	vSolidPolygon,vSolidRoundRect,vFile,
						 	xPolygon, yPolygon;		
	protected static LinkedList<Shape> vObjects, redoStack;
	
	private Color foreGroundColor, backGroundColor; 
	
	private int x1,y1,x2,y2, drawMode=0;
	private boolean solidMode, polygonBuffer;
	
	private Point currentDragPoint;
	
	private File fileName;
					    
	public CanvasPanel()
	{
		vObjects		= new LinkedList<Shape>();
		vPolygon		= new Vector<Serializable>();
		vRoundRect		= new Vector<Serializable>();
		vSolidPolygon	= new Vector<Serializable>();
		vSolidRoundRect	= new Vector<Serializable>();
		vFile			= new Vector<Serializable>();
		xPolygon		= new Vector<Serializable>();
		yPolygon		= new Vector<Serializable>();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		solidMode 		= false;
		polygonBuffer 	= false; 
		
		foreGroundColor = Color.BLACK;
		backGroundColor = Color.WHITE;
		setBackground(backGroundColor);
		
		redoStack = new LinkedList<Shape>();
		
		repaint();		
	}
/*----------------------------------------------------------------------------*/		
	public void mousePressed(MouseEvent event)
	{
		x1 = event.getX();
        y1 = event.getY();
	}
/*----------------------------------------------------------------------------*/
	public void mouseClicked(MouseEvent event){}
	public void mouseMoved(MouseEvent event){}
/*----------------------------------------------------------------------------*/
	public void mouseReleased(MouseEvent event)
	{
		if (drawMode == DRAG)
        {	
			currentDragPoint = null;
        }
		if (drawMode == LINE)
        {	
           	vObjects.add(new Line(x1,y1,event.getX(),event.getY(),foreGroundColor));
        }
        if (drawMode == SQUARE) 
        {
            if(solidMode)
           	{
           		if(x1 > event.getX() || y1 > event.getY())
				{
           			vObjects.add(new Rectangle(event.getX(),event.getY(),x1,y1,foreGroundColor, Boolean.TRUE));
           		}
           		else
           		{
           			vObjects.add(new Rectangle(x1,y1,event.getX(),event.getY(),foreGroundColor, Boolean.TRUE));
           		}
           	}
            else
            {
           		if(x1 > event.getX() || y1 > event.getY())
           		{
           			vObjects.add(new Rectangle(event.getX(),event.getY(),x1,y1,foreGroundColor));
           		}
           		else
           		{
           			vObjects.add(new Rectangle(x1,y1,event.getX(),event.getY(),foreGroundColor));
           		}
           	}
        }
        if (drawMode == CanvasPanel.OVAL) 
        {
          	if(solidMode)
          	{
          		if(x1 > event.getX() || y1 > event.getY())
          		{
          			vObjects.add(new Oval(event.getX(),event.getY(),x1,y1,foreGroundColor, Boolean.TRUE));
          		}
          		else
          		{
          			vObjects.add(new Oval(x1,y1,event.getX(),event.getY(),foreGroundColor, Boolean.TRUE));
          		}
           	}
           	else
           	{
           		if(x1 > event.getX() || y1 > event.getY())
           		{
           			vObjects.add(new Oval(event.getX(),event.getY(),x1,y1,foreGroundColor));
           		}
           		else	
           		{
           			vObjects.add(new Oval(x1,y1,event.getX(),event.getY(),foreGroundColor));
           		}
           	}
        }
        if (drawMode == CanvasPanel.POLYGON || drawMode == CanvasPanel.SOLID_POLYGON) 
        {
        	xPolygon.add(new Integer(event.getX()));
        	yPolygon.add(new Integer(event.getY()));
        	polygonBuffer = true;
        	repaint();       	      
        }
        if (drawMode == CanvasPanel.ROUND_RECT) 
        {
          	if(solidMode)
          	{
          		if(x1 > event.getX() || y1 > event.getY())
          		{
          			vSolidRoundRect.add(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor));
          		}
          		else
          		{
           			vSolidRoundRect.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor));
           		}
           	}
           	else
           	{
           		if(x1 > event.getX() || y1 > event.getY())
           		{
           			vRoundRect.add(new Coordinate(event.getX(),event.getY(),x1,y1,foreGroundColor));
           		}
           		else
           		{
           			vRoundRect.add(new Coordinate(x1,y1,event.getX(),event.getY(),foreGroundColor));
           		}
           	}
        }           
	}
/*----------------------------------------------------------------------------*/
	public void mouseEntered(MouseEvent event)
	{
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}
/*----------------------------------------------------------------------------*/
	public void mouseExited(MouseEvent event)
	{
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
/*----------------------------------------------------------------------------*/
	public void mouseDragged(MouseEvent event)
	{
        x2 = event.getX();
        y2 = event.getY();
            
        if (drawMode == CanvasPanel.DRAG) 
        {
        	if (currentDragPoint == null){
	        	for (int i=0;i<vObjects.size();i++){
	        		Point p = ((Shape)vObjects.get(i)).contains(new Point(x1, y1), RADIUS);
	        		if (p != null){
	        			currentDragPoint = p;
	        		}
	          	}
        	}
        	if (currentDragPoint != null){
        		currentDragPoint.setLocation(x2, y2);
        	}	
        }
        if (drawMode == CanvasPanel.FREE_HAND) 
        {
        	vObjects.add(new Line(x1, y1, x2, y2, foreGroundColor));
            x1 = x2;
            y1 = y2;
         }
         repaint();
	}
/*----------------------------------------------------------------------------*/
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
      	redrawVectorBuffer(g);

	  	g.setColor(foreGroundColor);
      
      	if (drawMode == LINE) 
      	{
        	Line l = new Line(x1, y1, x2, y2, foreGroundColor);
      		l.draw(g);
      		l.drawPathPoints(g);
      	}
      	if (drawMode == OVAL) 
      	{
      		Oval o = new Oval(x1, y1, x2, y2, foreGroundColor);
      	 	if(solidMode)
      	 	{
      	 		o.setSolid(Boolean.TRUE);
      	 	}
      	 	o.draw(g);
      		o.drawPathPoints(g);
      	}
      	if (drawMode == ROUND_RECT) 
      	{
         	if(solidMode)
         	{
         		if(x1 > x2 || y1 > y2)
         			g.fillRoundRect(x2,y2,x1-x2,y1-y2,25,25);
         		else
         			g.fillRoundRect(x1,y1,x2-x1,y2-y1,25,25);
         	}
         	else
         	{
         		if(x1 > x2 || y1 > y2)
         			g.drawRoundRect(x2,y2,x1-x2,y1-y2,25,25);
         		else
         			g.drawRoundRect(x1,y1,x2-x1,y2-y1,25,25);
         	}
      	}
      	if (drawMode == SQUARE) 
      	{
      		Rectangle r = new Rectangle(x1, y1, x2, y2, foreGroundColor);
      	 	if(solidMode)
      	 	{
      	 		r.setSolid(Boolean.TRUE);
      	 	}
      	 	r.draw(g);
      		r.drawPathPoints(g);
      	}
      	if (drawMode == POLYGON || drawMode == SOLID_POLYGON)
      	{
      		int xPos[] = new int[xPolygon.size()];
      	 	int yPos[] = new int[yPolygon.size()];
      	 
      	 	for(int count=0;count<xPos.length;count++)
      	 	{
      	 		xPos[count] = ((Integer)(xPolygon.elementAt(count))).intValue();
      	 		yPos[count] = ((Integer)(yPolygon.elementAt(count))).intValue();
      	 	}
      	 	g.drawPolyline(xPos,yPos,xPos.length);
      	 	polygonBuffer = true;
	  	}
      	/*if (drawMode == FREE_HAND) 
      	{
         	g.drawLine(x1, y1, x2, y2);
      	}*/
	}
/*----------------------------------------------------------------------------*/
	public void setDrawMode(int mode)
	{
		drawMode = mode;
	}
	public int getDrawMode()
	{	
		return drawMode;	
	}
/*----------------------------------------------------------------------------*/
	public void setSolidMode(Boolean inSolidMode)
	{
		solidMode = inSolidMode.booleanValue();
	}
	public Boolean getSolidMode()
	{
		return Boolean.valueOf(solidMode);
	}
/*----------------------------------------------------------------------------*/
	public void setForeGroundColor(Color inputColor)
	{
		foreGroundColor = inputColor;
	}
	public Color getForeGroundColor()
	{
		return foreGroundColor;
	}
/*----------------------------------------------------------------------------*/
	public void setBackGroundColor(Color inputColor)
	{
		backGroundColor = inputColor;
		this.setBackground(backGroundColor);
	}
	public Color getBackGroundColor()
	{
		return backGroundColor;
	}
/*----------------------------------------------------------------------------*/
	public void undo()
	{
		
		if(vObjects.isEmpty())
			JOptionPane.showMessageDialog(null, "Can't Undo","Painter", JOptionPane.INFORMATION_MESSAGE);
		else
		{
			Shape o = vObjects.removeLast();
			redoStack.add(o);
		}
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public void redo()
	{
		if(redoStack.isEmpty())
			JOptionPane.showMessageDialog(null,"Can't Redo","Painter",JOptionPane.INFORMATION_MESSAGE);
		else
		{
			Shape o = redoStack.removeLast();
			vObjects.add(o);
		}
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public void clearCanvas()
	{
		vObjects.clear();
		vPolygon.removeAllElements();
		vRoundRect.removeAllElements();
		vSolidPolygon.removeAllElements();
		vSolidRoundRect.removeAllElements();
		redoStack.clear();
		repaint();
	}
/*----------------------------------------------------------------------------*/	
	public void SaveCanvasToFile()
	{
		if(fileName != null)
		{
			vFile.removeAllElements();
			vFile.addElement(vObjects);
			vFile.addElement(vPolygon);
			vFile.addElement(vRoundRect);
			vFile.addElement(vSolidPolygon);
			vFile.addElement(vSolidRoundRect);
			vFile.addElement(new Color(backGroundColor.getRGB()));
			RenderedImage rendImage = myCreateImage();
			
			try
			{
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(vFile);
				JOptionPane.showMessageDialog(null,"File Saved","Painter",JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception exp){}
			
			try 
			{
        		File file = new File(fileName.toString() + ".jpg");        		
        		ImageIO.write(rendImage, "jpg", file);
    		}catch (IOException e) {}
		}
		else
		{
			SaveAsCanvasToFile();
		}
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public void SaveAsCanvasToFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	
		int result = fileChooser.showSaveDialog(null);
	
		if(result == JFileChooser.CANCEL_OPTION) return;
			
		fileName = fileChooser.getSelectedFile();

		if(fileName == null || fileName.getName().equals(""))
			JOptionPane.showMessageDialog(null,"Invalid File Name","Painter",JOptionPane.ERROR_MESSAGE);
		else
		{
			vFile.removeAllElements();
			vFile.addElement(vObjects);
			vFile.addElement(vPolygon);
			vFile.addElement(vRoundRect);
			vFile.addElement(vSolidPolygon);
			vFile.addElement(vSolidRoundRect);
			vFile.addElement(new Color(backGroundColor.getRGB()));
			
			RenderedImage rendImage = myCreateImage();
			
			try
			{
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(vFile);
				JOptionPane.showMessageDialog(null,"File Saved","Painter",JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception exp){}
			
			try {
        		File file = new File(fileName.toString() + ".jpg");        		
        		ImageIO.write(rendImage, "jpg", file);
    		}catch (IOException e) {}
		}		    
	repaint();
	}
/*----------------------------------------------------------------------------*/
	public void OpenCanvasFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
		int result = fileChooser.showOpenDialog(null);
		if(result == JFileChooser.CANCEL_OPTION) return;
			
		fileName = fileChooser.getSelectedFile();
		
		if(fileName != null)
		{
			try{
				FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				vFile = (Vector<Serializable>) ois.readObject();
				
				this.clearCanvas();
				vObjects 		= (LinkedList<Shape>)vFile.elementAt(1);
				vPolygon		= (Vector<Serializable>)vFile.elementAt(3);
				vRoundRect		= (Vector<Serializable>)vFile.elementAt(4);
				vSolidPolygon	= (Vector<Serializable>)vFile.elementAt(6);
				vSolidRoundRect	= (Vector<Serializable>)vFile.elementAt(7);
				backGroundColor = (Color)vFile.elementAt(10);
				
				this.setBackground(backGroundColor);
			}
			catch(Exception exp){
				JOptionPane.showMessageDialog(null,"Can't Open File","Painter",JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		else{
			fileName = null;
		}
		repaint();
	}
/*----------------------------------------------------------------------------*/
	public boolean isExistPolygonBuffer()
	{
		return polygonBuffer;
	} 
/*----------------------------------------------------------------------------*/	
	public void flushPolygonBuffer()
	{
		if(!solidMode)
		{
			vPolygon.add(new Coordinate(xPolygon, yPolygon, foreGroundColor));
		}
		else
		{
			vSolidPolygon.add(new Coordinate(xPolygon, yPolygon, foreGroundColor));
		}
		
		xPolygon.removeAllElements();
		yPolygon.removeAllElements();
			
		polygonBuffer = false;
		repaint();
	}
/*----------------------------------------------------------------------------*/
	private class Coordinate implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4142607983957465035L;
		private Point p1,p2;
		private Color foreColor;
		private Vector<Serializable> xPoly, yPoly;
		
		public Coordinate (int inx1,int iny1,int inx2, int iny2, Color color) 
		{
        	p1 = new Point(inx1, iny1);
         	p2 = new Point(inx2, iny2);
         	foreColor = color;
      	}
      	public Coordinate(Vector<Serializable> inXPolygon, Vector<Serializable> inYPolygon, Color color)
      	{
      		xPoly = (Vector<Serializable>)inXPolygon.clone();
      		yPoly = (Vector<Serializable>)inYPolygon.clone();
      		foreColor = color;
      	}
      	public Color colour()
      	{
        	return foreColor;
      	}
      	public int getX1 () 
      	{
        	return p1.x;
      	}
      	public int getX2 () 
      	{
        	return p2.x;
      	}
      	public int getY1 () 
      	{
        	return p1.y;
      	}
      	public int getY2 () 
      	{
        	return p2.y;
      	}
      	public Vector<Serializable> getXPolygon()
      	{
      		return xPoly;
      	}
      	public Vector<Serializable> getYPolygon()
      	{
      		return yPoly;
      	}
	}		
/*----------------------------------------------------------------------------*/	
	private RenderedImage myCreateImage() 
	{
        BufferedImage bufferedImage = new BufferedImage(600,390, BufferedImage.TYPE_INT_RGB);

        Graphics g = bufferedImage.createGraphics();
    	redrawVectorBuffer(g);

      	g.dispose();
      	return bufferedImage;
    }
/*----------------------------------------------------------------------------*/	
    private void redrawVectorBuffer(Graphics g)
    {
      	for (int i=0;i<vObjects.size();i++){
         	((Shape)vObjects.get(i)).draw(g);
         	//((Shape)vObjects.get(i)).drawPathPoints(g);
      	}
      	for (int i=0;i<vRoundRect.size();i++){
         	g.setColor(((Coordinate)vRoundRect.elementAt(i)).colour());
         	g.drawRoundRect(((Coordinate)vRoundRect.elementAt(i)).getX1(),((Coordinate)vRoundRect.elementAt(i)).getY1(),((Coordinate)vRoundRect.elementAt(i)).getX2()-((Coordinate)vRoundRect.elementAt(i)).getX1(),((Coordinate)vRoundRect.elementAt(i)).getY2()-((Coordinate)vRoundRect.elementAt(i)).getY1(),25,25);
      	}
      	for (int i=0;i<vSolidRoundRect.size();i++){
         	g.setColor(((Coordinate)vSolidRoundRect.elementAt(i)).colour());
       	 	g.fillRoundRect(((Coordinate)vSolidRoundRect.elementAt(i)).getX1(),((Coordinate)vSolidRoundRect.elementAt(i)).getY1(),((Coordinate)vSolidRoundRect.elementAt(i)).getX2()-((Coordinate)vSolidRoundRect.elementAt(i)).getX1(),((Coordinate)vSolidRoundRect.elementAt(i)).getY2()-((Coordinate)vSolidRoundRect.elementAt(i)).getY1(),25,25);
      	}
      	for(int i=0;i<vPolygon.size();i++){
      	 	int xPos[] = new int[((Coordinate)vPolygon.elementAt(i)).getXPolygon().size()];
      	 	int yPos[] = new int[((Coordinate)vPolygon.elementAt(i)).getYPolygon().size()];
      	 
      	 	for(int count=0;count<xPos.length;count++)
      	 	{
      	 		xPos[count] = ((Integer)((Coordinate)vPolygon.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 		yPos[count] = ((Integer)((Coordinate)vPolygon.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 	}     	 
      	 	g.setColor(((Coordinate)vPolygon.elementAt(i)).colour());
      	 	g.drawPolygon(xPos,yPos,xPos.length);
	  	}
	  	for(int i=0;i<vSolidPolygon.size();i++){
      	 	int xPos[] = new int[((Coordinate)vSolidPolygon.elementAt(i)).getXPolygon().size()];
      	 	int yPos[] = new int[((Coordinate)vSolidPolygon.elementAt(i)).getYPolygon().size()];
      	 
      	 	for(int count=0;count<xPos.length;count++)
      	 	{
      	 		xPos[count] = ((Integer)((Coordinate)vSolidPolygon.elementAt(i)).getXPolygon().elementAt(count)).intValue();
      	 		yPos[count] = ((Integer)((Coordinate)vSolidPolygon.elementAt(i)).getYPolygon().elementAt(count)).intValue();
      	 	}
      	 	g.setColor(((Coordinate)vSolidPolygon.elementAt(i)).colour());
      	 	g.fillPolygon(xPos,yPos,xPos.length);
      	}	
    }
    
}