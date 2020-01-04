package com.tph;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tph.IA.pathfinding.PathThread;
import com.tph.ingame.tools.map.LevelMap;
import com.tph.ingame.tools.map.TiledMapStage;
import com.tph.ingame.tools.ui.HpBar;

public class Resources {
	public static final TiledMapStage stage=new TiledMapStage(new StretchViewport(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight()));//;
	public static final AssetManager manager=new AssetManager();
	protected static BitmapFont font;
    public static final PathThread thread =new PathThread();
	
	public static void LoadMenu(){
		System.out.println("w "+Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());
		manager.load("ui/atlas.pack", TextureAtlas.class);

        //manager.load( "mh.jpg", Texture.class);
        //manager.load("dur.png", Texture.class);
        //manager.load("tur.png", Texture.class);
		manager.load("figure/gunner.png", Texture.class);
		manager.load("figure/mighty_slayer.png", Texture.class);

		manager.load("sliders/hpbars/cord.png", Texture.class);
		manager.load(HpBar.DEFAULT_BACKGROUND, Texture.class);
		manager.load(HpBar.HPBAR  , Texture.class);
		manager.load(HpBar.KNOB   , Texture.class);
	    manager.load("ui/menuSkin.json", Skin.class, new SkinLoader.SkinParameter("ui/atlas.pack"));
	    
	}
	
	public static TiledMap LoadMission(String campaign,String info1){
		
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load(campaign+info1+".tmx", TiledMap.class);

		TmxMapLoader mapLoader=new TmxMapLoader();
		TiledMap map= mapLoader.load("forest.tmx");
		
		for(TiledMapTileSet tileSet : map.getTileSets()) {
		   Iterator<TiledMapTile> it = tileSet.iterator();
		   while(it.hasNext())
		      it.next().getTextureRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.MipMapNearestNearest);
		}
		
		TiledMapTileLayer tile=(TiledMapTileLayer)map.getLayers().get(LevelMap.COLLISION_LAYER);
		TiledMapTileLayer layer=(TiledMapTileLayer)map.getLayers().get(LevelMap.CURSOR_LAYER);

		stage.setupMapToStage(tile,layer);
		return map;
	}
	
	public static void LoadMission(String campaign){
		
		Resources.LoadMission(campaign,"");
	}
	
	public static void Dispose(){
		
		manager.dispose();
		stage.dispose();
	
	}
	public static void LoadFont(){
		
	    manager.load("font/white32.fnt",BitmapFont.class);
	    
	}
	public static BitmapFont getFont(String title,int size,Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(title));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color=color;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        return font12;
    }
	
	
	
	public static TiledMapStage getStage() {
		return stage;
	}
	public static AssetManager getManager() {
		return manager;
	}
	public static BitmapFont getFont() {
		return font;
	}
	public static void setFont(BitmapFont font) {
		Resources.font = font;
	}
	public static Batch getBatch() {
		return stage.getBatch();
	}
	
	
}
