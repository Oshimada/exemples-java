package crawler.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import crawler.maps.LevelMap;

/**
 * Created by ismael on 25/08/2015.
 */
public class Door {

    private int x , y;
    private boolean locked = true;
    private LevelMap map;

    public Door(int x , int y)
    {
        setX(x);
        setY(y);
    }

    public Door(int x , int y , LevelMap map)
    {
        this(x,y);
        this.map = map;
    }

    public void Unlock()
    {
        setLocked(false);
        TiledMapTileLayer layer = ( TiledMapTileLayer )(map.getLayers().get(0));

        layer.getCell(x,y).setTile(map.getTile("vide"));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x)
    {
        if( x >= 0 )
            this.x = x;
    }

    public void setY(int y) {
        if( y >=0 )
            this.y = y;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
