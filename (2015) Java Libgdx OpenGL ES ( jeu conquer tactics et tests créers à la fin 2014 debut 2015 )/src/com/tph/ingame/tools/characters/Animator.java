package com.tph.ingame.tools.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animator extends Animation{

	protected String name;
	public Animator(float frameDuration,
			Array<? extends TextureRegion> keyFrames, String name) {
		super(frameDuration, keyFrames);
		this.name=name;
	}
	public Animator(String s,float time,TextureAtlas atlas) {
		super(time, atlas.findRegions(s));
	}
	public String Name() {
		return name;
	}
	public void setName(String s) {
		this.name = s;
	}
	
}
