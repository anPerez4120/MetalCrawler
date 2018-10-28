package csc472.depaul.edu.metalcrawler.GameComponents;

public class Environment {
    Tile[][] tileGrid;

    public Environment(int x, int y)
    {
        tileGrid = new Tile[x][y];
        // TODO: populate grid
    }

    public Tile[][] GetTiles()
    {
        return tileGrid;
    }



}
