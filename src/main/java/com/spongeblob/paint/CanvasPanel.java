package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.imageio.*;

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

	private static final long serialVersionUID = -3371112021797757444L;
	protected final static int LINE = 1, SQUARE = 2, OVAL = 3, POLYGON = 4,
			FREE_HAND = 6, SOLID_POLYGON = 44,
			DRAG = 7;
	protected static Vector<Serializable> vFile;
	protected static LinkedList<Shape> vObjects, redoStack;

	private Color foreGroundColor, backGroundColor;

	private int x1, y1, x2, y2, drawMode = 0;
	private boolean solidMode;

	private Point currentDragPoint;
	private HandLine currentHandLine;
	private Polygon currentPolygon;

	private File fileName;

	public CanvasPanel() {
		vObjects = new LinkedList<Shape>();
		vFile = new Vector<Serializable>();

		addMouseListener(this);
		addMouseMotionListener(this);

		solidMode = false;

		foreGroundColor = Color.BLACK;
		backGroundColor = Color.WHITE;
		setBackground(backGroundColor);

		redoStack = new LinkedList<Shape>();

		repaint();
	}

	/*----------------------------------------------------------------------------*/
	public void mousePressed(MouseEvent event) {
		x1 = event.getX();
		y1 = event.getY();

		if (drawMode == CanvasPanel.FREE_HAND) {
			currentHandLine = new HandLine(x1, y1, foreGroundColor);
		}
	}

	/*----------------------------------------------------------------------------*/
	public void mouseClicked(MouseEvent event) {
		if (drawMode == CanvasPanel.POLYGON) {
			if (currentPolygon == null)
				currentPolygon = new Polygon(event.getX(), event.getY(), foreGroundColor, solidMode);
			else{
				currentPolygon.addPoint(event.getX(), event.getY());
				if (!vObjects.contains(currentPolygon)){
					vObjects.add(currentPolygon);
				}
				repaint();
			}
		}
	}

	public void mouseMoved(MouseEvent event) {
	}

	/*----------------------------------------------------------------------------*/
	public void mouseReleased(MouseEvent event) {
		if (drawMode == DRAG) {
			currentDragPoint = null;
		}
		if (drawMode == LINE) {
			vObjects.add(new Line(x1, y1, event.getX(), event.getY(),
					foreGroundColor));
		}
		if (drawMode == SQUARE) {
			if (x1 > event.getX() || y1 > event.getY()) {
				vObjects.add(new Rectangle(event.getX(), event.getY(), x1,
						y1, foreGroundColor, solidMode));
			} else {
				vObjects.add(new Rectangle(x1, y1, event.getX(), event
						.getY(), foreGroundColor, solidMode));
			}
		}
		if (drawMode == CanvasPanel.OVAL) {
			if (x1 > event.getX() || y1 > event.getY()) {
				vObjects.add(new Oval(event.getX(), event.getY(), x1, y1,
						foreGroundColor, solidMode));
			} else {
				vObjects.add(new Oval(x1, y1, event.getX(), event.getY(),
						foreGroundColor, solidMode));
			}
		}
		if (drawMode == CanvasPanel.FREE_HAND) {
			vObjects.add(currentHandLine);
			currentHandLine = null;
		}
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
		x2 = event.getX();
		y2 = event.getY();

		if (drawMode == CanvasPanel.DRAG) {
			if (currentDragPoint == null) {
				for (int i = 0; i < vObjects.size(); i++) {
					Point p = ((Shape) vObjects.get(i)).contains(new Point(x1,
							y1), RADIUS);
					if (p != null) {
						currentDragPoint = p;
					}
				}
			}
			if (currentDragPoint != null) {
				currentDragPoint.setLocation(x2, y2);
			}
		}
		if (drawMode == CanvasPanel.FREE_HAND) {
			currentHandLine.addPoint(x2, y2);
			x1 = x2;
			y1 = y2;
		}
		repaint();
	}

	/*----------------------------------------------------------------------------*/

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		redrawVectorBuffer(g);

		g.setColor(foreGroundColor);

		if (drawMode == LINE) {
			Line l = new Line(x1, y1, x2, y2, foreGroundColor);
			l.draw(g);
			l.drawPathPoints(g);
		}
		if (drawMode == OVAL) {
			Oval o = new Oval(x1, y1, x2, y2, foreGroundColor);
			if (solidMode) {
				o.setSolid(Boolean.TRUE);
			}
			o.draw(g);
			o.drawPathPoints(g);
		}
		if (drawMode == SQUARE) {
			Rectangle r = new Rectangle(x1, y1, x2, y2, foreGroundColor);
			if (solidMode) {
				r.setSolid(Boolean.TRUE);
			}
			r.draw(g);
			r.drawPathPoints(g);
		}
		if (drawMode == FREE_HAND) {
			if (currentHandLine != null){
				currentHandLine.draw(g);
				currentHandLine.drawPathPoints(g);
			}
		}

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
		}
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	public void OpenCanvasFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return;

		fileName = fileChooser.getSelectedFile();

		if (fileName != null) {
			try {
				FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				vFile = (Vector<Serializable>) ois.readObject();

				this.clearCanvas();
				vObjects = (LinkedList<Shape>) vFile.elementAt(1);
				backGroundColor = (Color) vFile.elementAt(10);

				this.setBackground(backGroundColor);
			} catch (Exception exp) {
				JOptionPane.showMessageDialog(null, "Can't Open File",
						"Painter", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			fileName = null;
		}
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	public boolean isDrawingPolygon() {
		return (currentPolygon != null);
	}

	/*----------------------------------------------------------------------------*/
	public void flushPolygonBuffer() {
		currentPolygon.setClosed(Boolean.TRUE);
		currentPolygon = null;
		repaint();
	}

	/*----------------------------------------------------------------------------*/
	private RenderedImage myCreateImage() {
		BufferedImage bufferedImage = new BufferedImage(600, 390,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = bufferedImage.createGraphics();
		redrawVectorBuffer(g);

		g.dispose();
		return bufferedImage;
	}

	/*----------------------------------------------------------------------------*/
	private void redrawVectorBuffer(Graphics g) {
		for (int i = 0; i < vObjects.size(); i++) {
			((Shape) vObjects.get(i)).draw(g);
			((Shape)vObjects.get(i)).drawPathPoints(g);
		}
	}

}