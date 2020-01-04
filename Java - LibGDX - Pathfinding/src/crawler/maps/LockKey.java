package crawler.maps;

/**
 * Created by ismael on 25/08/2015.
 */
public class LockKey {

    private int x , y;
    private boolean activated = true;
    private LevelMap map;

    public LockKey(int x , int y , LevelMap map )
    {
        setX(x);
        setY(y);
        this.map = map;
    }

    public void Desactivate(){
        setActivated(false);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if( x >= 0 )
            this.x = x;
    }

    public void setY(int y) {
        if( y >=0 )
            this.y = y;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }
}
