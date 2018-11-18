package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Canvas;

import csc472.depaul.edu.metalcrawler.DrawTest;

public abstract class DrawState {
    public abstract void Draw(Canvas canvas, DrawTest drawTest);
}
