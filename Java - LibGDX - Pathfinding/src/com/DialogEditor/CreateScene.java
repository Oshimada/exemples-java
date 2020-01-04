package com.DialogEditor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.tph.Resources;
public class CreateScene implements Screen {

	protected Table table;
	protected Skin skin;
	protected VerticalGroup perso_group; protected Array<TextArea> nom_list;
	protected VerticalGroup img_group; protected Array<TextArea> img_list;
	
	public CreateScene(){
		skin=Resources.getManager().get("ui/menuSkin.json");
	} 
	@Override
	public void show() {
		table=new Table();
		final SelectBox<String> nb_perso=new SelectBox<String>(skin);
		
		perso_group=new VerticalGroup();
		nom_list=new Array<TextArea>();
		img_group=new VerticalGroup();
		img_list=new Array<TextArea>();
		
		
		final TextArea src=new TextArea("",skin);
		TextButton create =new TextButton("Create", skin);
		
		
		perso_group.space(10);
		for(int i=0;i<6;i++){
			nom_list.add(new TextArea("", skin));
			nom_list.get(i).setMessageText("nom"+i);
			perso_group.addActor(nom_list.get(i));
		}
		img_group.space(10);
		for(int i=0;i<6;i++){
			img_list.add(new TextArea("", skin));
			img_list.get(i).setMessageText("img"+i);
			perso_group.addActor(img_list.get(i));
		}
		img_group.fill();
		perso_group.fill();
		nb_perso.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println(nb_perso.getSelected());
				for(int i=nb_perso.getSelectedIndex();i<6;i++)
					nom_list.get(i).setVisible(false);
				for(int i=0;i<=nb_perso.getSelectedIndex();i++)
							nom_list.get(i).setVisible(true);
				for(int i=nb_perso.getSelectedIndex();i<6;i++)
					img_list.get(i).setVisible(false);
				for(int i=0;i<=nb_perso.getSelectedIndex();i++)
							img_list.get(i).setVisible(true);
				
				
	    }});
		nb_perso.setItems("1 perso", "2 persos", "3 persos", "4 persos", "5 persos", "6 persos");
		src.setMessageText("storage file");
		
		
		
		create.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Array<String> persos=new Array<String>();
				for(TextArea t:nom_list)
					persos.add(t.getText());

				Array<String> images=new Array<String>();
				for(TextArea t:img_list)
					images.add(t.getText());
				((Game)Gdx.app.getApplicationListener()).setScreen(new SceneEditor(src.getText() ,new DialogFile(nb_perso.getSelectedIndex()+1,persos,images)));
			}
		});
		
		
		
		
		table.setFillParent(true);
		table.add(nb_perso).space(50).top();
		table.add(perso_group).minWidth(200);
		table.add(img_group).minWidth(200).row();
		table.add(src).left().minWidth(300);
		table.add(create).space(20);
		Resources.stage.clear();
		
		Resources.stage.addActor(table);
		Gdx.input.setInputProcessor(Resources.stage);
		
	}

	@Override
	public void resize(int width, int height) {

		Resources.stage.getViewport().update(width,height);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		Gdx.gl20.glClearColor( 0f, .5f, 0.5f, 1 );
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		Resources.getStage().act(delta);
		//table.debug();
		Resources.getStage().draw();
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