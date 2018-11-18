package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Potion extends Entity {

    float healAmount = 45;

    public Potion(View view) {
        super(view);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.potion);
        type = EntityType.POTION;
        Init();
    }

    public Potion(View view, int x, int y) {
        super(view, x, y);
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.potion);
        type = EntityType.POTION;
        SetPosition(x,y);
        Init();
    }

    void Init()
    {
        description = "Looks like someone left this here... Whoever drinks it will be healed 45 hp.";
    }

    @Override
    public void OnTouched(Actor other)
    {
        other.Heal(healAmount);
        Recycle();
    }

    @Override
    public void Recycle()
    {
        GameManager.Instance().GetCurrentEnvironment().HookUpTile(x,y,-1,-1,this);
        EntityFactory.Instance().ReturnPotion(this);
    }

}
