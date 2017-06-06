package com.brashmonkey.spriter;

import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.signum;

/**
 * Represents a bone in a Spriter SCML file. A bone holds a {@link #position}, {@link #scale}, an {@link #angle} and a
 * {@link #pivot}. Bones are the only objects which can be used as a parent for other tweenable objects.
 *
 * @author Trixt0r
 */
public class SpriterObject
{
	public final Vector2 position, scale, pivot;
	public float angle;

	/**
	 * Constructor for root
	 */
	public SpriterObject()
	{
		this(new Vector2(), new Vector2(1, 1), new Vector2(0, 1), 0);
	}

	public SpriterObject(SpriterObject other)
	{
		this.position = new Vector2(other.position);
		this.scale = new Vector2(other.scale);
		this.pivot = new Vector2(other.pivot);
		this.angle = other.angle;
	}

	public SpriterObject(Vector2 position, Vector2 scale, Vector2 pivot, float angle)
	{
		this.position = position;
		this.scale = scale;
		this.angle = angle;
		this.pivot = pivot;
	}

	/**
	 * Sets the values of this object to the values of the given object
	 *
	 * @param object the object
	 */
	public void set(SpriterObject object)
	{
		this.angle = object.angle;
		this.position.set(object.position.x, object.position.y);
		this.scale.set(object.scale.x, object.scale.y);
		this.pivot.set(object.pivot.x, object.pivot.y);
	}

	/**
	 * Maps this bone from it's parent's coordinate system to a global one.
	 *
	 * @param parent the parent bone of this bone
	 */
	public void unmap(SpriterObject parent)
	{
		this.angle *= signum(parent.scale.x) * signum(parent.scale.y);
		this.angle += parent.angle;
		this.scale.scl(parent.scale);
		this.position.scl(parent.scale);
		this.position.rotate(parent.angle);
		this.position.add(parent.position);
	}

	@Override
	public SpriterObject clone()
	{
		try
		{
			return getClass().getConstructor(getClass()).newInstance(this);
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public boolean isFlippedX()
	{
		return scale.x < 0;
	}

	public boolean isFlippedY()
	{
		return scale.y < 0;
	}

	public void setFlippedX(boolean flip)
	{
		if(flip ^ isFlippedX())
			scale.x *= -1;
	}

	public void setFlippedY(boolean flip)
	{
		if(flip ^ isFlippedY())
			scale.y *= -1;
	}

	public void setFlipped(boolean x, boolean y)
	{
		setFlippedX(x);
		setFlippedY(y);
	}

	public void setScale(float scale)
	{
		this.scale.x = scale;
		this.scale.y = scale;
	}

}