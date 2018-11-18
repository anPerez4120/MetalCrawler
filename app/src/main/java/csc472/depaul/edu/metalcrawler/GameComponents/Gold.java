package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Gold extends Entity{

    public Gold(View view) {
        super(view);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.gold);
        type = EntityType.GOLD;
        Init();
    }

    public Gold(View view, int x, int y)
    {
        super(view);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.gold);
        type = EntityType.GOLD;
        SetPosition(x,y);
        Init();
    }

    void Init()
    {
        description = "This shiny gold cog is the object of your adventure! Grab it to increase your score.";
    }

    @Override
    public void Recycle()
    {
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);
        EntityFactory.Instance().ReturnGold(this);
    }

    @Override
    public void OnTouched(Actor other)
    {
        Recycle();
    }


}
