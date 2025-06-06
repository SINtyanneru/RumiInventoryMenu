package su.rumishistem.rumiinventorymenu.TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Menu {
	private String Title;
	private int Size;
	private MenuItem[] Content;

	public Menu() {
		Title = "No Title";
		Size = 54;
		Content = new MenuItem[Size];

		for (int I = 0; I < Size; I++) {
			Content[I] = null;
		}
	}

	public Menu(JsonNode Data) {
		Title = Data.get("TITLE").asText();
		Size = Data.get("SIZE").asInt();
		Content = new MenuItem[Size];

		for (int I = 0; I < Data.get("CONTENTS").size(); I++) {
			JsonNode Row = Data.get("CONTENTS").get(I);
			Material M = Material.getMaterial(Row.get("ITEM").asText());
			if (M != null) {
				List<String> LeftClickEvent = new ArrayList<String>();
				for (int J = 0;J < Row.get("LEFT_CLICK").size(); J++) {
					LeftClickEvent.add(Row.get("LEFT_CLICK").get(J).asText());
				}

				Content[Row.get("POSITION").asInt()] = new MenuItem(
					Row.get("POSITION").asInt(),
					Row.get("NAME").asText(),
					Row.get("DESC").asText(),
					M,
					LeftClickEvent
				);
			}
		}
	}

	public String genJson() throws IOException {
		LinkedHashMap<String, Object> Body = new LinkedHashMap<String, Object>();
		Body.put("TITLE", Title);
		Body.put("ITEM", null);
		Body.put("SIZE", Size);

		List<JsonNode> Contents = new ArrayList<JsonNode>();
		for (int I = 0; I < Size; I++) {
			if (Content[I] != null) {
				Contents.add(Content[I].genJSON());
			}
		}
		Body.put("CONTENTS", Contents);

		return new ObjectMapper().writeValueAsString(Body);
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public int getSize() {
		return Size;
	}

	public MenuItem[] getContent() {
		return Content;
	}

	public void setContent(int Position, MenuItem Item) {
		Content[Position] = Item;
	}

	public MenuItem getItem(int I) {
		for (MenuItem Item:getContent()) {
			if (Item == null) continue;

			if (Item.getPosition() == I) {
				return Item;
			}
		}

		return null;
	}
}
