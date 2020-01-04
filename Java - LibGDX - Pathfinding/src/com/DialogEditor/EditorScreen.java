package com.DialogEditor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tph.Resources;

public class EditorScreen implements Screen {
	protected Skin skin;
	public EditorScreen()
	{
		skin=Resources.getManager().get("ui/menuSkin.json");
	}
	@Override
	public void render(float delta) {

		Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

		Gdx.gl20.glClearColor( 0f, .5f, 0.5f, 1 );
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		Resources.getStage().act(delta);
		Resources.getStage().draw();
	}

	@Override
	public void resize(int width, int height) {
		Resources.stage.getViewport().update(width,height);
	}

	@Override
	public void show() {
		Resources.stage.clear();
		
		Table table=new Table(skin);
        Resources.getFont().setColor(Color.BLACK);
        Label.LabelStyle style=new Label.LabelStyle(Resources.getFont(), Color.WHITE);


		TextButton new_file=new TextButton("Nouveau dialogue", skin);
		TextButton visionner=new TextButton("Visionner dialogue", skin);
		final TextArea src= new TextArea("", skin);

        new_file.getLabel().setStyle(style);
        visionner.getLabel().setStyle(style);

		new_file.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				((Game)Gdx.app.getApplicationListener()).setScreen(new CreateScene());
			}
		});
		visionner.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new ViewScene(src.getText()));
			}
		});
		table.setFillParent(true);
		HorizontalGroup ligne=new HorizontalGroup();
		VerticalGroup colonne = new VerticalGroup();
		
		colonne.space(100).addActor(visionner);
		colonne.addActor(src);
		ligne.top().space(100).addActor(new_file);
		ligne.addActor(colonne);
		
		table.add(ligne);
		
		Resources.stage.addActor(table);
		Gdx.input.setInputProcessor(Resources.stage);
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

}
