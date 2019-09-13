package com.tph.ingame.tools.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tph.Resources;
import com.tph.IA.IATest;
import com.tph.IA.pathfinding.Node;
import com.tph.IA.pathfinding.Path;
import com.tph.ingame.tools.characters.Animations;
import com.tph.ingame.tools.characters.Character;
import com.tph.ingame.tools.map.LevelMap;

public class SelectElement {

	//private Table box;
    private Window window;
	private HorizontalGroup group;
    private TextButton cancel,end;
	private byte type;
	private Character element;
	private CellActor actor,oldactor;
	private boolean attacking = false;
	public static final byte ALLIE = 0, ENEMY = 1, NEUTRAL = 3;
    private Array<ImageButton> skills = new Array<ImageButton>();

	public SelectElement() {

		Skin skin = Resources.getManager().get("ui/menuSkin.json");
		BitmapFont font = Resources.getFont("font/euphorigenic.ttf", 25, Color.BLACK);
        //box = new Table();


        for(int i=0;i<4;i++)
            skills.add(new ImageButton(skin,"sword"));

        window = new Window("",skin,"skills");
        //box.setFillParent(true);
        //box.center();
        group= new HorizontalGroup();
        end = new TextButton("End",skin);
        end.getLabel().setStyle(new Label.LabelStyle(font, Color.BLACK));
        group.addActor(end);
        for(ImageButton button : skills)
            group.addActor(button);
        cancel = new TextButton("Cancel",skin);
        cancel.getLabel().setStyle(new Label.LabelStyle(font,Color.BLACK));
        group.addActor(cancel);
        group.fill();
        group.space(10);
        group.pad(30);

        setupButtons();
        group.clear();

        window.add(group);
        //window.pack();
        window.pad(30);
        window.setVisible(false);

	}

	public void select(Character element) {
		this.element = element;
        clearMap();
		if(element != null && !element.isMoved())
			showMovRange(element);
		refresh();
        window.setVisible(true);
	}

	public void refresh() {

		group.clear();
		setType(element.getType());

		if (type == ALLIE) {
			if (element.isTurn())
				selectAllie();
			else
				selectNeutral();
		}
		if (type == ENEMY)
			selectEnemy();
		if (type == NEUTRAL)
			selectNeutral();
	}

	public void selectAllie() {
		group.clear();

        group.addActor(end);
        for(ImageButton button : skills)
            group.addActor(button);
        group.addActor(cancel);
	}

	public void selectEnemy() {
		group.clear();
		group.addActor(cancel);
	}

	public void selectNeutral() {
		group.clear();
		group.addActor(cancel);
	}

	public void moveOver(Character element) {
		this.element = element;

	}

	public void deselect() {
		if(element != null)
			clearMap();
		this.element = null;
		group.clear();
        window.setVisible(false);
		
	}

	public Window getBox() {
		return window;
	}

	public void setType(byte type) {
		if (type == 0 || type == 1)
			this.type = type;
		else
			this.type = NEUTRAL;
	}

	public boolean selected() {
		return element != null;
	}

	public void cancelstuff()
	{
		if(getElement() != null )
		{	if(getElement().getType() == SelectElement.ALLIE)
			getElement().setStance(Animations.STAND);

			if (element.isAction() && element.isTurn() && element.isMoved()) {
				getElement().setMoved(false);
				element.placer(actor.getX2(), actor.getY2());
				refresh();
                System.out.println("( " + oldactor.getX2() + "," + oldactor.getY2() + ") ");
                System.out.println("( " + actor.getX2() + "," + actor.getY2() + ") ");

                if(oldactor!=null)
					oldactor.setElement(null);
				actor.setElement(element);
			}
		}
		deselect();
	}

