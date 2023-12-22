package com.rumisystem.rumiinventorymenu;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RumiInventoryMenu extends JavaPlugin {
	private static RumiInventoryMenu INSTANCE;

	@Override
	public void onEnable() {
		//プラグイン起動
		System.out.println("==========[RIM]==========");
		System.out.println("RumiInventoryMenu Loaded!");
		System.out.println("V1.0");

		//インスタンスを変数に
		INSTANCE = this;

		//BungeeCordと通信する準備
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		//イベントリスナー登録
		getServer().getPluginManager().registerEvents(new Events(), this);
	}

	@Override
	public void onDisable() {
		//シャットダウン
		System.out.println("RumiInventoryMenu Unloaded... Goodbye");
	}

	public static RumiInventoryMenu getInstance() {
		return INSTANCE;
	}

	//鯖を移動する
	public void SendServer(String NAME, Player player){
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(NAME);

		player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
	}

	//メニュー表示用アイテムを入れる
	public void SetMenuItem(String MENU_NAME, Player PLAYER){
		JsonNode MENU_DATA = new MenuData().LoadMenu("test");

		if(Objects.nonNull(MENU_DATA)){
			ItemStack ITEM = new ItemStack(Material.getMaterial(MENU_DATA.get("ITEM").get("MATERIAL").textValue()), 1);
			ItemMeta META = ITEM.getItemMeta();
			META.setDisplayName(MENU_DATA.get("ITEM").get("NAME").textValue());
			ITEM.setItemMeta(META);

			PLAYER.getInventory().setItem(MENU_DATA.get("ITEM").get("SLOT").intValue(), ITEM);
		}
	}
}
