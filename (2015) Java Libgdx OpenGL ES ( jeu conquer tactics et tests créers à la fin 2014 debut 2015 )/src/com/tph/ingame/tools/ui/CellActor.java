package com.tph.ingame.tools.ui;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tph.ingame.tools.characters.Element;

public class CellActor extends Actor {

	protected TiledMapTileLayer.Cell cell;
	protected TiledMapTileLayer layer;
	protected Element element;
	protected int x, y;

	public static final int CELL_VOID = 254, CELL_CURSOR = 8,CELL_MOV = 132,
			CELL_STEPS = 131, CELL_RANGE = 148;

	public CellActor(TiledMapTileLayer layer, TiledMapTileLayer.Cell cell,
			int x, int y) {

		this.cell = cell;
		this.layer = layer;
		this.x = x;
		this.y = y;
	}

	public int getX2() {
		return x;
	}

	public void setX2(int x) {
		this.x = x;
	}

	public int getY2() {
		return y;
	}

	public void setY2(int y) {
		this.y = y;
	}

	public TiledMapTileLayer.Cell getCell() {
		return cell;
	}

	public void setCell(TiledMapTileLayer.Cell cell) {
		this.cell = cell;
	}

	public TiledMapTileLayer getLayer() {
		return layer;
	}

	public void setLayer(TiledMapTileLayer layer) {
		this.layer = layer;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

}
