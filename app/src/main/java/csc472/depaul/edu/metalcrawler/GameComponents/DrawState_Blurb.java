package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;

import csc472.depaul.edu.metalcrawler.DrawTest;

public class DrawState_Blurb extends DrawState_Base {

    @Override
    public void Draw(Canvas canvas, DrawTest drawTest)
    {
        super.Draw(canvas,drawTest);
        drawTest.DrawBlurb(canvas);
    }
}
