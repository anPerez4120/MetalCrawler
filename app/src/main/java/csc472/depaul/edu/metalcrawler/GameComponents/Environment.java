package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;
import android.view.View;

public class Environment {
    Tile[][] tileGrid;
    int width, height;
    public Environment(View view, int x, int y)
    {
        tileGrid = new Tile[x][y];
        width = x;
        height = y;
        PopulateTiles(view);
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
                System.out.println(tileGrid[x][y]);
                tileGrid[x][y].draw(canvas);
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


}
