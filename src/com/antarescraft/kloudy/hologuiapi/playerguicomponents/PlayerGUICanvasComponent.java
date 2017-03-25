package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.CanvasPixel;

public class PlayerGUICanvasComponent extends PlayerGUIComponent
{
	CanvasPixel[][] pixels;

	public PlayerGUICanvasComponent(Player player, CanvasComponent canvasComponent)
	{
		super(player, canvasComponent);
		
		pixels = new CanvasPixel[canvasComponent.getProperties().getHeight()][];
	}

	@Override
	public void updateComponentLines()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{
		
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		
	}
	
	@Override
	protected Location calculateArmorStandLocation(int rowIndex, Location origin, Vector vect, double distance, double lineHeight, 
			double verticalOffset, double horizontalOffset)
	{			
		double xi = origin.getX();
		double yi = origin.getY();
		double zi = origin.getZ();
		
		//rotate vect by horizontal offset
		double horizontalRadianAngle = horizontalOffset * Math.PI/3.72;
		double verticalRadianAngle = verticalOffset * Math.PI/4.7;

		vect = rotateAboutYAxis(vect, horizontalRadianAngle);
		//double horizontalDistance = (distance * Math.sin(horizontalRadianAngle)) / Math.sin((Math.PI/2) - horizontalOffset);
		//Vector orthogVector = new Vector(vect.getZ(), 0, -vect.getX());
		
		//doing rotation relative origin <0,0,0> will add xi,yi,zi in at the end
		double x0 = (vect.getX() * distance);
		double y0 = (vect.getY() * distance);
		double z0 = (vect.getZ() * distance);
		//cartesian conversion to spherical coordinates of where player is looking 'distance' blocks away
		double r0 = Math.sqrt((x0*x0)+(y0*y0)+(z0*z0));
		double theta0 = Math.acos((y0 / r0));
		double phi0 = Math.atan2(z0,  x0);
		
		//rowIndex 0 is the label, rotate the label up a line length so it sits with a one row gap above the component
		if(rowIndex == 0) lineHeight *= 2;
		
		//rotate theta0 by deltaTheta
		double r1 = r0;
		double theta1 = theta0 + (rowIndex * lineHeight) - verticalRadianAngle;
		double phi1 = phi0;
		
		//convert spherical coordinates back into cartesian coordinates
		double y1 = r1 * Math.cos(theta1);
		double x1 = r1 * Math.cos(phi1) * Math.sin(theta1);
		double z1 = r1 * Math.sin(theta1) * Math.sin(phi1);
		x1 += xi;
		y1 += yi;
		z1 += zi;
		
		//x1 += orthogVector.getX() * horizontalDistance;
		//z1 += orthogVector.getZ() * horizontalDistance;
		return new Location(origin.getWorld(), x1, y1,  z1);
	}
}