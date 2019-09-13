package crawler.players;

import com.badlogic.gdx.Gdx;

import net.dermetfan.gdx.scenes.scene2d.ui.Popup;

/**
 * Created by ismael on 24/08/2015.
 */
public class Enemy extends Player {

    public boolean visible = true;
    public String rank ="noob";
    /* ranks :
         ***** Boss
         ****  captain( mini boss)
         ***   bigMom ( strong enemy)
         **    mercenary ( medium level soldier )
         *     noob ( lowest level soldier )
     */
    public Enemy(boolean visible, String nom)
    {
        super(); this.visible = visible;
        this.rank = nom;
    }

    public void Unlock() {
        setVisible(true);
    }



    public void dispose(){
        getTexture().dispose();
        setVisible(false);
    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;

    }
}
