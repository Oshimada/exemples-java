package com.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Effects implements Disposable{
	
	private ParticleEffectPool bombEffectPool;
	private Array<PooledEffect> effects = new Array<PooledEffect>();
	private Batch batch;
	
	
	public Effects(String s) 
	{
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("particles/"+s), Gdx.files.internal("particles/"));
		bombEffect.scaleEffect(1.5f);
		
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
		
	}
	public Effects(Batch batch) 
	{
		this("rgboom.p");
		this.batch=batch;	
	}

	public void addEffect(float x , float y )
	{
		PooledEffect effect = bombEffectPool.obtain();
		effect.setPosition(x, y);
		effects.add(effect);
	}
	
	public void draw(float delta) {
		for (int i = effects.size - 1; i >= 0; i--) {
		    PooledEffect effect = effects.get(i);
		    effect.draw(batch, delta);
		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		    }
		}
	}
	
	@Override
	public void dispose() {
		for (int i = effects.size - 1; i >= 0; i--)
		    effects.get(i).free();
		effects.clear();	
	}
	
}
