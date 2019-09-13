package crawler.maps;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tph.ingame.tools.ui.CellActor;

import crawler.players.PlayerActor;
import crawler.players.PlayerListener;

/**
 * Created by ismael on 24/08/2015.
 */
public class CellListener implements EventListener {

    private PlayerActor actor;
    private static PlayerListener playerlistener = new PlayerListener();

    public CellListener (PlayerActor actor)
    {
        this.actor = actor ;
    }


    @Override
    public boolean handle(Event event) {
        return false;
    }
}
