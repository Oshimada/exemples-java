package com.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.PlayerTweener;
import com.brashmonkey.spriter.Spriter;
import com.tph.Resources;
import com.tph.ingame.tools.characters.Stats;
import com.tph.ingame.tools.ui.HpBar;

public class ViewFight implements Screen{
	protected Player p1,p2;
	protected PlayerTweener tweener1,tweener2;
	protected float timer=0;
	protected ShapeRenderer renderer=new ShapeRenderer();
	
	protected Stats stats1,stats2;
	protected HpBar bar1,bar2;
	
	protected  FightingPlateform plateform;
	protected String[] files;
	
	/** 
	 * Loads a sequence from the given file
	 * setup the ui for the given stats ( HP ... etc )
	 * the sequence contains the list of animations for the given attacks and reactions
	*/
	
	public ViewFight(Stats stats1, Stats stats2) {
		
		this.stats1=stats1;
		this.stats2=stats2;
		
		bar1=new HpBar(stats1.getHP_MAX(), stats1.getRace());
		bar2=new HpBar(stats2.getHP_MAX(), stats2.getRace());

		bar1.setHp(stats1.getHp());
		bar2.setHp(stats2.getHp());
		
		Table table=new Table();
		table.setFillParent(true);
		table.top();
		table.add(bar1.getBar()).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/4).space(50).top();
		table.add(bar2.getBar()).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/4).top().row();
		
		Resources.stage.addActor(table);
		 
		
	}
	public ViewFight() {
		
		//this(new Stats(Classe.ARCHER, Classe.HUMAN) , new Stats(Classe.ARCHER, Classe.HUMAN) );
		files=new String[2];
		files[0]="necro/nico.scml";
		files[1]="necro/nico.scml";
	}
	@Override
	public void show() {
		Spriter.setDrawerDependencies(Resources.getBatch(),renderer);
		Spriter.init(LibGdxLoader.class,LibGdxDrawer.class);
		
		for(String file:files)
			Spriter.load(Gdx.files.internal(file).read(),file);
		tweener1=setupPlayer(files[0],"idle",6,"spell2",6,false);
		tweener2=setupVictim(files[1],"idle",6,"hit",6);
		
		plateform = new FightingPlateform( tweener1 , tweener2 );
	}
	
	public void update(float delta){

		Resources.stage.act(delta);
		//Resources.stage.getActors().get(0).debug();
		Resources.stage.draw();
		
		if(tweener2.getWeight()==0 && !plateform.isShaken())
			if(!plateform.isShake())
				plateform.setShake(true);
		
		Spriter.update();
		Resources.getBatch().begin();
			plateform.draw(Resources.getBatch());
			Spriter.draw();
		Resources.getBatch().end();
		timer+=delta;
		if(timer>3)
		{
			bar2.setHp( (int)( stats2.getHP_MAX()*(float)Math.random() ) );
			bar1.setHp( (int)( stats1.getHP_MAX()*(float)Math.random() ) );
			timer=0;
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
		
	}

	@Override
	public void dispose() {
		
		Resources.stage.dispose();
		
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.7f, .7f, .7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
	}

	private PlayerTweener setupVictim(String file, String first,int speed_first,String last,int speed_last){
		PlayerTweener tweener=setupPlayer(file,first,speed_first,last,speed_last,true);
		tweener.setPosition(Gdx.graphics.getWidth()/4*3,0);
		tweener.flip(true, false);
		
		return tweener;
	}
	
	private PlayerTweener setupPlayer(String file, String first,int speed_first,String last,int speed_last , boolean dep){
		PlayerTweener tweener;
		tweener=(PlayerTweener) Spriter.newPlayer(file, 0 , PlayerTweener.class);
		tweener.setScale(.5f);
		
		tweener.getSecondPlayer().setAnimation(first);
		tweener.getFirstPlayer().setAnimation(last);
		
		PListener listener1=new PListener(tweener,dep);
		tweener.getSecondPlayer().addListener(listener1);

		tweener.getSecondPlayer().speed=speed_first;
		tweener.getFirstPlayer().speed=speed_last;
		tweener.setBaseAnimation(first);
		tweener.setPosition(Gdx.graphics.getWidth()/4,0);

		tweener.setWeight(1f);
		tweener.baseBoneName = null;

		return tweener;
	}
	
}
