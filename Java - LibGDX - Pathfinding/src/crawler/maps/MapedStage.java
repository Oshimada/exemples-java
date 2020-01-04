package crawler.maps;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tph.IA.pathfinding.Path;

/**
 * Created by ismael on 24/08/2015.
 */
public class MapedStage extends Stage {

    private Table table;
    private Path finder;
    private LevelMap map;
    private EnvironementToMap mapstuff;

    public MapedStage(Viewport v) {
        super();
    }

    public MapedStage() {
        super();
    }

    public MapedStage(TiledMapTileLayer layer , Path finder) {
        super();
        this.finder = finder;
        SetupEnvironnement(map);
    }
    {
        table = new Table();
        table.setFillParent(true);
        table.setName("table");
    }
    private void SetupEnvironnement(LevelMap map) {
        setMap(map);
        mapstuff = new EnvironementToMap(map);
    }

    public void setMap(LevelMap map) {
        try{
            if(map == null) throw new NullPointerException();
            this.map = map;
        }catch(NullPointerException e)
        {
            e.printStackTrace();
            return;
        }
    }
}