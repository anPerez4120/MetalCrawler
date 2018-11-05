package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Player extends Actor {
    float momentum = 1;
    int ldx = 0;
    int ldy = 0;

    public Player(View view) {
        super(view);
        drawLayer = 1;
        isSolid = true;
        bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.player);
        GameManager.Instance().SetPlayer(this);
        type = EntityType.PLAYER;
    }

    @Override
    public void AttemptMove(int dx, int dy)
    {
        if (ldx == dx && ldy == dy)
        {
            momentum += 1;
        } else
        {
            momentum = 1;
        }

        ldx = dx;
        ldy = dy;

        super.AttemptMove(dx,dy);
    }

    @Override
    public float GetDamage()
    {
        return damage * momentum;
    }


    @Override
    protected void SetDrawLayer()
    {
        drawLayer = 1;
    }

}
