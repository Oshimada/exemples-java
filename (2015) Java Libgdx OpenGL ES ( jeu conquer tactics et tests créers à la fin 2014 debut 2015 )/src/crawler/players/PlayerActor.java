package crawler.players;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tph.ingame.tools.characters.Element;

/**
 * Created by ismael on 24/08/2015.
 */
public class PlayerActor extends Actor {

    protected TiledMapTileLayer.Cell cell ;
    protected Player player ;
    protected int x , y ;

    public PlayerActor( TiledMapTileLayer.Cell cell , int x , int y)
    {
        this.cell = cell;
        this.x = x ;
        this.y = y ;
    }

    public Player getPlayer( )
    {
        if( player != null )
            return player;
        System.out.println(" IN PlayerActor.getPlayer() - returning PLAYER NULL");
        return null;
    }

    public void setPlayer( Player player )
    {
        if( player != null )
        {
            this.player = player ;
            return;
        }
        System.out.println(" IN PlayerActor.setPlayer() - argument PLAYER is NULL");
    }

    public int getCellX (  )      {  return x;  }
    public void setCellX( int x ) {  if( x > -1 )  this.x = x;  }
    public int getCellY (  )      {  return y;  }
    public void setCellY( int y ) {  if( y > -1 )  this.y = y;  }

}
