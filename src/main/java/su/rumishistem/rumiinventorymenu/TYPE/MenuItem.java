package su.rumishistem.rumiinventorymenu.TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MenuItem {
	private String Name;
	private String Desc;
	private Material ItemMaterial;
	private int POSITION;
	private List<String> LeftClickEvent = new ArrayList<String>();

	public MenuItem(int POSITION, String Name, String Desc, Material ItemMaterial, List<String> LeftClickEvent) {
		this.POSITION = POSITION;
		this.Name = Name;
		this.Desc = Desc;
		this.ItemMaterial = ItemMaterial;
		this.LeftClickEvent = LeftClickEvent;
	}

	public JsonNode genJSON() throws JsonProcessingException, IOException {
		LinkedHashMap<String, Object> Body = new LinkedHashMap<String, Object>();
		Body.put("POSITION", POSITION);
		Body.put("NAME", Name);
		Body.put("DESC", Desc);
		Body.put("ITEM", ItemMaterial.name());
		Body.put("LEFT_CLICK", LeftClickEvent);

		return new ObjectMapper().readTree(new ObjectMapper().writeValueAsBytes(Body));
	}

	public ItemStack getItem() {
		ItemStack Item = new ItemStack(ItemMaterial, 1);

		//メタデータ
		ItemMeta Meta = Item.getItemMeta();
		Meta.setDisplayName(Name);
		if (Desc.split("\n").length != 0) {
			Meta.setLore(Arrays.asList(Desc.split("\n")));
		}
		Item.setItemMeta(Meta);

		return Item;
	}

	public Material getItemMaterial() {
		return ItemMaterial;
	}

	public int getPosition() {
		return POSITION;
	}

	public String getName() {
		return Name;
	}

	public String getDesc() {
		return Desc;
	}

	public String[] getLeftClickEvent() {
		String[] Body = new String[LeftClickEvent.size()];
		for (int I = 0; I < LeftClickEvent.size(); I++) {
			Body[I] = LeftClickEvent.get(I);
		}
		return Body;
	}
}
