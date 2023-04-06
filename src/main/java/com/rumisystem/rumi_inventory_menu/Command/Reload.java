/**
 * 設定を再読み込み
 * */
package com.rumisystem.rumi_inventory_menu.Command;

import com.rumisystem.rumi_inventory_menu.Config;

import java.io.IOException;
import java.util.ArrayList;

import static com.rumisystem.rumi_inventory_menu.Config.MENU_COMMAND;
import static com.rumisystem.rumi_inventory_menu.Config.MENU_DATA;

public class Reload {
	  public static void main() throws IOException {
			MENU_DATA = new ArrayList<>();
			MENU_COMMAND = new ArrayList<>();

			Config.main();
	  }
}
