/*/
public enum TileBlockType
{
    EMPTY = 0,
    WALL,
    PIT,
    OPENING,
}

public enum TileType
{
    YELLOW = 0,
    ORANGE ,
    RED ,
    PINK,
    PURPLE,
    BLUE,
    TEAL,
    GREEN,
    BLACK,
    WHITE,
    ST_WHITE,
    ST_PINK,
    ST_ORANGE,
    ST_RED,
    ST_BLUE,
    ST_GREEN
}


public class MapGenerator
    {
static int TILE_TYPES = 16;
        int width;
        int height;

        String seed;
        boolean useRandomSeed;


        int randomFillPercent;

        int smoothingIter = 5;
        int smoothingNeighborTarget = 4;

         int wallThresholdSize = 50;
         int roomThresholdSize = 50;

        Tile[,] map;

private void Start()
{
// GenerateMap();
}

private void Update()
{
}

public void GenerateMap()
{
    map = new Tile[width, height];
    RandomFillMap();

    for (int i = 0; i < smoothingIter; i++)
    {
        SmoothMap();
    }

    SetRandomTileTypes();
    SetRoomHeights();

    CleanMap();
}

void CleanMap()
{
    ChangeRegion(wallThresholdSize, TileBlockType.WALL, TileBlockType.PIT);

    List<List<Coord>> roomRegions = GetRegions(TileBlockType.EMPTY);
    List<Room> survivingRooms = new List<Room>();

    for (List<Coord> region : roomRegions)
    {
        if (region.Count < roomThresholdSize)
            {
            for (Coord tile : region)
            {
                map[tile.x, tile.y] = new Tile(TileBlockType.WALL);
            }
        }
        else
            survivingRooms.Add(new Room(region, map));
    }
    if (survivingRooms.Count != 0)
    {
        survivingRooms.Sort();
        survivingRooms[0].MakeMainRoom();
        ConnectClosestRooms(survivingRooms);
    }
}

void ChangeRegion(int thresholdSize, TileBlockType srcType, TileBlockType destType)
{
List<List<Coord>> regions = GetRegions(srcType);

foreach (List<Coord> region in regions)
{
if (region.Count < thresholdSize)
{
foreach (Coord tile in region)
{
map[tile.x, tile.y] = new Tile(destType);
}
}
}
}

void ConnectClosestRooms(List<Room> allRooms, bool forceAccessabilityFromMainRoom = false) // TODO ... ???
{
List<Room> roomListA = new List<Room>();
List<Room> roomListB = new List<Room>();

if (forceAccessabilityFromMainRoom)
{
foreach (Room room in allRooms)
{
if (room.isConnectedToMainRoom)
roomListB.Add(room);
else
roomListA.Add(room);
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
bool connection = false;

foreach (Room roomA in roomListA)
{
if (!forceAccessabilityFromMainRoom)
{
connection = false;
if (roomA.connectedRooms.Count > 0)
{
continue;
}
}

foreach (Room roomB in roomListB)
{
if (roomB == roomA || roomA.IsConnected(roomB))
{
continue;
}
for (int tileIndexA = 0; tileIndexA < roomA.edgeTiles.Count; tileIndexA++)
{
for (int tileIndexB = 0; tileIndexB < roomB.edgeTiles.Count; tileIndexB++) {
Coord tileA = roomA.edgeTiles[tileIndexA];
Coord tileB = roomB.edgeTiles[tileIndexB];
int distanceBetweenRooms = (int)(Mathf.Pow(tileA.x - tileB.x,2)+ Mathf.Pow(tileA.y - tileB.y,2)); // diff

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
TileType tileType = UnityEngine.Random.value < 0.5f ? map[tileA.x, tileA.y].GetTileType() : map[tileB.x, tileB.y].GetTileType();
foreach (Coord c in line)
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
map[drawX, drawY] = new Tile(TileBlockType.EMPTY);
map[drawX, drawY].SetTileType(tileType);
}
}
}
}
}

void SetRoomHeights()
{
List < List < Coord >> rooms = GetRooms();

foreach (List<Coord> room in rooms)
{
float size = room.Count;
float minHeight = 4;
float maxAddlHeight = 12;
float newHeight = minHeight + (size / ((width * height) / 4) * maxAddlHeight);
newHeight = UnityEngine.Random.Range(minHeight, maxAddlHeight);
foreach (Coord c in room)
{
map[c.x, c.y].SetWallHeight(newHeight);
}
List<Coord> outline = GetOutlineTilesOfRoom(room);
foreach (Coord c in outline)
{
map[c.x, c.y].SetWallHeight(newHeight);
}
}
}

void SetRandomTileTypes()
{
List<List<Coord>> rooms = GetRegions(TileBlockType.EMPTY);

foreach (List<Coord> room in rooms)
{
List<Coord> outline = GetOutlineTilesOfRoom(room);
TileType tileType = GetRandomTile();//(TileType)UnityEngine.Random.Range(0, TILE_TYPES);

foreach(Coord coord in room)
{
map[coord.x, coord.y].SetTileType(tileType);
}

foreach(Coord coord in outline)
{
map[coord.x, coord.y].SetTileType(tileType);
}
}

}

TileType GetRandomTile()
{
float r = UnityEngine.Random.value;
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
List<Coord> line = new List<Coord>();

int x = from.x;
int y = from.y;

int dx = to.x - from.x;
int dy = to.y - from.y;

bool inverted = false;
int step = Math.Sign(dx);
int gradientStep = Math.Sign(dy);

int longest = Math.Abs(dx);
int shortest = Math.Abs(dy);

if (longest < shortest)
{
inverted = true;
longest = Math.Abs(dy);
shortest = Math.Abs(dx);

step = Math.Sign(dy);
gradientStep = Math.Sign(dx);
}

int gradientAccumulation = longest / 2;
for (int i=0; i<longest; i++)
{
line.Add(new Coord(x, y));

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

Vector3 CoordToWorldPoint(Coord tile)
{
return new Vector3(-width / 2 + .5f + tile.x, 2, -height / 2 + .5f + tile.y);
}
List<List<Coord>> GetRegions(TileBlockType tileType)
{
List<List<Coord>> regions = new List<List<Coord>>();
int[,] mapFlags = new int[width, height];

for (int x = 0; x < width; x++)
{
for (int y = 0; y < height; y++)
{
if (mapFlags[x,y] == 0 && map[x,y].GetBlockType() == tileType)
{
List<Coord> newRegion = GetRegionTiles(x, y);
regions.Add(newRegion);

foreach (Coord tile in newRegion)
{
mapFlags[tile.x, tile.y] = 1;
}
}
}
}

return regions;
}

List<Coord> GetRegionTiles(int startX, int startY)
{
    List<Coord> tiles = new List<Coord>();
    int[,] mapFlags = new int[width, height];
    TileBlockType tileType = map[startX, startY].GetBlockType();

    Queue<Coord> queue = new Queue<Coord>();
    queue.Enqueue(new Coord(startX, startY));
    mapFlags[startX, startY] = 1;

    while (queue.Count > 0)
    {
        Coord tile = queue.Dequeue();
        tiles.Add(tile);

        for (int x = tile.x - 1; x <= tile.x+1;x++)
        {
            for (int y = tile.y - 1; y <= tile.y+1; y++)
            {
                if (IsInMapRange(x,y) && (y == tile.y || x == tile.x))
                {
                    if (mapFlags[x,y] == 0 && map[x,y].GetBlockType() == tileType)
                    {
                        mapFlags[x, y] = 1;
                        queue.Enqueue(new Coord(x, y));
                    }
                }
            }
        }
    }

    return tiles;
}

bool IsInMapRange(int x, int y)
{
    return x >= 0 && x < width && y >= 0 && y < height;
}

public static bool IsInMapRange(int x, int y, Tile[,] map)
        {
        return x >= 0 && x < map.GetLength(0) && y >= 0 && y < map.GetLength(1);
        }

        void RandomFillMap()
        {
        if (useRandomSeed)
        {
        seed = Time.time.ToString();
        }

        Random pseudoRandom = new Random(seed.GetHashCode());

        for (int x=0;x < width; x++)
        {
        for (int y=0; y < height; y++)
        {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
        {
        map[x, y] = new Tile(TileBlockType.WALL);
        }
        else
        {
        map[x, y] = new Tile((pseudoRandom.Next(0, 100) < randomFillPercent) ? TileBlockType.WALL : TileBlockType.EMPTY);
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
        map[x, y] = new Tile(TileBlockType.WALL);
        else if (neighborTiles < smoothingNeighborTarget)
        map[x, y] = new Tile(TileBlockType.EMPTY);
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
        wallCount += map[nX, nY].GetBlockType() == TileBlockType.EMPTY ? 0 : 1;
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
        List<Coord> edgeTiles = new List<Coord>();

        foreach (List<Coord> tiles in roomRegions)
        {
        foreach (Coord tile in tiles)
        {
        for (int x = tile.x - 1; x <= tile.x + 1; x++)
        {
        for (int y = tile.y - 1; y <= tile.y + 1; y++)
        {
        if (x == tile.x || y == tile.y)
        {
        if (IsInMapRange(x, y))
        {
        if (map[x, y].GetBlockType() != TileBlockType.EMPTY)
        {
        Coord c = new Coord(x, y);
        if (!edgeTiles.Contains(c))
        edgeTiles.Add(c);
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
        List<Coord> edgeTiles = new List<Coord>();

        foreach (Coord tile in room)
        {
        for (int x = tile.x - 1; x <= tile.x + 1; x++)
        {
        for (int y = tile.y - 1; y <= tile.y + 1; y++)
        {
        if (x == tile.x || y == tile.y)
        {
        if (IsInMapRange(x, y))
        {
        if (map[x, y].GetBlockType() != TileBlockType.EMPTY)
        {
        Coord c = new Coord(x, y);
        if (!edgeTiles.Contains(c))
        edgeTiles.Add(c);
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
        List<Coord> retList = new List<Coord>();
        List<List<Coord>> rooms = GetRooms();
        foreach(List<Coord> tiles in rooms)
        {
        foreach (Coord tile in tiles)
        {
        retList.Add(tile);
        }
        }
        return retList;
        }

public List<List<Coord>> GetRooms()
        {
        return GetRegions(TileBlockType.EMPTY);
        }

public Vector2Int GetSize()
        {
        return new Vector2Int(width, height);
        }

public Tile[,] GetMap()
        {
        return map;
        }

        }
        }

//*/