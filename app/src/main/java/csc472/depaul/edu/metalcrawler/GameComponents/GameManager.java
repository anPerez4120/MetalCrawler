package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.CellularAutomata;

public class GameManager {
    List<Environment> environmentList;
    int currentEnvironment = -1;
    CellularAutomata cellAuto;

    private static GameManager instance;
    public static GameManager Instance()
    {
        if (instance == null) {
            instance = new GameManager();
            instance.environmentList = new ArrayList<Environment>();
            instance.cellAuto = new CellularAutomata();
        }
        return instance;
    }
View view;
    int w;
    int h;
    public void GameStart(View view)
    {
        // TODO
        this.view = view;
        w = 15;
        h = 15;
        environmentList.add(new Environment(view,w,h));
        currentEnvironment++;
        environmentList.get(currentEnvironment).PopulateTiles(view,cellAuto.GenerateMap(w,h));
        view.invalidate();
    }

    public void GameEnd()
    {
        // TODO
    }

    public void PerformTurn()
    {
//        environmentList.get(currentEnvironment).PopulateTiles(view,cellAuto.GenerateMap(w,h));
    }

    public void Draw(Canvas canvas)
    {
        environmentList.get(currentEnvironment).Draw(canvas);
    }





}
