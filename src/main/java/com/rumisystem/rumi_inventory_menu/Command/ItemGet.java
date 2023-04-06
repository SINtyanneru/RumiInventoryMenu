/**
 * メニューのアイテムをゲットする
 * */
package com.rumisystem.rumi_inventory_menu.Command;

import com.rumisystem.rumi_inventory_menu.*;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static com.rumisystem.rumi_inventory_menu.Config.MENU_DATA;

public class ItemGet {
	  public static void main(String MenuName, CommandSender sender){
			if(MenuName.isEmpty()){
				  sender.sendMessage("§eメニューの名前を指定して！");
				  return;
			}
			if(sender instanceof Player){
				  Player player = (Player) sender;
				  for (MenuData menuData: MENU_DATA){
						if(menuData.getTITLE().equals(MenuName)){
							  ItemStack itemStack = new ItemStack(Material.valueOf(menuData.getITEM_MATERIAL()), 1);
							  itemStack.setItemMeta(menuData.getITEM_MATA());
							  player.getInventory().addItem(itemStack);
							  return;
						}
				  }
			}else {
				  sender.sendMessage("§eこのコマンドはプレイヤーからしか実行できないよ！");
				  return;
			}

			//メニューが無い場合
			sender.sendMessage("§eそんな名前のメニューはないよ！");
	  }
}
