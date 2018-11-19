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
        Init();
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bull);
    }

    void Init()
    {
        isSolid = true;
        name = "Bronze Bull";
        type = EntityType.ENEMY;
        description = "A soulless Bronze Bull. Upon seeing the player, charges forward until it hits a wall, where it stays for three turns. Very deadly.";
        bullState = BullFSM.seek;
        health=50;
        health_max=50;
        damage = 25;
    }


    public Bull(View view, int x, int y)
    {
        super(view);
        SetPosition(x,y);
        Init();
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.bull);
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

    @Override
    public String GetDescription()
    {
        String desc = super.GetDescription();

        if (stun > 0) {
            desc += String.format("\n\nStunned for %d more turns",stun);
        }

        return desc;
    }

}
