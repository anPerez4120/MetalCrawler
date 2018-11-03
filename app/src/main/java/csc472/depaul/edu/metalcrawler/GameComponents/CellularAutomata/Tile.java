package csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata;

public class Tile
{
    public int x;// get pr set
    public int y;// get pr set

    public int gCost ;
    public int hCost ;

    public Tile parent;

    public Tile()
    {
        x = 0;
        y = 0;
    }

    public Tile(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    TileBlockType blockType;
    TileType tileType;
    float wallHeight = 4;
    public void SetWallHeight(float wallHeight)
    {
        this.wallHeight = wallHeight;
    }

    public float GetWallHeight()
    {
        return wallHeight;
    }
    public TileBlockType GetBlockType()
    {
        return blockType;
    }

    public TileType GetTileType()
    {
        return tileType;
    }

    public Tile(TileBlockType blockType)
    {
        this.blockType = blockType;
    }

    public void SetTileType(TileType tileType)
    {
        this.tileType = tileType;
    }

    public int fCost;/*/
    {
        get
        {
            return gCost + hCost;
        }
    }//*/

    public void Set_HCost(int hCost)
    {
        this.hCost = hCost;
    }

    public void Set_GCost(int gCost)
    {
        this.gCost = gCost;
    }

    public void SetParent(Tile parent)
    {
        this.parent = parent;
    }
/*/
    public Vector3 GetPos()
    {
        return new Vector3((float)x, 0, (float)y);
    }
//*/
    public void SetPos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}