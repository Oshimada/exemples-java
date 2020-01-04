package com.tph.ingame.tools.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.tph.Resources;
import com.tph.IA.IATest;
import com.tph.IA.pathfinding.Node;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.ui.CellActor;


public abstract class Element extends Sprite{
	
	protected String 		nom,description; 
	protected int 			curX,oldX,curY,oldY;
	public Array<Node> 		bestpath;
	protected int 	  		nodePosition=0;
	protected Stats     	stats;
	protected float   		movementTime=2;
	protected Texture 		logo_img;
	protected TiledMapTileLayer layer;
	
	public Element(String url,String nom, String description, TiledMapTileLayer layer,int lvl){
		super(new Texture("mob.png"));
		this.nom=nom;
		if(description == null || description.trim().equals(""))
			description = "There's no much things known about him/her";
		this.description=description;
		
		this.layer=layer;
		System.out.println(nom);
		//logo_img=Resources.getManager().get("tur"+".png", Texture.class);
		stats=new Stats(url,lvl);
	}
	public void update(float delta) {
		transCell(delta);
	}
	
	private void transCell(float delta) {
		if(bestpath!=null)
		{   
			if(nodePosition<bestpath.size )
			{
				deplacer(delta);
			}
			else
			{
				for(Node n:bestpath)
					layer.getCell(n.getX(), n.getY()).setTile(IATest.map.getTileSets().getTile(CellActor.CELL_VOID));
				bestpath=null;
				
			}
    			
		}
	}
	private void deplacer(float delta){
		float speed=delta*movementTime*80;
		
		if( getX()==pathX() && getY() == pathY() ) 
			return ;
		if( getX() < pathX() )		setX(getX()+speed);
		else if( getX() > pathX() )	setX(getX()-speed);
		
		if( getY() < pathY() )		setY(getY()+speed);
		else if( getY() > pathY() )	setY(getY()-speed);
		
		if(Math.abs(getX()-pathX()) < speed ) placeX(bestpath.get(nodePosition).getX());
		if(Math.abs(getY()-pathY()) < speed ) placeY(bestpath.get(nodePosition).getY());
		
		if( getX()+getWidth()- speed <= pathX()+layer.getTileWidth()&& getX() >= pathX() &&
				getY() + getHeight() - speed  <= pathY() + layer.getTileHeight() && getY() >= pathY() &&
				nodePosition < bestpath.size )
		{
			nodePosition++;
		}
	}
	public boolean move(int x,int y,Path finder)
	{
		finder.setBounds(curX,curY , x,y);
	    bestpath=finder.FindPath(stats.getClasse(), stats.getRace());
	    nodePosition=0;
    	oldX=curX;oldY=curY;
    	if(bestpath!= null && bestpath.size>0 && bestpath.size < stats.getPM_MAX())
    	{
    	    if(curX==bestpath.get(0).getX() && curY == bestpath.get(0).getY())
    	    	nodePosition++;
    		return true;
    	}
	    bestpath=null;
	    return false;
	}
	
	public void placer(int x, int y){ 
		curX=(int)x; curY=(int)y;
		setPosition(x*layer.getTileWidth(), y*layer.getTileHeight());
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
		update(Gdx.graphics.getDeltaTime());
	}
	
	public CellActor getActor(){
		return Resources.stage.getActor(mapX(), mapY());
	}

	public float cordX(int x){	return x*layer.getTileWidth(); }
	public float cordY(int y){	return y*layer.getTileHeight(); }
	
	public int mapX(){ return (int)((getX())/layer.getTileWidth()); }
	public int mapY(){ return (int)((getY())/layer.getTileHeight()); }
	
	public float pathX(){ return cordX(bestpath.get(nodePosition).getX()); }
	public float pathY(){ return cordY(bestpath.get(nodePosition).getY()); }
	
	public void translateCell(Node i, Node j) { placer(j.getX(),j.getY()); }
	public void placeX(float X){ curX=(int)X; setX(X*layer.getTileWidth()); }
	public void placeY(float y){ curY=(int)y; setY(y*layer.getTileHeight()); }
	public int getOldX() {	return oldX;	}
	public int getOldY() {	return oldY;	}
	public String getNom() {  return nom;  }
	public String getDescription() {  return description;  }
	public TiledMapTileLayer getLayer() {  return layer;  }
	
	
	/*
	Move
	Attack
	Skill
	End
	*/

}
