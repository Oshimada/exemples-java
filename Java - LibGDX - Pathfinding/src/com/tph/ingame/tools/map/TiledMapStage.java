package com.tph.ingame.tools.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.ui.CellActor;
import com.tph.ingame.tools.ui.CellActorClickListener;
import com.tph.ingame.tools.ui.InfoCase;
import com.tph.ingame.tools.ui.InfoElement;
import com.tph.ingame.tools.ui.SelectElement;

public class TiledMapStage extends Stage {
	private Path finder;
	private Skin skin;
	private Table table;
	private static SelectElement select;
	private InfoCase info;
	private InfoElement infelem;
	private CellActor[][] actors;
	public TiledMapStage(Viewport v) {
		super(v);
	}

	public TiledMapStage() {
		super();
	}

	public TiledMapStage(TiledMapTileLayer layer, TiledMapTileLayer layer2,
			Path finder) {
		super();
		this.finder = finder;
		createActorsForLayer(layer, layer2);
	}
	{
		table = new Table();
		table.setFillParent(true);
		table.setName("table");
	}

	public void setupMapToStage( TiledMapTileLayer layer , TiledMapTileLayer layer2) {
		this.finder = new Path(layer);
		createActorsForLayer(layer, layer2);
	}

	private void createActorsForLayer(TiledMapTileLayer tiledLayer,
			TiledMapTileLayer layer2) {

		info = new InfoCase();
		select = new SelectElement();
		infelem = new InfoElement();
		actors = new CellActor[tiledLayer.getWidth()][tiledLayer.getHeight()];

		for (int x = 0; x < tiledLayer.getWidth(); x++) {
			for (int y = 0; y < tiledLayer.getHeight(); y++) {

				TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
				CellActor actor = new CellActor(tiledLayer, cell, x, y);
				actors[x][y] = actor;
				actor.setBounds(x * tiledLayer.getTileWidth(),y * tiledLayer.getTileHeight(),
					tiledLayer.getTileWidth(), tiledLayer.getTileHeight());
				addActor(actor);
				EventListener eventListener = new CellActorClickListener(actor,
						finder, layer2, info, select, infelem);
				actor.addListener(eventListener);
			}
		}

		new ElementsToMap(layer2);
		
		infelem.getBox().left().top();
		info.getBox().left().top();
		select.getBox().pad(5).bottom().right();
		table.setFillParent(true);
		table.add(info.getBox()).width(Gdx.graphics.getWidth() / 4).expand()
				.left().top();
		table.add(infelem.getBox()).width(Gdx.graphics.getWidth() / 4).expand()
				.top().right().row();
		table.add(select.getBox()).colspan(2).expand().center().bottom().space(20);
	}

	public CellActor getActor(int x , int y)
	{
		return actors[x][y];
	}

	public Path getFinder() {
		return finder;
	}

	public void setFinder(Path finder) {
		this.finder = finder;
	}

	public Skin Skin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public Table getTable() {
		return table;
	}

	public static SelectElement getSelect() { return select; }
}
