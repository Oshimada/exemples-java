package com.tph.ingame.tools.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tph.Resources;
import com.tph.IA.IATest;
import com.tph.IA.MapTile;
import com.tph.IA.pathfinding.Path;

public class InfoCase {
	protected Window box;
	protected HorizontalGroup titregroup;
	protected VerticalGroup infogroup;
	protected Label titre,line;
	protected byte cellid=0;
	protected Image image;
	protected Skin skin;
	
	public InfoCase() {

		
		BitmapFont font= Resources.getFont("font/euphorigenic.ttf", 20, Color.WHITE); //"font/koren.ttf"
		LabelStyle style=new LabelStyle(font,Color.WHITE);
		
		skin=Resources.getManager().get("ui/menuSkin.json");
		infogroup=new VerticalGroup();
		titre=new Label("",skin,"small");
		line=new Label("",skin,"small");
		titre.setStyle(style);
		line.setStyle(style);
		image=new Image();
		box=new Window("",skin,"mod");
		box.pad(10);
		infogroup.addActor(line);
		box.add(image).left().spaceLeft(Gdx.graphics.getWidth()/8).spaceRight(Gdx.graphics.getWidth()/12);
		box.add(titre).left().row();
		box.add(infogroup).colspan(2);
	}
	protected void cellTitle(int x,int y,Path finder){
		
		int[][] map= finder.getCollisionlayer();
		cellid=(byte) map[x][y];
		image.setDrawable(new TextureRegionDrawable(IATest.map.getTileSets().getTile(MapTile.TEX[cellid]+1).getTextureRegion()));
		String title=MapTile.cellname[cellid],
			   infos=MapTile.cellinfos[cellid];
		titre.setText(title);
		line.setText(infos);
	}
	public Table getBox() {
		return box;
	}


}









/* 				-Character Handle-
 		
	Les Options d'un personnage traitees dans des Fonctions.
	les fonctions generent et retournent un fils ( Cell ). 
	les fils vont etre ajoutes dans un ( container ).
	le conteneur correspondant va etre affiche pour le personnage selectionne.

*/