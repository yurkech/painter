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

	private JButton dragBtn, lineBtn, squareBtn, ovalBtn, polygonBtn, curveLine3PBtn, curveLine4PBtn, freeHandBtn, undoBtn, redoBtn, clearBtn;		
	
	private JCheckBox solidChk, showPath;
	private CanvasPanel canvasPanel;
	
	public ToolButtonPanel(CanvasPanel inCanvasPanel)
	{
		canvasPanel = inCanvasPanel;
/*----------------------------------------------------------------------------*/	
		java.net.URL imgURL = getClass().getResource("lineBtn.gif");
		lineBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("squareBtn.gif");
		squareBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("ovalBtn.gif");
		ovalBtn	 		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("polygonBtn.gif");
		polygonBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("polygonBtn.gif");
		curveLine3PBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("polygonBtn.gif");
		curveLine4PBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("freeHandBtn.gif");
		freeHandBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("undoBtn.gif");
		undoBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("redoBtn.gif");
		redoBtn			= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("clearBtn.gif");
		clearBtn		= new JButton("",new ImageIcon(imgURL));
		imgURL = getClass().getResource("dragBtn.png");
		dragBtn			= new JButton("",new ImageIcon(imgURL));
		
		lineBtn.addActionListener(new ToolButtonListener());
		lineBtn.setToolTipText("Line");
		squareBtn.addActionListener(new ToolButtonListener());
		squareBtn.setToolTipText("Rectangle");
		ovalBtn.addActionListener(new ToolButtonListener());
		ovalBtn.setToolTipText("Oval");
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
		showPath = new JCheckBox("Show Path");
		showPath.setSelected(Boolean.TRUE);
		showPath.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent event)
				{
					if(showPath.isSelected())
						canvasPanel.setShowPathMode(Boolean.TRUE);
					else
						canvasPanel.setShowPathMode(Boolean.FALSE);
				}	
			}	
		);
/*----------------------------------------------------------------------------*/		
		this.setLayout(new GridLayout(1,20)); // 9 Buttons & 1 CheckBox
		this.add(dragBtn);
		this.add(lineBtn);
		this.add(squareBtn);
		this.add(ovalBtn);
		this.add(polygonBtn);
		this.add(curveLine3PBtn);
		this.add(curveLine4PBtn);
		this.add(freeHandBtn);
		this.add(undoBtn);
		this.add(redoBtn);
		this.add(clearBtn);
		this.add(solidChk);	
		this.add(showPath);
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
			if(event.getSource() == squareBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.SQUARE);
			}
			if(event.getSource() == ovalBtn)
			{
				canvasPanel.setDrawMode(CanvasPanel.OVAL);
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
			if(event.getSource() == clearBtn)
			{
				canvasPanel.clearCanvas();
			}
		}
	}
}