	public void setupButtons() {
		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				cancelstuff();
				deselect();
			}

		});

		end.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (getElement().getType() == SelectElement.ALLIE)
                    getElement().setStance(Animations.STAND);
                element.endTurn();
                deselect();
            }
        });
		skills.get(0).addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                int MAX = element.getStats().getMAX_RANGE();
                int finX = element.getLayer().getWidth(),
                        finY = element.getLayer().getHeight();

                int debutX = element.mapX() - MAX, i;
                int debutY = element.mapY() - MAX, j;
                clearMap();
                TiledMapTileLayer layer = (TiledMapTileLayer) IATest.map.getLayers().get(LevelMap.RANGE_LAYER);

                if (element.bestpath == null)
                    for (int X = debutX; X <= element.mapX() + MAX; X++)
                        for (int Y = debutY; Y <= element.mapY() + MAX; Y++) {

                            if (X < 0)
                                i = 0;
                            else if (X > finX)
                                i = finX;
                            else
                                i = X;
                            if (Y < 0)
                                j = 0;
                            else if (Y > finY)
                                j = finY;
                            else
                                j = Y;

                            if (InAtkTange(i, j))
                                layer.getCell(i, j).setTile(IATest.map.getTileSets().getTile(CellActor.CELL_RANGE));
                        }
                attacking = true;
            }
        });
	}

	
	/**
	 * if the position ( x , y ) is in the attack range of the selected element
	 * @param  x
	 * @param  y
	 * @return wether it's true or false the position is in the range
	 */

	public boolean InAtkTange(int x, int y) {
		int MAX = element.getStats().getMAX_RANGE();
		int MIN = element.getStats().getMIN_RANGE();
		int distance = Math.abs(x - element.mapX())
				+ Math.abs(y - element.mapY());

		if (distance >= MIN && distance <= MAX)
			return true;
		return false;
	}

	/**
	 * if the actor is in the range of the selected element
	 * @param actor
	 * @return true if can move 
	 * else returns false
	 */
	public boolean InMoveRange(CellActor actor) {
		if (Math.abs(actor.getX2() - element.mapX())
				+ Math.abs(actor.getY2() - element.mapY()) < element
				.getStats().getPM_MAX())
			return true;
		return false;
	}

	/**
	 * clears every cell in the range layer
	 */
	
	public void clearMap() {
		attacking = false;
		int finX = element.getLayer().getWidth(), finY = element.getLayer()
				.getHeight();
		TiledMapTileLayer layer = (TiledMapTileLayer) IATest.map.getLayers()
				.get(LevelMap.RANGE_LAYER);
		for (int x = 0; x < finX ; x++ )
			for (int y = 0; y < finY ; y++ )
			{
				if(layer.getCell(x, y) == null)
					System.out.println("NULL CELL IN SELECTELEMENT - CHECK THE TMX");
				else
					layer.getCell(x, y).setTile(IATest.map.getTileSets().getTile(CellActor.CELL_VOID));
			}
		refresh();
	}
	/**
	 * displays the move range of the given character
	 * in the range layer
	 * @param element
	 */
	private void showMovRange(Character element){
		if(element == null || element.getType() == SelectElement.ENEMY)
			return ;
        long beg = TimeUtils.millis();

		Path path=Resources.stage.getFinder();
		int PM = element.getStats().getPM_MAX() - 2;

		TiledMapTileLayer layer = (TiledMapTileLayer) IATest.map.getLayers().get(LevelMap.RANGE_LAYER);

		int BX = (element.mapX() - PM) >0 ? (element.mapX() - PM) : 1 ;
		int EX = (element.mapX() + PM) < layer.getWidth()  ? (element.mapX() + PM) : layer.getWidth()-2 ;

		int BY = (element.mapY() - PM) >0 ? (element.mapY() - PM) : 1 ;
		int EY = (element.mapY() + PM) < layer.getHeight() ? (element.mapY() + PM) : layer.getHeight()-2 ;

		for( int i = BX ; i <= EX ; i++ )
			for( int j = BY ; j <= EY ; j++  )
			{
				if( Math.abs(i - element.mapX())+Math.abs(j - element.mapY()) <= PM)
					if(canMoveToPosition(element, path, i , j ))
						layer.getCell(i , j).setTile(IATest.map.getTileSets().getTile(CellActor.CELL_MOV));
			}
        System.out.println("show range : "+(-beg+TimeUtils.millis()));
	}
	/**
	 * if the element can move to the target position ( target X , target Y ).
	 * @param element
	 * @param finder
	 * @param targetX
	 * @param targetY
	 * @return true if the element can move, else returns false.  
	 */
	public static boolean canMoveToPosition(Character element , Path finder , int targetX , int targetY ){
		Array<Node> bp;
		finder.setBounds(element.mapX() , element.mapY() , targetX , targetY );
        Node[][] graphe = finder.getGraphe();
    	if(!graphe[targetX][targetY].isBlocked(element.getStats().getRace(),element.getStats().getClasse()) &&
                graphe[targetX][targetY].getDistanceFrom(graphe[element.mapX()][element.mapY()]) <= element.getStats().getPM_MAX())
        {
            bp=finder.FindPath(element.getStats().getClasse(), element.getStats().getRace());
            if(bp!= null && bp.size>0 &&bp.size < element.getStats().getPM_MAX())
                return true;
        }
	    bp=null;
	    return false;
	}

	public Character getElement() {
		return element;
	}

	public void keepActor(CellActor actor) {
		this.actor = actor;
	}

	public void deleteActor(CellActor act) {

		oldactor=act;
		this.actor.setElement(null);
		
	}
	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
}
