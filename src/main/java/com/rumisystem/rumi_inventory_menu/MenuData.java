package com.rumisystem.rumi_inventory_menu;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuData {
	  private Material ITEM_MATERIAL;
	  private ItemMeta ITEM_META;
	  private String TITLE;
	  private int SIZE;
	  private List<MenuData_DATA> DATA = new ArrayList<>();

	  public MenuData(Material item_material, ItemMeta itemMeta, String title, int size, List<MenuData_DATA> data) {
			this.TITLE = title;
			this.SIZE = size;
			this.DATA = data;
			this.ITEM_MATERIAL = item_material;
			this.ITEM_META = itemMeta;
	  }

	  public String getITEM_MATERIAL() {
			return ITEM_MATERIAL.toString();
	  }

	  public ItemMeta getITEM_MATA() {
			return ITEM_META;
	  }

	  public String getTITLE() {
			return TITLE;
	  }

	  public int getSIZE() {
			return SIZE;
	  }

	  public List<MenuData_DATA> getDATA() {
			return DATA;
	  }
}
