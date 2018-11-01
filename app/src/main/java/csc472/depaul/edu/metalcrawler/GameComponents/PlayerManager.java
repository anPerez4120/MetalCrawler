package csc472.depaul.edu.metalcrawler.GameComponents;

import android.view.View;

public class PlayerManager
{
    static PlayerManager instance;

    public static PlayerManager Instance()
    {
        if (instance == null)
            instance = new PlayerManager();
        return instance;
    }

    Player player;

    public Player GetPlayer()
    {
        return player;
    }

    View view;
    public void SetView(View view)
    {
        this.view = view;
    }

    public void SpawnPlayer(int x, int y)
    {
        player = new Player(view);
        player.JumpToPosition(x,y);
    }

}
