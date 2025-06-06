package su.rumishistem.rumiinventorymenu.Command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import su.rumishistem.rumiinventorymenu.MenuManager;

public class CompleterOpenMenuCommand implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender Sender, Command CMD, String Label, String[] args) {
		return Arrays.asList(MenuManager.getMenuNameList());
	}
}
