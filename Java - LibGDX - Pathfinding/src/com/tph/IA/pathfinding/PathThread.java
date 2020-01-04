package com.tph.IA.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tph.IA.TeamIA;
import com.tph.ingame.tools.characters.*;

/**
 * Created by ismael on 28/06/2015.
 */
public class PathThread extends Thread {

    public static boolean done_cells = true;
    public static boolean done_path  = true;
    public static boolean sleeping_cells  = true;
    public static boolean sleeping_path   = true;
    public static float timer = 0;


    protected static Player element;

    protected static Array<Vector2> rangecells = null;
    protected static Array<Node> path = null;

   public PathThread(Runnable run){
       super(run);
   }

    public PathThread(){

        super(new Runnable() {
            @Override
            public void run() {
                float delta = Gdx.graphics.getDeltaTime();
                while(true) {
                    if(timer > 1)
                    {
                        timer = 0;
                        if (!done_cells) {
                            rangecells = TeamIA.getMovRangeCells(element);
                            done_cells = true;
                        }

                        if (!done_path) {
                           //TODO
                        }
                        //System.out.println("Done_cells : "+done_cells+" Sleeping_cells : "+sleeping_cells);
                    }

                    timer += delta;
                }
            }
        });
    }
    public void findPath(){

    }
    public void findCells(com.tph.ingame.tools.characters.Character element){
        done_cells     = false;
        sleeping_cells = false;
        this.element = (Player) element;
    }
    public Array<Vector2> getMovRangeCells(com.tph.ingame.tools.characters.Character element){
        if(element == this.element && done_cells)
        {
            System.out.println("RangeCells IN Thread == null");
            sleeping_cells = true;
            done_cells = false;
            return rangecells;
        }
        sleeping_cells = false;

        return null;
    }
    public boolean isDone_cells() {
        return done_cells;
    }

    public boolean isDone_path() {
        return done_path;
    }

    public boolean isSleeping_cells() {
        return sleeping_cells;
    }

    public boolean isSleeping_path() {
        return sleeping_path;
    }
}
