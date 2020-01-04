package com.tph.IA;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapTile {
	public static final byte
		 PLAIN=0,
	 DIFFICULT=1,
	 	  LAKE=2,
	 	  ROCK=3,
	 	   SEA=4,
   UNREACHABLE=5,
   		   HUT=6,
		 CHEST=7,
		   POT=8,
		  GHUT=9,
		 LOGS=10;
	public static final String
		cellname[]={
		"Green Hills", // move izi
		"Forest", // can hide in forest - hard movement
		"Lake",   // cavalry can't cross rivers - hard movement
		"Rock",   // blocked for all exept flying units
		"Sea",    // can dive - blocked for all exept sea/flying units
		"UNKNOWN",// blocked
		"Hut",	  // sleep well bitch
		"Chest",   // what's better than abandoned treasures nowhere...
		"Boiling Pot",
		"Giant Hut",
		"Logs"
	};
	public static final String cellinfos[]={
	"Smells like good grass. you  \ncan move easily in these weeds",
	"It's hard to move in forests \nbut can't be spotted easily	  ",
	"everyone can swim in lakes   \nexept cavalry... they don't	  ",
	"Can't move on rocks, exept   \nif you are flying			  ",
	"Only good swimmers and flying\nunits can cross the sea	  	  ",
	"well, you dont need to know  \nmore about unreachable areas...",
	"Your wounds have to recover  \nhere mate					  ",
	"what's better than abandoned \ntreasures nowhere...		  ",
	"Need some pot homie ?		  \n						      ",
	"Looks fishy... 			  \nbetter stay away			  ",
	"Useless Logs...			  \nare useless					  "
};
	public static final int
		TEX[]={
		0,  //TEX_PLAIN
		129,//TEX_DIFFICULT
		11, //TEX_LAKE
		27, //TEX_ROCK
		133,//TEX_SEA
		226,//TEX_UNREACHABLE
		60, //TEX_HUT
		31, //TEX_CHEST
		81, //TEX_POT
		62, //TEX_GHUT
		30  //TEX_LOGS
	};
	public static ArrayList<TileInfos> tiles;
	protected int[][] collisionlayer;
	protected int width,height;
	
	public MapTile(TiledMap map){
		
	}
	
	public MapTile(int[][] collisionLayer,int width, int height){ //Node startPoint,Node endPoint,
		this.collisionlayer=collisionLayer;
		this.width=width;
		this.height=height;
	}
	public MapTile(TiledMapTileLayer tiledCollisionLayer){
		width=tiledCollisionLayer.getWidth();
		height=tiledCollisionLayer.getHeight();
		collisionlayer=new int[width][height];
		for(int i=0;i<width;i++)
			for(int j=0;j<height;j++){
				MapProperties prop=tiledCollisionLayer.getCell(i,j).getTile().getProperties();
				
				if(prop.containsKey("difficult"))
					collisionlayer[i][j]=DIFFICULT;
				else if(prop.containsKey("lake"))
					collisionlayer[i][j]=LAKE;
				else if(prop.containsKey("rock"))
					collisionlayer[i][j]=ROCK;
				else if(prop.containsKey("hut"))
					collisionlayer[i][j]=HUT;
				else if(prop.containsKey("giant_hut"))
					collisionlayer[i][j]=GHUT;
				else if(prop.containsKey("sea"))
					collisionlayer[i][j]=SEA;
				else if(prop.containsKey("pot"))
					collisionlayer[i][j]=POT;
				else if(prop.containsKey("chest"))
					collisionlayer[i][j]=CHEST;
				else if(prop.containsKey("logs"))
					collisionlayer[i][j]=LOGS;
				else if(prop.containsKey("blocked"))
					collisionlayer[i][j]=UNREACHABLE;
				else  //DEFAULT TILE
					collisionlayer[i][j]=PLAIN;
			}
		
		/*
		 * 
		 tiles=new ArrayList<MapTile.TileInfos>();
		int id=1;
		TiledMapTileSets tileset =IATest.map.getTileSets();
		for(int i=1;i<256;i++)
			if(tileset.getTile(i).getProperties().containsKey("name"))
			{
				TiledMapTile ti= tileset.getTile(i);
				tiles.add(new TileInfos(
								ti.getId(),
								(String)ti.getProperties().,
								(String)ti.getProperties().get("infos")));
			}
		for(TileInfos in:tiles)
			System.out.println(in.name+" "+in.Infos+" "+in.id);
			*/
	}
	public int[][] getCollisionlayer() {
		return collisionlayer;
	}
	public void setCollisionlayer(int[][] collisionlayer) {
		this.collisionlayer = collisionlayer;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	class TileInfos{
		String name,
			   Infos;
		int id;
		public TileInfos(int id,String name,String infos) {
			
		}
	}
}
