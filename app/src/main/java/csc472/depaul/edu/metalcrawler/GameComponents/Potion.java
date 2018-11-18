package csc472.depaul.edu.metalcrawler.GameComponents;

import android.view.View;

public class Potion extends Entity {

    float healAmount = 45;
    
    public Potion(View view) {
        super(view);
    }

    public Potion(View view, int x, int y) {
        super(view, x, y);
    }

    @Override
    public void OnTouched(Actor other)
    {
        other.Heal(healAmount);
    }

}
