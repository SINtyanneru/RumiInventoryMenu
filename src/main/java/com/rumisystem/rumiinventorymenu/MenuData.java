package com.rumisystem.rumiinventorymenu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuData {
	private RumiInventoryMenu PLUGIN_INSTANCE;

	public MenuData(){
		PLUGIN_INSTANCE = RumiInventoryMenu.getInstance();
	}

	public JsonNode LoadMenu(String MENU_NAME){
		try{
			//作業フォルダを取得
			File WORK_DIR = PLUGIN_INSTANCE.getDataFolder();

			//フォルダが存在するか
			if (!WORK_DIR.exists()) {
				//無いので作る
				WORK_DIR.mkdirs();
				Path MENU_DIR_PATH = Paths.get(WORK_DIR.getPath() + "/MENU");
				Files.createDirectory(MENU_DIR_PATH);
			}

			//メニューのファイルパス
			Path MENU_FILE_PATH = Paths.get(WORK_DIR.getPath() + "/MENU/" + MENU_NAME + ".json");

			//メニューのファイルが有るか
			if(Files.exists(MENU_FILE_PATH)){//あるのでJSONを解析
				ObjectMapper OBJ_MAPPER = new ObjectMapper();
				JsonNode MENU_JSON = OBJ_MAPPER.readTree(MENU_FILE_PATH.toFile());

				//解析結果をけえす
				return MENU_JSON;
			}else {//ないので適当にnullでも返す
				PLUGIN_INSTANCE.getLogger().warning("FILE NONE");
				return null;
			}
		}catch (Exception EX){//例外発生ちんちん
			PLUGIN_INSTANCE.getLogger().warning(EX.getMessage());
			return null;
		}
	}
}
