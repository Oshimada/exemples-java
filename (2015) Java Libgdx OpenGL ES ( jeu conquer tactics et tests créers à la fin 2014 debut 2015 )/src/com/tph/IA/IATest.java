package com.tph.IA;

import java.util.Iterator;

import com.animation.Fight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tph.Resources;
import com.tph.IA.pathfinding.PathFinder;
import com.tph.ingame.tools.characters.Player;
import com.tph.ingame.tools.characters.PlayerGroup;
import com.tph.ingame.tools.map.LevelMap;
import com.tph.ingame.tools.map.TiledMapStage;
import com.tph.ingame.tools.map.ZoomInput;
import com.tph.ingame.tools.ui.DialogStarter;
import com.tph.ingame.tools.ui.SplashLevel;

public class IATest implements Screen{
	
	protected Skin skin; 
	protected TiledMapStage stage;
	public static DialogStarter dialog = null ;
	protected OrthogonalTiledMapRenderer renderer ;
	protected float tileWidth , tileHeight;
    protected SplashLevel splash;
	protected TiledMapTileLayer	layer , layer2;
	protected Table table;
	protected Vector2 keyCamera=new Vector2();
	
	public static PlayerGroup players=new PlayerGroup();
	public static Fight fight = null;
	public static Stage uistage; 
	public static TiledMap map;
	
	protected static float x = Gdx.graphics.getWidth()/2 , timer = 0;
	protected static float y = Gdx.graphics.getHeight()/2;
	protected static OrthographicCamera camera , ui;

    protected static double times[]=new double[6];


	public IATest(){
		stage=Resources.getStage();
		skin=stage.Skin();
	}
	@Override
	public void show() {
        Gdx.input.setCatchBackKey(true);
		keyCamera.x=0;keyCamera.y=0;
		new PathFinder();
		table=(Table)stage.getTable();
		uistage=new Stage(){
			@Override
			public boolean keyDown(int key) {
			
				if(key == Keys.UP)
					keyCamera.y=10;
				if(key == Keys.DOWN)
					keyCamera.y=-10;
				if(key == Keys.LEFT)
					keyCamera.x=-10;
				if(key == Keys.RIGHT)
					keyCamera.x=10;
				
				if(key == Keys.SPACE || key == Keys.BACK)
					for(Player p:players.getAllies())
						p.endTurn();

				return false;
			}// 
			@Override
			public boolean keyUp(int key) {
				if(key == Keys.UP)
				if(key == Keys.DOWN)
					keyCamera.y=0;
				if(key == Keys.LEFT)
				if(key == Keys.RIGHT)
					keyCamera.x=0;
				return false;
			}
			
		};
		uistage.addActor(table);
		
		TmxMapLoader mapLoader=new TmxMapLoader();
		map= mapLoader.load("level0_1.tmx");
		
		for(TiledMapTileSet tileSet : map.getTileSets()) {
			   Iterator<TiledMapTile> it = tileSet.iterator();
			   while(it.hasNext())
			      it.next().getTextureRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.MipMapNearestNearest);
			}
		
		TiledMapTileLayer tile=(TiledMapTileLayer)map.getLayers().get(LevelMap.COLLISION_LAYER); 
		tileWidth = tile.getTileWidth();tileHeight=tile.getTileHeight();

		layer  = (TiledMapTileLayer)map.getLayers().get(LevelMap.CURSOR_LAYER);
		layer2 = (TiledMapTileLayer)map.getLayers().get(LevelMap.RANGE_LAYER);
		
		stage.setupMapToStage(tile,layer);
		
		renderer      = new OrthogonalTiledMapRenderer(map, stage.getBatch());
			
		camera 		  = new OrthographicCamera();
		ui 			  = new OrthographicCamera();
		
		float tx=(stage.getFinder().getWidth()/2)*tileWidth, ty=(stage.getFinder().getHeight()/2)*tileHeight;
		camera.position		.set(tx,ty, 0);		ui.position			.set(tx,ty, 0);
		
		stage.getViewport().setCamera(camera);
		
		InputMultiplexer mplex=new InputMultiplexer();
		ZoomInput zewm=new ZoomInput(camera);
		
		//dialog=new DialogStarter("introdialog");
		//dialog.addTo(uistage);

        mplex.addProcessor(new GestureDetector(zewm));
		mplex.addProcessor(uistage);
		mplex.addProcessor(stage);
		Gdx.input.setInputProcessor(mplex);
        Resources.thread.start();
        players.setupIA();

