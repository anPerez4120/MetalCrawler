package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;
import android.view.View;

import java.util.List;

import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.TileBlockType;
import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.CellularAutomata;

public class Environment {
    Tile[][] tileGrid;
    int width, height;
    public Environment(View view, int x, int y)
    {
        tileGrid = new Tile[x][y];
        width = x;
        height = y;
        // PopulateTiles(view);
        // TODO: populate grid
    }

    public Tile[][] GetTiles()
    {
        return tileGrid;
    }

    void PopulateTiles(csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.Tile[][] map)
    {
        for (int x=0; x < width; x++)
        {
            for (int y=0; y < height; y++)
            {
                if (tileGrid[x][y] != null)
                    tileGrid[x][y].Recycle();
            }
        }

        width = map.length;
        height = map[0].length;


        tileGrid = new Tile[map.length][map[0].length];

        for (int x=0; x < map.length; x++)
        {
            for (int y=0; y < map[0].length; y++)
            {
                if (map[x][y].GetBlockType() == TileBlockType.EMPTY)
                    tileGrid[x][y] = TileFactory.Instance().GetTile(x,y);
            }
        }
    }

    public Tile GetTile(int x, int y)
    {
        if (x > -1 && y > -1 && x < width && y < height)
            return tileGrid[x][y];
        else
            return null;
    }


    public void PopulateEnemies(int depth, List<Tile> tiles)
    {
        EnemyFactory.Instance().GetEnemy(3,3);
        EnemyFactory.Instance().GetEnemy(6,3);
        EnemyFactory.Instance().GetEnemy(3,9);
    }

    public void HookUpTile(int oldX, int oldY, int newX, int newY, Entity entity)
    {
        Tile oldTile = GetTile(oldX,oldY);
        if (oldTile != null)
            oldTile.SetEntity(null);

        Tile newTile = GetTile(newX,newY);
        if (newTile != null)
            newTile.SetEntity(entity);
    }
}
