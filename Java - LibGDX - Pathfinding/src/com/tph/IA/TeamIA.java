package com.tph.IA;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tph.Resources;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.characters.Character;
import com.tph.ingame.tools.characters.Classe;
import com.tph.ingame.tools.characters.Player;
import com.tph.ingame.tools.characters.PlayerGroup;
import com.tph.ingame.tools.map.LevelMap;
import com.tph.ingame.tools.ui.SelectElement;

import java.util.Calendar;

/**
 * @author ismael abstract Class TeamIA used to manage the IA to control a team
 *
 * 
 *         has a "current" element {@link com.tph.IA.IACharacter} wich is the
 *         current player managed by the class
 */

public abstract class TeamIA {

	protected IACharacter current;
	protected Player pl;
	protected float wait = 0 , waiting_time = .02f;

	/**
	 * empty constructor setting current to null 
	 */
	public TeamIA() {

		current = null ;
		pl = null ;

	}

	/**
	 *  considered as the main loop of the various team IA
	 *  this method updates the current player. and invokes the methods 
	 *  {@link #cacIA(com.badlogic.gdx.utils.Array, float)}
	 *  {@link #supportIA(com.badlogic.gdx.utils.Array, float)}
	 *  {@link #rangedIA(com.badlogic.gdx.utils.Array, float)}
	 *  to update each type of players in the team
	 */
	public void updateIA() {
		PlayerGroup playerGroup = IATest.players;
		float delta = Gdx.graphics.getDeltaTime();



		//
		//{
		//	if( wait < waiting_time)
		//		wait += delta;
		//	else
		//	{	wait = 0;
		if( IATest.fight == null && current == null )//&& pl == null
			for (Player player : playerGroup.getEnemies())
				if ( player.isTurn() )
				{
					pl = player;
                    break;
				}
		//	}
		//}

		if (current == null ) { //&& pl != null
			/*
			if (Resources.thread.isSleeping_cells())
			{
				Resources.thread.findCells(pl);
				System.out.println("FindCellsFor( " + pl.getNom() + " )");
				return;
			}
			*/
			//if(Resources.thread.isDone_cells())
			{
				current = new IACharacter(pl,getMovRangeCells(pl));
				System.out.println("DoneFindingFor( "+pl.getNom()+" )");
				pl = null ;
			}
		}





		if (current != null && !current.getPlayer().isTurn() )
		{
            System.out.println("ENDProcessingFor( "+current.getPlayer().getNom()+" )");
			current = null;
		}

		//System.out.println(current);

		if (current != null && IATest.fight == null) 
		{
			if( current.getPlayer().getStats().isRanged())
				rangedIA(playerGroup.getAllies(), delta);
			
			if( current.getPlayer().getStats().isCac())
				cacIA(playerGroup.getAllies(), delta);
			
			if( current.getPlayer().getStats().isSupport())
				supportIA(playerGroup.getAllies(), delta);

		}
	}

	/**
	 * edit the " current " ranged player's behavior
	 *
     */
	public abstract void rangedIA(Array<Player> defenders, float delta);

	/**
	 * edit the " current " ranged player's behavior
	 *
     */
	public abstract void cacIA(Array<Player> defenders, float delta);

	/**
	 * edit the " current " ranged player's behavior
	 *
	 */
	public abstract void supportIA(Array<Player> defenders, float delta);

	/**
	 *
	 *
	 */
	
	public boolean InRange(Player attacker, Player victim) {
		if (Math.abs(attacker.mapX() - victim.mapX())
				+ Math.abs(attacker.mapY() - victim.mapY()) <= attacker
				.getStats().getPM_MAX() + attacker.getStats().getMAX_RANGE())
			return true;
		return false;
	}

	/**
	 *
	 *
	 */
	
	public boolean InAtkRange(Player attacker, Player victim) {

		float max_range = attacker.getStats().getMAX_RANGE();
		float min_range = attacker.getStats().getMIN_RANGE();
		float distance = distanceBetween(attacker, victim);

		if (distance >= min_range && distance <= max_range)
			return true;

		return false;
	}

	/**
	 *
	 *
	 */
	
	public int distanceBetween(Player attacker, Player victim) {
		return Math.abs(attacker.mapX() - victim.mapX())
				+ Math.abs(attacker.mapY() - victim.mapY());
	}

	/**
	 *
	 *
	 **/
	
	public boolean InMoveRange(Player attacker, int i, int j) {
		if (Math.abs(attacker.mapX() - i) + Math.abs(attacker.mapY() - j) <= attacker
				.getStats().getPM_MAX())
			return true;
		return false;
	}


	/**
	 *
	 *
	 */
	
	public boolean blocked(int blocked, Player attacker) {
		byte classe = attacker.getStats().getClasse();

		if (classe == Classe.ARCHER) {// SEA, ROCK, NAN
			if (blocked == MapTile.SEA || blocked == MapTile.ROCK
					|| blocked == MapTile.UNREACHABLE)
				return true;
			return false;
		}
		if (classe == Classe.MAGE) {// SEA,ROCK,NAN, LAKE
			if (blocked == MapTile.SEA || blocked == MapTile.ROCK
					|| blocked == MapTile.UNREACHABLE
					|| blocked == MapTile.LAKE)
				return true;
			return false;
		}
		if (classe == Classe.TANK) { // SEA,ROCK,NAN,LAKE
			if (blocked == MapTile.SEA || blocked == MapTile.ROCK
					|| blocked == MapTile.UNREACHABLE
					|| blocked == MapTile.LAKE)
				return true;
			return false;
		}
		if (classe == Classe.WARRIOR) { // SEA,ROCK,NAN,LAKE
			if (blocked == MapTile.SEA || blocked == MapTile.ROCK
					|| blocked == MapTile.UNREACHABLE
					|| blocked == MapTile.LAKE)
				return true;
			return false;
		} else { // SEA,ROCK,NAN,LAKE
			if (blocked == MapTile.SEA || blocked == MapTile.ROCK
					|| blocked == MapTile.UNREACHABLE
					|| blocked == MapTile.LAKE)
				return true;
			return false;
		}
	}

	public static Array<Vector2> getMovRangeCells(Character element){

        if(element == null )
			return null;
        int cp = 0 ;

        double time=Calendar.getInstance().getTimeInMillis();
        double max1,max2,max3; max1=max2=max3=time;

        Array< Vector2 > cells = new Array<Vector2>();

		Path path=Resources.stage.getFinder();
		int PM = element.getStats().getPM_MAX() - 2;
		
		TiledMapTileLayer layer = (TiledMapTileLayer) IATest.map.getLayers().get(LevelMap.RANGE_LAYER);

		int BX = (element.mapX() - PM) >0 ? (element.mapX() - PM) : 1 ;
		int EX = (element.mapX() + PM) < layer.getWidth()  ? (element.mapX() + PM) : layer.getWidth()-2 ;
		
		int BY = (element.mapY() - PM) >0 ? (element.mapY() - PM) : 1 ;
		int EY = (element.mapY() + PM) < layer.getHeight() ? (element.mapY() + PM) : layer.getHeight()-2 ;

		for( int i = BX ; i < EX ; i++ )
			for( int j = BY ; j < EY ; j++ )
			{
                cp++;
				if( Math.abs(i - element.mapX())+Math.abs(j - element.mapY()) <= PM)
					if(SelectElement.canMoveToPosition(element, path, i , j ))
						cells.add(new Vector2(i,j));
			}
        System.out.println("\n"+(Calendar.getInstance().getTimeInMillis()-time)+" - "+cp+" - "+(EX-BX)+" X "+(EY-BY)+"");
		return cells;
	}
}
