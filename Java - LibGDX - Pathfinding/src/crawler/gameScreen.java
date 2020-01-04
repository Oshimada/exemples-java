package crawler;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import crawler.maps.LevelMap;
import crawler.maps.MapedStage;

/**
 * Created by ismael on 27/08/2015.
 */
public class gameScreen implements Screen {

    private LevelMap map;
    private MapedStage stage;
    private OrthogonalTiledMapRenderer renderer ;
    private OrthographicCamera camera;

    @Override
    public void show()
    {
        TmxMapLoader maploader = new TmxMapLoader();
        map      = (LevelMap) maploader.load("donjon1.tmx");
        stage    = new MapedStage();
        renderer = new OrthogonalTiledMapRenderer(map, stage. getBatch());
        camera   = new OrthographicCamera();

    }

    @Override
    public void render(float delta)
    {
        stage   .act();
        stage   .draw();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
