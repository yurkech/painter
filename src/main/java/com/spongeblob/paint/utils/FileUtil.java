package com.spongeblob.paint.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUtil {
	
	public static String JPEG = "JPEG";
	public static String BMP = "BMP";
	public static String PNG = "PNG";
	public static String VEC = "VEC";
	public static String JSON = "JSON";
	
	
	public static String getFileExtension(File file){
		String extension = "";
		if (file != null){
			String fileName = file.getName();
			int i = fileName.lastIndexOf('.');
			if (i > 0) {
			    extension = fileName.substring(i+1);
			}
		}	
		return extension;
	}

	public static File openFile(){
		List<FileNameExtensionFilter> filters = new LinkedList<FileNameExtensionFilter>();
		filters.add(new FileNameExtensionFilter(JPEG, "jpeg", "jpg"));
		filters.add(new FileNameExtensionFilter(BMP, "bmp"));
		filters.add(new FileNameExtensionFilter(PNG, "png"));
		filters.add(new FileNameExtensionFilter(VEC, "vec"));
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		for (FileNameExtensionFilter fileNameExtensionFilter : filters) {
			fileChooser.addChoosableFileFilter(fileNameExtensionFilter);
		}

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return null;

		File vfile = fileChooser.getSelectedFile();
		
		if (vfile == null || vfile.getName().equals(""))
			JOptionPane.showMessageDialog(null, "Invalid File Name", "Painter",
					JOptionPane.ERROR_MESSAGE);
		return vfile;
	}
	
	public static File saveFile(){
		List<FileNameExtensionFilter> filters = new LinkedList<FileNameExtensionFilter>();
		filters.add(new FileNameExtensionFilter(VEC, "vec"));
		filters.add(new FileNameExtensionFilter(JSON, "json"));
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		for (FileNameExtensionFilter fileNameExtensionFilter : filters) {
			fileChooser.addChoosableFileFilter(fileNameExtensionFilter);
		}

		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return null;

		File vFile = fileChooser.getSelectedFile();
		
		if (vFile == null || vFile.getName().equals(""))
			JOptionPane.showMessageDialog(null, "Invalid File Name", "Painter",
					JOptionPane.ERROR_MESSAGE);
		if (FileUtil.getFileExtension(vFile).equals("vec") || (FileUtil.getFileExtension(vFile).equals("json"))){
			
		}else{
			String filterDescription = fileChooser.getFileFilter().getDescription();
			if (filterDescription.equals(VEC)){
				vFile = new File(vFile.toString() + ".vec");
			}
			if (filterDescription.equals(JSON)){
				vFile = new File(vFile.toString() + ".json");
			}
			
		}
		return vFile;
	}
}
