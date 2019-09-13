package com.tph.IA.pathfinding;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tph.IA.MapTile;
import com.tph.ingame.tools.characters.Classe;

import java.util.Calendar;

public class Path extends MapTile{

	protected Node startPoint,endPoint;
	protected Node[][] graphe;

    protected Array<Node> pt;

	public Path(int[][] collisionLayer, int width, int height) {
		super(collisionLayer, width, height);
	}

	public Path(TiledMapTileLayer tiledCollisionLayer){
		super(tiledCollisionLayer);
		graphe=new Node[width][height];
		for(int i=0;i<width;i++)
			for(int j=0;j<height;j++)
			{
				graphe[i][j]= new Node();
				graphe[i][j].setX(i);
				graphe[i][j].setY(j);
				graphe[i][j].setBlocked(collisionlayer[i][j]);

			}
	}
	public Path(MapTile map,Node start,Node end){
		this(map.getCollisionlayer(),map.getWidth(),map.getHeight());
	}

	/***************************************************/

	public Array<Node> FindPath(byte race,byte classe, int n){

		Array<Array<Node>> paths=new Array<Array<Node>>();
		Array<Node> ret = null ;
		for(int i=0;i<n;i++)
		{
			ret = PathFinder.FindPath(graphe, startPoint, endPoint, race, classe);
			if(ret != null && ret.size > 0 )
			{
				if(paths.size==0)
					paths.add(ret);
				else
					if(ret.size< paths.get(0).size)
						paths.add(ret);
			}

			ret = PathFinder.FindPath(graphe, endPoint, startPoint, race, classe);

            if(ret != null && ret.size > 0 )
			{
				ret.reverse();
				if(paths.size==0)
					paths.add(ret);
				else
					if(ret.size< paths.get(0).size)
						paths.add(ret);
			}
		}
		if(paths.size == 0)
			return null;
		ret = paths.get(0);
		
		for(Array<Node> r : paths)
			if(ret.size > r.size)
				ret=r;

		return ret;
	}

    /***************************************************/

    public Array<Node> FindPath(final byte race,final byte classe) {

         pt = FindPath(race,classe,2);
        return pt;
    }
    /***************************************************/


    /***************************************************/

    public void setBounds(int Ax,int Ay,int Bx,int By){
		startPoint=graphe[Ax][Ay];
		endPoint=graphe[Bx][By];
	}
	public void setEndCords(int Ax,int Ay){
		endPoint=graphe[Ax][Ay];
	}
	public Node[][] getGraphe()
    {
        return graphe;
    }
	public Node getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Node startPoint) {
		this.startPoint = startPoint;
	}
	public Node getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Node endPoint) {
		this.endPoint = endPoint;
	}
}
