package com.tph.ingame.tools.map;

import com.animation.LibGdxDrawer;
import com.animation.LibGdxLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Mainline.Key;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Player.PlayerListener;
import com.brashmonkey.spriter.SCMLReader;
import com.tph.IA.IATest;

public class SplashTurn  {
	
	public int turn = 0 ;
	private boolean ended = false;
	private Player player ;
	protected LibGdxDrawer drawer;
	protected LibGdxLoader loader;
	private FileHandle scmlHandle;
	private SCMLReader reader;
	public ShapeRenderer renderer;
	
	public SplashTurn( float f ) {

		renderer=new ShapeRenderer();
		scmlHandle = Gdx.files.internal("turn/turn.scml");
		reader = new SCMLReader(scmlHandle.read());
		
		Data data=reader.getData();
		loader= new LibGdxLoader(data);
		loader.load("turn/");
		drawer=new LibGdxDrawer(loader, (SpriteBatch) IATest.uistage.getBatch(),renderer );
		
		player=new Player(data.getEntity("entity"));
		player.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		player.setAnimation("yourturn");
		player.speed=18;
		player.setScale(f);
		
		player.addListener(new PlayerListener() {
			
			@Override
			public void preProcess(Player player) {
				
			}
			
			@Override
			public void postProcess(Player player) {
				
			}
			
			@Override
			public void mainlineKeyChanged(Key prevKey, Key newKey) {
				
			}
			
			@Override
			public void animationFinished(Animation animation) {
				ended = true;
			}
			
			@Override
			public void animationChanged(Animation oldAnim, Animation newAnim) {
				
			}
		});
		
	}
	public void setTurn(String animation) {
		player.setAnimation(animation);
		turn++;
		ended = false;
	}
	public void draw()
	{
		if( !ended )
		{
			IATest.uistage.getBatch().begin();	
				renderer.begin(ShapeType.Filled);
					player.update();
					drawer.draw(player);
				renderer.end();
			IATest.uistage.getBatch().end();
		}
	}
	public boolean isEnded() {
		return ended;
	}
	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
}
