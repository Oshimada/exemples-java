package com.DialogEditor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.tph.Resources;

public class ViewScene implements Screen {
	protected String text;
	protected DialogList scene;
	protected DialogBox box;
	protected Skin skin;
	protected Table table;
	protected int current_id=0;
	protected boolean keypressed=false,update_text=false;
	protected Sprite background;
	
	public ViewScene(String text){
		this.text=text;
		skin=Resources.getManager().get("ui/menuSkin.json");
	}
	@Override
	public void show() {
		Resources.stage.clear();
		background=new Sprite(Resources.getManager().get("mh.jpg", Texture.class));
		background.setBounds(0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		table=new Table();

		scene=Load(text);
		box=new DialogBox(""+scene.get(0).getNom()+": ", skin,scene.get(0));
		
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
		Resources.stage.addActor(table);
		Gdx.input.setInputProcessor(Resources.stage);
		Resources.stage.setKeyboardFocus(table);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

		Gdx.gl20.glClearColor( 0f, .5f, 0.5f, 1 );
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		
		Resources.stage.getBatch().begin();
		background.draw(Resources.stage.getBatch());
		Resources.stage.getBatch().end();
		
		Resources.getStage().act(delta);
		Resources.getStage().draw();

		update_text=box.update(delta);
		if(keypressed && current_id<scene.size()){
			table.getChildren().get(0).clear();
			box.Next(scene.get(current_id));
			keypressed=false;
			if(current_id<scene.size())current_id++; // INGAME PAUSE
			
		}
		else if(update_text&& keypressed && current_id==scene.size())
			Continue(); // THROW EVENT CONTINUE
		
	}
	protected void Continue(){
		table.getChildren().get(0).clear();

		table.clear();
		TextButton retour=new TextButton("Back", skin);
		retour.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new EditorScreen());
			}
		});
		table.add(retour);
		keypressed=true;
		
	}
	@Override
	public void resize(int width, int height) {
		Resources.stage.getViewport().update(width,height);
	}


	@Override
	public void hide() {
	
	}

	@Override
	public void pause() {
	
	}

	@Override
	public void resume() {
	
	}

	@Override
	public void dispose() {
	
	}
	public DialogList Load(String text)
	{
		FileHandle file=Gdx.files.local("Dialog/"+text+".json");
		Json json=new Json();
		DialogList profile;
		profile=json.fromJson(DialogList.class, Base64Coder.decodeString(file.readString()));	
		return profile;
	}
	
	
}
