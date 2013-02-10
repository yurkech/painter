package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

import javax.swing.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.spongeblob.paint.model.CleverLine;
import com.spongeblob.paint.model.CurveLine3Points;
import com.spongeblob.paint.model.CurveLine4Points;
import com.spongeblob.paint.model.HandLine;
import com.spongeblob.paint.model.Line;
import com.spongeblob.paint.model.Oval;
import com.spongeblob.paint.model.Rectangle;
import com.spongeblob.paint.model.Polygon;
import com.spongeblob.paint.model.Shape;
import com.spongeblob.paint.model.Point;
import com.spongeblob.paint.settings.CanvasSettings;


public class CanvasPanel extends JPanel implements MouseListener,
		MouseMotionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3371112021797757444L;
	
	protected final static int RADIUS = 10;
	protected final static double SCALE_STEP = 0.05;
	
	private BufferedImage image;
	

	private StatusBarPanel statusBarPanel;
	private SettingsPanel settingsPanel;
	
	
	protected final static int LINE = 1, SQUARE = 2, OVAL = 3, POLYGON = 4, CURVE_LINE_3P = 5, FREE_HAND = 6, DRAG = 7, CURVE_LINE_4P = 8;
	protected static LinkedList<Shape> vObjects, redoStack;

	private Color foreGroundColor, backGroundColor;

	private int drawMode = 0;
	private boolean showPathMode;

	private Point currentDragPoint;
	private Shape currentShape;
	private int baseX, baseY;
	
    private double scale = 1.0;
	
	public CanvasPanel(StatusBarPanel statusBarPanel, SettingsPanel settingsPanel) {
		this.statusBarPanel = statusBarPanel;
		this.settingsPanel = settingsPanel;
		
		vObjects = new LinkedList<Shape>();
		
		addMouseListener(this);
		addMouseMotionListener(this);

		foreGroundColor = Color.BLACK;
		backGroundColor = Color.WHITE;
		setBackground(backGroundColor);

		redoStack = new LinkedList<Shape>();
		
		showPathMode = true;
		setDefaulsSettings();
	}

	public void mousePressed(MouseEvent event) {
		if (drawMode == DRAG) {
			for (int i = 0; i < vObjects.size(); i++) {
				Point p = ((Shape) vObjects.get(i)).contains(new Point(event.getX(), event.getY()), RADIUS);
				if (p != null) {
					currentShape = vObjects.get(i);
					currentDragPoint = p;
				}
			}
			if (currentDragPoint == null) {
				for (int i = 0; i < vObjects.size(); i++) {
					 if (((Shape) vObjects.get(i)).intersects(new Point(event.getX(), event.getY()), RADIUS)){
						currentShape = vObjects.get(i);
					 	
					 	baseX = event.getX();
					 	baseY = event.getY();
					 }	
				}
			}	
		}
		if (drawMode == FREE_HAND) {
			currentShape = new HandLine(event.getX(), event.getY(), foreGroundColor);
			vObjects.add(currentShape);
		}
		if (drawMode == LINE) {
			currentShape = new CleverLine(event.getX(), event.getY(), foreGroundColor);
			vObjects.add(currentShape);
		}
		if (drawMode == SQUARE) {
			currentShape = new Rectangle(event.getX(), event.getY(), foreGroundColor);
			vObjects.add(currentShape);
		}
		if (drawMode == OVAL) {
			currentShape = new Oval(event.getX(), event.getY(), foreGroundColor);
			vObjects.add(currentShape);
		}
		if (currentShape != null){
			settingsPanel.setSettings(currentShape.getAllSettings());
	 		settingsPanel.repaint();
		}	
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseMoved(MouseEvent event) {
		statusBarPanel.showStatus("x:" + event.getX() + ", y:" + event.getY());
	}

	public void mouseReleased(MouseEvent event) {
		if (drawMode == DRAG) {
			currentDragPoint = null;
			currentShape = null;
		}
		if (drawMode == FREE_HAND) {
			currentShape = null;
		}
		
		if (drawMode == POLYGON) {
			if (currentShape == null){
				currentShape = new Polygon(event.getX(), event.getY(), foreGroundColor);
				vObjects.add(currentShape);
			}
			else{
				((Polygon)currentShape).addPoint(event.getX(), event.getY());
			}
		}
		if (drawMode == CURVE_LINE_3P) {
			if (currentShape == null){
				currentShape = new CurveLine3Points(event.getX(), event.getY(), foreGroundColor);
				vObjects.add(currentShape);
			}
			else{
				((CurveLine3Points)currentShape).addPoint(event.getX(), event.getY());
			}
		}
		if (drawMode == CURVE_LINE_4P) {
			if (currentShape == null){
				currentShape = new CurveLine4Points(event.getX(), event.getY(), foreGroundColor);
				vObjects.add(currentShape);
			}
			else{
				((CurveLine4Points)currentShape).addPoint(event.getX(), event.getY());
			}
		}
		repaint();
	}

	public void mouseEntered(MouseEvent event) {
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void mouseExited(MouseEvent event) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mouseDragged(MouseEvent event) {

		if (drawMode == DRAG) {
			if (currentDragPoint != null) {
				currentDragPoint.move(event.getX(), event.getY());
			} else if (currentShape != null){
				int deltaX = event.getX() - baseX;
				int deltaY = event.getY() - baseY;
				currentShape.move(deltaX, deltaY);
				
				baseX = event.getX();
				baseY = event.getY();
			}
		}
		if (drawMode == LINE || drawMode == SQUARE || drawMode == OVAL) {
			currentShape.getPoints().get(1).move(event.getX(), event.getY());
		}
		if (drawMode == FREE_HAND) {
			((HandLine)currentShape).addPoint(event.getX(), event.getY());
		}
		repaint();
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null){
			g.drawImage(image, 0, 0, null);             
		}
		redrawVectorBuffer(g);
	}

	public void setDrawMode(int mode) {
		drawMode = mode;
	}

	public int getDrawMode() {
		return drawMode;
	}

	public void setForeGroundColor(Color inputColor) {
		foreGroundColor = inputColor;
	}

	public Color getForeGroundColor() {
		return foreGroundColor;
	}

	public void setBackGroundColor(Color inputColor) {
		backGroundColor = inputColor;
		this.setBackground(backGroundColor);
	}

	public Color getBackGroundColor() {
		return backGroundColor;
	}

	public void undo() {

		if (vObjects.isEmpty())
			JOptionPane.showMessageDialog(null, "Can't Undo", "Painter",
					JOptionPane.INFORMATION_MESSAGE);
		else {
			Shape o = vObjects.removeLast();
			redoStack.add(o);
		}
		repaint();
	}

	public void redo() {
		if (redoStack.isEmpty())
			JOptionPane.showMessageDialog(null, "Can't Redo", "Painter",
					JOptionPane.INFORMATION_MESSAGE);
		else {
			Shape o = redoStack.removeLast();
			vObjects.add(o);
		}
		repaint();
	}

	public void clearCanvas() {
		vObjects.clear();
		redoStack.clear();
		repaint();
	}

	public String getJSONView() throws JsonGenerationException, JsonMappingException, IOException{
		return getJSONView(true);
	}
	public String getJSONView(Boolean enableTyping) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
		if (enableTyping){
			mapper.enableDefaultTyping();
		}	
		String json = mapper.writeValueAsString(vObjects);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public void renderFromJSON(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
		mapper.enableDefaultTyping();
		vObjects = mapper.readValue(json, LinkedList.class);
		repaint();
	}
	
	public void flushDrawing() {
		currentShape = null;
		
		setDefaulsSettings();
		repaint();
	}

	public void releaseFocused(){
		for (Shape shape : vObjects) {
			shape.setFocus(false);
		}
	}
	/*
	private RenderedImage myCreateImage() {
		BufferedImage bufferedImage = new BufferedImage(1400,800, BufferedImage.TYPE_INT_RGB);

		Graphics g = bufferedImage.createGraphics();
		redrawVectorBuffer(g);

		g.dispose();
		return bufferedImage;
	}
	*/
	
	private void redrawVectorBuffer(Graphics g) {
		for (int i = 0; i < vObjects.size(); i++) {
			((Shape) vObjects.get(i)).draw(g);
			if (isShowPathMode())
				((Shape)vObjects.get(i)).drawPathPoints(g);
		}
	}

	public boolean isShowPathMode() {
		return showPathMode;
	}

	public void setShowPathMode(boolean showPathMode) {
		this.showPathMode = showPathMode;
		repaint();
	}

	public void zoomIn(){
        /*
		if(scale > 0.25)
            scale -= SCALE_STEP;
        System.out.println(getSize());
		setSize(new Dimension((int)(WIDTH * scale), (int)(HEIGHT * scale)));
		setPreferredSize(new Dimension((int)(WIDTH * scale), (int)(HEIGHT * scale)));
		*/
	}
	
	public void zoomOut(){
		/*
		scale += SCALE_STEP;
		System.out.println(getSize());	
		setSize(new Dimension((int)(WIDTH * scale), (int)(HEIGHT * scale)));
		setPreferredSize(new Dimension((int)(WIDTH * scale), (int)(HEIGHT * scale)));
		*/
	}
	
	public void setDefaulsSettings(){
		CanvasSettings canvasSettings = new CanvasSettings();
		canvasSettings.setHeight(this.getHeight());
		canvasSettings.setWidth(this.getWidth());
		
		settingsPanel.setSettings(canvasSettings);
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}