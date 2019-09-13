package com.tph.ingame.tools.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.tph.IA.IATest;
import com.tph.IA.dumbIA;
import com.tph.ingame.tools.map.SplashTurn;
import com.tph.ingame.tools.map.TiledMapStage;
import com.tph.ingame.tools.ui.SelectElement;

public class PlayerGroup {

	protected Array<Player> allie;
	protected Array<Player> enemi;
	protected Array<Player> neutral;
	protected dumbIA dumbintel;
	protected static int turn=0;
	protected SplashTurn splash ;

	public PlayerGroup() {
		
		allie=new Array<Player>();
		enemi=new Array<Player>();
		neutral=new Array<Player>();
		
	}
	public void setupIA(){
		dumbintel=new dumbIA();
		splash = new SplashTurn( .4f );
	}
	public void drawPlayers(Batch batch){
		
		if( IATest.fight == null )//&& splash.isEnded()
		{
            for(Player p:getAllies())
			    p.draw(batch);
            for(Player p:getEnemies())
			{
				p.setFlip(true,false);
				p.draw(batch);
			}
            //for(Player p:getNeutral())
		    //	p.draw(batch);
		}

        Gdx.graphics.setTitle("Conquer Tactics - "+Gdx.graphics.getFramesPerSecond());

    	boolean ended=true;
    	
	    if(turn == 0){
	    	for(Player p:getAllies())
			{
				if(p.isTurn() && p.isMoved() && !p.isAction())
				{
					p.endTurn();
					p.setStance(Animations.STAND);
					TiledMapStage.getSelect().deselect();
				}
				if(p.isTurn())
					ended=false;
			}
	    }
	    if(turn == 1){
	    	for(Player p:getEnemies())
		    	if(p.isTurn())
		    		ended=false;

			if(!ended && splash.isEnded() && IATest.fight == null )
				dumbintel.updateIA();
        }

	    splash.draw();
		
	    /*
	    if(turn == 2){
	    	for(Player p:getNeutral())
		    	if(p.isTurn())
		    		ended=false;
	    }
	    */
    	if(ended && IATest.fight == null )
    	{
    		
    		int parties=0;
    		if(allie.size>0)parties++;
    		if(enemi.size>0)parties++;
    		//if(neutral.size>0)parties++;
    		
    		turn++;
    		turn%=parties;

    		if(turn == 0){
        		splash.setTurn("yourturn");
    	    	for(Player p:getAllies())
    		    	p.setTurn();
    	    }

            if(turn == 1){
        		splash.setTurn("enemyturn");
    	    	for(Player p:enemi)
    	    		p.setTurn();
        	}

    	    /*if(turn == 2){
    	    	for(Player p:neutral)
    	    		p.setTurn();
    	    }*/
    	}
	}

	public Player getAllie(int i) {
		return allie.get(i);
	}
	public void setAllie(int index,Player p) {
		allie.set(index, p);
	}
	public void addAllie(Player p) {
		p.setType(SelectElement.ALLIE);
		allie.add(p);
	}
	public void removeAllie(Player p) {
		allie.removeValue(p,false);
	}
	
	
	public Player getEnemi(int i) {
		return enemi.get(i);
	}
	public void setEnemi(int index,Player p) {
		enemi.set(index, p);
	}
	public void addEnemi(Player p) {
		enemi.add(p);
	}
	public void removeEnemi(Player p) {
		enemi.removeValue(p,false);
	}
	
	
	public Array<Player> getNeutral() {
		return neutral;
	}
	public void setNeutral(Array<Player> neutral) {
		this.neutral = neutral;
	}
	public Array<Player> getAllies() {
		return allie;
	}

	public Array<Player> getEnemies() {
		return enemi;
	}
	public void remove(Character character) {
		if(allie.contains( (Player) character,true)){
			allie.removeValue((Player) character, true);
			return;
		}
		if(enemi.contains( (Player) character,true)){
			enemi.removeValue((Player) character, true);
			return;
		}
		if(neutral.contains( (Player) character,true)){
			neutral.removeValue((Player) character, true);
			return;
		}
	}
	public static int getTurn(){
		return turn;
	}

}
