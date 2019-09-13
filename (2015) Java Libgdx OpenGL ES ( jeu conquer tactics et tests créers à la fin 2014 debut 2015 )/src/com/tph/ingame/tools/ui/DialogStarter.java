package com.tph.ingame.tools.ui;

import com.DialogEditor.DialogBox;
import com.DialogEditor.DialogList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.tph.Resources;

public class DialogStarter {
	protected String text;
	protected DialogList scene;
	protected DialogBox box;
	protected Skin skin;
	protected Table table;
	protected int current_id=0;
	public static boolean keypressed=false;
	protected boolean update_text=false,done=false;
	private Stage stage;
	
	public DialogStarter(String text){
		this.text=text;
		skin=Resources.getManager().get("ui/menuSkin.json");
		table=new Table();
		
		scene=Load(text);
		box=new DialogBox("", skin,scene.get(0));
		
		table.addListener(new InputListener(){
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if(update_text)keypressed=true;
				return true;
			}
		});
		table.setFillParent(true);
		table.center();
		box.Next(scene.get(current_id++));
		table.add(box.getWindow()).width(Gdx.graphics.getWidth()*.75f).height(Gdx.graphics.getHeight()*.25f);
		table.bottom();
	}
	public Table getTable(){
		return table;
	}
	public void addTo(Stage stage){
		this.stage=stage;
		stage.addActor(table);
		Gdx.input.setInputProcessor(Resources.stage);
		stage.setKeyboardFocus(table);
	}
	public void removeFrom(Stage stage){
		table.clear();
		stage.getActors().removeIndex(1);
		done=true;
	}
	public void update(float delta) {
		
		update_text=box.update(delta);
		if(keypressed && current_id<scene.size()){
			table.getChildren().get(0).clear();
			box.Next(scene.get(current_id));
			keypressed=false;
			if(current_id<scene.size())current_id++;
		}
		else if(update_text&& keypressed && current_id==scene.size())
			removeFrom(stage);
	}
	public DialogList Load(String text)
	{
		FileHandle file=Gdx.files.internal("Dialog/"+text+".json"); // local for desktop
		Json json=new Json();
		DialogList profile;
		profile=json.fromJson(DialogList.class, Base64Coder.decodeString(file.readString()));	
		return profile;
	}
	public boolean isDone() {
		return done;
	}

	
	
	
}
