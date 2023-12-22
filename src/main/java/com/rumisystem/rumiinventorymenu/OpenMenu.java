/**
 * メニューを開く
 */

package com.rumisystem.rumiinventorymenu;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class OpenMenu {
	public static void main(PlayerInteractEvent E){
		ItemStack CLICK_ITEM = E.getPlayer().getInventory().getItemInMainHand();    //持っているアイテム
		Player PLAYER = E.getPlayer();         //プレイヤー
		JsonNode MENU_DATA = new MenuData().LoadMenu("test");

		if(Objects.nonNull(MENU_DATA)){
			if(CLICK_ITEM.getType().toString().equals(MENU_DATA.get("ITEM").get("ID").textValue())){
				//インベントリを作成
				Inventory INVENTORY = Bukkit.createInventory(null, 54, MENU_DATA.get("TITLE").textValue());

				for (int I = 0; I < 54; I++){
					JsonNode INVENTORY_DATA = MENU_DATA.get("INVENTORY").get(Integer.valueOf(I).toString());
					if(Objects.nonNull(INVENTORY_DATA)){
						Material ITEM_MATERIAL = Material.getMaterial(INVENTORY_DATA.get("MATERIAL").textValue());
						if(ITEM_MATERIAL != null){
							ItemStack ITEM_STACK = new ItemStack(ITEM_MATERIAL, 1);
							ItemMeta ITEM_META = ITEM_STACK.getItemMeta();
							ITEM_META.setDisplayName(INVENTORY_DATA.get("NAME").textValue());
							ITEM_META.setLore(Arrays.asList(new String[]{INVENTORY_DATA.get("DESC").textValue()}));
							ITEM_STACK.setItemMeta(ITEM_META);

							INVENTORY.setItem(I, ITEM_STACK);
						}
					}
				}


				PLAYER.openInventory(INVENTORY);
			}
		}else {
			PLAYER.sendMessage("ない！");
		}
	}
}
