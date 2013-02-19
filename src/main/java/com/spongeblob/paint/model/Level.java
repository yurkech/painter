package com.spongeblob.paint.model;

import java.util.LinkedList;

public class Level {
	private LinkedList<PhysicObject> objects;
	private int width;
	private int height;

	public LinkedList<PhysicObject> getObjects() {
		return objects;
	}

	public void setObjects(LinkedList<PhysicObject> objects) {
		this.objects = objects;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
}
