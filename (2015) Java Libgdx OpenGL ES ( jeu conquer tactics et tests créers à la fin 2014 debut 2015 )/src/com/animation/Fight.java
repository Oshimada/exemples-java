package com.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.PlayerTweener;
import com.brashmonkey.spriter.SCMLReader;
import com.animation.Spriter;
import com.tph.Resources;
import com.tph.IA.IATest;
import com.tph.ingame.tools.characters.Stats;
import com.tph.ingame.tools.ui.HpBar;

import java.util.HashMap;
import java.util.Map;

public class Fight {
	
	protected Player p1,p2;
	protected PlayerTweener tweener1,tweener2;
	protected float timer=0;
	protected static ShapeRenderer renderer=new ShapeRenderer();
	
	protected Stats stats1,stats2;
	protected HpBar bar1,bar2;
	protected Image background;
	protected boolean ended=false;
	protected  FightingPlateform plateform;
	protected static String[] files;
	protected static final HashMap map= new HashMap< String , String >();
	protected static final HashMap scale= new HashMap< String , Float >();
	protected Stage stage;
	protected Stack stack;
	
	protected static Array<PlayerTweener> players;
	protected static Array<PListener> lis;
	protected float HP2,HP1;
	protected static float SCALE_P1=.5f,SCALE_P2=.5f;
	
	/** 
	 * Loads a sequence from the given file
	 * setup the ui for the given stats ( HP ... etc )
	 * the sequence contains the list of animations for the given attacks and reactions
	 */
	
	public static void Load(String... player){
		//com.brashmonkey.spriter.XmlReader
		map.put("necro","necro/necro.scml");
		map.put("shaman","necro/necro.scml");
		map.put("shaman bad","necro/necro.scml");
		map.put("necro bad","necro/necro.scml");
		map.put("necromancer","necro/necro.scml" );
		map.put("necromancer bad","necro/necro.scml" );

		map.put("gunner bad","Human - gunner - bad/gunner.scml");
		map.put("gunner","Human - gunner/gunner.scml");

		map.put("alchemist bad","Human - alchemist - bad/alchemist.scml");
		map.put("alchemist","Human - alchemist/alchemist.scml");

		map.put("warrior","Human - warrior/warrior.scml");
		map.put("warrior bad", "Human - warrior - bad/warrior.scml");


		map.put("fear","Alien - tank/fear.scml");
		map.put("fear bad","Alien - tank/fear.scml");
		map.put("slayer bad", "Alien - warrior/slayer.scml");
		map.put("slayer", "Alien - warrior/slayer.scml");




		scale.put("necromancer",new Float(.3f) );
		scale.put("necromancer bad",new Float(.3f) );
		scale.put("necro",new Float(.3f) );
		scale.put("shaman",new Float(.3f));
		scale.put("shaman bad",new Float(.3f));
		scale.put("necro bad",new Float(.3f));

		scale.put("gunner bad",new Float(.3f));
		scale.put("gunner",new Float(.3f));

		scale.put("alchemist bad",new Float(0.5f));
		scale.put("alchemist",new Float(0.5f));

		scale.put("warrior",new Float(0.5f));
		scale.put("warrior bad",new Float(0.5f));

		scale.put("slayer",new Float(0.5f));
		scale.put("slayer bad",new Float(0.5f));
		scale.put("fear bad",new Float(0.5f));
		scale.put("fear",new Float(0.5f));



		Spriter.dispose();
		files=new String[player.length];
		int i = 0;
		for(String s : player)
		{
			files[i++]= (String) map.get(s);
			Spriter.setDrawerDependencies(IATest.uistage.getBatch(),renderer);
			Spriter.init(LibGdxLoader.class,LibGdxDrawer.class);
		}
		for(String file:files)
			Spriter.load(Gdx.files.internal(file).read(),file);

		players=new Array<PlayerTweener>();	
		lis =new Array<PListener>();
		
	}
	public static void LoadPlayers(String attacker,String victim){

		files[0]= (String) map.get(attacker);//attacker+"/"+attacker+".scml";
		files[1]= (String) map.get(victim);//victim+"/"+victim+".scml";

		System.out.println("vicim is "+victim);
		SCALE_P1 = (Float) scale.get(attacker);
		SCALE_P2 = (Float) scale.get(victim);

		Spriter.getPlayers().clear();
		players.clear();
		lis.clear();
		
		players.add(setupPlayer(files[0], "idle", 12, "attack", 12, false));
		players.add(setupVictim(files[1], "idle", 12, "hit", 12));


		if( attacker.equals("slayer") || attacker.equals("slayer bad") )
			players.get(0).flip(true,false);
		if( victim.equals("slayer")|| victim.equals("slayer bad") )
		{
			players.get(1).flip(true, false);
		}
	}
	
