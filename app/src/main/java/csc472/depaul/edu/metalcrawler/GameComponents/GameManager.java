package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    List<Environment> environmentList;
    int currentEnvironment = -1;

    private static GameManager instance;
    public static GameManager Instance()
    {
        if (instance == null) {
            instance = new GameManager();
            instance.environmentList = new ArrayList<Environment>();
        }
        return instance;
    }

    public void GameStart(View view)
    {
        // TODO
        environmentList.add(new Environment(view,10,10));
        currentEnvironment++;
        view.invalidate();
    }

    public void GameEnd()
    {
        // TODO
    }

    public void PerformTurn()
    {
        // TODO
        // Update everything
        // Draw everything

    }

    public void Draw(Canvas canvas)
    {
        environmentList.get(currentEnvironment).Draw(canvas);
    }





}
