/**
 * 
 */
package com.spongeblob.paint.utils;


import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import com.spongeblob.paint.model.PhysicObject;
import com.spongeblob.paint.model.PhysicObjectType;
import com.spongeblob.paint.model.Polygon;
import com.spongeblob.paint.settings.PhysicsSettings;

/**
 * @author yurkech
 *
 */

public class CloneObjectTest {

	@Test
    public void testObjectClone(){
		PhysicObject po = new PhysicObject();
		po.setType(PhysicObjectType.BORDERTRACK);
		po.setPhysicsSettings(new PhysicsSettings(po));
		po.setShape(new Polygon(0, 0, Color.BLACK));
		
		PhysicObject copy = CloneObjectUtil.clone(po);
		Assert.assertEquals(po, copy);
	}
}
