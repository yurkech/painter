package com.spongeblob.paint.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.settings.PhysicsSettings;
import com.spongeblob.paint.settings.Settings;

public class PhysicObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7311103560094655035L;
	private static int ID = 0;
	
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private PhysicObjectType type = PhysicObjectType.BORDERTRACK;
	
	private Shape shape;
	private PhysicsSettings physicsSettings;
	
	public PhysicObject() {
		id = ID++;
	}
	
	public PhysicObject(Shape shape) {
		id = ID++;
		this.physicsSettings = new PhysicsSettings();
		this.shape = shape;
	}
	
	public PhysicObjectType getType() {
		return type;
	}
	public void setType(PhysicObjectType type) {
		this.type = type;
	}
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@JsonProperty(value = "phys")
	public PhysicsSettings getPhysicsSettings() {
		return physicsSettings;
	}

	public void setPhysicsSettings(PhysicsSettings physicsSettings) {
		this.physicsSettings = physicsSettings;
	}

	@JsonIgnore
	public List<Settings> getSettings() {
		LinkedList<Settings> list = new LinkedList<Settings>();
		list.add(getPhysicsSettings());
		list.addAll(getShape().getSettings());
		return list;
	}
}
