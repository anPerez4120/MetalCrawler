package csc472.depaul.edu.metalcrawler.GameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.CellularAutomata;
import csc472.depaul.edu.metalcrawler.R;

public class GameManager {
    // Android Objects
    Context context;
    View view;

    // Map Objects
    CellularAutomata cellAuto;
    int w;
    int h;
    List<Environment> environmentList;
    int currentEnvironment = -1;

    List<List<Sprite>> sprites;
    List<Actor> actors;

    Player player;
    // Singleton
    private static GameManager instance;
    public static GameManager Instance()
    {
        if (instance == null) {
            instance = new GameManager();
            instance.environmentList = new ArrayList<Environment>();
            instance.cellAuto = new CellularAutomata();
            instance.sprites = new ArrayList<List<Sprite>>();
            instance.actors = new ArrayList<Actor>();
        }

        return instance;
    }

    // GameStart
    public void GameStart(Context context, View view)
    {
        this.context = context;
        this.view = view;
        w = 15;
        h = 15;

        environmentList.add(new Environment(view,w,h));
        currentEnvironment++;


        player =  new Player(view);
        environmentList.get(currentEnvironment).PopulateTiles(cellAuto,w,h);
        // environmentList.get(currentEnvironment).PopulateEnemies(0,null);

        view.invalidate();
    }

    public void GameEnd()
    {
        // TODO
    }

    public void PerformTurn()
    {
        for (int i=0; i<actors.size(); i++)
        {
            actors.get(i).Update();
        }
        view.invalidate();
    }

    public void Draw(Canvas canvas)
    {
        for (List<Sprite> sprite : sprites)
        {
            for (Sprite s : sprite) {
                s.draw(canvas);
            }
        }
    }

    public void GenerateNewMap()
    {
        for (int i=0; i< actors.size(); i++)
        {
            if (actors.get(i).GetType() == EntityType.ENEMY)
            {
                ((Enemy) actors.get(i)).Recycle();
            }
        }
        environmentList.get(currentEnvironment).PopulateTiles(cellAuto,w,h);
    }


    public Context GetContext()
    {
        return context;
    }

    public View GetView()
    {
        return view;
    }


    public void AddSprite(Sprite sprite)
    {
        while (sprites.size() <= sprite.GetDrawLayer())
        {
            sprites.add(new ArrayList<Sprite>());
        }

        sprites.get(sprite.GetDrawLayer()).add(sprite);
    }

    public void RemoveSprite(Sprite sprite)
    {
        sprites.get(sprite.GetDrawLayer()).remove(sprite);
    }

    public boolean IsValidTile(int x, int y)
    {
        return environmentList.get(currentEnvironment).GetTiles()[x][y] != null;
    }

    public Tile GetTile(int x, int y)
    {
        return environmentList.get(currentEnvironment).GetTile(x,y);
    }

    public void AddActor(Actor actor)
    {
        actors.add(actor);
    }

    public void RemoveActor(Actor actor)
    {
        actors.remove(actor);
    }

    public void SetPlayer(Player player)
    {
        this.player = player;
    }

    public Player GetPlayer()
    {
        return player;
    }

    public Environment GetCurrentEnvironment()
    {
        return environmentList.get(currentEnvironment);
    }
}
