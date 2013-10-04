package com.spongeblob.paint;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.spongeblob.paint.model.Level;
import com.spongeblob.paint.model.PhysicObject;
import com.spongeblob.paint.model.point.MarkedPoint;
import com.spongeblob.paint.model.point.Marker;
import com.spongeblob.paint.model.point.Point;
import com.spongeblob.paint.model.shape.ComplexPolygon;
import com.spongeblob.paint.model.shape.CurveLine3Points;
import com.spongeblob.paint.model.shape.CurveLine4Points;
import com.spongeblob.paint.model.shape.HandLine;
import com.spongeblob.paint.model.shape.Line;
import com.spongeblob.paint.model.shape.Oval;
import com.spongeblob.paint.model.shape.Rectangle;
import com.spongeblob.paint.model.shape.Ruler;
import com.spongeblob.paint.model.shape.Shape;
import com.spongeblob.paint.model.shape.startrack.Road;
import com.spongeblob.paint.settings.CanvasSettings;
import com.spongeblob.paint.utils.CloneObjectUtil;
import com.spongeblob.paint.utils.PropertyFilteringModule;

public class CanvasPanel extends JPanel implements MouseListener, KeyListener,
		MouseMotionListener, Serializable {
	public static String CANVAS_SETTINGS = "CANVAS_SETTINGS";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3371112021797757444L;

	protected final static int RADIUS = 10;
	protected final static double SCALE_STEP = 0.1;

	private Image image;

	private StatusBarPanel statusBarPanel;
	private SettingsPanel settingsPanel;

	protected final static int LINE = 1, SQUARE = 2, OVAL = 3, POLYGON = 4,
			CURVE_LINE_3P = 5, FREE_HAND = 6, DRAG = 7, CURVE_LINE_4P = 8,
			RULER = 9, ROAD = 10;
	protected static LinkedList<PhysicObject> vObjects, redoStack;

	private Color foreGroundColor, backGroundColor;

	private int drawMode = 0;
	private boolean showPathMode;

	private Point currentDragPoint;
	private PhysicObject selectedObject;
	private int baseX, baseY;
	private float scale = 1;
	private boolean zoomIn, zoomOut = false;

	private int canvasWidth;
	private int canvasHeight;
	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	private Dimension canvasDimension;

	public CanvasPanel(StatusBarPanel statusBarPanel,
			SettingsPanel settingsPanel) {
		this.statusBarPanel = statusBarPanel;
		this.settingsPanel = settingsPanel;
		this.settingsPanel.setCanvasPanel(this);

		vObjects = new LinkedList<PhysicObject>();

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		foreGroundColor = Color.BLACK;
		backGroundColor = Color.WHITE;
		setBackground(backGroundColor);

		redoStack = new LinkedList<PhysicObject>();

		showPathMode = true;
	}

	@SuppressWarnings("rawtypes")
	public void mousePressed(MouseEvent event) {
		if (drawMode == DRAG) {
			for (int i = 0; i < vObjects.size(); i++) {
				Point p = ((Shape) vObjects.get(i).getShape())
						.getClosestControlPointInRadius(new Point(event.getX(),
								event.getY()), RADIUS);
				if (p != null) {
					selectedObject = vObjects.get(i);
					currentDragPoint = p;
				}
			}
			if (currentDragPoint == null) {
				for (int i = 0; i < vObjects.size(); i++) {
					if (((Shape) vObjects.get(i).getShape())
							.getClosestControlLineInRadius(
									new Point(event.getX(), event.getY()),
									RADIUS) >= 0) {
						selectedObject = vObjects.get(i);
					}
				}
			}
			baseX = event.getX();
			baseY = event.getY();
		}
		if (drawMode == FREE_HAND) {
			selectedObject = new PhysicObject(new HandLine(event.getX(),
					event.getY(), foreGroundColor));
			vObjects.add(selectedObject);
		}
		if (drawMode == LINE) {
			selectedObject = new PhysicObject(new Line(event.getX(),
					event.getY(), foreGroundColor));
			vObjects.add(selectedObject);
		}
		if (drawMode == SQUARE) {
			selectedObject = new PhysicObject(new Rectangle(event.getX(),
					event.getY(), foreGroundColor));
			vObjects.add(selectedObject);
		}
		if (drawMode == OVAL) {
			selectedObject = new PhysicObject(new Oval(event.getX(),
					event.getY(), foreGroundColor));
			vObjects.add(selectedObject);
		}
		if (selectedObject != null) {
			settingsPanel.activateSettings(selectedObject.getSettings());
			settingsPanel.repaint();
		}
		if (drawMode == RULER) {
			vObjects.add(new PhysicObject(new Ruler(event.getX(), event.getY(),
					foreGroundColor)));
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
			selectedObject = null;
		}
		if (drawMode == FREE_HAND) {
			selectedObject = null;
		}

		if (drawMode == POLYGON) {
			if (selectedObject == null) {
				selectedObject = new PhysicObject(new ComplexPolygon(
						event.getX(), event.getY(), foreGroundColor));
				vObjects.add(selectedObject);
			} else {
				((ComplexPolygon) selectedObject.getShape()).addPoint(
						event.getX(), event.getY());
			}
		}
		if (drawMode == ROAD) {
			if (selectedObject == null) {
				selectedObject = new PhysicObject(new Road(event.getX(),
						event.getY(), foreGroundColor));
				vObjects.add(selectedObject);
			} else {
				((Road) selectedObject.getShape()).addPoint(event.getX(),
						event.getY());
			}
		}
		if (drawMode == CURVE_LINE_3P) {
			if (selectedObject == null) {
				selectedObject = new PhysicObject(new CurveLine3Points(
						event.getX(), event.getY(), foreGroundColor));
				vObjects.add(selectedObject);
			} else {
				((CurveLine3Points) selectedObject.getShape())
						.getControlPoints().add(
								new Point(event.getX(), event.getY()));
			}
		}
		if (drawMode == CURVE_LINE_4P) {
			if (selectedObject == null) {
				selectedObject = new PhysicObject(new CurveLine4Points(
						event.getX(), event.getY(), foreGroundColor));
				vObjects.add(selectedObject);
			} else {
				((CurveLine4Points) selectedObject.getShape())
						.getControlPoints().add(
								new Point(event.getX(), event.getY()));
			}
		}
		if (drawMode == RULER) {
			vObjects.removeLast();
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
			} else if (selectedObject != null) {
				int deltaX = event.getX() - baseX;
				int deltaY = event.getY() - baseY;
				selectedObject.getShape().move(deltaX, deltaY);

				baseX = event.getX();
				baseY = event.getY();
			}
		}
		if (drawMode == LINE || drawMode == SQUARE || drawMode == OVAL) {
			((Point) selectedObject.getShape().getControlPoints().get(1)).move(
					event.getX(), event.getY());
		}
		if (drawMode == FREE_HAND) {
			((HandLine) selectedObject.getShape()).getControlPoints().add(
					new Point(event.getX(), event.getY()));
		}
		if (drawMode == RULER) {
			((Point) vObjects.getLast().getShape().getControlPoints().get(1))
					.move(event.getX(), event.getY());
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			if (zoomIn || zoomOut) {
				Image scaledImage = image.getScaledInstance((int) (getCanvasWidth() * scale),
						(int) (getCanvasHeight() * scale),
						Image.SCALE_FAST);
				setImage(scaledImage);
			}
			g.drawImage(image, 0, 0, null);
		}
		redrawVectorBuffer(g);
		zoomIn = false;
		zoomOut = false;
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
			PhysicObject o = vObjects.removeLast();
			redoStack.add(o);
		}
		repaint();
	}

	public void redo() {
		if (redoStack.isEmpty())
			JOptionPane.showMessageDialog(null, "Can't Redo", "Painter",
					JOptionPane.INFORMATION_MESSAGE);
		else {
			PhysicObject o = redoStack.removeLast();
			vObjects.add(o);
		}
		repaint();
	}

	public void clearCanvas() {
		vObjects.clear();
		redoStack.clear();
		repaint();
	}

	public String getJSONView() throws JsonGenerationException,
			JsonMappingException, IOException {
		return getJSONView(true);
	}

	public String getJSONView(Boolean enableTyping)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper;

		Level level = new Level();
		level.setObjects(vObjects);
		level.setWidth(getWidth());
		level.setHeight(getHeight());

		if (enableTyping) {
			mapper = getObjectMapper();
			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		} else {
			mapper = getFilteringObjectMapper();
		}
		return mapper.writeValueAsString(level);
	}

	public void renderFromJSON(String json) throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = getObjectMapper();
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		Level level = mapper.readValue(json, Level.class);
		vObjects = level.getObjects();
		repaint();
	}

	public void flushDrawing() {
		selectedObject = null;

		activateDefaulsSettings();
		repaint();
	}

	/*
	 * private RenderedImage myCreateImage() { BufferedImage bufferedImage = new
	 * BufferedImage(1400,800, BufferedImage.TYPE_INT_RGB);
	 * 
	 * Graphics g = bufferedImage.createGraphics(); redrawVectorBuffer(g);
	 * 
	 * g.dispose(); return bufferedImage; }
	 */

	@SuppressWarnings("rawtypes")
	private void redrawVectorBuffer(Graphics g) {
		for (int i = 0; i < vObjects.size(); i++) {
			if (zoomIn){
				((Shape) vObjects.get(i).getShape()).scale(1 + SCALE_STEP, 1 + SCALE_STEP);
			}
			if (zoomOut){
				((Shape) vObjects.get(i).getShape()).scale(1 - SCALE_STEP, 1 - SCALE_STEP);
			}
			((Shape) vObjects.get(i).getShape()).draw(g);
			if (isShowPathMode())
				((Shape) vObjects.get(i).getShape()).drawControlPoints(g);
		}
	}

	public boolean isShowPathMode() {
		return showPathMode;
	}

	public void setShowPathMode(boolean showPathMode) {
		this.showPathMode = showPathMode;
		repaint();
	}

	public void zoomIn() {
		scale += SCALE_STEP;
		System.out.println(getSize());
		setSize(new Dimension((int) (getCanvasWidth() * scale),
				(int) (getCanvasHeight() * scale)));
		setPreferredSize(new Dimension((int) (getCanvasWidth() * scale),
				(int) (getCanvasHeight() * scale)));
		zoomIn = true;
	}

	public void zoomOut() {
		if (scale > 0.1) {
			scale -= SCALE_STEP;
			System.out.println(getSize());
			setSize(new Dimension((int) (getCanvasWidth() * scale),
					(int) (getCanvasHeight() * scale)));
			setPreferredSize(new Dimension(
					(int) (getCanvasWidth() * scale), (int) (getCanvasHeight() * scale)));
			zoomOut = true;
		}
	}

	public void activateDefaulsSettings() {
		CanvasSettings canvasSettings = new CanvasSettings();
		canvasSettings.setHeight(this.getCanvasHeight());
		canvasSettings.setWidth(this.getCanvasWidth());
		
		settingsPanel.activateSettings(CANVAS_SETTINGS, canvasSettings);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	public void keyTyped(KeyEvent e) {
	}

	@SuppressWarnings("unchecked")
	public void keyPressed(KeyEvent e) {
		if (drawMode == DRAG) {
			/*
			 * Copy shape
			 */
			if (e.getKeyCode() == KeyEvent.VK_F5) {
				if (selectedObject != null) {
					PhysicObject copy = CloneObjectUtil.clone(selectedObject);
					//just a little shift to see new object on canvas
					copy.getShape().move(10, 10);
					//refresh id to avoid id duplications
					copy.refreshId();
					vObjects.add(copy);
				}
			}
			/*
			 * Delete point from the shape
			 */
			if (e.getKeyCode() == KeyEvent.VK_F8) {
				if (currentDragPoint != null) {
					if (JOptionPane
							.showConfirmDialog(null,
									"Really delete this point? Operation can not be undo!") == JOptionPane.OK_OPTION) {
						selectedObject.getShape().getControlPoints()
								.remove(currentDragPoint);
						if (selectedObject.getShape().getControlPoints()
								.isEmpty()) {
							vObjects.remove(selectedObject);
						}
					}
				}
			}
			/*
			 * Delete entire object
			 */
			if (e.getKeyCode() == KeyEvent.VK_F9) {
				if (selectedObject != null) {
					if (JOptionPane
							.showConfirmDialog(null,
									"Really delete this object? Operation can not be undo!") == JOptionPane.OK_OPTION) {
						vObjects.remove(selectedObject);
					}
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_F2) {
				Point p = new Point(baseX, baseY);
				if (currentDragPoint != null) {
					if (selectedObject.getShape() instanceof ComplexPolygon) {
						((MarkedPoint) currentDragPoint)
								.setMarker(Marker.L_POINT);
					}
				} else if (selectedObject != null) {
					int position = selectedObject.getShape()
							.getClosestControlLineInRadius(p, RADIUS);
					if (position >= 0)
						if (selectedObject.getShape() instanceof ComplexPolygon)
							selectedObject.getShape().getControlPoints()
									.add(position + 1, new MarkedPoint(p));
						else
							selectedObject.getShape().getControlPoints()
									.add(position + 1, p);
					else if (selectedObject.getShape() instanceof ComplexPolygon)
						selectedObject.getShape().getControlPoints()
								.add(new MarkedPoint(p));
					else
						selectedObject.getShape().getControlPoints().add(p);
				} else {
					PhysicObject o = vObjects.peekLast();
					if (o != null) {
						if (o.getShape() instanceof ComplexPolygon)
							o.getShape().getControlPoints()
									.add(new MarkedPoint(p));
						else
							o.getShape().getControlPoints().add(p);
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_F3) {
				Point p = new Point(baseX, baseY);
				if (currentDragPoint != null) {
					if (selectedObject.getShape() instanceof ComplexPolygon) {
						((MarkedPoint) currentDragPoint)
								.setMarker(Marker.CL3_POINT);
					}
				} else if (selectedObject != null) {
					int position = selectedObject.getShape()
							.getClosestControlLineInRadius(p, RADIUS);
					if (position >= 0)
						if (selectedObject.getShape() instanceof ComplexPolygon)
							selectedObject
									.getShape()
									.getControlPoints()
									.add(position + 1,
											new MarkedPoint(p, Marker.CL3_POINT));
						else if (selectedObject.getShape() instanceof ComplexPolygon)
							selectedObject.getShape().getControlPoints()
									.add(new MarkedPoint(p, Marker.CL3_POINT));
				} else {
					PhysicObject o = vObjects.peekLast();
					if (o != null) {
						if (o.getShape() instanceof ComplexPolygon)
							o.getShape().getControlPoints()
									.add(new MarkedPoint(p, Marker.CL3_POINT));
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_F4) {
				Point p = new Point(baseX, baseY);
				if (currentDragPoint != null) {
					if (selectedObject.getShape() instanceof ComplexPolygon) {
						((MarkedPoint) currentDragPoint)
								.setMarker(Marker.CL4_POINT);
					}
				} else if (selectedObject != null) {
					int position = selectedObject.getShape()
							.getClosestControlLineInRadius(p, RADIUS);
					if (position >= 0)
						if (selectedObject.getShape() instanceof ComplexPolygon)
							selectedObject
									.getShape()
									.getControlPoints()
									.add(position + 1,
											new MarkedPoint(p, Marker.CL4_POINT));
						else if (selectedObject.getShape() instanceof ComplexPolygon)
							selectedObject.getShape().getControlPoints()
									.add(new MarkedPoint(p, Marker.CL4_POINT));
				} else {
					PhysicObject o = vObjects.peekLast();
					if (o != null) {
						if (o.getShape() instanceof ComplexPolygon)
							o.getShape().getControlPoints()
									.add(new MarkedPoint(p, Marker.CL4_POINT));
					}
				}
			}
			repaint();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	private static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	private static ObjectMapper getFilteringObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(PropertyFilteringModule.builder("Export Module")
				.exclude(MarkedPoint.class, "marker")
				.exclude(PhysicObject.class, "id")
				.exclude(ComplexPolygon.class, "color")
				.exclude(ComplexPolygon.class, "solid").build());
		return mapper;
	}

	public Dimension getCanvasDimension() {
		return canvasDimension;
	}

	public void setCanvasDimension(Dimension canvasDimension) {
		this.canvasDimension = canvasDimension;
	}

	public int getCanvasWidth() {
		return canvasWidth;
	}

	public void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	public void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}

}