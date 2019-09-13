package com.tph.IA.pathfinding;

import com.badlogic.gdx.utils.Array;
import com.tph.ingame.tools.characters.Classe;

import java.util.Calendar;

public class PathFinder {
	
	public static int NODE_DISTANCE_VALUE = 10; // distance parcouru en un noeud
	protected static Node startPoint , endPoint; 
	public static Array<Node> openList ; // liste ouverte
	public static Array<Node> closeList; // liste fermee
	public PathFinder(){
		openList=new Array<Node>();
		closeList=new Array<Node>();
		startPoint = null;
		endPoint = null ;
	}
	/**
	 * @param graphe collision map
	 * @param startPoint point de depart
	 * @param endpoint point d'arrivee
	 * @return le chemin rapidement
	 */
	public static Array<Node> FindPath(Node[][] graphe,Node startPoint,Node endpoint, byte race, byte classe ){

        double time= Calendar.getInstance().getTimeInMillis();
		PathFinder.startPoint=startPoint;
		endPoint=endpoint;
		openList.clear();
		closeList.clear();
		
		Array<Node> finalPath=new Array<Node>();
		
		addOpenList(startPoint);
		Node current=null;
		
		while(openList.size>0)
		{
			current=getCurrent();
			if(current==endpoint){
				break;
			}
			addClosedList(current);
			Array<Node> neighbours=getNeighbours(current, graphe);
			int maxi=neighbours.size;
			for(int i=0;i<maxi;i++){
				Node node=neighbours.get(i);
				if(node.isBlocked(race, classe) || isOnCloseList(node))
					continue;
				int newG= node.getParent().getgDistance()+10;
				int newH= ( Math.abs(node.getX()-endpoint.getX()) + Math.abs(node.getY()-endpoint.getY()) ) * NODE_DISTANCE_VALUE;
				int newF=newG+newH;

				if(Classe.newPM(classe,race) <   Math.abs(node.getX()-endpoint.getX()) + Math.abs(node.getY()-endpoint.getY()))
					continue;
				if( isOnOpenList(node) ){
					if(newG<node.getgDistance()){
						node.setgDistance(newG);
						node.setParent(current);
						node.sethDistance(newH);
						node.setfPod(newF);
					}
				}
				else{
					addOpenList(node);
					node.setgDistance(newG);
					node.sethDistance(newH);
					node.setfPod(newF);
					node.setParent(current);
				}
			}
		}
		if(openList.size==0)
			return finalPath;
				
		Node lastnode=endpoint;

		while(lastnode!=startPoint){
			finalPath.add(lastnode);
			lastnode=lastnode.getParent();
		}

		if(!lastnode.isBlocked(race, classe))
			finalPath.add(lastnode);
		else
			finalPath.clear();
		
		finalPath.reverse();


        if(Calendar.getInstance().getTimeInMillis()-time >1.0)
            System.out.print(" "+(Calendar.getInstance().getTimeInMillis()-time));
        else
            System.out.print(".");
		return finalPath;
	}
	
	protected static Array<Node> getNeighbours(Node node,Node[][] graphe){
		Array<Node> neib=new Array<Node>();
		int maxcol=graphe[0].length;
		int maxline=graphe.length;
		
		int idTop    = node.getY()-1;
		int idBot    = node.getY()+1;
		int idLeft   = node.getX()-1;
		int idRight  = node.getX()+1;
		
		if(idTop>-1)
				neib.add(graphe[node.getX()][idTop]);
		if(idBot<maxcol)
				neib.add(graphe[node.getX()][idBot]);
		if(idLeft>-1)
				neib.add(graphe[idLeft][node.getY()]);
		if(idRight<maxline)
				neib.add(graphe[idRight][node.getY()]);
		
		return neib;
	}
	protected static boolean isOnCloseList(Node node){
		try{
			for(Node n:closeList)
			{
				if(n == null) throw new Exception(" null in closelist");
				if(node == null) throw new Exception(" node in params null");
				if (n.equals(node))
					return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	protected static boolean isOnOpenList(Node node){
		try {

			for (Node n : openList) {
				if (n == null) throw new Exception(" null in openlist");
				if (node == null) throw new Exception(" node in params null");
				if (n.equals(node))
					return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	protected static void addOpenList(Node node){
		openList.add(node);
		closeList.removeValue(node,false);
	}
	protected static void addClosedList(Node node){
		closeList.add(node);
		openList.removeValue(node,false);
	}
	protected static Node getCurrent(){
		Node curnode=null;
		int max=openList.size;
		int minF=Integer.MAX_VALUE;
		for(int i=0;i<max;++i){
			Node n=openList.get(i);
			if(n.getfPod()<minF){
				minF=n.getfPod();
				curnode=n;
			}
		}
		
		return curnode;
	}
	
}









//1301
