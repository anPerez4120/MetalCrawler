package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;

import csc472.depaul.edu.metalcrawler.DrawTest;
import csc472.depaul.edu.metalcrawler.GameComponents.DrawState;

public class DrawState_Base extends DrawState {

    public void Draw(Canvas canvas, DrawTest drawTest)
    {
        drawTest.DrawGame(canvas);
    }

}
