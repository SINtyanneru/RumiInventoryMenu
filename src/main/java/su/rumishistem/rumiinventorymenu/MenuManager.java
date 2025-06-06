package su.rumishistem.rumiinventorymenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.fasterxml.jackson.databind.ObjectMapper;
import su.rumishistem.rumiinventorymenu.TYPE.Menu;
import su.rumishistem.rumiinventorymenu.TYPE.MenuItem;

public class MenuManager {
	private static Path MenuDirPath = null;
	private static HashMap<String, Menu> MenuTable = new HashMap<String, Menu>();

	public static void Init() throws IOException {
		MenuDirPath = Main.PluginDirPath.resolve("Menu");

		//フォルダを作る
		if (!Files.exists(MenuDirPath)) {
			Files.createDirectory(MenuDirPath);
		}

		Load();
	}

	public static void Load() throws IOException {
		File MenuDir = new File(MenuDirPath.toString());
		for (File F:MenuDir.listFiles()) {
			if (F.isFile()) {
				String Name = F.getName().replace(".json", "");
				MenuTable.put(Name, new Menu(new ObjectMapper().readTree(F)));
			}
		}
	}

	public static String[] getMenuNameList() {
		Set<String> KeyList = MenuTable.keySet();
		String[] NameList = new String[KeyList.size()];

		for (int I = 0; I < KeyList.size(); I++) {
			NameList[I] = (String) KeyList.toArray()[I];
		}

		return NameList;
	}

	public static Menu getMenu(String Name) {
		return MenuTable.get(Name);
	}

	public static void New(String Name) throws IOException {
		File MenuFile = new File(MenuDirPath.resolve(Name+".json").toString());
		if (MenuFile.exists()) {
			throw new Error("既にメニューががあります");
		}

		FileOutputStream FOS = new FileOutputStream(MenuFile);
		FOS.write(new Menu().genJson().getBytes());
		FOS.close();
	}

	public static void Save(String Name, String Body) throws IOException {
		File MenuFile = new File(MenuDirPath.resolve(Name+".json").toString());
		if (MenuFile.exists()) {
			FileOutputStream FOS = new FileOutputStream(MenuFile);
			FOS.write(Body.getBytes());
			FOS.close();
		} else {
			throw new Error("メニューがありません");
		}
	}

	public static void Open(Player P, String Name) {
		if (MenuManager.getMenu(Name) == null) {
			throw new Error("メニューがない");
		}

		Menu M = MenuManager.getMenu(Name);
		MenuItem[] ItemList = M.getContent();
		Inventory Inv = Bukkit.createInventory(null, M.getSize(), M.getTitle());
		for (int I = 0; I < ItemList.length; I++) {
			if (ItemList[I] != null) {
				Inv.setItem(I, ItemList[I].getItem());
			}
		}
		P.openInventory(Inv);

		MCEvent.OpeningMenuTable.put(P.getUniqueId().toString(), Name);
	}
}