		Fight.Load("necro", "gunner","gunner bad","warrior","warrior bad","alchemist","alchemist bad","fear","slayer"); //

	}
	@Override
	public void render(float delta) {
		
		Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		Gdx.gl20.glClearColor( 50/256f, 33/256f, 37/256f, 1 );
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT );

		manageCameras(delta);
		renderer.setView(camera);
		
	    renderer.render(LevelMap.GROUND);
	    if(fight == null && dialog == null)
	    {
	    	timer += delta * 2;
	    	renderer.render(LevelMap.CURSORS);
	    	layer.setOpacity( (float) Math.abs( Math.sin(timer))*.6f + .4f );
	    	layer2.setOpacity((float) Math.abs( Math.sin(timer))*.3f + .7f  );
	    }
	    stage.getBatch().begin();
	    	players.drawPlayers(stage.getBatch());
	    stage.getBatch().end();
		
		stage.act(delta);
		stage.draw();
		
		uistage.act();
		//table.debug();
		uistage.draw();
		
		if(fight!=null)
		{
			if(fight.isEnded())
			{
				fight.dispose();
				fight=null;
			}
			else 
				fight.draw(uistage.getBatch(),delta);
		}
		if( dialog != null )
		{
			dialog.update(delta);
			if( dialog.isDone() )
				dialog = null ;
		}
	    
	}

	@Override
	public void resize(int width, int height) {
		float m=Gdx.graphics.getWidth()>Gdx.graphics.getHeight()?Gdx.graphics.getHeight():Gdx.graphics.getWidth(),
				VIEWPORT_SCALE=768/m;
	    
		ui.viewportWidth =  camera.viewportWidth = (int) width/VIEWPORT_SCALE;
		ui.viewportHeight = camera.viewportHeight =(int) height/VIEWPORT_SCALE;

	    stage.getViewport().update(width, height, true);
	    uistage.getViewport().update(width, height, true);
	}
	public static void moveCamera(float x,float y){
			IATest.x=x;
			IATest.y=y;
	}
	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
        try{
            Resources.thread.join();
        }
        catch(Exception e){

        }
		Gdx.app.exit();
	}	

	private void manageCameras(float delta) {
		float dx=camera.position.x-x;
		float dy=camera.position.y-y;
		float cameraspeedX=2*delta*(Math.abs(dx));
		float cameraspeedY=2*delta*(Math.abs(dy));
		float cameraflatspeed=delta*10;


		float bound = 0;
		
		if(camera.position.x != x || camera.position.y != y){

			if(camera.position.y < tileHeight*(layer.getHeight()-bound))
				if(dy<bound && Math.abs(dy)>cameraspeedY){
					camera.position.y += cameraspeedY;
					dy = camera.position.y-y;
				}
			if(camera.position.y > bound * tileHeight )
				if( dy > bound && Math.abs(dy) > cameraspeedY ){
					camera.position.y -= cameraspeedY;
					dy = camera.position.y - y ;
				}
			if( Math.abs(dy) <= cameraflatspeed )
				camera.position.y = y;


			if( camera.position.x < tileWidth*(layer.getWidth() - bound))
				if( dx<bound && Math.abs(dx) > cameraspeedX ){
					camera.position.x += cameraspeedX;
					dx = camera.position.x - x;
				}
			if(camera.position.x > bound * tileWidth )
				if( dx > bound && Math.abs(dx) > cameraspeedX ){
					camera.position.x -= cameraspeedX;
					dx = camera.position.x - x;
				}
			if( Math.abs(dx) <= cameraflatspeed )
				camera.position.x = x;
		}

        if(camera.position.x > tileWidth*layer.getWidth() - camera.viewportWidth/2*camera.zoom )
            camera.position.x = tileWidth*layer.getWidth() - camera.viewportWidth/2*camera.zoom ; //- camera.viewportWidth/2

        if(camera.position.y > tileHeight*layer.getHeight() - camera.viewportHeight/2*camera.zoom )
            camera.position.y = tileHeight*layer.getHeight()- camera.viewportHeight/2*camera.zoom ;

        if(camera.position.x  < camera.viewportWidth/2*camera.zoom)
            camera.position.x = camera.viewportWidth/2*camera.zoom;

        if(camera.position.y < camera.viewportHeight/2*camera.zoom)
            camera.position.y =   camera.viewportHeight/2*camera.zoom;

		ui.position.x=table.getX();
		ui.position.y=table.getY();

		camera.update();
		ui.update();
		
	}
}
