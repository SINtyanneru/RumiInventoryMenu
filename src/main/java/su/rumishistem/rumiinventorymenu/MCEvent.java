package su.rumishistem.rumiinventorymenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import su.rumishistem.rumiinventorymenu.Command.MenuCommand;
import su.rumishistem.rumiinventorymenu.TYPE.Menu;
import su.rumishistem.rumiinventorymenu.TYPE.MenuItem;

public class MCEvent implements Listener {
	public static HashMap<String, String> OpeningMenuTable = new HashMap<String, String>();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		//Rightクリック
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack Item = e.getPlayer().getInventory().getItemInMainHand();
			if (Objects.nonNull(Item)) {
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) throws IOException {
		//チェストのインベントリを閉じた
		if (e.getInventory().getType() == InventoryType.CHEST) {
			String PlayerID = e.getPlayer().getUniqueId().toString();

			//メニュー
			if (OpeningMenuTable.get(PlayerID) != null) {
				OpeningMenuTable.remove(PlayerID);
			}

			//編集
			if (MenuCommand.EditPlayer.get(PlayerID) != null) {
				//適用
				String MenuName = MenuCommand.EditPlayer.get(PlayerID);
				Menu M = MenuManager.getMenu(MenuName);

				for (int I = 0; I < M.getSize(); I++) {
					ItemStack Item = e.getInventory().getContents()[I];
					//ない
					if (Item == null || Item.getType() == Material.AIR) {
						M.setContent(I, null);
						continue;
					};

					//ある
					String ItemName = Item.getItemMeta().getDisplayName();
					List<String> LoreList = (Item.getItemMeta().hasLore()) ? Item.getItemMeta().getLore() : new ArrayList<String>();
					M.setContent(I, new MenuItem(I, ItemName, String.join("\n", LoreList), Item.getType(), new ArrayList<String>()));
				}

				//保存
				MenuManager.Save(MenuName, M.genJson());

				//おけ
				e.getPlayer().sendMessage("編集を適用した");
				MenuCommand.EditPlayer.remove(PlayerID);
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player P = (Player)e.getWhoClicked();
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if (OpeningMenuTable.get(P.getUniqueId().toString()) == null) return;
		if (MenuManager.getMenu(OpeningMenuTable.get(P.getUniqueId().toString())) == null) return;

		Menu M = MenuManager.getMenu(OpeningMenuTable.get(P.getUniqueId().toString()));
		if (M.getItem(e.getSlot()) != null) {
			String[] Code = M.getItem(e.getSlot()).getLeftClickEvent();
			for (int I = 0; I < Code.length; I++) {
				String Line = Code[I];
				String[] CMD = Line.split(" ");

				if (Line.startsWith("#")) {
					continue;
				}

				switch (CMD[0].toUpperCase()) {
					case "CLOSE":
						P.closeInventory();
						break;

					case "PRINT":
						String PrintText = FindDK(Line);
						if (PrintText != null) {
							P.sendMessage(PrintText);
						} else {
							P.sendMessage(ChatColor.RED + "構文エラー：" + (I+1) + "行目「ダブルクオートで囲まれた箇所を検出できませんでした」");
						}
						break;

					case "OPEN":
						MenuManager.Open(P, CMD[1]);
						break;

					case "BUNGEECORD":
						switch (CMD[1]) {
							case "MOVE":
								String MoveServerName = FindDK(Line);
								if (MoveServerName != null) {
									ByteArrayDataOutput BADO = ByteStreams.newDataOutput();
									BADO.writeUTF("Connect");
									BADO.writeUTF(MoveServerName);
									P.sendPluginMessage(Main.getCTX(), "BungeeCord", BADO.toByteArray());
								} else {
									P.sendMessage(ChatColor.RED + "構文エラー：" + (I+1) + "行目「ダブルクオートで囲まれた箇所を検出できませんでした」");
								}
								break;
						}
						break;

					default:
						P.sendMessage(ChatColor.RED + "構文エラー：" + (I+1) + "行目「未定義動作」");
						break;
				}
			}
		}

		e.setCancelled(true);
	}

	private String FindDK(String Text) {
		Matcher MTC = Pattern.compile("\"(.*?)\"").matcher(Text);
		if (MTC.find()) {
			return MTC.group(1);
		} else {
			return null;
		}
	}
}
