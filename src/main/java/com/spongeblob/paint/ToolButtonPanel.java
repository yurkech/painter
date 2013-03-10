package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ToolButtonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917208796965969921L;

	private String[] instruments = { "Drag", "Line", "Oval", "Road", "Polygon",
			"CurveLine 3-Points", "CurveLine 4-Points", "Free Hand", "Undo",
			"Redo", "Zoom In", "Zoom Out", "Clear Canvas", "Ruler" };
	private String[] instrumentIcons = { "cursor_drag_arrow_icon.png",
			"stock_draw_line.png", "ovalBtn.gif",
			"stock_draw_polygon_filled.png", "stock_draw_polygon_filled.png",
			"line.png", "line.png", "stock_draw_freeform_line.png", "undo.png",
			"redo.png", "zoom_in.png", "zoom_out.png", "eraser.png",
			"rulers.png" };

	private CanvasPanel canvasPanel;

	private JButton foreGroundColorBtn, backGroundColorBtn;
	private Color foreColor, backColor;

	public ToolButtonPanel(CanvasPanel panel) {
		this.canvasPanel = panel;
		this.setLayout(new GridLayout(1, 20));

		for (int i = 0; i < instruments.length; i++) {
			JButton b = new JButton("", new ImageIcon(getClass().getResource(
					instrumentIcons[i])));
			b.setName(instruments[i]);
			b.setToolTipText(instruments[i]);
			b.addActionListener(new ToolButtonListener());

			this.add(b);
		}

		foreGroundColorBtn = new JButton();
		foreGroundColorBtn.setOpaque(true);
		foreGroundColorBtn.setBackground(canvasPanel.getForeGroundColor());
		foreGroundColorBtn.setBorder(BorderFactory
				.createLineBorder(Color.BLACK));
		foreGroundColorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setForeGroundColor();
			}
		});

		backGroundColorBtn = new JButton();
		backGroundColorBtn.setOpaque(true);
		backGroundColorBtn.setBackground(canvasPanel.getBackGroundColor());
		backGroundColorBtn.setBorder(BorderFactory
				.createLineBorder(Color.WHITE));
		backGroundColorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setBackGroundColor();
			}
		});

		this.add(foreGroundColorBtn);
		this.add(backGroundColorBtn);
	}

	/*----------------------------------------------------------------------------*/
	private class ToolButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			canvasPanel.flushDrawing();
			canvasPanel.addNotify();

			if (((JButton) event.getSource()).getName().equals("Line")) {
				canvasPanel.setDrawMode(CanvasPanel.LINE);
			}
			if (((JButton) event.getSource()).getName().equals("Oval")) {
				canvasPanel.setDrawMode(CanvasPanel.OVAL);
			}
			if (((JButton) event.getSource()).getName().equals("Polygon")) {
				canvasPanel.setDrawMode(CanvasPanel.POLYGON);
			}
			if (((JButton) event.getSource()).getName().equals("Road")) {
				canvasPanel.setDrawMode(CanvasPanel.ROAD);
			}
			if (((JButton) event.getSource()).getName().equals(
					"CurveLine 3-Points")) {
				canvasPanel.setDrawMode(CanvasPanel.CURVE_LINE_3P);
			}
			if (((JButton) event.getSource()).getName().equals(
					"CurveLine 4-Points")) {
				canvasPanel.setDrawMode(CanvasPanel.CURVE_LINE_4P);
			}
			if (((JButton) event.getSource()).getName().equals("Free Hand")) {
				canvasPanel.setDrawMode(CanvasPanel.FREE_HAND);
			}
			if (((JButton) event.getSource()).getName().equals("Drag")) {
				canvasPanel.setDrawMode(CanvasPanel.DRAG);
			}
			if (((JButton) event.getSource()).getName().equals("Undo")) {
				canvasPanel.undo();
			}
			if (((JButton) event.getSource()).getName().equals("Redo")) {
				canvasPanel.redo();
			}
			if (((JButton) event.getSource()).getName().equals("Zoom In")) {
				canvasPanel.zoomIn();
				canvasPanel.repaint();
			}
			if (((JButton) event.getSource()).getName().equals("Zoom Out")) {
				canvasPanel.zoomOut();
				canvasPanel.repaint();
			}
			if (((JButton) event.getSource()).getName().equals("Clear Canvas")) {
				canvasPanel.clearCanvas();
			}
			if (((JButton) event.getSource()).getName().equals("Ruler")) {
				canvasPanel.setDrawMode(CanvasPanel.RULER);
			}
		}
	}

	/*----------------------------------------------------------------------------*/
	public void setForeGroundColor() {
		foreColor = JColorChooser.showDialog(null, "ForeGround Color",
				foreColor);
		if (foreColor != null) {
			foreGroundColorBtn.setBackground(foreColor);
			canvasPanel.setForeGroundColor(foreColor);
		}
	}

	/*----------------------------------------------------------------------------*/
	public void setBackGroundColor() {
		backColor = JColorChooser.showDialog(null, "BackGround Color",
				backColor);
		if (backColor != null) {
			backGroundColorBtn.setBackground(backColor);
			canvasPanel.setBackGroundColor(backColor);
		}
	}
}
