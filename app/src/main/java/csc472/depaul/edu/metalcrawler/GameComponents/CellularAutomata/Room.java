package csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Room implements Comparable<Room>
{
    public List<Coord> tiles; // get pr set
    public List<Coord> edgeTiles;
    public List<Room> connectedRooms;
    public int roomSize;
    public boolean isConnectedToMainRoom ;
    public boolean isMainRoom ;

    public Room() { }
    public Room(List<Coord> roomTiles, Tile[][] map)
    {
        tiles = roomTiles;
        roomSize = tiles.size();
        connectedRooms = new ArrayList<Room>();

        edgeTiles = new ArrayList<Coord>();

        for (Coord tile : tiles)
        {
            for (int x = tile.x - 1; x <= tile.x + 1; x++)
            {
                for (int y = tile.y - 1; y <= tile.y + 1; y++)
                {
                    if (x == tile.x || y == tile.y)
                    {
                        if (map[x][y].GetBlockType() != TileBlockType.EMPTY)
                        {
                            edgeTiles.add(tile);
                        }
                    }
                }
            }
        }
    }

    public static void ConnectRooms(Room roomA, Room roomB)
    {
        if (roomA.isConnectedToMainRoom)
        {
            roomB.MakeConnectedToMainRoom();
        }
        else if (roomB.isConnectedToMainRoom)
        {
            roomA.MakeConnectedToMainRoom();
        }

        roomA.connectedRooms.add(roomB);
        roomB.connectedRooms.add(roomA);
    }

    public boolean IsConnected(Room otherRoom)
    {
        return connectedRooms.contains(otherRoom);
    }

    public int CompareTo(Room otherRoom)
    {
        return Integer.compare(otherRoom.roomSize,roomSize);
    }

    public void MakeMainRoom()
    {
        isMainRoom = true;
        isConnectedToMainRoom = true;
    }

    public void MakeConnectedToMainRoom()
    {
        if (!isConnectedToMainRoom)
        {
            isConnectedToMainRoom = true;
            for (Room connectedRoom : connectedRooms)
            {
                connectedRoom.MakeConnectedToMainRoom();
            }
        }
    }

    @Override
    public int compareTo(@NonNull Room o) {
        return Integer.compare(o.roomSize,roomSize);
    }
}

