package su.rumishistem.rumiinventorymenu.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import su.rumishistem.rumiinventorymenu.MenuManager;

public class OpenMenuCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender Sender, Command CMD, String Label, String[] args) {
		if (!(Sender instanceof Player)) {
			Sender.sendMessage("Player only");
			return true;
		}

		if (args.length == 0) {
			Sender.sendMessage("メニュー名を指定しろカス");
			return true;
		}

		Player P = (Player) Sender;
		MenuManager.Open(P, args[0]);
		return true;
	}
}
