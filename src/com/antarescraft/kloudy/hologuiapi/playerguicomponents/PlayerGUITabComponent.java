package com.antarescraft.kloudy.hologuiapi.playerguicomponents;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.antarescraft.kloudy.hologuiapi.HoloGUIApi;
import com.antarescraft.kloudy.hologuiapi.config.ComponentPosition;
import com.antarescraft.kloudy.hologuiapi.config.LabelComponentConfig;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponentFactory;
import com.antarescraft.kloudy.hologuiapi.guicomponents.LabelComponent;
import com.antarescraft.kloudy.hologuiapi.imageprocessing.MinecraftColor;

public class PlayerGUITabComponent extends PlayerGUIComponent
{
	private PlayerGUICanvasComponent canvas;
	private PlayerGUITextComponent label;
	
	private int tabIndex;
	private String tabImageName;
	private int tabWidth;
	private int tabHeight;
	private boolean open;
	
	private int imageX = (tabIndex * tabWidth) + tabIndex + 2;
	private int imageY = 0;
	
	public PlayerGUITabComponent(Player player, PlayerGUICanvasComponent canvas, 
			String tabTitle, int tabIndex, String tabImageName, int tabWidth, int tabHeight, boolean open)
	{
		super(player, null);
		
		this.tabIndex = tabIndex;
		this.tabImageName = tabImageName;
		this.tabWidth = tabWidth;
		this.tabHeight = tabHeight;
		this.open = open;
		
		// Construct the label displayed on the tab.
		LabelComponentConfig labelConfig = new LabelComponentConfig();
		labelConfig.id = String.format("###tab-label-%d###", tabIndex);
		labelConfig.position = new ComponentPosition(0, 0);
		labelConfig.lines = new ArrayList<String>();
		labelConfig.lines.add(tabTitle);
		
		guiComponent = GUIComponentFactory.createLabelComponent(canvas.getCanvasComponent().getHoloGUIPlugin(), labelConfig);
	}

	@Override
	public void updateComponentLines() 
	{
		guiComponent.updateComponentLines(player);
	}

	@Override
	public void spawnEntities(Location lookLocation, boolean stationary)
	{
		canvas.drawImage(imageX, imageY, tabImageName, tabWidth, tabHeight);
		
		label = (PlayerGUITextComponent)guiComponent.initPlayerGUIComponent(player);
		label.spawnEntities(lookLocation, false);
		
		if(open)
		{
			int x1 = imageX + 1;
			int y1 = tabHeight - 1;
			int x2 = x1 + tabWidth - 3;
			int y2 = y1;
						
			// Removes the bottom line on the tab, making it appear 'open'
			canvas.fill(x1, y1, x2, y2, MinecraftColor.TRANSPARENT);
		}
	}

	@Override
	public void updateLocation(Location lookLocation, boolean stationary) 
	{
		
	}
	
	private Location calculateLabelLocation()
	{
		PlayerCanvasPixel topLeftPixel = canvas.getPixel(imageX, imageY);
		PlayerCanvasPixel bottomRightPixel = canvas.getPixel(imageX + tabWidth - 1, tabHeight - 1);
		Location topLeftPixelLocation = topLeftPixel.getLocation();
		Location bottomRightPixelLocation = bottomRightPixel.getLocation();
		
		double xi = topLeftPixelLocation.getX();
		double yi = topLeftPixelLocation.getY();
		double zi = topLeftPixelLocation.getZ();
		
		double dx = bottomRightPixelLocation.getX() - xi;
		double dy = bottomRightPixelLocation.getY() - yi;
		double dz = bottomRightPixelLocation.getZ() - zi;
		
		return new Location(topLeftPixelLocation.getWorld(), xi + (dx * 0.5), yi + (dy * 0.5), zi + (dz * 0.5));
	}
}