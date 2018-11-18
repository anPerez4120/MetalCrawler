package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Gold extends Entity{

    public Gold(View view) {
        super(view);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.gold);
        type = EntityType.GOLD;
    }

    public Gold(View view, int x, int y)
    {
        super(view);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.gold);
        type = EntityType.GOLD;
        SetPosition(x,y);

    }

    public void Recycle()
    {
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);
        GoldFactory.Instance().ReturnGold(this);
    }

    @Override
    public void OnTouched(Actor other)
    {
        //add 100 points to the score if the player touches the gold
        if(other.getClass() == Player.class){
            GameManager.Instance().addToScore(100);
            GameManager.Instance().saveHighScorePreference();
        }
        Recycle();
    }


}
