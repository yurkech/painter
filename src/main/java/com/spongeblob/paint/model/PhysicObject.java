package com.spongeblob.paint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spongeblob.paint.model.shape.Shape;
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
	private double density = 1.0;
	private int restriction = 0;
	private int friction = 0;

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
		this.physicsSettings = new PhysicsSettings(this);
	}
	
	public void refreshId(){
		id = ID++;
	}

	@SuppressWarnings("rawtypes")
	public PhysicObject(Shape shape) {
		id = ID++;
		this.shape = shape;

		this.physicsSettings = new PhysicsSettings(this);
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
	@JsonIgnore
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

	/**
	 * @return the density
	 */
	public double getDensity() {
		return density;
	}

	/**
	 * @param density
	 *            the density to set
	 */
	public void setDensity(double density) {
		this.density = density;
	}

	/**
	 * @return the restriction
	 */
	public int getRestriction() {
		return restriction;
	}

	/**
	 * @param restriction
	 *            the restriction to set
	 */
	public void setRestriction(int restriction) {
		this.restriction = restriction;
	}

	/**
	 * @return the friction
	 */
	public int getFriction() {
		return friction;
	}

	/**
	 * @param friction
	 *            the friction to set
	 */
	public void setFriction(int friction) {
		this.friction = friction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(density);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + friction;
		result = prime * result + id;
		result = prime * result + restriction;
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
		if (Double.doubleToLongBits(density) != Double
				.doubleToLongBits(other.density))
			return false;
		if (friction != other.friction)
			return false;
		if (id != other.id)
			return false;
		if (restriction != other.restriction)
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
