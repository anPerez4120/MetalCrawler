package csc472.depaul.edu.metalcrawler.GameComponents;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.Coord;
import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.TileBlockType;
import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.CellularAutomata;

import static csc472.depaul.edu.metalcrawler.GameComponents.SpawnChances.gold;

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
    List<Coord> tiles;
    Door door;
    void PopulateTiles(CellularAutomata cellAuto, int w, int h, int depth)
    {
        if (door != null)
            door.Recycle();

        csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.Tile[][] map = cellAuto.GenerateMap(w,h);
        tiles = cellAuto.GetEmptyTiles();

        List<Coord> tempTiles = new ArrayList<>();

        for (Coord tile : tiles)
        {
            tempTiles.add(tile);
            //TODO:Println
            Log.d("Environment: ","x: " + tile.x + " y: " + tile.y);
        }

        for (int x=0; x < width; x++)
        {
            for (int y=0; y < height; y++)
            {
                if (tileGrid[x][y] != null) {
                    if (tileGrid[x][y].GetEntity() != null)
                    {
                        /*
                        if (tileGrid[x][y].GetEntity().type == EntityType.GOLD) {
                            ((Gold)tileGrid[x][y].GetEntity()).Recycle();
                        }
                        if (tileGrid[x][y].GetEntity().type == EntityType.DOOR) {
                            ((Door)tileGrid[x][y].GetEntity()).Recycle();
                        }
                        */
                    }
                    tileGrid[x][y].Recycle();
                }
            }
        }

        width = map.length;
        height = map[0].length;

        Random random = new Random();

        tileGrid = new Tile[map.length][map[0].length];

        for (int x=0; x < map.length; x++)
        {
            for (int y=0; y < map[0].length; y++)
            {
                if (map[x][y].GetBlockType() == TileBlockType.EMPTY)
                    tileGrid[x][y] = TileFactory.Instance().GetTile(x,y);
            }
        }

        // SPAWNING ---
        Coord tile;
        // PLAYER / DOOR ---------------------------------------------------------------------------------

        tile = tempTiles.get(random.nextInt(tempTiles.size()));
        door = DoorFactory.Instance().GetDoor(tile.x,tile.y);
        tileGrid[tile.x][tile.y].SetEntity(door);
        tempTiles.remove(tile);

        tile = tempTiles.get(random.nextInt(tempTiles.size()));
        GameManager.Instance().GetPlayer().JumpToPosition(tile.x,tile.y);
        //TODO:remove log
        Log.d("Environment Player: ", "x:" + tile.x + " y:" + tile.y);
        tileGrid[tile.x][tile.y].SetEntity(GameManager.Instance().GetPlayer());
        tempTiles.remove(tile);

        // COGS / POTIONS ---------------------------------------------------------------------------------
        int cogs = (int)SpawnChances.gold.GetRandom(depth);
        for (int i=0; i<cogs; i++)
        {
            tile = tempTiles.get(random.nextInt(tempTiles.size()));
            //TODO:remove log
            Log.d("Environment Gold: ", "x:" + tile.x + " y:" + tile.y);
            tileGrid[tile.x][tile.y].SetEntity(EntityFactory.Instance().GetGold(tile.x, tile.y));
            tempTiles.remove(tile);
        }

        int potions = (int)SpawnChances.potions.GetRandom(depth);
        for (int i=0; i < potions; i++)
        {
            tile = tempTiles.get(random.nextInt(tempTiles.size()));
            //TODO:remove log
            Log.d("Environment Potion: ", "x:" + tile.x + " y:" + tile.y);
            tileGrid[tile.x][tile.y].SetEntity(EntityFactory.Instance().GetPotion(tile.x, tile.y));
            tempTiles.remove(tile);
        }

        // ENEMIES -----------------------------------------------------------------------------------------------
        int junkies = (int)SpawnChances.junkie.GetRandom(depth);
        for (int i=0; i < junkies; i++)
        {
            tile = tempTiles.get(random.nextInt(tempTiles.size()));
            //TODO:remove log
            Log.d("Environment Junkie: ", "x:" + tile.x + " y:" + tile.y);
            tileGrid[tile.x][tile.y].SetEntity(EnemyFactory.Instance().GetJunkie(tile.x, tile.y));
            tempTiles.remove(tile);
        }

        int bulls = (int)SpawnChances.bull.GetRandom(depth);
        for (int i=0; i < bulls; i++)
        {
            tile = tempTiles.get(random.nextInt(tempTiles.size()));
            //TODO:remove log
            Log.d("Environment Bull: ", "x:" + tile.x + " y:" + tile.y);
            tileGrid[tile.x][tile.y].SetEntity(EnemyFactory.Instance().GetBull(tile.x, tile.y));
            tempTiles.remove(tile);
        }

        int alchemists = (int)SpawnChances.alchemist.GetRandom(depth);
        for (int i=0; i < alchemists; i++)
        {
            tile = tempTiles.get(random.nextInt(tempTiles.size()));
            //TODO:remove log
            Log.d("Environment Alchemist: ", "x:" + tile.x + " y:" + tile.y);
            tileGrid[tile.x][tile.y].SetEntity(EnemyFactory.Instance().GetAlchemist(tile.x, tile.y));
            tempTiles.remove(tile);
        }
    }


    void SpawnEntity(List<Tile> tempTiles, Entity entity)
    {

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
        EnemyFactory.Instance().GetJunkie(3,3);
        EnemyFactory.Instance().GetJunkie(6,3);
        EnemyFactory.Instance().GetJunkie(3,9);
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

    public float GetPotionFrequency(int level)
    {
        return 0.5f;
    }
}
