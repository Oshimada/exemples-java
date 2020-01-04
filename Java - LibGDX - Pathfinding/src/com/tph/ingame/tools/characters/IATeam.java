package com.tph.ingame.tools.characters;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.tph.Resources;
import com.tph.IA.MapTile;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.ui.CellActor;

public class IATeam {
	protected Array<Player> players;
	protected boolean focus = false , defend = false ;
	protected Array<Player> target;
	protected Path path;
	
	public IATeam(Array<Player> players , Array<Player> target , Path path) {
		this.players=players;
		this.target=target;
		this.path=path;
	}
	
	public void UpdateIA(){
		dumbIA();
	}
	
	public void dumbIA(){
		/*
		for(int i=0;i<players.size;i++)
		{	
			boolean done=false;
			if(players.get(i).isDead())
				i++;
			Player attacker = players.get(i);
			Player    last  = i-1 >= 0 ? players.get(i-1):null;
			
			for(Player victim:target)
				if(attacker.isTurn() && attacker.isAction() && !attacker.isMoved() && 
						( last == null || ( !last.isTurn() && (last.bestpath == null || last.isMoved())) ) )
					if( InRange(attacker,victim) ) 
					{
						moveToReachableCell(attacker,victim);
						done=true;
					}
			
			if(!done){
				attacker.endTurn();
			}
		}
		*/
			for(Player attacker:players)
			{	boolean done=false;
			for(Player victim:target)
				if(attacker.isTurn() && attacker.isAction() && !attacker.isMoved())
					if( InRange(attacker,victim) ) 
					{
						moveToReachableCell(attacker,victim);
						done=true;
					}
			if(!done)
				attacker.endTurn();
			}
	}
	public void moveToReachableCell(Player attacker,Player victim){
		
		int beginX = Math.min(attacker.mapX(),victim.mapX() );
		int beginY = Math.min(attacker.mapY(),victim.mapY() );
		int endX = Math.max(attacker.mapX(),victim.mapX() );
		int endY = Math.max(attacker.mapY(),victim.mapY() );
		
		int [][] collision=path.getCollisionlayer();
		
		if( attacker.isTurn())
		{	
			for(int i=beginX;i<endX;i++)
			{
				if(!InAtkRange(attacker.mapX(),attacker.mapY(),attacker.getStats().getMAX_RANGE(), victim))
				for(int j=beginY;j<endY;j++)
					if( !blocked ( collision[i][j] , attacker )&& InMoveRange(attacker,i,j)&& !attacker.isMoved() && 
					InAtkRange(i,j,attacker.getStats().getMAX_RANGE(), victim))
					{	
						CellActor oldcell=null,newcell=null;
						for(Actor actor:Resources.stage.getActors())
						{
							if(actor.getName().equals(i+","+j))
								newcell=(CellActor) actor;
							else 
								if(actor.getName().equals(attacker.mapX()+","+attacker.mapY()))
										oldcell=(CellActor) actor;
							if(oldcell!=null && newcell!=null)
								if(((CellActor)newcell).getElement()==null)
								{
									attacker.moveIA(i, j,path);
									newcell.setElement(attacker);
									oldcell.setElement(null);
								}
						}		
					}
				if(attacker.isMoved())break;
			}
			attacker.setMoved(true);
		}
		
		if( attacker.isAction() && attacker.isTurn() && attacker.bestpath==null
				&& InAtkRange(attacker.mapX(),attacker.mapY(), attacker.getStats().getMAX_RANGE(), victim))
		{	for(Actor actor:Resources.stage.getActors())
			{
				if(actor.getName().equals(victim.mapX()+","+victim.mapY()))
				{
					attacker.IAttack((CellActor) actor);
					break;
				}
			}
			attacker.setAction(false);
		}
		
		if(!attacker.isAction())
				attacker.endTurn();
	}
	

	public boolean InRange(Player attacker,Player victim) {
		if (Math.abs(attacker.mapX() - victim.mapX())+ Math.abs(attacker.mapY() - victim.mapY()) 
				<= attacker.getStats().getPM_MAX() + attacker.getStats().getMAX_RANGE())
			return true;
		return false;
	}
	public boolean InAtkRange(int i,int j,int range,Player victim) {

		if (Math.abs(i - victim.mapX())+ Math.abs(j - victim.mapY()) 
				<= range)
			return true;
		
		return false;
	}
	public boolean InMoveRange(Player attacker,int i,int j){
		if (Math.abs(attacker.mapX() - i)+ Math.abs(attacker.mapY() - j) 
				<= attacker.getStats().getPM_MAX() 
				)
			return true;
		return false;
	}
	public boolean blocked(int blocked,Player attacker)
	{
		byte  classe=attacker.getStats().getClasse();
		
		if(classe == Classe.ARCHER){// SEA, ROCK, NAN
			if(blocked==MapTile.SEA || blocked==MapTile.ROCK || blocked==MapTile.UNREACHABLE)
				return true;
			return false;
		}
		if(classe == Classe.MAGE){// SEA,ROCK,NAN, LAKE
			if(blocked==MapTile.SEA || blocked==MapTile.ROCK || blocked==MapTile.UNREACHABLE || blocked==MapTile.LAKE)
				return true;
			return false;
		}
		if(classe == Classe.TANK){ // SEA,ROCK,NAN,LAKE
			if(blocked==MapTile.SEA || blocked==MapTile.ROCK || blocked==MapTile.UNREACHABLE || blocked==MapTile.LAKE)
				return true;
			return false;
		}
		if(classe == Classe.WARRIOR){ // SEA,ROCK,NAN,LAKE
			if(blocked==MapTile.SEA || blocked==MapTile.ROCK || blocked==MapTile.UNREACHABLE || blocked==MapTile.LAKE)
				return true;
			return false;
		}
		else{ // SEA,ROCK,NAN,LAKE
			if(blocked==MapTile.SEA || blocked==MapTile.ROCK || blocked==MapTile.UNREACHABLE || blocked==MapTile.LAKE)
				return true;
			return false;
		} 
	}
}
/**

011111
111111
111111
111110








*/