package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import java.util.Random;

import csc472.depaul.edu.metalcrawler.R;

public class Enemy extends Actor {
    int move = 0;
    public Enemy(View view) {
        super(view);
        isSolid = true;
        health=20;
        health_max=20;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.junkie);
        type = EntityType.ENEMY;
    }

    public Enemy(View view, int x, int y)
    {
        super(view);
        SetPosition(x,y);
        isSolid = true;

        health=20;
        health_max=20;

        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.junkie);
        type = EntityType.ENEMY;
    }

    public void Recycle()
    {
        EnemyFactory.Instance().ReturnEnemy(this);
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
}
