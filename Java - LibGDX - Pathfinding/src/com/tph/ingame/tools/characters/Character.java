package com.tph.ingame.tools.characters;

import com.animation.Fight;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tph.IA.IATest;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.ui.CellActor;

public class Character extends Element {
	protected Animations animations;
	protected boolean selected = false, action = true;
	protected float elapsedTime = 0;
	protected byte type = 2; // SelectElement. ( ALLIE = 0, ENEMY = 1 or NEUTRAL = 3 )
	protected boolean turn = true, moved = false;
	protected byte stance = Animations.STAND;
    protected Array<Vector2> rangecells;


    public Character(String url, String nom, String description,
			TiledMapTileLayer layer,int lvl) {
		super(url,nom, description, layer,lvl);
		animations = new Animations(url, url+"_stand_", url+"_attack_",
				url+"_highlight_");
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		elapsedTime += delta;
		setRegion(animations.getKeyFrame(stance, elapsedTime));
		if( stance == Animations.ATTACK && animations.get("attack").isAnimationFinished(elapsedTime))
			setStance(Animations.STAND);
	}
	public void moveIA(int x,int y,Path finder){
		move(x,y,finder);
		moved=true;
	}
	
	public void Attack(CellActor actor) {
		
		Player p = (Player) actor.getElement();
		if (bestpath == null && p.bestpath == null)
		{
			Fight.LoadPlayers(stats.getUrl(),p.getStats().getUrl()+" bad");
			IATest.fight=new Fight(this.getStats(),p.getStats(),IATest.uistage);
			
			setStance(Animations.ATTACK);
			IATest.fight.setHP2(p.getStats().getHp() - stats.getAtq());
			p.getStats().setHp(p.getStats() .getHp() - stats.getAtq());
		}
		
		if (p.getStats().getHp() <= 0) {
			p.die();
			actor.setElement(null);
		}
		action = false;
	}
	public void IAttack(CellActor actor) {
		Player p = (Player) actor.getElement();
		if ( p.bestpath == null)
		{
			Fight.LoadPlayers(stats.getUrl()+" bad",p.getStats().getUrl());
			IATest.fight=new Fight(this.getStats(),p.getStats(),IATest.uistage);
			
			setStance(Animations.ATTACK);
			IATest.fight.setHP2(p.getStats().getHp() - stats.getAtq());
			p.getStats().setHp(p.getStats() .getHp() - stats.getAtq());
		}
			
		if (p.getStats().getHp() <= 0) {
			actor.setElement(null);
			p.die();
		}
		action = false;
	}
	
	public void die() {
		animations.clear();
		getTexture().dispose();
		IATest.players.remove(this);
	}

	public void setTurn() {
		turn = true;
		moved = false;
		action = true;
	}

	public void endTurn() {
		turn = false;
        rangecells = null ;
	}

	public void setMoved() {
		moved = true;
	}

	public void setMoved(boolean b) {
		moved = b;
	}

	public boolean isMoved() {
		return moved;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public Animations getAnimations() {
		return animations;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public boolean isTurn() {
		return turn;
	}

	public byte getStance() {
		return stance;
	}

	public void setStance(byte stance) {
		this.stance = stance;
		elapsedTime = 0;
	}

    public Array<Vector2> getRangecells() {
        return rangecells;
    }

    public void setRangecells(Array<Vector2> rangecells) {
        this.rangecells = rangecells;
    }

	public TextureRegion getLogo_img() { return animations.getKeyFrame(stance, 0); }
}
