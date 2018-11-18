package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.Stack;

public class DoorFactory {
    Stack<Door> doors;

    private static DoorFactory instance;
    public static DoorFactory Instance()
    {
        if (instance == null) {
            instance = new DoorFactory();
            instance.doors = new Stack<Door>();
        }
        return instance;
    }


    public Door GetDoor(int x, int y)
    {
        if (doors.empty())
        {
            Door door = new Door(GameManager.Instance().GetView(),x,y);
            GameManager.Instance().AddEntity(door);
            return door;
        }
        else
        {
            Door door= doors.pop();
            GameManager.Instance().AddSprite(door);
            GameManager.Instance().AddEntity(door);
            door.SetPosition(x,y);
            return door;
        }
    }

    public void ReturnDoor(Door door)
    {
        GameManager.Instance().RemoveSprite(door);
        GameManager.Instance().RemoveEntity(door);
        doors.push(door);
    }
}
