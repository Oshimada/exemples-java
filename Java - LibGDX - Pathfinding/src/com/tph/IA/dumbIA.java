package com.tph.IA;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tph.Resources;
import com.tph.IA.pathfinding.Node;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.characters.Player;
import com.tph.ingame.tools.characters.Stats;
import com.tph.ingame.tools.ui.CellActor;

public class dumbIA extends TeamIA {
	
	private float w8 = 0, waiting = .5f;
	
	@Override
	public void rangedIA(Array<Player> defenders, float delta) {
		
		Stats      stats =  current.getPlayer().getStats();
        int distance;
        Array<Vector2> cells =current.getCells();

            for (Player player : defenders) {
                if (InAtkRange(current.getPlayer(), player) && current.getPlayer().isAction() && IATest.fight == null) {
                    {

                        if (w8 > waiting) {
                            w8 = 0;
                            current.getPlayer().IAttack(player.getActor());
                            current.getPlayer().endTurn();
    //                        current = null;
                            break;
                        }
                        else
                           w8 += delta;
                    }
                }
                else
                {
                    if (!current.getPlayer().isAction()) {
                        current.getPlayer().endTurn();
  //                      current = null;
                        break;
                    }
                }

                if (cells != null && IATest.fight == null && current.getPlayer().bestpath == null && !current.getPlayer().isMoved()) {

                    if(cells.size == 0)
                    {
                        current.getPlayer().endTurn();
//                        current = null;
                    }

                    for (Vector2 cell : cells) {

                        distance = Math.abs(player.mapX() - (int) (cell.x)) + Math.abs(player.mapY() - (int) (cell.y));

                        if (distance >= stats.getMIN_RANGE() && distance <= stats.getMAX_RANGE()) {
                            CellActor actor = Resources.stage.getActor((int) cell.x, (int) cell.y);

                            if (actor.getElement() == null) {

                                CellActor act = current.getPlayer().getActor();
                                current.getPlayer().moveIA((int) cell.x, (int) cell.y, Resources.getStage().getFinder());
                                actor.setElement(current.getPlayer());
                                act.setElement(null);
                                break;

                            }
                        }
                    }
                }

            }

            if( current.getPlayer().bestpath == null && !current.getPlayer().isMoved())
			    current.getPlayer().endTurn();
	}

	@Override
	public void cacIA(Array<Player> defenders, float delta) {
		
		rangedIA(defenders, delta);
		
	}
	
	@Override
	public void supportIA(Array<Player> defenders, float delta) {
		rangedIA(defenders, delta);
	}
	public Array<Node> pathTo( Player element , int X , int Y , int target  )
	{
		Path finder=Resources.stage.getFinder();
		Array<Node> bestpath;
		finder.setBounds(element.mapX() , element.mapY() , X , Y);
	    bestpath=finder.FindPath(element.getStats().getClasse(), element.getStats().getRace());
    	
	    if(bestpath!= null && bestpath.size>0 && bestpath.size <= target )
    		return bestpath;
	    
	    return null;
	}
}
