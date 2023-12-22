package com.rumisystem.rumiinventorymenu;

import org.bukkit.plugin.java.JavaPlugin;

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
}
