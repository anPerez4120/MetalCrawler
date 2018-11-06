package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Door extends Entity{ // Door is an entity -- actors can touch it -- entities cannot touch each other

    public Door(View view) {
        super(view);
        isSolid = false;
        type = EntityType.DOOR;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.door);
    }

    public Door(View view,int x,int y)
    {
        super(view);
        isSolid = false;
        type = EntityType.DOOR;
        SetPosition(x,y);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.door);

    }


    @Override
    public void OnTouched(Actor other)
    {
        // if other is player
        if (other.GetType() == EntityType.PLAYER)
        {
            GameManager.Instance().GenerateNewMap();
        }
    }

    @Override
    protected void SetDrawLayer()
    {
        drawLayer = 1;
    }
}
