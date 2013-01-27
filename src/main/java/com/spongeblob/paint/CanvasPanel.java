package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.*;

import com.spongeblob.paint.model.CurveLine3Points;
import com.spongeblob.paint.model.CurveLine4Points;
import com.spongeblob.paint.model.HandLine;
import com.spongeblob.paint.model.Line;
import com.spongeblob.paint.model.Oval;
import com.spongeblob.paint.model.Rectangle;
import com.spongeblob.paint.model.Polygon;
import com.spongeblob.paint.model.Shape;

public class CanvasPanel extends JPanel implements MouseListener,
		MouseMotionListener, Serializable {
	/**
	 * 
	 */
	protected final static int RADIUS = 10;
	protected final static double SCALE_STEP = 0.05;
	protected final static int WIDTH = 600;
	protected final static int HEIGHT = 600;
	

	private static final long serialVersionUID = -3371112021797757444L;
	protected final static int LINE = 1, SQUARE = 2, OVAL = 3, POLYGON = 4, CURVE_LINE_3P = 5, FREE_HAND = 6, DRAG = 7, CURVE_LINE_4P = 8;
	protected static Vector<Serializable> vFile;
	protected static LinkedList<Shape> vObjects, redoStack;

	private Color foreGroundColor, backGroundColor;

	private int drawMode = 0;
	private boolean solidMode, showPathMode;

	private Point currentDragPoint;
	private Shape currentShape;
	
    private double scale = 1.0;
	
	private File fileName;

	public CanvasPanel() {
		vObjects = new LinkedList<Shape>();
		vFile = new Vector<Serializable>();

		addMouseListener(this);
		addMouseMotionListener(this);

		solidMode = false;
		showPathMode = true;

		foreGroundColor = Color.BLACK;
		backGroundColor = Color.WHITE;
		setBackground(backGroundColor);
		setSize(WIDTH, HEIGHT);

		redoStack = new LinkedList<Shape>();
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	public void mousePressed(MouseEvent event) {
		if (drawMode == DRAG) {
			for (int i = 0; i < vObjects.size(); i++) {
				Point p = ((Shape) vObjects.get(i)).contains(new Point(event.getX(), event.getY()), RADIUS);
				if (p != null) {
					currentDragPoint = p;
				}
			}
		}
		if (drawMode == FREE_HAND) {
			currentShape = new HandLine(event.getX(), event.getY(), foreGroundColor);
			vObjects.add(currentShape);
		}
		if (drawMode == LINE) {
			currentShape = new Line(event.getX(), event.getY(), event.getX(), event.getY(), foreGroundColor);
			vObjects.add(currentShape);
		}
		if (drawMode == SQUARE) {
			currentShape = new Rectangle(event.getX(), event.getY(), event.getX(), event.getY(), foreGroundColor, solidMode);
			vObjects.add(currentShape);
		}
		if (drawMode == OVAL) {
			currentShape = new Oval(event.getX(), event.getY(), event.getX(), event.getY(), foreGroundColor, solidMode);
			vObjects.add(currentShape);
		}
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseMoved(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
		if (drawMode == DRAG) {
			currentDragPoint = null;
		}
		if (drawMode == FREE_HAND) {
			currentShape = null;
		}
		
		if (drawMode == POLYGON) {
			if (currentShape == null){
				currentShape = new Polygon(event.getX(), event.getY(), foreGroundColor, solidMode);
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

	/*----------------------------------------------------------------------------*/
	public void mouseEntered(MouseEvent event) {
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	/*----------------------------------------------------------------------------*/
	public void mouseExited(MouseEvent event) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/*----------------------------------------------------------------------------*/
	public void mouseDragged(MouseEvent event) {

		if (drawMode == DRAG) {
			if (currentDragPoint != null) {
				currentDragPoint.setLocation(event.getX(), event.getY());
			}
		}
		if (drawMode == LINE || drawMode == SQUARE || drawMode == OVAL) {
			currentShape.getPathPoints().get(1).setLocation(event.getX(), event.getY());
		}
		if (drawMode == FREE_HAND) {
			((HandLine)currentShape).addPoint(event.getX(), event.getY());
		}
		repaint();
	}

	/*----------------------------------------------------------------------------*/

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setSize((int)(WIDTH * scale), (int)(HEIGHT * scale));
		
		redrawVectorBuffer(g);
	}

	/*----------------------------------------------------------------------------*/
	public void setDrawMode(int mode) {
		drawMode = mode;
	}

	public int getDrawMode() {
		return drawMode;
	}

	/*----------------------------------------------------------------------------*/
	public void setSolidMode(Boolean inSolidMode) {
		solidMode = inSolidMode.booleanValue();
	}

	public Boolean getSolidMode() {
		return Boolean.valueOf(solidMode);
	}

	/*----------------------------------------------------------------------------*/
	public void setForeGroundColor(Color inputColor) {
		foreGroundColor = inputColor;
	}

	public Color getForeGroundColor() {
		return foreGroundColor;
	}

	/*----------------------------------------------------------------------------*/
	public void setBackGroundColor(Color inputColor) {
		backGroundColor = inputColor;
		this.setBackground(backGroundColor);
	}

	public Color getBackGroundColor() {
		return backGroundColor;
	}

	/*----------------------------------------------------------------------------*/
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

	/*----------------------------------------------------------------------------*/
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

	/*----------------------------------------------------------------------------*/
	public void clearCanvas() {
		vObjects.clear();
		redoStack.clear();
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	public void SaveCanvasToFile() {
		if (fileName != null) {
			vFile.removeAllElements();
			vFile.addElement(vObjects);
			vFile.addElement(new Color(backGroundColor.getRGB()));
			RenderedImage rendImage = myCreateImage();

			try {
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(vFile);
				JOptionPane.showMessageDialog(null, "File Saved", "Painter",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception exp) {
			}

			try {
				File file = new File(fileName.toString() + ".jpg");
				ImageIO.write(rendImage, "jpg", file);
			} catch (IOException e) {
			}
		} else {
			SaveAsCanvasToFile();
		}
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	public void SaveAsCanvasToFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(null);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		fileName = fileChooser.getSelectedFile();

		if (fileName == null || fileName.getName().equals(""))
			JOptionPane.showMessageDialog(null, "Invalid File Name", "Painter",
					JOptionPane.ERROR_MESSAGE);
		else {
			vFile.removeAllElements();
			vFile.addElement(vObjects);
			vFile.addElement(new Color(backGroundColor.getRGB()));

			RenderedImage rendImage = myCreateImage();
			
			ObjectOutputStream oos = null;
			FileOutputStream fos = null;
			File file = new File(fileName.toString() + ".vec");
			try {
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(vFile);
				JOptionPane.showMessageDialog(null, "File Saved", "Painter",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					oos.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			

			try {
				file = new File(fileName.toString() + ".jpg");
				ImageIO.write(rendImage, "jpg", file);
			} catch (IOException e) {
			}
		}
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public void OpenCanvasFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("VEC Images", "vec"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return;

		fileName = fileChooser.getSelectedFile();

		ObjectInputStream ois = null;
		if (fileName != null) {
			try {
				FileInputStream fis = new FileInputStream(fileName);
				ois = new ObjectInputStream(fis);
				vFile = (Vector<Serializable>) ois.readObject();

				this.clearCanvas();
				vObjects = (LinkedList<Shape>) vFile.elementAt(0);
				backGroundColor = (Color) vFile.elementAt(1);

				this.setBackground(backGroundColor);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Can't Open File",
						"Painter", JOptionPane.INFORMATION_MESSAGE);
			}finally{
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			fileName = null;
		}
		repaint();
	}

	public void flushDrawing() {
		if (drawMode == CanvasPanel.POLYGON) {
			if (currentShape != null)
				((Polygon)currentShape).setClosed(Boolean.TRUE);
		}
		currentShape = null;
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	private RenderedImage myCreateImage() {
		BufferedImage bufferedImage = new BufferedImage(1400,800, BufferedImage.TYPE_INT_RGB);

		Graphics g = bufferedImage.createGraphics();
		redrawVectorBuffer(g);

		g.dispose();
		return bufferedImage;
	}

	/*----------------------------------------------------------------------------*/
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
        if(scale > 0.25)
            scale -= SCALE_STEP;
        repaint();
	}
	
	public void zoomOut(){
		scale += SCALE_STEP;
        repaint();
	}
}