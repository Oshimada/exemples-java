package com.tph.IA.pathfinding;

import com.tph.IA.MapTile;
import com.tph.ingame.tools.characters.Classe;

public class Node {
	
	protected	Node parent ;		//  parent du noeud present
	protected	int gDistance ;		// distance parcourue 
	protected	int hDistance ;		// Distance vol d'oiseau
	protected	int fPod ;			// H+G
	protected	int blocked ;		// si obstacle
	protected	int x ;				// colonne du Noeud
	protected	int y ;				// ligne du Noeud
	protected   Node north,south,east,west ;

	public Node(Node parent){
		blocked = 0 ;
		gDistance = hDistance = fPod = 0 ;
		this.parent=parent;
	
	}
	public Node(){
		this( null );
		parent=this;
	}
	
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getgDistance() {
		return gDistance;
	}
	public void setgDistance(int gDistance) {
		this.gDistance = gDistance;
	}
	public int gethDistance() {
		return hDistance;
	}
	public void sethDistance(int hDistance) {
		this.hDistance = hDistance;
	}
	public int getfPod() {
		return fPod;
	}
	public void setfPod(int fPod) {
		this.fPod = fPod;
	}
	public boolean isBlocked(byte race,byte classe) {
		return block(race,classe);
	}
	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean block(byte race,byte classe)
	{
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
	public Node getNorth() {
		return north;
	}
	public void setNorth(Node north) {
		this.north = north;
	}
	public Node getSouth() {
		return south;
	}
	public void setSouth(Node south) {
		this.south = south;
	}
	public Node getWest() {
		return west;
	}
	public void setWest(Node west) {
		this.west = west;
	}
	public Node getEast() {
		return east;
	}
	public void setEast(Node east) {
		this.east=east;
	}
	public int getDistanceFrom(Node node) {
		return Math.abs( x - node.getX()) + Math.abs( y - node.getY());
	}
	public int getDistanceFromStart() {
        Node start=PathFinder.startPoint;
        return getDistanceFrom(start);
    }
	public int getDistanceFromEnd() {
		Node end=PathFinder.endPoint;
		return getDistanceFrom(end);
	}
	@Override
	public String toString() {
		return "-"+x+","+y+"-";
	}
}
