package com.spongeblob.paint;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Painter extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7100427091085957843L;
	private CanvasPanel 		canvasPanel;
	private ToolButtonPanel   	toolButtonPanel;
	
	private Container 			mainContainer;
	private File file;
	
	JMenuBar mainBar;
	JMenu fileMenu, editMenu, setColorMenuItem, aboutMenu, viewMenu;
	JMenuItem newMenuItem, openMenuItem, closeMenuItem, 
			saveMenuItem, saveAsMenuItem, exitMenuItem, undoMenuItem, 
			redoMenuItem, foreGroundMenuItem, backGroundMenuItem, 
			authorMenuItem, showControlPointMenuItem;
	
	public Painter()
	{
		super("Painter");
		
		mainBar 		= new JMenuBar();
		setJMenuBar(mainBar);
/*----------------------------------------------------------------------------*/		
		fileMenu  		= new JMenu("File");
		fileMenu.setMnemonic('F');
		
		newMenuItem		= new JMenuItem("New");
		openMenuItem 	= new JMenuItem("Open");
		closeMenuItem 	= new JMenuItem("Close"); 
		saveMenuItem 	= new JMenuItem("Save");
		saveAsMenuItem 	= new JMenuItem("Save As");
		exitMenuItem	= new JMenuItem("Exit");
		
		newMenuItem.addActionListener(new MenuButtonListener());
		openMenuItem.addActionListener(new MenuButtonListener());
		saveMenuItem.addActionListener(new MenuButtonListener());
		saveAsMenuItem.addActionListener(new MenuButtonListener());
		closeMenuItem.addActionListener(new MenuButtonListener());
		exitMenuItem.addActionListener(new MenuButtonListener());
		
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
/*----------------------------------------------------------------------------*/			
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		
		undoMenuItem	   = new JMenuItem("Undo");
		redoMenuItem 	   = new JMenuItem("Redo");
		
		setColorMenuItem   = new JMenu("Set Color");
		foreGroundMenuItem = new JMenuItem("Set ForeGround");
		backGroundMenuItem = new JMenuItem("Set BackGround");
		
		undoMenuItem.addActionListener(new MenuButtonListener());
		redoMenuItem.addActionListener(new MenuButtonListener());
		foreGroundMenuItem.addActionListener(new MenuButtonListener());
		backGroundMenuItem.addActionListener(new MenuButtonListener());
		
		setColorMenuItem.add(foreGroundMenuItem);
		setColorMenuItem.add(backGroundMenuItem);
		
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		editMenu.addSeparator();
		editMenu.add(setColorMenuItem);
/*----------------------------------------------------------------------------*/			
		viewMenu	= new JMenu("View");
		viewMenu.setMnemonic('V');
		
		showControlPointMenuItem = new JMenuItem("Hide Control Points");
		showControlPointMenuItem.addActionListener(new MenuButtonListener());
		
		viewMenu.add(showControlPointMenuItem);
/*----------------------------------------------------------------------------*/			
		aboutMenu	= new JMenu("About");
		aboutMenu.setMnemonic('A');
		
		authorMenuItem = new JMenuItem("Author");
		authorMenuItem.addActionListener(new MenuButtonListener());
		
		aboutMenu.add(authorMenuItem);
/*----------------------------------------------------------------------------*/				
		mainBar.add(fileMenu);
		mainBar.add(editMenu);
		mainBar.add(viewMenu);
		mainBar.add(aboutMenu);
/*----------------------------------------------------------------------------*/
		canvasPanel 	  = new CanvasPanel();
		JScrollPane scrollCanvasPanel = new JScrollPane(canvasPanel);
		scrollCanvasPanel.setPreferredSize(new Dimension(getWidth(),getHeight()));
		scrollCanvasPanel.setAutoscrolls(true);
		
		toolButtonPanel   = new ToolButtonPanel(canvasPanel);
		
		mainContainer = getContentPane();
		mainContainer.add(toolButtonPanel,BorderLayout.NORTH);
		mainContainer.add(scrollCanvasPanel,BorderLayout.CENTER);
		
		addWindowListener (
      		new WindowAdapter () 
      		{
      			public void windowClosing (WindowEvent e) 
      			{
      				System.exit(0);
      			}
      			public void windowDeiconified (WindowEvent e) 
      			{
      				canvasPanel.repaint();
      			}
      			public void windowActivated (WindowEvent e) 
      			{	 
      				canvasPanel.repaint();
      			}
      		}
      	);
	}
