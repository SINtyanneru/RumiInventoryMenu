/**
 * コマンドマネージャー
 * */
package com.rumisystem.rumi_inventory_menu.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class Command_Manager implements CommandExecutor {
	  @Override
	  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
			//コマンド関連の処理
			if (command.getName().equalsIgnoreCase("rim")) { //親コマンドの判定
				  if (args.length == 0) { //サブコマンドの個数が0、つまりサブコマンド無し
						sender.sendMessage("§a========[RIM]========\n§a|reload => 再読み込みします\n§a|itemget => メニューのアイテムをゲットします");
						return true; //終わり
				  } else { //サブコマンドの個数が0以外
						switch (args[0]){
							  case "reload":
									sender.sendMessage("§b[ *** ]再読み込み中...");
									Reload reload = new Reload();
									sender.sendMessage("§b[ OK ]完了！！");
									try {
										  reload.main();
									} catch (IOException e) {
										  throw new RuntimeException(e);
									}
									break;
							  case "itemget":
									if(args[1].isEmpty()){
										  sender.sendMessage("§eメニューの名前を指定して！");
										  return false;
									}
									ItemGet.main(args[1], sender);
									break;
							  default:
									//それ以外のサブコマンド
									sender.sendMessage("§eそんなコマンドねえぞ！！");
									break;
						}
						return true; //終わり
				  }
			}
			return false;
	  }
}
