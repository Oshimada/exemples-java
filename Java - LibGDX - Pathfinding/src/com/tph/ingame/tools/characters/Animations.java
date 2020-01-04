package com.tph.ingame.tools.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animations extends Array<Animator> {
	public static final byte STAND = 0, HIGHLIGHT = 1, MOVE = 2, ATTACK = 3;
	public static final float FPS_DEFAULT = 1 / 30f, FPS_FAST = 1 / 3f,
			FPS_SLOW = 1 / 40f;
	private String name;
	private TextureAtlas atlas;

	public Animations(String name, String... titre) {
		Load("characters/" +name, titre);
		this.name=name;
	}

	public void remove(String name) {
		for (int i = 0; i < size; i++)
			if (name == get(i).Name())
				removeIndex(i);
	}

	public void add(Animation animation, String name) {
		Animator anime = (Animator) animation;
		anime.setName(name);
	}

	public Animator get(String name) {
		for (Animator m : this)
			if (m.Name() == name)
				return m;
		return get(0);
	}

	public void Load(String name, String... titre) {
		try {

			atlas = new TextureAtlas(Gdx.files.internal("elements/" + name
					+ ".pack"));
			for (String s : titre) {
				Animator tmp = new Animator(s, FPS_FAST, atlas);
				tmp.setName(s);
				add(tmp);
			}
		} catch (Exception e) {
			System.out.println("l'animation - " + "elements/" + name + ".pack"
					+ " - n'as pas pu etre chargee");
			this.atlas = null;
			e.printStackTrace();
		}
	}

	public void dispose() {
		atlas.dispose();
		clear();
	}

	public TextureRegion getKeyFrame(byte stance, float elapsedTime) {
		Animation animation;
		if (stance == ATTACK) {
			animation = get(name+"_attack_");
			animation.setPlayMode(Animation.PlayMode.NORMAL);
			return animation.getKeyFrame(elapsedTime);
		}
		if (stance == MOVE) {
			animation = get("move");
			animation.setPlayMode(Animation.PlayMode.LOOP);
		}
		if (stance == HIGHLIGHT) {
			animation = get(name+"_highlight_");
			animation.setPlayMode(Animation.PlayMode.LOOP);
		} else // STAND
		{
			animation = get(name+"_stand_");
			animation.setPlayMode(Animation.PlayMode.LOOP);
		}
		try{
			return animation.getKeyFrame(elapsedTime);	
		}
		catch( ArithmeticException e ){
			System.out.println(name+"_stand_");
			Gdx.app.exit();
			return null;
		}
	}
}
