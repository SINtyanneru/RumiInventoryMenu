# RumiInventoryMenu
___
[Click here for the English version of the README](./README_EN.md)<BR>
<BR>
This plugin is for Spigot and is used to create an inventory menu.<BR>
Easy to use (compared to ours).<BR>
<BR>
<BR>
## Installation
Put it in the Plugins folder of the Spigot mackerel.<BR>
<BR>
## Use
In the Plugins folder, in /Rumi_Inventory_Menu, there is a directory called "Menu",<BR>
In it, create a JSON file with any name you like.<BR>
The contents should be written as follows<BR>
```json
{
	"ITEM":"Material of the item",
	"ITEM_NAME":"Item Name",
	"ITEM_DESC":"Item Description",
	"TITLE":"Title (English Latin recommended)",
	"SIZE":Size (in INT type),
	"DATA":[
		{
			"POS":Location (in INT type),
			"ITEM":"Material of the item",
			"NAME":"Item Name",
			"DESC":"Item Description",
			"COMMAND":[
				"Type your command here"
			]
		}
	]
}
```
The "item material" may be familiar to anyone who has created a Spigot plugin ((<BR>
For diamonds, enter something like "DIAMOND".<BR>
Here is a list of commands.<BR>
<TABLE border>
	<TR>
		<TH>Command</TH>
		<TH>Use</TH>
		<TH>meaning</TH>
	</TR>
	<TR>
		<TD>CLOSE<TD>
		<TD>CLOSE</TD>
		<TD>Close the menu</TD>
	</TR>
	<TR>
		<TD>SAY<TD>
		<TD>SAY \"What you want to display\"</TD>
		<TD>Send a message to the player.</TD>
	</TR>
	<TR>
		<TD>SERVER<TD>
		<TD>SERVER \"Destination mackerel name\"</TD>
		<TD>Use Bungee's API to move to another mackerel.</TD>
	</TR>
</TABLE>
The " \" " must be written in double-cotation marks, single-cotation marks are not recognized.<BR>