package csc472.depaul.edu.metalcrawler.GameComponents;


import android.view.View;

public class Entity extends Sprite {
    boolean isSolid;

    public Entity(View view) {
        super(view);
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,x,y,this);
    }

    public Entity(View view, int x, int y) {
        super(view);
    }

    public void OnTouched(Actor other)
    {
        // Touch ME ;)))))
    }

    public boolean IsSolid()
    {
        return isSolid;
    }
}
