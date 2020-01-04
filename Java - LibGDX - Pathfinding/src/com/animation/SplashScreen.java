package com.animation;

import com.DialogEditor.EditorScreen;
import com.Menu.Menu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.tph.Resources;
import com.tph.IA.IATest;

public class SplashScreen implements Screen{
	
	protected Player player;
	protected float timer=0;
	protected LibGdxDrawer drawer;
	protected LibGdxLoader loader;
	private FileHandle scmlHandle;
	private SCMLReader reader;
	public ShapeRenderer renderer;
	private BitmapFont font12,title;
	
	@Override
	public void show() {
		
		Texture.setAssetManager(Resources.getManager());
		
		renderer=new ShapeRenderer();
		scmlHandle = Gdx.files.internal("necro/nico-birj.scml");
		reader = new SCMLReader(scmlHandle.read());
		Data data=reader.getData();
		loader= new LibGdxLoader(data);
		loader.load("necro/");
		drawer=new LibGdxDrawer(loader, (SpriteBatch) Resources.getBatch(),renderer );
		player=new Player(data.getEntity("entity"));
		player.setPosition(Gdx.graphics.getWidth()/2-180, Gdx.graphics.getHeight()/2-120);
		player.setAnimation("standing");
		player.speed=6;
		player.setScale(.5f);
		
		font12=Resources.getFont("font/curlz.TTF", 32,Color.BLACK);
		title=Resources.getFont("font/koren.ttf", 50,Color.BLACK);
		Resources.LoadFont();
		Resources.manager.finishLoading();
		Resources.setFont(font12);
		Resources.LoadMenu();
	
		Pixmap pm = new Pixmap(Gdx.files.internal("curseur2.png"));
		Gdx.input.setCursorImage(pm, 0, 0);
		pm.dispose();

	}
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(.7f,.7f,.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Resources.getBatch().begin();	// + begin batch
			renderer.begin(ShapeType.Filled); // + begin render
				player.update();
				drawer.draw(player);
				font12.draw(Resources.getBatch(),"LOADING... "+(int)(Resources.manager.getProgress()*100)+"% .",Gdx.graphics.getWidth()/2-60,Gdx.graphics.getHeight()/2);
				title.draw(Resources.getBatch(),"Divide And Conquer",Gdx.graphics.getWidth()/2-180,Gdx.graphics.getHeight()*.9f);
				title.setColor(0, 0, 0, (.5f));
				font12.setColor(0, 0, 0, (font12.getColor().a+0.015f));
			renderer.end();			// - end render
		Resources.getBatch().end(); // - end batch
		timer+=delta;
			if(Resources.manager.update() && timer > 2 )
            {
                Resources.getStage().setSkin((Skin) (Resources.getManager().get("ui/menuSkin.json")));
                ((Game)Gdx.app.getApplicationListener()).setScreen(new IATest());
		    }
			
	}
	@Override
	public void resize(int width, int height) {
		
	}
	@Override
	public void pause() {
		
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void hide() {	
		renderer.dispose();
		
	}
	@Override
	public void dispose() {

		renderer.dispose();
	}
}
