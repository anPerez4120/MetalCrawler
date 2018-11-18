package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.util.Random;

import csc472.depaul.edu.metalcrawler.R;

public class Bull extends Actor implements  IEnemy {

    BullState bullState;
    int stun = 0;
    public Bull(View view) {
        super(view);
        isSolid = true;
        health=50;
        health_max=50;
        damage = 25;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bull);
        type = EntityType.ENEMY;
        Init();
    }

    void Init()
    {
        description = "A soulless Bronze Bull. Upon seeing the player, charges forward until it hits a wall, where it stays for three turns. Very deadly.";
        bullState = BullFSM.seek;
    }


    public Bull(View view, int x, int y)
    {
        super(view);
        SetPosition(x,y);
        isSolid = true;

        health=20;
        health_max=20;

        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bull);
        type = EntityType.ENEMY;
        Init();
    }

    @Override
    public void Recycle()
    {
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);
        EnemyFactory.Instance().ReturnBull(this);
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

        bullState = bullState.GetNextState(this);

    }

    @Override
    public void PrintEntity()
    {
        System.out.println("Bull");
        Log.d("Bull","Bull");
    }

    public void SetStun(int stun)
    {
        this.stun = stun;
    }

    public void RunStun()
    {
        stun--;
    }

    public boolean IsStunned()
    {
        return stun > 0;
    }


}
