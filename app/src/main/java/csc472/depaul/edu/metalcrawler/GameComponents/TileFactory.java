package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Stack;

public class TileFactory {
    Stack<Tile> tiles;

    private static TileFactory instance;
    public static TileFactory Instance()
    {
        if (instance == null) {
            instance = new TileFactory();
            instance.tiles = new Stack<Tile>();
        }
        return instance;
    }


    public Tile GetTile(int x, int y)
    {
        if (tiles.empty())
        {
            Tile tile = new Tile(GameManager.Instance().GetView(),x,y);
            return tile;
        }
        else
        {
            Tile tile = tiles.pop();
            GameManager.Instance().AddSprite(tile);
            tile.SetPosition(x,y);
            return tile;
        }
    }

    public void ReturnTile(Tile tile)
    {
        GameManager.Instance().RemoveSprite(tile);
        tiles.push(tile);
    }


}
