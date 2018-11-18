package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.BitmapFactory;
import android.view.View;

import csc472.depaul.edu.metalcrawler.R;

public class Player extends Actor {
    float momentum = 1;
    int ldx = 0;
    int ldy = 0;
    boolean riposte;

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
            //if they are riposted then make take them out of it and reset momentum
            if(riposte){
                momentum = 1;
                riposte = false;
            }
            momentum += 1;
        }
        //riposte, if the player moves in the opposite direction of where they were initially going then give them some damage modification in the form of momentum
        else if((ldx == dx * -1 && ldy == dy) || (ldx == dx && ldy == dy * -1)){
            momentum = (float) 1.5;
            //put them in the riposte position
            riposte = true;
        }
        else
        {
            momentum = 1;
            riposte = false;
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
