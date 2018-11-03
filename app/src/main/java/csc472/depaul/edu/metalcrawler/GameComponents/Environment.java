package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;
import android.view.View;

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

    public void Draw(Canvas canvas)
    {
        for (int x=0; x < width; x++)
        {
            for (int y=0; y < height; y++)
            {
                if (tileGrid[x][y] != null) {
                    tileGrid[x][y].draw(canvas);
                }
            }
        }
    }


    void PopulateTiles(View view)
    {
        for (int x=0; x < width; x++)
        {
            for (int y=0; y < height; y++)
            {
                tileGrid[x][y] = new Tile(view,x,y);
            }
        }
    }

    void PopulateTiles(View view, csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.Tile[][] map)
    {
        width = map.length;
        height = map[0].length;
        tileGrid = new Tile[map.length][map[0].length];

        for (int x=0; x < map.length; x++)
        {
            for (int y=0; y < map[0].length; y++)
            {
                if (map[x][y].GetBlockType() == TileBlockType.EMPTY)
                    tileGrid[x][y] = new Tile(view,x,y);
            }
        }
    }

}
