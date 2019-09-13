package crawler.players;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ismael on 24/08/2015.
 */
public class Player extends Sprite {

    public boolean isEnemy() {

        return this instanceof Enemy;

    }
}
