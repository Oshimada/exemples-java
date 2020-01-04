package com.tph.ingame.tools.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tph.IA.IATest;
import com.tph.IA.pathfinding.Node;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.characters.Animations;
import com.tph.ingame.tools.characters.Character;
import com.tph.ingame.tools.characters.Player;
import com.tph.ingame.tools.characters.PlayerGroup;

public class CellActorClickListener extends ClickListener {
	private CellActor actor;
	private Path finder;
	private TiledMapTileLayer layer;
	protected InfoCase info;
	protected SelectElement select;
	protected InfoElement ielem;

	public CellActorClickListener(CellActor actor, Path finder,
			TiledMapTileLayer layer, InfoCase info, SelectElement select,
			InfoElement ielem) {

		this.select = select;
		this.actor  = actor;
		this.finder = finder;
		 this.layer = layer;
		  this.info = info;
		 this.ielem = ielem;
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {

		if(IATest.dialog == null)
		{
			if(PlayerGroup.getTurn() == 0 ) {
				if(!select.selected())
					IATest.moveCamera(actor.getX2() * layer.getTileWidth(), actor.getY2()
							* layer.getTileHeight());

				clickAllie(event, x, y);
				clickElse(event, x, y);
			}
			else
				select.deselect();
		}
		else
			IATest.dialog.keypressed=true;
	}
	
	private void clickElse(InputEvent event, float x, float y){
        if(actor.getElement() == null || ((Player) actor.getElement()).getType() != SelectElement.ALLIE) //&& select.getElement().getType() != SelectElement.ALLIE
        {
            if (!select.selected())
                if (actor.getElement() != null && ((Character) actor.getElement()).getType() != SelectElement.ALLIE) {

                select.select((Character) actor.getElement());
                select.keepActor(actor);
                select.getElement().setStance(Animations.HIGHLIGHT);
            }
        }
	}
	private void clickAllie(InputEvent event, float x, float y){
		if ( select.selected() && select.getElement().getType() == SelectElement.ALLIE) 
		{
			if( ! select.InMoveRange(actor) && !select.isAttacking())
			{
				select.cancelstuff(); return ;
			}
			if ( actor.getElement() == null && !select.getElement().isMoved() && !select.isAttacking() ) 
			{
				if (select.getElement().move(actor.getX2(),actor.getY2(), finder))
				{
					select.getElement().setMoved();
					select.refresh();
					select.deleteActor(actor);
					
					for (Node n : select.getElement().bestpath)
						layer.getCell(n.getX(), n.getY()).setTile( IATest.map.getTileSets().getTile(CellActor.CELL_STEPS));
					if (select.getElement() != null)
						actor.setElement( select.getElement() );
				}
			}
			if ( actor.getElement() != null )
			{
				if (select.getElement().isAction() && select.isAttacking() && select.InAtkTange(actor.getX2(), actor.getY2())) 
				{
					select.getElement().Attack(actor);
					select.clearMap();
					if( !select.getElement().isMoved() )
						select.select( select.getElement() );
				}
				else if (!select.getElement().isMoved())
				{
					if(select.getElement().getType() == SelectElement.ALLIE)
						select.getElement().setStance(Animations.STAND);
					select.deselect();
				}
			}
		}
		else if (actor.getElement() != null && ((Character) actor.getElement()).getType() == SelectElement.ALLIE ) {
			select.select((Character) actor.getElement());
			select.keepActor(actor);
			select.getElement().setStance(Animations.HIGHLIGHT);
		}
	}
	
	
	
	@Override
	public void enter(InputEvent event, float x, float y, int pointer,
			Actor fromActor) {
		
		info.cellTitle(actor.getX2(), actor.getY2(), finder);
		
		if (actor.getElement() != null )
			ielem.setElement((Character) actor.getElement());
		
		if(actor.getElement()==null){
			Pixmap pm = new Pixmap(Gdx.files.internal("cursor1.png"));
			Gdx.input.setCursorImage(pm, 0, 0);
			pm.dispose();
		}
		else if(((Character) actor.getElement()).getType()==SelectElement.ALLIE){
			Pixmap pm = new Pixmap(Gdx.files.internal("curseur2.png"));
			Gdx.input.setCursorImage(pm, 0, 0);
			pm.dispose();
		}
		else if(((Character) actor.getElement()).getType()==SelectElement.ENEMY){
			Pixmap pm = new Pixmap(Gdx.files.internal("curseur2.png"));
			Gdx.input.setCursorImage(pm, 0, 0);
			pm.dispose();
		}
		else { // ... TODO case of other elements
			Pixmap pm = new Pixmap(Gdx.files.internal("curseur2.png"));
			Gdx.input.setCursorImage(pm, 0, 0);
			pm.dispose();
		}
		
		super.enter(event, x, y, pointer, fromActor);
	}

}
