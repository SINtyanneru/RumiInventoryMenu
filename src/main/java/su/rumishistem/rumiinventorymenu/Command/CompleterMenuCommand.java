package su.rumishistem.rumiinventorymenu.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import su.rumishistem.rumiinventorymenu.MenuManager;

public class CompleterMenuCommand implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender Sender, Command CMD, String Label, String[] args) {
		if (args.length == 1) {
			return Arrays.asList("list", "new", "edit");
		} else if (args.length == 2 && args[0].equals("edit")) {
			//メニュー名補完
			List<String> Completer = new ArrayList<String>();
			for (String Name:MenuManager.getMenuNameList()) {
				Completer.add(Name);
			}
			return Completer;
		} else if (args.length == 3) {
			return Arrays.asList("title", "content");
		} else {
			return null;
		}
	}
}