	public Fight(Stats stats1, Stats stats2,Stage stage) {
		
		this.stage=stage;
		this.stats1=stats1;
		this.stats2=stats2;
		
		bar1=new HpBar(stats1.getHP_MAX(), stats1.getRace());
		bar2=new HpBar(stats2.getHP_MAX(), stats2.getRace());

		bar1.setHp(stats1.getHp());
		bar2.setHp(stats2.getHp());

		background = new Image(Resources.getManager().get("sliders/hpbars/cord.png", Texture.class));
		
		Table table=new Table();
		table.setFillParent(true);
		table.top();
		table.add(bar1.getBar()).size(Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/6f).space(50).top();
		table.add(bar2.getBar()).size(Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/6f).top().row();

		for(Actor actor:stage.getActors())
			actor.setVisible(false);

		stack = new Stack();
		stack.setFillParent(true);
		stack.add(background);
		stack.add(table);

		stage.addActor(stack);

		tweener1=(PlayerTweener) players.get(0);
		tweener2=(PlayerTweener) players.get(1);

		tweener1.getSecondPlayer().removeListener(lis.get(0));
		tweener2.getSecondPlayer().removeListener(lis.get(1));
		
		lis.clear();
		lis.add(new PListener(tweener1,false));
		lis.add(new PListener(tweener2,true));
		
		tweener1.getSecondPlayer().addListener( lis.get(0) );
		tweener2.getSecondPlayer().addListener( lis.get(1) );
		
		plateform = new FightingPlateform( tweener1 , tweener2 );

		HP1=stats1.getHp();
		HP2=stats2.getHp();
		
		
	}
	
	
	public void update(Batch batch,float delta){

		stage.act(delta);
		//stage.getActors().get(0).debug();
		stage.draw();
		
		if(tweener2.getWeight()==0 && !plateform.isShaken())
		{	
			if(!plateform.isShake())
			{
				plateform.setShake(true);
				if(HP2<=0)
					tweener2.getFirstPlayer().setAnimation("dying");
				else
					tweener2.getFirstPlayer().setAnimation("hit");
				if(HP1<=0)
					tweener1.getFirstPlayer().setAnimation("dying");
				
				bar2.setHp(HP2);
				bar1.setHp(HP1);
				System.out.println(stats1.getHP_MAX()+" / "+HP1+" "+stats2.getHP_MAX()+" / "+HP2);
			}
		}
		if(tweener2.getWeight()==1 && plateform.isShaken())
			setEnded(true);
		
		Spriter.update();
		batch.begin();
			plateform.draw(batch);
			Spriter.draw();
		batch.end();
		timer+=delta;
	}
	public void draw(Batch batch,float delta) {
		update(batch,delta);
	}

	private static PlayerTweener setupVictim(String file, String first,int speed_first,String last,int speed_last){
		
		PlayerTweener tweener=setupPlayer(file,first,speed_first,last,speed_last,true);

		tweener.setScale(SCALE_P2 * (Gdx.graphics.getHeight() / 1000f));
		tweener.setPosition(Gdx.graphics.getWidth() / 4 * 3, 0);

		if(tweener.flippedX() == -1)
			tweener.flip(false, false);
		else
			tweener.flip(true, false);

		return tweener;
	}
	
	private static PlayerTweener setupPlayer(String file, String first,int speed_first,String last,int speed_last , boolean dep){
		
		PlayerTweener tweener;
		tweener=(PlayerTweener) Spriter.newPlayer(file, 0 , PlayerTweener.class);
		tweener.setScale(SCALE_P1 * (Gdx.graphics.getHeight()/1000f));
		tweener.getSecondPlayer().setAnimation(first);
		tweener.getFirstPlayer().setAnimation(last);
		
		PListener listener1=new PListener(tweener,dep);
		tweener.getSecondPlayer().addListener(listener1);
		lis.add(listener1);
		
		tweener.getSecondPlayer().speed=speed_first;
		tweener.getFirstPlayer().speed=speed_last;
		tweener.setBaseAnimation(first);
		tweener.setPosition(Gdx.graphics.getWidth()/4,0);

		tweener.setWeight(1f);
		tweener.baseBoneName = null;

		return tweener;
	}


	public boolean isEnded() {
		return ended;
	}


	public void setEnded(boolean ended) {
		this.ended = ended;
	}
	
	public void dispose(){
		plateform.dispose();
		stage.getActors().removeValue(stack, true);
		for(Actor actor:stage.getActors())
			actor.setVisible(true);
	}


	public HpBar getBar1() {
		return bar1;
	}


	public void setBar1(HpBar bar1) {
		this.bar1 = bar1;
	}
	public void setHP1(float hp) {
		if(hp<0)hp=0;
		HP1=hp;
	}
	public void setHP2(float hp) {
		if(hp<0)hp=0;
		HP2=hp;
	}


	public HpBar getBar2() {
		return bar2;
	}


	public void setBar2(HpBar bar2) {
		this.bar2 = bar2;
	}
}