# RumiInventoryMenu
___
[Click here for the English version of the README](./README_EN.md)<BR>
<BR>
このプラグインは、Spigot用で、インベントリのメニューを作るためのプラグインです。<BR>
簡単に使える(当社比)です。<BR>
<BR>
<BR>
## インストール
Spigot鯖のPluginsフォルダに入れてください。<BR>
<BR>
## 使い方
Pluginsフォルダの、/Rumi_Inventory_Menuの中に、「Menu」というディレクトリがあるので、<BR>
その中に、好きな名前でJSONファイルを作ってください。<BR>
内容は以下の通りに書いてください。<BR>
```json
{
	"ITEM":"アイテムのマテリアル",
	"ITEM_NAME":"アイテムの名前",
	"ITEM_DESC":"アイテムの説明",
	"TITLE":"タイトル(英語ラテン文字推奨)",
	"SIZE":サイズ(INT型で),
	"DATA":[
		{
			"POS":場所(INT型で),
			"ITEM":"アイテムのマテリアル",
			"NAME":"アイテムの名前",
			"DESC":"アイテムの説明",
			"COMMAND":[
				"ここにコマンドをうつ"
			]
		}
	]
}
```
「アイテムのマテリアル」は、Spigotプラグインを作った人ならわかるかも((<BR>
ダイヤモンドなら「DIAMOND」という感じに入力してください。<BR>
ここにコマンド一覧を書いておきます。<BR>
<TABLE border>
	<TR>
		<TH>コマンド</TH>
		<TH>使い方</TH>
		<TH>意味</TH>
	</TR>
	<TR>
		<TD>CLOSE<TD>
		<TD>CLOSE</TD>
		<TD>メニューを閉じます</TD>
	</TR>
	<TR>
		<TD>SAY<TD>
		<TD>SAY \"表示したい内容\"</TD>
		<TD>プレイヤーに、メッセージを送ります。</TD>
	</TR>
	<TR>
		<TD>SERVER<TD>
		<TD>SERVER \"宛先の鯖名\"</TD>
		<TD>BungeeのAPIを使って、別の鯖に移動します。</TD>
	</TR>
</TABLE>
「\"」は、必ずダブルコーテーションで書いてください、シングルコーテーションは認識しません。<BR>