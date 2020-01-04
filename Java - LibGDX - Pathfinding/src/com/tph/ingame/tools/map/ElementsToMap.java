package com.tph.ingame.tools.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.tph.Resources;
import com.tph.IA.IATest;
import com.tph.ingame.tools.characters.Player;
import com.tph.ingame.tools.ui.CellActor;

public class ElementsToMap {

	public ElementsToMap(TiledMapTileLayer layer2) {

		MapObjects mapallies = IATest.map.getLayers().get("allies")
				.getObjects();
		MapObjects mapenemies = IATest.map.getLayers().get("enemies")
				.getObjects();
		
		for (MapObject obj : mapallies) {
			MapProperties prop = obj.getProperties();
			
			float a = (Float) prop.get("x") / layer2.getTileWidth();
			float b = (Float) prop.get("y") / layer2.getTileHeight();
			String classe =(String) prop.get("classe") ;
			String nom = (String) obj.getName();
			String description = (String) prop.get("Description");
			String slvl = (String)(prop.get("lvl"));
			int lvl=1;
			try{
				lvl=Integer.parseInt(slvl);
				if(lvl<1)
					lvl=1;
			}
			catch(NumberFormatException e){
				lvl=1;
			}
			put( classe,nom,description,(int)a,(int)b,layer2,lvl);
		}
		for (MapObject obj : mapenemies) {

			MapProperties prop = obj.getProperties();
			float a = (Float) prop.get("x") / layer2.getTileWidth();
			float b = (Float) prop.get("y") / layer2.getTileHeight();
			String description = (String) prop.get("Description");
			String classe =(String) prop.get("classe") ;
			String nom = (String) obj.getName();
			String slvl = (String)(prop.get("lvl"));
			int lvl=1;
			try{
				lvl=Integer.parseInt(slvl);
				if(lvl<1)
					lvl=1;
			}
			catch(NumberFormatException e){
				lvl=1;
			}
			putEnemy(classe,nom, description,(int)a,(int)b,layer2,lvl);
		}
	}

	private void put(String string , String nom , String description,int x, int y, TiledMapTileLayer layer2,int lvl) {
		
		CellActor actor=Resources.stage.getActor(x, y);
		actor.setElement(new Player( string, nom, description, layer2,lvl));
		actor.getElement().setSize(layer2.getTileWidth(), layer2.getTileHeight());
		actor.getElement().placer(x, y);
		IATest.players.addAllie((Player) actor.getElement());
	}

	private void putEnemy(String string, String nom , String description, int x, int y, TiledMapTileLayer layer2,int lvl) {
		
		CellActor actor=Resources.stage.getActor(x, y);
		actor.setElement(new Player(string, nom, description, layer2, lvl));
		actor.getElement().setSize(layer2.getTileWidth(), layer2.getTileHeight());
		actor.getElement().setFlip(true,false);
		//actor.getElement().setColor(.9f, .8f, .8f, 1);
		actor.getElement().placer(x, y);
		IATest.players.addEnemi((Player) actor.getElement());
	}
}
