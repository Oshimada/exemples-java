package com.tph.ingame.tools.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tph.Resources;
import com.tph.ingame.tools.map.TiledMapStage;

public class SplashLevel {
	
	
	protected String levelName; 
	protected Image background;
	protected Label text;
	protected Skin skin;
	protected TiledMapStage stage;
	
	public SplashLevel(String levelName){
		this.levelName=levelName;
		skin=Resources.getManager().get("ui/menuSkin.json");
		background = new Image(skin.getAtlas().findRegion("default.selection"));
		text=new Label(levelName, skin);
		
		stage=Resources.getStage();
	
	}
	public void applySplash(){
		
		Table table=(Table)stage.getActors().get(0);
		
		background.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(5f)));
		
		text.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(3f)));
		text.addAction(Actions.sequence(Actions.delay(1),Actions.fadeOut(3f)));
		
		table.clear();
		table.add(text);
		
		Stack stack=new Stack();
		stack.add(background);
		stack.add(text);
		table.add(stack);
	}
	public boolean update(Camera camera){
		stage.getActors().get(0).setPosition(camera.position.x-Gdx.graphics.getWidth()/2,
				camera.position.y-Gdx.graphics.getHeight()/2);
		if(text.getActions().size==0)
		{
			stage.getActors().get(0).clear();
			return true;
		}
		return false;
	}
}
