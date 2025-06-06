package su.rumishistem.rumiinventorymenu;

import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.plugin.java.JavaPlugin;

import su.rumishistem.rumiinventorymenu.Command.CompleterMenuCommand;
import su.rumishistem.rumiinventorymenu.Command.CompleterOpenMenuCommand;
import su.rumishistem.rumiinventorymenu.Command.MenuCommand;
import su.rumishistem.rumiinventorymenu.Command.OpenMenuCommand;

public class Main extends JavaPlugin {
	private static Main CTX;
	public static Path PluginDirPath = Path.of("./plugins/RumiInventoryMenu/");

	public static Main getCTX() {
		return CTX;
	}

	@Override
	public void onEnable() {
		getLogger().info("==========[RIM]==========");
		getLogger().info("RumiInventoryMenu");
		getLogger().info("=========================");

		CTX = this;
		getServer().getPluginManager().registerEvents(new MCEvent(), CTX);

		//Bungeecordと通信する準備
		getServer().getMessenger().registerOutgoingPluginChannel(CTX, "BungeeCord");

		//コマンド登録
		getCommand("menu").setExecutor(new MenuCommand());
		getCommand("menu").setTabCompleter(new CompleterMenuCommand());
		getCommand("openmenu").setExecutor(new OpenMenuCommand());
		getCommand("openmenu").setTabCompleter(new CompleterOpenMenuCommand());

		//フォルダを作る
		if (!Files.exists(PluginDirPath)) {
			try {
				Files.createDirectories(PluginDirPath);
			} catch (Exception EX) {
				EX.printStackTrace();
				getLogger().info("フォルダを作れなかった");
			}
		}

		//初期化
		try {
			MenuManager.Init();
		} catch (Exception EX) {
			EX.printStackTrace();
			getLogger().info("初期化失敗");
		}
	}
}
