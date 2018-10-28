package csc472.depaul.edu.metalcrawler.GameComponents;

import java.util.List;

public class GameManager {
    List<Environment> environmentList;

    private static GameManager instance;
    public static GameManager Instance()
    {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    public void GameStart()
    {
        // TODO
    }

    public void GameEnd()
    {
        // TODO
    }

    public void PerformTurn()
    {
        // TODO
    }





}
