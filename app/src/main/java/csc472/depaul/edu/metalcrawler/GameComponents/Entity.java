package csc472.depaul.edu.metalcrawler.GameComponents;


import android.util.Log;
import android.view.View;

public class Entity extends Sprite {
    boolean isSolid;
    EntityType type;

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


    public void SetType(EntityType type)
    {
        this.type = type;
    }

    public EntityType GetType()
    {
        return type;
    }

    @Override
    public void SetPosition(int x, int y)
    {
        int ox = this.x;
        int oy = this.y;
        super.SetPosition(x,y);
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(ox,oy,x,y,this);
    }

    public void PrintEntity()
    {
        System.out.println("UH");
        Log.d("UH","IH");
    }
}
