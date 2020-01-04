package com.tph.IA;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tph.ingame.tools.characters.Player;

public class IACharacter {
	
	protected Player player;
	protected Array<Player> inrange , inatkrange ;
	protected Array<Vector2> cells ;
	public IACharacter( Player player  , Array<Vector2> cels) {
		
		inrange    = new Array<Player>();
		inatkrange = new Array<Player>();
		this.player=player;
		if(cells == null)
			System.out.println(" Cells NULL in CURRENT");
        cells= cels;
	}

    public Array<Vector2> getCells() {
        return cells;
    }

    public void setCells(Array<Vector2> cells) {
        this.cells = cells;
    }

    public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Array<Player> getInrange() {
		return inrange;
	}

	public void setInrange(Array<Player> inrange) {
		this.inrange = inrange;
	}

	public Array<Player> getInatkrange() {
		return inatkrange;
	}

	public void setInatkrange(Array<Player> inatkrange) {
		this.inatkrange = inatkrange;
	}

    @Override
    public String toString() {
        return player.toString();
    }
}
