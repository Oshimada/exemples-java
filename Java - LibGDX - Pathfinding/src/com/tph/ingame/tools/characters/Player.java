package com.tph.ingame.tools.characters;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player extends Character {

	public Player(String url, String nom,
			String description, TiledMapTileLayer layer,int lvl) {
		super(url, nom, description, layer,lvl);
	}

	
	public boolean isDead(){
		if(stats.getHp()<=0)
			return true;
		return false;
	}

    @Override
    public String toString() {
        int size = getRangecells()!= null ? getRangecells().size : -1;
        return getNom()+" , "+size+" , "+isTurn();
    }
}
