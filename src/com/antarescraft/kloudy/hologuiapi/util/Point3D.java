package com.antarescraft.kloudy.hologuiapi.util;

import org.bukkit.Location;

public class Point3D 
{
	public double x, y, z;
	public Point3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(Location location)
	{
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
	}
}