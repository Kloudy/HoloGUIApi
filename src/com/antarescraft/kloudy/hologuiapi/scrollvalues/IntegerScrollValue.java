package com.antarescraft.kloudy.hologuiapi.scrollvalues;

/**
 * Represents an Integer ComponentValue type
 */
public class IntegerScrollValue extends AbstractScrollValue<Integer, Integer>
{	
	private boolean wrap;
	
	public IntegerScrollValue(Integer value, Integer step, Integer minValue, Integer maxValue)
	{
		this(value, step, minValue, maxValue, false);
	}
	
	public IntegerScrollValue(Integer value, Integer step, Integer minValue, Integer maxValue, boolean wrap)
	{
		super(value, step, minValue, maxValue);
		
		this.wrap = wrap;
	}
	
	@Override
	public IntegerScrollValue clone()
	{
		return new IntegerScrollValue(new Integer(value), new Integer(step), new Integer(minValue), new Integer(maxValue));
	}

	@Override
	public void increment()
	{
		value += step;
		if(value > maxValue)
		{
			if(wrap)
			{
				if(minValue != null)value = minValue.intValue();
			}
			else
			{
				if(maxValue != null)value = maxValue.intValue();
			}
		}
	}

	@Override
	public void decrement() 
	{
		value -= step;
		if(value < minValue)
		{
			if(wrap)
			{
				if(maxValue != null)value = maxValue.intValue();
			}
			else
			{
				if(minValue != null)value = minValue.intValue();
			}
		}
	}

	@Override
	public String toString()
	{
		return value.toString();
	}
}