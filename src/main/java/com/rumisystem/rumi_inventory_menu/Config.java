/*
 * この部分のコードの、JSON解析部分はChatGPTのちからだぞ！()
 * */

package com.rumisystem.rumi_inventory_menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Config {
	  public static String Config_Path = null;

	  public static List<MenuData> MENU_DATA = new ArrayList<>();

	  public static List<Menu_COMMAND> MENU_COMMAND = new ArrayList<>();

	  public static void main() throws IOException {
			System.out.println("Config Path :" + Config_Path);
			ConfigFileCheck();
			MenuLoader();
	  }

	  public static void ConfigFileCheck(){
			if(Files.exists(Paths.get(Config_Path))){
				  //コンフィグフォルダがある
				  System.out.println("Configuration file is available! Thank you for using it!");
			}else {
				  //コンフィグフォルダが無い
				  try {
						Files.createDirectory(Paths.get(Config_Path));
						Files.createDirectory(Paths.get(Config_Path + "/Menu"));
				  }catch (IOException ex){
						System.out.println("[ ERR ]Config file create err!!!!!");
				  }
			}
	  }

	  public static void MenuLoader() throws IOException {
			File dir = new File(Config_Path + "/Menu/");
			File[] fileArray = dir.listFiles();
			for(File f: fileArray){
				  if(f.isFile()){
						Path file_path = Paths.get(f.getPath());
						String DATA = String.join("", Files.readAllLines(file_path, StandardCharsets.UTF_8));
						DATA = DATA.replaceAll("\\t", "").replaceAll("\\r\\n|\\r|\\n", "");

						System.out.println("[ *** ] Load menu:" + f.toPath());
						System.out.println("[ *** ] DATA:" + DATA);

						ObjectMapper objectMapper = new ObjectMapper();
						JsonNode rootNode = objectMapper.readTree(DATA);

						// ルートノードから値を取得
						Material item_material = Material.valueOf(rootNode.get("ITEM").asText().toUpperCase());

						String title = rootNode.get("TITLE").asText();
						String item_name = rootNode.get("ITEM_NAME").asText();
						String item_desc = rootNode.get("ITEM_DESC").asText();
						int size = rootNode.get("SIZE").asInt();

						ItemStack itemStack_temp = new ItemStack(item_material, 1);
						ItemMeta itemMeta_temp = itemStack_temp.getItemMeta();
						itemMeta_temp.setDisplayName(item_name);
						itemMeta_temp.setLore(Arrays.asList(item_desc));

						List<MenuData_DATA> menu_data = new ArrayList<>();

						// DATA配列から値を取得
						JsonNode dataNode = rootNode.get("DATA");
						for(JsonNode row: dataNode){
							  int pos = row.get("POS").asInt();
							  String item = row.get("ITEM").asText();
							  String name = row.get("NAME").asText();
							  String desc = row.get("DESC").asText();

							  List<String > cmd = new ArrayList<>();
							  Iterator<JsonNode> elements = row.get("COMMAND").elements();
							  while (elements.hasNext()){
									JsonNode element = elements.next();
									cmd.add(element.asText());
							  }

							  ItemStack itemStack = new ItemStack(Material.valueOf(item.toUpperCase()), 1);
							  ItemMeta itemMeta = itemStack.getItemMeta();
							  itemMeta.setDisplayName(name);
							  itemMeta.setLore(Arrays.asList(desc));

							  Menu_COMMAND menu_command = new Menu_COMMAND(title, itemMeta, Material.valueOf(item.toUpperCase()), cmd);
							  MENU_COMMAND.add(menu_command);

							  MenuData_DATA data = new MenuData_DATA(pos, item, name, desc);
							  menu_data.add(data);
						}

						MenuData menu = new MenuData(item_material, itemMeta_temp, title, size, menu_data);
						MENU_DATA.add(menu);
				  }
			}
	  }
}

class MenuData_DATA{
	  private int POS;
	  private String ITEM;
	  private String NAME;
	  private String DESC;

	  public MenuData_DATA(int pos, String item, String name, String desc){
			this.POS = pos;
			this.ITEM = item;
			this.NAME = name;
			this.DESC = desc;
	  }

	  public int getPOS(){
			return POS;
	  }

	  public String getITEM(){
			return ITEM;
	  }

	  public String getNAME(){
			return NAME;
	  }

	  public String getDESC(){
			return DESC;
	  }
}

class Menu_COMMAND{
	  private String MENU_NAME;
	  private ItemMeta ITEM_META;
	  private Material ITEM_MATERIAL;
	  private List<String> COMMAND;


	  public Menu_COMMAND(String menu_name, ItemMeta itemMeta, Material item_material, List<String> cmd){
			this.MENU_NAME = menu_name;
			this.ITEM_META = itemMeta;
			this.ITEM_MATERIAL = item_material;
			this.COMMAND = cmd;
	  }

	  public String getMENU_NAME(){
			return MENU_NAME;
	  }

	  public ItemMeta getITEM_META(){
			return ITEM_META;
	  }

	  public Material getITEM_MATERIAL(){
			return ITEM_MATERIAL;
	  }

	  public List<String> getCOMMAND(){
			return COMMAND;
	  }
}