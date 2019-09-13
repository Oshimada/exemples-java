package com.tph.ingame.tools.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.tph.Resources;
import com.tph.ingame.tools.characters.Classe;

public class HpBar {
	
	protected Slider bar   ;
	protected float  hp = 0;

	public static final String   DRUID_BACKGROUND = "sliders/hpbars/druidbg.png";
	public static final String 	 HUMAN_BACKGROUND = "sliders/hpbars/humanbg.png";
	public static final String 	   ORC_BACKGROUND = "sliders/hpbars/orcbg.png";
	public static final String DEFAULT_BACKGROUND = "sliders/hpbars/background.png";
	public static final String 				HPBAR = "sliders/hpbars/knob.png";
	public static final String 				 KNOB = "sliders/hpbars/beforeknob.png";
	public static final String 		   background = "sliders/hpbars/corde.png";
	
	public HpBar(int HP_MAX, byte race) {
		
		AssetManager manager =Resources.getManager();
		Skin skin = new Skin();
		
		if (race == Classe.DRUIDS)
			skin.add("bg", manager.get(DEFAULT_BACKGROUND, Texture.class));
		
		if (race == Classe.HUMAN)
			skin.add("bg", manager.get(DEFAULT_BACKGROUND, Texture.class));
		
		if (race == Classe.ORCS)
			skin.add("bg", manager.get(DEFAULT_BACKGROUND, Texture.class));

		skin.add("hp", manager.get(HPBAR, Texture.class));
		skin.add("knob", manager.get(KNOB, Texture.class));

		SliderStyle style = new SliderStyle();
		style.background = skin.getDrawable("bg");
		style.knobBefore =skin.getDrawable("hp");
		style.knob=skin.getDrawable("knob");
		bar = new Slider(0, HP_MAX, .1f, false, style){
			@Override
			public void draw(Batch batch, float parentAlpha) {
			if(getValue() != hp )
			{
				if (hp > getValue())
				{
					setValue( getValue()+getStepSize() );
				}
				else
				{
					setValue( getValue()-getStepSize() );
				}
			}
			validate();
				super.draw(batch, parentAlpha);
			}
		};
	}

	public Slider getBar() {
		return bar;
	}

	public void setHp(float f) {
		if (f < 0)
			f = 0;
		hp=f;
	}

	public float getHp() {
		return hp;
	}


}

/*
 * 
 * 
 * private NinePatchDrawable loadingBarBackground;
 * 
 * private NinePatchDrawable loadingBar;
 * 
 * public HpBar() { TextureAtlas skinAtlas = new
 * TextureAtlas(Gdx.files.internal("data/uiskin.atlas")); NinePatch
 * loadingBarBackgroundPatch = new
 * NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4); NinePatch
 * loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"),
 * 5, 5, 4, 4); loadingBar = new NinePatchDrawable(loadingBarPatch);
 * loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch); }
 * 
 * @Override public void draw(Batch batch, float parentAlpha) { float progress =
 * 0.4f;
 * 
 * loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(),
 * getHeight() * getScaleY()); loadingBar.draw(batch, getX(), getY(), progress *
 * getWidth() * getScaleX(), getHeight() * getScaleY()); }
 */