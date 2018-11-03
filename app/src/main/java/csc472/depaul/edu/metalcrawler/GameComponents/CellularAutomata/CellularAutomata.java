//*/
package csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata;

// import csc472.depaul.edu.metalcrawler.GameComponents.Tile;

import java.sql.Time;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class CellularAutomata
{
    static int TILE_TYPES = 16;
    int width;
    int height;

    String seed;
    boolean useRandomSeed;


    float randomFillPercent = 0.45f;

    int smoothingIter = 0;
    int smoothingNeighborTarget = 4;

    int wallThresholdSize = 4;
    int roomThresholdSize = 4;

    Tile[][] map;

    Random random;
    public CellularAutomata()
    {
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    public Tile[][] GenerateMap(int x, int y)
    {
        width = x;
        height = y;

        map = new Tile[width][height];
        RandomFillMap();

        for (int i = 0; i < smoothingIter; i++)
        {
            SmoothMap();
        }

        SetRandomTileTypes();
        SetRoomHeights();

        CleanMap();
        return map;
    }

    void CleanMap()
    {
         ChangeRegion(wallThresholdSize, TileBlockType.WALL, TileBlockType.EMPTY); // TODO : ???

        List<List<Coord>> roomRegions = GetRegions(TileBlockType.EMPTY);
        List<Room> survivingRooms = new ArrayList<Room>();

        for (List<Coord> region : roomRegions)
        {
            if (region.size() < roomThresholdSize)
                {
                for (Coord tile : region)
                {
                    map[tile.x][tile.y] = new Tile(TileBlockType.WALL);
                }
            }
            else
                survivingRooms.add(new Room(region, map));
        }
        if (survivingRooms.size() != 0)
        {
            Collections.sort(survivingRooms);
            survivingRooms.get(0).MakeMainRoom();
            ConnectClosestRooms(survivingRooms, false);
        }
    }

    void ChangeRegion(int thresholdSize, TileBlockType srcType, TileBlockType destType)
    {
        List<List<Coord>> regions = GetRegions(srcType);

        for (List<Coord> region : regions)
        {
            if (region.size()< thresholdSize)
            {
                for (Coord tile : region)
                {
                    map[tile.x][tile.y] = new Tile(destType);
                }
            }
        }
    }

    void ConnectClosestRooms(List<Room> allRooms, boolean forceAccessabilityFromMainRoom ) // TODO ... ???
    {
        List<Room> roomListA = new ArrayList<Room>();
        List<Room> roomListB = new ArrayList<Room>();

        if (forceAccessabilityFromMainRoom)
        {
            for (Room room : allRooms)
            {
                if (room.isConnectedToMainRoom)
                    roomListB.add(room);
                else
                    roomListA.add(room);
            }
        }
        else
        {
            roomListA = allRooms;
            roomListB = allRooms;
        }

        int distanceMin = 0;
        Coord minTileA = new Coord();
        Coord minTileB = new Coord();
        Room minRoomA = new Room();
        Room minRoomB = new Room();
        boolean connection = false;

        for (Room roomA : roomListA)
        {
            if (!forceAccessabilityFromMainRoom)
            {
                connection = false;
                if (roomA.connectedRooms.size() > 0)
                {
                    continue;
                }
            }

            for (Room roomB : roomListB)
            {
                if (roomB == roomA || roomA.IsConnected(roomB))
                {
                    continue;
                }
                for (int tileIndexA = 0; tileIndexA < roomA.edgeTiles.size(); tileIndexA++)
                {
                    for (int tileIndexB = 0; tileIndexB < roomB.edgeTiles.size(); tileIndexB++) {
                        Coord tileA = roomA.edgeTiles.get(tileIndexA);
                        Coord tileB = roomB.edgeTiles.get(tileIndexB);
                        int distanceBetweenRooms = ((tileA.x - tileB.x)*(tileA.x-tileB.x)+ ((tileA.y - tileB.y)*(tileA.y-tileB.y))); // diff

                        if (distanceBetweenRooms < distanceMin || !connection)
                        {
                            distanceMin = distanceBetweenRooms;
                            connection = true;
                            minTileA = tileA;
                            minTileB = tileB;
                            minRoomA = roomA;
                            minRoomB = roomB;

                        }
                    }
                }
            }
        // really weird edgecases below vvv
            if (connection && !forceAccessabilityFromMainRoom)
            {
                CreatePassage(minRoomA, minRoomB, minTileA, minTileB);
            }
        }


        if (connection && forceAccessabilityFromMainRoom)
        {
            CreatePassage(minRoomA, minRoomB, minTileA, minTileB);
            ConnectClosestRooms(allRooms, true);
        }

        if (!forceAccessabilityFromMainRoom)
        {
            ConnectClosestRooms(allRooms, true);
        }
    }

    void CreatePassage(Room roomA, Room roomB, Coord tileA, Coord tileB)
    {
        Room.ConnectRooms(roomA, roomB);
        // Debug.DrawLine(CoordToWorldPoint(tileA), CoordToWorldPoint(tileB), Color.green,100);

        List<Coord> line = GetLine(tileA, tileB);
        TileType tileType = random.nextInt(2) < 1 ? map[tileA.x][tileA.y].GetTileType() : map[tileB.x][tileB.y].GetTileType();
        for (Coord c : line)
        {
            DrawCircle(c,2, tileType);
        }

    }

    void DrawCircle(Coord c, int r, TileType tileType)
    {
        for (int x = -r; x <= r; x++)
        {
            for (int y = -r; y <= r; y++)
            {
                if (x*x + y*y <= r*r)
                {
                    int drawX = c.x + x;
                    int drawY = c.y + y;
                    if (IsInMapRange(drawX, drawY))
                    {
                        map[drawX][drawY] = new Tile(TileBlockType.EMPTY);
                        map[drawX][drawY].SetTileType(tileType);
                    }
                }
            }
        }
    }

    void SetRoomHeights()
    {
        List < List < Coord >> rooms = GetRooms();

        for (List<Coord> room : rooms)
        {
            float size = room.size();
            float minHeight = 4;
            float maxAddlHeight = 12;
            float newHeight = minHeight + (size / ((width * height) / 4) * maxAddlHeight);
            newHeight = 0;//UnityEngine.Random.Range(minHeight, maxAddlHeight);
            for (Coord c : room)
            {
                map[c.x][c.y].SetWallHeight(newHeight);
            }
            List<Coord> outline = GetOutlineTilesOfRoom(room);
            for (Coord c :outline)
            {
                map[c.x][c.y].SetWallHeight(newHeight);
            }
        }
    }

    void SetRandomTileTypes()
    {
        List<List<Coord>> rooms = GetRegions(TileBlockType.EMPTY);

        for (List<Coord> room : rooms)
        {
            List<Coord> outline = GetOutlineTilesOfRoom(room);
            TileType tileType = GetRandomTile();//(TileType)UnityEngine.Random.Range(0, TILE_TYPES);

            for(Coord coord : room)
            {
                map[coord.x][coord.y].SetTileType(tileType);
            }

            for(Coord coord : outline)
            {
                map[coord.x][coord.y].SetTileType(tileType);
            }
        }

    }

    TileType GetRandomTile()
    {
        float r = random.nextFloat();
        // return (TileType)(r * TILE_TYPES);
        float numCases = 4;
        if (r < 1 / numCases)
        {
            return TileType.YELLOW;
        }
        else if (r < 2 / numCases)
        {
            return TileType.BLACK;
        }
        else if (r < 3 / numCases)
        {
            return TileType.GREEN;
        }
        else
            return TileType.ST_GREEN;
    }

    List<Coord> GetLine(Coord from, Coord to) // TODO ... ???
    {
        List<Coord> line = new ArrayList<Coord>();

        int x = from.x;
        int y = from.y;

        int dx = to.x - from.x;
        int dy = to.y - from.y;

        boolean inverted = false;

        int step = Integer.signum(dx);
        int gradientStep = Integer.signum(dy);

        int longest = Math.abs(dx);
        int shortest = Math.abs(dy);

        if (longest < shortest)
        {
            inverted = true;
            longest = Math.abs(dy);
            shortest = Math.abs(dx);

            step = Integer.signum(dy);
            gradientStep = Integer.signum(dx);
        }

        int gradientAccumulation = longest / 2;
        for (int i=0; i<longest; i++)
        {
            line.add(new Coord(x, y));

            if (inverted)
            {
                y += step;
            }
            else
            {
                x += step;
            }

            gradientAccumulation += shortest;
            if (gradientAccumulation >= longest)
            {
                if (inverted)
                {
                    x += gradientStep;
                }
                else
                {
                    y += gradientStep;
                }
                gradientAccumulation -= longest;
            }
        }
        return line;
    }

    /*/
    Vector3 CoordToWorldPoint(Coord tile)
    {
        return new Vector3(-width / 2 + .5f + tile.x, 2, -height / 2 + .5f + tile.y);
    }
//*/
    List<List<Coord>> GetRegions(TileBlockType tileType)
    {
        List<List<Coord>> regions = new ArrayList<List<Coord>>();
        int[][] mapFlags = new int[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (mapFlags[x][y] == 0 && map[x][y].GetBlockType() == tileType)
                {
                    List<Coord> newRegion = GetRegionTiles(x, y);
                    regions.add(newRegion);

                    for (Coord tile : newRegion)
                    {
                        mapFlags[tile.x][tile.y] = 1;
                    }
                }
            }
        }

        return regions;
    }

    List<Coord> GetRegionTiles(int startX, int startY)
    {
        List<Coord> tiles = new ArrayList<Coord>();
        int[][] mapFlags = new int[width][height];
        TileBlockType tileType = map[startX][startY].GetBlockType();

        Queue<Coord> queue = new ArrayDeque<Coord>(); // TODO:  What the fuck
        queue.add(new Coord(startX, startY));
        mapFlags[startX][startY] = 1;

        while (queue.size() > 0)
        {
            Coord tile = queue.remove();
            tiles.add(tile);

            for (int x = tile.x - 1; x <= tile.x+1;x++)
            {
                for (int y = tile.y - 1; y <= tile.y+1; y++)
                {
                    if (IsInMapRange(x,y) && (y == tile.y || x == tile.x))
                    {
                        if (mapFlags[x][y] == 0 && map[x][y].GetBlockType() == tileType)
                        {
                            mapFlags[x][y] = 1;
                            queue.add(new Coord(x, y));
                        }
                    }
                }
            }
        }

        return tiles;
    }

    boolean IsInMapRange(int x, int y)
    {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // public static bool IsInMapRange(int x, int y, Tile[][] map)
    // {
    //     return x >= 0 && x < map.GetLength(0) && y >= 0 && y < map.GetLength(1);
    // }

    void RandomFillMap()
    {
        for (int x=0;x < width; x++)
        {
            for (int y=0; y < height; y++)
            {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
                {
                    map[x][y] = new Tile(TileBlockType.WALL);
                }
                else
                {
                    map[x][y] = new Tile((random.nextFloat() < randomFillPercent) ? TileBlockType.WALL : TileBlockType.EMPTY);
                }
            }
        }
    }

    void SmoothMap()
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int neighborTiles = GetSurroundingWallCount(x, y);

                if (neighborTiles > smoothingNeighborTarget)
                    map[x][y] = new Tile(TileBlockType.WALL);
                else if (neighborTiles < smoothingNeighborTarget)
                    map[x][ y] = new Tile(TileBlockType.EMPTY);
            }
        }
    }

    int GetSurroundingWallCount(int gridX, int gridY)
    {
        int wallCount = 0;
        for (int nX = gridX - 1; nX <= gridX + 1; nX ++)
        {
            for (int nY = gridY - 1; nY <= gridY + 1; nY++)
            {
                if (IsInMapRange(nX,nY))
                {
                    if (nX != gridX || nY != gridY)
                    {
                        wallCount += map[nX][ nY].GetBlockType() == TileBlockType.EMPTY ? 0 : 1;
                    }
                }
                else
                {
                    wallCount++;
                }
            }
        }

        return wallCount;
    }

    public List<Coord> GetOutlineTiles()
    {
        List<List<Coord>> roomRegions = GetRegions(TileBlockType.EMPTY);
        List<Coord> edgeTiles = new ArrayList<Coord>();

        for (List<Coord> tiles : roomRegions)
        {
            for (Coord tile : tiles)
            {
                for (int x = tile.x - 1; x <= tile.x + 1; x++)
                {
                    for (int y = tile.y - 1; y <= tile.y + 1; y++)
                    {
                        if (x == tile.x || y == tile.y)
                        {
                            if (IsInMapRange(x, y))
                            {
                                if (map[x][ y].GetBlockType() != TileBlockType.EMPTY)
                                {
                                    Coord c = new Coord(x, y);
                                    if (!edgeTiles.contains(c))
                                    edgeTiles.add(c);
                                }
                            }
                        }
                    }
                }
            }
        }

        return edgeTiles;
    }

    List<Coord> GetOutlineTilesOfRoom(List<Coord> room)
    {
        List<Coord> edgeTiles = new ArrayList<Coord>();

        for (Coord tile : room)
        {
            for (int x = tile.x - 1; x <= tile.x + 1; x++)
            {
                for (int y = tile.y - 1; y <= tile.y + 1; y++)
                {
                    if (x == tile.x || y == tile.y)
                    {
                        if (IsInMapRange(x, y))
                        {
                            if (map[x][y].GetBlockType() != TileBlockType.EMPTY)
                            {
                                Coord c = new Coord(x, y);
                                if (!edgeTiles.contains(c))
                                edgeTiles.add(c);
                            }
                        }
                    }
                }
            }
        }

        return edgeTiles;
    }

    public List<Coord> GetEmptyTiles()
    {
        List<Coord> retList = new ArrayList<Coord>();
        List<List<Coord>> rooms = GetRooms();
        for(List<Coord> tiles : rooms)
        {
            for (Coord tile : tiles)
            {
                retList.add(tile);
            }
        }
        return retList;
    }

    public List<List<Coord>> GetRooms()
    {
        return GetRegions(TileBlockType.EMPTY);
    }

    /*/
    public Vector2Int GetSize()
    {
        return new Vector2Int(width, height);
    }
    //*/

    public Tile[][] GetMap()
    {
        return map;
    }

}


//*/