package su.rumishistem.rumiinventorymenu.Command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import su.rumishistem.rumiinventorymenu.MCEvent;
import su.rumishistem.rumiinventorymenu.MenuManager;
import su.rumishistem.rumiinventorymenu.TYPE.Menu;
import su.rumishistem.rumiinventorymenu.TYPE.MenuItem;

public class MenuCommand implements CommandExecutor {
	private static final String HR = "----------------------------------------";

	public static HashMap<String, String> EditPlayer = new HashMap<String, String>();

	@Override
	public boolean onCommand(CommandSender Sender, Command CMD, String Label, String[] args) {
		try {
			if (Sender instanceof Player) {
				Player P = (Player) Sender;

				if (args.length == 0) {
					Help(P);
				} else {
					switch (args[0]) {
						case "list":
							P.sendMessage(HR);
							for (String Name:MenuManager.getMenuNameList()) {
								P.sendMessage("・" + Name + " - " + MenuManager.getMenu(Name).getTitle());
							}
							P.sendMessage(HR);
							break;

						case "new":
							if (args.length == 2) {
								if (args[1].matches("^[A-Za-z_]+$")) {
									MenuManager.New(args[1]);
									MenuManager.Load();
									P.sendMessage("メニューを作成しました。「/menu edit " + args[1] + "」で編集可能です。");
								} else {
									P.sendMessage(ChatColor.RED + "名前はAからZの大文字小文字とアンダーバーのみ使用可能です");
								}
							} else {
								Help(P);
							}
							break;

						case "edit":
							if (args.length < 2) {
								P.sendMessage(ChatColor.RED + "メニューの名前を指定して");
								break;
							}

							if (MenuManager.getMenu(args[1]) == null) {
								P.sendMessage(ChatColor.RED + "メニューがありません");
								break;
							}

							if (args.length > 2) {
								Menu M = MenuManager.getMenu(args[1]);

								switch (args[2]) {
									case "title":
										M.setTitle(args[3]);
										MenuManager.Save(args[1], M.genJson());
										MenuManager.Load();
										P.sendMessage("タイトル変更：" + args[3]);
										break;
									case "content":
										MenuItem[] ItemList = M.getContent();
										Inventory Inv = Bukkit.createInventory(null, M.getSize(), M.getTitle());
										for (int I = 0; I < ItemList.length; I++) {
											if (ItemList[I] != null) {
												Inv.setItem(I, ItemList[I].getItem());
											}
										}
										P.openInventory(Inv);

										EditPlayer.put(P.getUniqueId().toString(), args[1]);
										break;
								}
							} else {
								P.sendMessage(HR);
								P.sendMessage("タイトル変更> /menu edit " + args[1] + " title {名前}");
								P.sendMessage("内容編集> /menu edit " + args[1] + " content");
								P.sendMessage(HR);
							}
							break;

						default:
							Help(P);
							break;
					}
				}
				return true;
			}

			Sender.sendMessage("Player only");
			return true;
		} catch (Exception EX) {
			EX.printStackTrace();
			Sender.sendMessage("Err");
			return true;
		}
	}

	private void Help(Player P) {
		P.sendMessage("・使い方");
		P.sendMessage("> /menu オプション");
		P.sendMessage("・オプション");
		P.sendMessage("list");
		P.sendMessage("new {名前}");
		P.sendMessage("edit {名前}");
	}
}
