package com.antarescraft.kloudy.hologuiapi.scrollvalues;

import java.time.Duration;

import com.antarescraft.kloudy.plugincore.time.TimeFormat;

/**
 * Represents a Duration ComponentValue in the form "hh:mm:ss"
 */
public class DurationScrollValue extends AbstractScrollValue<Duration, Duration>
{
	private boolean wrap;
	
	public DurationScrollValue(Duration value, Duration step, Duration minValue, Duration maxValue, boolean wrap)
	{
		super(value, step, minValue, maxValue);
		
		this.wrap = wrap;
	}
	
	@Override
	public DurationScrollValue clone()
	{
		return new DurationScrollValue(value.plus(Duration.ZERO), step.plus(Duration.ZERO), minValue.plus(Duration.ZERO), 
				maxValue.plus(Duration.ZERO), wrap);
	}

	@Override
	public void increment() 
	{
		value = value.plus(step);
		if(value.compareTo(maxValue) > 0)
		{
			if(wrap)
			{
				value = minValue.plus(Duration.ZERO);
			}
			else
			{
				value = maxValue.plus(Duration.ZERO);
			}
		}
	}

	@Override
	public void decrement() 
	{
		value = value.minus(step);
		if(value.compareTo(minValue) < 0)
		{
			if(wrap)
			{
				value = maxValue.plus(Duration.ZERO);
			}
			else
			{
				value = minValue.plus(Duration.ZERO);
			}
		}
	}

	@Override
	public String toString()
	{
		return TimeFormat.getDurationFormatString(value);
	}
}