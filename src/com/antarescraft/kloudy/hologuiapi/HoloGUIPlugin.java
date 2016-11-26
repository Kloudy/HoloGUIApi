package com.antarescraft.kloudy.hologuiapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIComponent;
import com.antarescraft.kloudy.hologuiapi.guicomponents.GUIPage;
import com.antarescraft.kloudy.hologuiapi.util.ConfigManager;

/**
 * Represents an external plugin using the HoloGUIAPI to create GUI pages
 */
public abstract class HoloGUIPlugin extends JavaPlugin
{	
	private HashMap<String, GUIPage> guiPages;
	private ArrayList<String> yamlFiles;
	private boolean guiPagesLoaded;
	
	public HoloGUIPlugin()
	{
		guiPages = new HashMap<String, GUIPage>();
		yamlFiles = new ArrayList<String>();
		guiPagesLoaded = false;
				
		initFileStructure();
		
		CodeSource source = this.getClass().getProtectionDomain().getCodeSource();
		if(source != null) 
		{
		    URL jar = source.getLocation();
		    try
		    {
		    	ZipInputStream zip = new ZipInputStream( jar.openStream());
			    ZipEntry entry = null;
			    
				while((entry = zip.getNextEntry()) != null)
				{
				    String entryName = entry.getName();
				    if( entryName.startsWith(HoloGUIApi.PATH_TO_YAMLS) &&  entryName.endsWith(".yml") )
				    {
				    	String[] pathTokens = entryName.split("/");
				        yamlFiles.add(pathTokens[pathTokens.length-1]);
				    }
				}
				
				copyResourceConfigs();
				
			} catch (IOException e) 
		    {
				e.printStackTrace();
			}
		 }
	}
	
	/**
	 * @return an instance of the HoloGUIApi class.
	 */
	public HoloGUIApi getHoloGUIApi()
	{
		return (HoloGUIApi) Bukkit.getServer().getPluginManager().getPlugin("HoloGUIApi");
	}
	
	/**
	 * @return A HashMap<String, GUIPage>, where the key is the gui page id and the value is the GUIPage object
	 */
	public HashMap<String, GUIPage> getGUIPages()
	{
		return guiPages;
	}
	
	/**
	 * 
	 * @param guiPageId
	 * @return GUIPage object with the given guiPageId. returns null if no gui page exists with the given guiPageId
	 */
	public GUIPage getGUIPage(String guiPageId)
	{
		return guiPages.get(guiPageId);
	}
	
	/**
	 * Returns the GUIComponent with the specified guiContainerId and guiComponentId.
	 * 
	 * @param guiPageId Id of the GUIPage containing the component
	 * @param guiComponentId Id of the GUIComponent
	 * @return The GUIComponent contained in the guiContainer with the specified id
	 */
	public GUIComponent getGUIComponent(String guiPageId, String guiComponentId)
	{
		GUIPage guiPage = getGUIPages().get(guiPageId);
		if(guiPage == null) return null;
		
		return guiPage.getComponent(guiComponentId);
	}
	
	public boolean guiPagesLoaded()
	{
		return guiPagesLoaded;
	}
	
	public void setGUIPagesLoaded(boolean guiPagesLoaded)
	{
		this.guiPagesLoaded = guiPagesLoaded;
	}
	
	public void addGUIPage(String guiContainerId, GUIPage guiContainer)
	{
		guiPages.put(guiContainerId, guiContainer);
	}
	
	public void setGUIPages(HashMap<String, GUIPage> guiContainers)
	{
		this.guiPages = guiContainers;
	}
	
	/**
	 * Causes HoloGUI to load the gui containers async from yaml for this HoloGUIPlugin
	 */
	public void loadGUIContainersFromYaml(CommandSender sender)
	{
		guiPagesLoaded = false;
		guiPages.clear();
		
		ConfigManager.getInstance().loadGUIContainers(sender, this);
	}

	/**
	 * Loads yaml files from folder plugins/<holoGUI_plugin_name/gui configuration files
	 */
	@SuppressWarnings("deprecation")
	public HashMap<String, YamlConfiguration> loadYamlConfigurations()
	{
		HashMap<String, YamlConfiguration> yamls = new HashMap<String, YamlConfiguration>();
		
		for(String yamlFile : yamlFiles)
		{
			try
			{
				YamlConfiguration yaml = new YamlConfiguration();
				yaml.load(new FileInputStream(String.format("plugins/%s/gui configuration files/%s",
						getName(), yamlFile)));
				
				yamls.put(yamlFile, yaml);
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		return yamls;
	}
	
	/**
	 * * Copies the resource gui pages from the plugin jar to plugins/<holoGUI_plugin_name>/gui configuration files folder
	 */
	public void copyResourceConfigs()
	{
		copyResourceConfigs(false);
	}
	
	/**
	 * @param overwriteExistingYamls (true|false) if the plugin should override yaml files from plugin folder with the yamls from inside the jar
	 * Copies the resource gui pages from the plugin jar to plugins/<holoGUI_plugin_name>/gui configuration files folder
	 */
	public void copyResourceConfigs(boolean overwriteExistingYamls)
	{
		for(String yamlName : yamlFiles)
		{
			try
			{
				InputStream inputStream = getResource("resources/yamls/" + yamlName);
				File yamlPath = new File(String.format("plugins/%s/gui configuration files/%s", 
						getName(), yamlName));
				
				if((!overwriteExistingYamls && !yamlPath.exists()) || overwriteExistingYamls)
				{
					FileOutputStream output = new FileOutputStream(yamlPath);
					output.write(IOUtils.toByteArray(inputStream));
					
					inputStream.close();
					output.close();
				}
			}
			catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Creates the plugin's data folder and gui_page_configs folder
	 */
	private void initFileStructure()
	{
		try
		{
			File folder = new File(String.format("plugins/%s", getName()));//plugin data folder
			if(!folder.exists())
			{
				folder.mkdir();
			}
			
			folder = new File(String.format("plugins/%s/gui configuration files", getName()));//gui page config folder
			if(!folder.exists())
			{
				folder.mkdir();
			}
		}
		catch(Exception e){}		
	}
	
	
}