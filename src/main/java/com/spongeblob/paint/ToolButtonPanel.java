package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ToolButtonPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917208796965969921L;

	private JButton dragBtn, lineBtn, polygonBtn, curveLine3PBtn, curveLine4PBtn, 
			freeHandBtn, undoBtn, redoBtn, zoomInBtn, zoomOutBtn, clearBtn;		
	
	private JCheckBox solidChk;
	private CanvasPanel canvasPanel;
	
	private JButton foreGroundColorBtn,backGroundColorBtn;
	private Color foreColor, backColor;
	
	public ToolButtonPanel(CanvasPanel inCanvasPanel)
	{
		canvasPanel = inCanvasPanel;
		
/*----------------------------------------------------------------------------*/	
		java.net.URL imgURL = getClass().getResource("stock_draw_line.png");
		lineBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("stock_draw_polygon_filled.png");
		polygonBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("line.png");
		curveLine3PBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("line.png");
		curveLine4PBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("stock_draw_freeform_line.png");
		freeHandBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("undo.png");
		undoBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("redo.png");
		redoBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("zoom_in.png");
		zoomInBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("zoom_out.png");
		zoomOutBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("eraser.png");
		clearBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("cursor_drag_arrow_icon.png");
		dragBtn			= new JButton("",new ImageIcon(imgURL));
		
		lineBtn.addActionListener(new ToolButtonListener());
		lineBtn.setToolTipText("Line");
		polygonBtn.addActionListener(new ToolButtonListener());
		polygonBtn.setToolTipText("Polygon");
		curveLine3PBtn.addActionListener(new ToolButtonListener());
		curveLine3PBtn.setToolTipText("CurveLine 3-Points");
		curveLine4PBtn.addActionListener(new ToolButtonListener());
		curveLine4PBtn.setToolTipText("CurveLine 4-Points");
		freeHandBtn.addActionListener(new ToolButtonListener());
		freeHandBtn.setToolTipText("Free Hand");
		undoBtn.addActionListener(new ToolButtonListener());
		undoBtn.setToolTipText("Undo");
		redoBtn.addActionListener(new ToolButtonListener());
		redoBtn.setToolTipText("Redo");
		zoomInBtn.addActionListener(new ToolButtonListener());
		zoomInBtn.setToolTipText("Zoom In");
		zoomOutBtn.addActionListener(new ToolButtonListener());
		zoomOutBtn.setToolTipText("ZoomOut");
		clearBtn.addActionListener(new ToolButtonListener());
		clearBtn.setToolTipText("Clear Canvas");
		dragBtn.addActionListener(new ToolButtonListener());
		dragBtn.setToolTipText("Drag");
/*----------------------------------------------------------------------------*/		
		solidChk = new JCheckBox("Solid");
		solidChk.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent event)
				{
					if(solidChk.isSelected())
						canvasPanel.setSolidMode(Boolean.TRUE);
					else
						canvasPanel.setSolidMode(Boolean.FALSE);
				}	
			}
		);	
		
		
		foreGroundColorBtn = new JButton();
		foreGroundColorBtn.setOpaque(true);
		foreGroundColorBtn.setBackground(canvasPanel.getForeGroundColor());
		foreGroundColorBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		foreGroundColorBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					setForeGroundColor();
				}
			}
		);
		
		backGroundColorBtn = new JButton();
		backGroundColorBtn.setOpaque(true);
		backGroundColorBtn.setBackground(canvasPanel.getBackGroundColor());
		backGroundColorBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		backGroundColorBtn.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					setBackGroundColor();
				}
			}
		);
	
		
/*----------------------------------------------------------------------------*/		
		this.setLayout(new GridLayout(1,20)); // 9 Buttons & 1 CheckBox
		this.add(dragBtn);
		this.add(lineBtn);
		this.add(polygonBtn);
		this.add(curveLine3PBtn);
		this.add(curveLine4PBtn);
		this.add(freeHandBtn);
		this.add(undoBtn);
		this.add(redoBtn);
		this.add(zoomInBtn);
		this.add(zoomOutBtn);
		this.add(clearBtn);
		this.add(solidChk);	
		this.add(foreGroundColorBtn);
		this.add(backGroundColorBtn);
	}
/*----------------------------------------------------------------------------*/
	private class ToolButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{	
			canvasPanel.flushDrawing();
			
			if(event.getSource() == lineBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.LINE);		
			}
			if(event.getSource() == polygonBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.POLYGON);
			}
			if(event.getSource() == curveLine3PBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.CURVE_LINE_3P);
			}
			if(event.getSource() == curveLine4PBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.CURVE_LINE_4P);
			}
			if(event.getSource() == freeHandBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.FREE_HAND);
			}
			if(event.getSource() == dragBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.DRAG);
			}
			if(event.getSource() == undoBtn)
			{
				canvasPanel.undo();
			}
			if(event.getSource() == redoBtn)
			{
				canvasPanel.redo();
			}
			if(event.getSource() == zoomInBtn)
			{
				canvasPanel.zoomIn();
				canvasPanel.repaint();
			}
			if(event.getSource() == zoomOutBtn)
			{
				canvasPanel.zoomOut();
				canvasPanel.repaint();
			}
			if(event.getSource() == clearBtn)
			{
				canvasPanel.clearCanvas();
				
			}
		}
	}
/*----------------------------------------------------------------------------*/	
	public void setForeGroundColor()
	{
		foreColor = JColorChooser.showDialog(null,"ForeGround Color",foreColor);
		if(foreColor!=null)
		{
			foreGroundColorBtn.setBackground(foreColor);
			canvasPanel.setForeGroundColor(foreColor);
		}
	}
/*----------------------------------------------------------------------------*/
	public void setBackGroundColor()
	{
		backColor = JColorChooser.showDialog(null,"BackGround Color",backColor);
		if(backColor!=null)
		{
			backGroundColorBtn.setBackground(backColor);
			canvasPanel.setBackGroundColor(backColor);
		}
	}
}
