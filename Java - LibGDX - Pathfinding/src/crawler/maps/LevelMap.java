package crawler.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;

/**
 *
 * Created by ismael on 24/08/2015.
 *
 */

public class LevelMap extends TiledMap
{


    protected Texture.TextureFilter filter;
    private static byte counter = 0 ;
    public static final byte
        COLLISION_LAYER = counter ++ ,
        ITEMS_LAYER     = counter ++ ,
        ENEMY_LAYER     = counter ++ ,
        LOCK_LAYER      = counter ++ ,
        PIT_LAYER       = counter ++ ,
        ITEM_LAYER      = counter ++    ;

    public static final int[]
        GROUND = new int[]{ COLLISION_LAYER , ITEMS_LAYER };


    private HashMap<String,Integer> tiles;


    public LevelMap()
    {
        super(); tiles = new HashMap<String, Integer>();
    }


    public float getTileWidth(){
        TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(COLLISION_LAYER);
        return layer.getTileWidth();
    }

    public float getTileHeight(){
        TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(COLLISION_LAYER);
        return layer.getTileHeight();
    }

    public int getWidth(){
        TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(COLLISION_LAYER);
        return layer.getWidth();
    }

    public int getHeight(){
        TiledMapTileLayer layer=(TiledMapTileLayer) getLayers().get(COLLISION_LAYER);
        return layer.getHeight();
    }

    public TiledMapTileLayer getTiledMapTileLayer(int i){
        return (TiledMapTileLayer) getLayers().get(i);
    }
    public TiledMapTile getTile(String nom){

        if ( tiles.containsKey(nom))
            return getTileSets().getTile(tiles.get(nom));
        try {

            int size = getTileSets().getTileSet(0).size();
            size += getTileSets().getTileSet(1).size();
            size += getTileSets().getTileSet(2).size();

            for (int i = 0; i < size; i++)
                if (getTileSets().getTile(i).getProperties().containsKey(nom)) {
                    tiles.put(nom, i);
                    return getTileSets().getTile(i);
                }
        }catch(NullPointerException e){
            System.out.println("NULL TILESET - ");
            e.printStackTrace();
        }
        return null;
    }
}
