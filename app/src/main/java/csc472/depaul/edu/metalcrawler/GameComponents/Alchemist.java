package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.util.Random;

import csc472.depaul.edu.metalcrawler.R;

public class Alchemist extends Actor implements IEnemy {

    int move = 0;
    public Alchemist(View view) {
        super(view);
        isSolid = true;
        health=10;
        health_max=10;
        damage = 30;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.alchemist);
        type = EntityType.ENEMY;
        Init();
    }

    void Init()
    {
        description = "A mangey junkie looking for normal-people flesh to eat. Not particularly dangerous, but do meet them with deadly force.";
    }


    public Alchemist(View view, int x, int y)
    {
        super(view);
        SetPosition(x,y);
        isSolid = true;

        health=20;
        health_max=20;

        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.alchemist);
        type = EntityType.ENEMY;
        Init();
    }

    public void Recycle()
    {
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);
        EnemyFactory.Instance().ReturnAlchemist(this);
    }

    @Override
    public void Die()
    {
        super.Die();
        Recycle();
    }

    @Override
    protected void SetDrawLayer()
    {
        drawLayer = 1;
    }

    @Override
    public void Update()
    {
        // move++;
        // if (move % 2 == 0)
        //     return;
        Player player = GameManager.Instance().GetPlayer();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        // 50% chance of aligning X first
        if (random.nextFloat() > 0.5f)
        {
            if (player.GetX() > x)
                MoveRight();
            else if (player.GetX() < x)
                MoveLeft();
            else
            {
                if (player.GetY() < y)
                    MoveUp();
                else
                    MoveDown();
            }
        }
        else // Otherwise align y first
        {
            if (player.GetY() < y)
                MoveUp();
            else if (player.GetY() > y)
                MoveDown();
            else
            {
                if (player.GetX() > x)
                    MoveRight();
                else
                    MoveLeft();
            }
        }
    }

    @Override
    public void PrintEntity()
    {
        System.out.println("JUnkie");
        Log.d("Junkie","Junkie");
    }
}
