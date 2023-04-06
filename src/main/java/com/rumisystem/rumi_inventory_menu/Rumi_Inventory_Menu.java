package com.rumisystem.rumi_inventory_menu;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.rumisystem.rumi_inventory_menu.Command.Command_Manager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rumisystem.rumi_inventory_menu.Config.MENU_COMMAND;

public final class Rumi_Inventory_Menu extends JavaPlugin implements Listener {
	  @Override
	  public void onEnable() {
			// Plugin startup logic
			System.out.println("==========[RIM]==========");
			System.out.println("RumiInventoryMenu Loaded!");
			System.out.println("V1.0");

			getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

			getCommand("rim").setExecutor(new Command_Manager());

			Config.Config_Path = getDataFolder().getPath();
			try {
				  Config.main();
			} catch (IOException e) {
				  throw new RuntimeException(e);
			}

			getServer().getPluginManager().registerEvents(this, this);
	  }

	  @Override
	  public void onDisable() {
			// Plugin shutdown logic
			System.out.println("RumiInventoryMenu Unloaded... Goodbye");
	  }

	  @EventHandler
	  public void onPlayerInteract(PlayerInteractEvent event) {
			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				  //Left Click!
			} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				  //Right Click!
				  ItemStack clickItem = event.getItem();
				  if(clickItem != null){
						OpenMenu.main(event);
				  }
			}
	  }

	  @EventHandler
	  public void onInventoryClick(InventoryClickEvent event) {
			if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) { // クリックされたアイテムが空気の場合は処理しない
				  return;
			}

			Player player = (Player) event.getWhoClicked();

			for(Menu_COMMAND cmd : MENU_COMMAND){
				  if(cmd.getMENU_NAME().equals(event.getView().getTitle())){
						//System.out.println("メニュー名一致！");
						event.setCancelled(true); // イベントをキャンセルする（アイテムを移動できなくする）
						if(cmd.getITEM_META().equals(event.getCurrentItem().getItemMeta())){
							  //System.out.println("メタ一致！");
							  if(cmd.getITEM_MATERIAL().equals(event.getCurrentItem().getType())){
									//System.out.println("全一致！");
									for (String cmd_data : cmd.getCOMMAND()){//コマンドをすべて回す
										  if(cmd_data.startsWith("SAY")){//SAYコマンド
												Pattern ptn = Pattern.compile("\\\"([^\\\"]*)\\\"");
												Matcher match = ptn.matcher(cmd_data);
												if(match.find()){
													  player.sendMessage(match.group().replaceAll("^\"|\"$", ""));
												}
										  }else if(cmd_data.equals("CLOSE")){//メニューを閉じるコマンド
												player.closeInventory();
										  }else if(cmd_data.startsWith("SERVER")){//メニューを閉じるコマンド
												Pattern ptn = Pattern.compile("\\\"([^\\\"]*)\\\"");
												Matcher match = ptn.matcher(cmd_data);
												if(match.find()){
													  String connect_server = match.group().replaceAll("^\"|\"$", "");
													  SendServer(connect_server, player);
												}
										  }
									}
							  }
						}
				  }else {
						// インベントリのタイトルが一致しない場合は処理しない
						return;
				  }
			}
        /*
        switch (event.getCurrentItem().getType()) {
            case DIAMOND:
                // ダイヤモンドがクリックされた場合の処理
                System.out.println("Clicked!!" + player.getName());

                break;
            // 他のアイテムがクリックされた場合の処理
            default:
                break;
        }
         */
	  }

	  @EventHandler
	  public void onInventoryClose(InventoryCloseEvent event){

	  }

	  public void SendServer(String NAME, Player player){
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(NAME);
			System.out.println("Byte OK" + out);
			// If you don't care about the player
			// Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
			// Else, specify them
			player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
			System.out.println("SendOK");
	  }
}