/*----------------------------------------------------------------------------*/	
	public class MenuButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == newMenuItem || event.getSource() == closeMenuItem)
			{
				canvasPanel.clearCanvas();
				canvasPanel.setDrawMode(0);
				canvasPanel.setForeGroundColor(Color.BLACK);
				canvasPanel.setBackGroundColor(Color.WHITE);
				canvasPanel.repaint();
			}
			if(event.getSource() == exitMenuItem)
			{
				System.exit(0);
			}
			if(event.getSource() == foreGroundMenuItem)
			{
				toolButtonPanel.setForeGroundColor();
				canvasPanel.repaint();
			}
			if(event.getSource() == backGroundMenuItem)
			{
				toolButtonPanel.setBackGroundColor();
				canvasPanel.repaint();
			}
			if(event.getSource() == authorMenuItem)
			{
				JOptionPane.showMessageDialog(Painter.this,"Author : Yurkech","Painter",JOptionPane.INFORMATION_MESSAGE);
				canvasPanel.repaint();
			}
			if(event.getSource() == saveMenuItem)
			{
				if (file == null || file.getName().equals("")){
					chooseFile();
				}
				try {
					if (file != null)
						writeJSONToFile(canvasPanel.getJSONView());
				} catch (IOException e) {
					file = null;
					JOptionPane.showMessageDialog(null, e, "Painter",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if(event.getSource() == saveAsMenuItem)
			{
				chooseFile();
				try {
					if (file != null)
						writeJSONToFile(canvasPanel.getJSONView());
				} catch (IOException e) {
					file = null;
					JOptionPane.showMessageDialog(null, e, "Painter",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if(event.getSource() == openMenuItem)
			{
				chooseFile();
				try {
					if (file != null)
							canvasPanel.renderFromJSON(readJSONFromFile());
				} catch (IOException e) {
					file = null;
					JOptionPane.showMessageDialog(null, e, "Painter",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if(event.getSource() == undoMenuItem)
			{
				canvasPanel.undo();
			}
			if(event.getSource() == redoMenuItem)
			{
				canvasPanel.redo();
			}
			if(event.getSource() == showControlPointMenuItem)
			{
				if (showControlPointMenuItem.getText().equals("Show Control Points")){
					showControlPointMenuItem.setText("Hide Control Points");
					canvasPanel.setShowPathMode(true);
				} else if (showControlPointMenuItem.getText().equals("Hide Control Points")){
					showControlPointMenuItem.setText("Show Control Points");
					canvasPanel.setShowPathMode(false);
				}	
			}
		}
	}
	
	void chooseFile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.vec", "vec"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return;

		file = fileChooser.getSelectedFile();
		
		if (file == null || file.getName().equals(""))
			JOptionPane.showMessageDialog(null, "Invalid File Name", "Painter",
					JOptionPane.ERROR_MESSAGE);
	}
	
	void writeJSONToFile(String json) throws IOException{
		FileWriter fw = new FileWriter(file);
		fw.write(json);
		JOptionPane.showMessageDialog(null, "File Saved", "Painter",
				JOptionPane.INFORMATION_MESSAGE);
		fw.close();
	}
	
	private String readJSONFromFile() throws IOException{
		StringBuilder res = new StringBuilder();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while((s = br.readLine()) != null) {
			res.append(s);
		}
		fr.close();
		return res.toString();
	}
	
	public static void main(String args[])
	{
		Painter f = new Painter();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800,800);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}
}
