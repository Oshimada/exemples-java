package crawler.maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Array;

import crawler.players.Enemy;

/**
 * Created by ismael on 26/08/2015.
 */
public class EnvironementToMap {

    private Array<Lock> locks;
    private Array<Pit> pits;
    private Array<MapItem> items;
    private Array<Enemy> enemies;



    public EnvironementToMap(LevelMap map) {


        locks   = new Array<Lock>();
        pits    = new Array<Pit>();
        items   = new Array<MapItem>();
        enemies = new Array<Enemy>();

        MapObjects lock  = map.getLayers().get(LevelMap.LOCK_LAYER) .getObjects();
        MapObjects pit   = map.getLayers().get(LevelMap.PIT_LAYER)  .getObjects();
        MapObjects item  = map.getLayers().get(LevelMap.ITEM_LAYER) .getObjects();
        MapObjects enemy = map.getLayers().get(LevelMap.ENEMY_LAYER).getObjects();

        readLocks(  lock  , map);
        readPits(   pit   , map);
        readItems(  item  , map);
        readEnemies(enemy , map);
    }

    private void readEnemies(MapObjects enemy, LevelMap map) {

        for (MapObject obj : enemy){
            MapProperties prop = obj.getProperties();
            int type = (Integer)prop.get("type");

            int x = (int)( (Float) prop.get("x") / map.getTileWidth () ) ;
            int y = (int) ( (Float)prop.get("y") / map.getTileHeight() ) ;

            String nom = (String) obj.getName();

            if(! addLockedEnemy(x,y,nom))
                enemies.add(new Enemy(true,nom));


        }
    }

    private void readItems(MapObjects item, LevelMap map) {

    }

    private void readPits(MapObjects pit, LevelMap map) {

    }

    private void readLocks(MapObjects lock, LevelMap map) {

        for (MapObject obj : lock){
            MapProperties prop = obj.getProperties();
            int type = (Integer)prop.get("type");

            Lock tmp = null;
            for(Lock l : locks)
                if(l.getId() == type)
                    tmp = l;
            if( tmp == null )
                tmp = new Lock(type);

            int x = (int)( (Float) prop.get("x") / map.getTileWidth () ) ;
            int y = (int) ( (Float)prop.get("y") / map.getTileHeight() ) ;

            String nom = (String) obj.getName();
            if(nom == "lock")
                tmp.addDoor( new Door(x,y,map));
            if(nom == "key")
                tmp.addLockKey( new LockKey(x,y,map));
        }
    }
    private boolean addLockedEnemy(int x ,int y , String nom)
    {
        for( Lock l : locks)
            for(Door d: l.getDoors())
                if(d.getX() == x && d.getY() == y)
                {
                    l.addEnemy(new Enemy( false , nom ));
                    return true;
                }
        return false;
    }
}
