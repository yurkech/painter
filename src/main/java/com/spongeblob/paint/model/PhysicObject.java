package com.spongeblob.paint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.settings.PhysicsSettings;
import com.spongeblob.paint.settings.Settings;

public class PhysicObject implements Serializable {
	public static String PHYSIC_SETTINGS = "PHYSIC_SETTINGS";
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

	@SuppressWarnings("rawtypes")
	private Shape shape;
	private PhysicsSettings physicsSettings;

	public PhysicObject() {
		id = ID++;
	}

	@SuppressWarnings("rawtypes")
	public PhysicObject(Shape shape) {
		id = ID++;
		this.physicsSettings = new PhysicsSettings("Physics");
		this.shape = shape;
	}

	public PhysicObjectType getType() {
		return type;
	}

	public void setType(PhysicObjectType type) {
		this.type = type;
	}

	@SuppressWarnings("rawtypes")
	public Shape getShape() {
		return shape;
	}

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Map<String, Settings> getSettings() {
		Map<String, Settings> map = new HashMap<String, Settings>();
		map.put(PHYSIC_SETTINGS, getPhysicsSettings());
		map.putAll(getShape().getShapeSettings());
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((physicsSettings == null) ? 0 : physicsSettings.hashCode());
		result = prime * result + ((shape == null) ? 0 : shape.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhysicObject other = (PhysicObject) obj;
		if (id != other.id)
			return false;
		if (physicsSettings == null) {
			if (other.physicsSettings != null)
				return false;
		} else if (!physicsSettings.equals(other.physicsSettings))
			return false;
		if (shape == null) {
			if (other.shape != null)
				return false;
		} else if (!shape.equals(other.shape))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
