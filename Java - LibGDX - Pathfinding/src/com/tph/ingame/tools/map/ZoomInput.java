package com.tph.ingame.tools.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.tph.IA.IATest;
import com.tph.Resources;

public class ZoomInput implements GestureListener,InputProcessor{
	
	private OrthographicCamera camera;
	private float zewm;

	public ZoomInput(OrthographicCamera camera){
		this.camera=camera;
		double resolution = Gdx.graphics.getWidth()*Gdx.graphics.getHeight();

		if(resolution < 800*480)
			zewm = 1f;
		else if(resolution < 1280*720)
			zewm = .8f;
		else if(resolution < 1370 * 800)
			zewm = 0.6f;
		else if(resolution < 1500 * 840)
			zewm = 0.5f;
		else
			zewm = 0.25f;
		camera.zoom = zewm;
		camera.viewportWidth = (int)(camera.viewportWidth);
		camera.viewportHeight = (int)(camera.viewportHeight);
	}
	
	@Override
	public boolean scrolled(int amount) {

		/*
        if(camera.zoom>zewm )
			camera.zoom=zewm ;
		if(camera.zoom<.25f)
			camera.zoom=.3f;
		camera.zoom+=amount*0.1 ;
*/
		return true;
	}
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
/*
		if(camera.zoom>zewm + .05f)
			camera.zoom=zewm ;
		if(camera.zoom<.25f)
			camera.zoom=.3f;
        camera.zoom+=(1) *0.1 ;
		return false;
*
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
        return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		/* if(camera.zoom>zewm )

			camera.zoom=zewm ;
		if(camera.zoom<.25f)
			camera.zoom=.3f;
        camera.zoom+=(deltaX/Gdx.graphics.getWidth()) *0.1 ;
		 */

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		TiledMapTileLayer l =(TiledMapTileLayer)( IATest.map.getLayers().get(0));

		IATest.moveCamera(Resources.stage.getFinder().getWidth()*l.getTileWidth() - x + deltaX , y +deltaY);

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
/*
		if(camera.zoom>zewm )
			camera.zoom=zewm ;
		if(camera.zoom<.25f)
			camera.zoom=.3f;
        camera.zoom+=((distance-initialDistance)/(Gdx.graphics.getWidth())) *0.1 ;
*/
		return false;

	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
        System.out.println("Pinch");

		
		return false;
	}


	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}
	/*
	 * float m=Gdx.graphics.getWidth()>Gdx.graphics.getHeight()?Gdx.graphics.getHeight():Gdx.graphics.getWidth();
		float VIEWPORT_WIDTH=1f*(768/m),VIEWPORT_HEIGHT=1f*(768/m),width=Gdx.graphics.getWidth(),height=Gdx.graphics.getHeight();
	    
		camera.viewportWidth = (int) width/VIEWPORT_WIDTH;
	    camera.viewportHeight =(int) height/VIEWPORT_HEIGHT;

	    camera2.viewportWidth = (int) width/VIEWPORT_WIDTH;
	    camera2.viewportHeight =(int) height/VIEWPORT_HEIGHT;
	    
	    camera3.viewportWidth = (int) width/VIEWPORT_WIDTH;
	    camera3.viewportHeight =(int) height/VIEWPORT_HEIGHT;

	    ui.viewportWidth = (int) width/VIEWPORT_WIDTH;
	    ui.viewportHeight =(int) height/VIEWPORT_HEIGHT;
	    Resources.getStage().getViewport().update( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		 Table table=Resources.getStage().getTable();
	    Window table1=(Window) table.getChildren().get(0);
	    Window table2=(Window) table.getChildren().get(1);
	    table.clear();
	    table2.getListeners().clear();
	    Resources.getStage().getSelect().setupButtons();
	    table.setFillParent(true);
	    table.add(table1).expand().left().top().row();
	    table.add(table2).expand().fill().space(20);
	    System.out.println("wtf");
	 */
}