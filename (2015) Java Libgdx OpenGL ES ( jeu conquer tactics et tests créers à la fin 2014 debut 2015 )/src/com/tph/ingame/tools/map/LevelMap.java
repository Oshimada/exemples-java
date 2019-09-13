package com.tph.ingame.tools.map;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


public class LevelMap extends TiledMap{
	
	protected TextureFilter filter;
	private static byte counter = 0 ; 
	public static final byte
		BACKGROUND_LAYER = counter ++ ,
			  //GRID_LAYER = counter ++ ,
		 COLLISION_LAYER = counter ++ ,
		    DECORS_LAYER = counter ++ ,
		    CURSOR_LAYER = counter ++ ,
			 RANGE_LAYER = counter ++ ;
		  // SHADOW1_LAYER = counter ++ ,
		  // SHADOW2_LAYER = counter ++ ,
			// PARA1_LAYER = counter ++ ,
		   //  PARA2_LAYER = counter ++ ;


	public static final int[] 
			 GROUND = new int[]{ BACKGROUND_LAYER  , COLLISION_LAYER , DECORS_LAYER }, //, GRID_LAYER
			CURSORS = new int[]{ CURSOR_LAYER , RANGE_LAYER };//,
			//  PARA1 = new int[]{SHADOW1_LAYER,PARA1_LAYER},
			//  PARA2 = new int[]{PARA2_LAYER};
	
	
	public LevelMap (){
		super();
	}
	
	public float getTileWidth(){
		TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(BACKGROUND_LAYER);
		return layer.getTileWidth();
	}
	
	public float getTileHeight(){
		TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(BACKGROUND_LAYER);
		return layer.getTileHeight();
	}
	
	public int getWidth(){
		TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(BACKGROUND_LAYER);
		return layer.getWidth();
	}
	
	public int getHeight(){
		TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(BACKGROUND_LAYER);
		return layer.getHeight();
	}
	
	public TiledMapTileLayer getTiledMapTileLayer(int i){
		return (TiledMapTileLayer) getLayers().get(i);
	}
	
}
