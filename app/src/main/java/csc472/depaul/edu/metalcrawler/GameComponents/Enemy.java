package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.DrawTest;
import csc472.depaul.edu.metalcrawler.R;

public class Enemy extends Actor {
    public Enemy(View view) {
        super(view);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.andremad_display);
        // ((DrawTest)view).SetPlayer(this);

    }


}
