package com.rumisystem.rumiinventorymenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Events implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent E) {
		if (E.getAction() == Action.LEFT_CLICK_AIR || E.getAction() == Action.LEFT_CLICK_BLOCK) {
			System.out.println("Left Click");
			//Leftクリック
		} else if (E.getAction() == Action.RIGHT_CLICK_AIR || E.getAction() == Action.RIGHT_CLICK_BLOCK) {
			System.out.println("Right Click");
			//Rightクリック
			ItemStack CLICK_ITEM = E.getPlayer().getInventory().getItemInMainHand();
			if(Objects.nonNull(CLICK_ITEM)){
				OpenMenu.main(E);
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent E) {
		//クリックされたアイテムが空気の場合は処理しない
		if (E.getCurrentItem() == null || E.getCurrentItem().getType() == Material.AIR) {
			return;
		}

		//本編処理
		Player PLAYER = (Player) E.getWhoClicked();

	}
}
