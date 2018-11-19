package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.util.Random;

import csc472.depaul.edu.metalcrawler.R;

public class Alchemist extends Actor implements IEnemy {

    int move = 0;
    int fireCounter;
    AlchemistState alchemistState = AlchemistFSM.seek;

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
        name = "Alchemist";
        description = "A frenzied Alchemist who huffs his own concoctions to get high.";
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

    @Override
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
        alchemistState = alchemistState.GetNextState(this);
    }

    @Override
    public void PrintEntity()
    {
        System.out.println("Junkie");
        Log.d("Junkie","Junkie");
    }

    public void SetFire(int count)
    {
        this.fireCounter = count;
    }

    public void RunFire()
    {fireCounter--;}

    public boolean IsFiring()
    {return fireCounter > 0;}


    @Override
    public String GetDescription()
    {
        String desc = super.GetDescription();

        if (fireCounter > 0) {
            desc += String.format("\n\nFireball is on cooldown for %d more turns",fireCounter);
        }

        return desc;
    }

}
