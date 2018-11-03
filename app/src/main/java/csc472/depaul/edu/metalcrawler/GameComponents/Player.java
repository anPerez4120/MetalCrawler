package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.DrawTest;
import csc472.depaul.edu.metalcrawler.R;

public class Player extends Actor {

    public Player(View view) {
        super(view);
        isSolid = true;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.andremad_display);
        ((DrawTest)view).SetPlayer(this);
    }

    @Override
    public void OnTouched(Actor other)
    {
        health -= other.GetDamage();
    }

}