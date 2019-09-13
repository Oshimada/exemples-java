package crawler.maps;
import com.badlogic.gdx.utils.Array;

import crawler.players.Enemy;

/**
 * Created by ismael on 25/08/2015.
 */
public class Lock {

    private int id;
    private boolean locked = true;
    private Array<LockKey> keys;
    private Array<Door> doors;
    private Array<Enemy> enemies;

    public Lock(int id )
    {
        keys =  new Array<LockKey>();
        doors = new Array<Door>();
    }

    public void setId(int id) {
        if(id >=0)
            this.id = id;
        else
            System.out.println("ID < 0 problem");
    }

    public void Update()
    {
        if( locked )
        {
            locked = false;
            for(LockKey key:keys)
                if (key.isActivated())
                    locked = true;
        }
        if(!locked)
        {
            UnlockDoors();
            UnlockEnemies();
        }
    }

    private void UnlockEnemies() {
        for( Enemy enemy : enemies)
            enemy.Unlock();
    }

    private void UnlockDoors() {
        for(Door door : doors)
            door.Unlock();
    }

    public int getId() {
        return id;
    }
    public void addDoor(Door d){
        if(d != null)
            doors.add(d);
    }
    public void addLockKey(LockKey d){
        if(d != null)
            keys.add(d);
    }
    public void addEnemy(Enemy e)
    {

    }

    public Array<Door> getDoors() {
        return doors;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public Array<LockKey> getKeys() {
        return keys;
    }
}
