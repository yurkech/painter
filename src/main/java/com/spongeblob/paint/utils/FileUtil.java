package com.spongeblob.paint.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileUtil {
	
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

	public static File chooseFile(){
		File vfile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG file", "jpeg", "jpg"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("BMP file", "bmp"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG file", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("VEC file", "vec"));

		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return null;

		vfile = fileChooser.getSelectedFile();
		
		if (vfile == null || vfile.getName().equals(""))
			JOptionPane.showMessageDialog(null, "Invalid File Name", "Painter",
					JOptionPane.ERROR_MESSAGE);
		return vfile;
	}
}
