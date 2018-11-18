package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.util.Random;

import csc472.depaul.edu.metalcrawler.R;

public class Fireball extends Actor{

    int move = 0;
    int dx = 0;
    int dy = 0;

    public Fireball(View view) {
        super(view);
        isSolid = true;
        health=20;
        health_max=20;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball);
        type = EntityType.ENEMY;
        Init();
    }

    void Init()
    {
        description = "Get out of the way.";
    }

    public Fireball(View view, int x, int y)
    {
        Init();
        isSolid = true;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.fireball);
        type = EntityType.ENEMY;
        freezeUpdate = true;
        SetDrawLayer();
        GameManager.Instance().AddSprite(this);
        GameManager.Instance().AddActor(this);
        // If the tile we spawned on is already taken, just die
        if (GameManager.Instance().GetCurrentEnvironment().GetTile(x,y) != null &&
            GameManager.Instance().GetCurrentEnvironment().GetTile(x,y).GetEntity() != null)
        {
            GameManager.Instance().GetCurrentEnvironment().GetTile(x,y).GetEntity().OnTouched(this);
            Recycle();
        }
        else { // else, keep going
            GameManager.Instance().GetCurrentEnvironment().HookUpTile(x, y, x, y, this);
            SetPosition(x, y);
        }
    }

    @Override
    public void Recycle()
    {
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);
        EnemyFactory.Instance().ReturnFireball(this);
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

    boolean freezeUpdate;

    @Override
    public void Update()
    {
        if (freezeUpdate)
        {
            freezeUpdate = false;
            return;
        }
        int xx = x;
        int yy = y;
        switch(dx)
        {
            case 1:
                MoveRight();
                break;
            case -1:
                MoveLeft();
                break;
        }
        switch(dy)
        {
            case 1:
                MoveDown();
                break;
            case -1:
                MoveUp();
                break;
        }

        if (x == xx && y == yy)
            Recycle();
    }

    @Override
    public void OnTouched(Actor actor)
    {
        actor.Damage(damage);
        Recycle();
    }

    @Override
    public float GetDamage()
    {
        float d = super.GetDamage();
        Recycle();
        return d;
    }


    @Override
    public void PrintEntity()
    {
        System.out.println("Fireball");
        Log.d("Fireball","Fireball");
    }

    public void SetDX(int dx)
    {
        this.dx = dx;
    }

    public void SetDY(int dy)
    {
        this.dy = dy;
    }

    public void SetFreezeUpdate(boolean freezeUpdate)
    {
        this.freezeUpdate = freezeUpdate;
    }
}
