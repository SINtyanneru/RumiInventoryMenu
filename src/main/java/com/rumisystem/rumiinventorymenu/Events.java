package com.rumisystem.rumiinventorymenu;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class Events implements Listener {
	//鯖参加
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent E) {
		Player PLAYER = E.getPlayer();

		RumiInventoryMenu.getInstance().SetMenuItem("test", PLAYER);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent E) {
		if (E.getAction() == Action.LEFT_CLICK_AIR || E.getAction() == Action.LEFT_CLICK_BLOCK) {
			//Leftクリック
		} else if (E.getAction() == Action.RIGHT_CLICK_AIR || E.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
		JsonNode MENU_DATA = new MenuData().LoadMenu("test");

		if(Objects.nonNull(MENU_DATA)){
			JsonNode INVENTORY_DATA = MENU_DATA.get("INVENTORY").get(Integer.valueOf(E.getSlot()).toString());
			if(Objects.nonNull(INVENTORY_DATA)){
				//アイテムが本当にインベントリメニューのものかチェック(アイテム名とマテリアルでチェック)
				if(
						(INVENTORY_DATA.get("NAME").textValue().equals(E.getCurrentItem().getItemMeta().getDisplayName()))&&
						(INVENTORY_DATA.get("MATERIAL").textValue().equals(E.getCurrentItem().getType().toString()))
				){
					//イベントをキャンセル（これでアイテムの移動ができない）
					E.setCancelled(true);
					//コマンドを回す
					for(int I_R = 0; I_R < INVENTORY_DATA.get("RUN").size(); I_R++){
						for(int I_C = 0; I_C < INVENTORY_DATA.get("RUN").get(0).size(); I_C++){
							String COMMAND_ARGS = INVENTORY_DATA.get("RUN").get(0).get(I_C).textValue();
							//コマンドを処理
							switch (COMMAND_ARGS){
								//メッセージ
								case "SAY":{
									PLAYER.sendMessage(INVENTORY_DATA.get("RUN").get(0).get(I_C + 1).textValue());
								}

								//本
								case "BOOK":{
									ItemStack BOOK = new ItemStack(Material.WRITTEN_BOOK);
									BookMeta BM = (BookMeta) BOOK.getItemMeta();
									//タイトル
									BM.setTitle("無");
									//著者
									BM.setAuthor("無");

									//内容
									int BOOK_I = 0;
									while (true){
										if(INVENTORY_DATA.get("RUN").get(0).hasNonNull(BOOK_I + 1)){
											BM.addPage(INVENTORY_DATA.get("RUN").get(0).get(BOOK_I + 1).textValue());
											BOOK_I++;
										}else {
											break;
										}
									}

									BOOK.setItemMeta(BM);

									PLAYER.openBook(BOOK);
								}

								//サーバー間での移動
								case "BMV":{
									RumiInventoryMenu.getInstance().SendServer(INVENTORY_DATA.get("RUN").get(0).get(I_C + 1).textValue(), PLAYER);
								}
							}
						}
					}
				}
			}
		}
	}

	//アイテムドロップ
	@EventHandler
	public void onItemDrop (PlayerDropItemEvent E) {
		Item DROP_ITEM = E.getItemDrop();
		Player PLAYER = E.getPlayer();
		JsonNode MENU_DATA = new MenuData().LoadMenu("test");

		if(Objects.nonNull(MENU_DATA)){
			JsonNode MENU_ITEM = MENU_DATA.get("ITEM");
			if(
					MENU_ITEM.get("MATERIAL").textValue().equals(DROP_ITEM.getItemStack().getType().toString())&&
					MENU_ITEM.get("NAME").textValue().equals(DROP_ITEM.getItemStack().getItemMeta().getDisplayName().toString())
			){
				DROP_ITEM.remove();
				RumiInventoryMenu.getInstance().SetMenuItem("test", PLAYER);
			}
		}
	}
}
