package com.antarescraft.kloudy.hologuiapi.scrollvalues;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Represents a Double ComponentValue type
 */

public class DoubleScrollValue extends AbstractScrollValue<Double, Double>
{
	private String decimalFormat;
	private boolean wrap;
	
	public DoubleScrollValue(Double value, Double step, Double minValue, Double maxValue, String decimalFormat)
	{
		this(value, step, minValue, maxValue, decimalFormat, false);
	}
	
	public DoubleScrollValue(Double value, Double step, Double minValue, Double maxValue, String decimalFormat, boolean wrap)
	{
		super(value, step, minValue, maxValue);
		
		this.decimalFormat = decimalFormat;
		this.wrap = wrap;
	}
	
	@Override
	public DoubleScrollValue clone()
	{
		return new DoubleScrollValue(new Double(value), new Double(step), new Double(minValue), new Double(maxValue), decimalFormat);
	}

	@Override
	public void increment()
	{
		value += step;
		if(value > maxValue)
		{
			if(wrap)
			{
				if(minValue != null)value = minValue.doubleValue();
			}
			else
			{
				if(maxValue != null)value = maxValue.doubleValue();
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
				if(maxValue != null)value = maxValue.doubleValue();
			}
			else
			{
				if(minValue != null)value = minValue.doubleValue();
			}
		}
	}

	@Override
	public String toString()
	{
		DecimalFormat decimal = new DecimalFormat(decimalFormat);
		decimal.setRoundingMode(RoundingMode.HALF_UP);
		value = Double.parseDouble(decimal.format(value));
		 
		return value.toString();
	}
}