package com.DialogEditor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainClass extends Game{
	@Override
	public void create () {
		// set resolution to HD ready (1280 x 720) and set full-screen to true
		//Gdx.graphics.setDisplayMode(1280, 720, true);

		// set resolution to default and set full-screen to true
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
		((Game)Gdx.app.getApplicationListener()).setScreen(new EditorScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}
}
