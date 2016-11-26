package com.antarescraft.kloudy.hologuiapi.scrollvalues;

/**
 * Represents an abstract value that can be contained in a value scroller
 * 
 * <T => Value type, K => Step type>
 */

public abstract class AbstractScrollValue <T, K>
{
	protected T value;
	protected K step;
	protected T minValue;
	protected T maxValue;
	
	public AbstractScrollValue(T value, K step, T minValue, T maxValue)
	{
		this.value = value;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;
	}	
	
	public abstract void increment();
	public abstract void decrement();
	public abstract String toString();
	public abstract AbstractScrollValue<?, ?> clone();
	
	public K getStep()
	{
		return step;
	}
	
	public void setStep(K step)
	{
		this.step = step;
	}
	
	public T getMinValue()
	{
		return minValue;
	}
	
	public void setMinValue(T minValue)
	{
		this.minValue = minValue;
	}
	
	public T getMaxValue()
	{
		return maxValue;
	}
	
	public void setMaxValue(T maxValue)
	{
		this.maxValue = maxValue;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public void setValue(T value)
	{
		this.value = value;
	}
}