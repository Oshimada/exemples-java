package com.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Point;
import com.tph.IA.IATest;

public class FightingPlateform extends Sprite{
	
	private static final String PLATEFORM_DEFAULT="decors/planche.png";
	
	private Player p1,p2;
	private boolean shake=false;
	
	private Effects effects;
	
	private float X = Gdx.graphics.getWidth()/10 , Y = Gdx.graphics.getHeight()/12 ;
	private float shake_weight = Gdx.graphics.getHeight() / 50;
	
	public FightingPlateform(Player player1 , Player player2) {
		
		super(new Texture(PLATEFORM_DEFAULT));
		this.p1=player1;
		this.p2=player2;
		
		setSize ( Gdx.graphics.getWidth()*.8f,Gdx.graphics.getHeight()/3);
		setPosition ( Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/12);
		
		p1.setPosition(getX()+getWidth() / 4,getY()+getHeight()*3/4);
		p2.setPosition(getX()+getWidth()*3/4,getY()+getHeight()*3/4);
		
		effects=new Effects(IATest.uistage.getBatch());
		
	}
	private void addEffectAt(Point p)
	{
		effects.addEffect(p.x, p.y);
	}
	private void update(float delta) {
		if(shake)
			shake(delta);
		effects.draw(delta);
	}
	
	private void shake(float delta)
	{
		if( shake_weight<=0)
		{
			shake=false;
			shake_weight=0;
			setPosition(X, Y);
		}
		else
		{
			shake_weight--;
			setPosition( X + (shake_weight/10) *randomInt(-1,1) , Y + shake_weight*randomInt(-1,-1) );
		}
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
		update(Gdx.graphics.getDeltaTime());
	}
	
	public boolean isShake() {
		return shake;
	}
	public void setShake(boolean shake) {
		if(shake==false){
			this.shake=shake;
			this.shake=shake;
			return;
		}
		if(!this.shake){
			this.shake = shake;
			shake_weight = Gdx.graphics.getHeight() / 50;
			addEffectAt(p2.getBone("chest").position);
		}
	}
	public boolean isShaken(){
		return (shake_weight<Gdx.graphics.getHeight() / 50);
	}
	public float randomInt(int i,int f)
	{
		return (float) (Math.random()*(i+f)-i);
	}
	public void dispose()
	{
		getTexture().dispose();
		effects.dispose();
	}
}
