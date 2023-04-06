/**
 * メニューをオープンするやつ
 * */

package com.rumisystem.rumi_inventory_menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static com.rumisystem.rumi_inventory_menu.Config.MENU_DATA;

public class OpenMenu{
	  public static void main(PlayerInteractEvent event){
			ItemStack clickItem = event.getItem();
			for (MenuData menuData: MENU_DATA){
				  if(clickItem.getItemMeta().equals(menuData.getITEM_MATA())){
						Player player = event.getPlayer();
						Inventory inventory = Bukkit.createInventory(null, menuData.getSIZE(), menuData.getTITLE());

						for(MenuData_DATA menuData_data: menuData.getDATA()){
							  ItemStack itemStack = new ItemStack(Material.valueOf(menuData_data.getITEM()), 1);
							  ItemMeta itemMeta = itemStack.getItemMeta();
							  itemMeta.setDisplayName(menuData_data.getNAME());
							  itemMeta.setLore(Arrays.asList(menuData_data.getDESC()));
							  itemStack.setItemMeta(itemMeta);

							  inventory.setItem(menuData_data.getPOS(), itemStack);
						}

						player.openInventory(inventory);
				  }
			}
	  }
}
